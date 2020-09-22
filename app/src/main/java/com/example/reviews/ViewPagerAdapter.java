package com.example.reviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context = null;
    ArrayList<Integer> data;

    // context를 전달받아 context에 저장하는 생성자 추가
    public ViewPagerAdapter(Context context, ArrayList<Integer> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // position 값을 받아 주어진 위치에 페이지를 생성한다.

        // LayoutInflater를 통해 "/res/layout/page.xml"을 뷰로 생성
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.page, container, false);

        ImageView imageView = view.findViewById(R.id.main_movie);
        imageView.setImageResource(data.get(position));


        // 뷰페이저에 추가
        container.addView(view);

        // 여기서 리턴한 View 객체가 아래 isViewFromObject()메소드에 전달됨
        return view;


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // position 값을 받아 주어진 위치의 페이지를 삭제한다
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        // 사용가능한 뷰의 개수를 return 한다.
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        // 페이지 뷰가 생성된 페이지의 object key와 같은지 확인한다
        // 해당 object key는 instantiateItem 메소드에서 리턴시킨 오브젝트이다
        // 즉, 페이지의 뷰가 생성된 뷰인지 아닌지를 확인하는 것
        return view == object;
    }
}
