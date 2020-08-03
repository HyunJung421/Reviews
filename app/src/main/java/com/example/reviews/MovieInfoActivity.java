package com.example.reviews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

// 영화상세페이지 java 파일
public class MovieInfoActivity extends AppCompatActivity {

    Button btnPlotMore;    // 영화 내용 더보기 버튼
    Button btnReviewMore;  // 영화 리뷰 더보기 버튼
    Button btnReviewWrite;  // 영화 리뷰 작성 버튼
    private MoviePlotDialog plotDialog;  // 영화내용 다이얼로그 클래스

    // 하단바 버튼
    ImageButton btnHome;
    ImageButton btnSocial;
    ImageButton btnMypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_info);

        // 다이얼로그 객체
        plotDialog = new MoviePlotDialog(MovieInfoActivity.this);

        // 내용 더보기 버튼 등록 및 리스너 구현
        btnPlotMore = (Button)findViewById(R.id.plot_more);
        btnPlotMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plotDialog.show();
            }
        });

        // 더보기 버튼 등록 및 리스너 구현
        btnReviewMore = (Button)findViewById(R.id.review_more);
        btnReviewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReviewOrderActivity.class);
                startActivity(intent); // 액티비티 띄우기

            }
        });

        // 작성하기 버튼 등록 및 리스너 구현
        btnReviewWrite = (Button)findViewById(R.id.review_write);
        btnReviewWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReviewWriteActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });

        // 하단바 underbar_home 버튼 등록 및 리스너 구현
        btnHome = (ImageButton)findViewById(R.id.home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });

        // 하단바 underbar_social 버튼 등록 및 리스너 구현
        btnSocial = (ImageButton)findViewById(R.id.social);
        btnSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SocialActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });

        // 하단바 underbar_mypage 버튼 등록 및 리스너 구현
        btnMypage = (ImageButton)findViewById(R.id.mypage);
        btnMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });
    }

}
