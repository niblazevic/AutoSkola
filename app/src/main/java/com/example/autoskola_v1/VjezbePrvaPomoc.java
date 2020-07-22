package com.example.autoskola_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class VjezbePrvaPomoc extends AppCompatActivity {

    Button btn1_22, btn23_44, btn45_66;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_vjezbe_prva_pomoc);

        btn1_22 = findViewById(R.id.btn1_22);
        btn23_44 = findViewById(R.id.btn23_44);
        btn45_66 = findViewById(R.id.btn45_66);

        btn1_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VjezbePrvaPomoc.this,VjezbePitanja.class);
                intent.putExtra("KATEGORIJA", "1. set pitanja");
                intent.putExtra("IDKATEGORIJE", "Kat2");
                intent.putExtra("IDSETA","1_22");
                intent.putExtra("IDDETALJNO","0");
                intent.putExtra("LOGO","logo_image_set_1");

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(btn1_22, "logo_image_set_1");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(VjezbePrvaPomoc.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });
        btn23_44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VjezbePrvaPomoc.this,VjezbePitanja.class);
                intent.putExtra("KATEGORIJA", "2. set pitanja");
                intent.putExtra("IDKATEGORIJE", "Kat2");
                intent.putExtra("IDSETA","23_44");
                intent.putExtra("IDDETALJNO","0");
                intent.putExtra("LOGO","logo_image_set_2");

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(btn23_44, "logo_image_set_2");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(VjezbePrvaPomoc.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });
        btn45_66.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VjezbePrvaPomoc.this,VjezbePitanja.class);
                intent.putExtra("KATEGORIJA", "3. set pitanja");
                intent.putExtra("IDKATEGORIJE", "Kat2");
                intent.putExtra("IDSETA","45_66");
                intent.putExtra("IDDETALJNO","0");
                intent.putExtra("LOGO","logo_image_set_3");

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(btn45_66, "logo_image_set_3");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(VjezbePrvaPomoc.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });
    }
}
