package com.example.reviews;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

// 리뷰작성페이지 java 파일
public class ReviewWriteActivity extends AppCompatActivity {
    Bitmap bitmap;

    // 영화 정보관련
    ImageView moviePoster; // 영화 포스터
    TextView movieTitle;  // 영화 제목
    TextView movieYear;  // 영화 연도
    TextView movieRunTime;  // 영화 상영시간
    TextView movieCountry;  // 영화 나라
    TextView movieGenre;  // 영화 장르

    EditText reviewContent;  // 리뷰 작성 내용
    Button btnWriteOk;   // 작성완료 버튼
    Button btnPhotoAttach;  // 사진 첨부 버튼

    // 사진첨부 RecyclerView 가로형
    private RecyclerView listview;
    private ReviewWriteAdapter adapter;

    final int PICTURE_REQUEST_CODE = 100;

    // 하단바 버튼
    ImageButton btnHome;
    ImageButton btnSocial;
    ImageButton btnMypage;

    private AlertDialog dialog;

    // 영화 고유번호
    int mID;

    // 사진 uri
    ArrayList<String> array;

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
            public void onResponse(String result) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    // 영화 정보 출력
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) { // 영화 정보를 불러온 경우
                        mID = jsonObject.getInt("m_ID");

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
                        urlImageToBitmap(url); // 영화 포스터 지정

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
        MovieInfoRequest movieInfoRequest = new MovieInfoRequest(title, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ReviewWriteActivity.this);
        queue.add(movieInfoRequest);


        // 사진첨부하기 버튼 등록 및 리스너 구현
        btnPhotoAttach = (Button)findViewById(R.id.review_write_btn_add);
        btnPhotoAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // 사진을 여러개 선택할 수 있도록 함
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICTURE_REQUEST_CODE);
                // onActivityResult(int requestCode, int resultCode, Intent data)가 호출됨
            }
        });

        // 로그인한 사용자 id(=전역변수 user) 가져오기
        GlobalVariable user = (GlobalVariable) getApplication();
        final String userID = user.getData();

        // 리뷰 작성 내용
        reviewContent = (EditText)findViewById(R.id.review_write_input);

        // 작성완료 버튼 등록 및 리스너 구현
        btnWriteOk = (Button)findViewById(R.id.review_write_btn_ok);
        btnWriteOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 뒤로가기 방지
                onBackPressed();

                // 리뷰 작성 내용 확인
                if(reviewContent.getText().toString().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReviewWriteActivity.this);
                    dialog = builder.setMessage("리뷰를 작성하세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    reviewContent.requestFocus();
                }

                else {
                    // 리뷰 내용 가져오기
                    final String revContent = reviewContent.getText().toString();
                    // 현재 시간 가져오기
                    long now = System.currentTimeMillis();
                    // Date 생성하기
                    Date date = new Date(now);
                    // 가져오고 싶은 형식을 가져오기
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월dd일");
                    final String revDate = sdf.format(date);

                    // 첨부사진
                    final String revPhoto = "";
                    // 리뷰추천수(형식상)
                    final int revRecom = 0;

                    // 리뷰 DB에 저장하기
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) { // 리뷰저장에 성공한 경우
                                    Toast.makeText(getApplicationContext(), "리뷰 저장에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                                    intent.putExtra("title", title);
                                    startActivity(intent);
                                } else { // 리뷰저장에 실패한 경우
                                    Toast.makeText(getApplicationContext(), "리뷰 저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    // 서버로 Volley를 이용해서 요청을 함
                    ReviewSaveRequest reviewSaveRequest = new ReviewSaveRequest(mID, userID, revDate, revContent, revPhoto, revRecom, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ReviewWriteActivity.this);
                    queue.add(reviewSaveRequest);
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
            moviePoster.setImageBitmap(bitmap);
       } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 사진첨부기능
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICTURE_REQUEST_CODE) {
            Log.i("result", String.valueOf(resultCode));

            if (resultCode == RESULT_OK) {
                // 첨부사진을 보여줄 ListView
                listview = findViewById(R.id.review_write_attach_list);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                listview.setLayoutManager(layoutManager);

                // uri형 배열리스트
                ArrayList<Uri> itemList = new ArrayList<>();
                // db 저장용 uri 배열리스트
                array  = new ArrayList<>();

                // 다중 선택을 지원하지 않는 기기에서는 getClipData()가 없음 => getData()로 접근해야 함
                if(data.getClipData() == null){
                    // 다중 선택을 지원하지 않기 때문에 한개씩 선택가능
                    Uri uri = data.getData();
                    Log.i("1.simgle choice", String.valueOf(uri));
                    itemList.add(uri);
                    array.add(String.valueOf(uri));
                }
                else { // 다중 선택을 지원하는 기기
                    ClipData clipData = data.getClipData();
                    Log.i("clipdata", String.valueOf(clipData.getItemCount()));

                    if(clipData.getItemCount() > 5) {
                        Toast.makeText(ReviewWriteActivity.this, "사진은 5장까지 선택 가능합니다.", Toast.LENGTH_LONG).show();
                    }
                    else if(clipData.getItemCount()==1){ // 다중 선택에서 하나만 선택했을 경우
                        Uri urione = clipData.getItemAt(0).getUri();
                        Log.i("2.clipdata one choice", String.valueOf(urione));
                        itemList.add(urione);
                        array.add(String.valueOf(urione));
                    }
                    else if(clipData.getItemCount() > 1 && clipData.getItemCount() < 6) { // 다중 선택한 경우
                        for(int i=0; i < clipData.getItemCount(); i++) {
                            Uri uris = clipData.getItemAt(i).getUri();
                            Log.i("3.clipdata multi choice", String.valueOf(uris));
                            itemList.add(uris);
                            array.add(String.valueOf(uris));
                        }
                    }
                    else {
                        Toast.makeText(ReviewWriteActivity.this, "사진 선택을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                }

                adapter = new ReviewWriteAdapter(this, itemList, onClickItem);
                listview.setAdapter(adapter);
            }
        }
    }
    
    private View.OnClickListener onClickItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = (String) v.getTag();
            Toast.makeText(ReviewWriteActivity.this, str, Toast.LENGTH_SHORT).show();
        }
    };

    /*public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSTION_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }*/
}
