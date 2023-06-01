package com.example.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.event.EventOpenPlaylist;
import com.example.project.event.OnClickListener;
import com.example.project.model.Playlist;
import com.example.project.model.Subject;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterPlaylist extends RecyclerView.Adapter<AdapterPlaylist.ViewHolder> {
    private List<Playlist> mData;

    private EventOpenPlaylist event;

    public void setData(List<Playlist> data, EventOpenPlaylist o) {
        this.event=o;
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate layout and create ViewHolder
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_playlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Playlist tmp=mData.get(position);
        holder.nameMusic.setText(tmp.getName());
        Picasso.get().load(tmp.getImg()).into(holder.avatar);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                event.openPlaylist(tmp);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameMusic;
        public TextView description;
        public ImageView avatar;
        public LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            nameMusic = itemView.findViewById(R.id.namemusic);
            avatar = itemView.findViewById(R.id.imgAstist);
            linearLayout = itemView.findViewById(R.id.container_favourites);
        }
    }
}