package ojass20.nitjsr.in.ojass.Activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;


import androidx.appcompat.app.AppCompatActivity;
import ojass20.nitjsr.in.ojass.R;
import ojass20.nitjsr.in.ojass.Utils.SharedPrefManager;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIMER =  1250;
    private static final int UPDATE_REQUEST_CODE = 101;
    private SharedPrefManager sharedPrefManager;
    private ImageView mImageView;
    private ImageView ivSplash;
    private static final String SPLASH_SCREEN_IMAGE = "https://lh3.googleusercontent.com/-OJj_lIygYuw/Wnt49IcYdVI/AAAAAAAABKo/coVAn3ShO6EgEjGjUr3jwtg5KxofE87IgCL0BGAYYCw/h441/23%2B%25282%2529.jpg";
    private static final int WALKTHROUGH = 1;
    private static final int LOGIN = 2;
    private static final int DASHBOARD = 3;
    private int destinationFlag;
    private AppUpdateManager appUpdateManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

//        Utilities.changeStatusBarColor(this);

        mImageView = findViewById(R.id.iv_splash_logo);

        ivSplash = findViewById(R.id.iv_splashscreen);
        Glide.with(this).load(R.drawable.splashscreen).into(ivSplash);
        destinationFlag = getDestinationActivity();
        animation();
        doTheDelayStuff();
    }

    private void animation() {

        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(mImageView, "scaleX", 5.0F, 1.0F);
        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleXAnimation.setDuration(1200);

        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(mImageView, "scaleY", 5.0F, 1.0F);
        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleYAnimation.setDuration(1200);

        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(mImageView, "alpha", 0.0F, 1.0F);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setDuration(1200);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
        animatorSet.start();

    }

    private void doTheDelayStuff() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                inAppUpdate();
                switch (destinationFlag){
                    case DASHBOARD:
                        moveToMainActivity();
                        break;
                    case LOGIN:
                        moveToLoginPage();
                        break;
                    case WALKTHROUGH:
                        moveToWalkthrough();
                        break;
                }
            }
        }, SPLASH_SCREEN_TIMER);
    }
    private void inAppUpdate() {
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(this);

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {

                Log.e("AVAILABLE_VERSION_CODE", appUpdateInfo.availableVersionCode()+"");
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        // For a flexible update, use AppUpdateType.FLEXIBLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    // Request the update.

                    try {
                        appUpdateManager.startUpdateFlowForResult(
                                // Pass the intent that is returned by 'getAppUpdateInfo()'.
                                appUpdateInfo,
                                // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                                AppUpdateType.IMMEDIATE,
                                // The current activity making the update request.
                                SplashScreen.this,
                                // Include a request code to later monitor this update request.
                                UPDATE_REQUEST_CODE);
                    } catch (IntentSender.SendIntentException ignored) {

                    }
                }
            }
        });

        appUpdateManager.registerListener(installStateUpdatedListener);

    }
    //lambda operation used for below listener
    InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(InstallState installState) {
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                SplashScreen.this.popupSnackbarForCompleteUpdate();
            } else
                Log.e("UPDATE", "Not downloaded yet");
        }
    };


    private void popupSnackbarForCompleteUpdate() {

        Snackbar snackbar =
                Snackbar.make(
                        findViewById(android.R.id.content),
                        "Update almost finished!",
                        Snackbar.LENGTH_INDEFINITE);
        //lambda operation used for below action
        snackbar.setAction(this.getString(R.string.restart), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUpdateManager.completeUpdate();
            }
        });
        snackbar.show();
    }
    private int getDestinationActivity(){
        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager.isFirstOpen()){
            sharedPrefManager.setIsFirstOpen(false);
            return WALKTHROUGH;
        } else {
            if (sharedPrefManager.isLoggedIn()){
                return DASHBOARD;
            } else {
                return LOGIN;
            }
        }
    }

    private void moveToLoginPage() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void moveToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void moveToWalkthrough() {
        startActivity(new Intent(this, WalkThroughActivity.class));
        finish();
    }
}