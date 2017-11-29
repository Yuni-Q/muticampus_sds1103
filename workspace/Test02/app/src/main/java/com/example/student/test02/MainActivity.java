package com.example.student.test02;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnIntent = (Button) findViewById(R.id.btn_intent);
        btnIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new Intent(보내는 액티비티, 받는 액티비티);
                Log.i("yangyu test", "second intent 로그 테스트 중입니다.");
                Intent intent = new Intent
                        (MainActivity.this, SecondActivity.class);
                startActivity(intent);
                Log.i("yangyu test", "second intent 이것도 나오나?");
            }
        });

        Button btnDial = (Button) findViewById(R.id.btn_dial);
        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 암시적 인텐트는 액티비티 이름이 아니라 액션의 종류를 적음.
                Intent intent = new Intent(Intent.ACTION_DIAL);
                startActivity(intent);
            }
        });

        Button btnCall = (Button) findViewById(R.id.btn_call);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent
                (Intent.ACTION_CALL, Uri.parse("tel:01075640626"));
                startActivity(intent);
            }
        });

        Button btnInternet = (Button) findViewById(R.id.btn_internet);
        btnInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent
//                (Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"));
//                startActivity(intent);
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(takePictureIntent);
            }
        });
    }
}
