package com.anioncode.drzewostan.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anioncode.drzewostan.SQLite.DatabaseAlder;
import com.anioncode.drzewostan.SQLite.DatabaseTablenameHelper;
import com.anioncode.drzewostan.Model.Trees;
import com.anioncode.drzewostan.R;


import java.util.ArrayList;

public class AlderMyAdapter extends BaseAdapter {

    Context context;
    ArrayList<Trees> arrayList;
    DatabaseAlder databaseHelper;

    // DatabaseHelper databaseHelper;

    public AlderMyAdapter(Context context, ArrayList<Trees> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater= (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view =inflater.inflate(R.layout.listrow,null);

        //  TextView text_id=(TextView)view.findViewById(R.id.zakres);
        TextView text_srednica = (TextView) view.findViewById(R.id.zakres);
        final  TextView ilosc = (TextView) view.findViewById(R.id.ilosc);
        Button button_plus = (Button) view.findViewById(R.id.button_plus);
        final Button butto_minus = (Button) view.findViewById(R.id.button_minus);
        final Spinner spinner =(Spinner) view.findViewById(R.id.Spinner);



        final Trees trees= arrayList.get(i);
        final int x=i;

///SPINER BEZ SZUKANIA POZYCJI A WARTOsci

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(view.getContext(),R.array.numbers,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        String compareValue = String.valueOf(trees.getWysokosc());
        spinner.setAdapter(adapter);

        if (compareValue != null) {
            int spinnerPosition = adapter.getPosition(compareValue);
            spinner.setSelection(spinnerPosition);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String tes= String.valueOf(spinner.getSelectedItemId());
                if(tes.equals("0")){

                }else {
                    //  Toast.makeText(context,"SZtos"+spinner.getSelectedItemId(),Toast.LENGTH_SHORT).show();
                    trees.setWysokosc(Math.toIntExact(spinner.getSelectedItemId()));
                    databaseHelper.updateData("WYSOKOSC",Math.toIntExact(getItemId(x)+1), Math.toIntExact(spinner.getSelectedItemId()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
                Toast.makeText(context,"Upss",Toast.LENGTH_SHORT).show();
            }

        });
        ////////////////////////////////////////////

        ilosc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///DIALOG

                AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());

                LayoutInflater inflater2= (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                View view1=inflater2.inflate(R.layout.dialog_trees,null);

                ///Tworzenie pola tekstowego
                final TextView iloscWa=view1.findViewById(R.id.iloscWA);
                final TextView iloscWb=view1.findViewById(R.id.iloscWB);
                final TextView iloscWc=view1.findViewById(R.id.iloscWC);

                final TextView iloscA=view1.findViewById(R.id.iloscA);
                final TextView iloscB=view1.findViewById(R.id.iloscB);
                final TextView iloscC_S=view1.findViewById(R.id.iloscC_S);

                ///Wypełnianie pola tekstem
                iloscWa.setText(String.valueOf(trees.getKlasa_1()));
                iloscWb.setText(String.valueOf(trees.getKlasa_2()));
                iloscWc.setText(String.valueOf(trees.getKlasa_3()));

                iloscA.setText(String.valueOf(trees.getKlasa_a()));
                iloscB.setText(String.valueOf(trees.getKlasa_b()));
                iloscC_S.setText(String.valueOf(trees.getKlasa_c()));


                ///DLA 1 KLASY
                Button button_minusWA=(Button)view1.findViewById(R.id.button_minusWA);
                Button button_plusWA=(Button)view1.findViewById(R.id.button_plusWA);

                button_minusWA.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
                        int liczba=Integer.parseInt(String.valueOf(iloscWa.getText()))-1;
                        trees.setKlasa_1(liczba);
                        try {

                            databaseHelper.updateData("KLASA_1",Math.toIntExact(getItemId(x)+1), liczba);

                        }
                        catch (Exception e){

                        }
                        ilosc.setText(String.valueOf(trees.getKlasa_1()+trees.getKlasa_2()+trees.getKlasa_3()+trees.getKlasa_a()+trees.getKlasa_b()+trees.getKlasa_c()));
                        iloscWa.setText(String.valueOf(liczba));
                    }
                });

                button_plusWA.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
                        int liczba=Integer.parseInt(String.valueOf(iloscWa.getText()))+1;
                        trees.setKlasa_1(liczba);
                        try {

                            databaseHelper.updateData("KLASA_1",Math.toIntExact(getItemId(x)+1), liczba);

                        }
                        catch (Exception e){

                        }
                        ilosc.setText(String.valueOf(trees.getKlasa_1()+trees.getKlasa_2()+trees.getKlasa_3()+trees.getKlasa_a()+trees.getKlasa_b()+trees.getKlasa_c()));
                        iloscWa.setText(String.valueOf(liczba));
                    }
                });

                ///DLA 2 KLASY

                Button button_minusWB=(Button)view1.findViewById(R.id.button_minusWB);
                Button button_plusWB=(Button)view1.findViewById(R.id.button_plus_WB);

                button_minusWB.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
                        int liczba=Integer.parseInt(String.valueOf(iloscWb.getText()))-1;
                        trees.setKlasa_2(liczba);
                        try {

                            databaseHelper.updateData("KLASA_2",Math.toIntExact(getItemId(x)+1), liczba);

                        }
                        catch (Exception e){

                        }
                        ilosc.setText(String.valueOf(trees.getKlasa_1()+trees.getKlasa_2()+trees.getKlasa_3()+trees.getKlasa_a()+trees.getKlasa_b()+trees.getKlasa_c()));
                        iloscWb.setText(String.valueOf(liczba));
                    }
                });

                button_plusWB.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
                        int liczba=Integer.parseInt(String.valueOf(iloscWb.getText()))+1;
                        trees.setKlasa_2(liczba);
                        try {

                            databaseHelper.updateData("KLASA_2",Math.toIntExact(getItemId(x)+1), liczba);

                        }
                        catch (Exception e){

                        }
                        ilosc.setText(String.valueOf(trees.getKlasa_1()+trees.getKlasa_2()+trees.getKlasa_3()+trees.getKlasa_a()+trees.getKlasa_b()+trees.getKlasa_c()));
                        iloscWb.setText(String.valueOf(liczba));
                    }
                });

                ///DLA 3 KLASY

                Button button_minusWC=(Button)view1.findViewById(R.id.button_minusWC);
                Button button_plus_WC=(Button)view1.findViewById(R.id.button_plus_WC);

                button_minusWC.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
                        int liczba=Integer.parseInt(String.valueOf(iloscWc.getText()))-1;
                        trees.setKlasa_3(liczba);
                        try {

                            databaseHelper.updateData("KLASA_3",Math.toIntExact(getItemId(x)+1), liczba);

                        }
                        catch (Exception e){

                        }
                        ilosc.setText(String.valueOf(trees.getKlasa_1()+trees.getKlasa_2()+trees.getKlasa_3()+trees.getKlasa_a()+trees.getKlasa_b()+trees.getKlasa_c()));
                        iloscWc.setText(String.valueOf(liczba));
                    }
                });

                button_plus_WC.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
                        int liczba=Integer.parseInt(String.valueOf(iloscWc.getText()))+1;
                        trees.setKlasa_3(liczba);
                        try {

                            databaseHelper.updateData("KLASA_3",Math.toIntExact(getItemId(x)+1), liczba);

                        }
                        catch (Exception e){

                        }
                        ilosc.setText(String.valueOf(trees.getKlasa_1()+trees.getKlasa_2()+trees.getKlasa_3()+trees.getKlasa_a()+trees.getKlasa_b()+trees.getKlasa_c()));
                        iloscWc.setText(String.valueOf(liczba));
                    }
                });
                ///DLA A KLASY

                Button button_minusA=(Button)view1.findViewById(R.id.button_minusA);
                Button button_plus_A=(Button)view1.findViewById(R.id.button_plus_A);

                button_minusA.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
                        int liczba=Integer.parseInt(String.valueOf(iloscA.getText()))-1;
                        trees.setKlasa_a(liczba);
                        try {

                            databaseHelper.updateData("KLASA_A",Math.toIntExact(getItemId(x)+1), liczba);

                        }
                        catch (Exception e){

                        }
                        ilosc.setText(String.valueOf(trees.getKlasa_1()+trees.getKlasa_2()+trees.getKlasa_3()+trees.getKlasa_a()+trees.getKlasa_b()+trees.getKlasa_c()));
                        iloscA.setText(String.valueOf(liczba));
                    }
                });

                button_plus_A.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
                        int liczba=Integer.parseInt(String.valueOf(iloscA.getText()))+1;
                        trees.setKlasa_a(liczba);
                        try {

                            databaseHelper.updateData("KLASA_A",Math.toIntExact(getItemId(x)+1), liczba);

                        }
                        catch (Exception e){

                        }
                        ilosc.setText(String.valueOf(trees.getKlasa_1()+trees.getKlasa_2()+trees.getKlasa_3()+trees.getKlasa_a()+trees.getKlasa_b()+trees.getKlasa_c()));
                        iloscA.setText(String.valueOf(liczba));
                    }
                });

                ///DLA B KLASY
                Button button_minusB=(Button)view1.findViewById(R.id.button_minusB);
                Button button_plus_B=(Button)view1.findViewById(R.id.button_plus_B);

                button_minusB.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
                        int liczba=Integer.parseInt(String.valueOf(iloscB.getText()))-1;
                        trees.setKlasa_a(liczba);
                        try {

                            databaseHelper.updateData("KLASA_B",Math.toIntExact(getItemId(x)+1), liczba);

                        }
                        catch (Exception e){

                        }
                        ilosc.setText(String.valueOf(trees.getKlasa_1()+trees.getKlasa_2()+trees.getKlasa_3()+trees.getKlasa_a()+trees.getKlasa_b()+trees.getKlasa_c()));
                        iloscB.setText(String.valueOf(liczba));
                    }
                });

                button_plus_B.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
                        int liczba=Integer.parseInt(String.valueOf(iloscB.getText()))+1;
                        trees.setKlasa_b(liczba);
                        try {

                            databaseHelper.updateData("KLASA_B",Math.toIntExact(getItemId(x)+1), liczba);

                        }
                        catch (Exception e){

                        }
                        ilosc.setText(String.valueOf(trees.getKlasa_1()+trees.getKlasa_2()+trees.getKlasa_3()+trees.getKlasa_a()+trees.getKlasa_b()+trees.getKlasa_c()));
                        iloscB.setText(String.valueOf(liczba));
                    }
                });

                ///DLA C KLASY

                Button button_minusC_S=(Button)view1.findViewById(R.id.button_minusC_S);
                Button button_plus_C_S=(Button)view1.findViewById(R.id.button_plus_C_S);

                button_minusC_S.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
                        int liczba=Integer.parseInt(String.valueOf(iloscC_S.getText()))-1;
                        trees.setKlasa_c(liczba);
                        try {

                            databaseHelper.updateData("KLASA_C",Math.toIntExact(getItemId(x)+1), liczba);

                        }
                        catch (Exception e){

                        }
                        ilosc.setText(String.valueOf(trees.getKlasa_1()+trees.getKlasa_2()+trees.getKlasa_3()+trees.getKlasa_a()+trees.getKlasa_b()+trees.getKlasa_c()));
                        iloscC_S.setText(String.valueOf(liczba));
                    }
                });

                button_plus_C_S.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
                        int liczba=Integer.parseInt(String.valueOf(iloscC_S.getText()))+1;
                        trees.setKlasa_c(liczba);
                        try {

                            databaseHelper.updateData("KLASA_C",Math.toIntExact(getItemId(x)+1), liczba);

                        }
                        catch (Exception e){

                        }
                        ilosc.setText(String.valueOf(trees.getKlasa_1()+trees.getKlasa_2()+trees.getKlasa_3()+trees.getKlasa_a()+trees.getKlasa_b()+trees.getKlasa_c()));
                        iloscC_S.setText(String.valueOf(liczba));
                    }
                });

                builder1.setView(view1);


                builder1.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();


            }
        });

        //Licznik do iklementacji



        text_srednica.setText(String.valueOf(trees.getSrednica()));

        //ilosc.setText(String.valueOf(trees.getKlasa_3()));
        ilosc.setText(String.valueOf(trees.getKlasa_1()+trees.getKlasa_2()+trees.getKlasa_3()+trees.getKlasa_a()+trees.getKlasa_b()+trees.getKlasa_c()));

        databaseHelper = new DatabaseAlder(view.getContext());


        button_plus.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                try {
                    //Do Updateowania
                    databaseHelper.updateData("KLASA_3",Math.toIntExact(getItemId(x)+1), trees.getKlasa_3()+1);
                    trees.setKlasa_3(trees.getKlasa_3()+1);
                }
                catch (Exception e){
                }
                ilosc.setText(String.valueOf(suma_wszystkich(Math.toIntExact(getItemId(x) + 1))));


            }
        });

        butto_minus.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                // int liczbax=Integer.parseInt(String.valueOf(ilosc.getText()))-1;

                try {
                    //Do Updateowania
                    databaseHelper.updateData("KLASA_3",Math.toIntExact(getItemId(x)+1), trees.getKlasa_3()-1);
                    trees.setKlasa_3(trees.getKlasa_3()-1);
                }
                catch (Exception e){;
                }

                //  ilosc.setText(String.valueOf(liczbax));
                ilosc.setText(String.valueOf(suma_wszystkich(Math.toIntExact(getItemId(x) + 1))));


            }
        });

        return view;
    }
    public int suma_wszystkich(int iso){
        int suma=0;
        Cursor data = databaseHelper.LasDane(iso);
        while(data.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
            suma=data.getInt(2)+data.getInt(3)+data.getInt(4)+data.getInt(5)+data.getInt(6)+data.getInt(7);

            //listData.add(data.getInt(0)+". L: "+data.getDouble(1)+", P: "+data.getDouble(2)+" "+data.getString(3)+" "+data.getString(4));
            //System.out.println(data.getInt(4)+" ......................................................................................................................................TU");

        }

        return suma;
    }

}
