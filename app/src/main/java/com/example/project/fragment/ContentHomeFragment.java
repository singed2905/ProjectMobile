package com.example.project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.adapter.AdapterListSound;
import com.example.project.adapter.AdapterPlaylist;
import com.example.project.adapter.AdapterPlaylistStyle2;
import com.example.project.adapter.BannerAdapter;
import com.example.project.model.Subject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContentHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContentHomeFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private  ArrayList<String> banner;
    private View view;
    public ContentHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContentHomeFragment newInstance(String param1, String param2) {
        ContentHomeFragment fragment = new ContentHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Bundle args = getArguments();
        banner = (ArrayList<String>) args.getSerializable("banner");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_content_home, container, false);
        ArrayList<String> images = new ArrayList<>();
        images.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRey6EOaGZO6WpC8zMYWKqbSII6V3hSUTrSdQ&usqp=CAU");
        images.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRey6EOaGZO6WpC8zMYWKqbSII6V3hSUTrSdQ&usqp=CAU");
        images.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRey6EOaGZO6WpC8zMYWKqbSII6V3hSUTrSdQ&usqp=CAU");
        images.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRey6EOaGZO6WpC8zMYWKqbSII6V3hSUTrSdQ&usqp=CAU");
        images.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRey6EOaGZO6WpC8zMYWKqbSII6V3hSUTrSdQ&usqp=CAU");
        initBanner(banner);
        return view;
    }
    public void initSongNews(ArrayList<Subject> subjects){
        LinearLayoutManager linearLayout  = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        RecyclerView songsNews = view.findViewById(R.id.newSongs);
        songsNews.setLayoutManager(linearLayout);
        AdapterListSound recently2 = new AdapterListSound();
        recently2.setData(subjects, null);
        songsNews.setAdapter(recently2);
    }
    public void initPlaylist(ArrayList<Subject> subjects, int id,int id_title, String title){
        RecyclerView homeFavourites = view.findViewById(id);
        TextView t = view.findViewById(id_title);
        t.setText(title);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        homeFavourites.setLayoutManager(layoutManager);
        AdapterPlaylist recently = new AdapterPlaylist();
        recently.setData(subjects, null);
        homeFavourites.setAdapter(recently);
    }
    public void initPlaylistStyle2(ArrayList<Subject> subjects, int id,int id_title, String title){
        RecyclerView homeFavourites = view.findViewById(id);
        TextView t = view.findViewById(id_title);
        t.setText(title);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        homeFavourites.setLayoutManager(layoutManager);
        AdapterPlaylistStyle2 recently = new AdapterPlaylistStyle2();
        recently.setData(subjects, null);
        homeFavourites.setAdapter(recently);
    }
    public void initBanner(ArrayList<String> images){
        ViewPager viewPager;
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        BannerAdapter viewPagerAdapter = new BannerAdapter(view.getContext(), images);
        viewPager.setAdapter(viewPagerAdapter);
    }
    public ArrayList<Subject> initListMusic() {
        //get list
        ArrayList<Subject> listMusic = new ArrayList<>();
        listMusic.add(new Subject("dsds","Heartiess", "The Weekend", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRey6EOaGZO6WpC8zMYWKqbSII6V3hSUTrSdQ&usqp=CAU", "sdsd"));
        listMusic.add(new Subject("dsds","Heartiess", "The Weekend", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRey6EOaGZO6WpC8zMYWKqbSII6V3hSUTrSdQ&usqp=CAU", "sdsd"));
        listMusic.add(new Subject("dsds","Heartiess", "The Weekend", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRey6EOaGZO6WpC8zMYWKqbSII6V3hSUTrSdQ&usqp=CAU", "sdsd"));
        listMusic.add(new Subject("dsds","Heartiess", "The Weekend", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRey6EOaGZO6WpC8zMYWKqbSII6V3hSUTrSdQ&usqp=CAU", "sdsd"));
        listMusic.add(new Subject("dsds","Heartiess", "The Weekend", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRey6EOaGZO6WpC8zMYWKqbSII6V3hSUTrSdQ&usqp=CAU", "sdsd"));
        listMusic.add(new Subject("dsds","Heartiess", "The Weekend", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRey6EOaGZO6WpC8zMYWKqbSII6V3hSUTrSdQ&usqp=CAU", "sdsd"));
        listMusic.add(new Subject("dsds","Heartiess", "The Weekend", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRey6EOaGZO6WpC8zMYWKqbSII6V3hSUTrSdQ&usqp=CAU", "sdsd"));
        listMusic.add(new Subject("dsds","Heartiess", "The Weekend", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRey6EOaGZO6WpC8zMYWKqbSII6V3hSUTrSdQ&usqp=CAU", "sdsd"));

        return listMusic;
    }
}