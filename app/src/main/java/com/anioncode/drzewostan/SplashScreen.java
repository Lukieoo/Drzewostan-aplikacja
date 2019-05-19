package com.anioncode.drzewostan;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.anioncode.drzewostan.SQLite.DatabaseAlder;
import com.anioncode.drzewostan.SQLite.DatabaseHelper;
import com.anioncode.drzewostan.SQLite.DatabaseTablenameHelper;

public class SplashScreen extends AppCompatActivity {
ImageView imageView;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        preferences = getSharedPreferences("0", Activity.MODE_PRIVATE);

        SharedPreferences.Editor preferencesEditor = preferences.edit();


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


            new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {
             Intent intent= new Intent(SplashScreen.this,MainActivity.class);
             startActivity(intent);
             finish();
         }
     },1000);

        }else {
           // Toast.makeText(this,"ELSE",Toast.LENGTH_LONG).show();
            Intent intent= new Intent(SplashScreen.this,MainActivity.class);
            startActivity(intent);
            finish();
        }



    }
    private void populate1(){

        double tmp;
        DatabaseHelper databaseHelper=new DatabaseHelper(this);
        for(int i = 7; i < 87; i+=2){
            tmp=i+1.9;

            databaseHelper.insertData(i+"-"+tmp,0,0,0,0,0,0,0);
        }
    }
    private void populate2(){

        double tmp;
        DatabaseTablenameHelper databaseHelper=new DatabaseTablenameHelper(this);
        for(int i = 7; i < 87; i+=2){
            tmp=i+1.9;

            databaseHelper.insertData(i+"-"+tmp,0,0,0,0,0,0,0);
        }
    }
    private void populate3(){

        double tmp;
        DatabaseAlder databaseHelper=new DatabaseAlder(this);
        for(int i = 7; i < 87; i+=2){
            tmp=i+1.9;
            databaseHelper.insertData(i+"-"+tmp,0,0,0,0,0,0,0);
        }
    }
}
