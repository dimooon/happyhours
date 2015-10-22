package happyhours.dimooon.com.happyhours.view.custom.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import happyhours.dimooon.com.happyhours.model.database.manager.SessionDataProvider;
import happyhours.dimooon.com.happyhours.view.fragments.MainSessionFragment;
import happyhours.dimooon.com.happyhours.view.fragments.SelectableFragment;
import happyhours.dimooon.com.happyhours.view.fragments.StoryFragment;

public class MainPager implements Pager{

    private ViewPager pager;

    public enum Page{
        MAIN(1),STORY(2);

        Page(int pageId) {
            this.id = pageId;
        }

        public int id;
    }

    public MainPager(FragmentActivity activity,ViewPager pager,SessionDataProvider manager) {

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

    private void initPagerAdapter(FragmentActivity activity, SessionDataProvider manager){
        final List<Fragment> fragments = new ArrayList<>();

        fragments.add(getStoryLogFragment(manager));
        fragments.add(getSessionViewFragment(manager));

        pager.setAdapter(new ScreenSlidePagerAdapter(activity.getSupportFragmentManager(), fragments));
        pager.setCurrentItem(Page.MAIN.id);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                ((SelectableFragment)fragments.get(position)).onSelected();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private Fragment getStoryLogFragment(SessionDataProvider manager){
        return new StoryFragment(manager);
    }

    private Fragment getSessionViewFragment(SessionDataProvider manager){
     return new MainSessionFragment(manager);
    }
}