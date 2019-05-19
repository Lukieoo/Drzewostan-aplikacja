package com.anioncode.drzewostan.Framgent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.anioncode.drzewostan.Adapters.AlderMyAdapter;
import com.anioncode.drzewostan.Adapters.MyAdapter;
import com.anioncode.drzewostan.Model.Trees;
import com.anioncode.drzewostan.R;
import com.anioncode.drzewostan.SQLite.DatabaseAlder;
import com.anioncode.drzewostan.SQLite.DatabaseTablenameHelper;

import java.util.ArrayList;

public class Fragment_alder extends Fragment {
    View view;

    ListView li;
    ArrayList<Trees> arrayList;
    AlderMyAdapter myAdapter;
    DatabaseAlder databaseHelper;


    public Fragment_alder() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.alder_fragment,container,false);
        li=(ListView)view.findViewById(R.id.listView);

        databaseHelper = new DatabaseAlder(view.getContext());

        arrayList =new ArrayList<>();
        arrayList=databaseHelper.getAllData();
        myAdapter=new AlderMyAdapter(view.getContext(),arrayList);
        li.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
//        try{
//            populateList();
//        }catch (Exception e){
//
//        }
        return  view;
    }

    private void populateList(){

        double tmp;
        for(int i = 7; i < 87; i+=2){
            tmp=i+1.9;
            databaseHelper.insertData(i+"-"+tmp,0,0,0,0,0,0,0);
        }
    }
}