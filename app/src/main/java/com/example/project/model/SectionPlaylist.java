package com.example.project.model;

import java.io.Serializable;
import java.util.ArrayList;

public class SectionPlaylist extends Section implements Serializable {
    private ArrayList<Playlist> playlists;

    public SectionPlaylist(String sectionType, String title, ArrayList<Playlist> playlists) {
        super(sectionType, title);
        this.playlists = playlists;
    }

    public SectionPlaylist(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }
    public SectionPlaylist() {
    }
    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }
}
