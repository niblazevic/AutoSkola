package com.example.autoskola_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    Animation topAnim, bottomAnim;

    ImageView imgLogoSplashScreen;
    TextView txtVelikiSplashScreen, txtMaliSplashScreen;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        fAuth = FirebaseAuth.getInstance();

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        imgLogoSplashScreen = findViewById(R.id.imgLogoSplashScreen);
        txtVelikiSplashScreen = findViewById(R.id.txtVelikiSplashScreen);
        txtMaliSplashScreen = findViewById(R.id.txtMaliSplashScreen);

        imgLogoSplashScreen.setAnimation(topAnim);
        txtVelikiSplashScreen.setAnimation(bottomAnim);
        txtMaliSplashScreen.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(fAuth.getCurrentUser() != null){

                    Intent intent = new Intent(SplashScreen.this, GlavniIzbornik.class);

                    Pair[] pairs = new Pair[3];
                    pairs[0] = new Pair<View, String>(imgLogoSplashScreen, "logo_image");
                    pairs[1] = new Pair<View, String>(txtVelikiSplashScreen, "logo_text");
                    pairs[2] = new Pair<View, String>(txtVelikiSplashScreen, "logo_desc");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this, pairs);
                    startActivity(intent, options.toBundle());

                }else{
                    Intent intent = new Intent(SplashScreen.this, Login.class);

                    Pair[] pairs = new Pair[3];
                    pairs[0] = new Pair<View, String>(imgLogoSplashScreen, "logo_image");
                    pairs[1] = new Pair<View, String>(txtVelikiSplashScreen, "logo_text");
                    pairs[2] = new Pair<View, String>(txtVelikiSplashScreen, "logo_desc");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this, pairs);
                    startActivity(intent, options.toBundle());
                }

            }
        }, 3000);
    }
}