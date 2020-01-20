package com.anioncode.drzewostan.Framgent;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.anioncode.drzewostan.Adapters.OakMyAdapter;
import com.anioncode.drzewostan.Model.Trees;
import com.anioncode.drzewostan.R;
import com.anioncode.drzewostan.SQLite.DatabaseTablenameHelper;

import java.util.ArrayList;


public class Fragment_oak extends Fragment {
    View view;

    ListView li;
    ArrayList<Trees> arrayList;
    OakMyAdapter myAdapter;
    DatabaseTablenameHelper databaseHelper;



    public Fragment_oak() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.oak_fragment,container,false);

        li=(ListView)view.findViewById(R.id.listView);

        databaseHelper = new DatabaseTablenameHelper(view.getContext());

        arrayList =new ArrayList<>();
        arrayList=databaseHelper.getAllData();
        myAdapter=new OakMyAdapter(view.getContext(),arrayList);
        li.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        return  view;
    }

//    private void populateList(){
//
//        double tmp;
//        for(int i = 7; i < 87; i+=2){
//            tmp=i+1.9;
//            databaseHelper.insertData(i+"-"+tmp,0,0,0,0,0,0,0);
//        }
//    }
}
