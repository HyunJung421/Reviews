package com.example.reviews;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// 리뷰 더보기 페이지 java 파일
public class ReviewOrderActivity extends AppCompatActivity {

    // DB 값 저장하여 Adapter와 연결하기 위한 ArrayList
    ArrayList<ArrayList<String>> arrayList;
    ArrayList<String> array;

    // MovieInfo 페이지 리뷰목록 RecyclerView
    private RecyclerView recyclerView;
    private MovieReviewAdapter reviewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // 하단바 버튼
    ImageButton btnHome;
    ImageButton btnSocial;
    ImageButton btnMypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_review_more);

        // 화면 이동 하면서 보낸 데이터 받기 -> 영화 제목
        // 영화 제목으로 db에 저장된 데이터 불러오기 위해 사용
        Intent intent = getIntent();
        final String title = intent.getExtras().getString("title");

        // 추천순,최신순 스피너
        Spinner spinner = (Spinner) findViewById(R.id.order_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.order_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // movie_review_more.xml의 RecyclerView 위젯
        recyclerView = (RecyclerView) findViewById(R.id.movie_review_list2);
        recyclerView.setHasFixedSize(true);

        // LinearLayoutManager 사용
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();

        // DB에 저장된 영화 정보 불러오기
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    // 해당영화의 리뷰 출력
                    for(int i=1; i<jsonArray.length(); i++) {
                        array = new ArrayList<>();
                        JSONObject subJsonObject = jsonArray.getJSONObject(i);

                        String userID = subJsonObject.getString("userID");
                        array.add(userID);  // 사용자 아이디
                        String revRecom = subJsonObject.getString("revRecom");
                        array.add(revRecom);  // 리뷰글 추천수
                        String revContent = subJsonObject.getString("revContent");
                        array.add(revContent);  // 영화 리뷰글

                        arrayList.add(array);
                    }

                    reviewAdapter = new MovieReviewAdapter(ReviewOrderActivity.this, arrayList);
                    recyclerView.setAdapter(reviewAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        MovieInfoRequest movieInfoRequest = new MovieInfoRequest(title, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ReviewOrderActivity.this);
        queue.add(movieInfoRequest);

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
