package com.example.project.model;

import java.io.Serializable;
import java.util.ArrayList;

public class SectionSong extends  Section implements Serializable {
    private ArrayList<Subject> subjects;

    public SectionSong(String sectionType, String title, ArrayList<Subject> subjects) {
        super(sectionType, title);
        this.subjects = subjects;
    }

    public SectionSong(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }
    public SectionSong() {
    }
    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }
}
