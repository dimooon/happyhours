package happyhours.dimooon.com.happyhours.view.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.model.database.manager.DatabaseSessionManager;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.view.fragments.dialog.StartSessionDialog;
import happyhours.dimooon.com.happyhours.view.session.SessionView;

public class MainFragment extends Fragment {


    private SessionView sessionView;
    private StartSessionDialog.CreateSessionDialogListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public static final String TAG = MainFragment.class.getSimpleName();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final View startButton = getView().findViewById(R.id.startSessionButton);
        final View stopButton = getView().findViewById(R.id.stopSessionButton);

        sessionView = (SessionView) getView().findViewById(R.id.sessionView);

        listener = new StartSessionDialog.CreateSessionDialogListener() {

            @Override
            public void onSessionCreated(HappySession session) {
                sessionView.setSession(MainFragment.this.getActivity(), session);
                changeSessionState(true, startButton, stopButton);
            }
        };

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeSessionState(R.id.startSessionButton == view.getId(), startButton, stopButton);
            }
        });
    }

    private void changeSessionState(boolean start,View startButton,View stopButton){

        if(start){
            if(!sessionView.isSessionStarted()){
                new StartSessionDialog().show(getActivity(), new DatabaseSessionManager(getActivity()), listener);
                return;
            }

            zoomImageFromThumb(getView().findViewById(R.id.startSessionButton),getView().findViewById(R.id.stopSessionButton) );

            sessionView.startSession();
        }else{
            sessionView.stopSession();

            if(sessionView.isSessionStarted()){
                ((Button)startButton).setText("Back to Work");
            }

            startButton.setVisibility(View.VISIBLE);

        }

        sessionView.setVisibility(start ? View.VISIBLE : View.INVISIBLE);
    }

    private Animator mCurrentAnimator;

    private void zoomImageFromThumb(final View startButton,final View stopButton) {

        final int mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        startButton.getGlobalVisibleRect(startBounds);

        getActivity().findViewById(R.id.container).getGlobalVisibleRect(finalBounds, globalOffset);

        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        final float startScale;

        if ((float) finalBounds.width() / finalBounds.height()  > (float) startBounds.width() / startBounds.height()) {

            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;

        } else {

            startScale = (float) startBounds.height() / finalBounds.height();

            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;

            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;

        }

        startButton.setAlpha(0f);
        stopButton.setVisibility(View.VISIBLE);

        stopButton.setPivotX(0f);
        stopButton.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(stopButton, View.X,startBounds.left, finalBounds.right - stopButton.getWidth()))
                .with(ObjectAnimator.ofFloat(stopButton, View.Y,startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(stopButton, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(stopButton, View.SCALE_Y, startScale, 1f));

        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });

        set.start();

        mCurrentAnimator = set;

        final float startScaleFinal = startScale;

        Log.e(TAG,"zoom: "+startScaleFinal);

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeSessionState(R.id.startSessionButton == view.getId(),getView().findViewById(R.id.startSessionButton),getView().findViewById(R.id.stopSessionButton));

                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator.ofFloat(stopButton, View.X, startButton.getLeft()))
                        .with(ObjectAnimator.ofFloat(stopButton, View.Y, startBounds.top))
                        .with(ObjectAnimator.ofFloat(stopButton, View.SCALE_X, 1f,(((float)startButton.getWidth())/stopButton.getWidth())))
                        .with(ObjectAnimator.ofFloat(stopButton, View.SCALE_Y, 1f, (((float)startButton.getWidth())/stopButton.getWidth())));

                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());

                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startButton.setAlpha(1f);
                        stopButton.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        startButton.setAlpha(1f);
                        stopButton.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }
}