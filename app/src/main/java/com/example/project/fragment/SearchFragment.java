package com.example.project.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.activity.MainActivity;
import com.example.project.activity.PlaylistActivity;
import com.example.project.adapter.AdapterFavourites;
import com.example.project.adapter.AdapterListSound;
import com.example.project.api.SongAPI;
import com.example.project.event.OnClickListener;
import com.example.project.event.SetURLStream;
import com.example.project.model.Subject;
import com.example.project.service.ProcessBar;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        RecyclerView layout = view.findViewById(R.id.homeListSearch);
        LinearLayoutManager linearLayout  = new LinearLayoutManager(getContext());
        layout.setLayoutManager(linearLayout);
        Bundle args = getArguments();
        if (args != null) {
            ArrayList<Subject> subjects = (ArrayList<Subject>) args.getSerializable("subjects");
            AdapterListSound adapterListSound = new AdapterListSound();
            adapterListSound.setData(subjects,this);
            layout.setAdapter(adapterListSound);
        }

        return view;
    }


    @Override
    public void clickItem() {

    }

    @Override
    public void setData(ArrayList<Subject> arr) {

    }

    @Override
    public void playSong(Subject subject) throws JSONException, IOException {
        ProcessBar.setURL(subject.getUrl());
        Intent intent = new Intent(getActivity(), PlaylistActivity.class);
        intent.putExtra("id", subject.getId());
        intent.putExtra("nameAstist", subject.getArtist());
        intent.putExtra("title", subject.getName());
        intent.putExtra("img", subject.getSrc());
        startActivity(intent);


    }
}