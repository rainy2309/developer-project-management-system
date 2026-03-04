package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Project {

    private String projectId;
    private String devId;
    private String projectName;
    private int duration;
    private LocalDate startDate;
    private LocalDate deadline;

    public Project(String projectId, String devId, String projectName,
            int duration, LocalDate startDate) {
        this.projectId = projectId;
        this.devId = devId;
        this.projectName = projectName;
        this.duration = duration;
        this.startDate = startDate;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getDevId() {
        return devId;
    }

    public int getDuration() {
        return duration;
    }

    public String toFileString() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return projectId + ", " + devId + ", " + projectName + ", "
                + duration + ", " + startDate.format(df);
    }

    @Override
    public String toString() {
        return toFileString();
    }

}
