package com.anioncode.drzewostan;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.anioncode.drzewostan.Framgent.Fragment_Bird_Cherry;
import com.anioncode.drzewostan.Framgent.Fragment_Oak_without;
import com.anioncode.drzewostan.Framgent.Fragment_alder;
import com.anioncode.drzewostan.Framgent.Fragment_beech;
import com.anioncode.drzewostan.Framgent.Fragment_birch;
import com.anioncode.drzewostan.Framgent.Fragment_oak;
import com.anioncode.drzewostan.Framgent.Fragment_oak_red;
import com.anioncode.drzewostan.Framgent.Fragment_pine;
import com.anioncode.drzewostan.SQLite.DatabaseAlder;
import com.anioncode.drzewostan.SQLite.DatabaseHelper;
import com.anioncode.drzewostan.SQLite.DatabaseTablenameHelper;
import com.anioncode.drzewostan.SQLite.DatabaseUniversal;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

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

        final SharedPreferences sharedPref = getPreferences(this.MODE_PRIVATE);
        String txt = sharedPref.getString("dane", "");


        if (txt.equals("Yes")) {

            //   appBarLayout.setVisibility(View.INVISIBLE);
//                    appBarLayout.setExpanded(false);
//                    appBarLayout.setActivated(false);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) appBarLayout.getLayoutParams();
            lp.height = 0;


        }


        viewPager=(ViewPager) findViewById(R.id.viewpager_id);
        Viewpageradapter viewpageradapter= new Viewpageradapter(getSupportFragmentManager());
        ///Dodajesz Fragmenty
        viewpageradapter.Addfragment(new Fragment_pine(),"Sosna");
        viewpageradapter.Addfragment(new Fragment_oak(), "Dąb Sz");
        viewpageradapter.Addfragment(new Fragment_oak_red(), "Dąb Czerwony");

        viewpageradapter.Addfragment(new Fragment_beech(), "Buk");
        viewpageradapter.Addfragment(new Fragment_birch(), "Brzoza");
        viewpageradapter.Addfragment(new Fragment_Bird_Cherry(), "Czeremch");
        viewpageradapter.Addfragment(new Fragment_Oak_without(), "Grab");
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
                ///Universal
                final DatabaseUniversal Universal1 = new DatabaseUniversal(view.getContext(), "BIRCH");
                final DatabaseUniversal Universal2 = new DatabaseUniversal(view.getContext(), "OAK_RED");
                final DatabaseUniversal Universal3 = new DatabaseUniversal(view.getContext(), "BIRDCHERRY");
                final DatabaseUniversal Universal4 = new DatabaseUniversal(view.getContext(), "BEECH");
                final DatabaseUniversal Universal5 = new DatabaseUniversal(view.getContext(), "HORNBEAM");

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

                                        Universal1.updateDatado0(i, 0, 0, 0, 0, 0, 0, 0);
                                        Universal2.updateDatado0(i, 0, 0, 0, 0, 0, 0, 0);
                                        Universal3.updateDatado0(i, 0, 0, 0, 0, 0, 0, 0);
                                        Universal4.updateDatado0(i, 0, 0, 0, 0, 0, 0, 0);
                                        Universal5.updateDatado0(i, 0, 0, 0, 0, 0, 0, 0);
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
                AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());

                LayoutInflater inflater2 = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
                View view1 = inflater2.inflate(R.layout.setings_layout, null);

                ///Tworzenie chexboxa


                // Toast.makeText(getApplicationContext(),sharedPref.getString("dane", ""),Toast.LENGTH_SHORT).show();
                String txt = sharedPref.getString("dane", "");


                final CheckBox checkBox = view1.findViewById(R.id.checkbox);

                if (txt.equals("Yes")) {

                    //   appBarLayout.setVisibility(View.INVISIBLE);
//                    appBarLayout.setExpanded(false);
//                    appBarLayout.setActivated(false);
//                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)appBarLayout.getLayoutParams();
//                    lp.height = 0;

                    checkBox.setChecked(true);
                } else {
                    // appBarLayout.setVisibility(View.VISIBLE);

//                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)appBarLayout.getLayoutParams();
//                    lp.height = 120;
                    checkBox.setChecked(false);
                }

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                        @Override
                                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                            if (isChecked) {

                                                                SharedPreferences.Editor editor = sharedPref.edit();
                                                                editor.putString("dane", "Yes");
                                                                editor.commit();
                                                                //     Toast.makeText(getApplicationContext(),sharedPref.getString("dane", ""),Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                SharedPreferences.Editor editor = sharedPref.edit();
                                                                editor.putString("dane", "NO");
                                                                editor.commit();
                                                                //     Toast.makeText(getApplicationContext(),sharedPref.getString("dane", ""),Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }
                );

                builder1.setView(view1);


                builder1.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                                dialog.cancel();

                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

