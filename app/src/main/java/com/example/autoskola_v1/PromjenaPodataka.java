package com.example.autoskola_v1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class PromjenaPodataka extends AppCompatActivity {

    TextInputEditText txtPromjeniImePrezime, txtPromjeniEmail;
    Button btnPromjeniPodatke;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_promjena_podataka);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        txtPromjeniImePrezime = findViewById(R.id.txtPromjeniImePrezime);
        txtPromjeniEmail = findViewById(R.id.txtPromjeniEmail);
        btnPromjeniPodatke = findViewById(R.id.btnPromjeniPodatke);



        btnPromjeniPodatke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promjeniPodatke();
            }
        });

        ucitavanjePodataka();
    }

    private void promjeniPodatke() {
        if(txtPromjeniImePrezime.getText().toString().isEmpty() || txtPromjeniEmail.getText().toString().isEmpty()){
            Toast.makeText(this, "Sva polja moraju biti popunjena!", Toast.LENGTH_SHORT).show();
            return;
        }

        final String email = txtPromjeniEmail.getText().toString();
        user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                DocumentReference documentReference = fStore.collection("users").document(user.getUid());
                Map<String, Object> map = new HashMap<>();
                map.put("Email", email);
                map.put("ImePrezime", txtPromjeniImePrezime.getText().toString());
                documentReference.update(map);

                Toast.makeText(PromjenaPodataka.this, "Korisnik je ažuriran!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),GlavniIzbornik.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PromjenaPodataka.this, "Neuspjeno ažuriranje: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ucitavanjePodataka() {

        DocumentReference documentReference = fStore.collection("users").document(user.getUid());
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                assert documentSnapshot != null;
                txtPromjeniImePrezime.setText(documentSnapshot.getString("ImePrezime"));
            }
        });
        txtPromjeniEmail.setText(user.getEmail());
    }
}
