package com.example.reviews;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

// 마이페이지 -> 작성한코멘트 목록 -> 작성한코멘트 상세페이지 java 파일
public class ReviewDetailDialog extends Dialog {

    // 창닫기 버튼
    private ImageButton review_back;
    TextView movie_title;     // 영화 제목
    TextView movie_year;      // 영화 개봉연도
    TextView movie_running;   // 영화 시간
    TextView movie_country;   // 영화 제작나라
    TextView movie_genre;     // 영화 장르
    TextView user_nick;       // 사용자 이름
    TextView movie_rating;    // 영화 평점
    TextView review_content;  // 리뷰내용
    ImageView photo;          // 첨부 이미지
    TextView movie_recommend; // 영화 추천수
    TextView review_date;     // 리뷰 작성일자


    public ReviewDetailDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.mypage_review_detail);

        // 다이얼로그의 너비와 높이 설정
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(this.getWindow().getAttributes());  // 여기서 설정한 값을 그대로 다이얼로그에 넣겠다는 의미
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(params);

        review_back = (ImageButton)findViewById(R.id.window_close_btn);
        movie_title = (TextView)findViewById(R.id.revdetail_movie_title);
        movie_year = (TextView)findViewById(R.id.movie_year);
        movie_running = (TextView)findViewById(R.id.movie_running_time);
        movie_country = (TextView)findViewById(R.id.movie_country);
        movie_genre = (TextView)findViewById(R.id.movie_genre);
        user_nick = (TextView)findViewById(R.id.user_id);
        movie_rating = (TextView)findViewById(R.id.revdetail_movie_rating);
        movie_recommend = (TextView)findViewById(R.id.movie_recommend);
        review_content = (TextView)findViewById(R.id.review_content);


        // 창닫기 버튼 클릭시
        review_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 다이얼로그 창 닫기
                dismiss();
            }
        });
    }
}
