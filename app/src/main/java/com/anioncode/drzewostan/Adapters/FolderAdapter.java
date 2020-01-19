package com.anioncode.drzewostan.Adapters;

import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anioncode.drzewostan.R;

import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.MyViewHolder> {

    private List<String> filenamelist;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.filename);

        }
    }


    public FolderAdapter(List<String> filenamelist) {
        this.filenamelist = filenamelist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_file, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String filename = filenamelist.get(position);

        holder.title.setText(filename);
        holder.title.setOnClickListener((View c)->{
            if(holder.title.isSelected()){
                holder.title.setSelected(false);
            }else {
                holder.title.setSelected(true);
            }

        });
    }

    @Override
    public int getItemCount() {
        return filenamelist.size();
    }
}