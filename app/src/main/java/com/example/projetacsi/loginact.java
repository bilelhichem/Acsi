package com.example.projetacsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class loginact extends AppCompatActivity {
    TextView textreg ;
    private Retrofit retrofit;
    private RetfrofitInterface retrofitInterface;
    private String BASE_URL = "http://192.168.43.234:3000";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginact);
        init();
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retrofitInterface = retrofit.create(RetfrofitInterface.class);
     Login();
        textreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new Intent(loginact.this,Registeract.class);
                startActivity(reg);
                finish();
            }
        });
    }
    public void init(){
        textreg = findViewById(R.id.textreg);
    }



    private void Login() {

        Button signupBtn = findViewById(R.id.signin);
        final EditText emailEdit = findViewById(R.id.email);
        final EditText passwordEdit = findViewById(R.id.password);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("hany hena");
                HashMap<String, String> map = new HashMap<>();

                map.put("email", emailEdit.getText().toString());
                map.put("password", passwordEdit.getText().toString());
               if (emailEdit.getText().toString().isEmpty()) {
                    Toast.makeText(loginact.this, "Le champ du email ne peut pas être vide", Toast.LENGTH_SHORT).show();
                }else if (passwordEdit.getText().toString().isEmpty()) {
                    Toast.makeText(loginact.this, "Le champ du password ne peut pas être vide", Toast.LENGTH_SHORT).show();
                } else if (passwordEdit.getText().toString().length() < 6) {
                    Toast.makeText(loginact.this, "Le mot de passe doit contenir au moins 6 caractères", Toast.LENGTH_SHORT).show();
                } else {
                    Call<Void> call = retrofitInterface.executelogin(map);


                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.code() == 200) {
                                Toast.makeText(loginact.this,
                                        "Hello in your app", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(loginact.this,Act1.class);
                                startActivity(intent);
                                finish();
                            } else if (response.code() == 400) {
                                Toast.makeText(loginact.this,
                                        "User not found", Toast.LENGTH_LONG).show();
                            }else if (response.code() == 401) {
                                Toast.makeText(loginact.this,
                                        "Verifie le format de email", Toast.LENGTH_LONG).show();
                            }
                            else if (response.code() == 402) {
                                Toast.makeText(loginact.this,
                                        "Verifier your password", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(loginact.this,
                                    t.toString(), Toast.LENGTH_LONG).show();
                        }

                    });
                }









            }
        });

    }

}