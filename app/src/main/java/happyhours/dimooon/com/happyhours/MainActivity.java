package happyhours.dimooon.com.happyhours;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import happyhours.dimooon.com.happyhours.model.database.manager.DatabaseSessionManager;
import happyhours.dimooon.com.happyhours.model.database.manager.SessionManager;
import happyhours.dimooon.com.happyhours.view.custom.pager.MainPager;
import happyhours.dimooon.com.happyhours.view.fragments.toolbar.HappyToolbar;

public class MainActivity extends AppCompatActivity {

    private MainPager pager;
    private SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new DatabaseSessionManager(this);

        createPageNavigation(manager);

        addActionBar();
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


    private void createPageNavigation(SessionManager manager){
        pager = new MainPager(this, (ViewPager) findViewById(R.id.pager),manager);
    }

    private void addActionBar() {
        HappyToolbar toolbar = (HappyToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.initView();
    }
}