package com.anioncode.drzewostan;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.anioncode.drzewostan.Adapters.FolderAdapter;
import com.anioncode.drzewostan.Framgent.Fragment_Bird_Cherry;
import com.anioncode.drzewostan.Framgent.Fragment_Oak_without;
import com.anioncode.drzewostan.Framgent.Fragment_alder;
import com.anioncode.drzewostan.Framgent.Fragment_beech;
import com.anioncode.drzewostan.Framgent.Fragment_birch;
import com.anioncode.drzewostan.Framgent.Fragment_fir;
import com.anioncode.drzewostan.Framgent.Fragment_larch;
import com.anioncode.drzewostan.Framgent.Fragment_oak;
import com.anioncode.drzewostan.Framgent.Fragment_oak_red;
import com.anioncode.drzewostan.Framgent.Fragment_pine;
import com.anioncode.drzewostan.Framgent.Fragment_spruce;
import com.anioncode.drzewostan.SQLite.DatabaseAlder;
import com.anioncode.drzewostan.SQLite.DatabaseHelper;
import com.anioncode.drzewostan.SQLite.DatabaseTablenameHelper;
import com.anioncode.drzewostan.SQLite.DatabaseUniversal;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.os.Environment.getExternalStorageState;
import static java.lang.System.currentTimeMillis;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private Button reset;
    private Button save;
    private Button send;
    private AdView mAdView;
    private FloatingActionButton actionButton;

    static String TAG = "ExelLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        MobileAds.initialize(this, "ca-app-pub-3788232558823244~9887124070");


        actionButton = (FloatingActionButton) findViewById(R.id.fabsave);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarid);


        final SharedPreferences sharedPref = getPreferences(this.MODE_PRIVATE);
        String txt = sharedPref.getString("dane", "");


        if (txt.equals("Yes")) {

            //   appBarLayout.setVisibility(View.INVISIBLE);
//                    appBarLayout.setExpanded(false);
//                    appBarLayout.setActivated(false);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) appBarLayout.getLayoutParams();
            lp.height = 0;


        }
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                LayoutInflater inflater2 = (LayoutInflater) v.getContext().getSystemService(v.getContext().LAYOUT_INFLATER_SERVICE);
                View view1 = inflater2.inflate(R.layout.odzial_layout, null);
                final EditText editText = view1.findViewById(R.id.editext);


                builder1.setView(view1);

                builder1.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    saveExcelFile(getApplicationContext(), String.valueOf(editText.getText()).trim() +"_odzial_"+currentTimeMillis()+ ".pdf",String.valueOf(editText.getText()).trim());
                                    Toast.makeText(getApplicationContext(), "Zapisano", Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Wystąpił błąd", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                AlertDialog alert1 = builder1.create();
                alert1.show();

            }
        });
