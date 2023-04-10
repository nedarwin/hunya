package com.example.hunya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private final int[] mImageIds = {R.drawable.bottle,R.drawable.flacon,R.drawable.pilules};
    private final String[] mTexts = {"Привет","Это лучшее приложении","для охраны вашего здороавья"};
    public int imageIndex,textIndex,skipIndex;
    private Button skip;
    private TextView textView;
    private ImageView images;
    private TimerTask tg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        skipIndex=0;
        images= findViewById(R.id.images);
        skip=findViewById(R.id.skip);

        skip.setOnClickListener(this::onNext);
        textView = findViewById(R.id.helloTv);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(tg=new TimerTask() {
            @Override
            public void run() {
                imageIndex = (imageIndex + 1) % mImageIds.length;
                textIndex = (textIndex + 1) % mTexts.length;

                if(imageIndex==3)
                {
                    tg.cancel();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        images.setImageResource(mImageIds[imageIndex]);
                        textView.setText(mTexts[textIndex]);

                    }
                });
            }
        }, 0, 5000); // 5000 мс = 5 секунд
    }

    @Override
    protected void onPause() {
        super.onPause();
        skipIndex=0;
    }

    private void onNext(View v){
        skipIndex+=1;
        imageIndex = (imageIndex + 1) % mImageIds.length;
        textIndex = (textIndex + 1) % mTexts.length;
        images.setImageResource(mImageIds[imageIndex]);
        textView.setText(mTexts[textIndex]);
        if(skipIndex==2){
            skip.setText("Завершить");
        }
        if(skipIndex==3){
            Intent intent = new Intent(this, GmailActivity.class);
            startActivity(intent);
        }

    }

}