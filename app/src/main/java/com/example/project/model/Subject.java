package com.example.project.model;

public class Subject {
    private String name;
    private String artist;
    private String src;

    public Subject(String name, String artist, String src) {
        this.name = name;
        this.artist = artist;
        this.src = src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", src='" + src + '\'' +
                '}';
    }
}
