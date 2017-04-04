package com.siestasystemheadpod.headpodv10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class IntroActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY=2000;//tiempo en ms

    private SharedPreferSession sharedPreferSession;//Clase par almacenar los inicios de sesión.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        sharedPreferSession = new SharedPreferSession(this);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {


                if(sharedPreferSession.getSession()==null) {
                    Log.d("password","no_exuste");
                    Intent mainIntent = new Intent().setClass(

                            IntroActivity.this, MainActivity.class
                    );
                    startActivity(mainIntent);
                    finish();

                }
                else
                {
                    Log.d("password","existe");
                    Intent intent = new Intent (IntroActivity.this, HomeActivity.class);
                    intent.putExtra("fragment", "fragment_home");
                    startActivity(intent);
                    finish();


                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);

    }
}
