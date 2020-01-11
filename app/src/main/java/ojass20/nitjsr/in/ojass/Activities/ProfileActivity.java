package ojass20.nitjsr.in.ojass.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import ojass20.nitjsr.in.ojass.R;
import ojass20.nitjsr.in.ojass.Utils.Constants;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout mEventsInterested, mMyEvents, mMerchandise, mQR, mDevelopers;
    private static final String LOG_TAG = "Profile";
    private RelativeLayout mDetailsLayout;
    private ArrayList<ValueAnimator> mAnimators;
    private ArrayList<Integer> mAngles;
    private ArrayList<ImageView> mImageViews;
    private ImageView mBack;
    private CircleImageView user_image;
    private TextView user_name;
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mauth=FirebaseAuth.getInstance();
        initializeInstanceVariables();

        mQR.setOnClickListener(this);

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

        Glide.with(this).load(mauth.getCurrentUser().getPhotoUrl()).into(user_image);
        user_name.setText(mauth.getCurrentUser().getDisplayName());
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

        user_image = findViewById(R.id.image_profile_activity);
        user_name = findViewById(R.id.username_profile_activity);
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

    @Override
    public void onClick(View view) {
        if(view == mQR){
            showQrDialog();
        }
    }
    public void showQrDialog() {
        final Dialog QRDialog = new Dialog(this);
        QRDialog.setContentView(R.layout.dialog_qr);
        final TextView tvOjassId = QRDialog.findViewById(R.id.tv_ojass_id);
        final ImageView ivQR = QRDialog.findViewById(R.id.iv_qr_code);
        QRDialog.getWindow().getAttributes().windowAnimations = R.style.pop_up_anim;
        QRDialog.show();
        QRDialog.findViewById(R.id.rl_qr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvOjassId.getText().toString().equals(Constants.NOT_REGISTERED)) {
                    QRDialog.dismiss();
                }
            }
        });
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid());
            ref.keepSynced(true);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Picasso.with(ProfileActivity.this).load("https://api.qrserver.com/v1/create-qr-code/?data=" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "&size=240x240&margin=10").placeholder(R.drawable.placeholder_sqaure).fit().networkPolicy(NetworkPolicy.OFFLINE).into(ivQR, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                Picasso.with(ProfileActivity.this).load("https://api.qrserver.com/v1/create-qr-code/?data=" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "&size=240x240&margin=10").placeholder(R.drawable.placeholder_sqaure).fit().into(ivQR);
                            }
                        });
                        tvOjassId.setText("Payment Due");
                        tvOjassId.setTextColor(getResources().getColor(R.color.red));
                    } else {
                        ivQR.setImageResource(R.drawable.placeholder_sqaure);
                        tvOjassId.setText(Constants.NOT_REGISTERED);
                        tvOjassId.setTextColor(Color.RED);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            ivQR.setImageResource(R.drawable.placeholder_sqaure);
            tvOjassId.setText(Constants.NOT_REGISTERED);
            tvOjassId.setTextColor(Color.RED);
        }
    }
}