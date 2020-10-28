package com.example.reviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Uri> itemList;
    private Context context;
    private View.OnClickListener onClickItem;

    public MyAdapter(Context context, ArrayList<Uri> itemList, View.OnClickListener onclickItem) {
        this.context = context;
        this.itemList = itemList;
        this.onClickItem = onclickItem;
    }

    // ViewHolder 클래스
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.item_imageview);
        }

    }

    // ViewHolder가 생성되는 함수
    // ViewHolder란 View 객체를 기억하고 있을 객체
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 한 개의 목록이 디자인 되어있는 레이아웃(attach_photo_item.xml)
        View view = LayoutInflater.from(context).inflate(R.layout.attach_photo_item, parent, false);

        return new ViewHolder(view);
    }

    // ViewHolder에 데이터를 바인딩(할당) 해주는 함수
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        Uri item = itemList.get(position);

        holder.imageView.setImageURI(item);
    }

    // 화면에 뿌려질 데이터의 전체 길이를 리턴
    @Override
    public int getItemCount() {
        return itemList.size();
    }


}
