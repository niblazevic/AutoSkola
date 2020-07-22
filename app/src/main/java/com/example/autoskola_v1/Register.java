package com.example.autoskola_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    TextInputEditText txtImePrezime, txtEmail, txtSifra, txtPotvrdaSifre;
    TextInputLayout txtEmailRegistracija, txtSifraRegistracija;
    ImageView imgLogoRegister;
    Button btnRegistrirajSe, btnLogin;
    TextView txtNaslovRegistracija, txtPodnaslovRegistracija;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    Ucitavanje ucitavanje = new Ucitavanje(Register.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        txtImePrezime = findViewById(R.id.txtImePrezime);
        txtEmail = findViewById(R.id.txtEmail);
        txtSifra = findViewById(R.id.txtSifra);
        txtPotvrdaSifre = findViewById(R.id.txtPotvrdaSifre);
        btnRegistrirajSe = findViewById(R.id.btnUlogirajSe);
        btnLogin = findViewById(R.id.btnLogin);
        imgLogoRegister = findViewById(R.id.imgLogoRegister);
        txtNaslovRegistracija = findViewById(R.id.txtNaslovRegistracija);
        txtPodnaslovRegistracija = findViewById(R.id.txtPodnaslovRegistracija);
        txtEmailRegistracija = findViewById(R.id.txtEmailRegistracija);
        txtSifraRegistracija = findViewById(R.id.txtSifraRegistracija);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        btnRegistrirajSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = txtEmail.getText().toString().trim();
                String sifra = txtSifra.getText().toString().trim();
                String potvrdaSifre = txtPotvrdaSifre.getText().toString().trim();
                final String ImePrezime = txtImePrezime.getText().toString();

                if(TextUtils.isEmpty(email)){
                    txtEmail.setError("Email nije unesen!");
                    return;
                }
                else if(TextUtils.isEmpty(sifra)){
                    txtSifra.setError("Sifra nije unesena!");
                    return;
                }
                else if(TextUtils.isEmpty(potvrdaSifre)){
                    txtPotvrdaSifre.setError("Potvrda sifre nije unesena!");
                    return;
                }
                else if(sifra.length() < 6){
                    txtSifra.setError("Sifra mora imati barem 6 znakova!");
                    return;
                }
                else if(!potvrdaSifre.equals(sifra)){
                    txtPotvrdaSifre.setError("Sifre se ne podudaraju!");
                    return;
                }

                ucitavanje.pokreniUcitavanje();

                fAuth.createUserWithEmailAndPassword(email,sifra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(Register.this,"Racun napravljen!", Toast.LENGTH_LONG).show();
                            userID = fAuth.getCurrentUser().getUid();


                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("ImePrezime", ImePrezime);
                            user.put("Email", email);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "Uspijeh: Korisnicki profil je napravljen za " + userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "Greska: " + e.toString());
                                }
                            });

                            Intent intent = new Intent(getApplicationContext(),GlavniIzbornik.class);

                            Pair[] pairs = new Pair[3];
                            pairs[0] = new Pair<View, String>(imgLogoRegister, "logo_image");
                            pairs[1] = new Pair<View, String>(txtNaslovRegistracija, "logo_text");
                            pairs[2] = new Pair<View, String>(txtPodnaslovRegistracija, "logo_desc");

                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Register.this, pairs);
                            ucitavanje.makniUcitavanje();
                            startActivity(intent, options.toBundle());
                        }else{
                            Toast.makeText(Register.this, "Neuspjela registracija! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            ucitavanje.makniUcitavanje();
                        }

                    }
                });
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                Pair[] pairs = new Pair[7];

                pairs[0] = new Pair<View, String>(imgLogoRegister, "logo_image");
                pairs[1] = new Pair<View, String>(txtNaslovRegistracija, "logo_text");
                pairs[2] = new Pair<View, String>(txtPodnaslovRegistracija, "logo_desc");
                pairs[3] = new Pair<View, String>(txtEmailRegistracija, "email_trans");
                pairs[4] = new Pair<View, String>(txtSifraRegistracija, "pass_trans");
                pairs[5] = new Pair<View, String>(btnRegistrirajSe, "prijava_trans");
                pairs[6] = new Pair<View, String>(btnLogin, "register_login_trans");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Register.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });
    }
}
