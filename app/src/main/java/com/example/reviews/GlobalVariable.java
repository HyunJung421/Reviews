package com.example.reviews;

import android.app.Application;

// 전역변수 선언 java 파일
public class GlobalVariable extends Application {
    // 로그인 유지를 위한 전역변수 선언
    private String user;

    public void onCreate() {
        super.onCreate();
    }

    // app 종료 시 가장 마지막에 실행 “데이터 삭제 및 해제 담당”
    public void onTerminate() {
        super.onTerminate();
    }

    // 로그인한 사용자 id(전역변수) set
    public void setData(String data) {
        this.user = data;
    }

    // 로그인한 사용자 id(전역변수) get
    public String getData() {
        return user;
    }

    public void deleteData() { this.user = ""; }
}
