package com.example.reviews;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

// 리뷰 작성 페이지에 영화정보를 불러오기 위한 java 파일
public class MovieInfoRequest extends StringRequest {

    // 서버 URL 설정 (PHP 파일 연동)
    final static private String URL = "http://3.34.44.58/LoadMovieInfo.php";
    private Map<String, String> map;

    public MovieInfoRequest(String m_Title, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("m_Title", m_Title);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
