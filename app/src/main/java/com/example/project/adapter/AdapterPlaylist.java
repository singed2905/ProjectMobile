package com.example.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.event.OnClickListener;
import com.example.project.model.Subject;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterPlaylist extends RecyclerView.Adapter<AdapterPlaylist.ViewHolder> {
    private List<Subject> mData;
    private OnClickListener onClick;


    public void setData(List<Subject> data, OnClickListener o) {
        this.onClick=o;
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
        Subject tmp=mData.get(position);
        holder.nameMusic.setText(tmp.getName());
        holder.nameAstist.setText(tmp.getArtist());
        System.out.println(holder.linearLayout);
        Picasso.get().load(tmp.getSrc()).into(holder.avatar);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println();
                onClick.clickItem();
            }
        });
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameMusic;
        public TextView nameAstist;
        public ImageView avatar;
        public LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            nameMusic = itemView.findViewById(R.id.namemusic);
            nameAstist = itemView.findViewById(R.id.nameAstist);
            avatar = itemView.findViewById(R.id.imgAstist);
            linearLayout = itemView.findViewById(R.id.container_favourites);
        }
    }
}