package com.example.reviews;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

//PW찾기 java 파일
public class LoginPwFindActivity extends AppCompatActivity {

    private EditText login_input_id, login_input_name, login_input_phone, login_input_randomnum;
    private Button auth_number_btn, auth_number_again_btn, auth_number_confirm;
    private AlertDialog dialog;  // 알림창
    private String authNumber;  // 인증번호
    private String userPassword; // 인증완료시 pw 넘기기 위한 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_password_find);

        // 아이디, 이름, 휴대폰 번호 입력칸
        login_input_id = (EditText)findViewById(R.id.login_input_id);
        login_input_name = (EditText)findViewById(R.id.login_input_name);
        login_input_phone = (EditText)findViewById(R.id.login_input_phone);

        // 인증번호 버튼 등록 및 리스너 구현
        auth_number_btn = (Button)findViewById(R.id.find_btn_randomnum);
        auth_number_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 아이디, 이름, 휴대폰 번호 데이터
                final String id = login_input_id.getText().toString();
                final String name = login_input_name.getText().toString();
                final String phone = login_input_phone.getText().toString();
                
                final String authNumber;
                setAuthNumber(); // 인증번호 생성
                authNumber = getAuthNumber();  // 인증번호 받기

                final String sms = "[Reviews 본인확인] 인증번호[" + authNumber + "]를 입력해주세요.";  // 보내질 문자내용

                if (id.equals("")) {  // 아이디 입력 여부
                    Toast.makeText(LoginPwFindActivity.this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                    login_input_id.requestFocus();
                } else if (name.equals("")) {  // 이름 입력 여부
                    Toast.makeText(LoginPwFindActivity.this, "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                    login_input_phone.requestFocus();
                } else if (phone.equals("")) {  // 휴대폰 번호 입력 여부
                    Toast.makeText(LoginPwFindActivity.this, "휴대폰 번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    login_input_phone.requestFocus();
                }else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) { // 등록된 회원일 경우
                                    userPassword = jsonObject.getString("userPassword");

                                    // 인증번호 전송
                                    sendAuthNumber(name, phone, authNumber, sms);

                                } else { // 등록되지 않은 회원일 경우
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginPwFindActivity.this);
                                    dialog = builder.setMessage("등록되지 않은 회원입니다.")
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
                    LoginPwFindRequest loginIdFindRequest = new LoginPwFindRequest(id, name, phone, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginPwFindActivity.this);
                    queue.add(loginIdFindRequest);
                }
            }
        });

        // 재전송 버튼 등록 및 리스너 구현
        auth_number_again_btn = (Button)findViewById(R.id.find_btn_randomnum_again);
        auth_number_again_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이름, 휴대폰 번호 데이터
                String id = login_input_id.getText().toString();
                String name = login_input_name.getText().toString();
                String phone = login_input_phone.getText().toString();

                String authNumber;
                setAuthNumber(); // 인증번호 생성
                authNumber = getAuthNumber();  // 인증번호 받기

                String sms = "[Reviews ID 본인확인] 인증번호[" + authNumber + "]를 입력해주세요.";

                // 인증번호 전송
                sendAuthNumber(name, phone, authNumber, sms);
            }
        });

        // 인증번호 입력칸
        login_input_randomnum = (EditText)findViewById(R.id.login_input_randomnum);

        // 인증번호 입력후 확인버튼 등록 및 리스너 구현
        auth_number_confirm = (Button)findViewById(R.id.login_btn_confirm2);
        auth_number_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputAuthNumber = login_input_randomnum.getText().toString();
                if(inputAuthNumber.equals(getAuthNumber())){  // 인증번호가 일치할 경우
                    Toast.makeText(LoginPwFindActivity.this, "인증 완료!", Toast.LENGTH_SHORT).show();

                    // userPassword와 함께 LoginPwShowActivity 페이지로 이동
                    Intent intent = new Intent(LoginPwFindActivity.this, LoginPwShowActivity.class);
                    intent.putExtra("userPassword", userPassword); // userPassword 전송
                    startActivity(intent);
                } else {  // 인증번호가 일치하지 않을 경우
                    Toast.makeText(LoginPwFindActivity.this, "인증번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    // 인증번호 생성 메소드
    public void setAuthNumber() {
        // 인증번호 100000-999999사이의 숫자로 랜덤 생성
        int min = 100000;
        int max = 999999;
        int random = new Random().nextInt((max-min)+1) + min;
        this.authNumber = String.valueOf(random);
    }

    // 인증번호 가져오는 메소드
    public String getAuthNumber() {
        return this.authNumber;
    }

    // 인증번호 보내는 메소드
    public void sendAuthNumber(String name, String phone, String authNumber, String sms) {
        // 인증번호 전송
        PendingIntent sentIntent = PendingIntent.getBroadcast(LoginPwFindActivity.this, 0, new Intent("SMS_SENT_ACTION"), 0);

        // 전송여부 확인
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch(getResultCode()){
                    case Activity.RESULT_OK:
                        // 전송 성공
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginPwFindActivity.this);
                        dialog = builder.setMessage("인증번호가 발송되었습니다.\n문자가 안올 경우 휴대폰 번호를 다시 확인해주세요.\n")
                                .setPositiveButton("확인", null)
                                .create();
                        dialog.show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        // 전송 실패
                        Toast.makeText(LoginPwFindActivity.this, "전송 실패", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_SENT_ACTION"));

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone, null, sms, sentIntent, null);
        /*
          destinationAddress : 받는사람의 Phone Number. String형식임
          scAddress :  null 입력 (구글API : is the service center address or null to use the current default SMSC)
          text : 문자의 내용
          sentIntent : 문자 전송에 관련한 PendingIntent
          deliveryIntent : 문자 도착에 관련한 PendingIntent
        */
    }
}