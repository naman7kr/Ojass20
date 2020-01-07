package ojass20.nitjsr.in.ojass.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
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
    private ArrayList<ValueAnimator> mAnimators;
    private ArrayList<Integer> mAngles;
    private ArrayList<ImageView> mImageViews;
    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initializeInstanceVariables();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animate();
            }
        }, 100);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void initializeInstanceVariables() {
        mEventsInterested = findViewById(R.id.events);
        mMyEvents = findViewById(R.id.comp);
        mMerchandise = findViewById(R.id.merch);
        mQR = findViewById(R.id.qr);
        mDevelopers = findViewById(R.id.dev);
        mDetailsLayout = findViewById(R.id.details);
        mAnimators = new ArrayList<>();
        mAngles = new ArrayList<>();
        mImageViews = new ArrayList<>();
        mBack = findViewById(R.id.back_arrow);
    }

    private void animate() {
        mAngles.add(33);
        mAngles.add(65);
        mAngles.add(89);
        mAngles.add(118);
        mAngles.add(145);

        mAnimators.add(animateView(mEventsInterested, 1000, 0));
        mAnimators.add(animateView(mMyEvents, 1000, 1));
        mAnimators.add(animateView(mMerchandise, 1000, 2));
        mAnimators.add(animateView(mQR, 1000, 3));
        mAnimators.add(animateView(mDevelopers, 1000, 4));

        recursiveAnimate(0);

        mImageViews.add((ImageView) findViewById(R.id.events_iv));
        mImageViews.add((ImageView) findViewById(R.id.merch_iv));
        mImageViews.add((ImageView) findViewById(R.id.qr_iv));
        mImageViews.add((ImageView) findViewById(R.id.comp_iv));
        mImageViews.add((ImageView) findViewById(R.id.dev_iv));

        TranslateAnimation tr = new TranslateAnimation(0.0f, 0.0f, 0, 30);
        tr.setDuration(100);

        for (int i = 0; i < mImageViews.size(); i++)
            mImageViews.get(i).startAnimation(tr);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        mDetailsLayout.startAnimation(animation);
        animation.setFillAfter(true);
    }

    private ValueAnimator animateView(final LinearLayout linearLayout, long duration, int index) {
        ConstraintLayout.LayoutParams lP = (ConstraintLayout.LayoutParams) linearLayout.getLayoutParams();
        ValueAnimator anim = ValueAnimator.ofInt(210, mAngles.get(index));
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

    private void recursiveAnimate(final int index) {
        if (index >= mAnimators.size())
            return;
        mAnimators.get(index).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recursiveAnimate(index + 1);
            }
        }, 200);
    }
}