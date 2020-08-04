package com.example.reviews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

// 로그인 페이지 java 파일
public class LoginActivity extends AppCompatActivity {

    // 아이디, 비밀번호 입력텍스트
    EditText userID;
    EditText userPW;

    // 로그인, 회원가입 버튼
    Button btnLogin;
    Button btnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        userID = (EditText)findViewById(R.id.login_input_id);
        userPW = (EditText)findViewById(R.id.login_input_pw);

        // 로그인 버튼 등록 및 리스너 구현
        btnLogin = (Button)findViewById(R.id.login_btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userID.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                    userID.requestFocus();
                } else if(userPW.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    userPW.requestFocus();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent); // 액티비티 띄우기
                }
            }
        });

        // 회원가입 버튼 등록 및 리스너 구현
        btnJoin = (Button)findViewById(R.id.login_btn_join);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });
    }
}
