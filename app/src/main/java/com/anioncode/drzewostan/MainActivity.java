package com.anioncode.drzewostan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.anioncode.drzewostan.Framgent.Fragment_alder;
import com.anioncode.drzewostan.Framgent.Fragment_oak;
import com.anioncode.drzewostan.Framgent.Fragment_pine;
import com.anioncode.drzewostan.SQLite.DatabaseAlder;
import com.anioncode.drzewostan.SQLite.DatabaseHelper;
import com.anioncode.drzewostan.SQLite.DatabaseTablenameHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private Button reset;
    private Button save;
    private Button send;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        MobileAds.initialize(this, "ca-app-pub-3788232558823244~9887124070");



        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        tabLayout=(TabLayout) findViewById(R.id.tablayout_id);
        appBarLayout=(AppBarLayout) findViewById(R.id.appbarid);
        viewPager=(ViewPager) findViewById(R.id.viewpager_id);
        Viewpageradapter viewpageradapter= new Viewpageradapter(getSupportFragmentManager());
        ///Dodajesz Fragmenty
        viewpageradapter.Addfragment(new Fragment_pine(),"Sosna");
        viewpageradapter.Addfragment(new Fragment_oak(),"Dąb");
        viewpageradapter.Addfragment(new Fragment_alder(),"Olszyna");
        ///Ukazanie adaptera
        viewPager.setAdapter(viewpageradapter);
        tabLayout.setupWithViewPager(viewPager);

        reset =(Button) findViewById(R.id.reset);
        save =(Button) findViewById(R.id.save);
        send =(Button) findViewById(R.id.send);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DatabaseAlder databaseAlder= new DatabaseAlder(view.getContext());
                final DatabaseHelper databaseHelper= new DatabaseHelper(view.getContext());
                final DatabaseTablenameHelper databaseTablenameHelper= new DatabaseTablenameHelper(view.getContext());
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());

                    builder1.setMessage("Czy chcesz zresetować bazę ?");
                    builder1.setTitle("Reset");

                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    for(int i=1;i<=39;i++) {
                                        databaseAlder.updateDatado0(i, 0, 0, 0, 0, 0, 0, 0);
                                        databaseHelper.updateDatado0(i, 0, 0, 0, 0, 0, 0, 0);
                                        databaseTablenameHelper.updateDatado0(i, 0, 0, 0, 0, 0, 0, 0);
                                    }
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                    dialog.cancel();
                                }
                            });
                    builder1.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


                    AlertDialog alert11 = builder1.create();
                    alert11.show();





            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  final DatabaseAlder databaseAlder= new DatabaseAlder(view.getContext());
                final DatabaseHelper databaseHelper = new DatabaseHelper(view.getContext());
                 final DatabaseTablenameHelper databaseTablenameHelper= new DatabaseTablenameHelper(view.getContext());

                //String text ="1";
                Date currentTime = Calendar.getInstance().getTime();

                String FILE_NAME = "Zapis"+currentTime.getDate()+currentTime.getTime()+currentTime.getDay()+".txt";
                FileOutputStream fos = null;
                try {
                    fos = openFileOutput(FILE_NAME, MODE_APPEND);
                    Cursor data = databaseHelper.AllData();
                    fos.write("Sosna  \n".getBytes());
                    fos.write(" Średnica  klasa_1  klasa_2  klasa_3  klasa_a klasa_b  klasa_c  wysokość \n".getBytes());

                    while (data.moveToNext()) {
                        //get the value from the database in column 1
                        //then add it to the ArrayList

                        String srednica = data.getString(1);
                        int klasa_1 = data.getInt(2);
                        int klasa_2 = data.getInt(3);
                        int klasa_3 = data.getInt(4);
                        int klasa_a = data.getInt(5);
                        int klasa_b = data.getInt(6);
                        int klasa_c = data.getInt(7);
                        int wysokosc = data.getInt(8);
                        String wyraz = srednica + " " + String.valueOf(klasa_1) + " " + String.valueOf(klasa_2) + " " + String.valueOf(klasa_3) + " " + String.valueOf(klasa_a) + " " + String.valueOf(klasa_b) + " " + String.valueOf(klasa_c) + " " + String.valueOf(wysokosc) + "\n";

                        fos.write(wyraz.getBytes());
                    }

                    fos = openFileOutput(FILE_NAME, MODE_APPEND);
                    Cursor datax = databaseTablenameHelper.AllData();
                    fos.write("Dąb  \n".getBytes());
                    fos.write(" Średnica  klasa_1  klasa_2  klasa_3  klasa_a klasa_b  klasa_c  wysokość \n".getBytes());
                    while (data.moveToNext()) {
                        //get the value from the database in column 1
                        //then add it to the ArrayList

                        String srednica = data.getString(1);
                        int klasa_1 = datax.getInt(2);
                        int klasa_2 = datax.getInt(3);
                        int klasa_3 = datax.getInt(4);
                        int klasa_a = datax.getInt(5);
                        int klasa_b = datax.getInt(6);
                        int klasa_c = datax.getInt(7);
                        int wysokosc = datax.getInt(8);
                        String wyraz = srednica + " " + String.valueOf(klasa_1) + " " + String.valueOf(klasa_2) + " " + String.valueOf(klasa_3) + " " + String.valueOf(klasa_a) + " " + String.valueOf(klasa_b) + " " + String.valueOf(klasa_c) + " " + String.valueOf(wysokosc) + "\n";

                        fos.write(wyraz.getBytes());
                    }
                    Cursor dataxx = databaseAlder.AllData();
                    fos.write("Olszyna  \n".getBytes());
                    fos.write(" Średnica  klasa_1  klasa_2  klasa_3  klasa_a klasa_b  klasa_c  wysokość \n".getBytes());
                    while (data.moveToNext()) {
                        //get the value from the database in column 1
                        //then add it to the ArrayList

                        String srednica = data.getString(1);
                        int klasa_1 = dataxx.getInt(2);
                        int klasa_2 = dataxx.getInt(3);
                        int klasa_3 = dataxx.getInt(4);
                        int klasa_a = dataxx.getInt(5);
                        int klasa_b = dataxx.getInt(6);
                        int klasa_c = dataxx.getInt(7);
                        int wysokosc = dataxx.getInt(8);
                        String wyraz = srednica + " " + String.valueOf(klasa_1) + " " + String.valueOf(klasa_2) + " " + String.valueOf(klasa_3) + " " + String.valueOf(klasa_a) + " " + String.valueOf(klasa_b) + " " + String.valueOf(klasa_c) + " " + String.valueOf(wysokosc) + "\n";

                        fos.write(wyraz.getBytes());
                    }
                    Toast.makeText(view.getContext(), "Zapisano w " + getFilesDir() + "/" + FILE_NAME + "\n Plik zainportuj do exela",
                            Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
//                FileInputStream fis = null;
//
//                try {
//                    fis = openFileInput(FILE_NAME);
//                    InputStreamReader isr = new InputStreamReader(fis);
//                    BufferedReader br = new BufferedReader(isr);
//                    StringBuilder sb = new StringBuilder();
//                    String text;
//
//                    while ((text = br.readLine()) != null) {
//                        sb.append(text).append("\n");
//                    }
//
//                 //   Toast.makeText(view.getContext(), sb.toString(),Toast.LENGTH_LONG).show();
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (fis != null) {
//                        try {
//                            fis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }

//                Intent intent = new Intent(Intent.ACTION_VIEW);
//
//                startActivityForResult(intent,10);
//                startActivity(intent);

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseAlder databaseAlder= new DatabaseAlder(view.getContext());
                final DatabaseHelper databaseHelper = new DatabaseHelper(view.getContext());
                final DatabaseTablenameHelper databaseTablenameHelper= new DatabaseTablenameHelper(view.getContext());

                Cursor data = databaseHelper.AllData();
                String sendtext="";
                sendtext+="Sosna  \n";
                sendtext+=" Średnica  klasa_1  klasa_2  klasa_3  klasa_a klasa_b  klasa_c  wysokość \n";

                while (data.moveToNext()) {
                    //get the value from the database in column 1
                    //then add it to the ArrayList

                    String srednica = data.getString(1);
                    int klasa_1 = data.getInt(2);
                    int klasa_2 = data.getInt(3);
                    int klasa_3 = data.getInt(4);
                    int klasa_a = data.getInt(5);
                    int klasa_b = data.getInt(6);
                    int klasa_c = data.getInt(7);
                    int wysokosc = data.getInt(8);
                     sendtext+= srednica + " " + String.valueOf(klasa_1) + " " + String.valueOf(klasa_2) + " " + String.valueOf(klasa_3) + " " + String.valueOf(klasa_a) + " " + String.valueOf(klasa_b) + " " + String.valueOf(klasa_c) + " " + String.valueOf(wysokosc) + "\n";


                }


                Cursor datax = databaseTablenameHelper.AllData();
                sendtext+="Dąb  \n";
                sendtext+=" Średnica  klasa_1  klasa_2  klasa_3  klasa_a klasa_b  klasa_c  wysokość \n";
                while (datax.moveToNext()) {
                    //get the value from the database in column 1
                    //then add it to the ArrayList

                    String srednica = datax.getString(1);
                    int klasa_1 = datax.getInt(2);
                    int klasa_2 = datax.getInt(3);
                    int klasa_3 = datax.getInt(4);
                    int klasa_a = datax.getInt(5);
                    int klasa_b = datax.getInt(6);
                    int klasa_c = datax.getInt(7);
                    int wysokosc = datax.getInt(8);
                    sendtext+= srednica + " " + String.valueOf(klasa_1) + " " + String.valueOf(klasa_2) + " " + String.valueOf(klasa_3) + " " + String.valueOf(klasa_a) + " " + String.valueOf(klasa_b) + " " + String.valueOf(klasa_c) + " " + String.valueOf(wysokosc) + "\n";


                }
                Cursor dataxx = databaseAlder.AllData();
                sendtext+="Olszyna  \n";
                sendtext+=" Średnica  klasa_1  klasa_2  klasa_3  klasa_a klasa_b  klasa_c  wysokość \n";
                while (dataxx.moveToNext()) {
                    //get the value from the database in column 1
                    //then add it to the ArrayList

                    String srednica = dataxx.getString(1);
                    int klasa_1 = dataxx.getInt(2);
                    int klasa_2 = dataxx.getInt(3);
                    int klasa_3 = dataxx.getInt(4);
                    int klasa_a = dataxx.getInt(5);
                    int klasa_b = dataxx.getInt(6);
                    int klasa_c = dataxx.getInt(7);
                    int wysokosc = dataxx.getInt(8);
                    sendtext+= srednica + " " + String.valueOf(klasa_1) + " " + String.valueOf(klasa_2) + " " + String.valueOf(klasa_3) + " " + String.valueOf(klasa_a) + " " + String.valueOf(klasa_b) + " " + String.valueOf(klasa_c) + " " + String.valueOf(wysokosc) + "\n";


                }


                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, sendtext);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Drzewostan"));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 10:
                if(requestCode==RESULT_OK){
                    String path = data.getData().getPath();

                }
                break;
        }
    }
}
