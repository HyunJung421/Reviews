package com.example.reviews;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

// 마이페이지 -> 작성한코멘트 목록 -> 작성한코멘트 상세페이지 java 파일
public class ReviewDetailDialog extends Dialog {

    // 창닫기 버튼
    private ImageButton review_back;

    public ReviewDetailDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.mypage_review_detail);

        // 다이얼로그의 너비와 높이 설정
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(this.getWindow().getAttributes());  // 여기서 설정한 값을 그대로 다이얼로그에 넣겠다는 의미
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(params);

        review_back = (ImageButton)findViewById(R.id.window_close_btn);

        // 창닫기 버튼 클릭시
        review_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다이얼로그 창 닫기
                dismiss();
            }
        });
    }
}
