package com.example.student.test05;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by student on 2017-07-04.
 */

public class MyAdapter extends ArrayAdapter<ContactVO> {
    private Context context; // inflate에 필요한 Activity 주소
    private int layoutId; // 만들 아이템xml 레이아웃
    private List<ContactVO> contactList; // 데이터묶음

    public MyAdapter(Context context, int resource, List<ContactVO> objects) {
        super(context, resource, objects);

        this.context = context;
        this.layoutId = resource;
        this.contactList = objects;
    }

    @Override
    public int getCount() {
        // adapter가 아이템 몇개 만들어야 하는지 알려주는 용도
        return contactList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // 어댑터 클래스에서 가장 중요한 메소드!!!!
        // 데이터를 아이템으로 만들어서 리스트로 보내는 역할!!!

        // inflater는 xml을 한꺼번에 팽창시키는 도구
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View wholeItem = inflater.inflate(layoutId, null); // 팽창!

        TextView textViewName = (TextView)
                wholeItem.findViewById(R.id.text_name);
        TextView textViewNumber = (TextView)
                wholeItem.findViewById(R.id.text_number);
        Button btnCall = (Button)
                wholeItem.findViewById(R.id.btn_call);
        Button btnSms = (Button)
                wholeItem.findViewById(R.id.btn_sms);

        // 데이터 묶음에서 연락처 하나 꺼내기
        final ContactVO contactVO = contactList.get(position);

        textViewName.setText(contactVO.getName());
        textViewNumber.setText(contactVO.getNumber());

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + contactVO.getNumber()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                context.startActivity(intent); // 전화걸기는 permission필요
            }
        });
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText
                    (context,"문자??",Toast.LENGTH_SHORT).show();
            }
        });

        return wholeItem;
    }
}
