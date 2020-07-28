package com.example.reviews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

// 영화상세페이지 java 파일
public class MovieInfoActivity extends AppCompatActivity {

    Button btnPlotMore;
    Button btnReviewMore;
    Button btnReviewWrite;
    private MoviePlotDialog plotDialog;

    public static final int re_more = 1001; // 리뷰더보기 페이지를 띄우기 위한 요청코드(상수)
    public static final int re_write = 1002; // 리뷰작성 페이지를 띄우기 위한 요청코드(상수)
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
                startActivityForResult(intent, re_more); // 액티비티 띄우기

            }
        });

        // 작성하기 버튼 등록 및 리스너 구현
        btnReviewWrite = (Button)findViewById(R.id.review_write);
        btnReviewWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReviewWriteActivity.class);
                startActivityForResult(intent, re_write); // 액티비티 띄우기
            }
        });
    }

}
