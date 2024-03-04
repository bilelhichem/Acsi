package com.example.projetacsi;

import androidx.annotation.AnyRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registeract extends AppCompatActivity {
TextView textreg ;
    private Retrofit retrofit;
    private RetfrofitInterface retrofitInterface;
    private String BASE_URL = "http://192.168.43.234:3000";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeract);
        init();


        ///////////////
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      ////////////////////
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retrofitInterface = retrofit.create(RetfrofitInterface.class);
        Sign_Up();
        /////////////////////
        textreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new Intent(Registeract.this,loginact.class);
                startActivity(reg);
                finish();
            }
        });


        //////////////

       animateView();

    }


    public void init(){
        textreg = findViewById(R.id.signin);
    }
    public static boolean Verifier_Infos(String name, String email, String password, String confPassword, Context context) {
        if (name.isEmpty()) {
            Toast.makeText(context, "Le champ du nom ne peut pas être vide", Toast.LENGTH_SHORT).show();
            return false;
        } else if (email.isEmpty()) {
            Toast.makeText(context, "Le champ du email ne peut pas être vide", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.isEmpty()) {
            Toast.makeText(context, "Le champ du password ne peut pas être vide", Toast.LENGTH_SHORT).show();
            return false;
        } else if (confPassword.isEmpty()) {
            Toast.makeText(context, "Le champ du Confirme Password ne peut pas être vide", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!confPassword.equals(password)) {
            Toast.makeText(context, "Vérifiez votre mot de passe", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(context, "Le mot de passe doit contenir au moins 6 caractères", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }



    private void Sign_Up() {

        Button signupBtn = findViewById(R.id.register);
        final EditText nameEdit = findViewById(R.id.nameuser);
        final EditText emailEdit = findViewById(R.id.email);
        final EditText passwordEdit = findViewById(R.id.password);
        final EditText confpass = findViewById(R.id.confpass);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

    if (Verifier_Infos( nameEdit.getText().toString(),emailEdit.getText().toString(),passwordEdit.getText().toString(),confpass.getText().toString(),Registeract.this)){
        HashMap<String, String> map = new HashMap<>();
        map.put("name", nameEdit.getText().toString());
        map.put("email", emailEdit.getText().toString());
        map.put("password", passwordEdit.getText().toString());
        Call<Void> call = retrofitInterface.executeSignup(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Toast.makeText(Registeract.this,
                            "Hello in your app", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Registeract.this,Act1.class);
                    startActivity(intent);
                    finish();
                } else if (response.code() == 400) {
                    Toast.makeText(Registeract.this,
                            "User is aleardy exist", Toast.LENGTH_LONG).show();
                }else if (response.code() == 401) {
                    Toast.makeText(Registeract.this,
                            "Verifie le format de email", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Registeract.this,
                        t.toString(), Toast.LENGTH_LONG).show();
            }

        });
    } else {
        Verifier_Infos( nameEdit.getText().toString(),emailEdit.getText().toString(),passwordEdit.getText().toString(),confpass.getText().toString(),Registeract.this);
    }}});

    }
    private void animateView() {
        YoYo.with(Techniques.RubberBand.getAnimator())
                .duration(1500)
                .repeat(1)
                .onEnd(animator -> animateView()) // Répéter l'animation à la fin
                .playOn(findViewById(R.id.tec1));
    }
}