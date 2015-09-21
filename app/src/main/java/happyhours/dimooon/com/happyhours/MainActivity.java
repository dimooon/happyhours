package happyhours.dimooon.com.happyhours;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import happyhours.dimooon.com.happyhours.model.pager.MainPager;
import happyhours.dimooon.com.happyhours.view.custom.toolbar.ActionToolBar;

public class MainActivity extends AppCompatActivity {

    private MainPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = new MainPager(this, (ViewPager) findViewById(R.id.pager));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((ActionToolBar)toolbar).initView(this);
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