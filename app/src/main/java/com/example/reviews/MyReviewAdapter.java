package com.example.reviews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.ViewHolder> {
    private ArrayList<ArrayList<String>> itemList;
    private Context context;
    private Bitmap bitmap;
    private ReviewDetailDialog reviewDialog;  // 리뷰글 다이얼로그 클래스

    public MyReviewAdapter(Context context, ArrayList<ArrayList<String>> itemList) {
        this.context = context;
        this.itemList = itemList;
        reviewDialog = new ReviewDetailDialog(context);
    }

    // ViewHolder 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout;
        ImageView user_image;
        TextView user_nick;       // 사용자 이름
        ImageView movie_poster;   // 영화 포스터
        TextView movie_title;     // 영화 제목
        TextView movie_year;      // 영화 개봉연도
        TextView movie_running;   // 영화 시간
        TextView movie_country;   // 영화 제작나라
        TextView movie_genre;     // 영화 장르
        TextView movie_recommend; // 영화 추천수
        TextView review_content;  // 리뷰내용


        public ViewHolder(View itemView){
            super(itemView);

            layout = itemView.findViewById(R.id.my_review1);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reviewDialog.show();
                }
            });
            user_image = itemView.findViewById(R.id.user_image);
            user_nick = itemView.findViewById(R.id.user_id);
            movie_poster = itemView.findViewById(R.id.movie_poster);
            movie_title = itemView.findViewById(R.id.movie_title);
            movie_year = itemView.findViewById(R.id.movie_year);
            movie_running = itemView.findViewById(R.id.movie_running_time);
            movie_country = itemView.findViewById(R.id.movie_country);
            movie_genre = itemView.findViewById(R.id.movie_genre);
            movie_recommend = itemView.findViewById(R.id.movie_recommend);
            review_content = itemView.findViewById(R.id.review_content);

        }

    }

    // ViewHolder가 생성되는 함수
    // ViewHolder란 View 객체를 기억하고 있을 객체
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mypage_review_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // ViewHolder에 데이터를 바인딩(할당) 해주는 함수
    @Override
    public void onBindViewHolder(@NonNull MyReviewAdapter.ViewHolder holder, int position) {
        ArrayList<String> items = itemList.get(position);
        Bitmap image = urlImageToBitmap(items.get(1)); // 영화포스터 경로 Bitmap형식으로 변환
        final String title = items.get(2);  // 영화 제목

        holder.user_nick.setText(items.get(0));
        holder.movie_poster.setImageBitmap(image);
        holder.movie_poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieInfoActivity.class);
                intent.putExtra("title", title); // 영화 제목 전달
                context.startActivity(intent);
            }
        });
        holder.movie_title.setText(title);
        holder.movie_year.setText(items.get(3));
        holder.movie_running.setText(items.get(4));
        holder.movie_country.setText(items.get(5));
        holder.movie_genre.setText(items.get(6));
        holder.movie_recommend.setText(items.get(7));
        holder.review_content.setText(items.get(8));
    }

    // 화면에 뿌려질 데이터의 전체 길이를 리턴
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // DB에 저장된 영화 포스터 url로 이미지 지정하기
    public Bitmap urlImageToBitmap(String url) {
        final String murl = url;

        // 안드로이드에서 네트워크와 관련된 작업을 할 때,
        // 반드시 메인 Thread가 아닌 별도의 작업 Thread를 생성하여 작업해야 한다.
        Thread mThread = new Thread() {
            @Override
            public void run(){
                try{
                    // 서버에 올려둔 이미지 url
                    URL url = new URL(murl);

                    //web에서 이미지 가져온 후 ImageView에 지정할 Bitmap 만들기
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoInput(true);  // Server 통신에서 입력 가능한 상태로 만듦
                    conn.connect();  // 연결된 곳에 접속할 때 (connect() 호출해야 실제 통신 가능)

                    InputStream is = conn.getInputStream();  // inputStream 값 가져오기
                    bitmap = BitmapFactory.decodeStream(is);  // Bitmap으로 반환
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        mThread.start();  // 작업 Thread 실행

        try {
            // 메인 Thread는 별도의 작업을 완료할 때까지 대기한다.
            // join() 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다린다.
            // join() 메소드는 InterruptedException을 발생시킨다.
            mThread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
