package com.anioncode.drzewostan.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.anioncode.drzewostan.R;

import java.io.File;
import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.MyViewHolder> {

    private List<String> filenamelist;
    private PressPDF pressPDF;
    private Context mcontext;

    public  interface PressPDF{
        void longClick(String filename);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public AppCompatImageView docs;
        public ImageButton sending;

        public MyViewHolder(View view) {
            super(view);
            sending = (ImageButton) view.findViewById(R.id.sending);
            title = (TextView) view.findViewById(R.id.filename);
            docs = (AppCompatImageView) view.findViewById(R.id.docs);

        }
    }


    public FolderAdapter(Context mcontext,List<String> filenamelist,PressPDF pressPDF) {
        this.filenamelist = filenamelist;
        this.pressPDF = pressPDF;
        this.mcontext = mcontext;
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

                   // File file = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/Lasy/"+filename)));
                    File file = new File(Environment.getExternalStorageDirectory() + "/Lasy/"+filename);
                    boolean deleted = file.delete();
                    filenamelist.remove(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();

                }
            });
            builder.setNegativeButton("ANULUJ", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    dialog.cancel();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
            return false;

        });
        holder.sending.setOnClickListener(v->{
            Intent intentx = new Intent(Intent.ACTION_SEND);
            intentx.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + Environment.getExternalStorageDirectory() + "/Lasy/" + filename));
            intentx.setType("aplication/pdf");
            intentx.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            mcontext.startActivity(Intent.createChooser(intentx, "Share PDF"));
        });
    }

    @Override
    public int getItemCount() {
        return filenamelist.size();
    }
}