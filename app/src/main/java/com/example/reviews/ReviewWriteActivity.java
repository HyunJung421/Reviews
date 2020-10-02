package com.example.reviews;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;

// 리뷰작성페이지 java 파일
public class ReviewWriteActivity extends AppCompatActivity {
    Bitmap bitmap;

    ImageView moviePoster; // 영화 포스터
    TextView movieTitle;  // 영화 제목
    TextView movieYear;  // 영화 연도
    TextView movieRunTime;  // 영화 상영시간
    TextView movieCountry;  // 영화 나라
    TextView movieGenre;  // 영화 장르
    Button btnWriteOk;   // 작성완료 버튼

    // 하단바 버튼
    ImageButton btnHome;
    ImageButton btnSocial;
    ImageButton btnMypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_write);

        // 화면 이동 하면서 보낸 데이터 받기 -> 영화 제목
        // 영화 제목으로 db에 저장된 영화 포스터 불러오기 위해 사용
        Intent intent = getIntent();
        final String title = intent.getExtras().getString("title");

        // 영화 정보
        moviePoster = (ImageView)findViewById(R.id.review_write_movie_poster);
        movieTitle = (TextView)findViewById(R.id.movie_info_title);
        movieYear= (TextView)findViewById(R.id.movie_info_year1);
        movieRunTime = (TextView)findViewById(R.id.movie_info_running_time1);
        movieCountry = (TextView)findViewById(R.id.movie_info_country1);
        movieGenre = (TextView)findViewById(R.id.movie_info_genre1);

        // DB에 저장된 영화 정보 불러오기
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) { // 영화 정보를 불러온 경우
                        String mTitle = jsonObject.getString("m_Title");
                        movieTitle.setText(mTitle);

                        String mYear = jsonObject.getString("m_Year");
                        movieYear.setText(mYear);

                        String mRunningTime = jsonObject.getString("m_RunningTime");
                        movieRunTime.setText(mRunningTime);

                        String mCountry = jsonObject.getString("m_Country");
                        movieCountry.setText(mCountry);

                        String mGenre = jsonObject.getString("m_Genre");
                        movieGenre.setText(mGenre);

                        String url = jsonObject.getString("m_Poster");
                        imageToBitmap(url); // 영화 포스터 지정

                    } else { // 영화 정보 불러오기 실패한 경우
                        AlertDialog.Builder builder = new AlertDialog.Builder(ReviewWriteActivity.this);
                        AlertDialog dialog = builder.setMessage("영화 정보 불러오기 실패.")
                                .setPositiveButton("확인", null)
                                .create();
                        dialog.show();;
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ReviewWriteRequest reviewWriteRequest = new ReviewWriteRequest(title, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ReviewWriteActivity.this);
        queue.add(reviewWriteRequest);



        // 작성완료 버튼 등록 및 리스너 구현
        btnWriteOk = (Button)findViewById(R.id.review_write_btn_ok);
        btnWriteOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
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
                Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });
    }

    // DB에 저장된 영화 포스터 url로 이미지 지정하기
    void imageToBitmap(String url) {
        final String murl = url;

        // 안드로이드에서 네트워크와 관련된 작업을 할 때,
        // 반드시 메인 Thread가 아닌 별도의 작업 Thread를 생성하여 작업해야 한다.
        Thread mThread = new Thread() {
            @Override
            public void run(){
                try{
                    // 서버에 올려둔 이미지 url
                    URL url = new URL(murl);

                    //web에서 이미지 가져온 후 ImageView에 지정할 Bitmap 만들기
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoInput(true);  // Server 통신에서 입력 가능한 상태로 만듦
                    conn.connect();  // 연결된 곳에 접속할 때 (connect() 호출해야 실제 통신 가능)

                    InputStream is = conn.getInputStream();  // inputStream 값 가져오기
                    bitmap = BitmapFactory.decodeStream(is);  // Bitmap으로 반환
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        mThread.start();  // 작업 Thread 실행

        try {
            // 메인 Thread는 별도의 작업을 완료할 때까지 대기한다.
            // join() 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다린다.
            // join() 메소드는 InterruptedException을 발생시킨다.
            mThread.join();

            // 작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤
            // ui 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지 지정정
            moviePoster.setImageBitmap(bitmap);
       } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
