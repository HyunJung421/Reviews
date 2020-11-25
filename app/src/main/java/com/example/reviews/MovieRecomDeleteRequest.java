package com.example.reviews;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MovieRecomDeleteRequest extends StringRequest {
    // 서버 URL 설정 (PHP 파일 연동)
    final static private String URL = "http://3.34.44.58/DeleteMovieRecom.php";
    private Map<String, String> map;

    public MovieRecomDeleteRequest(int m_Count, int m_ID, String userID, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("m_Count", m_Count + "");
        map.put("m_ID", m_ID + "");
        map.put("userID", userID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
