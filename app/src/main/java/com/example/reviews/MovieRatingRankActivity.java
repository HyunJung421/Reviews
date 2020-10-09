package com.example.reviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MovieRatingRankActivity extends AppCompatActivity {
    ImageButton ratingMovie1, ratingMovie2, ratingMovie3, ratingMovie4, ratingMovie5, ratingMovie6; //영화 포스터

    // 하단바 버튼
    ImageButton btnHome;
    ImageButton btnSocial;
    ImageButton btnMypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_rating_rank);

        // TOP1 영화포스터에 대한 리스너 구현
        ratingMovie1 = (ImageButton) findViewById(R.id.social_movie1);
        ratingMovie1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });
        // TOP2 영화포스터에 대한 리스너 구현
        ratingMovie2 = (ImageButton)findViewById(R.id.social_movie2);
        ratingMovie2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });
        // TOP3 영화포스터에 대한 리스너 구현
        ratingMovie3 = (ImageButton)findViewById(R.id.social_movie3);
        ratingMovie3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });
        // TOP4 영화포스터에 대한 리스너 구현
        ratingMovie4 = (ImageButton)findViewById(R.id.movie_grade1);
        ratingMovie4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });
        // TOP5 영화포스터에 대한 리스너 구현
        ratingMovie5 = (ImageButton)findViewById(R.id.movie_grade2);
        ratingMovie5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });
        // TOP6 영화포스터에 대한 리스너 구현
        ratingMovie6 = (ImageButton)findViewById(R.id.movie_grade3);
        ratingMovie6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });


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

