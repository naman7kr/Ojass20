package ojass20.nitjsr.in.ojass.Activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GestureDetectorCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import ojass20.nitjsr.in.ojass.R;

public class MainActivity extends AppCompatActivity implements
        GestureDetector.OnGestureListener {
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private NavigationView mNavigationDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private ImageView mPullUp;
    private ImageView mPullDown;
    private String LOG_TAG = "MAIN";
    private TranslateAnimation mAnimation;
    private TextView mHeading;
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeInstanceVariables();
        setUpNavigationDrawer();
        setUpAnimationForImageView(mPullUp);
        detectTouchEvents();
    }

    private void detectTouchEvents() {
        mPullUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPullUp.setEnabled(false);
                mPullDown.setEnabled(true);
                mPullDown.setVisibility(View.VISIBLE);
                mHeading.setVisibility(View.VISIBLE);
                mPullDown.setAlpha(0.0f);
                mHeading.setAllCaps(true);
                mHeading.setAlpha(0.0f);
                mHeading.animate().alpha(1.0f).setDuration(1000);
                mPullDown.animate().alpha(1.0f).setDuration(1000);
                mPullUp.animate().alpha(0.0f).setDuration(1000);
                mPullUp.setVisibility(View.GONE);
                setUpAnimationForImageView(mPullDown);
                mToolbar.setTitle("");
            }
        });

        mPullDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPullDown.setEnabled(false);
                mPullUp.setEnabled(true);
                mPullUp.setVisibility(View.VISIBLE);
                mPullUp.setAlpha(0.0f);
                mPullUp.animate().alpha(1.0f).setDuration(1000);
                mPullDown.animate().alpha(0.0f).setDuration(1000);
                mHeading.animate().alpha(0.0f).setDuration(1000);
                mPullDown.setVisibility(View.GONE);
                mToolbar.setTitle(getResources().getString(R.string.feeds));
                setUpAnimationForImageView(mPullDown);
            }
        });

        mHeading.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    private void setUpAnimationForTextView(final int code, final long mainTime, String curr) {
        long tempTime = System.currentTimeMillis();
        if (tempTime - mainTime > 500) {
            mHeading.setText("Heading");
            return;
        }
        String temp = " ";
        Log.e(LOG_TAG, curr);
        for (int i = 0; i < curr.length(); i++) {
            char ch = curr.charAt(i);
            if (code == 1) {
                if (ch == 'Z')
                    temp = temp + 'A';
                else {
                    int u = (int) ch;
                    u++;
                    temp = temp + (char) u;
                }
            } else {
                if (ch == 'A')
                    temp = temp + 'Z';
                else {
                    int u = (int) ch;
                    u--;
                    temp = temp + (char) u;
                }
            }
        }
        temp = temp.trim();
        mHeading.setText(temp);
        //Log.e(LOG_TAG, temp);
        final String x = temp;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setUpAnimationForTextView(code, mainTime, x.toUpperCase());
            }
        }, 10);
    }

    private void setUpAnimationForImageView(ImageView mImageView) {
        mAnimation.setDuration(700);
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setInterpolator(new LinearInterpolator());
        mImageView.setAnimation(mAnimation);
    }

    private void initializeInstanceVariables() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationDrawer = (NavigationView) findViewById(R.id.navigation_view);
        mPullUp = findViewById(R.id.pull_up);
        mPullDown = findViewById(R.id.pull_down);
        mAnimation = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.005f);
        mHeading = (TextView) findViewById(R.id.heading);
        mDetector = new GestureDetectorCompat(this, this);
    }

    private void setUpNavigationDrawer() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Uncomment below once all fragments have been created
        //setupDrawerContent(mNavigationDrawer);

        //Replacing back arrow with hamburger icon
        mDrawerToggle = setupDrawerToggle();
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimaryDark));
        mDrawerToggle.syncState();
        mDrawer.addDrawerListener(mDrawerToggle);

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(MainActivity.this, mDrawer, mToolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass = null;
//        switch(menuItem.getItemId()) {
//            case R.id.nav_first_fragment:
//                fragmentClass = FirstFragment.class;
//                break;
//            case R.id.nav_second_fragment:
//                fragmentClass = SecondFragment.class;
//                break;
//            case R.id.nav_third_fragment:
//                fragmentClass = ThirdFragment.class;
//                break;
//            default:
//                fragmentClass = FirstFragment.class;
//        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.notifications:
                return true;
            case R.id.profile:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() < e2.getX()) {
            Toast.makeText(this, "Swiped Right", Toast.LENGTH_LONG).show();
            setUpAnimationForTextView(1, System.currentTimeMillis(), mHeading.getText().toString().toUpperCase());
        } else {
            Toast.makeText(this, "Swiped Left", Toast.LENGTH_LONG).show();
            setUpAnimationForTextView(-1, System.currentTimeMillis(), mHeading.getText().toString().toUpperCase());
        }
        return true;
    }
}