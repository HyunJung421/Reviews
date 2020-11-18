package com.example.reviews;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
    LinearLayout review_photo_layout;  // 리뷰 첨부사진 레이아웃
    ImageView review_photo;   // 리뷰 첨부사진


    public ReviewDetailDialog(@NonNull Context context, ArrayList<String> items) {
        super(context);
        setContentView(R.layout.mypage_review_detail);

        // 다이얼로그의 너비와 높이 설정
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(this.getWindow().getAttributes());  // 여기서 설정한 값을 그대로 다이얼로그에 넣겠다는 의미
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(params);

        review_back = (ImageButton)findViewById(R.id.window_close_btn);    // 뒤로가기 버튼
        movie_title = (TextView)findViewById(R.id.revdetail_movie_title);  // 영화 제목
        movie_title.setText(items.get(2));
        movie_year = (TextView)findViewById(R.id.revdetail_movie_year);  // 영화 개봉연도
        movie_year.setText(items.get(3));
        movie_running = (TextView)findViewById(R.id.revdetail_movie_runningTime);  // 영화 시간
        movie_running.setText(items.get(4));
        movie_country = (TextView)findViewById(R.id.revdetail_movie_country);  // 영화 나라
        movie_country.setText(items.get(5));
        movie_genre = (TextView)findViewById(R.id.revdetail_movie_genre);  // 영화 장르
        movie_genre.setText(items.get(6));
        user_nick = (TextView)findViewById(R.id.revdetail_userID);  // 사용자 이름
        user_nick.setText(items.get(0));
        movie_rating = (TextView)findViewById(R.id.revdetail_movie_rating);  // 영화 평점
        movie_rating.setText(items.get(9));
        movie_recommend = (TextView)findViewById(R.id.revdetail_movie_recommend);  // 영화 추천수
        movie_recommend.setText(items.get(7));
        review_content = (TextView)findViewById(R.id.revdetail_comment);  // 영화 리뷰글
        review_content.setText(items.get(8));
        review_date = (TextView)findViewById(R.id.revdetail_date);  // 영화 리뷰 작성일자
        review_date.setText(items.get(10));
        review_photo = (ImageView)findViewById(R.id.revdetail_photo);
        review_photo_layout = (LinearLayout)findViewById(R.id.revdetail_photo_layout);
        //if(items.get(11) == null) {
            review_photo_layout.setVisibility(View.GONE);
        //}


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
