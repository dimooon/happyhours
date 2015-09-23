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
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.session.SessionViewPresenter;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.toolbar.ToolbarPresenter;
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

    public MainPager(FragmentActivity activity,ViewPager pager,SessionManager manager) {

        this.pager = pager;

        initPagerAdapter(activity,manager);
    }

    @Override
    public boolean handleBackPressed() {

        boolean overrideBackButton = Page.MAIN.id != this.pager.getCurrentItem();

        if(overrideBackButton){
            this.pager.setCurrentItem(Page.MAIN.id);
        }

        return overrideBackButton;
    }

    private void initPagerAdapter(FragmentActivity activity, SessionManager manager){
        List<Fragment> fragments = new ArrayList<>();

        fragments.add(getStoryLogFragment(manager));
        fragments.add(getSessionViewFragment(activity, manager));

        pager.setAdapter(new ScreenSlidePagerAdapter(activity.getSupportFragmentManager(),fragments));
        pager.setCurrentItem(Page.MAIN.id);
    }

    private Fragment getStoryLogFragment(SessionManager manager){

        MyStoryView storyLog = new MyStoryFragment(manager);

        return (Fragment) storyLog;
    }

    private Fragment getSessionViewFragment(Activity activity,SessionManager manager){

        MainSessionView sessionFragmentView = new MainSessionFragment(activity,manager);

     return (Fragment) sessionFragmentView;
    }
}