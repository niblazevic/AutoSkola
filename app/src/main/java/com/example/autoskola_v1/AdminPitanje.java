package com.example.autoskola_v1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AdminPitanje extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseStorage fStorage;
    StorageReference storageReference;

    private Uri filePath;

    TextView txtAdminOpcija;
    Spinner spinnerTocanOdgovorAdmin;
    ImageView imgSlikaAdmin;
    TextInputEditText txtPitanjeAdmin, txtOdgovor1Admin, txtOdgovor2Admin, txtOdgovor3Admin;
    Button btnPotvrdi;
    String urlNoveSlike = "";


    private final int imgRequest = 71;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_pitanje);


        fAuth = FirebaseAuth.getInstance();
        fStorage = FirebaseStorage.getInstance();
        storageReference = fStorage.getReference();

        txtAdminOpcija = findViewById(R.id.txtAdminOpcija);
        txtPitanjeAdmin = findViewById(R.id.txtPitanjeAdmin);
        txtOdgovor1Admin = findViewById(R.id.txtOdgovor1Admin);
        txtOdgovor2Admin = findViewById(R.id.txtOdgovor2Admin);
        txtOdgovor3Admin = findViewById(R.id.txtOdgovor3Admin);
        imgSlikaAdmin = findViewById(R.id.imgSlikaAdmin);
        spinnerTocanOdgovorAdmin = findViewById(R.id.spinnerTocanOdgovorAdmin);
        btnPotvrdi = findViewById(R.id.btnPotvrdi);

        txtAdminOpcija.setText(getIntent().getStringExtra("KATEGORIJA") + " - " + getIntent().getStringExtra("ADMIN"));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tocanOdgovor, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTocanOdgovorAdmin.setAdapter(adapter);

        if(Objects.equals(getIntent().getStringExtra("ADMIN"), "Izmjene")){

            if(!Objects.equals(getIntent().getStringExtra("SLIKA"), "")){
                Picasso.get().load(getIntent().getStringExtra("SLIKA")).into(imgSlikaAdmin);
            }

            btnPotvrdi.setText("Ažuriraj pitanje");
            txtPitanjeAdmin.setText(getIntent().getStringExtra("PITANJE"));
            txtOdgovor1Admin.setText(getIntent().getStringExtra("ODGOVOR1"));
            txtOdgovor2Admin.setText(getIntent().getStringExtra("ODGOVOR2"));
            txtOdgovor3Admin.setText(getIntent().getStringExtra("ODGOVOR3"));

            String compareValue = getIntent().getStringExtra("TOCANODGOVOR");

            if (compareValue != null) {
                int spinnerPosition = adapter.getPosition(compareValue);
                spinnerTocanOdgovorAdmin.setSelection(spinnerPosition);
            }

        }

        btnPotvrdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Objects.equals(getIntent().getStringExtra("ADMIN"), "Izmjene")){
                    izmjenaPitanja();
                }else{
                    ucitavanjeBroja();
                }
            }
        });
        imgSlikaAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oznaciSliku();
            }
        });
    }

    private void oznaciSliku() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Označi sliku"), imgRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==imgRequest && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgSlikaAdmin.setImageBitmap(bitmap);
            }catch (IOException e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void izmjenaPitanja() {
        if(TextUtils.isEmpty(txtPitanjeAdmin.getText()) || TextUtils.isEmpty(txtOdgovor1Admin.getText()) || TextUtils.isEmpty(txtOdgovor2Admin.getText()) || TextUtils.isEmpty(txtOdgovor3Admin.getText())){
            Toast.makeText(this, "Sva polja moraju biti popunjena!", Toast.LENGTH_SHORT).show();
        }else{

            if(filePath!=null){

                if(!Objects.equals(getIntent().getStringExtra("SLIKA"), "")){
                    StorageReference photoRef = fStorage.getReferenceFromUrl(Objects.requireNonNull(getIntent().getStringExtra("SLIKA")));
                    photoRef.delete();
                }

                String Kategorija1 = getIntent().getStringExtra("IDKATEGORIJE");
                String IDseta1 = getIntent().getStringExtra("IDSETA");

                final StorageReference reference = storageReference.child("Pitanja/"+ Kategorija1 +"/"+ IDseta1 + "/" + UUID.randomUUID().toString());
                reference.putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        urlNoveSlike = uri.toString();

                                        fAuth.getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<GetTokenResult> task) {
                                                if (task.isSuccessful()) {

                                                    String Kategorija = getIntent().getStringExtra("IDKATEGORIJE");
                                                    String IDseta = getIntent().getStringExtra("IDSETA");
                                                    String Pitanje = getIntent().getStringExtra("NAME");


                                                    String url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/" + Kategorija + "/" + IDseta + "/" + Pitanje;
                                                    if(!getIntent().getStringExtra("IDDETALJNO").equals("0")){
                                                        url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/" + Kategorija + "/" + IDseta + "/" + getIntent().getStringExtra("IDDETALJNO") +"/Pitanja/" + Pitanje;
                                                    }

                                                    String auth = "Bearer " + task.getResult().getToken();

                                                    String json = "{\n" +
                                                            "  \"fields\": {\n" +
                                                            "    \"Pitanje\": {\n" +
                                                            "      \"stringValue\": \""+ txtPitanjeAdmin.getText() +"\"\n" +
                                                            "    },\n" +
                                                            "    \"Odgovor1\": {\n" +
                                                            "      \"stringValue\": \""+ txtOdgovor1Admin.getText() +"\"\n" +
                                                            "    },\n" +
                                                            "    \"Odgovor2\": {\n" +
                                                            "      \"stringValue\": \""+ txtOdgovor2Admin.getText() +"\"\n" +
                                                            "    },\n" +
                                                            "    \"Odgovor3\": {\n" +
                                                            "      \"stringValue\": \""+ txtOdgovor3Admin.getText() +"\"\n" +
                                                            "    },\n" +
                                                            "    \"TocanOdgovor\": {\n" +
                                                            "      \"integerValue\": "+ spinnerTocanOdgovorAdmin.getSelectedItem().toString() +"\n" +
                                                            "    },\n" +
                                                            "    \"Slika\": {\n" +
                                                            "      \"stringValue\": \""+ urlNoveSlike +"\"\n" +
                                                            "    }\n" +
                                                            "  }\n" +
                                                            "}";

                                                    RequestBody requestBody = RequestBody.create(json, JSON);

                                                    OkHttpClient client = new OkHttpClient();

                                                    Request request = new Request.Builder().addHeader("Authorization", auth).addHeader("Accept", "application/json").addHeader("Content-Type", "application/json").patch(requestBody).url(url).build();

                                                    client.newCall(request).enqueue(new Callback() {
                                                        @Override
                                                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                                            Toast.makeText(AdminPitanje.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }

                                                        @Override
                                                        public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                                                            if(response.isSuccessful()){
                                                                AdminPitanje.this.runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        Toast.makeText(AdminPitanje.this, "Pitanje je ažurirano!", Toast.LENGTH_SHORT).show();
                                                                        Intent intent = new Intent(AdminPitanje.this, EditiranjePitanja.class);
                                                                        intent.putExtra("ADMIN", getIntent().getStringExtra("ADMIN"));
                                                                        intent.putExtra("KATEGORIJA", getIntent().getStringExtra("KATEGORIJA"));
                                                                        intent.putExtra("IDKATEGORIJE", getIntent().getStringExtra("IDKATEGORIJE"));
                                                                        intent.putExtra("IDSETA", getIntent().getStringExtra("IDSETA"));
                                                                        intent.putExtra("IDDETALJNO", getIntent().getStringExtra("IDDETALJNO"));
                                                                        startActivity(intent);
                                                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                                                        finish();
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        });
            }else{
                final String Kategorija1 = getIntent().getStringExtra("IDKATEGORIJE");
                final String IDseta1 = getIntent().getStringExtra("IDSETA");

                fAuth.getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {

                            String Slika = "";

                            if(!Objects.equals(getIntent().getStringExtra("SLIKA"), "")){
                                Slika = getIntent().getStringExtra("SLIKA");
                            }

                            String Pitanje = getIntent().getStringExtra("NAME");



                            String url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/" + Kategorija1 + "/" + IDseta1 + "/" + Pitanje;
                            if(!getIntent().getStringExtra("IDDETALJNO").equals("0")){
                                url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/" + Kategorija1 + "/" + IDseta1 + "/" + getIntent().getStringExtra("IDDETALJNO") +"/Pitanja/" + Pitanje;
                            }

                            String auth = "Bearer " + task.getResult().getToken();

                            String json = "{\n" +
                                    "  \"fields\": {\n" +
                                    "    \"Pitanje\": {\n" +
                                    "      \"stringValue\": \""+ txtPitanjeAdmin.getText() +"\"\n" +
                                    "    },\n" +
                                    "    \"Odgovor1\": {\n" +
                                    "      \"stringValue\": \""+ txtOdgovor1Admin.getText() +"\"\n" +
                                    "    },\n" +
                                    "    \"Odgovor2\": {\n" +
                                    "      \"stringValue\": \""+ txtOdgovor2Admin.getText() +"\"\n" +
                                    "    },\n" +
                                    "    \"Odgovor3\": {\n" +
                                    "      \"stringValue\": \""+ txtOdgovor3Admin.getText() +"\"\n" +
                                    "    },\n" +
                                    "    \"TocanOdgovor\": {\n" +
                                    "      \"integerValue\": "+ spinnerTocanOdgovorAdmin.getSelectedItem().toString() +"\n" +
                                    "    },\n" +
                                    "    \"Slika\": {\n" +
                                    "      \"stringValue\": \""+ Slika +"\"\n" +
                                    "    }\n" +
                                    "  }\n" +
                                    "}";

                            RequestBody requestBody = RequestBody.create(json, JSON);

                            OkHttpClient client = new OkHttpClient();

                            Request request = new Request.Builder().addHeader("Authorization", auth).addHeader("Accept", "application/json").addHeader("Content-Type", "application/json").patch(requestBody).url(url).build();

                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                    Toast.makeText(AdminPitanje.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                                    if(response.isSuccessful()){
                                        AdminPitanje.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(AdminPitanje.this, "Pitanje je ažurirano!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(AdminPitanje.this, EditiranjePitanja.class);
                                                intent.putExtra("ADMIN", getIntent().getStringExtra("ADMIN"));
                                                intent.putExtra("KATEGORIJA", getIntent().getStringExtra("KATEGORIJA"));
                                                intent.putExtra("IDKATEGORIJE", getIntent().getStringExtra("IDKATEGORIJE"));
                                                intent.putExtra("IDSETA", getIntent().getStringExtra("IDSETA"));
                                                intent.putExtra("IDDETALJNO", getIntent().getStringExtra("IDDETALJNO"));
                                                startActivity(intent);
                                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                                finish();
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
    }

    private void ucitavanjeBroja() {
        if(TextUtils.isEmpty(txtPitanjeAdmin.getText()) || TextUtils.isEmpty(txtOdgovor1Admin.getText()) || TextUtils.isEmpty(txtOdgovor2Admin.getText()) || TextUtils.isEmpty(txtOdgovor3Admin.getText())){
            Toast.makeText(this, "Sva polja moraju biti popunjena!", Toast.LENGTH_SHORT).show();
        }else{
            fAuth.getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                @Override
                public void onComplete(@NonNull Task<GetTokenResult> task) {
                    if (task.isSuccessful()) {

                        String Kategorija = getIntent().getStringExtra("IDKATEGORIJE");
                        String IDseta = getIntent().getStringExtra("IDSETA");

                        String url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/" + Kategorija + "/BrojPitanja/" + IDseta;
                        if(!getIntent().getStringExtra("IDDETALJNO").equals("0")){
                            url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/" + Kategorija + "/BrojPitanja/" + IDseta + "/Pitanja/" + getIntent().getStringExtra("IDDETALJNO");
                        }

                        String auth = "Bearer " + task.getResult().getToken();


                        OkHttpClient client = new OkHttpClient();

                        final Request request = new Request.Builder().addHeader("Authorization", auth).url(url).build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Toast.makeText(AdminPitanje.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                if(response.isSuccessful()){
                                    final String myResponse = Objects.requireNonNull(response.body()).string();

                                    AdminPitanje.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                String brojPitanja = new JSONObject(myResponse).getJSONObject("fields").getJSONObject("BrojPitanja").getString("integerValue");
                                                updateanjeBroja(brojPitanja);

                                            }catch (JSONException err){
                                                Toast.makeText(AdminPitanje.this, err.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        });

                    }
                }
            });
        }
    }

    private void updateanjeBroja(final String broj) {
        fAuth.getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if (task.isSuccessful()) {

                    String Kategorija = getIntent().getStringExtra("IDKATEGORIJE");
                    String IDseta = getIntent().getStringExtra("IDSETA");

                    String url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/" + Kategorija + "/BrojPitanja/" + IDseta;
                    if(!getIntent().getStringExtra("IDDETALJNO").equals("0")){
                        url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/" + Kategorija + "/BrojPitanja/" + IDseta + "/Pitanja/" + getIntent().getStringExtra("IDDETALJNO");
                    }

                    String auth = "Bearer " + task.getResult().getToken();

                    final int temp = Integer.parseInt(broj);

                    String json = "{\n" +
                            "  \"fields\": {\n" +
                            "    \"BrojPitanja\": {\n" +
                            "      \"integerValue\": \"" + (temp + 1) + "\"\n" +
                            "    }\n" +
                            "  }\n" +
                            "}";

                    RequestBody requestBody = RequestBody.create(json, JSON);

                    OkHttpClient client = new OkHttpClient();

                    final Request request = new Request.Builder().addHeader("Authorization", auth).addHeader("Accept", "application/json").addHeader("Content-Type", "application/json").patch(requestBody).url(url).build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Toast.makeText(AdminPitanje.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                AdminPitanje.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dodavanjePitanja(String.valueOf(temp + 1));
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    private void dodavanjePitanja(final String brojPitanja) {

        if(filePath!=null){
            String Kategorija1 = getIntent().getStringExtra("IDKATEGORIJE");
            String IDseta1 = getIntent().getStringExtra("IDSETA");

            final StorageReference reference = storageReference.child("Pitanja/"+ Kategorija1 +"/"+ IDseta1 + "/" + UUID.randomUUID().toString());
            reference.putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            urlNoveSlike = uri.toString();

                            fAuth.getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                @Override
                                public void onComplete(@NonNull Task<GetTokenResult> task) {
                                    if (task.isSuccessful()) {

                                        final String Kategorija = getIntent().getStringExtra("IDKATEGORIJE");
                                        String IDseta = getIntent().getStringExtra("IDSETA");
                                        String Slika = urlNoveSlike;


                                        String url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/" + Kategorija + "/" + IDseta + "/Pitanje" + brojPitanja;
                                        if(!getIntent().getStringExtra("IDDETALJNO").equals("0")){
                                            url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/" + Kategorija + "/" + IDseta + "/" + getIntent().getStringExtra("IDDETALJNO") +"/Pitanja/Pitanje" + brojPitanja;
                                        }

                                        String auth = "Bearer " + task.getResult().getToken();

                                        String json = "{\n" +
                                                "  \"fields\": {\n" +
                                                "    \"Pitanje\": {\n" +
                                                "      \"stringValue\": \""+ txtPitanjeAdmin.getText() +"\"\n" +
                                                "    },\n" +
                                                "    \"Odgovor1\": {\n" +
                                                "      \"stringValue\": \""+ txtOdgovor1Admin.getText() +"\"\n" +
                                                "    },\n" +
                                                "    \"Odgovor2\": {\n" +
                                                "      \"stringValue\": \""+ txtOdgovor2Admin.getText() +"\"\n" +
                                                "    },\n" +
                                                "    \"Odgovor3\": {\n" +
                                                "      \"stringValue\": \""+ txtOdgovor3Admin.getText() +"\"\n" +
                                                "    },\n" +
                                                "    \"TocanOdgovor\": {\n" +
                                                "      \"integerValue\": "+ spinnerTocanOdgovorAdmin.getSelectedItem().toString() +"\n" +
                                                "    },\n" +
                                                "    \"Slika\": {\n" +
                                                "      \"stringValue\": \""+ Slika +"\"\n" +
                                                "    }\n" +
                                                "  }\n" +
                                                "}";

                                        RequestBody requestBody = RequestBody.create(json, JSON);

                                        OkHttpClient client = new OkHttpClient();

                                        Request request = new Request.Builder().addHeader("Authorization", auth).addHeader("Accept", "application/json").addHeader("Content-Type", "application/json").patch(requestBody).url(url).build();

                                        client.newCall(request).enqueue(new Callback() {
                                            @Override
                                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                                Toast.makeText(AdminPitanje.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                                                if(response.isSuccessful()){
                                                    AdminPitanje.this.runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(AdminPitanje.this, "Pitanje je dodano!", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(AdminPitanje.this, EditiranjePitanja.class);
                                                            intent.putExtra("ADMIN", "Izmjene");
                                                            intent.putExtra("KATEGORIJA", getIntent().getStringExtra("KATEGORIJA"));
                                                            intent.putExtra("IDKATEGORIJE", getIntent().getStringExtra("IDKATEGORIJE"));
                                                            intent.putExtra("IDSETA", getIntent().getStringExtra("IDSETA"));
                                                            intent.putExtra("IDDETALJNO", getIntent().getStringExtra("IDDETALJNO"));
                                                            startActivity(intent);
                                                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                                            finish();
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    });
                }
            });
        }else{
            fAuth.getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                @Override
                public void onComplete(@NonNull Task<GetTokenResult> task) {
                    if (task.isSuccessful()) {

                        final String Kategorija = getIntent().getStringExtra("IDKATEGORIJE");
                        String IDseta = getIntent().getStringExtra("IDSETA");
                        String Slika = "";


                        String url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/" + Kategorija + "/" + IDseta + "/Pitanje" + brojPitanja;
                        if(!getIntent().getStringExtra("IDDETALJNO").equals("0")){
                            url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/" + Kategorija + "/" + IDseta + "/" + getIntent().getStringExtra("IDDETALJNO") +"/Pitanja/Pitanje" + brojPitanja;
                        }

                        String auth = "Bearer " + task.getResult().getToken();

                        String json = "{\n" +
                                "  \"fields\": {\n" +
                                "    \"Pitanje\": {\n" +
                                "      \"stringValue\": \""+ txtPitanjeAdmin.getText() +"\"\n" +
                                "    },\n" +
                                "    \"Odgovor1\": {\n" +
                                "      \"stringValue\": \""+ txtOdgovor1Admin.getText() +"\"\n" +
                                "    },\n" +
                                "    \"Odgovor2\": {\n" +
                                "      \"stringValue\": \""+ txtOdgovor2Admin.getText() +"\"\n" +
                                "    },\n" +
                                "    \"Odgovor3\": {\n" +
                                "      \"stringValue\": \""+ txtOdgovor3Admin.getText() +"\"\n" +
                                "    },\n" +
                                "    \"TocanOdgovor\": {\n" +
                                "      \"integerValue\": "+ spinnerTocanOdgovorAdmin.getSelectedItem().toString() +"\n" +
                                "    },\n" +
                                "    \"Slika\": {\n" +
                                "      \"stringValue\": \""+ Slika +"\"\n" +
                                "    }\n" +
                                "  }\n" +
                                "}";

                        RequestBody requestBody = RequestBody.create(json, JSON);

                        OkHttpClient client = new OkHttpClient();

                        Request request = new Request.Builder().addHeader("Authorization", auth).addHeader("Accept", "application/json").addHeader("Content-Type", "application/json").patch(requestBody).url(url).build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Toast.makeText(AdminPitanje.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                                if(response.isSuccessful()){
                                    AdminPitanje.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(AdminPitanje.this, "Pitanje je dodano!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(AdminPitanje.this, EditiranjePitanja.class);
                                            intent.putExtra("ADMIN", "Izmjene");
                                            intent.putExtra("KATEGORIJA", getIntent().getStringExtra("KATEGORIJA"));
                                            intent.putExtra("IDKATEGORIJE", getIntent().getStringExtra("IDKATEGORIJE"));
                                            intent.putExtra("IDSETA", getIntent().getStringExtra("IDSETA"));
                                            intent.putExtra("IDDETALJNO", getIntent().getStringExtra("IDDETALJNO"));
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                            finish();
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}