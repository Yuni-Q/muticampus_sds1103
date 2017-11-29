package com.example.student.test01;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by student on 2017-06-30.
 */

public class MainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // xml 표지 붙이기

        Button btnHello = (Button) findViewById(R.id.btn_hello);

        btnHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // btnHello가 클릭됐을 때 하고싶은 일을 적는 부분
                Toast.makeText
                  (MainActivity.this,"눌렸다!!",Toast.LENGTH_LONG).show();
            }
        });
    }
}
