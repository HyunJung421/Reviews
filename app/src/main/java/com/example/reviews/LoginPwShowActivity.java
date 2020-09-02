package com.example.reviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// 비밀번호 찾고 비밀번호 출력하는 java 파일
public class LoginPwShowActivity extends AppCompatActivity {

    Button btnlogin;
    TextView showUserPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_password_find2);

        // LoginPwFindActivity에서 보낸 userPassword 받기
        Intent intent = getIntent();
        String userPw = intent.getExtras().getString("userPassword");

        // 전달받은 userID 출력
        showUserPw = (TextView)findViewById(R.id.display_user_pw);
        showUserPw.setText(userPw);

        // 로그인 하는 xml로 이동하기
        btnlogin = (Button) findViewById(R.id.mypage_before_login_btn_login);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
