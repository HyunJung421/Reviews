package com.example.reviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//아이디 찾고 아이디 출력하는 java 파일
public class LoginIdShowActivity extends AppCompatActivity {

    Button btnlogin, btnfindpw;
    TextView showUseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_id_find2);

        // LoginIdFindActivity에서 보낸 userID 받기
        Intent intent = getIntent();
        String userID = intent.getExtras().getString("userID");

        // 전달받은 userID 출력
        showUseId = (TextView)findViewById(R.id.display_user_id);
        showUseId.setText(userID);

        // 로그인 하는 xml로 이동하기
        btnlogin = (Button)findViewById(R.id.mypage_before_login_btn_login);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        // 비밀번호 찾는 xml로 이동하기
        btnfindpw = (Button)findViewById(R.id.mypage_before_login_btn_findpw);
        btnfindpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginPwFindActivity.class);
                startActivity(intent);
            }
        });
    }
}
