package com.anioncode.drzewostan.Framgent;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.anioncode.drzewostan.Adapters.UniversalAdapter;
import com.anioncode.drzewostan.Model.Trees;
import com.anioncode.drzewostan.R;
import com.anioncode.drzewostan.SQLite.DatabaseUniversal;

import java.util.ArrayList;

public class Fragment_Oak_without extends Fragment {

    View view;

    ListView li;
    ArrayList<Trees> arrayList;
    UniversalAdapter myAdapter;
    DatabaseUniversal databaseHelper;

    public Fragment_Oak_without() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.universal_layout, container, false);
        li = (ListView) view.findViewById(R.id.listView);

        databaseHelper = new DatabaseUniversal(view.getContext(), "HORNBEAM");
        arrayList = new ArrayList<>();
        arrayList = databaseHelper.getAllData();
        myAdapter = new UniversalAdapter(view.getContext(), arrayList, "HORNBEAM");
        li.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        return view;
    }


}
