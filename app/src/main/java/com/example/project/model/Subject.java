package com.example.project.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Subject implements Serializable, Parcelable {
    private String name;
    private String artist;
    private String src;
    private String id;
    private String url;


    public Subject() {

    }
    public Subject(String name, String artist, String src) {
        this.name = name;
        this.artist = artist;
        this.src = src;
    }

    public Subject(String id, String name, String artist, String src, String url) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.artist = artist;
        this.src = src;
    }

    protected Subject(Parcel in) {
        id = in.readString();
        name = in.readString();
        artist = in.readString();
        src = in.readString();
        url = in.readString();
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
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", src='" + src + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(artist);
        parcel.writeString(src);
        parcel.writeString(url);
    }

    public static final Parcelable.Creator<Subject> CREATOR = new Parcelable.Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };
}