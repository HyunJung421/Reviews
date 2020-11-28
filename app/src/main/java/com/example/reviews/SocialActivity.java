package com.example.reviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// 소셜페이지 java 파일
public class SocialActivity extends AppCompatActivity {

    // DB 값 저장하여 Adapter와 연결하기 위한 ArrayList
    ArrayList<ArrayList<String>> arrayList;
    ArrayList<String> array;

    // 추천 영화 RecyclerView
    private RecyclerView recyclerView;
    private SocialAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    // 하단바 버튼
    ImageButton btnHome;
    ImageButton btnSocial;
    ImageButton btnMypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_layout);

        // social_layout.xml의 RecyclerView 위젯
        recyclerView = (RecyclerView) findViewById(R.id.social_list);
        recyclerView.setHasFixedSize(true);

        // LinearLayoutManager 사용 (가로 RecyclerView)
        layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();

        // m_Poster, m_Title 2개
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

                        arrayList.add(array);
                    }

                    adapter = new SocialAdapter(SocialActivity.this, arrayList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SocialRequest socialRequest = new SocialRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(SocialActivity.this);
        queue.add(socialRequest);


        // 하단바 underbar_home 버튼 등록 및 리스너 구현
        btnHome = (ImageButton) findViewById(R.id.underbar_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });

        // 하단바 underbar_social 버튼 등록 및 리스너 구현
        btnSocial = (ImageButton) findViewById(R.id.underbar_social);
        btnSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SocialActivity.this, "소셜페이지 입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // 하단바 underbar_mypage 버튼 등록 및 리스너 구현
        btnMypage = (ImageButton) findViewById(R.id.underbar_mypage);
        btnMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });
    }
}