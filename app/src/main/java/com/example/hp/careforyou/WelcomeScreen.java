package com.example.hp.careforyou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WelcomeScreen extends AppCompatActivity {

    ImageView WelcomeImage;
    TextView AppNameTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        WelcomeImage = (ImageView)findViewById(R.id.welcomeimageid);
        AppNameTextView =(TextView)findViewById(R.id.appnametextViewid);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        Animation animationtextview = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.sequential);
        WelcomeImage.startAnimation(animation);
        AppNameTextView.startAnimation(animationtextview);
        Thread timer = new Thread(){

            @Override
            public void run() {

                try {
                    sleep(4000);
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                    super.run();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

      timer.start();
    }
}
