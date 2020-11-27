package com.example.reviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieRecommendRankActivity extends AppCompatActivity {

    // DB 값 저장하여 Adapter와 연결하기 위한 ArrayList
    ArrayList<ArrayList<String>> arrayList;
    ArrayList<String> array;

    // 영화 평점순 RecyclerView
    private RecyclerView recyclerView;
    private MovieRecommendRankAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    ImageButton rocommedMovie1, rocommedMovie2, rocommedMovie3; //영화 포스터

    // 하단바 버튼
    ImageButton btnHome;
    ImageButton btnSocial;
    ImageButton btnMypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_recommend_rank);

        // TOP1 영화포스터에 대한 리스너 구현
        rocommedMovie1 = (ImageButton) findViewById(R.id.social_movie1);
        rocommedMovie1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });

        // TOP2 영화포스터에 대한 리스너 구현
        rocommedMovie2 = (ImageButton)findViewById(R.id.social_movie2);
        rocommedMovie2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });

        // TOP3 영화포스터에 대한 리스너 구현
        rocommedMovie3 = (ImageButton)findViewById(R.id.social_movie3);
        rocommedMovie3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });

        // movie_recommend_rank.xml의 RecyclerView 위젯
        recyclerView = (RecyclerView) findViewById(R.id.movie_recommend_list);
        recyclerView.setHasFixedSize(true);

        // LinearLayoutManager 사용
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();


        // m_Poster, m_Title, m_Rating 3개
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

                        String reccomend = subJsonObject.getString("m_Count");
                        array.add(reccomend);  // 영화 추천수

                        arrayList.add(array);
                    }

                    adapter = new MovieRecommendRankAdapter(MovieRecommendRankActivity.this, arrayList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        MovieRecommendRankRequest movieRecommendRankRequest = new MovieRecommendRankRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MovieRecommendRankActivity.this);
        queue.add(movieRecommendRankRequest);

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
