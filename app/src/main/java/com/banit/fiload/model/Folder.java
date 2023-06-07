package com.banit.fiload.model;

public class Folder {
    private final String name;
    private final String date;

    public Folder(String name, String date) {
        this.name = name;
        this.date = date;
    }


    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
