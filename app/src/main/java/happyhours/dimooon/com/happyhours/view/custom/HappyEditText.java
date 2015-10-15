package happyhours.dimooon.com.happyhours.view.custom;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by dmytro on 10/15/15.
 */
public class HappyEditText extends TextView {

    private KeyboardViewPresenter presenter;

    public HappyEditText(Context context) {
        super(context);
        init();
    }

    public HappyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HappyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setInputType(InputType.TYPE_NULL);
        requestFocus();
    }

    public void bindToKeyboard(KeyboardViewPresenter presenter){
        this.presenter = presenter;
        this.presenter.showKeyboard();
        this.presenter.bindEditText(this);
    }

    public void unbindFromKeyboard(){
        this.presenter.close();
        this.presenter.bindEditText(null);
        this.presenter = null;
    }
}