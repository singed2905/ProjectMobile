package com.example.project.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.example.project.R;
import com.example.project.api.SongAPI;
import com.example.project.event.InitHomeContent;
import com.example.project.event.OnClickListener;
import com.example.project.model.Subject;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements OnClickListener {
    private SearchView mSearchView ;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SearchFragment searchFragment;
    private  String keyword;
    private RecyclerView homeFavourites;
    private View view;

    private OnClickListener o = this;
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    private void init(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        ContentHomeFragment contentHomeFragment = new ContentHomeFragment();
        Bundle args = new Bundle();
        SongAPI.getHome(new InitHomeContent() {
            @Override
            public <T> void init(T rs) {
                ArrayList<String> array = (ArrayList<String> ) rs;
                System.out.println(array.size() + "Sdsdsd");
                args.putSerializable("banner", array);
                contentHomeFragment.setArguments(args);
                fragmentManager.beginTransaction().replace(R.id.main_home_fragment, contentHomeFragment).addToBackStack(null).commit();

            }
        });
        }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        search();
        init();

        return view;

    }

    private void search(){
        SearchView searchView = view.findViewById(R.id.search_home);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                keyword = query;

                SongAPI.setO(o);
                View view = getActivity().getCurrentFocus();

                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                LoadingFragment loadingFragment = new LoadingFragment();
                fragmentManager.beginTransaction().replace(R.id.main_home_fragment, loadingFragment).addToBackStack(null).commit();
                try {
                    ArrayList<Subject> subjects = SongAPI.searchSongByName(keyword);
                    System.out.println(subjects.size() + "sdsdsdsdsd");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void clickItem( ) {
        }
    @Override
    public void setData(ArrayList<Subject> arr) {
        searchFragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putSerializable("subjects", arr);
        searchFragment.setArguments(args);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_home_fragment, searchFragment).addToBackStack(null).commit();
    }

    @Override
    public void playSong(Subject id) {

    }
}