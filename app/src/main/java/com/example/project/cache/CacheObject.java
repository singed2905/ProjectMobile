package com.example.project.cache;

import com.example.project.model.Section;
import com.example.project.model.SectionPlaylist;
import com.example.project.model.SectionSong;

public class CacheObject {
    private Section object;
    private SectionPlaylist sp;
    private SectionSong ss;
    private String className;

    public CacheObject(Section object) {
        this.object = object;
        this.className = object.getClass().getName();
        if(this.className.contains("SectionSong")){
            ss = (SectionSong) object;
        }else{
            sp = (SectionPlaylist) object;
        }
    }

    public Section getObject() {
        if(this.getClassName().contains("SectionSong")){
            Section rs =  ss;
            return  rs;
        }else{
            Section rs = sp;
            return  rs;
        }
    }
    public String getClassName() {
        return className;
    }
}