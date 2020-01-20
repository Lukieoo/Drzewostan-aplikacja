package com.anioncode.drzewostan.Adapters;

import android.content.DialogInterface;
import android.graphics.Movie;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anioncode.drzewostan.R;

import java.io.File;
import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.MyViewHolder> {

    private List<String> filenamelist;
    private PressPDF pressPDF;

    public  interface PressPDF{
        void longClick(String filename);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public AppCompatImageView docs;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.filename);
            docs = (AppCompatImageView) view.findViewById(R.id.docs);

        }
    }


    public FolderAdapter(List<String> filenamelist,PressPDF pressPDF) {
        this.filenamelist = filenamelist;
        this.pressPDF = pressPDF;
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
        holder.docs.setOnClickListener((View c)->{
            pressPDF.longClick(filename);
        });
        holder.title.setOnLongClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Usunąć plik PDF ?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    File file = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/Lasy/"+filename)));
                    boolean deleted = file.delete();
                    filenamelist.remove(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();

                }
            });
            builder.setNegativeButton("ANULUJ", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
            return false;

        });
    }

    @Override
    public int getItemCount() {
        return filenamelist.size();
    }
}