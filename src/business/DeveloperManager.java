package business;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import tools.Inputter;
import model.Developer;
import tools.Acceptable;

public class DeveloperManager {

    private List<Developer> devList = new ArrayList<>();

    public List<Developer> getDevList() {
        return devList;
    }

    public void loadFromFile() throws Exception {
        File f = new File("data/developers.txt");
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

                Developer dev = parseDeveloperLine(line);
                if (dev != null) {
                    devList.add(dev); // FIX: chỉ add khi hợp lệ
                }
            }
        }
    }

    /*
     * - DevID: DEVxxx, unique
     * - Name: >= 2 words
     * - Languages: không rỗng
     * - Salary >= 1000
     */
    private Developer parseDeveloperLine(String line) {

        int open = line.indexOf('[');
        int close = line.indexOf(']');

        // bắt buộc phải có []
        if (open < 0 || close < 0 || close < open) {
            System.out.println("Skip invalid developer line (missing []): " + line);
            return null;
        }

        // phần trước [
        String prefix = line.substring(0, open).trim();
        if (prefix.endsWith(",")) {
            prefix = prefix.substring(0, prefix.length() - 1).trim();
        }

        // prefix chỉ gồm: ID, Name
        String[] prefixParts = prefix.split(",");
        if (prefixParts.length < 2) {
            System.out.println("Skip invalid developer line (missing id/name): " + line);
            return null;
        }

        String id = prefixParts[0].trim().toUpperCase();
        String name = prefixParts[1].trim();

        // validate ID
        if (!id.matches(Acceptable.DEV_ID_VALID)) {
            System.out.println("Skip invalid Dev ID: " + line);
            return null;
        }

        // check duplicate ID
        if (findDeveloperById(id) != null) {
            System.out.println("Skip duplicate Dev ID: " + line);
            return null;
        }

        // validate name (>= 2 words)
        if (name.isEmpty() || name.split("\\s+").length < 2) {
            System.out.println("Skip invalid name (>=2 words): " + line);
            return null;
        }

        // languages trong []
        String langContent = line.substring(open + 1, close).trim();
        if (langContent.isEmpty()) {
            System.out.println("Skip empty languages: " + line);
            return null;
        }

        List<String> langs = new ArrayList<>();
        for (String s : langContent.split("\\s*,\\s*")) {
            if (!s.trim().isEmpty()) {
                langs.add(s.trim());
            }
        }

        if (langs.isEmpty()) {
            System.out.println("Skip empty languages: " + line);
            return null;
        }

        // phần sau ]
        String suffix = line.substring(close + 1).trim();
        if (suffix.startsWith(",")) {
            suffix = suffix.substring(1).trim();
        }

    suffix = suffix.replaceAll("[^0-9]", "");
        if (suffix.isEmpty()) {
            System.out.println("Skip invalid salary: " + line);
            return null;

        }

        int salary = Integer.parseInt(suffix);

        // validate salary
        if (salary < Acceptable.MIN_SALARY) {
            System.out.println("Skip salary < 1000: " + line);
            return null;
        }

        // tạo Developer 
        return new Developer(id, name, langs, salary);
    }

    // 1
    public void listAllDevelopers() {
        if (devList.isEmpty()) {
            return;
        }

        System.out.printf("|%-6s|%-20s|%-20s|%6s|\n",
                "ID", "Name", "Languages", "Salary");
        for (Developer d : devList) {
            System.out.printf("|%-6s|%-20s|%-20s|%6d|\n",
                    d.getId(), d.getName(), d.getLanguages().toString(), d.getSalary());
        }
    }

    // 2
    public void addNewDeveloper() {
        String id;
        while (true) {
            id = Inputter.inputNonEmptyString("Enter ID (DEVxxx): ").toUpperCase();
            if (!id.matches(Acceptable.DEV_ID_VALID)) {
                System.out.println("Failed: ID must be in format DEVxxx");
                continue;
            }
            if (findDeveloperById(id) != null) {
                System.out.println("Failed: This ID already exists!");
                continue;
            }
            break;
        }

        String name;
        while (true) {
            name = Inputter.inputNonEmptyString("Enter Fullname: ");
            if (name.trim().split("\\s+").length < 2) {
                System.out.println("The name must have at least 2 words!");
                continue;
            }
            break;
        }

        String language = Inputter.inputNonEmptyString("Enter Programming Languages: ");
        List<String> languageList = new ArrayList<>();
        for (String s : Arrays.asList(language.split("\\s*,\\s*"))) {
            if (!s.trim().isEmpty()) {
                languageList.add(s.trim());
            }
        }
        if (languageList.isEmpty()) {
            System.out.println("Failed: Languages cannot be empty!");
            return;
        }

//      List<String>levelList = new ArrayList<>();
        int salary = Inputter.inputInt("Enter salary (>= 1000): ", 1000);

        devList.add(new Developer(id, name, languageList, salary));
        System.out.println("Added successfully!");

    }

    // 3
    public Developer findDeveloperById(String id) {
        for (Developer d : devList) {
            if (d.getId().equalsIgnoreCase(id)) {
                return d;
            }
        }
        return null;
    }

    public void searchById() {
        String id = Inputter.inputString("Enter Developer ID: ");
        Developer d = findDeveloperById(id);

        if (d != null) {
            System.out.println(d.toString());
        } else {
            System.out.println("Developer ID does not exist!");
        }
    }

    // 4
    public void updateSalary() {
        String id = Inputter.inputString("Dev ID: ");
        Developer d = findDeveloperById(id);
        if (d == null) {
            System.out.println("Developer ID does not exist!");
        } else {
            int newSalary = Inputter.inputInt("New salary: ", 1000);
            d.setSalary(newSalary);
        }
    }

    // 5
    public void listByLanguage() {
        String languages = Inputter.inputNonEmptyString("Enter language: ").toLowerCase();
        boolean found = false;

        for (Developer d : devList) {
            List<String> devLangs = d.getLanguages();

            for (String lang : devLangs) {
                if (lang.toLowerCase().equals(languages)) {
                    System.out.println(d);
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("No developers found with language: " + languages);
        }
    }

    // 9
    public boolean removeDeveloper(ProjectManager prjManager) {
        String id = Inputter.inputString("Enter ID: ");
        Developer d = findDeveloperById(id);

        if (d == null) {
            System.out.println("Developer ID does not exist!");
            return false;
        }
        if (prjManager.isDevInAnyProject(id)) {
            System.out.println("Cannot delete: Developer is assigned to projects.");
            return false;
        } else {
            devList.remove(d);
            System.out.println("Deleted successfully!");
            return true;
        }

    }

    // 10
    public void sortBySalary() {
        // 1. Tạo một bản sao của danh sách gốc
        List<Developer> sortedList = new ArrayList<>(devList);

        // 2. Sắp xếp trên bản sao này
        sortedList.sort(Comparator.comparingInt(Developer::getSalary));

        // 3. In danh sách đã sắp xếp
        System.out.println("List of developers after sorting by salary in ascending order: ");
        System.out.printf("|%-6s|%-20s|%-20s|%6s|\n",
                "ID", "Name", "Languages", "Salary");
        for (Developer d : sortedList) {
            System.out.printf("|%-6s|%-20s|%-20s|%6d\n",
                    d.getId(), d.getName(), d.getLanguages().toString(), d.getSalary());
        }
    }
}
