package com.example.reviews;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

// 영화 신작 xml에 대한 java 파일
public class MovieNewListActivity extends AppCompatActivity {

    // DB 값
    ArrayList<ArrayList<String>> arrayList;
    ArrayList<String> array;

    // 영화 신작 RecyclerView
    private RecyclerView recyclerView;
    private MovieNewListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    // 하단바 버튼
    ImageButton btnHome;
    ImageButton btnSocial;
    ImageButton btnMypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_new_layout);

        // movie_new_layout.xml의 RecyclerView 위젯
        recyclerView = (RecyclerView) findViewById(R.id.movie_new_list);
        recyclerView.setHasFixedSize(true);

        // LinearLayoutManager 사용
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();

        // DB에서 리뷰목록 가져오기
        // m_Poster, m_Title, m_Year, m_Running, m_Country, m_Genre, m_Director, m_Rating, m_Plot 9개

        // DB에 저장된 영화 정보 불러오기
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                try {

                    JSONArray jsonArray = new JSONArray(result);

                    for(int i=0; i<jsonArray.length(); i++) {
                        array = new ArrayList<>();
                        JSONObject subJsonObject = jsonArray.getJSONObject(i);

                        String poster = subJsonObject.getString("m_Poster");
                        array.add(poster);  // 영화 포스터

                        String title = subJsonObject.getString("m_Title");
                        array.add(title);  // 영화 제목

                        String year = subJsonObject.getString("m_Year");
                        array.add(year);   // 영화 개봉연도

                        String running = subJsonObject.getString("m_RunningTime");
                        array.add(running);  // 영화 시간

                        String country = subJsonObject.getString("m_Country");
                        array.add(country);  // 영화 제작나라

                        String genre = subJsonObject.getString("m_Genre");
                        array.add(genre);  // 영화 장르

                        String director = subJsonObject.getString("m_Director");
                        array.add(director);  // 영화 감독

                        String rating = subJsonObject.getString("m_Rating");
                        array.add(rating);  // 영화 평점

                        String plot = subJsonObject.getString("m_Plot");
                        array.add(plot);  // 영화 내용

                        arrayList.add(array);
                    }

                    adapter = new MovieNewListAdapter(MovieNewListActivity.this, arrayList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        MovieNewListRequest movieNewListRequest = new MovieNewListRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MovieNewListActivity.this);
        queue.add(movieNewListRequest);

        // 하단바 underbar_home 버튼 등록 및 리스너 구현
        btnHome = (ImageButton)findViewById(R.id.underbar_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });

        // 하단바 underbar_social 버튼 등록 및 리스너 구현
        btnSocial = (ImageButton)findViewById(R.id.underbar_social);
        btnSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SocialActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });

        // 하단바 underbar_mypage 버튼 등록 및 리스너 구현
        btnMypage = (ImageButton)findViewById(R.id.underbar_mypage);
        btnMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });

    }
}
