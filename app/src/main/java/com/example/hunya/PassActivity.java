package com.example.hunya;

import static com.example.hunya.CryptoUtils.decrypt;
import static com.example.hunya.CryptoUtils.encrypt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class PassActivity extends AppCompatActivity {
    EditText pass;
    Button createPass,skip2;
    Boolean created;
    private String password;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);
        pass=findViewById(R.id.pass);
        createPass=findViewById(R.id.createPass);
        skip2=findViewById(R.id.skip2);
        createPass.setOnClickListener(this::onPass);
        skip2.setOnClickListener(this::onSkip);
        Intent intent = getIntent();
        token = intent.getStringExtra("jwt");

    }
    private static final String SECRET_KEY = "mysecretkey12345";
    private static final String INIT_VECTOR = "initialvector123";


    public void onSkip(View v){
        created=false;
        System.out.println(token);
        Intent intent = new Intent(this,CardCreate.class);
        intent.putExtra("jwt",token);
        startActivity(intent);

    }
    public void onPass(View v){
        created=true;
        password= String.valueOf(pass.getText());

        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);

// получение редактора SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

// шифрование пароля

        String encryptedPassword = encrypt(password);

// сохранение зашифрованного пароля в SharedPreferences
        editor.putString("password", encryptedPassword);
        editor.apply();
        System.out.println(encryptedPassword);
        System.out.println(decrypt(encryptedPassword));
        System.out.println(token);
        Intent intent = new Intent(this,CardCreate.class);
        intent.putExtra("jwt",token);
        startActivity(intent);
    }

}