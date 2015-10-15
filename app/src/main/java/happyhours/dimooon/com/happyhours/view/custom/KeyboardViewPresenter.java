package happyhours.dimooon.com.happyhours.view.custom;

import happyhours.dimooon.com.happyhours.tools.animation.WidthAnimation;

/**
 * Created by dmytro on 10/15/15.
 */
public class KeyboardViewPresenter {

    private KeyboardView keyboard;
    private WidthAnimation keyboardAnimation;
    private HappyEditText editText;

    public KeyboardViewPresenter(KeyboardView keyboard) {
        this.keyboard = keyboard;
        keyboardAnimation = new WidthAnimation();
    }

    public void bindEditText(HappyEditText editText){

        this.editText = editText;
        keyboard.bindEditText(this.editText);
    }

    public void showKeyboard(){
        keyboardAnimation.animateIn(this.keyboard.getView());
    }

    public void close(){
        keyboardAnimation.animateOut(this.keyboard.getView());
    }
}