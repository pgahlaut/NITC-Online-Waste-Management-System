package com.team15.nitcwastemanagementsystem;

import android.util.Log;

public class Admin {
    private int permissionLevel = 1;
    private String email;
    private String name;
    private String uuid;
    private String domainURL = "http://172.16.25.192:80/api";
    private static Admin admin=null;
    private Admin() {

    }

    public String getDomainURL() {
        return domainURL;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {

        this.uuid = uuid;
        Log.d("ADMIN CLASS:","ID set to "+this.getUuid());
    }

    public static Admin getInstance(){
        if(admin==null)
        admin =  new Admin();
        return admin;
    }
    public int getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(int permissionLevel) {
        this.permissionLevel = permissionLevel;
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

    public String toString(){
        return admin.getUuid()+" "+admin.getPermissionLevel()+" "+admin.getEmail();
    }
}
