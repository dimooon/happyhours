package happyhours.dimooon.com.happyhours;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import happyhours.dimooon.com.happyhours.model.database.data.DatabaseSessionDataProvider;
import happyhours.dimooon.com.happyhours.model.database.data.SessionDataProvider;
import happyhours.dimooon.com.happyhours.service.ActivityService;
import happyhours.dimooon.com.happyhours.view.custom.pager.MainPager;
import happyhours.dimooon.com.happyhours.view.fragments.toolbar.HappyToolbar;

public class MainActivity extends AppCompatActivity {

    private MainPager pager;
    private SessionDataProvider manager;
    private boolean bound = false;
    private ActivityService activityService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new DatabaseSessionDataProvider(this);
        addActionBar();

        startService(new Intent(this, ActivityService.class));
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

    @Override
    protected void onStart() {
        super.onStart();

        Intent activityServiceIntent = new Intent(this, ActivityService.class);
        bindService(activityServiceIntent, ActivityServuceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bound) {
            unbindService(ActivityServuceConnection);
            bound = false;
        }
    }

    private void createPageNavigation(){
        pager = new MainPager(this, (ViewPager) findViewById(R.id.pager),manager,activityService);
    }

    private void addActionBar() {
        HappyToolbar toolbar = (HappyToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.initView();
    }

    private ServiceConnection ActivityServuceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ActivityService.ActivityServiceBinder binder = (ActivityService.ActivityServiceBinder) service;
            activityService = binder.getService();
            bound = true;
            createPageNavigation();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };
}