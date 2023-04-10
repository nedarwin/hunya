package com.example.hunya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GmailActivity extends AppCompatActivity {
    Button mb;
    TextView warn;
    EditText mail;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmail);
        mail = findViewById(R.id.mail);
        mb= findViewById(R.id.mailB);
        warn=findViewById(R.id.warn);
        mb.setOnClickListener(this::onMailButton);
    }
    public void onMailButton(View v){
        email=String.valueOf(mail.getText());
        if(isEmailValid(email)){
            sendCode();
            Intent intent = new Intent(this,CodeActivity.class);
            intent.putExtra("name", mail.getText().toString());
            startActivity(intent);
        }
        else{

            warn.setText("Вы ввели неверную почту");
        }
    }
    public boolean isEmailValid(String email){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void sendCode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = RequestBody.create("", MediaType.parse("application/json"));
                    Request request = new Request.Builder()
                            .url("https://medic.madskill.ru/api/sendCode")
                            .post(body)
                            .addHeader("accept", "application/json")
                            .addHeader("email", email)
                            .build();
                    Response response = client.newCall(request).execute();
                    String result = response.body().string();
                    Log.d("Response", result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}