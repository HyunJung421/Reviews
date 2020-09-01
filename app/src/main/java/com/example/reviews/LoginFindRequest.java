package com.example.reviews;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginFindRequest extends StringRequest {

    // 서버 URL 설정 (PHP 파일 연동)
    final static private String URL = "http://3.34.44.58/IdFind.php";
    private Map<String, String> map;

    public LoginFindRequest(String userName, String userNumber, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userName);
        map.put("userPassword", userNumber);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
