package com.banit.fiload.model;

public class MyFile {
    private final String id, name, folder, type, size, date;

    public MyFile(String id, String name, String folder, String type, String size, String date) {
        this.id = id;
        this.name = name;
        this.folder = folder;
        this.type = type;
        this.size = size;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFolder() {
        return folder;
    }

    public String getType() {
        return type;
    }

    public String getSize() {
        return size;
    }

    public String getDate() {
        return date;
    }
}
