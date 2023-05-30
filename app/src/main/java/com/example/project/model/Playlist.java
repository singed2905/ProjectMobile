package com.example.project.model;

import java.util.ArrayList;

public class Playlist {
    private String id;
    private String name;
    private ArrayList<Subject> subjects;

    public Playlist(String id, String name, ArrayList<Subject> subjects) {
        this.id = id;
        this.name = name;
        this.subjects = subjects;
    }

    public Playlist(String id, String name) {
        this.id = id;
        this.name = name;
        subjects = new ArrayList<>();
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
