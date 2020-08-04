package com.example.reviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

// 메인페이지 java 파일
public class MainActivity extends AppCompatActivity {

    // 하단바 버튼
    ImageButton btnHome;
    ImageButton btnSocial;
    ImageButton btnMypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost1 = (TabHost)findViewById(R.id.tabHost1);
        tabHost1.setup();

        // 첫번째 탭
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("movie_rating_ranking");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("영화평점순위");
        tabHost1.addTab(ts1);

        // 두번째 탭
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("movie_rating_ranking");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("영화추천순위");
        tabHost1.addTab(ts2);

        // 세번째 탭
        TabHost.TabSpec ts3 = tabHost1.newTabSpec("movie_rating_ranking");
        ts3.setContent(R.id.content3);
        ts3.setIndicator("영화신작");
        tabHost1.addTab(ts3);

        // 하단바 underbar_home 버튼 등록 및 리스너 구현
        btnHome = (ImageButton)findViewById(R.id.underbar_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "메인페이지 입니다.", Toast.LENGTH_SHORT).show();
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
