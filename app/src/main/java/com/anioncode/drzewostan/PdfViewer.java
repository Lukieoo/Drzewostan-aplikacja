package com.anioncode.drzewostan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class PdfViewer extends AppCompatActivity {
    FloatingActionButton back;
    FloatingActionButton send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);


        Intent intent = getIntent();
        String filename = intent.getStringExtra("filename");

        PDFView pdfView = findViewById(R.id.pdfView);
        send = findViewById(R.id.send);
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {finish();});
        send.setOnClickListener(v -> {

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/Lasy/" + filename).toString());

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello!");

// (Optional) Here we're setting the title of the content
            sendIntent.putExtra(Intent.EXTRA_STREAM, "fille://"+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/Lasy/" + filename).toString());
            System.out.println(Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/Lasy/" + filename).toString()));
            sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            sendIntent.setType("application/pdf");
// Show the Sharesheet
            startActivity(Intent.createChooser(sendIntent, null));
        });

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/Lasy/" + filename).toURI());
        pdfView.fromFile(file) // all pages are displayed by default
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                // allows to draw something on the current page, usually visible in the middle of the screen
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                .load();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
