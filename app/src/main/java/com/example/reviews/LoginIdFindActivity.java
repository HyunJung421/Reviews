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

// ID찾기 java 파일
public class LoginIdFindActivity extends AppCompatActivity {

    private EditText login_input_name, login_input_phone;
    private Button auth_number_btn;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_id_find);

        login_input_name = (EditText)findViewById(R.id.login_input_name);
        login_input_phone = (EditText)findViewById(R.id.login_input_phone);

        auth_number_btn = (Button)findViewById(R.id.find_btn_randomnum);
        auth_number_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = login_input_name.getText().toString();
                String phone = login_input_phone.getText().toString();

                int min = 100000;
                int max = 999999;
                int random = new Random().nextInt((max-min)+1) + min;
                String sms = "[Reviews ID 본인확인] 인증번호[" + random + "]를 입력해주세요.";

                if (name.equals("")) {
                    Toast.makeText(LoginIdFindActivity.this, "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                    login_input_name.requestFocus();
                } else if (phone.equals("")) {
                    Toast.makeText(LoginIdFindActivity.this, "휴대폰 번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    login_input_phone.requestFocus();
                } else {
                    PendingIntent sentIntent = PendingIntent.getBroadcast(LoginIdFindActivity.this, 0, new Intent("SMS_SENT_ACTION"), 0);

                    registerReceiver(new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            switch(getResultCode()){
                                case Activity.RESULT_OK:
                                    // 전송 성공
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginIdFindActivity.this);
                                    dialog = builder.setMessage("인증번호가 발송되었습니다.\n 문자가 안올 경우 휴대폰 번호를 다시 확인해주세요.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                    break;
                                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                                    // 전송 실패
                                    Toast.makeText(LoginIdFindActivity.this, "전송 실패", Toast.LENGTH_SHORT).show();
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
    }
}
