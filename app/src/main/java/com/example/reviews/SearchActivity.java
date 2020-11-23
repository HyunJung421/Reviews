package com.example.reviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

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

public class SearchActivity extends AppCompatActivity {

    //뒤로가기 버튼 클릭시 메인화면으로
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent); // 액티비티 띄우기
    }

    // 검색돋보기
    ImageView btnsearchmag1;
    EditText editSearch;

    private AlertDialog dialog;

    // 검색할 key
    String key;

    // DB 값 저장하여 Adapter와 연결하기 위한 ArrayList
    ArrayList<ArrayList<String>> arrayList;
    ArrayList<String> array;

    // 작성한 코멘트 RecyclerView
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    // 하단바 버튼
    ImageButton btnHome;
    ImageButton btnSocial;
    ImageButton btnMypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        editSearch = (EditText)findViewById(R.id.search_field);
        // search_layout.xml의 RecyclerView 위젯
        recyclerView = (RecyclerView) findViewById(R.id.search_result_list);
        recyclerView.setHasFixedSize(true);

        // LinearLayoutManager 사용
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // 돋보기 구현
        btnsearchmag1 = (ImageView)findViewById(R.id.search_magnifier1);
        btnsearchmag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = editSearch.getText().toString();
                if (key.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                    dialog = builder.setMessage("영화정보를 입력하세요")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();;
                    editSearch.requestFocus();
                }
                else {
                    key = editSearch.getText().toString();

                    // DB에서 저장된 영화목록 가져오기
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String result) {
                            try {
                                JSONArray jsonArray = new JSONArray(result);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    arrayList = new ArrayList<>();
                                    array = new ArrayList<>();
                                    JSONObject subJsonObject = jsonArray.getJSONObject(i);

                                    String poster = subJsonObject.getString("m_Poster");
                                    array.add(poster);  // 영화 포스터

                                    String title = subJsonObject.getString("m_Title");
                                    array.add(title);  // 영화 제목

                                    String year = subJsonObject.getString("m_Year");
                                    array.add(year);   // 영화 개봉연도

                                    String running = subJsonObject.getString("m_RunningTime");
                                    array.add(running);  // 영화 시간

                                    String country = subJsonObject.getString("m_Country");
                                    array.add(country);  // 영화 제작나라

                                    String genre = subJsonObject.getString("m_Genre");
                                    array.add(genre);  // 영화 장르

                                    String director = subJsonObject.getString("m_Director");
                                    array.add(director); // 감독

                                    String rating = subJsonObject.getString("m_Rating");
                                    array.add(rating);  // 영화 평점

                                    arrayList.add(array);
                                }
                                adapter = new SearchAdapter(SearchActivity.this, arrayList);
                                recyclerView.setAdapter(adapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    SearchRequest searchRequest = new SearchRequest(key, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(SearchActivity.this);
                    queue.add(searchRequest);
                }
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
                Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });
    }
}
