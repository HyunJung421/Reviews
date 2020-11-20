package com.example.reviews;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

// 리뷰 작성 내용 db에 저장하기 위한 java 파일
public class ReviewSaveRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동)
    final static private String URL = "http://3.34.44.58/RevWrite.php";
    private Map<String, String> map;

    public ReviewSaveRequest(int m_ID, String userID, String revDate, String revContent, String revPhoto, int revRecom, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("m_ID", m_ID + "");
        map.put("userID", userID);
        map.put("revDate", revDate);
        map.put("revContent", revContent);
        map.put("revPhoto", revPhoto);
        map.put("revRecom", revPhoto+"");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
