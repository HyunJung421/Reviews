package com.example.reviews;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class JoinRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동)
    final static private String URL = "ec2-3-34-44-58.ap-northeast-2.compute.amazonaws.com/Join.php";
    private Map<String, String> map;

    public JoinRequest(String userID, String userPassword, String userName, int userNumber, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userPassword", userPassword);
        map.put("userName", userName);
        map.put("userNumber", userNumber + "");
    }
}
