package com.example.reviews;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

// 마이페이지 -> 작성한코멘트 목록 페이지 java 파일
public class MyReviewActivity extends AppCompatActivity {

    String userID; // 로그인한 사용자
    // DB 값
    ArrayList<ArrayList<String>> arrayList;
    ArrayList<String> array;
    // 작성한 코멘트 RecyclerView
    private RecyclerView recyclerView;
    private MyReviewAdapter adapter;

    // 작성한코멘트의 리뷰글
    LinearLayout layReview;

    // 영화 포스터
    ImageView poster;

    private ReviewDetailDialog reviewDialog;  // 리뷰글 다이얼로그 클래스

    // 하단바 버튼
    ImageButton btnHome;
    ImageButton btnSocial;
    ImageButton btnMypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_review);

        // 사용자 로그인 여부에 따른 마이페이지 출력
        GlobalVariable user = (GlobalVariable) getApplication();
        userID = user.getData();

        // mypage_review.xml의 RecyclerView 위젯
        recyclerView = (RecyclerView) findViewById(R.id.mypage_review_list);
        // LinearLayoutManager 사용
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();
        // DB에서 리뷰목록 가져오기
        // userID, m_poster, m_title, m_info(4개), 추천수, review_content
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    for(int i=0; i<jsonArray.length(); i++) {
                        array = new ArrayList<>();
                        JSONObject subJsonObject = jsonArray.getJSONObject(i);

                        array.add(userID);
                        String poster = subJsonObject.getString("m_Title");
                        array.add(poster);
                        String title = subJsonObject.getString("m_Title");
                        array.add(title);
                        String year = subJsonObject.getString("m_Year");
                        array.add(year);
                        String running = subJsonObject.getString("m_RunningTime");
                        array.add(running);
                        String country = subJsonObject.getString("m_Country");
                        array.add(country);
                        String genre = subJsonObject.getString("m_Genre");
                        array.add(genre);
                        String count = subJsonObject.getString("m_Count");
                        array.add(count);
                        String revContent = subJsonObject.getString("revContent");
                        array.add(revContent);

                        arrayList.add(array);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        MyReviewRequest myReviewRequest = new MyReviewRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MyReviewActivity.this);
        queue.add(myReviewRequest);

        adapter = new MyReviewAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);



        // 다이얼로그 객체
        reviewDialog = new ReviewDetailDialog(MyReviewActivity.this);
        /*
        // 리뷰 클릭시 리뷰 상세페이지로 넘어감
        layReview = (LinearLayout) findViewById(R.id.my_review1);
        layReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewDialog.show();
            }
        });

        // 영화 포스터 클릭시 영화정보페이지로 넘어감
        poster = (ImageView)findViewById(R.id.movie_poster);
        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                startActivity(intent);
            }
        });
         */

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
