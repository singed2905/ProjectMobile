package com.example.project.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.activity.PlaylistActivity;
import com.example.project.adapter.AdapterListSound;
import com.example.project.event.OnClickListener;
import com.example.project.model.Playlist;
import com.example.project.model.Subject;
import com.example.project.service.ProcessBar;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaylistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaylistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private Playlist playlist;
    private String mParam2;

    public PlaylistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlaylistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaylistFragment newInstance(String param1, String param2) {
        PlaylistFragment fragment = new PlaylistFragment();
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

        playlist = (Playlist) getArguments().getSerializable("playlist");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_playlist, container, false);
        view.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_home));

        TextView title = view.findViewById(R.id.title_playlist_list);
        TextView count = view.findViewById(R.id.count_playlist_list);
        if(playlist.getName().equals("")){
            title.setText("Không có tiêu đề");
        }else{
            title.setText(playlist.getName());
        }
        count.setText(playlist.getSubjects().size() + " bài hát");
        initSongs(playlist.getSubjects());
        ImageView imageView = view.findViewById(R.id.imageView2);
        Picasso.get().load(playlist.getImg()).into(imageView);
        return view;
    }
    public void initSongs(ArrayList<Subject> subjects){
        LinearLayoutManager linearLayout  = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        RecyclerView songsNews = view.findViewById(R.id.list_song_in_playlist);
        songsNews.setLayoutManager(linearLayout);
        AdapterListSound recently2 = new AdapterListSound();
        recently2.setData(subjects, new OnClickListener() {
            @Override
            public void clickItem() {

            }

            @Override
            public void setData(ArrayList<Subject> arr) {

            }

            @Override
            public void playSong(Subject subject, List<Subject> list, int position) throws JSONException, IOException {
                Intent intent = new Intent(getActivity(), PlaylistActivity.class);
                intent.putExtra("id", subject.getId());
                intent.putExtra("nameAstist", subject.getArtist());
                intent.putExtra("title", subject.getName());
                intent.putExtra("img", subject.getSrc());
                ProcessBar.setURL(getContext(),subject.getUrl(),subject.getId());
                startActivity(intent);
            }
        });
        songsNews.setAdapter(recently2);
    }
}