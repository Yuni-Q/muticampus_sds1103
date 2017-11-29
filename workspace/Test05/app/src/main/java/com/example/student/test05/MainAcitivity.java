package com.example.student.test05;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 2017-07-04.
 */

public class MainAcitivity extends Activity {
    private ListView listView;
    private Button btnAddContact;

    private List<ContactVO> contactList; // 연락처 데이터 묶음
    private MyAdapter adapter; // 리스트뷰 채우는 어댑터

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // ListView 들어있음

        listView = (ListView) findViewById(R.id.my_listview);
        btnAddContact = (Button) findViewById(R.id.btn_add_contact);

        contactList = new ArrayList<>();

        // 리스트뷰에 보여줄 샘플 데이터 만들기
        for(int i=0; i<20; i++){
            ContactVO c = new ContactVO("양유"+i, "0107564062"+i);
            contactList.add(c);
        }

        //[어댑터]:데이터를 item으로 만들어서 listview에 넣는작업 반복
        // 어댑터에게 줘야하는 것들
        // 1. MainActivity 정보
        // 2. 데이터 묶음(배열, 리스트, 등등)
        // 3. 만들 아이템 레이아웃

        adapter = new MyAdapter
                (this, R.layout.contact_item, contactList);

        // 리스트뷰에 adapter 꽂아놓기
        listView.setAdapter(adapter);

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 연락처 추가 다이얼로그 띄우기
                final Dialog dialog = new Dialog(MainAcitivity.this);
                dialog.setContentView(R.layout.add_dialog); // inflate

                final EditText editName =
                        dialog.findViewById(R.id.edit_dialog_name);
                final EditText editPhone =
                        dialog.findViewById(R.id.edit_dialog_phone);
                Button btnDialogOk =
                        dialog.findViewById(R.id.btn_dialog_ok);
                Button btnDialogCancel =
                        dialog.findViewById(R.id.btn_dialog_cancel);

                btnDialogOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ContactVO contactVO = new ContactVO();
                        contactVO.setName(editName.getText().toString());
                        contactVO.setNumber(editPhone.getText().toString());

                        contactList.add(contactVO);

                    // 데이터가 추가됐으니 아이템 더만들어서 리스트뷰에 넣어라.
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });

                btnDialogCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
    }
}
