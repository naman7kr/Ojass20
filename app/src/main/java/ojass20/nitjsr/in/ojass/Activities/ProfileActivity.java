package ojass20.nitjsr.in.ojass.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ojass20.nitjsr.in.ojass.R;

public class ProfileActivity extends AppCompatActivity {

    private LinearLayout mEventsInterested, mMyEvents, mMerchandise, mQR, mDevelopers;
    private static final String LOG_TAG = "Profile";
    private RelativeLayout mDetailsLayout;
    ArrayList<ValueAnimator> mAnimators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initializeInstanceVariables();
        animate();

    }

    private void initializeInstanceVariables() {
        mEventsInterested = findViewById(R.id.events);
        mMyEvents = findViewById(R.id.comp);
        mMerchandise = findViewById(R.id.merch);
        mQR = findViewById(R.id.qr);
        mDevelopers = findViewById(R.id.dev);
        mDetailsLayout = findViewById(R.id.details);
        mAnimators = new ArrayList<>();
    }

    private void animate() {
        mAnimators.add(animateView(mEventsInterested, 1000));
        mAnimators.add(animateView(mMyEvents, 1000));
        mAnimators.add(animateView(mMerchandise, 1000));
        mAnimators.add(animateView(mQR, 1000));
        mAnimators.add(animateView(mDevelopers, 1000));

        for (int i = 0; i < mAnimators.size(); i++)
            mAnimators.get(i).start();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        mDetailsLayout.startAnimation(animation);
        animation.setFillAfter(true);
    }

    private ValueAnimator animateView(final LinearLayout linearLayout, long duration) {
        ConstraintLayout.LayoutParams lP = (ConstraintLayout.LayoutParams) linearLayout.getLayoutParams();
        int angle = (int) lP.circleAngle;
        ValueAnimator anim = ValueAnimator.ofInt(210, angle);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) linearLayout.getLayoutParams();
                layoutParams.circleAngle = val;
                linearLayout.setLayoutParams(layoutParams);
            }
        });
        anim.setDuration(duration);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        return anim;
    }
}