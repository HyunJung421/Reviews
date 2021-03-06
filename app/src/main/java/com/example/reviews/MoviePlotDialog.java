package com.example.reviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

// 영화 내용 더보기 다이얼로그 java 파일
public class MoviePlotDialog extends Dialog {

    // 창닫기 버튼
    private ImageButton plot_back;
    TextView mTitle;
    TextView mPlot;

    public MoviePlotDialog(@NonNull Context context, String title, String plot) {
        super(context);
        setContentView(R.layout.movie_plot);

        // 다이얼로그의 너비와 높이 설정
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(this.getWindow().getAttributes());  // 여기서 설정한 값을 그대로 다이얼로그에 넣겠다는 의미
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(params);

        mTitle = (TextView)findViewById(R.id.movie_plot_title);
        mTitle.setText(title);
        mPlot = (TextView)findViewById(R.id.movie_plot_plot);
        mPlot.setText(plot);

        plot_back = (ImageButton)findViewById(R.id.window_close_btn);

        // 창닫기 버튼 클릭시
        plot_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 다이얼로그 창 닫기
                dismiss();
            }
        });
    }
}