//        actionButton1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                Uri uri = Uri.parse(String.valueOf(getExternalFilesDir(null))); // a directory
//                Toast.makeText(MainActivity.this,String.valueOf(getExternalFilesDir(null)),Toast.LENGTH_LONG).show();
//                intent.setDataAndType(uri, "*/*");
//                startActivity(Intent.createChooser(intent, "Open folder"));
//               // Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//               // intent.setDataAndType(Uri.fromFile(getExternalFilesDir(null)),"*/*");
//              //  startActivityForResult(intent, 7);
//
////                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.anioncode.drzewostan");
////                if (launchIntent != null) {
////                    startActivity(launchIntent);//null pointer check in case package name was not found
////                }
//            }
//        });

        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        Viewpageradapter viewpageradapter = new Viewpageradapter(getSupportFragmentManager());
        ///Dodajesz Fragmenty
        viewpageradapter.Addfragment(new Fragment_pine(), "Sosna");
        viewpageradapter.Addfragment(new Fragment_oak(), "Dąb Sz.");
        viewpageradapter.Addfragment(new Fragment_oak_red(), "Dąb Czer.");

        viewpageradapter.Addfragment(new Fragment_beech(), "Buk");
        viewpageradapter.Addfragment(new Fragment_birch(), "Brzoza");
        viewpageradapter.Addfragment(new Fragment_Bird_Cherry(), "Czeremch");
        viewpageradapter.Addfragment(new Fragment_Oak_without(), "Grab");
        viewpageradapter.Addfragment(new Fragment_alder(), "Olszyna");
        viewpageradapter.Addfragment(new Fragment_fir(), "Jodła");
        viewpageradapter.Addfragment(new Fragment_larch(), "Modrzew");
        viewpageradapter.Addfragment(new Fragment_spruce(), "Świerk");
        ///Ukazanie adaptera
        viewPager.setAdapter(viewpageradapter);
        tabLayout.setupWithViewPager(viewPager);

        reset = (Button) findViewById(R.id.reset);
        save = (Button) findViewById(R.id.save);
        send = (Button) findViewById(R.id.send);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DatabaseAlder databaseAlder = new DatabaseAlder(view.getContext());
                final DatabaseHelper databaseHelper = new DatabaseHelper(view.getContext());
                final DatabaseTablenameHelper databaseTablenameHelper = new DatabaseTablenameHelper(view.getContext());
                ///Universal
                final DatabaseUniversal Universal1 = new DatabaseUniversal(view.getContext(), "BIRCH");
                final DatabaseUniversal Universal2 = new DatabaseUniversal(view.getContext(), "OAK_RED");
                final DatabaseUniversal Universal3 = new DatabaseUniversal(view.getContext(), "BIRDCHERRY");
                final DatabaseUniversal Universal4 = new DatabaseUniversal(view.getContext(), "BEECH");
                final DatabaseUniversal Universal5 = new DatabaseUniversal(view.getContext(), "HORNBEAM");
                final DatabaseUniversal Universal6 = new DatabaseUniversal(view.getContext(), "FIR");
                final DatabaseUniversal Universal7 = new DatabaseUniversal(view.getContext(), "LARCH");
                final DatabaseUniversal Universal8 = new DatabaseUniversal(view.getContext(), "SPRUCE");

                AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());

                builder1.setMessage("Czy chcesz zresetować bazę ?");
                builder1.setTitle("Reset");

                builder1.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                for (int i = 1; i <= 39; i++) {
                                    databaseAlder.updateDatado0(i, 0, 0, 0, 0, 0, 0, 0);
                                    databaseHelper.updateDatado0(i, 0, 0, 0, 0, 0, 0, 0);
                                    databaseTablenameHelper.updateDatado0(i, 0, 0, 0, 0, 0, 0, 0);

                                    Universal1.updateDatado0(i, 0, 0, 0, 0, 0, 0, 0);
                                    Universal2.updateDatado0(i, 0, 0, 0, 0, 0, 0, 0);
                                    Universal3.updateDatado0(i, 0, 0, 0, 0, 0, 0, 0);
                                    Universal4.updateDatado0(i, 0, 0, 0, 0, 0, 0, 0);
                                    Universal5.updateDatado0(i, 0, 0, 0, 0, 0, 0, 0);
                                    Universal6.updateDatado0(i, 0, 0, 0, 0, 0, 0, 0);
                                    Universal7.updateDatado0(i, 0, 0, 0, 0, 0, 0, 0);
                                    Universal8.updateDatado0(i, 0, 0, 0, 0, 0, 0, 0);
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
            ///TODO DOKO
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());

                LayoutInflater inflater2 = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
                View view1 = inflater2.inflate(R.layout.dialog_file_explorer, null);
                RecyclerView  recyclerView=view1.findViewById(R.id.filesRec);
                List<String> folderList = new ArrayList<>();


                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+"/Lasy/").toString();
                Log.d("Files", "Path: " + path);
                File directory = new File(path);
                File[] files = directory.listFiles();
                Log.d("Files", "Size: "+ files.length);
                for (int i = 0; i < files.length; i++)
                {
                    folderList.add( files[i].getName()) ;
                    Log.d("Files", "FileName:" + files[i].getName());

                }


                FolderAdapter mAdapter = new FolderAdapter(folderList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);

                builder1.setView(view1);
                builder1.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert11 = builder1.create();
                alert11.show();



            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 10:
                if (requestCode == RESULT_OK) {
                    String path = data.getData().getPath();

                }
                break;
        }
    }

    private boolean saveExcelFile(Context context, String fileName,String odzial) throws DocumentException, IOException {

        ///PDF
        Document document = new Document();

        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+"/Lasy/"+fileName).toString()));

            document.open();
        }catch(Exception e){

        }
            PdfPTable table = new PdfPTable(8); // 3 columns.

        /// PDF ~~

        // check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e(TAG, "Storage not available or read only");
            return false;
        }


        boolean success = false;

