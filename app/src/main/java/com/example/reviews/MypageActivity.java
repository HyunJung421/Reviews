package com.example.reviews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

// 마이페이지 java 파일
public class MypageActivity extends AppCompatActivity {

    ImageButton btnMyReview;  // 작성한 코멘트 버튼
    // 하단바 버튼
    ImageButton btnHome;
    ImageButton btnSocial;
    ImageButton btnMypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        btnMyReview = (ImageButton)findViewById(R.id.my_review);
        btnMyReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyReviewActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });
        /*
        // 하단바 home 버튼 등록 및 리스너 구현
        btnHome = (ImageButton)findViewById(R.id.home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });

        // 하단바 social 버튼 등록 및 리스너 구현
        btnSocial = (ImageButton)findViewById(R.id.social);
        btnSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SocialActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });

        // 하단바 mypage 버튼 등록 및 리스너 구현
        btnMypage = (ImageButton)findViewById(R.id.mypage);
        btnMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MypageActivity.this, "마이페이지 입니다.", Toast.LENGTH_SHORT).show();
            }
        }); */
    }
}
