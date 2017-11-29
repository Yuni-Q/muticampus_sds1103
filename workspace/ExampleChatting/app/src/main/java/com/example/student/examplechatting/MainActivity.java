package com.example.student.examplechatting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by student on 2017-07-03.
 */

public class MainActivity extends AppCompatActivity {
    private TextView textViewChat;
    private EditText editChat;
    private Button btnSend;

    private Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;

    private String nickname;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            // ChattingThread가 보낸 메세지 메인쓰레드가 받는부분
            String receiveMsg = (String) msg.obj;
            textViewChat.append(receiveMsg + "\n");
            textViewChat.setVerticalScrollbarPosition
                    (textViewChat.getText().length());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewChat = (TextView) findViewById(R.id.textview_chat);
        editChat = (EditText) findViewById(R.id.edit_chat);
        btnSend = (Button) findViewById(R.id.btn_send);

        textViewChat.setVerticalScrollBarEnabled(true);
        textViewChat.setMovementMethod
                (new ScrollingMovementMethod());

        showDialogNickname(); // 앱 실행되자마자 닉네임 입력상자 띄우기

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendMsg = editChat.getText() + "";
                try {
                    bw.write(sendMsg + "\n");
                    bw.flush();
                    editChat.setText("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //////////////////////////////////////////////////////////////
    public void showDialogNickname() {
        final View dialogView = getLayoutInflater().inflate
                (R.layout.dialog_nickname, null);

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        builder.setView(dialogView)
                .setTitle("닉네임 입력창")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 닉네입 입력 후 확인버튼 눌렀을 때
                        EditText editNick = (EditText)
                                dialogView.findViewById(R.id.edit_nick);

                        nickname = editNick.getText() + "";

                        if (nickname == null || nickname.length() == 0) {
                            Toast.makeText(MainActivity.this,
                                    "닉네임을 입력해주세요!", Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            // 닉네임 입력 되었으면 쓰레드 시작!
                            new ChattingThread().start();
                        }
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,
                                "취소를 눌러 앱이 종료됩니다.", Toast.LENGTH_SHORT)
                                .show();
                        dialog.cancel(); // 대화상자 종료
                        MainActivity.this.finish();// 앱 종료
                    }
                }).show();
    }

    //////////////////////////////////////////////////////////////
    class ChattingThread extends Thread {
        public ChattingThread() {

        }

        @Override
        public void run() {
            try {
                socket = new Socket
                        (InetAddress.getByName("70.12.111.130"), 1234);

                bw = new BufferedWriter(
                        new OutputStreamWriter
                                (socket.getOutputStream()));
                br = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));

                // 소켓 연결 성공이면 일단 서버에 닉네임 전송하기
                bw.write(nickname + "\n");
                bw.flush();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "서버에 문제가 발생하였습니다. 잠시후 다시 시도해주세요.",
                        Toast.LENGTH_SHORT).show();
            }

            try {
                while (true) {
                    // 서버로부터 받은 데이터를 현재 쓰레드는
                    // 화면 못건드리므로 핸들러에게 보냄.
                    String receiveMsg = br.readLine();
                    Message msg = new Message();
                    msg.obj = receiveMsg;
                    handler.sendMessage(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected void onDestroy() {
        try {
            socket.close();
            Toast.makeText(getApplicationContext(),
                    "채팅을 종료합니다.",
                    Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}