//        //New Workbook
//        Workbook wb = new HSSFWorkbook();
//
//        Cell c = null;
//
//        //Cell style for header row
//        CellStyle cs = wb.createCellStyle();
//        cs.setFillForegroundColor(HSSFColor.LIME.index);
//        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//
//        //New Sheet
//        Sheet sheet1 = null;
//        sheet1 = wb.createSheet(odzial);
//
//        // Generate column headings
//        Row row = sheet1.createRow(0);
//
//        c = row.createCell(0);
//        c.setCellValue("Drzewo");
//        //c.setCellStyle(cs);
//
//        c = row.createCell(1);
//        c.setCellValue("Średnica");
//        c.setCellStyle(cs);
//
//        c = row.createCell(2);
//        c.setCellValue("klasa_1");
//        c.setCellStyle(cs);
//
//        c = row.createCell(3);
//        c.setCellValue("klasa_2");
//        c.setCellStyle(cs);
//
//        c = row.createCell(4);
//        c.setCellValue("klasa_3");
//        c.setCellStyle(cs);
//
//        c = row.createCell(5);
//        c.setCellValue("klasa_a");
//        c.setCellStyle(cs);
//
//        c = row.createCell(6);
//        c.setCellValue("klasa_b");
//        c.setCellStyle(cs);
//
//        c = row.createCell(7);
//        c.setCellValue("klasa_c");
//        c.setCellStyle(cs);
//
//        c = row.createCell(8);
//        c.setCellValue("wysokość");
//        c.setCellStyle(cs);

        //PDF!!
         pdfCreator(document,odzial,"Sosna", table);
        //PDF!!

        ArrayList<String> strings = new ArrayList<>();
        strings.add("Brzoza");
        strings.add("Dąb czerwony");
        strings.add("Czeremch");
        strings.add("Grab");
        strings.add("Buk");
        strings.add("Jodła");
        strings.add("Świerk");
        strings.add("Modrzew");

        DatabaseUniversal Universal;

        int ixx = 1;

        final DatabaseAlder databaseAlder = new DatabaseAlder(getApplicationContext());
        final DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        final DatabaseTablenameHelper databaseTablenameHelper = new DatabaseTablenameHelper(getApplicationContext());

        Cursor data = databaseHelper.AllData();

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
//            Row row1 = sheet1.createRow(ixx);
//
//            c = row1.createCell(0);
//            c.setCellValue("Sosna");
//            //c.setCellStyle(cs);
//
//            c = row1.createCell(1);
//            c.setCellValue(srednica);
//
//
//            c = row1.createCell(2);
//            c.setCellValue(klasa_1);
//
//
//            c = row1.createCell(3);
//            c.setCellValue(klasa_2);
//
//
//            c = row1.createCell(4);
//            c.setCellValue(klasa_3);
//
//
//            c = row1.createCell(5);
//            c.setCellValue(klasa_a);
//
//
//            c = row1.createCell(6);
//            c.setCellValue(klasa_b);
//
//
//            c = row1.createCell(7);
//            c.setCellValue(klasa_c);
//
//
//            c = row1.createCell(8);
//            c.setCellValue(wysokosc);

            ixx++;
            ///PDF
            pdfdata(document,table,srednica,klasa_1,klasa_2,klasa_3,klasa_a,klasa_b,klasa_c,wysokosc);

            ///PDF~~
        }



        Cursor datax = databaseTablenameHelper.AllData();
        //pdf
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        table.flushContent();
        document.newPage();
        pdfCreator(document,odzial,"Dąb", table);
        //
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
//            Row row1 = sheet1.createRow(ixx);
//
//            c = row1.createCell(0);
//            c.setCellValue("Dąb");
//            //c.setCellStyle(cs);
//
//            c = row1.createCell(1);
//            c.setCellValue(srednica);
//
//
//            c = row1.createCell(2);
//            c.setCellValue(klasa_1);
//
//
//            c = row1.createCell(3);
//            c.setCellValue(klasa_2);
//
//
//            c = row1.createCell(4);
//            c.setCellValue(klasa_3);
//
//
//            c = row1.createCell(5);
//            c.setCellValue(klasa_a);
//
//
//            c = row1.createCell(6);
//            c.setCellValue(klasa_b);
//
//
//            c = row1.createCell(7);
//            c.setCellValue(klasa_c);
//
//
//            c = row1.createCell(8);
//            c.setCellValue(wysokosc);

            ixx++;

            //pdf
            pdfdata(document,table,srednica,klasa_1,klasa_2,klasa_3,klasa_a,klasa_b,klasa_c,wysokosc);
            //
        }
        Cursor dataxx = databaseAlder.AllData();

        //pdf
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        table.flushContent();
        document.newPage();
        pdfCreator(document,odzial,"Olszyna", table);
        //
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

