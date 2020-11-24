package com.example.reviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MypageBeforeLogin extends AppCompatActivity {

    //뒤로가기 버튼 클릭시 로그인화면으로
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent); // 액티비티 띄우기
    }

    // 로그인, 회원가입 버튼
    Button login, join;

    // 하단바 버튼
    ImageButton btnHome;
    ImageButton btnSocial;
    ImageButton btnMypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_before_login);

        // 로그인 버튼 등록 및 리스너 구현
        login = (Button)findViewById(R.id.mypage_before_login_btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });

        // 회원가입 버튼 등록 및 리스너 구현
        join = (Button)findViewById(R.id.mypage_before_login_btn_join);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });

    }
}
