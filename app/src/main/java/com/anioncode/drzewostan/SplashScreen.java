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

import com.anioncode.drzewostan.utils.InitializationDatabase;

public class SplashScreen extends AppCompatActivity {
    ImageView imageView;
    private SharedPreferences preferences;
    static public SharedPreferences preferencesLicence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        preferences = getSharedPreferences("0", Activity.MODE_PRIVATE);
        preferencesLicence = getSharedPreferences("licenceKey", Activity.MODE_PRIVATE);

        SharedPreferences.Editor preferencesEditor = preferences.edit();

        String textFromPreferences = preferences.getString("0", "");
        String textFromLicence = preferencesLicence.getString("licenceKey", "");

        imageView = findViewById(R.id.imagecon);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        imageView.startAnimation(animation);

        if (!textFromPreferences.equals("Pierwszy")) {
            preferencesEditor.putString("0", "Pierwszy");
            preferencesEditor.apply();

            InitializationDatabase database = new InitializationDatabase(this);

            //Licencja
//            if (!textFromLicence.equals("corect")) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(SplashScreen.this, LicenceActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }, 1000);
//            } else {
            //Licencja koniec iplementacji
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 1000);
//            }

        } else {
            //Licencja
//            if (!textFromLicence.equals("corect")) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(SplashScreen.this, LicenceActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }, 1000);
//            } else {
            //Licencja koniec iplementacji
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
//            }

        }


    }

}
