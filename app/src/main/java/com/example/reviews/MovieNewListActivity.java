package com.example.reviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

// 영화 신작 xml에 대한 java 파일
public class MovieNewListActivity extends AppCompatActivity {

    ImageButton newMovie1,newMovie2,newMovie3,newMovie4;
    Button movie_plot_more1,movie_plot_more2,movie_plot_more3,movie_plot_more4;
    private MoviePlotDialog plotDialog;  // 영화내용 다이얼로그 클래스

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_new_list);

        // 다이얼로그 객체
        plotDialog = new MoviePlotDialog(MovieNewListActivity.this);

        // 첫번째 영화 포스터 등록 및 리스너 구현
        newMovie1 = (ImageButton) findViewById(R.id.movie_newlist1);
        newMovie1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });
        // 첫번째 영화 더보기 클릭 리스너 구현
        movie_plot_more1 = (Button) findViewById(R.id.movie_info_btn_plot_more1);
        movie_plot_more1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plotDialog.show();
            }
        });
        // 두번째 영화 포스터 등록 및 리스너 구현
        newMovie2 = (ImageButton) findViewById(R.id.movie_newlist2);
        newMovie2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });
        // 두번째 영화 더보기 클릭 리스너 구현
        movie_plot_more2 = (Button) findViewById(R.id.movie_info_btn_plot_more2);
        movie_plot_more2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plotDialog.show();
            }
        });
        // 세번째 영화 포스터 등록 및 리스너 구현
        newMovie3 = (ImageButton) findViewById(R.id.movie_newlist3);
        newMovie3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });
        // 세번째 영화 더보기 클릭 리스너 구현
        movie_plot_more3 = (Button) findViewById(R.id.movie_info_btn_plot_more3);
        movie_plot_more3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plotDialog.show();
            }
        });
        // 네번째 영화 포스터 등록 및 리스너 구현
        newMovie4 = (ImageButton) findViewById(R.id.movie_newlist4);
        newMovie4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });
        // 네번째 영화 더보기 클릭 리스너 구현
        movie_plot_more4 = (Button) findViewById(R.id.movie_info_btn_plot_more4);
        movie_plot_more4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plotDialog.show();
            }
        });
    }
}
