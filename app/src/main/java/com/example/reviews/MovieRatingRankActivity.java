package com.example.reviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

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

public class MovieRatingRankActivity extends AppCompatActivity {

    // DB 값 저장하여 Adapter와 연결하기 위한 ArrayList
    ArrayList<ArrayList<String>> arrayList;
    ArrayList<String> array;

    // 영화 평점순 RecyclerView
    private RecyclerView recyclerView;
    private MovieNewListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    // TOP3 영화 포스터
    ImageView ratingMovie1;
    ImageView ratingMovie2;
    ImageView ratingMovie3;

    // 하단바 버튼
    ImageButton btnHome;
    ImageButton btnSocial;
    ImageButton btnMypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_rating_rank);

        // TOP1 영화포스터에 대한 리스너 구현
        ratingMovie1 = (ImageView) findViewById(R.id.movie_rating_title1);
        ratingMovie1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });

        // TOP2 영화포스터에 대한 리스너 구현
        ratingMovie2 = (ImageView) findViewById(R.id.movie_rating_title2);
        ratingMovie2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });

        // TOP3 영화포스터에 대한 리스너 구현
        ratingMovie3 = (ImageView) findViewById(R.id.movie_rating_title3);
        ratingMovie3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });

        // movie_rating_rank.xml의 RecyclerView 위젯
        recyclerView = (RecyclerView) findViewById(R.id.movie_rating_list);
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

                        String rating = subJsonObject.getString("m_Rating");
                        array.add(rating);  // 영화 평점

                        arrayList.add(array);
                    }

                    adapter = new MovieNewListAdapter(MovieRatingRankActivity.this, arrayList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        MovieRatingRankRequest movieRatingRankRequest = new MovieRatingRankRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MovieRatingRankActivity.this);
        queue.add(movieRatingRankRequest);

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

