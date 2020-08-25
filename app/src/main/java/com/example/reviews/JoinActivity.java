package com.example.reviews;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

// 회원가입 페이지 java 파일
public class JoinActivity extends AppCompatActivity {

    private EditText join_input_id, join_input_pw, join_input_pw_submit, join_input_name, join_input_phone;
    private Button join_btn_join, validateButton;
    private AlertDialog dialog;
    private boolean id_validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_layout);

        join_input_id = findViewById(R.id.join_input_id);
        join_input_pw = findViewById(R.id.join_input_pw);
        join_input_pw_submit = findViewById(R.id.join_input_pw_submit);
        join_input_name = findViewById(R.id.join_input_name);
        join_input_phone = findViewById(R.id.join_input_phone);

        // id 중복체크 버튼 구현
        validateButton = findViewById(R.id.join_btn_check);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = join_input_id.getText().toString();

                if(userID.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("아이디를 입력하세요")
                                    .setPositiveButton("확인", null)
                                    .create();
                    dialog.show();;
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                dialog = builder.setMessage("사용가능한 아이디입니다.")
                                                .setPositiveButton("확인", null)
                                                .create();
                                dialog.show();
                                id_validate = true;
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                dialog = builder.setMessage("사용중인 아이디입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                id_validate = false;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
                queue.add(validateRequest);

            }
        });

        // EditText focus를 위한 메소드
        join_input_id.setFocusable(true);
        join_input_pw.setFocusable(true);
        join_input_pw_submit.setFocusable(true);
        join_input_name.setFocusable(true);
        join_input_phone.setFocusable(true);

        // 회원가입 버튼 클릭 시 수행
        join_btn_join = findViewById(R.id.join_btn_join);
        join_btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 아이디 중복 확인
                if(!id_validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("사용중인 아이디입니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    join_input_id.requestFocus();
                }
                // 비밀번호 입력 확인
                else if(join_input_pw.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("비밀번호를 입력하세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    join_input_pw.requestFocus();
                }
                // 비밀번호 일치 확인
                else if(!join_input_pw_submit.getText().toString().equals(join_input_pw.getText().toString())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("비밀번호가 일치하지 않습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    join_input_pw_submit.requestFocus();
                }
                // 이름 입력 확인
                else if(join_input_name.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("이름을 입력하세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    join_input_name.requestFocus();
                }
                // 휴대전화 번호 입력 확인
                else if(join_input_phone.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("휴대전화 번호를 입력하세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    join_input_phone.requestFocus();
                }
                // 회원 등록
                else{
                    // EditText에 현재 입력되어 있는 값을 get(가져온다)해온다.
                    String userID = join_input_id.getText().toString();
                    String userPass = join_input_pw.getText().toString();
                    String userName = join_input_name.getText().toString();
                    String userNumber = join_input_phone.getText().toString();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) { // 회원등록에 성공한 경우
                                    Toast.makeText(getApplicationContext(), "회원 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else { // 회원등록에 실패한 경우
                                    Toast.makeText(getApplicationContext(), "회원 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };

                    // 서버로 Volley를 이용해서 요청을 함
                    JoinRequest joinRequest = new JoinRequest(userID, userPass, userName, userNumber, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
                    queue.add(joinRequest);
                }
            }
        });
    }
}
