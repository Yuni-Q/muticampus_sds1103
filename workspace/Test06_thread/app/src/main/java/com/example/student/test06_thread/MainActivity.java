package com.example.student.test06_thread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by student on 2017-07-05.
 */

public class MainActivity extends Activity {
    private TextView textCount;
    private Button btnCount;
    private int count = 0;

// [최종 해결책]
// 메인 쓰레드 말고 다른 쓰레드에게 어떤 작업을 시키되
// 화면에 뭔가 그려져야 할 때는 메인 쓰레드가 메세지 받아서 처리해주기
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int receiveCount = (Integer)msg.obj;
            textCount.setText(receiveCount+"");
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textCount = (TextView) findViewById(R.id.text_count);
        btnCount = (Button) findViewById(R.id.btn_count);

        btnCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                textCount.setText(count+"");
            }
        });

        Thread t = new CountThread();
        t.start();

        // main 쓰레드가 sleep 하면 화면 그리는건 어떻게 하나....
        // 만약 버튼 누르거나 하면 눌리는거 그려줘야 하는데....
        // 문제가 있네 .......[문제1]
//        while(true) {
//            try {
//                count++;
//                Thread.sleep(5000);
//                textCount.setText(count + "");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    class CountThread extends Thread{
        @Override
        public void run() {
            // Thread가 해야 하는 작업 명세서 작성 부분
            for(int i=0; i<20; i++){
                try {
                    Thread.sleep(3000);
                    count++;
//                    textCount.setText(count+"");
                // [문제2]
                // 안드로이드에서는 화면 그리는건 main쓰레드만 해야함!!
                // 다른 쓰레드가 감히 화면에 나타나는 뷰를 건드림???

                // 직접 뷰 건드리지 않고 핸들러한테 메세지 보내기
                    Message msg = new Message();
                    msg.obj = count;
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
