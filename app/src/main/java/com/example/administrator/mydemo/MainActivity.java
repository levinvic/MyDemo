package com.example.administrator.mydemo;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.example.administrator.mydemo.Utils_Tools.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "ccc";
    private DrawerLayout drawlayout;
    private long mLastClickTime;
    private NavigationView navigation_view;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private ArrayList<Fragment> lst_fragment;
    private NoScrollViewPager vp_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitData();
        InitComponent();
        InitView();
        InitEventListener();
    }


    private void InitData() {
        mLastClickTime = 0;
        lst_fragment = new ArrayList<>();
        /** 計算機 */
        lst_fragment.add(new com.example.administrator.mydemo.BlockCalculator.ViewFragment());
        /** 畫布 */
        lst_fragment.add(new com.example.administrator.mydemo.BlockCanvas.ViewFragment());
        /** 類比時鐘 */
        lst_fragment.add(new com.example.administrator.mydemo.BlockClock.ViewFragment());
        /** 圖片移動 */
        lst_fragment.add(new com.example.administrator.mydemo.BlockImageLoader.ViewFragment());
    }

    private void InitComponent() {
        drawlayout = findViewById(R.id.drawlayout);
        navigation_view = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        vp_main = findViewById(R.id.vp_main);
    }

    private void InitView() {
        // 側邊選單導覽
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawlayout, toolbar, R.string.open, R.string.close);
        drawlayout.addDrawerListener(toggle);
        toggle.syncState();
        // 主頁內容
        MyFragmentPagerAdapter mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), lst_fragment);
        vp_main.setAdapter(mAdapter);

    }

    private void InitEventListener() {
        navigation_view.setNavigationItemSelectedListener(this);
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> lst_fragment;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> lst_fragment) {
            super(fm);
            this.lst_fragment = lst_fragment;
        }

        @Override
        public Fragment getItem(int i) {
            return lst_fragment.get(i);
        }

        @Override
        public int getCount() {
            return lst_fragment.size();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawlayout.closeDrawer(Gravity.START);
        // 防連擊
        if (0 == mLastClickTime || System.currentTimeMillis() - mLastClickTime > Constants.click_waitTime) {
            switch (menuItem.getItemId()) {
                case R.id.nav_calcultor:
                    vp_main.setCurrentItem(0, false);
                    break;
                case R.id.nav_canvas:
                    vp_main.setCurrentItem(1, false);
                    break;
                case R.id.nav_clock:
                    vp_main.setCurrentItem(2, false);
                    break;
                case R.id.nav_image_manipulation:
                    vp_main.setCurrentItem(3, false);
                    break;
            }
            mLastClickTime = System.currentTimeMillis();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<>(10);
//
//    public interface MyOnTouchListener {
//        boolean onTouch(MotionEvent event);
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.d(TAG, "dispatchTouchEvent");
//        for (MyOnTouchListener onTouchListener : onTouchListeners) {
//            onTouchListener.onTouch(ev);
//        }
//        return super.dispatchTouchEvent(ev);
//    }
//
//    public void registerOnTouch(MyOnTouchListener my) {
//        onTouchListeners.add(my);
//    }
//
//    public void unregisterOnTouch(MyOnTouchListener my) {
//        onTouchListeners.remove(my);
//    }
}
