package happyhours.dimooon.com.happyhours.view.custom.toolbar;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import happyhours.dimooon.com.happyhours.R;

public class HappyToolbar extends Toolbar implements ActionToolBar{

    private View titleBar;
    private View addTimerBar;

    public HappyToolbar(Context context) {
        super(context);
        initView();
    }

    public HappyToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public HappyToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        titleBar = findViewById(R.id.toolbar_action_bar);
        addTimerBar = findViewById(R.id.toolbar_add_create_timer);
    }

    @Override
    public void updateTitle(String title) {
        setTitle(title);
    }

    @Override
    public void changeVisibilityForAddButton(boolean show) {

    }

    @Override
    public void addView() {

    }

    @Override
    public void expand() {

    }

    @Override
    public void collapse() {

    }

    @Override
    public void showAddTimerBar(boolean show) {
        addTimerBar.setVisibility(show ? View.VISIBLE: View.GONE);
    }
}