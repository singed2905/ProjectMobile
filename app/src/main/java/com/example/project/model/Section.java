package com.example.project.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Section implements Serializable {
    private String sectionType;
    private String title;

    public Section(String sectionType, String title) {
        this.sectionType = sectionType;
        this.title = title;
    }

    public Section() {
    }

    public String getSectionType() {
        return sectionType;
    }

    public void setSectionType(String sectionType) {
        this.sectionType = sectionType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
