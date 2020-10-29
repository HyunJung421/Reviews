package com.example.reviews;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Network;
import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// 영화상세페이지 java 파일
public class MovieInfoActivity extends AppCompatActivity {
    Bitmap bitmap;

    // 영화 정보
    ImageView mPoster;
    TextView mTitle;
    TextView mYear;
    TextView mRunningTime;
    TextView mCountry;
    TextView mGenre;
    TextView mDirector;
    TextView mRating;
    TextView mPlot;

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

        // 화면 이동 하면서 보낸 데이터 받기 -> 영화 제목
        // 영화 제목으로 db에 저장된 영화 정보 불러오기 위해 사용
        Intent intent = getIntent();
        final String title = intent.getExtras().getString("title");

        mPoster = (ImageView)findViewById(R.id.movie_info_poster);
        mTitle = (TextView)findViewById(R.id.movie_info_title);
        mYear = (TextView)findViewById(R.id.movie_info_year1);
        mRunningTime = (TextView)findViewById(R.id.movie_info_running_time1);
        mCountry = (TextView)findViewById(R.id.movie_info_country1);
        mGenre = (TextView)findViewById(R.id.movie_info_genre1);
        mDirector = (TextView)findViewById(R.id.movie_info_director1);
        mRating = (TextView)findViewById(R.id.movie_info_rating1);
        mPlot = (TextView)findViewById(R.id.movie_info_plot);

        // DB에 저장된 영화 정보 불러오기
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) { // 영화 정보를 불러온 경우
                        String moTitle = jsonObject.getString("m_Title");
                        mTitle.setText(moTitle);

                        String moYear = jsonObject.getString("m_Year");
                        mYear.setText(moYear);

                        String moRunningTime = jsonObject.getString("m_RunningTime");
                        mRunningTime.setText(moRunningTime);

                        String moCountry = jsonObject.getString("m_Country");
                        mCountry.setText(moCountry);

                        String moGenre = jsonObject.getString("m_Genre");
                        mGenre.setText(moGenre);

                        String moDirector = jsonObject.getString("m_Director");
                        mDirector.setText(moDirector);

                        String moRating = jsonObject.getString("m_Rating");
                        mRating.setText(moRating);

                        String moPlot = jsonObject.getString("m_Plot");
                        // 다이얼로그 객체 생성
                        dialog(moTitle, moPlot);
                        mPlot.setText(moPlot);

                        String url = jsonObject.getString("m_Poster");
                        urlImageToBitmap(url); // 영화 포스터 지정

                    } else { // 영화 정보 불러오기 실패한 경우
                        AlertDialog.Builder builder = new AlertDialog.Builder(MovieInfoActivity.this);
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
        MovieInfoRequest movieInfoRequest = new MovieInfoRequest(title, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MovieInfoActivity.this);
        queue.add(movieInfoRequest);

        // 내용 더보기 버튼 등록 및 리스너 구현
        btnPlotMore = (Button)findViewById(R.id.movie_info_btn_plot_more);
        btnPlotMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plotDialog.show();
            }
        });

        // 더보기 버튼 등록 및 리스너 구현
        btnReviewMore = (Button)findViewById(R.id.movie_info_btn_review_more);
        btnReviewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReviewOrderActivity.class);
                startActivity(intent); // 액티비티 띄우기

            }
        });

        // 작성하기 버튼 등록 및 리스너 구현
        btnReviewWrite = (Button)findViewById(R.id.moive_info_btn_review_write);
        btnReviewWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReviewWriteActivity.class);
                intent.putExtra("title", title);
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
    void urlImageToBitmap(String url) {
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
            // ui 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지 지정
            mPoster.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void dialog(String moTitle, String moPlot) {
        // 다이얼로그 객체
        plotDialog = new MoviePlotDialog(MovieInfoActivity.this, moTitle, moPlot);
    }
}
