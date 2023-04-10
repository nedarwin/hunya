package com.example.hunya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CardCreate extends AppCompatActivity {
    String token;
    Button createCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_create);
        Intent intent = getIntent();
        token = intent.getStringExtra("jwt");

        createCard=findViewById(R.id.createCard);
        createCard.setOnClickListener(this::createCard);
    }
    private void createCard(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();


                    RequestBody body = RequestBody.create("{\n  \"id\": 1,\n  \"firstname\": \"1\",\n  \"lastname\": \"1\",\n  \"middlename\": \"1\",\n  \"bith\": \"1\",\n  \"pol\": \"Мужской\",\n  \"image\": \"1\"\n}",  MediaType.parse("application/json"));
                    Request request = new Request.Builder()
                            .url("https://medic.madskill.ru/api/createProfile")
                            .post(body)
                            .addHeader("accept", "application/json")
                            .addHeader("Authorization", "Bearer "+token)
                            .addHeader("Content-Type", "application/json")
                            .build();
                    System.out.println("Bearer "+token);
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Log.d("MyApp", "Запрос успешно отправлен: " + response.body().string());
                    } else {
                        Log.e("MyApp", "Ошибка при отправке запроса: " + response.code() + " - " + response.message());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}