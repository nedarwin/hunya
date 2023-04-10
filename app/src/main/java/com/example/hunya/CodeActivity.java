package com.example.hunya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CodeActivity extends AppCompatActivity {
    String txtName;
    String code;
    EditText Code;
    Intent intent;
    Button codeB;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        txtName = getIntent().getStringExtra("name");
        Code = findViewById(R.id.code);
        codeB= findViewById(R.id.codeB);
        codeB.setOnClickListener(this::OnCode);
        code = "0";

    }

    public void OnCode(View v) {
        code = String.valueOf(Code.getText());
        checkCode();
        if(token!=null) {
            System.out.println(token);
            intent = new Intent(this,PassActivity.class);
            intent.putExtra("jwt",token);
            startActivity(intent);
        }
    }

    private void checkCode() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = RequestBody.create("", MediaType.parse("application/json"));
                    Request request = new Request.Builder()
                            .url("https://medic.madskill.ru/api/signin")
                            .post(body)
                            .addHeader("accept", "application/json")
                            .addHeader("email", txtName)
                            .addHeader("code", code)
                            .build();
                    Response response = client.newCall(request).execute();

                    String result = response.body().string();

                    try {
                        token = new JSONObject(result).getString("token");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(token!=null) {
                        Log.d("Response", token);
                    }
                    // Move to the next activity

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

}