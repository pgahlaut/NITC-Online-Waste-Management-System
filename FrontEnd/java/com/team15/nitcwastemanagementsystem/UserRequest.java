package com.team15.nitcwastemanagementsystem;

public class UserRequest {

    private String title;
    private String uuid;
    private String requestId;
    private String status;
    public UserRequest(String requestId,String title, String uuid, String location) {
        this.title = title;
        this.uuid = uuid;
        this.location = location;
        this.requestId = requestId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserRequest(String requestId, String title, String uuid, String location, String status) {
        this.title = title;
        this.uuid = uuid;
        this.location = location;
        this.requestId = requestId;
        this.status=status;
    }
    public UserRequest(String requestId,String title, String location) {
        this.title = title;
        this.location = location;
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String location;

}
