package com.example.project.event;

import com.example.project.model.Subject;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface OnClickListener {
    public void clickItem();
    public void setData(ArrayList<Subject> arr);
    public void playSong(Subject subject, List<Subject> list,int position) throws JSONException, IOException;
}
