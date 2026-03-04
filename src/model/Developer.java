package model;

import java.util.List;

public class Developer {

    private String id;
    private String name;
    private List<String> languages;
    private int salary;

    public Developer(String id, String name, List<String> languages, int salary) {
        this.id = id;
        this.name = name;
        this.languages = languages;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void showProfile() {
        System.out.printf("|%-6s|%-20s|%-25s|%6d|",
                id, name, languages.toString(), salary);
    }

    @Override
    public String toString() {
        return toFileString();
    }

    public String toFileString() {
        return id + ", " + name + ", ["
                + String.join(", ", languages)
                + "], " + salary;
    }
}
