package com.example.project.event;

import com.example.project.model.Subject;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public interface OnClickListener {
    public void clickItem();
    public void setData(ArrayList<Subject> arr);
    public void playSong(Subject subject) throws JSONException, IOException;

}
