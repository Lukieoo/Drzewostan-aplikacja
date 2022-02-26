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
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.anioncode.drzewostan.Adapters.FolderAdapter;
import com.anioncode.drzewostan.Adapters.ViewPagerAdapter;
import com.anioncode.drzewostan.Framgent.TypeOfTreeFragment;
import com.anioncode.drzewostan.SQLite.DatabaseUniversal;
import com.anioncode.drzewostan.utils.MyPreference;
import com.anioncode.drzewostan.utils.TreeListMock;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.os.Environment.getExternalStorageState;
import static java.lang.System.currentTimeMillis;

public class MainActivity extends AppCompatActivity {

    static String TAG = "ExelLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        FloatingActionButton actionButton = (FloatingActionButton) findViewById(R.id.fabsave);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbarid);


        final SharedPreferences sharedPref = getPreferences(this.MODE_PRIVATE);
        String txt = sharedPref.getString("dane", "");


        if (txt.equals("Yes")) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) appBarLayout.getLayoutParams();
            lp.height = 0;
        }
        MyPreference myPreference = new MyPreference(this);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                LayoutInflater inflater2 = (LayoutInflater) v.getContext().getSystemService(v.getContext().LAYOUT_INFLATER_SERVICE);
                View view1 = inflater2.inflate(R.layout.odzial_layout, null);
                final EditText editText = view1.findViewById(R.id.editext);
                final EditText editext2 = view1.findViewById(R.id.editext2);
                final EditText nameOfUser = view1.findViewById(R.id.tv_name_user);
                nameOfUser.setText(myPreference.getNameOfUser());
                builder1.setView(view1);
                builder1.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (editText.getText().toString().trim().equals("")) {
                                    Toast.makeText(getApplicationContext(), "Nie zapisano, brak nazwy oddziału", Toast.LENGTH_LONG).show();
                                } else {
                                    try {
                                        myPreference.setNameOfUser(nameOfUser.getText().toString());
                                        saveExcelFile(getApplicationContext(), String.valueOf(editText.getText()).trim() + "_oddzial_" + currentTimeMillis() + ".pdf", String.valueOf(editText.getText()).trim(), editext2.getText().toString());
                                        Toast.makeText(getApplicationContext(), "Zapisano", Toast.LENGTH_LONG).show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "Wystąpił błąd", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });

                AlertDialog alert1 = builder1.create();
                alert1.show();

            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        ViewPagerAdapter viewpageradapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (String name : TreeListMock.INSTANCE.getTreeKindList()) {
            System.out.println(name);
            viewpageradapter.Addfragment(TypeOfTreeFragment.newInstance(name), TreeListMock.INSTANCE.translateTree(name));
        }

        viewPager.setAdapter(viewpageradapter);
        tabLayout.setupWithViewPager(viewPager);

        Button reset = (Button) findViewById(R.id.reset);
        Button save = (Button) findViewById(R.id.save);
        Button send = (Button) findViewById(R.id.send);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseUniversal Universal1 = new DatabaseUniversal(view.getContext(), "BIRCH");
                final DatabaseUniversal Universal2 = new DatabaseUniversal(view.getContext(), "OAK_RED");
                final DatabaseUniversal Universal3 = new DatabaseUniversal(view.getContext(), "BIRDCHERRY");
                final DatabaseUniversal Universal4 = new DatabaseUniversal(view.getContext(), "BEECH");
                final DatabaseUniversal Universal5 = new DatabaseUniversal(view.getContext(), "HORNBEAM");
                final DatabaseUniversal Universal6 = new DatabaseUniversal(view.getContext(), "FIR");
                final DatabaseUniversal Universal7 = new DatabaseUniversal(view.getContext(), "LARCH");
                final DatabaseUniversal Universal8 = new DatabaseUniversal(view.getContext(), "SPRUCE");
                final DatabaseUniversal Universal9 = new DatabaseUniversal(view.getContext(), "PINE");
                final DatabaseUniversal Universal10 = new DatabaseUniversal(view.getContext(), "OAK");
                final DatabaseUniversal Universal11 = new DatabaseUniversal(view.getContext(), "ALDER");

                AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());

                builder1.setMessage("Czy chcesz zresetować bazę ?");
                builder1.setTitle("Reset");

                builder1.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                for (int i = 1; i <= 39; i++) {
                                    Universal1.resetDatabase(i, 0, 0, 0, 0, 0, 0, 0);
                                    Universal2.resetDatabase(i, 0, 0, 0, 0, 0, 0, 0);
                                    Universal3.resetDatabase(i, 0, 0, 0, 0, 0, 0, 0);
                                    Universal4.resetDatabase(i, 0, 0, 0, 0, 0, 0, 0);
                                    Universal5.resetDatabase(i, 0, 0, 0, 0, 0, 0, 0);
                                    Universal6.resetDatabase(i, 0, 0, 0, 0, 0, 0, 0);
                                    Universal7.resetDatabase(i, 0, 0, 0, 0, 0, 0, 0);
                                    Universal8.resetDatabase(i, 0, 0, 0, 0, 0, 0, 0);
                                    Universal9.resetDatabase(i, 0, 0, 0, 0, 0, 0, 0);
                                    Universal10.resetDatabase(i, 0, 0, 0, 0, 0, 0, 0);
                                    Universal11.resetDatabase(i, 0, 0, 0, 0, 0, 0, 0);
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

                LayoutInflater inflater2 = (LayoutInflater) view
                        .getContext()
                        .getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);

                View view1 = inflater2.inflate(R.layout.setings_layout, null);

                String txt = sharedPref.getString("dane", "");
                final CheckBox checkBox = view1.findViewById(R.id.checkbox);

                if (txt.equals("Yes")) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
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

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());

                LayoutInflater inflater2 = (LayoutInflater) view
                        .getContext()
                        .getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
                View view1 = inflater2.inflate(R.layout.dialog_file_explorer, null);
                RecyclerView recyclerView = view1.findViewById(R.id.filesRec);
                List<String> folderList = new ArrayList<>();

                File file = new File(Environment.getExternalStorageDirectory() + "/Lasy/");
                if (file.exists()) {
                } else {
                    file.mkdir();
                }

                String path = Environment.getExternalStorageDirectory() + "/Lasy/";
                Log.d("Files", "Path: " + path);
                File directory = new File(path);
                File[] files = directory.listFiles();

                assert files != null;
                Log.d("Files", "Size: " + files.length);
                for (File value : files) {
                    folderList.add(value.getName());
                    Log.d("Files", "FileName:" + value.getName());

                }


                FolderAdapter mAdapter = new FolderAdapter(MainActivity.this, folderList, new FolderAdapter.PressPDF() {
                    @Override
                    public void longClick(String filename) {
                        Intent intent = new Intent(MainActivity.this, PdfViewer.class);
                        intent.putExtra("filename", filename);
                        startActivity(intent);
                    }
                });
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
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                if (requestCode == RESULT_OK) {
                    String path = data.getData().getPath();

                }
                break;
        }
    }

    private boolean saveExcelFile(Context context, String fileName, String odzial, String notes) throws DocumentException, IOException {
        Document document = new Document();

        File file = new File(Environment.getExternalStorageDirectory() + "/Lasy/");
        if (file.exists()) {
        } else {
            file.mkdir();
        }

        PdfWriter.getInstance(document,
                new FileOutputStream(Environment.getExternalStorageDirectory() + "/Lasy/" + fileName));

        document.open();

        PdfPTable table = new PdfPTable(8); // 3 columns.
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e(TAG, "Storage not available or read only");
            return false;
        }
        boolean success = false;

        BaseFont urName = BaseFont.createFont("assets/Exoplanetaria.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font urFontName = new Font(urName, 15);
        Chunk chunk1 = new Chunk("Notatka służbowa", urFontName);
        document.add(chunk1);
        document.add(Chunk.NEWLINE);
        Phrase phrase1 = new Phrase(20);
        document.add(phrase1);
        urFontName = new Font(urName, 12);
        Chunk chunk2 = new Chunk(notes, urFontName);
        document.add(chunk2);
        document.add(Chunk.NEWLINE);
        Phrase phrase2 = new Phrase(40);
        document.add(phrase2);

        DatabaseUniversal Universal;

        for (String a : TreeListMock.INSTANCE.getTreeKindList()) {

            pdfCreator(document, odzial, TreeListMock.INSTANCE.translateTreeFullName(a), table);
            Universal = new DatabaseUniversal(getApplicationContext(), a);
            Cursor dataTreeCursor = Universal.AllData();

            while (dataTreeCursor.moveToNext()) {

                String srednica = dataTreeCursor.getString(1);
                int klasa_1 = dataTreeCursor.getInt(2);
                int klasa_2 = dataTreeCursor.getInt(3);
                int klasa_3 = dataTreeCursor.getInt(4);
                int klasa_a = dataTreeCursor.getInt(5);
                int klasa_b = dataTreeCursor.getInt(6);
                int klasa_c = dataTreeCursor.getInt(7);
                int wysokosc = dataTreeCursor.getInt(8);

                pdfdata(document, table, srednica, klasa_1, klasa_2, klasa_3, klasa_a, klasa_b, klasa_c, wysokosc);
            }
            try {
                document.add(table);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            table.flushContent();
            document.newPage();

        }


        document.close();
        return success;
    }

    private void pdfdata(Document document, PdfPTable table, String srednica, int klasa_1, int klasa_2, int klasa_3, int klasa_a, int klasa_b, int klasa_c, int wysokosc) {
        Paragraph ph1 = new Paragraph(srednica);
        ph1.setAlignment(Element.ALIGN_JUSTIFIED);
        Paragraph ph2 = new Paragraph(new Paragraph(String.valueOf(klasa_1)));
        ph2.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
        Paragraph ph3 = new Paragraph(new Paragraph(String.valueOf(klasa_2)));
        ph3.setAlignment(Element.ALIGN_MIDDLE);
        Paragraph ph4 = new Paragraph(new Paragraph(String.valueOf(klasa_3)));
        ph4.setAlignment(Element.ALIGN_RIGHT);
        Paragraph ph5 = new Paragraph(new Paragraph(String.valueOf(klasa_a)));
        ph5.setAlignment(Element.ALIGN_CENTER);
        Paragraph ph6 = new Paragraph(new Paragraph(String.valueOf(klasa_b)));
        ph6.setAlignment(Element.ALIGN_CENTER);
        Paragraph ph7 = new Paragraph(new Paragraph(String.valueOf(klasa_c)));
        ph7.setAlignment(Element.ALIGN_CENTER);
        Paragraph ph8 = new Paragraph(new Paragraph(String.valueOf(wysokosc)));
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

    private void pdfCreator(Document document, String odzial, String nazwalasu, PdfPTable table) throws IOException, DocumentException {
        ///PDF
        InputStream ims = getAssets().open("forest.png");
        Bitmap bmp = BitmapFactory.decodeStream(ims);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image image = Image.getInstance(stream.toByteArray());

        image.setAbsolutePosition(500f, 50f);
        document.add(image);
        BaseFont urName = BaseFont.createFont("assets/Exoplanetaria.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font urFontName = new Font(urName, 12);
        Font urFontName2 = new Font(urName, 20);
        Font urFontNameinside = new Font(urName, 10);

        MyPreference preference = new MyPreference(this);
//        Font font1 = new Font(Font.FontFamily.HELVETICA  , 25, Font.BOLD);
        Chunk chunk1 = new Chunk("Drzewostan (c) " + preference.getNameOfUser(), urFontName);

        Chunk chunk2 = new Chunk(nazwalasu + ", odział : " + odzial, urFontName2);

        document.add(chunk1);
        document.add(Chunk.NEWLINE);
        Phrase phrase1 = new Phrase(40);
        document.add(phrase1);
        document.add(chunk2);
        Phrase phrase2 = new Phrase(30);
        document.add(phrase2);
        document.add(Chunk.NEWLINE);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        document.add(new Chunk(dateFormat.format(date).toString(), urFontName));
        document.add(phrase2);
        PdfPCell cell1 = new PdfPCell(new Paragraph("Średnica", urFontNameinside));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Klasa_1", urFontNameinside));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Klasa_2", urFontNameinside));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Klasa_3", urFontNameinside));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Klasa_a", urFontNameinside));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Klasa_b", urFontNameinside));
        PdfPCell cell7 = new PdfPCell(new Paragraph("Klasa_c", urFontNameinside));
        PdfPCell cell8 = new PdfPCell(new Paragraph("Wysokość", urFontNameinside));


        cell1.setBackgroundColor(new BaseColor(123, 205, 180));
        cell2.setBackgroundColor(new BaseColor(123, 205, 180));
        cell3.setBackgroundColor(new BaseColor(123, 205, 180));
        cell4.setBackgroundColor(new BaseColor(123, 205, 180));
        cell5.setBackgroundColor(new BaseColor(123, 205, 180));
        cell6.setBackgroundColor(new BaseColor(123, 205, 180));
        cell7.setBackgroundColor(new BaseColor(123, 205, 180));
        cell8.setBackgroundColor(new BaseColor(123, 205, 180));

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
