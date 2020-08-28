package com.example.reviews;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

// 로그인 페이지 java 파일
public class LoginActivity extends AppCompatActivity {

    // 자동로그인 체크박스
    private CheckBox login_auto_ch;

    // 아이디, 비밀번호 입력텍스트
    private EditText login_input_id, login_input_pw;
    private AlertDialog dialog;

    // 로그인, 회원가입 버튼
    private Button btnLogin, btnJoin, btnFindId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        // 자동로그인을 위한 editor
        final SharedPreferences loginInfo = getSharedPreferences("user", 0); // "user"라는 파일 생성
        final SharedPreferences.Editor editor = loginInfo.edit(); // Editor 연결

        // 자동로그인 여부 확인
        if(loginInfo.contains("id") && loginInfo.contains("pw")) {  // 자동로그인일 경우
            // EditText에 현재 입력되어 있는 값을 get(가져온다)해온다.
            String id = loginInfo.getString("id", "");
            String pw = loginInfo.getString("pw", "");

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) { // 로그인에 성공한 경우
                            String userID = jsonObject.getString("userID");
                            String userPass = jsonObject.getString("userPassword");
                            Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();

                            // 전역변수에 사용자 id 저장
                            GlobalVariable user = (GlobalVariable) getApplication();
                            user.setData(userID);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("userID", userID);
                            intent.putExtra("userPass", userPass);
                            startActivity(intent);
                        } else { // 로그인에 실패한 경우
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            dialog = builder.setMessage("아이디나 비밀번호가 일치하지 않습니다.")
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
            LoginRequest loginRequest = new LoginRequest(id, pw, responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
        }
        else {  // 자동로그인이 아닐 경우
            login_input_id = (EditText)findViewById(R.id.login_input_id);
            login_input_pw = (EditText)findViewById(R.id.login_input_pw);
            login_auto_ch = (CheckBox)findViewById(R.id.login_check_auto);

            // 로그인 버튼 등록 및 리스너 구현
            btnLogin = (Button)findViewById(R.id.login_btn_login);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // EditText에 현재 입력되어 있는 값을 get(가져온다)해온다.
                    final String id = login_input_id.getText().toString();
                    final String pw = login_input_pw.getText().toString();

                    if(id.equals("")) {
                        Toast.makeText(LoginActivity.this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                        login_input_id.requestFocus();
                    } else if(pw.equals("")){
                        Toast.makeText(LoginActivity.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                        login_input_pw.requestFocus();
                    }
                    else {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    if (success) { // 로그인에 성공한 경우
                                        String userID = jsonObject.getString("userID");
                                        String userPass = jsonObject.getString("userPassword");
                                        Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();

                                        // 전역변수에 사용자 id 저장
                                        GlobalVariable user = (GlobalVariable) getApplication();
                                        user.setData(userID);

                                        // 내장메모리에 사용자정보 저장
                                        if(login_auto_ch.isChecked()) {
                                            editor.putString("id", id);  // "user" 파일에 로그인한 id 저장
                                            editor.putString("pw", pw);  // "user" 파일에 로그인한 pw 저장
                                            editor.commit();  // 저장
                                        }

                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.putExtra("userID", userID);
                                        intent.putExtra("userPass", userPass);
                                        startActivity(intent);
                                    } else { // 로그인에 실패한 경우
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                        dialog = builder.setMessage("아이디나 비밀번호가 일치하지 않습니다.")
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
                        LoginRequest loginRequest = new LoginRequest(id, pw, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                        queue.add(loginRequest);
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

            btnFindId = (Button)findViewById(R.id.login_btn_findid);
            btnFindId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LoginIdFindActivity.class);
                    startActivity(intent); // 액티비티 띄우기
                }
            });
        }
    }
}
