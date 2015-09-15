package happyhours.dimooon.com.happyhours.model.pager;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import happyhours.dimooon.com.happyhours.view.fragments.MainFragment;
import happyhours.dimooon.com.happyhours.view.fragments.TimerSessionsLists;

public class MainPager implements Pager{

    private Activity activity;
    private ViewPager pager;

    public enum Page{
        MAIN(1),STORY(2);

        Page(int pageId) {
            this.id = pageId;
        }

        public int id;
    }

    public MainPager(FragmentActivity activity,ViewPager pager) {

        this.activity = activity;
        this.pager = pager;

        List<Fragment> fragments = new ArrayList<Fragment>();

        fragments.add(new TimerSessionsLists());
        fragments.add(new MainFragment());

        pager.setAdapter(new ScreenSlidePagerAdapter(activity.getSupportFragmentManager(),fragments));
        pager.setCurrentItem(Page.MAIN.id);

    }

    @Override
    public boolean handleBackPressed() {
        if (Page.MAIN.id != this.pager.getCurrentItem()) {
            this.pager.setCurrentItem(Page.MAIN.id);
            return true;
        }
        return false;
    }
}