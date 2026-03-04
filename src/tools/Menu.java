package tools;

import java.util.ArrayList;

public class Menu {

    private String title;
    private ArrayList<String> options = new ArrayList<>();

    public Menu(String title) {
        this.title = title;
    }

    public void addOption(String option) {
        options.add(option);
    }

    public void display() {
        System.out.println("\n----- " + title + " -----");
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
    }

    public int getChoice() {
        return Inputter.inputIntRange("Enter a choice (1-" + options.size() + "): ", 1, options.size());
    }
}
