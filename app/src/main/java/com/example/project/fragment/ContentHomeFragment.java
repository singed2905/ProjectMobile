package com.example.project.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.activity.PlaylistActivity;
import com.example.project.adapter.AdapterListSound;
import com.example.project.adapter.AdapterPlaylist;
import com.example.project.adapter.AdapterPlaylistStyle2;
import com.example.project.adapter.BannerAdapter;
import com.example.project.api.SongAPI;
import com.example.project.event.CallbackAPI;
import com.example.project.event.EventOpenPlaylist;
import com.example.project.event.OnClickListener;
import com.example.project.model.Playlist;
import com.example.project.model.Section;
import com.example.project.model.SectionPlaylist;
import com.example.project.model.SectionSong;
import com.example.project.model.Subject;
import com.example.project.service.ProcessBar;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContentHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContentHomeFragment extends Fragment implements EventOpenPlaylist{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private  ArrayList<Section> sections;
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
        sections = (ArrayList<Section>) args.getSerializable("sections");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_content_home, container, false);
        LinearLayout layoutManager = view.findViewById(R.id.main_home);
        layoutManager.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.angryimg));
        Stack<FragmentPlaylist> stack = new Stack<>();
        stack.add(new FragmentPlaylist( R.id.homeListPlaylist,R.id.title_playlist1,0));
        stack.add(new FragmentPlaylist( R.id.homeListPlaylist2,R.id.title_playlist2,0));
        stack.add(new FragmentPlaylist( R.id.homeListPlaylist3,R.id.title_playlist3,1));
        stack.add(new FragmentPlaylist( R.id.homeListPlaylist4,R.id.title_playlist4,0));

        for (int i = 0; i < sections.size(); i++) {
            Section section = sections.get(i);
            if(section.getSectionType().equals("banner")){
                SectionPlaylist sp = (SectionPlaylist)section;;

                System.out.println(sp.getTitle());
                initBanner(((SectionPlaylist) section).getPlaylists());
            }
            if(section.getSectionType().equals("new-release")){
                ArrayList<Subject> subjects =  ((SectionSong) section).getSubjects();
                initSongNews(subjects);
            }
            if(section.getSectionType().equals("playlist")){
                if(!stack.empty()){
                    FragmentPlaylist fragmentPlaylist = stack.pop();
                    ArrayList<Playlist> playlists =  ((SectionPlaylist) section).getPlaylists();
                    if(fragmentPlaylist.getStyle() == 0){
                        initPlaylist(playlists, fragmentPlaylist.getIdRec(),fragmentPlaylist.getIdTitle(),  section.getTitle());
                    }else{
                        initPlaylistStyle2(playlists, fragmentPlaylist.getIdRec(),fragmentPlaylist.getIdTitle(),  section.getTitle());
                    }
                }
            }
        }
        return view;
    }
    public void initSongNews(ArrayList<Subject> subjects){
        LinearLayoutManager linearLayout  = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        RecyclerView songsNews = view.findViewById(R.id.newSongs);
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
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("subjectList", (ArrayList<? extends Parcelable>) list);
                intent.putExtras(bundle);
                ProcessBar.setURL(getContext(),subject.getUrl(),subject.getId());
                startActivity(intent);
            }
        });
        songsNews.setAdapter(recently2);
    }
    public void initPlaylist(ArrayList<Playlist> playlists, int id,int id_title, String title){
        RecyclerView homeFavourites = view.findViewById(id);
        TextView t = view.findViewById(id_title);
        t.setText(title);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        homeFavourites.setLayoutManager(layoutManager);
        AdapterPlaylist recently = new AdapterPlaylist();
        recently.setData(playlists, this);
        homeFavourites.setAdapter(recently);
    }
    public void initPlaylistStyle2(ArrayList<Playlist> playlists, int id,int id_title, String title){
        RecyclerView homeFavourites = view.findViewById(id);
        TextView t = view.findViewById(id_title);
        t.setText(title);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        homeFavourites.setLayoutManager(layoutManager);
        AdapterPlaylistStyle2 recently = new AdapterPlaylistStyle2();
        recently.setData(playlists, this);
        homeFavourites.setAdapter(recently);
    }
    public void initBanner(ArrayList<Playlist> playlists){

        ViewPager viewPager;
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        BannerAdapter viewPagerAdapter = new BannerAdapter(view.getContext(), playlists);
        viewPagerAdapter.setEvent(this);
        viewPager.setAdapter(viewPagerAdapter);
    }

    class FragmentPlaylist{
        private int idRec;
        private int idTitle;
        private int style;

        public FragmentPlaylist(int idRec, int idTitle, int style) {
            this.idRec = idRec;
            this.idTitle = idTitle;
            this.style= style;
        }

        public int getStyle() {
            return style;
        }

        public void setStyle(int style) {
            this.style = style;
        }

        public int getIdRec() {
            return idRec;
        }

        public void setIdRec(int idRec) {
            this.idRec = idRec;
        }

        public int getIdTitle() {
            return idTitle;
        }

        public void setIdTitle(int idTitle) {
            this.idTitle = idTitle;
        }
    }
    @Override
    public void openPlaylist(Playlist playlist) {
        System.out.println(playlist.getId());
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        LoadingFragment loadingFragment = new LoadingFragment();
        fragmentManager.beginTransaction().replace(R.id.main_home_fragment, loadingFragment).addToBackStack(null).commit();
        SongAPI.getSongsByIdPlaylist(playlist.getId(), new CallbackAPI() {
            @Override
            public <T> void callback(T data) {
                ArrayList<Subject> subjects  = (ArrayList<Subject>) data;
                playlist.setSubjects(subjects);
                System.out.println(playlist.getSubjects().size());
                PlaylistFragment playlistFragment = new PlaylistFragment();
                Bundle args = new Bundle();
                args.putSerializable("playlist", playlist);
                playlistFragment.setArguments(args);
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_home_fragment, playlistFragment).addToBackStack(null).commit();

            }
        });
    }
}