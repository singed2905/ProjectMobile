package com.example.project.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.project.R;
import com.example.project.event.EventOpenPlaylist;
import com.example.project.model.Playlist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<String> images ;
    private ArrayList<Playlist> playlists ;

    private EventOpenPlaylist event;
    public BannerAdapter(Context context, ArrayList<Playlist> playlists) {
        this.context = context;
        this.playlists = playlists;
        ArrayList<String> images = new ArrayList<>();
        for (int j = 0; j <  playlists.size() ; j++) {
            Playlist playlist = playlists.get(j);
            images.add(playlist.getImg());

        }
        this.images = images;
    }
    public void setEvent(EventOpenPlaylist event){
        this.event = event;
    }
    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_banner, null);
        LinearLayout linearLayout = view.findViewById(R.id.item_layout_banner);


        ImageView imageView = (ImageView) view.findViewById(R.id.item_banner);

        Picasso.get().load(images.get(position)).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                event.openPlaylist(playlists.get(position));
            }
        });
        ViewPager vp = (ViewPager) container;
        vp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(playlists.get(position).getName());

            }
        });
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}