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

import happyhours.dimooon.com.happyhours.model.pager.MainPager;
import happyhours.dimooon.com.happyhours.view.fragments.MainFragment;
import happyhours.dimooon.com.happyhours.view.fragments.TimerSessionsLists;

public class MainActivity extends FragmentActivity {

    private MainPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = new MainPager(this, (ViewPager) findViewById(R.id.pager));

    }

    @Override
    public void onBackPressed() {
        boolean processed = false;
        if(pager!=null){
            processed = pager.handleBackPressed();
        }
        if(!processed){
            super.onBackPressed();
        }
    }


}