package business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.Developer;
import model.Project;
import tools.Acceptable;
import tools.Inputter;

public class ProjectManager {

    private final List<Project> prjList = new ArrayList<>();
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter YF = DateTimeFormatter.ofPattern("yyyy");

    public List<Project> getPrjList() {
        return prjList;
    }

    public void loadFromFile(DeveloperManager devManager) throws Exception {
        File f = new File("data/projects.txt");
        if (!f.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                Project p = parseProjectLine(line, devManager);
                if (p != null) {
                    prjList.add(p);
                }
            }
        }
    }

    /*
     * Validate theo đề:
     * - Project ID unique
     * - Dev ID tồn tại trong dev list
     * - Duration >= 1
     * - Start date future
     */
    private Project parseProjectLine(String line, DeveloperManager devManager) {
        String[] parts = line.split(",");
        if (parts.length < 5) {
            System.out.println("Skip invalid project line: " + line);
            return null;
        }

        String projectId = parts[0].trim().toUpperCase();//projid
        String devId = parts[1].trim().toUpperCase();//devid
        String projectName = parts[2].trim();//name

        //check duplicate PROJECT ID (đúng biến)
        if (findProjectById(projectId) != null) {
            System.out.println("Skip duplicate Project ID: " + line);
            return null;
        }

        //check DEV ID exists (đúng điều kiện)
        if (devManager.findDeveloperById(devId) == null) {
            System.out.println("Skip project (Dev ID not found): " + line);
            return null;
        }

        if (projectName.isEmpty()) {
            System.out.println("Skip project (empty name): " + line);
            return null;
        }
        //dura
        int duration;
        try {
            duration = Integer.parseInt(parts[3].trim());
        } catch (Exception e) {
            System.out.println("Skip project (invalid duration): " + line);
            return null;
        }
        if (duration < 1) {
            System.out.println("Skip project (duration < 1): " + line);
            return null;
        }
        //ngay
        String dateStr = parts[4].trim();
        if (!dateStr.matches(Acceptable.DATE_VALID)) {
            System.out.println("Skip project (bad date format): " + line);
            return null;
        }
        LocalDate startDate;
        try {
            startDate = LocalDate.parse(dateStr, DF);
        } catch (Exception e) {
            System.out.println("Skip project (invalid date): " + line);
            return null;
        }

        if (!startDate.isAfter(LocalDate.now())) {
            System.out.println("Skip project (start date not future): " + line);
            return null;
        }   

        return new Project(projectId, devId, projectName, duration, startDate);
    }

    // helper
    public Project findProjectById(String projectId) {
        for (Project p : prjList) {
            if (p.getProjectId().equalsIgnoreCase(projectId)) {
                return p;
            }
        }
        return null;
    }

    // 6
    public void addNewProject(DeveloperManager devManager) {

        // projectId unique -> check trong prjList, không check trong dev list
        String prjId;
        while (true) {
            prjId = Inputter.inputNonEmptyString("Enter Project ID: ").toUpperCase();
            if (findProjectById(prjId) != null) {
                System.out.println("Project ID already exists!");
                continue;
            }
            break;
        }

        // Dev ID must exist
        String devId;
        while (true) {
            devId = Inputter.inputNonEmptyString("Enter Dev ID: ").toUpperCase();
            if (devManager.findDeveloperById(devId) == null) {
                System.out.println("This developer ID does not exist");
                continue;
            }
            break;
        }

        String name = Inputter.inputNonEmptyString("Enter Project name: ");
        int duration = Inputter.inputInt("Enter month (>=1): ", 1);

        // Date must be future
        LocalDate startDate;
        while (true) {
            String date = Inputter.inputNonEmptyString("Enter start date (dd/MM/yyyy): ");
            if (!date.matches(Acceptable.DATE_VALID)) {
                System.out.println("Wrong form dd/MM/yyyy!");
                continue;
            }

            try {
                startDate = LocalDate.parse(date, DF);
            } catch (Exception e) {
                System.out.println("A day doesn't exist!");
                continue;
            }

            if (!startDate.isAfter(LocalDate.now())) {
                System.out.println("Start date must be a future date!");
                continue;
            }
            break;
        }
        // deadline
//        LocalDate deadline;
//        while (true) {
//            String d = Inputter.inputNonEmptyString("Enter deadline (dd/MM/yyyy): ");
//            if (!d.matches(Acceptable.DATE_VALID)) {
//                System.out.println("Wrong format dd/MM/yyyy!");
//                continue;
//            }
//            try {
//                deadline = LocalDate.parse(d, DF);
//
//                if (!deadline.isAfter(startDate)) {
//                    System.out.println("Deadline must be after start date!");
//                    continue;
//                }
//                break;
//            } catch (Exception e) {
//                System.out.println("Invalid date!");
//            }
//        }

        prjList.add(new Project(prjId, devId, name, duration, startDate));
        System.out.println("Added project successfully!");
    }

    // 7
    public void listGrouped(DeveloperManager devManager) {
        if (devManager.getDevList().isEmpty()) {
            System.out.println("No developers to display.");
            return;
        }

        for (Developer dev : devManager.getDevList()) {
            System.out.println("\n[" + dev.getId() + "] " + dev.getName());
            boolean hasProject = false;

            for (Project p : prjList) {
                if (p.getDevId().equalsIgnoreCase(dev.getId())) {
                    System.out.println(" - Project: " + p.getProjectId()
                            + " (" + p.getDuration() + " months)"
                            + " [" + p.getStartDate().format(DF) + "]");
                    hasProject = true;
                }
            }

            if (!hasProject) {
                System.out.println(" - No project");
            }
        }
    }

    // 8
    public void calculateTotalExperience(DeveloperManager devManager) {
        String id = Inputter.inputString("Enter Dev ID: ").toUpperCase();

        if (devManager.findDeveloperById(id) == null) {
            // FIX: message nên đúng đề
            System.out.println("Developer ID does not exist!");
            return;
        }

        int total = 0;
        for (Project p : prjList) {
            if (p.getDevId().equalsIgnoreCase(id)) {
                total += p.getDuration();
            }
        }
        System.out.println("Total experience of " + id + ": " + total + " (months)");
    }

    // helper for menu 9
    public boolean isDevInAnyProject(String devID) {
        for (Project p : prjList) {
            if (p.getDevId().equalsIgnoreCase(devID)) {
                return true;
            }
        }
        return false;
    }

    public void yearDeadline() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int year = Inputter.inputInt("Entnter year(yyyy): ", 2000);
        System.out.printf("%-9s | %-6s | %-20s | %-9s | %s\n","ProjectID","DevID","Project Name","Duration","Start Date");
        for (Project p : prjList) {
            LocalDate endDate = p.getStartDate().plusMonths(p.getDuration());
            if (endDate.getYear() == year) {
                System.out.printf(" %-8s | %-6s | %-20s | %2d months | %s\n",
                        p.getProjectId(),
                        p.getDevId(),
                        p.getProjectName(),
                        p.getDuration(),
                        p.getStartDate().format(dtf));
            }
        }
    }
}
