package com.example.reviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MovieRecommendRankActivity extends AppCompatActivity {

    ImageButton rocommedMovie1, rocommedMovie2, rocommedMovie3, rocommedMovie4, rocommedMovie5, rocommedMovie6; //영화 포스터

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
        // TOP4 영화포스터에 대한 리스너 구현
        rocommedMovie4 = (ImageButton)findViewById(R.id.movie_grade1);
        rocommedMovie4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });
        // TOP5 영화포스터에 대한 리스너 구현
        rocommedMovie5 = (ImageButton)findViewById(R.id.movie_grade2);
        rocommedMovie5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });
        // TOP6 영화포스터에 대한 리스너 구현
        rocommedMovie6 = (ImageButton)findViewById(R.id.movie_grade3);
        rocommedMovie6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });
    }
}