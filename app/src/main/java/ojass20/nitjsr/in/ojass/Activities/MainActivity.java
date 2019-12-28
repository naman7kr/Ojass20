package ojass20.nitjsr.in.ojass.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ojass20.nitjsr.in.ojass.Adapters.FeedAdapter;
import ojass20.nitjsr.in.ojass.Fragments.HomeFragment;
import ojass20.nitjsr.in.ojass.Models.Comments;
import ojass20.nitjsr.in.ojass.Models.FeedPost;
import ojass20.nitjsr.in.ojass.Models.Likes;
import ojass20.nitjsr.in.ojass.R;
import ojass20.nitjsr.in.ojass.Utils.OjassApplication;

public class MainActivity extends AppCompatActivity implements HomeFragment.HomeFragInterface {
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private NavigationView mNavigationDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private ImageView mPullUp;
    private AlertDialog.Builder builder;
    private ImageView mPullDown;
    private String LOG_TAG = "MAIN";
    private TranslateAnimation mAnimation;
    private TextView mHeading;
    private int mInd;
    private TextView mSubHeading;
    private ProgressBar recyclerview_progress;
    private ImageView mRoundedRectangle;
    private RelativeLayout mRecyclerContainer;
    private ImageView mPlaceholerImage;


    private RecyclerView mRecyclerView;
    private FeedAdapter mFeedAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private DatabaseReference dref;
    private FirebaseAuth mauth;
    private ArrayList<FeedPost> listposts;

    private String currentuid;
    private OjassApplication ojassApplication;
    private FrameLayout homeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        builder = new AlertDialog.Builder(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("PostNo", Integer.toString(0));
        editor.apply();

        mRecyclerView = findViewById(R.id.feed_recycler_view);
        listposts = new ArrayList<>();

        mauth = FirebaseAuth.getInstance();
        currentuid = mauth.getCurrentUser().getUid();

        dref = FirebaseDatabase.getInstance().getReference().child("Feeds");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recyclerview_progress.setVisibility(View.VISIBLE);
                listposts.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //FeedPost post = ds.getValue(FeedPost.class);
                    String post_id_temp = ds.getKey();
                    boolean flag = false;

                    ArrayList<Likes> mlikes = new ArrayList<>();
                    for (DataSnapshot dslike : ds.child("likes").getChildren()) {
                        //Likes like=dslike.getValue(Likes.class);
                        String temp = dslike.getValue().toString();
                        Likes like = new Likes(temp);
                        mlikes.add(like);
                        if (temp.equals(currentuid)) {
                            flag = true;
                        }
                    }

                    ArrayList<Comments> mcomments = new ArrayList<>();
                    for (DataSnapshot dscomment : ds.child("comments").getChildren()) {
                        Comments comment = dscomment.getValue(Comments.class);
                        mcomments.add(comment);
                    }

                    String content = ds.child("content").getValue().toString();
                    String event_name = ds.child("event").getValue().toString();
                    String subevent_name = ds.child("subEvent").getValue().toString();
                    String image_url = ds.child("imageURL").getValue().toString();


                    FeedPost post = new FeedPost(flag, post_id_temp, content, event_name, image_url, subevent_name, mlikes, mcomments);

                    Log.e("vila", post.getImageURL());
                    listposts.add(post);
                }
                setUpRecyclerView();
                Log.e("VIVZ", "onDataChange: listposts count = " + listposts.size());
                mFeedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        initializeInstanceVariables();
        setUpNavigationDrawer();
        //setUpRecyclerView();


