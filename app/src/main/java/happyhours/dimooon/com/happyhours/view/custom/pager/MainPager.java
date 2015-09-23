package happyhours.dimooon.com.happyhours.view.custom.pager;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import happyhours.dimooon.com.happyhours.model.database.manager.DatabaseSessionManager;
import happyhours.dimooon.com.happyhours.model.database.manager.SessionManager;
import happyhours.dimooon.com.happyhours.view.fragments.MainSessionFragment;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.MainSessionPresenter;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.MainSessionView;
import happyhours.dimooon.com.happyhours.view.fragments.MyStoryFragment;
import happyhours.dimooon.com.happyhours.view.fragments.storylog.MyStoryPresenter;
import happyhours.dimooon.com.happyhours.view.fragments.storylog.MyStoryView;

public class MainPager implements Pager{

    private ViewPager pager;

    public enum Page{
        MAIN(1),STORY(2);

        Page(int pageId) {
            this.id = pageId;
        }

        public int id;
    }

    public MainPager(FragmentActivity activity,ViewPager pager) {

        this.pager = pager;

        initPagerAdapter(activity);
    }

    @Override
    public boolean handleBackPressed() {
        if (Page.MAIN.id != this.pager.getCurrentItem()) {
            this.pager.setCurrentItem(Page.MAIN.id);
            return true;
        }
        return false;
    }

    private void initPagerAdapter(FragmentActivity activity){
        List<Fragment> fragments = new ArrayList<>();

        SessionManager manager = new DatabaseSessionManager(activity);

        fragments.add(getStoryLogFragment(manager));
        fragments.add(getSessionViewFragment(activity, manager));

        pager.setAdapter(new ScreenSlidePagerAdapter(activity.getSupportFragmentManager(),fragments));
        pager.setCurrentItem(Page.MAIN.id);
    }

    private Fragment getStoryLogFragment(SessionManager manager){

        MyStoryView storyLog = new MyStoryFragment();
        MyStoryPresenter storyPresenter = new MyStoryPresenter(storyLog,manager);

        storyLog.setPresenter(storyPresenter);

        return (Fragment) storyLog;
    }

    private Fragment getSessionViewFragment(Activity activity,SessionManager manager){

        MainSessionView sessionFragmentView = new MainSessionFragment();

     return (Fragment) sessionFragmentView;
    }
}