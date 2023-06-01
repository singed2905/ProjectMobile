package com.example.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.model.Subject;

import java.util.List;

public class AdapterProfile extends RecyclerView.Adapter<AdapterProfile.ProfileViewHolder>  {
    private  List<Subject> dataList;


    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sound, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        Subject song = dataList.get(position);
        holder.tv_songname.setText(song.getName());
        holder.tv_artist.setText(song.getArtist());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ProfileViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_songname, tv_artist;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_songname = itemView.findViewById(R.id.name_item_sound);
            tv_artist = itemView.findViewById(R.id.astist_item_sound);
        }
    }
    public AdapterProfile(List<Subject> dataList) {
        this.dataList = dataList;
    }
    public void updateData(List<Subject> newDataList) {
        dataList = newDataList;
        notifyDataSetChanged();

    }


}
