package com.example.reviews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

// 소셜페이지 java 파일
public class SocialActivity extends AppCompatActivity {

    // 추천 영화 제목
    TextView movieTitle1;
    TextView movieTitle2;
    TextView movieTitle3;

    // 소셜인 추천수
    TextView social_recommendation1,social_recommendation2,social_recommendation3,social_recommendation4;

    // 추천 영화 포스터
    ImageButton socialMovie1;
    ImageButton socialMovie2;
    ImageButton socialMovie3;

    // 하단바 버튼
    ImageButton btnHome;
    ImageButton btnSocial;
    ImageButton btnMypage;

    // 좋아요 여부 저장
    boolean likeStste = false;
    // 좋아요 증가치
    int likeCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_layout);

        // 추천 영화 제목
        movieTitle1 = (TextView)findViewById(R.id.movie_info_title1);
        final String title1 = movieTitle1.getText().toString(); // 첫번째 영화 제목

        movieTitle2 = (TextView)findViewById(R.id.movie_info_title2);
        final String title2 = movieTitle2.getText().toString(); // 두번째 영화 제목

        movieTitle3 = (TextView)findViewById(R.id.movie_info_title3);
        final String title3 = movieTitle3.getText().toString(); // 세번째 영화 제목

        // 추천 첫번째 영화 포스터 등록 및 리스너 구현
        socialMovie1 = (ImageButton)findViewById(R.id.social_movie1);
        socialMovie1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                intent.putExtra("title", title1); // 영화 제목 전달
                startActivity(intent); // 액티비티 띄우기
            }
        });

        socialMovie2 = (ImageButton)findViewById(R.id.social_movie2);
        socialMovie2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                intent.putExtra("title", title2); // 영화 제목 전달
                startActivity(intent);
            }
        });

        socialMovie3 = (ImageButton)findViewById(R.id.social_movie3);
        socialMovie3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                intent.putExtra("title", title3); // 영화 제목 전달
                startActivity(intent);
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
                Toast.makeText(SocialActivity.this, "소셜페이지 입니다.", Toast.LENGTH_SHORT).show();
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

        // 소셜인 추천 버튼 등록 및 리스너 구현
        social_recommendation1 = (TextView)findViewById(R.id.social_recommendation1);
        social_recommendation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likeStste) {
                    decrLikeCount(social_recommendation1);
                } else {
                    incrLikeCount(social_recommendation1);
                }
                likeStste = !likeStste;
            }
        });

        social_recommendation2 = (TextView)findViewById(R.id.social_recommendation2);
        social_recommendation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likeStste) {
                    decrLikeCount(social_recommendation2);
                } else {
                    incrLikeCount(social_recommendation2);
                }
                likeStste = !likeStste;
            }
        });

        social_recommendation3 = (TextView)findViewById(R.id.social_recommendation3);
        social_recommendation3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likeStste) {
                    decrLikeCount(social_recommendation3);
                } else {
                    incrLikeCount(social_recommendation3);
                }
                likeStste = !likeStste;
            }
        });

        social_recommendation4 = (TextView)findViewById(R.id.social_recommendation4);
        social_recommendation4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likeStste) {
                    decrLikeCount(social_recommendation4);
                } else {
                    incrLikeCount(social_recommendation4);
                }
                likeStste = !likeStste;
            }
        });
    }

    // 좋아요 +1 메소드
    private void incrLikeCount(TextView recomm) {
        likeCount++;
        // 파란따봉으로 이미지 변경
        Drawable img = getResources().getDrawable(R.drawable.drawable_left_image_customise);
        img.setBounds(0,0,60,60);
        recomm.setCompoundDrawables(img, null, null, null);
        // 좋아요 숫자 파란색으로 변경
        recomm.setTextColor(Color.parseColor("#0000E1"));
    }

    // 좋아요 -1 메소드
    public void decrLikeCount(TextView recomm) {
        likeCount--;
        // 회색따봉으로 이미지 변경
        Drawable img = getResources().getDrawable(R.drawable.unlike_left_image_customise);
        img.setBounds(0,0,60,60);
        recomm.setCompoundDrawables(img, null, null, null);
        // 좋아요 숫자 회색으로 변경
        recomm.setTextColor(Color.parseColor("#999999"));
    }
}
