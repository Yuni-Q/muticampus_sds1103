package com.example.student.test07_naver;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by student on 2017-07-05.
 */

// with json
public class NaverThread2 extends Thread{
    private String keyword; // 사용자가 입력하는 검색어
    private Handler handler;

    public NaverThread2(Handler handler, String keyword){
        this.handler = handler;
        this.keyword = keyword;
    }

    @Override
    public void run() {
        String clientId = "RTuw0ym_NEZQscqMWHk6";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "jZfYS44gVx";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode(keyword, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/book?query="+ text; // json 결과
//            String apiURL = "https://openapi.naver.com/v1/search/book.xml?query="+ text; // xml 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            // br이 네이버가 주는 결과 데이터 받는 스트림
/////////////////////////////////////////////////////////////////
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            Log.i("json",response.toString());

            // 결과 json String 파싱해서 vo객체로 만드는 작업 시작
            List<BookVO> bookList = new ArrayList<>();
            JSONObject obj = new JSONObject(response.toString());

            JSONArray arr = obj.getJSONArray("items");
            for (int i = 0; i < arr.length(); i++)
            {
                String title = arr.getJSONObject(i).getString("title");
                // <b></b> 태그 지우는 작업///////////////
                Pattern pattern = Pattern.compile("<.?b>");
                Matcher matcher = pattern.matcher(title);

                if(matcher.find()==true){
                    title = matcher.replaceAll("");
                }
                /////////////////////////////////////////
                String author = arr.getJSONObject(i).getString("author");
                String pub = arr.getJSONObject(i).getString("publisher");
                String price = arr.getJSONObject(i).getString("price");
                String dis = arr.getJSONObject(i).getString("discount");

                BookVO b = new BookVO(title,author,pub,price,dis);
                bookList.add(b);
            }

            // 결과 데이터가 bookList에 다 담겼겠네요
            // 이 데이터를 화면에 그리는 작업을 부탁하기 위해
            // 메인 쓰레드의 핸들러에게 메세지 객체 보내기
            Message msg = new Message();
            msg.obj = bookList;
            handler.sendMessage(msg);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