//                  final DatabaseAlder databaseAlder= new DatabaseAlder(view.getContext());
//                final DatabaseHelper databaseHelper = new DatabaseHelper(view.getContext());
//                 final DatabaseTablenameHelper databaseTablenameHelper= new DatabaseTablenameHelper(view.getContext());
//
//                //String text ="1";
//                Date currentTime = Calendar.getInstance().getTime();
//
//                String FILE_NAME = "Zapis"+currentTime.getDate()+currentTime.getTime()+currentTime.getDay()+".txt";
//                FileOutputStream fos = null;
//                try {
//                    fos = openFileOutput(FILE_NAME, MODE_APPEND);
//                    Cursor data = databaseHelper.AllData();
//                    fos.write("Sosna  \n".getBytes());
//                    fos.write(" Średnica  klasa_1  klasa_2  klasa_3  klasa_a klasa_b  klasa_c  wysokość \n".getBytes());
//
//                    while (data.moveToNext()) {
//                        //get the value from the database in column 1
//                        //then add it to the ArrayList
//
//                        String srednica = data.getString(1);
//                        int klasa_1 = data.getInt(2);
//                        int klasa_2 = data.getInt(3);
//                        int klasa_3 = data.getInt(4);
//                        int klasa_a = data.getInt(5);
//                        int klasa_b = data.getInt(6);
//                        int klasa_c = data.getInt(7);
//                        int wysokosc = data.getInt(8);
//                        String wyraz = srednica + " " + String.valueOf(klasa_1) + " " + String.valueOf(klasa_2) + " " + String.valueOf(klasa_3) + " " + String.valueOf(klasa_a) + " " + String.valueOf(klasa_b) + " " + String.valueOf(klasa_c) + " " + String.valueOf(wysokosc) + "\n";
//
//                        fos.write(wyraz.getBytes());
//                    }
//
//                    fos = openFileOutput(FILE_NAME, MODE_APPEND);
//                    Cursor datax = databaseTablenameHelper.AllData();
//                    fos.write("Dąb  SZypułkowy\n".getBytes());
//                    fos.write(" Średnica  klasa_1  klasa_2  klasa_3  klasa_a klasa_b  klasa_c  wysokość \n".getBytes());
//                    while (data.moveToNext()) {
//                        //get the value from the database in column 1
//                        //then add it to the ArrayList
//
//                        String srednica = data.getString(1);
//                        int klasa_1 = datax.getInt(2);
//                        int klasa_2 = datax.getInt(3);
//                        int klasa_3 = datax.getInt(4);
//                        int klasa_a = datax.getInt(5);
//                        int klasa_b = datax.getInt(6);
//                        int klasa_c = datax.getInt(7);
//                        int wysokosc = datax.getInt(8);
//                        String wyraz = srednica + " " + String.valueOf(klasa_1) + " " + String.valueOf(klasa_2) + " " + String.valueOf(klasa_3) + " " + String.valueOf(klasa_a) + " " + String.valueOf(klasa_b) + " " + String.valueOf(klasa_c) + " " + String.valueOf(wysokosc) + "\n";
//
//                        fos.write(wyraz.getBytes());
//                    }
//                    Cursor dataxx = databaseAlder.AllData();
//                    fos.write("Olszyna  \n".getBytes());
//                    fos.write(" Średnica  klasa_1  klasa_2  klasa_3  klasa_a klasa_b  klasa_c  wysokość \n".getBytes());
//                    while (data.moveToNext()) {
//                        //get the value from the database in column 1
//                        //then add it to the ArrayList
//
//                        String srednica = data.getString(1);
//                        int klasa_1 = dataxx.getInt(2);
//                        int klasa_2 = dataxx.getInt(3);
//                        int klasa_3 = dataxx.getInt(4);
//                        int klasa_a = dataxx.getInt(5);
//                        int klasa_b = dataxx.getInt(6);
//                        int klasa_c = dataxx.getInt(7);
//                        int wysokosc = dataxx.getInt(8);
//                        String wyraz = srednica + " " + String.valueOf(klasa_1) + " " + String.valueOf(klasa_2) + " " + String.valueOf(klasa_3) + " " + String.valueOf(klasa_a) + " " + String.valueOf(klasa_b) + " " + String.valueOf(klasa_c) + " " + String.valueOf(wysokosc) + "\n";
//
//                        fos.write(wyraz.getBytes());
//                    }
//
//                    Toast.makeText(view.getContext(), "Zapisano w " + getFilesDir() + "/" + FILE_NAME + "\n Plik zainportuj do exela",
//                            Toast.LENGTH_LONG).show();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (fos != null) {
//                        try {
//                            fos.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
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

                AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());

                LayoutInflater inflater2 = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
                View view1 = inflater2.inflate(R.layout.odzial_layout, null);
                final EditText editText = view1.findViewById(R.id.editext);

                builder1.setView(view1);


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
                    sendtext += srednica + " " + String.valueOf(klasa_1) + " " + String.valueOf(klasa_2) + " " + String.valueOf(klasa_3) + " " + String.valueOf(klasa_a) + " " + String.valueOf(klasa_b) + " " + String.valueOf(klasa_c) + " " + String.valueOf(wysokosc) + "\n";


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

                ArrayList<String> strings = new ArrayList<>();
                strings.add("Brzoza");
                strings.add("Dąb czerwony");
                strings.add("Czeremch");
                strings.add("Grab");
                strings.add("Buk");

                DatabaseUniversal Universal;

                for (String a : strings) {
                    Cursor datax1 = null;
                    if (a.equals("Brzoza")) {
                        sendtext += a + "  \n";
                        Universal = new DatabaseUniversal(view.getContext(), "BIRCH");
                        datax1 = Universal.AllData();
                    }
                    if (a.equals("Dąb czerwony")) {
                        sendtext += a + "  \n";
                        Universal = new DatabaseUniversal(view.getContext(), "OAK_RED");
                        datax1 = Universal.AllData();
                    }
                    if (a.equals("Czeremch")) {
                        sendtext += a + "  \n";
                        Universal = new DatabaseUniversal(view.getContext(), "BIRDCHERRY");
                        datax1 = Universal.AllData();
                    }
                    if (a.equals("Buk")) {
                        sendtext += a + "  \n";

                        Universal = new DatabaseUniversal(view.getContext(), "BEECH");
                        datax1 = Universal.AllData();
                    }
                    if (a.equals("Grab")) {
                        sendtext += a + "  \n";
                        Universal = new DatabaseUniversal(view.getContext(), "HORNBEAM");
                        datax1 = Universal.AllData();
                    }


                    sendtext += " Średnica  klasa_1  klasa_2  klasa_3  klasa_a klasa_b  klasa_c  wysokość \n";
                    while (datax1.moveToNext()) {
                        //get the value from the database in column 1
                        //then add it to the ArrayList

                        String srednica = datax1.getString(1);
                        int klasa_1 = datax1.getInt(2);
                        int klasa_2 = datax1.getInt(3);
                        int klasa_3 = datax1.getInt(4);
                        int klasa_a = datax1.getInt(5);
                        int klasa_b = datax1.getInt(6);
                        int klasa_c = datax1.getInt(7);
                        int wysokosc = datax1.getInt(8);
                        sendtext += srednica + " " + String.valueOf(klasa_1) + " " + String.valueOf(klasa_2) + " " + String.valueOf(klasa_3) + " " + String.valueOf(klasa_a) + " " + String.valueOf(klasa_b) + " " + String.valueOf(klasa_c) + " " + String.valueOf(wysokosc) + "\n";


                    }
                }
                final String Wszystko = sendtext;
                builder1.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ///
                                String pom = String.valueOf(editText.getText());

                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, "ODZIAŁ : " + pom + "\n" + Wszystko);
                                sendIntent.setType("text/plain");
                                startActivity(Intent.createChooser(sendIntent, "Drzewostan"));
                                ///
                                dialog.cancel();

                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                ////


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
