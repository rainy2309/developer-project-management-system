package business;

import tools.FileUtils;

public class ManagementSystem {

    private DeveloperManager devManager;
    private ProjectManager prjManager;
    private boolean isChanged = false;

    public ManagementSystem(DeveloperManager devManager, ProjectManager prjManager) {
        this.devManager = devManager;
        this.prjManager = prjManager;
    }
    
    public void loadData() throws Exception {
        devManager.loadFromFile();
        prjManager.loadFromFile(devManager);
        System.out.println("Data reloaded on program start");
    }

    // 11
    public void saveData() {
        if(!isChanged){
            System.out.println("No changes detected. Skip saving.");
            return;
        }

        FileUtils.saveToFile("data/developers.txt", devManager.getDevList());
        FileUtils.saveToFile("data/projects.txt", prjManager.getPrjList());

        this.isChanged = false;

        System.out.println("Save data successfully to developers.txt and projects.txt!");
    }

    public boolean isChanged() {
        return isChanged;
    }
    
    public void setChanged(boolean changed) {
        this.isChanged = changed;
    }
}