//            Row row1 = sheet1.createRow(ixx);
//
//            c = row1.createCell(0);
//            c.setCellValue("Olszyna");
//            //c.setCellStyle(cs);
//            c = row1.createCell(1);
//            c.setCellValue(srednica);
//
//            c = row1.createCell(2);
//            c.setCellValue(klasa_1);
//
//            c = row1.createCell(3);
//            c.setCellValue(klasa_2);
//
//            c = row1.createCell(4);
//            c.setCellValue(klasa_3);
//
//            c = row1.createCell(5);
//            c.setCellValue(klasa_a);
//
//            c = row1.createCell(6);
//            c.setCellValue(klasa_b);
//
//            c = row1.createCell(7);
//            c.setCellValue(klasa_c);
//
//            c = row1.createCell(8);
//            c.setCellValue(wysokosc);
            ixx++;
            pdfdata(document,table,srednica,klasa_1,klasa_2,klasa_3,klasa_a,klasa_b,klasa_c,wysokosc);
        }
        ///pdf
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        table.flushContent();
        document.newPage();

        ///pdf
        for (String a : strings) {

            Cursor datax1 = null;
            if (a.equals("Brzoza")) {
                pdfCreator(document,odzial,"Brzoza", table);
                Universal = new DatabaseUniversal(getApplicationContext(), "BIRCH");
                datax1 = Universal.AllData();
            }
            if (a.equals("Dąb czerwony")) {
                pdfCreator(document,odzial,"Dąb czerwony", table);
                Universal = new DatabaseUniversal(getApplicationContext(), "OAK_RED");
                datax1 = Universal.AllData();
            }
            if (a.equals("Czeremch")) {
                pdfCreator(document,odzial,"Czeremch", table);
                Universal = new DatabaseUniversal(getApplicationContext(), "BIRDCHERRY");
                datax1 = Universal.AllData();
            }
            if (a.equals("Buk")) {
                pdfCreator(document,odzial,"Buk", table);
                Universal = new DatabaseUniversal(getApplicationContext(), "BEECH");
                datax1 = Universal.AllData();
            }
            if (a.equals("Grab")) {
                pdfCreator(document,odzial,"Grab", table);
                Universal = new DatabaseUniversal(getApplicationContext(), "HORNBEAM");
                datax1 = Universal.AllData();
            }
            if (a.equals("Jodła")) {
                pdfCreator(document,odzial,"Jodła", table);
                Universal = new DatabaseUniversal(getApplicationContext(), "FIR");
                datax1 = Universal.AllData();
            }
            if (a.equals("Świerk")) {
                pdfCreator(document,odzial,"Świerk", table);
                Universal = new DatabaseUniversal(getApplicationContext(), "SPRUCE");
                datax1 = Universal.AllData();
            }
            if (a.equals("Modrzew")) {
                pdfCreator(document,odzial,"Modrzew", table);
                Universal = new DatabaseUniversal(getApplicationContext(), "LARCH");
                datax1 = Universal.AllData();
            }
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

//                Row row1 = sheet1.createRow(ixx);
//
//                c = row1.createCell(0);
//                c.setCellValue(a);
//                //c.setCellStyle(cs);
//
//                c = row1.createCell(1);
//                c.setCellValue(srednica);
//
//
//                c = row1.createCell(2);
//                c.setCellValue(klasa_1);
//
//
//                c = row1.createCell(3);
//                c.setCellValue(klasa_2);
//
//
//                c = row1.createCell(4);
//                c.setCellValue(klasa_3);
//
//
//                c = row1.createCell(5);
//                c.setCellValue(klasa_a);
//
//
//                c = row1.createCell(6);
//                c.setCellValue(klasa_b);
//
//
//                c = row1.createCell(7);
//                c.setCellValue(klasa_c);
//
//
//                c = row1.createCell(8);
//                c.setCellValue(wysokosc);
                pdfdata(document,table,srednica,klasa_1,klasa_2,klasa_3,klasa_a,klasa_b,klasa_c,wysokosc);
                ixx++;
            }
            ///pdf
            try {
                document.add(table);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            table.flushContent();
            document.newPage();

            ///pdf
        }
        ixx = 1;

