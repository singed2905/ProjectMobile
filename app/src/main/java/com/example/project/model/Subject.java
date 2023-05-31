package com.example.project.model;

import java.io.Serializable;

public class Subject implements Serializable {
    private String name;
    private String artist;
    private String src;
    private String id;
    private String url ;


    public Subject() {

    }
    public Subject(String name, String artist, String src) {
        this.name = name;
        this.artist = artist;
        this.src = src;
    }
    public Subject(String id,String name, String artist, String src, String url) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.artist = artist;
        this.src = src;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
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
                "id='" + id + '\'' +

                "name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", src='" + src + '\'' +
                '}';
    }
}
