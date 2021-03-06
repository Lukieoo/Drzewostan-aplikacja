package com.anioncode.drzewostan;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PdfViewer extends AppCompatActivity {
    FloatingActionButton back;
    FloatingActionButton send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1);


        Intent intent = getIntent();
        String filename = intent.getStringExtra("filename");

        PDFView pdfView = findViewById(R.id.pdfView);
        send = findViewById(R.id.send);
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {finish();});
        send.setOnClickListener(v -> {
            Intent intentx = new Intent(Intent.ACTION_SEND);
            intentx.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + Environment.getExternalStorageDirectory()  + "/Lasy/" + filename));
            intentx.setType("aplication/pdf");
            intentx.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(intentx, "Share PDF"));

        });
        System.out.println(Environment.getDataDirectory() + "/Lasy/" + filename);
        File file = new File(Environment.getExternalStorageDirectory()  + "/Lasy/" + filename);
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
