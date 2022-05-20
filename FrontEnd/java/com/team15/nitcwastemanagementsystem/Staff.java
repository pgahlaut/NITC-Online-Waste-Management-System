package com.team15.nitcwastemanagementsystem;

public class Staff {

    private String email;
    private String name;
    private String uuid;
    private final int type = 2;

    public Staff(String uuid,String email, String name) {
        this.uuid=uuid;
        this.email = email;
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }
}
