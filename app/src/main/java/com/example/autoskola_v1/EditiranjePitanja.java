package com.example.autoskola_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditiranjePitanja extends AppCompatActivity implements AdminAdapter.OnPitanjeClickListener{

    FirebaseAuth fAuth;
    FirebaseStorage fStorage;
    StorageReference storageReference;

    private RecyclerView RecyclerView;
    private RecyclerView.Adapter RecyclerViewAdapter;
    private RecyclerView.LayoutManager RecyclerViewLM;

    TextView txtNaslov;
    Button btnSljedecaStranica;
    String pageToken = "0";
    String brojPitanja = "";
    List<Pitanje> ListaPitanja;

    Ucitavanje ucitavanjeSadrzaja = new Ucitavanje(EditiranjePitanja.this);

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_editiranje_pitanja);

        fAuth = FirebaseAuth.getInstance();
        fStorage = FirebaseStorage.getInstance();
        storageReference = fStorage.getReference();

        RecyclerView = findViewById(R.id.RecyclerViewListaPitanja);
        txtNaslov = findViewById(R.id.txtNaslov);
        btnSljedecaStranica = findViewById(R.id.btnSljedecaStranica);

        txtNaslov.setText(getIntent().getStringExtra("KATEGORIJA") + " - " + getIntent().getStringExtra("ADMIN"));


        btnSljedecaStranica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ucitavanjeSadrzaja.pokreniUcitavanje();
                ucitavanje(pageToken, false, "", "");
            }
        });

        ucitavanjeSadrzaja.pokreniUcitavanje();
        ucitavanje(pageToken, false, "", "");

    }

    @Override
    public void onPitanjeClickListener(final int position) {
        if(Objects.equals(getIntent().getStringExtra("ADMIN"), "Izmjene")){
            Intent intent = new Intent(EditiranjePitanja.this, AdminPitanje.class);
            intent.putExtra("ADMIN", getIntent().getStringExtra("ADMIN"));
            intent.putExtra("KATEGORIJA", getIntent().getStringExtra("KATEGORIJA"));
            intent.putExtra("IDKATEGORIJE", getIntent().getStringExtra("IDKATEGORIJE"));
            intent.putExtra("IDSETA", getIntent().getStringExtra("IDSETA"));
            intent.putExtra("IDDETALJNO", getIntent().getStringExtra("IDDETALJNO"));
            intent.putExtra("NAME", ListaPitanja.get(position).getName());
            intent.putExtra("PITANJE", ListaPitanja.get(position).getPitanje());
            intent.putExtra("ODGOVOR1", ListaPitanja.get(position).getOdgovor1());
            intent.putExtra("ODGOVOR2", ListaPitanja.get(position).getOdgovor2());
            intent.putExtra("ODGOVOR3", ListaPitanja.get(position).getOdgovor3());
            intent.putExtra("TOCANODGOVOR", String.valueOf(ListaPitanja.get(position).getTocanOdgovor()));
            intent.putExtra("SLIKA", ListaPitanja.get(position).getSlika());
            startActivity(intent);
            finish();
        }else{

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            ucitavanjeSadrzaja.pokreniUcitavanje();
                            ucitavanjeBroja(ListaPitanja.get(position).getName());
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Jeste li sigurni da želite obrisati ovo pitanje?").setPositiveButton("Da", dialogClickListener)
                    .setNegativeButton("Ne", dialogClickListener).show();
        }
    }

    private void ucitavanjeBroja(final String getName) {
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
                            Toast.makeText(EditiranjePitanja.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                final String myResponse = Objects.requireNonNull(response.body()).string();

                                EditiranjePitanja.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            brojPitanja = new JSONObject(myResponse).getJSONObject("fields").getJSONObject("BrojPitanja").getString("integerValue");
                                            updateanjeBroja(brojPitanja, getName);

                                        }catch (JSONException err){
                                            Toast.makeText(EditiranjePitanja.this, err.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void updateanjeBroja(final String broj, final String getName) {
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
                            "      \"integerValue\": \"" + (temp - 1) + "\"\n" +
                            "    }\n" +
                            "  }\n" +
                            "}";

                    RequestBody requestBody = RequestBody.create(json, JSON);

                    OkHttpClient client = new OkHttpClient();

                    final Request request = new Request.Builder().addHeader("Authorization", auth).addHeader("Accept", "application/json").addHeader("Content-Type", "application/json").patch(requestBody).url(url).build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Toast.makeText(EditiranjePitanja.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                EditiranjePitanja.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ucitavanje("0", true, getName, broj);
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    private void ucitavanje(final String token, boolean Delete, final String Ime, final String brojPitanja) {

        if(!Delete){
            fAuth.getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                @Override
                public void onComplete(@NonNull Task<GetTokenResult> task) {
                    if (task.isSuccessful()) {

                        OkHttpClient client = new OkHttpClient();

                        String Kategorija = getIntent().getStringExtra("IDKATEGORIJE");
                        String IDseta = getIntent().getStringExtra("IDSETA");

                        String url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/"+ Kategorija +"/"+ IDseta;
                        if(!getIntent().getStringExtra("IDDETALJNO").equals("0")){
                            url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/"+ Kategorija +"/"+ IDseta + "/"+ getIntent().getStringExtra("IDDETALJNO") +"/Pitanja";
                        }

                        if(!token.equals("0")){

                            url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/"+ Kategorija +"/"+ IDseta + "?pageToken=" + token;
                            if(!getIntent().getStringExtra("IDDETALJNO").equals("0")){
                                url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/"+ Kategorija +"/"+ IDseta + "/"+ getIntent().getStringExtra("IDDETALJNO") +"/Pitanja?pageToken=" + token;
                            }
                        }


                        String auth = "Bearer " + task.getResult().getToken();


                        Request request = new Request.Builder().addHeader("Authorization", auth).url(url).build();

                        Response responses = null;
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Toast.makeText(EditiranjePitanja.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                                if(response.isSuccessful()){
                                    final String myResponse = Objects.requireNonNull(response.body()).string();

                                    EditiranjePitanja.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                ListaPitanja = new ArrayList<>();
                                                JSONObject jsonObject = new JSONObject(myResponse);
                                                JSONArray jsonArray = jsonObject.getJSONArray("documents");

                                                if(jsonArray.length() <= 19){
                                                    pageToken = "0";
                                                }else{
                                                    pageToken = jsonObject.getString("nextPageToken");
                                                }


                                                for (int i = 0; i < jsonArray.length(); i++){
                                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                                    JSONObject jsonObject3 = jsonObject2.getJSONObject("fields");
                                                    JSONObject jsonObject4 = jsonObject3.getJSONObject("Pitanje");
                                                    JSONObject jsonObject5 = jsonObject3.getJSONObject("Odgovor1");
                                                    JSONObject jsonObject6 = jsonObject3.getJSONObject("Odgovor2");
                                                    JSONObject jsonObject7 = jsonObject3.getJSONObject("Odgovor3");
                                                    JSONObject jsonObject8 = jsonObject3.getJSONObject("TocanOdgovor");
                                                    JSONObject jsonObject9 = jsonObject3.getJSONObject("Slika");

                                                    String Pitanje = jsonObject4.getString("stringValue");
                                                    String Odgovor1 = jsonObject5.getString("stringValue");
                                                    String Odgovor2 = jsonObject6.getString("stringValue");
                                                    String Odgovor3 = jsonObject7.getString("stringValue");
                                                    long TocanOdgovor = Long.parseLong(jsonObject8.getString("integerValue"));
                                                    String Slika = jsonObject9.getString("stringValue");
                                                    String name = jsonObject2.getString("name");

                                                    name = name.substring(name.lastIndexOf('/') + 1);

                                                    Pitanje pitanje = new Pitanje(Pitanje, Odgovor1, Odgovor2, Odgovor3, TocanOdgovor, Slika, name);
                                                    ListaPitanja.add(pitanje);
                                                }

                                                RecyclerViewLM = new LinearLayoutManager(EditiranjePitanja.this);
                                                RecyclerViewAdapter = new AdminAdapter(ListaPitanja, EditiranjePitanja.this);
                                                RecyclerView.setLayoutManager(RecyclerViewLM);
                                                RecyclerView.setAdapter(RecyclerViewAdapter);

                                            } catch (JSONException e) {
                                                Toast.makeText(EditiranjePitanja.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });
            ucitavanjeSadrzaja.makniUcitavanje();
        }

        if(Delete){


            fAuth.getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                @Override
                public void onComplete(@NonNull Task<GetTokenResult> task) {
                    if (task.isSuccessful()) {
                        String Kategorija = getIntent().getStringExtra("IDKATEGORIJE");
                        String IDseta = getIntent().getStringExtra("IDSETA");

                        String url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/" + Kategorija + "/" + IDseta + "/Pitanje" + brojPitanja;
                        if(!getIntent().getStringExtra("IDDETALJNO").equals("0")){
                            url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/"+ Kategorija +"/"+ IDseta + "/"+ getIntent().getStringExtra("IDDETALJNO") +"/Pitanja/Pitanje" + brojPitanja;
                        }

                        String auth = "Bearer " + task.getResult().getToken();


                        OkHttpClient client2 = new OkHttpClient();

                        Request request2 = new Request.Builder().addHeader("Authorization", auth).addHeader("Accept", "application/json").url(url).build();
                        client2.newCall(request2).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Toast.makeText(EditiranjePitanja.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                if(response.isSuccessful()){
                                    final String myResponse = Objects.requireNonNull(response.body()).string();
                                    EditiranjePitanja.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                JSONObject jsonObject = new JSONObject(myResponse);
                                                JSONObject jsonObject2 = jsonObject.getJSONObject("fields");
                                                JSONObject jsonObject3 = jsonObject2.getJSONObject("Pitanje");
                                                JSONObject jsonObject4 = jsonObject2.getJSONObject("Odgovor1");
                                                JSONObject jsonObject5 = jsonObject2.getJSONObject("Odgovor2");
                                                JSONObject jsonObject6 = jsonObject2.getJSONObject("Odgovor3");
                                                JSONObject jsonObject7 = jsonObject2.getJSONObject("TocanOdgovor");
                                                Long temp = Long.parseLong(jsonObject7.getString("integerValue"));
                                                JSONObject jsonObject8 = jsonObject2.getJSONObject("Slika");

                                                premjestanjePodataka(new Pitanje(jsonObject3.getString("stringValue"), jsonObject4.getString("stringValue"),
                                                        jsonObject5.getString("stringValue"), jsonObject6.getString("stringValue"), temp , jsonObject8.getString("stringValue")), Ime, brojPitanja);

                                            } catch (JSONException e) {
                                                Toast.makeText(EditiranjePitanja.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void premjestanjePodataka(final Pitanje pitanjeTemp, final String getName, final String brojPitanja) {
        fAuth.getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if (task.isSuccessful()) {

                    String Kategorija = getIntent().getStringExtra("IDKATEGORIJE");
                    String IDseta = getIntent().getStringExtra("IDSETA");

                    String url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/" + Kategorija + "/" + IDseta + "/" + getName;
                    if(!getIntent().getStringExtra("IDDETALJNO").equals("0")){
                        url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/"+ Kategorija +"/"+ IDseta + "/"+ getIntent().getStringExtra("IDDETALJNO") +"/Pitanja/" + getName;
                    }

                    String auth = "Bearer " + task.getResult().getToken();

                    String json = "{\n" +
                            "  \"fields\": {\n" +
                            "    \"Pitanje\": {\n" +
                            "      \"stringValue\": \""+ pitanjeTemp.getPitanje() +"\"\n" +
                            "    },\n" +
                            "    \"Odgovor1\": {\n" +
                            "      \"stringValue\": \""+ pitanjeTemp.getOdgovor1() +"\"\n" +
                            "    },\n" +
                            "    \"Odgovor2\": {\n" +
                            "      \"stringValue\": \""+ pitanjeTemp.getOdgovor2() +"\"\n" +
                            "    },\n" +
                            "    \"Odgovor3\": {\n" +
                            "      \"stringValue\": \""+ pitanjeTemp.getOdgovor3() +"\"\n" +
                            "    },\n" +
                            "    \"TocanOdgovor\": {\n" +
                            "      \"integerValue\": "+ pitanjeTemp.getTocanOdgovor() +"\n" +
                            "    },\n" +
                            "    \"Slika\": {\n" +
                            "      \"stringValue\": \""+ pitanjeTemp.getSlika() +"\"\n" +
                            "    }\n" +
                            "  }\n" +
                            "}";



                    RequestBody requestBody = RequestBody.create(json, JSON);

                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder().addHeader("Authorization", auth).addHeader("Accept", "application/json").addHeader("Content-Type", "application/json").patch(requestBody).url(url).build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Toast.makeText(EditiranjePitanja.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                            if(response.isSuccessful()){
                                EditiranjePitanja.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        brisanjePitanja(brojPitanja, pitanjeTemp.getSlika());
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    private void brisanjePitanja(final String brojPitanja, String Slika) {
        fAuth.getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if (task.isSuccessful()) {
                    String Kategorija = getIntent().getStringExtra("IDKATEGORIJE");
                    String IDseta = getIntent().getStringExtra("IDSETA");

                    String url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/" + Kategorija + "/" + IDseta + "/Pitanje" + brojPitanja + "?currentDocument.exists=true";
                    if(!getIntent().getStringExtra("IDDETALJNO").equals("0")){
                        url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/"+ Kategorija +"/"+ IDseta + "/"+ getIntent().getStringExtra("IDDETALJNO") +"/Pitanja/Pitanje" + brojPitanja + "?currentDocument.exists=true";
                    }

                    String auth = "Bearer " + task.getResult().getToken();


                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder().addHeader("Authorization", auth).addHeader("Accept", "application/json").delete().url(url).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Toast.makeText(EditiranjePitanja.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                EditiranjePitanja.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(EditiranjePitanja.this, "Uspjesno obrisano!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

        if(!Slika.equals("")){
            StorageReference photoRef = fStorage.getReferenceFromUrl(Slika);
            photoRef.delete();
        }

        ucitavanje("0", false, "", "");
    }
}