package com.example.reviews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

// 마이페이지 java 파일
public class MypageActivity extends AppCompatActivity {

    String validate; // 사용자 로그인 여부
    ImageButton btnMyReview;  // 작성한 코멘트 버튼

    // 하단바 버튼
    ImageButton btnHome;
    ImageButton btnSocial;
    ImageButton btnMypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_layout);

        // 사용자 로그인 여부에 따른 마이페이지 출력
        GlobalVariable user = (GlobalVariable) getApplication();
        validate = user.getData();

        if(validate == null) { // 전역변수에 로그인한 사용자 id 값이 비어있는 경우 mypage_before_login.xml 출력
            Intent intent = new Intent(getApplicationContext(), MypageBeforeLogin.class);
            startActivity(intent);
        }
        else { // 전역변수에 로그인한 사용자 id 값이 있는 경우 mypage_layout.xml 출력
            btnMyReview = (ImageButton)findViewById(R.id.mypage_btn_comment);
            btnMyReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MyReviewActivity.class);
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
                    Toast.makeText(MypageActivity.this, "마이페이지 입니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
