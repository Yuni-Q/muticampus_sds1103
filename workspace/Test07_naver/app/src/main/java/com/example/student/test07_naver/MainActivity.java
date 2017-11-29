package com.example.student.test07_naver;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 2017-07-05.
 */

public class MainActivity extends Activity {
    private EditText editKeyword;
    private Button btnSearch;
    private ListView listViewBook;

    private BookAdapter adapter;
    private List<BookVO> bookList;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // NaverThread가 작업이 완료되면 이쪽으로 메세지를 보냄.
            // msg.obj에 bookList가 담겨있음.
            List<BookVO> threadResult = (List)msg.obj;
            bookList.clear();
            bookList.addAll(threadResult);

            // 어댑터에게 리스트뷰에 넣을 데이터가 갱신됐음을 알리기.
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editKeyword = (EditText) findViewById(R.id.edit_keyword);
        btnSearch = (Button) findViewById(R.id.btn_search);
        listViewBook = (ListView) findViewById(R.id.listview_book);
//////////////////////////////////////////////////////////////////
        bookList = new ArrayList<>();
        adapter = new BookAdapter(this, R.layout.book_item, bookList);

        listViewBook.setAdapter(adapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = editKeyword.getText().toString();
                // NaverThread에게 일시킬 시점.
                new NaverThread2(handler,keyword).start();
            }
        });
    }
}
