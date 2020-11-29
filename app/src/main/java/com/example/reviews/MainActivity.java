package com.example.reviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

// 메인페이지 java 파일
public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;

    ArrayList<Integer> data = new ArrayList<Integer>();

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 3000;   // 타이머 시작 후 해당 시간에 작동(초기 웨이팅 타입)
    final long PERIOD_MS = 3000;  // 4초 주기로 작동

    // 검색돋보기
    ImageView btnsearchmag;

    // 더보기 버튼
    Button btnmore1;
    Button btnmore2;
    Button btnmore3;

    // 영화평점포스터
    ImageView movie_rating1;
    ImageView movie_rating2;
    ImageView movie_rating3;

    // 영화평점타이틀
    TextView movieRatingTitle1;
    TextView movieRatingTitle2;
    TextView movieRatingTitle3;

    // 영화추천포스터
    ImageView movie_recommend1;
    ImageView movie_recommend2;
    ImageView movie_recommend3;

    // 영화추천타이틀
    TextView movieRecTitle1;
    TextView movieRecTitle2;
    TextView movieRecTitle3;

    // 영화신작포스터
    ImageView movie_new1;
    ImageView movie_new2;
    ImageView movie_new3;

    // 영화신작타이틀
    TextView movieNewTitle1;
    TextView movieNewTitle2;
    TextView movieNewTitle3;

    // 영화사이트 버튼
    ImageButton maincgv;
    ImageButton mainmega;
    ImageButton mainlotte;

    // 하단바 버튼
    ImageButton btnHome;
    ImageButton btnSocial;
    ImageButton btnMypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 뷰페이저에 추가할 영화 포스터 추가
        data.add(R.drawable.main_view_movie1);
        data.add(R.drawable.main_view_movie2);
        data.add(R.drawable.main_view_movie3);

        viewPager = findViewById(R.id.viewPager);
        pagerAdapter = new ViewPagerAdapter(this, data);
        viewPager.setAdapter(pagerAdapter);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if(currentPage == data.size()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        // 돋보기 구현
        btnsearchmag = (ImageView) findViewById(R.id.search_magnifier);
        btnsearchmag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });

        // 더보기 버튼 구현
        btnmore1 = (Button)findViewById(R.id.more1);
        btnmore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieRatingRankActivity.class);
                startActivity(intent);
            }
        });

        btnmore2 = (Button)findViewById(R.id.more2);
        btnmore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieRecommendRankActivity.class);
                startActivity(intent);
            }
        });

        btnmore3 = (Button)findViewById(R.id.more3);
        btnmore3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieNewListActivity.class);
                startActivity(intent);
            }
        });


        // 탭호스트 구현
        final TabHost tabHost = (TabHost)findViewById(R.id.tabHost1);
        tabHost.setup();

        // 첫번째 탭
        TabHost.TabSpec ts1 = tabHost.newTabSpec("movie_rating_ranking");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("영화평점순위");
        tabHost.addTab(ts1);

        // 두번째 탭
        TabHost.TabSpec ts2 = tabHost.newTabSpec("movie_recommend_ranking");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("영화추천순위");
        tabHost.addTab(ts2);

        // 세번째 탭
        TabHost.TabSpec ts3 = tabHost.newTabSpec("movie_new_ranking");
        ts3.setContent(R.id.content3);
        ts3.setIndicator("영화신작");
        tabHost.addTab(ts3);

        // 평점순위영화 제목
        movieRatingTitle1 = (TextView)findViewById(R.id.movie_rating_title1);
        movieRatingTitle2 = (TextView)findViewById(R.id.movie_rating_title2);
        movieRatingTitle3 = (TextView)findViewById(R.id.movie_rating_title3);

        // 추천순위영화 제목
        movieRecTitle1 = (TextView)findViewById(R.id.movie_recommend_title1);
        movieRecTitle2 = (TextView)findViewById(R.id.movie_recommend_title2);
        movieRecTitle3 = (TextView)findViewById(R.id.movie_recommend_title3);

        // 신작영화 제목
        movieNewTitle1 = (TextView)findViewById(R.id.movie_newlist_title1);
        movieNewTitle2 = (TextView)findViewById(R.id.movie_newlist_title2);
        movieNewTitle3 = (TextView)findViewById(R.id.movie_newlist_title3);

        final String titleR1 = movieRatingTitle1.getText().toString(); // 첫번째 영화 제목
        final String titleR2 = movieRatingTitle2.getText().toString(); // 두번째 영화 제목
        final String titleR3 = movieRatingTitle3.getText().toString(); // 세번째 영화 제목

        movie_rating1 = (ImageView)findViewById(R.id.movie_rating1);
        movie_rating1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                intent.putExtra("title", titleR1); // 영화 제목 전달
                startActivity(intent);
            }
        });

        movie_rating2 = (ImageView)findViewById(R.id.movie_rating2);
        movie_rating2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                intent.putExtra("title", titleR2); // 영화 제목 전달
                startActivity(intent);
            }
        });

        movie_rating3 = (ImageView)findViewById(R.id.movie_rating3);
        movie_rating3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                intent.putExtra("title", titleR3); // 영화 제목 전달
                startActivity(intent);
            }
        });

        // 탭이 변경될 때 발생하는 이벤트
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId=="movie_rating_ranking"){
                    // 평점순위영화 제목
                    final String titleR1 = movieRatingTitle1.getText().toString(); // 첫번째 영화 제목
                    final String titleR2 = movieRatingTitle2.getText().toString(); // 두번째 영화 제목
                    final String titleR3 = movieRatingTitle3.getText().toString(); // 세번째 영화 제목

                    movie_rating1 = (ImageView)findViewById(R.id.movie_rating1);
                    movie_rating1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                            intent.putExtra("title", titleR1); // 영화 제목 전달
                            startActivity(intent);
                        }
                    });

                    movie_rating2 = (ImageView)findViewById(R.id.movie_rating2);
                    movie_rating2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                            intent.putExtra("title", titleR2); // 영화 제목 전달
                            startActivity(intent);
                        }
                    });

                    movie_rating3 = (ImageView)findViewById(R.id.movie_rating3);
                    movie_rating3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                            intent.putExtra("title", titleR3); // 영화 제목 전달
                            startActivity(intent);
                        }
                    });
                }
                else if(tabId=="movie_recommend_ranking"){
                    // 추천순위영화 제목
                    final String titleRec1 = movieRecTitle1.getText().toString(); // 첫번째 영화 제목
                    final String titleRec2 = movieRecTitle2.getText().toString(); // 두번째 영화 제목
                    final String titleRec3 = movieRecTitle3.getText().toString(); // 세번째 영화 제목

                    movie_recommend1 = (ImageView)findViewById(R.id.movie_recommend1);
                    movie_recommend1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                            intent.putExtra("title", titleRec1); // 영화 제목 전달
                            startActivity(intent);
                        }
                    });

                    movie_recommend2 = (ImageView)findViewById(R.id.movie_recommend2);
                    movie_recommend2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                            intent.putExtra("title", titleRec2); // 영화 제목 전달
                            startActivity(intent);
                        }
                    });

                    movie_recommend3 = (ImageView)findViewById(R.id.movie_recommend3);
                    movie_recommend3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                            intent.putExtra("title", titleRec3); // 영화 제목 전달
                            startActivity(intent);
                        }
                    });
                }
                else {
                    // 신작영화 제목
                    final String titleNew1 = movieNewTitle1.getText().toString(); // 첫번째 영화 제목
                    final String titleNew2 = movieNewTitle2.getText().toString(); // 두번째 영화 제목
                    final String titleNew3 = movieNewTitle3.getText().toString(); // 세번째 영화 제목

                    movie_new1 = (ImageView)findViewById(R.id.movie_new1);
                    movie_new1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                            intent.putExtra("title", titleNew1); // 영화 제목 전달
                            startActivity(intent);
                        }
                    });

                    movie_new2 = (ImageView)findViewById(R.id.movie_new2);
                    movie_new2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                            intent.putExtra("title", titleNew2); // 영화 제목 전달
                            startActivity(intent);
                        }
                    });

                    movie_new3 = (ImageView)findViewById(R.id.movie_new3);
                    movie_new3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                            intent.putExtra("title", titleNew3); // 영화 제목 전달
                            startActivity(intent);
                        }
                    });
                }

            }
        });

        // 영화사이트
        maincgv = (ImageButton)findViewById(R.id.main_btn_cgv);
        maincgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cgv.co.kr/ticket/"));
                startActivity(intent);
            }
        });

        mainmega = (ImageButton)findViewById(R.id.main_btn_mega);
        mainmega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.megabox.co.kr/booking"));
                startActivity(intent);
            }
        });

        mainlotte = (ImageButton)findViewById(R.id.main_btn_lotte);
        mainlotte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.lottecinema.co.kr/NLCHS/Ticketing"));
                startActivity(intent);
            }
        });


        // 하단바 underbar_home 버튼 등록 및 리스너 구현
        btnHome = (ImageButton)findViewById(R.id.underbar_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "메인페이지 입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // 하단바 underbar_social 버튼 등록 및 리스너 구현
        btnSocial = (ImageButton)findViewById(R.id.underbar_social);
        btnSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SocialActivity.class);
                startActivity(intent); // 액티비티 띄우기
            }
        });

        // 하단바 underbar_mypage 버튼 등록 및 리스너 구현
        btnMypage = (ImageButton)findViewById(R.id.underbar_mypage);
        btnMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
                startActivity(intent); // 액티비티 띄우기

            }
        });
    }
}