//        sheet1.setColumnWidth(0, (15 * 100));
//        sheet1.setColumnWidth(1, (15 * 100));
//        sheet1.setColumnWidth(2, (15 * 100));
//        sheet1.setColumnWidth(3, (15 * 100));
//        sheet1.setColumnWidth(4, (15 * 100));
//        sheet1.setColumnWidth(5, (15 * 100));
//        sheet1.setColumnWidth(6, (15 * 100));
//        sheet1.setColumnWidth(7, (15 * 100));
//        sheet1.setColumnWidth(8, (15 * 100));

//        // Create a path where we will place our List of objects on external storage
//        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+"/Lasy/");
//        path.mkdir();
//        File file = new File( path,  fileName);
//        FileOutputStream os = null;
//        System.out.println("fille://" + (context.getExternalFilesDir(null)));
//
//
//        try {
//            os = new FileOutputStream(file);
//            wb.write(os);
//            Log.w("FileUtils", "Writing file" + file);
//            success = true;
//        } catch (IOException e) {
//            Log.w("FileUtils", "Error writing " + file, e);
//        } catch (Exception e) {
//            Log.w("FileUtils", "Failed to save file", e);
//        } finally {
//            try {
//                if (null != os)
//                    os.close();
//            } catch (Exception ex) {
//            }
//        }

        ///PDF

        document.close();
        ///PDF~~
        return success;
    }

    private void pdfdata(Document document,PdfPTable table,String srednica ,int klasa_1,int klasa_2,int klasa_3,int klasa_a,int klasa_b,int klasa_c,int wysokosc ) {
        Paragraph ph1=new Paragraph(srednica);
        ph1.setAlignment(Element.ALIGN_JUSTIFIED);
        Paragraph ph2=new Paragraph(new Paragraph(String.valueOf(klasa_1) ));
        ph2.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
        Paragraph ph3=new Paragraph(new Paragraph(String.valueOf(klasa_2)));
        ph3.setAlignment(Element.ALIGN_MIDDLE);
        Paragraph ph4=new Paragraph(new Paragraph(String.valueOf(klasa_3)));
        ph4.setAlignment(Element.ALIGN_RIGHT);
        Paragraph ph5=new Paragraph(new Paragraph(String.valueOf(klasa_a)));
        ph5.setAlignment(Element.ALIGN_CENTER);
        Paragraph ph6=new Paragraph(new Paragraph(String.valueOf(klasa_b)));
        ph6.setAlignment(Element.ALIGN_CENTER);
        Paragraph ph7=new Paragraph(new Paragraph(String.valueOf(klasa_c)));
        ph7.setAlignment(Element.ALIGN_CENTER);
        Paragraph ph8=new Paragraph(new Paragraph(String.valueOf(wysokosc)));
        ph8.setAlignment(Element.ALIGN_CENTER);

        PdfPCell rowll1 = new PdfPCell(ph1);
        PdfPCell rowll2 = new PdfPCell(ph2);
        PdfPCell rowll3 = new PdfPCell(ph3);
        PdfPCell rowll4 = new PdfPCell(ph4);
        PdfPCell rowll5 = new PdfPCell(ph5);
        PdfPCell rowll6 = new PdfPCell(ph6);
        PdfPCell rowll7 = new PdfPCell(ph7);
        PdfPCell rowll8 = new PdfPCell(ph8);

        table.addCell(rowll1);
        table.addCell(rowll2);
        table.addCell(rowll3);
        table.addCell(rowll4);
        table.addCell(rowll5);
        table.addCell(rowll6);
        table.addCell(rowll7);
        table.addCell(rowll8);


    }

    private void pdfCreator(Document document,String odzial,String nazwalasu,PdfPTable table) throws IOException, DocumentException {
        ///PDF
        InputStream ims = getAssets().open("forest.png");
        Bitmap bmp = BitmapFactory.decodeStream(ims);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image image = Image.getInstance(stream.toByteArray());

        image.setAbsolutePosition(500f, 50f);
        document.add(image);
        BaseFont urName = BaseFont.createFont("assets/Exoplanetaria.ttf", BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
        Font urFontName = new Font(urName, 12);
        Font urFontName2 = new Font(urName, 20);
        Font urFontNameinside = new Font(urName, 10);

//        Font font1 = new Font(Font.FontFamily.HELVETICA  , 25, Font.BOLD);
        Chunk chunk1 = new Chunk("Drzwostan (c) Paweł Krzyściak " ,urFontName);

        Chunk chunk2 = new Chunk(nazwalasu+ ", odział : "+odzial,urFontName2);

        document.add(chunk1);
        document.add( Chunk.NEWLINE );
        Phrase phrase1 = new Phrase(40);
        document.add(phrase1);
        document.add(chunk2);
        Phrase phrase2 = new Phrase(30);
        document.add(phrase2);
        document.add( Chunk.NEWLINE );

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        document.add(new Chunk(dateFormat.format(date).toString(),urFontName));
        document.add(phrase2);
        PdfPCell cell1 = new PdfPCell(new Paragraph("Średnica",urFontNameinside));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Klasa_1",urFontNameinside));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Klasa_2",urFontNameinside));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Klasa_3",urFontNameinside));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Klasa_a",urFontNameinside));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Klasa_b",urFontNameinside));
        PdfPCell cell7 = new PdfPCell(new Paragraph("Klasa_c",urFontNameinside));
        PdfPCell cell8 = new PdfPCell(new Paragraph("Wysokość",urFontNameinside));


        cell1.setBackgroundColor(new BaseColor(123,205,180));
        cell2.setBackgroundColor(new BaseColor(123,205,180));
        cell3.setBackgroundColor(new BaseColor(123,205,180));
        cell4.setBackgroundColor(new BaseColor(123,205,180));
        cell5.setBackgroundColor(new BaseColor(123,205,180));
        cell6.setBackgroundColor(new BaseColor(123,205,180));
        cell7.setBackgroundColor(new BaseColor(123,205,180));
        cell8.setBackgroundColor(new BaseColor(123,205,180));

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);


        ///PDF~~
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}
