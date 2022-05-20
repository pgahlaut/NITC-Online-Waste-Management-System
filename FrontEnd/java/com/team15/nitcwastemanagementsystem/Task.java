package com.team15.nitcwastemanagementsystem;

public class Task {

    private String taskid;
    private String title;
    private String location;

    public Task(String taskid, String title, String location) {
        this.taskid = taskid;
        this.title = title;
        this.location = location;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
