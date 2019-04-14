package com.antelope.android.frat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.antelope.android.frat.Fragment.ControlFragment;
import com.antelope.android.frat.Fragment.DataFragment;
import com.antelope.android.frat.Fragment.HistoryFragment;
import com.antelope.android.frat.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private MenuItem mMenuItem;
    private BottomNavigationView mBottomNavigationView;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigationView = findViewById(R.id.navigation);
        mViewPager = findViewById(R.id.view_pager);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.navigation_dashboard:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.history:
                        mViewPager.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mMenuItem != null){
                    mMenuItem.setChecked(false);
                } else {
                    mBottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                mMenuItem= mBottomNavigationView.getMenu().getItem(position);
                mMenuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(DataFragment.newInstance());
        viewPagerAdapter.addFragment(ControlFragment.newInstance());
        viewPagerAdapter.addFragment(HistoryFragment.newInstance());

        viewPager.setAdapter(viewPagerAdapter);
    }

}