        setUpNavigationDrawer();
        setUpAnimationForImageView(mPullUp);
        detectTouchEvents();


    }

    private void setUpRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mFeedAdapter = new FeedAdapter(this, getSupportFragmentManager(), listposts, currentuid);
        mRecyclerView.setAdapter(mFeedAdapter);
        recyclerview_progress.setVisibility(View.GONE);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String numpost = preferences.getString("PostNo", "");
        mRecyclerView.scrollToPosition(Integer.parseInt(numpost));
    }



    private void detectTouchEvents() {
        //pullup button click
        mPullUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hide mPullUp
                AlphaAnimation anim = new AlphaAnimation(1.0f,0.0f);
                anim.setDuration(100);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mPullUp.setAlpha(0.0f);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mPullUp.startAnimation(anim);
                // create fragment
                openFragment();
            }
        });
    }
    @Override
    public void onCancel() {
        //show mPullUp
        AlphaAnimation anim = new AlphaAnimation(0.0f,1.0f);
        anim.setDuration(1000);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mPullUp.setAlpha(1.0f);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mPullUp.startAnimation(anim);
        closeFragment();
    }



    private void openFragment() {
        HomeFragment homeFrag = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_bottom,R.anim.no_anim);
        transaction.add(R.id.home_container,homeFrag);
        transaction.commit();
        Toast.makeText(ojassApplication, "Click", Toast.LENGTH_SHORT).show();
    }
    private void closeFragment() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.home_container);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.no_anim,R.anim.slide_out_bottom);
        transaction.remove(f).commit();
    }

    private void setUpAnimationForImageView(ImageView mImageView) {
        mAnimation.setDuration(700);
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setInterpolator(new LinearInterpolator());
        mImageView.setAnimation(mAnimation);
    }

    private void initializeInstanceVariables() {
        ojassApplication = OjassApplication.getInstance();
        homeContainer = findViewById(R.id.home_container);
        recyclerview_progress = findViewById(R.id.recycler_view_progress);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationDrawer = (NavigationView) findViewById(R.id.navigation_view);
        mPullUp = findViewById(R.id.pull_up);

        mAnimation = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.005f);

        mInd = 0;
        mSubHeading = findViewById(R.id.sub_heading);
        mRecyclerContainer = findViewById(R.id.recycler_container);
        mPlaceholerImage = findViewById(R.id.placeholer_image_view);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        float x = (float) Math.sqrt(convertDpToPixel(125, this) * convertDpToPixel(125, this) - convertDpToPixel(41, this) * convertDpToPixel(41, this));
        float x1 = (float) Math.sqrt(convertDpToPixel(125, this) * convertDpToPixel(125, this) - convertDpToPixel(57, this) * convertDpToPixel(57, this));

        float m1 = width / 2 - x;
        m1 = m1 - convertDpToPixel(9, this);
        m1 = m1 + (x - x1);
        float m2 = m1 + 2 * x1;
    }

    private void setUpNavigationDrawer() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Uncomment below once all fragments have been created
        setupDrawerContent(mNavigationDrawer);

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
        Log.d("ak47", "selectDrawerItem: " + menuItem.getItemId());
        switch (menuItem.getItemId()) {
            case R.id.events:
                startActivity(new Intent(MainActivity.this, EventsActivity.class));
                break;
            case R.id.itinerary:
                startActivity(new Intent(MainActivity.this, ItineraryActivity.class));
                break;
            case R.id.help:
                startActivity(new Intent(MainActivity.this, Help.class));
                break;
            case R.id.team:
                startActivity(new Intent(MainActivity.this, TeamActivity.class));
                break;
            case R.id.developers:
                startActivity(new Intent(MainActivity.this, DeveloperActivity.class));
                break;
            case R.id.logout:
                mauth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mDrawer.closeDrawers();
    }

    public static float convertDpToPixel(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
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
                startActivity(new Intent(this, ItineraryActivity.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            case R.id.emergency:
                showList();
        }

        return super.onOptionsItemSelected(item);
    }

    public void showList() {

        final ArrayList<String> emer = new ArrayList<>();
        emer.add("Emergency");
        emer.add("Police");
        emer.add("Fire");
        emer.add("Ambulance");
        emer.add("Gas Leakage");
        emer.add("Tourist-Helpline");
        emer.add("Child-Helpline");
        emer.add("Blood-Requirement");
        emer.add("Women-Helpline");
        emer.add("Ambulance Network (Emergency or Non-Emergency)");


        final ArrayList<String> num = new ArrayList<>();

        num.add("112");
        num.add("100");
        num.add("102");
        num.add("108");
        num.add("1906");
        num.add("1363");
        num.add("1098");
        num.add("104");
        num.add("181");
        num.add("09343180000");


        builder.setTitle("Emergency Numbers");
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, emer);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent.setData(Uri.parse("tel:" + num.get(which)));
                startActivity(intent);

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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



}