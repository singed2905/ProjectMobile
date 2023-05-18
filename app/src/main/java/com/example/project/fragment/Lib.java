package com.example.project.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.project.event.OnClickListener;
import com.example.project.R;
import com.example.project.activity.PlaylistActivity;
import com.example.project.adapter.AdapterFavourites;
import com.example.project.adapter.AdapterRecently;
import com.example.project.model.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Lib#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Lib extends Fragment implements OnClickListener {

    RecyclerView homeRecycler;
    RecyclerView homeFavourites;

    List<Subject> listMusic;
    View view;

    public Lib() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lib, container, false);
        this.init();
        this.setAdapterRecently();
        this.setAdapterFavourites();
        return view;
    }
    public void init() {
        homeRecycler = view.findViewById(R.id.homeRecycler);
        homeFavourites = view.findViewById(R.id.homeFavourites);
        listMusic = new ArrayList<>();
        this.initListMusic();
        System.out.println(R.id.linearLayout);
    }

    public void setAdapterRecently() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        homeRecycler.setLayoutManager(layoutManager);
        AdapterRecently adapterRecently = new AdapterRecently();
        adapterRecently.setData(listMusic,this);
        homeRecycler.setAdapter(adapterRecently);
    }
    public void setAdapterFavourites() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        homeFavourites.setLayoutManager(gridLayoutManager);
        AdapterFavourites adapterFavourites = new AdapterFavourites();
        adapterFavourites.setData(listMusic,this);
        homeFavourites.setAdapter(adapterFavourites);
    }

    public void initListMusic() {
        //get list
        this.listMusic.add(new Subject("Heartiess", "The Weekend", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRey6EOaGZO6WpC8zMYWKqbSII6V3hSUTrSdQ&usqp=CAU"));
        this.listMusic.add(new Subject("Heartiess", "The Weekend", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRey6EOaGZO6WpC8zMYWKqbSII6V3hSUTrSdQ&usqp=CAU"));
        this.listMusic.add(new Subject("Heartiess", "The Weekend", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRey6EOaGZO6WpC8zMYWKqbSII6V3hSUTrSdQ&usqp=CAU"));
        this.listMusic.add(new Subject("Heartiess", "The Weekend", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRey6EOaGZO6WpC8zMYWKqbSII6V3hSUTrSdQ&usqp=CAU"));
        this.listMusic.add(new Subject("Heartiess", "The Weekend", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRey6EOaGZO6WpC8zMYWKqbSII6V3hSUTrSdQ&usqp=CAU"));
        this.listMusic.add(new Subject("Heartiess", "The Weekend", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRey6EOaGZO6WpC8zMYWKqbSII6V3hSUTrSdQ&usqp=CAU"));

    }
    @Override
    public void clickItem() {
        Intent intent = new Intent(getActivity(), PlaylistActivity.class);
        startActivity(intent);
        Toast.makeText(this.getContext(), "clickITem", Toast.LENGTH_SHORT).show();
    }
}