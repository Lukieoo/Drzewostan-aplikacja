package com.anioncode.drzewostan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

import static com.anioncode.drzewostan.SplashScreen.preferencesLicence;

public class LicenceActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private EditText licencekey;
    private TextView tekst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licence);
        ImageView join = findViewById(R.id.join);
        PulsatorLayout pulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        licencekey = (EditText) findViewById(R.id.licencekey);
        tekst = (TextView) findViewById(R.id.tekst);

        db = FirebaseFirestore.getInstance();


        join.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

            pulsator.start();
            licencekey.setVisibility(View.GONE);
            tekst.setVisibility(View.GONE);
            if (isNetworkConnected()){
            db.collection("licences_key")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {



                                    if (licencekey.getText().toString().trim().equals(document.getId()) ) {
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                pulsator.stop();
                                                SharedPreferences.Editor preferencesEditor2 = preferencesLicence.edit();
                                                preferencesEditor2.putString("licenceKey", "corect");
                                                preferencesEditor2.commit();
                                                Toast.makeText(LicenceActivity.this,"Klucz prawidłowy",Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(LicenceActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }, 1000);
                                    } else {
                                        Toast.makeText(LicenceActivity.this,"Nie prawidłowy",Toast.LENGTH_LONG).show();
                                        pulsator.stop();
                                        licencekey.setVisibility(View.VISIBLE);
                                        tekst.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                pulsator.stop();
                                licencekey.setVisibility(View.VISIBLE);
                                tekst.setVisibility(View.VISIBLE);
                                Toast.makeText(LicenceActivity.this,"Nie możemy nawiązać połączenia",Toast.LENGTH_LONG).show();
                                Log.w("okrs", "Error getting documents.", task.getException());
                            }
                        }
                    });
            }else {
                pulsator.stop();
                licencekey.setVisibility(View.VISIBLE);
                tekst.setVisibility(View.VISIBLE);
                Toast.makeText(LicenceActivity.this,"Brak połączenia do autoryzacji",Toast.LENGTH_LONG).show();
            }
        });
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)  getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }
}
