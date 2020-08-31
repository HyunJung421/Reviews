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

import java.util.Random;

//PW찾기 java 파일
public class LoginpwFindActivity extends AppCompatActivity {

    private EditText login_input_id, login_input_name, login_input_phone, login_input_randomnum;
    private Button auth_number_btn, auth_number_again_btn, auth_number_confirm;
    private AlertDialog dialog;  // 알림창
    private String authNumber;  // 인증번호

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
                String id = login_input_id.getText().toString();
                String name = login_input_name.getText().toString();
                String phone = login_input_phone.getText().toString();
                
                String authNumber;
                setAuthNumber(); // 인증번호 생성
                authNumber = getAuthNumber();  // 인증번호 받기
                String sms = "[Reviews ID 본인확인] 인증번호[" + authNumber + "]를 입력해주세요.";  // 보내질 문자내용

                if (name.equals("")) {  // 이름 입력 여부
                    Toast.makeText(LoginpwFindActivity.this, "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                    login_input_name.requestFocus();
                } else if (phone.equals("")) {  // 휴대폰 번호 입력 여부
                    Toast.makeText(LoginpwFindActivity.this, "휴대폰 번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    login_input_phone.requestFocus();
                } else {
                    PendingIntent sentIntent = PendingIntent.getBroadcast(LoginpwFindActivity.this, 0, new Intent("SMS_SENT_ACTION"), 0);

                    // 전송여부 확인
                    registerReceiver(new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            switch(getResultCode()){
                                case Activity.RESULT_OK:
                                    // 전송 성공
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginpwFindActivity.this);
                                    dialog = builder.setMessage("인증번호가 발송되었습니다.\n문자가 안올 경우 휴대폰 번호를 다시 확인해주세요.\n")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                    break;
                                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                                    // 전송 실패
                                    Toast.makeText(LoginpwFindActivity.this, "전송 실패", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }, new IntentFilter("SMS_SENT_ACTION"));

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phone, null, sms, sentIntent, null);
                    /*
                        destinationAddress : 받는사람의 Phone Number입니다 신기하게도 String형식입니다
                        scAddress : 이건 잘 모르겠습니다 일단 null을 입력해 주세요 (구글API : is the service center address or null to use the current default SMSC)
                        text : 문자의 내용입니다
                        sentIntent : 문자 전송에 관련한 PendingIntent입니다.
                        deliveryIntent : 문자 도착에 관련한 PendingIntent라고 합니다.
                    */

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

                PendingIntent sentIntent = PendingIntent.getBroadcast(LoginpwFindActivity.this, 0, new Intent("SMS_SENT_ACTION"), 0);

                registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        switch(getResultCode()){
                            case Activity.RESULT_OK:
                                // 전송 성공
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginpwFindActivity.this);
                                dialog = builder.setMessage("인증번호가 발송되었습니다.\n문자가 안올 경우 휴대폰 번호를 다시 확인해주세요.\n")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                break;
                            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                                // 전송 실패
                                Toast.makeText(LoginpwFindActivity.this, "전송 실패", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }, new IntentFilter("SMS_SENT_ACTION"));

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phone, null, sms, sentIntent, null);
            }
        });
        // 인증번호 입력칸
        login_input_randomnum = (EditText)findViewById(R.id.login_input_randomnum);

        // 인증번호 입력후 확인버튼 등록 및 리스너 구현
        auth_number_confirm = (Button)findViewById(R.id.login_btn_confirm);
        auth_number_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputAuthNumber = login_input_randomnum.getText().toString();
                if(inputAuthNumber.equals(getAuthNumber())){
                    Toast.makeText(LoginpwFindActivity.this, "인증 확인!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 인증번호 생성
    public void setAuthNumber() {
        // 인증번호 100000-999999사이의 숫자로 랜덤 생성
        int min = 100000;
        int max = 999999;
        int random = new Random().nextInt((max-min)+1) + min;
        this.authNumber = String.valueOf(random);
    }

    // 인증번호 가져오기
    public String getAuthNumber() {
        return this.authNumber;
    }
}