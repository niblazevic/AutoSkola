package com.example.autoskola_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.annotations.NotNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditiranjePitanja extends AppCompatActivity {

    FirebaseAuth fAuth;

    TextView textView14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_editiranje_pitanja);

        fAuth = FirebaseAuth.getInstance();

        textView14 = findViewById(R.id.textView14);

        fAuth.getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if(task.isSuccessful()){

                    OkHttpClient client = new OkHttpClient();

                    String Kategorija = getIntent().getStringExtra("IDKATEGORIJE");
                    String IDseta = getIntent().getStringExtra("IDSETA");

                    String url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/" + Kategorija + "/" + IDseta;
                    String auth = "Bearer " + task.getResult().getToken();

                    Request request = new Request.Builder().header("Authorization", auth).url(url).build();

                    client.newCall(request).enqueue(new  Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                final String myResponse = Objects.requireNonNull(response.body()).string();

                                EditiranjePitanja.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        textView14.setText(myResponse);
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