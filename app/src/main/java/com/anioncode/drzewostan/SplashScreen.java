package com.anioncode.drzewostan;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.anioncode.drzewostan.SQLite.DatabaseAlder;
import com.anioncode.drzewostan.SQLite.DatabaseHelper;
import com.anioncode.drzewostan.SQLite.DatabaseTablenameHelper;
import com.anioncode.drzewostan.SQLite.DatabaseUniversal;

public class SplashScreen extends AppCompatActivity {
    ImageView imageView;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        preferences = getSharedPreferences("0", Activity.MODE_PRIVATE);

        SharedPreferences.Editor preferencesEditor = preferences.edit();

        //Toast.makeText(this,"OKi",Toast.LENGTH_LONG).show();

       // preferencesEditor.putString("0","drugi");
      //  preferencesEditor.commit();

            String textFromPreferences = preferences.getString("0", "");


        //  Toast.makeText(this,textFromPreferences,Toast.LENGTH_LONG).show();
        imageView=findViewById(R.id.imagecon);
//        SplashScreen.this.getSupportActionBar().hide();


        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        imageView.startAnimation(animation);
//Remove notification bar
        //  this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//set content view AFTER ABOVE sequence (to avoid crash)
        //    this.setContentView(R.layout.activity_splash_screen);


        if(!textFromPreferences.equals("Pierwszy")){
           // Toast.makeText(this,"IF",Toast.LENGTH_LONG).show();
            preferencesEditor.putString("0","Pierwszy");
            preferencesEditor.commit();
            populate1();
            populate2();
            populate3();
            populate4();
            populate5();
            populate6();
            populate7();
            populate8();
            populate9();
            populate10();
            populate11();


            new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {
             Intent intent= new Intent(SplashScreen.this,MainActivity.class);
             startActivity(intent);
             finish();
         }
     },1000);

        }else {

            Intent intent= new Intent(SplashScreen.this,MainActivity.class);
            startActivity(intent);
            finish();
        }



    }
    private void populate1(){

        double tmp;
        DatabaseHelper databaseHelper=new DatabaseHelper(this);
        for(int i = 7; i <= 87; i+=2){

            if(i<27)tmp=i+1.9;
            else if(i==27)tmp=i+3.9;
            else{
                i+=2;
                tmp=i+3.9;

            }


            databaseHelper.insertData(i+"-"+tmp,0,0,0,0,0,0,0);
        }
    }
    private void populate2(){

        double tmp;
        DatabaseTablenameHelper databaseHelper=new DatabaseTablenameHelper(this);
        for(int i = 7; i <= 87; i+=2){
            if(i<27)tmp=i+1.9;
            else if(i==27)tmp=i+3.9;
            else{
                i+=2;
                tmp=i+3.9;

            }

            databaseHelper.insertData(i+"-"+tmp,0,0,0,0,0,0,0);
        }
    }

    private void populate3(){

        double tmp;
        DatabaseAlder databaseHelper=new DatabaseAlder(this);
        for(int i = 7; i <= 87; i+=2){
            if(i<27)tmp=i+1.9;
            else if(i==27)tmp=i+3.9;
            else{
                i+=2;
                tmp=i+3.9;

            }
            databaseHelper.insertData(i+"-"+tmp,0,0,0,0,0,0,0);
        }
    }

    private void populate4() {

        double tmp;
        DatabaseUniversal databaseHelper = new DatabaseUniversal(this, "BIRCH");
        for (int i = 7; i <= 87; i += 2) {
            if(i<27)tmp=i+1.9;
            else if(i==27)tmp=i+3.9;
            else{
                i+=2;
                tmp=i+3.9;

            }
            databaseHelper.insertData(i + "-" + tmp, 0, 0, 0, 0, 0, 0, 0);
        }
    }

    private void populate5() {

        double tmp;
        DatabaseUniversal databaseHelper = new DatabaseUniversal(this, "OAK_RED");
        for (int i = 7; i <= 87; i += 2) {
            if(i<27)tmp=i+1.9;
            else if(i==27)tmp=i+3.9;
            else{
                i+=2;
                tmp=i+3.9;

            }
            databaseHelper.insertData(i + "-" + tmp, 0, 0, 0, 0, 0, 0, 0);
        }
    }

    private void populate6() {

        double tmp;
        DatabaseUniversal databaseHelper = new DatabaseUniversal(this, "BIRDCHERRY");
        for (int i = 7; i <= 87; i += 2) {
            if(i<27)tmp=i+1.9;
            else if(i==27)tmp=i+3.9;
            else{
                i+=2;
                tmp=i+3.9;

            }
            databaseHelper.insertData(i + "-" + tmp, 0, 0, 0, 0, 0, 0, 0);
        }
    }

    private void populate7() {

        double tmp;
        DatabaseUniversal databaseHelper = new DatabaseUniversal(this, "BEECH");
        for (int i = 7; i <= 87; i += 2) {
            if(i<27)tmp=i+1.9;
            else if(i==27)tmp=i+3.9;
            else{
                i+=2;
                tmp=i+3.9;

            }
            databaseHelper.insertData(i + "-" + tmp, 0, 0, 0, 0, 0, 0, 0);
        }
    }

    private void populate8() {

        double tmp;
        DatabaseUniversal databaseHelper = new DatabaseUniversal(this, "HORNBEAM");
        for (int i = 7; i <= 87; i += 2) {
            if(i<27)tmp=i+1.9;
            else if(i==27)tmp=i+3.9;
            else{
                i+=2;
                tmp=i+3.9;

            }
            databaseHelper.insertData(i + "-" + tmp, 0, 0, 0, 0, 0, 0, 0);
        }
    }
    private void populate9() {

        double tmp;
        DatabaseUniversal databaseHelper = new DatabaseUniversal(this, "FIR");
        for (int i = 7; i <= 87; i += 2) {
            if(i<27)tmp=i+1.9;
            else if(i==27)tmp=i+3.9;
            else{
                i+=2;
                tmp=i+3.9;

            }
            databaseHelper.insertData(i + "-" + tmp, 0, 0, 0, 0, 0, 0, 0);
        }
    }
    private void populate10() {

        double tmp;
        DatabaseUniversal databaseHelper = new DatabaseUniversal(this, "LARCH");
        for (int i = 7; i <= 87; i += 2) {
            if(i<27)tmp=i+1.9;
            else if(i==27)tmp=i+3.9;
            else{
                i+=2;
                tmp=i+3.9;

            }
            databaseHelper.insertData(i + "-" + tmp, 0, 0, 0, 0, 0, 0, 0);
        }
    }
    private void populate11() {

        double tmp;
        DatabaseUniversal databaseHelper = new DatabaseUniversal(this, "SPRUCE");
        for (int i = 7; i <= 87; i += 2) {
            if(i<27)tmp=i+1.9;
            else if(i==27)tmp=i+3.9;
            else{
                i+=2;
                tmp=i+3.9;

            }
            databaseHelper.insertData(i + "-" + tmp, 0, 0, 0, 0, 0, 0, 0);
        }
    }
}
