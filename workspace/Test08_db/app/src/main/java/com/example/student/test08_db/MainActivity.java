package com.example.student.test08_db;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 2017-07-06.
 */

public class MainActivity extends Activity {
    private EditText editWord;
    private Button btnAdd;
    private Button btnShow;
    private ListView listviewWord;

    // 리스트뷰에 아이템 나열하는 어댑터와 단어묶음.
    private ArrayAdapter<String> adapter;
    private List<String> wordList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editWord = (EditText) findViewById(R.id.edit_word);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnShow = (Button) findViewById(R.id.btn_show);
        listviewWord = (ListView) findViewById(R.id.listview_word);

        wordList = new ArrayList<>();
        adapter = new ArrayAdapter<String>
            (this, android.R.layout.simple_list_item_1, wordList);

        listviewWord.setAdapter(adapter);

        final MyDBHelper helper = new MyDBHelper(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputWord = editWord.getText().toString();

                // 입력된 단어를 데이터베이스에 추가
                helper.insert(inputWord);
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 데이터베이스의 단어들을 select 해서
                // 어댑터에게 단어 목록을 넘겨야 함.
                List<String> selectedWordList = helper.selectAll();

                wordList.clear();
                wordList.addAll(selectedWordList);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
