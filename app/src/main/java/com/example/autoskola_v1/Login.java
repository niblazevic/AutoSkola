package com.example.autoskola_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.Window;
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

import java.util.Objects;

public class Login extends AppCompatActivity {

    ImageView imgLogoLogin;
    TextInputEditText txtEmail, txtSifra;
    TextInputLayout Email, Sifra;
    TextView txtNaslov, txtPodnaslov;
    Button btnUlogirajSe, btnZaboravljenaSifra, btnRegistracija;
    FirebaseAuth fAuth;

    Ucitavanje ucitavanje = new Ucitavanje(Login.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.txtEmail);
        txtSifra = findViewById(R.id.txtSifra);
        fAuth = FirebaseAuth.getInstance();
        btnUlogirajSe = findViewById(R.id.btnUlogirajSe);
        btnRegistracija = findViewById(R.id.btnRegistracija);
        btnZaboravljenaSifra = findViewById(R.id.btnZaboravljenaSifra);
        Email = findViewById(R.id.Email);
        Sifra = findViewById(R.id.Sifra);
        imgLogoLogin = findViewById(R.id.imgLogoLogin);
        txtNaslov = findViewById(R.id.txtNaslov);
        txtPodnaslov = findViewById(R.id.txtPodnaslov);

        btnUlogirajSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = Objects.requireNonNull(txtEmail.getText()).toString();
                String sifra = Objects.requireNonNull(txtSifra.getText()).toString();

                if(TextUtils.isEmpty(email)){
                    txtEmail.setError("Email nije unesen!");
                    return;
                }
                if(TextUtils.isEmpty(sifra)){
                    txtSifra.setError("Sifra nije unesena!");
                    return;
                }
                if(sifra.length() < 6){
                    txtSifra.setError("Sifra mora imati barem 6 znakova!");
                    return;
                }

                ucitavanje.pokreniUcitavanje();

                fAuth.signInWithEmailAndPassword(email,sifra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Login.this,"Ulogiravanje uspijesno!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Login.this, GlavniIzbornik.class);

                            Pair[] pairs = new Pair[3];
                            pairs[0] = new Pair<View, String>(imgLogoLogin, "logo_image");
                            pairs[1] = new Pair<View, String>(txtNaslov, "logo_text");
                            pairs[2] = new Pair<View, String>(txtPodnaslov, "logo_desc");
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);

                            ucitavanje.makniUcitavanje();
                            startActivity(intent, options.toBundle());
                            Login.this.finishAfterTransition();

                        }else{
                            Toast.makeText(Login.this, "Neuspjesno ulogiravanje!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            ucitavanje.makniUcitavanje();
                        }
                    }
                });
            }
        });

        btnRegistracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                Pair[] pairs = new Pair[8];

                pairs[0] = new Pair<View, String>(imgLogoLogin, "logo_image");
                pairs[1] = new Pair<View, String>(txtNaslov, "logo_text");
                pairs[2] = new Pair<View, String>(txtPodnaslov, "logo_desc");
                pairs[3] = new Pair<View, String>(Email, "email_trans");
                pairs[4] = new Pair<View, String>(Sifra, "pass_trans");
                pairs[5] = new Pair<View, String>(btnZaboravljenaSifra, "forget_trans");
                pairs[6] = new Pair<View, String>(btnUlogirajSe, "prijava_trans");
                pairs[7] = new Pair<View, String>(btnRegistracija, "register_login_trans");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        btnZaboravljenaSifra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetEmail = new EditText(v.getContext());
                AlertDialog.Builder dijalogZaPromjenuSifre = new AlertDialog.Builder(v.getContext());
                dijalogZaPromjenuSifre.setTitle("Promjena sifre?");
                dijalogZaPromjenuSifre.setMessage("Upisi svoj email kako bi primio link za promjenu sifre!");
                dijalogZaPromjenuSifre.setView(resetEmail);

                dijalogZaPromjenuSifre.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                   public void onClick(DialogInterface dialog, int which) {
                        String emailPromjena = resetEmail.getText().toString();
                        if(emailPromjena.length() < 1){
                            Toast.makeText(Login.this,"Niste unijeli e-mail!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            fAuth.sendPasswordResetEmail(emailPromjena).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Login.this,"Link za promjenu lozinke vam je poslan na email!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Login.this, "Link za promjenu sifre nije poslan!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                dijalogZaPromjenuSifre.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                dijalogZaPromjenuSifre.create().show();
            }
        });
    }
}
