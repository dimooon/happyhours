package happyhours.dimooon.com.happyhours;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import happyhours.dimooon.com.happyhours.view.fragments.MainFragment;
import happyhours.dimooon.com.happyhours.view.fragments.TimerSessionsLists;

public class MainActivity extends FragmentActivity {

    private static final int NUM_PAGES = 2;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPager();
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 1) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(1);
        }
    }

    private void initPager(){
        pager = (ViewPager) findViewById(R.id.pager);

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new TimerSessionsLists());
        fragments.add(new MainFragment());

        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(),fragments);

        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(1);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragments ;

        public ScreenSlidePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);

            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}