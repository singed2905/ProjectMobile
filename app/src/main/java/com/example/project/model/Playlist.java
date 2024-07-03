package com.example.project.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Playlist implements Serializable {
    private String id;
    private String name;
    private String img;
    private String description;

    private ArrayList<Subject> subjects;

    public Playlist(String id, String name,String img, ArrayList<Subject> subjects) {
        this.id = id;
        this.name = name;
        this.subjects = subjects;
        this.img = img;
    }

    public Playlist() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Playlist(String id, String name, String img) {
        this.id = id;
        this.name = name;
        subjects = new ArrayList<>();
        this.img = img;

    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }
}
