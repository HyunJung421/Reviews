package com.example.reviews;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieRecommendRankActivity extends AppCompatActivity {
    boolean likeState1 = false;
    boolean likeState2 = false;
    boolean likeState3 = false;

    // 영화 고유번호
    int mID;

    // DB 값 저장하여 Adapter와 연결하기 위한 ArrayList
    ArrayList<ArrayList<String>> arrayList;
    ArrayList<String> array;

    // 영화 평점순 RecyclerView
    private RecyclerView recyclerView;
    private MovieRecommendRankAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    ImageButton recommedMovie1, recommedMovie2, recommedMovie3; // Top3 영화 포스터
    TextView recomtitle1, recomtitle2, recomtitle3;      // Top3 영화 제목
    TextView recomnum1, recomnum2, recomnum3;  // Top3 영화 추천수

    // 하단바 버튼
    ImageButton btnHome;
    ImageButton btnSocial;
    ImageButton btnMypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_recommend_rank);

        // TOP3 영화 제목
        recomtitle1 = (TextView) findViewById(R.id.movie_recom_title1);
        final String title1 = recomtitle1.getText().toString(); // 첫번째 영화 제목

        recomtitle2 = (TextView) findViewById(R.id.movie_recom_title2);
        final String title2 = recomtitle1.getText().toString(); // 두번째 영화 제목

        recomtitle3 = (TextView) findViewById(R.id.movie_recom_title3);
        final String title3 = recomtitle3.getText().toString(); // 세번째 영화 제목


        // TOP1 영화포스터에 대한 리스너 구현
        recommedMovie1 = (ImageButton) findViewById(R.id.social_movie1);
        recommedMovie1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                intent.putExtra("title", title1);
                startActivity(intent); // 액티비티 띄우기
            }
        });

        // TOP2 영화포스터에 대한 리스너 구현
        recommedMovie2 = (ImageButton)findViewById(R.id.social_movie2);
        recommedMovie2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                intent.putExtra("title", title2);
                startActivity(intent); // 액티비티 띄우기
            }
        });

        // TOP3 영화포스터에 대한 리스너 구현
        recommedMovie3 = (ImageButton)findViewById(R.id.social_movie3);
        recommedMovie3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                intent.putExtra("title", title3);
                startActivity(intent); // 액티비티 띄우기
            }
        });

        // 로그인한 사용자 id(=전역변수 user) 가져오기
        final GlobalVariable user = (GlobalVariable) getApplication();
        final String userID = user.getData();

        // TOP1 영화제목에 대한 리스너 구현
        recomnum1 = (TextView)findViewById(R.id.number_recommendation1);
        recomnum1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likeState1) {
                    decrLikeCount(recomnum1);
                } else {
                    incrLikeCount(recomnum1);
                }
                likeState1 = !likeState1;
            }
        });

        // TOP2 영화제목에 대한 리스너 구현
        recomnum2 = (TextView)findViewById(R.id.number_recommendation2);
        recomnum2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likeState2) {
                    decrLikeCount(recomnum2);
                } else {
                    incrLikeCount(recomnum2);
                }
                likeState2 = !likeState2;
            }
        });

        // TOP3 영화제목에 대한 리스너 구현
        recomnum3 = (TextView)findViewById(R.id.number_recommendation3);
        recomnum3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likeState3) {
                    decrLikeCount(recomnum3);
                } else {
                    incrLikeCount(recomnum3);
                }
                likeState3 = !likeState3;
            }
        });

        // movie_recommend_rank.xml의 RecyclerView 위젯
        recyclerView = (RecyclerView) findViewById(R.id.movie_recommend_list);
        recyclerView.setHasFixedSize(true);

        // LinearLayoutManager 사용
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();


        // m_Poster, m_Title, m_Rating 3개
        // DB에 저장된 영화 정보 불러오기
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                try {

                    JSONArray jsonArray = new JSONArray(result);

                    for(int i=0; i<jsonArray.length(); i++) {
                        array = new ArrayList<>();
                        JSONObject subJsonObject = jsonArray.getJSONObject(i);

                        String poster = subJsonObject.getString("m_Poster");
                        array.add(poster);  // 영화 포스터

                        String title = subJsonObject.getString("m_Title");
                        array.add(title);  // 영화 제목

                        String reccomend = subJsonObject.getString("m_Count");
                        array.add(reccomend);  // 영화 추천수

                        arrayList.add(array);
                    }

                    adapter = new MovieRecommendRankAdapter(MovieRecommendRankActivity.this, arrayList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        MovieRecommendRankRequest movieRecommendRankRequest = new MovieRecommendRankRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MovieRecommendRankActivity.this);
        queue.add(movieRecommendRankRequest);

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

    // 좋아요 +1 메소드
    private void incrLikeCount(TextView recomm) {
        int num = Integer.parseInt(recomm.getText().toString()) + 1;
        recomm.setText(String.valueOf(num));
        // 파란따봉으로 이미지 변경
        Drawable img = getResources().getDrawable(R.drawable.drawable_left_image_customise);
        img.setBounds(0,0,60,60);
        recomm.setCompoundDrawables(img, null, null, null);
        // 좋아요 숫자 파란색으로 변경
        recomm.setTextColor(Color.parseColor("#0000E1"));
    }

    // 좋아요 -1 메소드
    public void decrLikeCount(TextView recomm) {
        int num = Integer.parseInt(recomm.getText().toString()) - 1;
        recomm.setText(String.valueOf(num));
        // 회색따봉으로 이미지 변경
        Drawable img = getResources().getDrawable(R.drawable.unlike_left_image_customise);
        img.setBounds(0,0,60,60);
        recomm.setCompoundDrawables(img, null, null, null);
        // 좋아요 숫자 회색으로 변경
        recomm.setTextColor(Color.parseColor("#999999"));
    }

    // DB에 영화 추천수 +1
    private void updateIncrCount(int movie_count, int mID, String userID){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // 영화 추천수 갱신
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) { // 영화 추천수 갱신 성공
                    } else { // 영화 추천수 갱신 실패
                        AlertDialog.Builder builder = new AlertDialog.Builder(MovieRecommendRankActivity.this);
                        AlertDialog dialog = builder.setMessage("영화 정보 업데이트 실패")
                                .setPositiveButton("확인", null)
                                .create();
                        dialog.show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        MovieRecomInsertRequest movieRecomInsertRequest = new MovieRecomInsertRequest(movie_count, mID, userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MovieRecommendRankActivity.this);
        queue.add(movieRecomInsertRequest);
    }

    // DB에 영화 추천수 -1
    private void updateDecrCount(int movie_count, int mID, String userID){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // 영화 추천수 갱신
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) { // 영화 추천수 갱신 성공
                    } else { // 영화 추천수 갱신 실패
                        AlertDialog.Builder builder = new AlertDialog.Builder(MovieRecommendRankActivity.this);
                        AlertDialog dialog = builder.setMessage("영화 정보 업데이트 실패")
                                .setPositiveButton("확인", null)
                                .create();
                        dialog.show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        MovieRecomDeleteRequest movieRecomDeleteRequest = new MovieRecomDeleteRequest(movie_count, mID, userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MovieRecommendRankActivity.this);
        queue.add(movieRecomDeleteRequest);
    }


}
