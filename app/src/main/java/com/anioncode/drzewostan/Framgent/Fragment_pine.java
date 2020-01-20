package com.anioncode.drzewostan.Framgent;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.anioncode.drzewostan.Adapters.MyAdapter;
import com.anioncode.drzewostan.Model.Trees;
import com.anioncode.drzewostan.R;
import com.anioncode.drzewostan.SQLite.DatabaseHelper;

import java.util.ArrayList;

public class Fragment_pine extends Fragment {

    View view;

//    private ListView lv;
//    private CustomeAdapter customeAdapter;
//    public ArrayList<EditTrees> editModelArrayList;

    ListView li;
    ArrayList<Trees> arrayList;
    MyAdapter myAdapter;
    DatabaseHelper databaseHelper;

    public Fragment_pine() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.pine_fragment,container,false);

        li=(ListView)view.findViewById(R.id.listView);

        databaseHelper = new DatabaseHelper(view.getContext());


        arrayList =new ArrayList<>();
        arrayList=databaseHelper.getAllData();
        myAdapter=new MyAdapter(view.getContext(),arrayList);
        li.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        return  view;
    }


}
