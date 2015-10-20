package happyhours.dimooon.com.happyhours.view.custom;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import happyhours.dimooon.com.happyhours.R;

/**
 * Created by dmytro on 10/15/15.
 */
public class HappyKeyboardView  implements KeyboardView, View.OnClickListener {

    private View keyboardView;

    private Button mBSpace, mBdone, mBack, mBChange, mNum;
    private RelativeLayout mKLayout;
    private boolean isEdit = true;
    private String mUpper = "upper", mLower = "lower";
    private HappyEditText editText;

    private String sL[] = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
            "x", "y", "z", "ç", "à", "é", "è", "û", "î" };

    private String cL[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
            "X", "Y", "Z", "ç", "à", "é", "è", "û", "î" };

    private String nS[] = { "!", ")", "'", "#", "3", "$", "%", "&", "8", "*",
            "?", "/", "+", "-", "9", "0", "1", "4", "@", "5", "7", "(", "2",
            "\"", "6", "_", "=", "]", "[", "<", ">", "|" };
    private Button mB[] = new Button[32];

    public HappyKeyboardView(View keyboardView) {
        this.keyboardView = keyboardView;

        mKLayout = (RelativeLayout) keyboardView.findViewById(R.id.xKeyBoard);

        setKeys();
    }

    @Override
    public View getView() {
        return this.keyboardView;
    }

    @Override
    public void bindEditText(HappyEditText editText) {
        this.editText = editText;
    }


    @Override
    public void onClick(View v) {

        if (v == mBChange) {

            if (mBChange.getTag().equals(mUpper)) {
                changeSmallLetters();
                changeSmallTags();
            } else if (mBChange.getTag().equals(mLower)) {
                changeCapitalLetters();
                changeCapitalTags();
            }

        } else if (v != mBdone && v != mBack && v != mBChange && v != mNum) {
            addText(v);

        } else if (v == mBdone) {

        } else if (v == mBack) {
            isBack(v);
        } else if (v == mNum) {
            String nTag = (String) mNum.getTag();
            if (nTag.equals("num")) {
                changeSyNuLetters();
                changeSyNuTags();
                mBChange.setVisibility(Button.GONE);

            }
            if (nTag.equals("ABC")) {
                changeCapitalLetters();
                changeCapitalTags();
            }

        }

    }

    private void addText(View v) {
        if (isEdit == true) {
            String b = "";
            b = (String) v.getTag();
            if (b != null) {
                editText.append(b);

            }
        }

    }

    private void isBack(View v) {
        if (isEdit == true) {
            CharSequence cc = editText.getText();
            if (cc != null && cc.length() > 0) {
                {
                    editText.setText("");
                    editText.append(cc.subSequence(0, cc.length() - 1));
                }

            }
        }

    }
    private void changeSmallLetters() {
        mBChange.setVisibility(Button.VISIBLE);
        for (int i = 0; i < sL.length; i++)
            mB[i].setText(sL[i]);
        mNum.setTag("12#");
    }
    private void changeSmallTags() {
        for (int i = 0; i < sL.length; i++)
            mB[i].setTag(sL[i]);
        mBChange.setTag("lower");
        mNum.setTag("num");
    }
    private void changeCapitalLetters() {
        mBChange.setVisibility(Button.VISIBLE);
        for (int i = 0; i < cL.length; i++)
            mB[i].setText(cL[i]);
        mBChange.setTag("upper");
        mNum.setText("12#");

    }

    private void changeCapitalTags() {
        for (int i = 0; i < cL.length; i++)
            mB[i].setTag(cL[i]);
        mNum.setTag("num");

    }

    private void changeSyNuLetters() {

        for (int i = 0; i < nS.length; i++)
            mB[i].setText(nS[i]);
        mNum.setText("ABC");
    }

    private void changeSyNuTags() {
        for (int i = 0; i < nS.length; i++)
            mB[i].setTag(nS[i]);
        mNum.setTag("ABC");
    }

    private void setKeys() {
        mB[0] = (Button) mKLayout.findViewById(R.id.xA);
        mB[1] = (Button) mKLayout.findViewById(R.id.xB);
        mB[2] = (Button) mKLayout.findViewById(R.id.xC);
        mB[3] = (Button) mKLayout.findViewById(R.id.xD);
        mB[4] = (Button) mKLayout.findViewById(R.id.xE);
        mB[5] = (Button) mKLayout.findViewById(R.id.xF);
        mB[6] = (Button) mKLayout.findViewById(R.id.xG);
        mB[7] = (Button) mKLayout.findViewById(R.id.xH);
        mB[8] = (Button) mKLayout.findViewById(R.id.xI);
        mB[9] = (Button) mKLayout.findViewById(R.id.xJ);
        mB[10] = (Button) mKLayout.findViewById(R.id.xK);
        mB[11] = (Button) mKLayout.findViewById(R.id.xL);
        mB[12] = (Button) mKLayout.findViewById(R.id.xM);
        mB[13] = (Button) mKLayout.findViewById(R.id.xN);
        mB[14] = (Button) mKLayout.findViewById(R.id.xO);
        mB[15] = (Button) mKLayout.findViewById(R.id.xP);
        mB[16] = (Button) mKLayout.findViewById(R.id.xQ);
        mB[17] = (Button) mKLayout.findViewById(R.id.xR);
        mB[18] = (Button) mKLayout.findViewById(R.id.xS);
        mB[19] = (Button) mKLayout.findViewById(R.id.xT);
        mB[20] = (Button) mKLayout.findViewById(R.id.xU);
        mB[21] = (Button) mKLayout.findViewById(R.id.xV);
        mB[22] = (Button) mKLayout.findViewById(R.id.xW);
        mB[23] = (Button) mKLayout.findViewById(R.id.xX);
        mB[24] = (Button) mKLayout.findViewById(R.id.xY);
        mB[25] = (Button) mKLayout.findViewById(R.id.xZ);
        mB[26] = (Button) mKLayout.findViewById(R.id.xS1);
        mB[27] = (Button) mKLayout.findViewById(R.id.xS2);
        mB[28] = (Button) mKLayout.findViewById(R.id.xS3);
        mB[29] = (Button) mKLayout.findViewById(R.id.xS1);
        mB[30] = (Button) mKLayout.findViewById(R.id.xS1);
        mB[31] = (Button) mKLayout.findViewById(R.id.xS1);
        mBSpace = (Button) mKLayout.findViewById(R.id.xSpace);
        mBdone = (Button) mKLayout.findViewById(R.id.xS1);
        mBChange = (Button) mKLayout.findViewById(R.id.xChange);
        mBack = (Button) mKLayout.findViewById(R.id.xBack);
        mNum = (Button) mKLayout.findViewById(R.id.xNum);
        for (int i = 0; i < mB.length; i++)
            mB[i].setOnClickListener(this);
        mBSpace.setOnClickListener(this);
        mBdone.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mBChange.setOnClickListener(this);
        mNum.setOnClickListener(this);

    }
}