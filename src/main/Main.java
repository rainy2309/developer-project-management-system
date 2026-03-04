package main;

import business.DeveloperManager;
import business.ManagementSystem;
import business.ProjectManager;
import tools.Inputter;
import tools.Menu;

public class Main {

    public static void main(String[] args) {
        DeveloperManager devManager = new DeveloperManager();
        ProjectManager prjManager = new ProjectManager();

        ManagementSystem system = new ManagementSystem(devManager, prjManager);

        try {
            system.loadData();
        } catch (Exception e) {
            System.out.println("Unable to reload data.");
        }

        Menu menu = new Menu("EMPLOYEE AND PROJECT MANAGEMENT PROGRAM");
        menu.addOption("List all Developers");
        menu.addOption("Add a new Developer");
        menu.addOption("Search for a Developer by ID");
        menu.addOption("Update a Developer's salary by ID");
        menu.addOption("List all Developers by Language");
        menu.addOption("Add a new Project");
        menu.addOption("List all Projects by Developer (Grouped)");
        menu.addOption("Calculate Total Experience by Dev ID");
        menu.addOption("Remove a Developer by ID");
        menu.addOption("Sort Developers by Salary");
        menu.addOption("Save data to files");
        menu.addOption("Quit program");
        menu.addOption("Year deadline");

        int choice;
        do {
            menu.display();
            choice = menu.getChoice();

            switch (choice) {

                case 1: {
                    devManager.listAllDevelopers();
                    break;
                }

                case 2: {
                    devManager.addNewDeveloper();
                    system.setChanged(true);
                    break;
                }

                case 3: {
                    devManager.searchById();
                    break;
                }

                case 4: {
                    devManager.updateSalary();
                    system.setChanged(true);
                    break;
                }

                case 5: {
                    devManager.listByLanguage();
                    break;
                }

                case 6: {
                    prjManager.addNewProject(devManager);
                    system.setChanged(true);
                    break;
                }

                case 7: {
                    prjManager.listGrouped(devManager);
                    break;
                }

                case 8: {
                    prjManager.calculateTotalExperience(devManager);
                    break;
                }

                case 9: {
                    if (devManager.removeDeveloper(prjManager)) {
                        system.setChanged(true);
                    }
                    break;
                }

                case 10: {
                    devManager.sortBySalary();
                    break;
                }

                case 11: {
                    system.saveData();
                    break;
                }

                case 12: {
                    quit(system);
                    break;
                }
                default:
                    System.out.println("Invalid choice. Please enter again!");
                    break;
                case 13:{
                    prjManager.yearDeadline();
                }
            }
                
        } while (choice != 12);
    }

    // 12
    public static void quit(ManagementSystem system) {
        if (system.isChanged()) {
            String confirm = Inputter.inputString("Do you want to save the changes before exiting? (Y/N): ");
            if (confirm.equalsIgnoreCase("Y")) {
                system.saveData();
                System.out.println("Data saved.");
            }
        }
        System.out.println("Exititng program. Goodbye!");
        System.exit(0);
    }
}
