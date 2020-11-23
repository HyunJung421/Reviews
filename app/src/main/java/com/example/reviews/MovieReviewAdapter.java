package com.example.reviews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ViewHolder> {
    private ArrayList<ArrayList<String>> itemList;
    private Context context;
    // 좋아요 여부 저장
    //boolean likeState = false;

    public MovieReviewAdapter(Context context, ArrayList<ArrayList<String>> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    // ViewHolder 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView user_nick;       // 사용자 이름
        TextView review_content;  // 리뷰내용
        TextView review_recommend; // 리뷰 추천수
        boolean likeState = false;


        public ViewHolder(View itemView){
            super(itemView);

            user_nick = itemView.findViewById(R.id.reviewer_id);
            review_recommend = itemView.findViewById(R.id.reviewer_recommend);
            review_recommend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (likeState) {
                        decrLikeCount(review_recommend);
                    } else {
                        incrLikeCount(review_recommend);
                    }
                    likeState = !likeState;
                }
            });
            review_content = itemView.findViewById(R.id.reviewer_story);

        }

    }

    // ViewHolder가 생성되는 함수
    // ViewHolder란 View 객체를 기억하고 있을 객체
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_review_item, parent, false);
        MovieReviewAdapter.ViewHolder viewHolder = new MovieReviewAdapter.ViewHolder(view);
        return viewHolder;
    }

    // ViewHolder에 데이터를 바인딩(할당) 해주는 함수
    @Override
    public void onBindViewHolder(@NonNull MovieReviewAdapter.ViewHolder holder, int position) {
        ArrayList<String> items = itemList.get(position);

        holder.user_nick.setText(items.get(0));
        holder.review_recommend.setText(items.get(1));
        holder.review_content.setText(items.get(2));
    }

    // 화면에 뿌려질 데이터의 전체 길이를 리턴
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // 좋아요 +1 메소드
    private void incrLikeCount(TextView recomm) {
        int num = Integer.parseInt(recomm.getText().toString()) + 1;
        recomm.setText(String.valueOf(num));
        // 파란따봉으로 이미지 변경
        Drawable img = context.getResources().getDrawable(R.drawable.drawable_left_image_customise);
        img.setBounds(0,0,60,60);
        recomm.setCompoundDrawables(img, null, null, null);
        // 좋아요 숫자 파란색으로 변경
        recomm.setTextColor(Color.parseColor("#0000E1"));
    }

    // 좋아요 -1 메소드
    public void decrLikeCount(TextView recomm) {
        int num = Integer.parseInt(recomm.getText().toString()) - 1;
        recomm.setText(String.valueOf(num));
        // 회색따봉으로 이미지 변경
        Drawable img = context.getResources().getDrawable(R.drawable.unlike_left_image_customise);
        img.setBounds(0,0,60,60);
        recomm.setCompoundDrawables(img, null, null, null);
        // 좋아요 숫자 회색으로 변경
        recomm.setTextColor(Color.parseColor("#999999"));
    }
}
