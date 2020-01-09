package ojass20.nitjsr.in.ojass.Activities;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import androidx.appcompat.app.AlertDialog;

import androidx.fragment.app.FragmentTransaction;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import me.relex.circleindicator.CircleIndicator;
import ojass20.nitjsr.in.ojass.Adapters.FeedAdapter;
import ojass20.nitjsr.in.ojass.Adapters.PosterAdapter;
import ojass20.nitjsr.in.ojass.Fragments.CommentsFragment;
import ojass20.nitjsr.in.ojass.Fragments.HomeFragment;
import ojass20.nitjsr.in.ojass.Models.BranchHeadModal;
import ojass20.nitjsr.in.ojass.Models.BranchModal;
import ojass20.nitjsr.in.ojass.Models.Comments;
import ojass20.nitjsr.in.ojass.Models.CoordinatorsModel;
import ojass20.nitjsr.in.ojass.Models.EventModel;
import ojass20.nitjsr.in.ojass.Models.FeedPost;
import ojass20.nitjsr.in.ojass.Models.Likes;
import ojass20.nitjsr.in.ojass.Models.RulesModel;
import ojass20.nitjsr.in.ojass.Utils.Constants;
import ojass20.nitjsr.in.ojass.Utils.OjassApplication;
import ojass20.nitjsr.in.ojass.R;
import ojass20.nitjsr.in.ojass.Utils.OjassApplication;
import ojass20.nitjsr.in.ojass.Utils.RecyclerClickInterface;

import static ojass20.nitjsr.in.ojass.Utils.Constants.FIREBASE_REF_IMG_SRC;
import static ojass20.nitjsr.in.ojass.Utils.Constants.FIREBASE_REF_POSTERIMAGES;
import static ojass20.nitjsr.in.ojass.Utils.Constants.SubEventsMap;
import static ojass20.nitjsr.in.ojass.Utils.Constants.eventNames;
import static ojass20.nitjsr.in.ojass.Utils.Constants.updateSubEventsArray;
import static ojass20.nitjsr.in.ojass.Utils.StringEqualityPercentCheckUsingJaroWinklerDistance.getSimilarity;

public class MainActivity extends AppCompatActivity implements HomeFragment.HomeFragInterface, ViewPager.OnPageChangeListener, FeedAdapter.CommentClickInterface {
    private static final String LOG_TAG = "Main";
    private DrawerLayout mDrawer;
    private View mDrwawerHeaderView;
    private Toolbar mToolbar;
    private NavigationView mNavigationDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private ImageView mPullUp;
    private AlertDialog.Builder builder;
    private TranslateAnimation mAnimation;
    private ProgressBar recyclerview_progress;

    //Poster shit
    private static final int BANNER_DELAY_TIME = 5 * 1000;
    private static final int BANNER_TRANSITION_DELAY = 1200;
    private Runnable runnable;
    private Handler handler;
    private boolean firstScroll = true;

    private RecyclerView mRecyclerView;
    private FeedAdapter mFeedAdapter;
    private PosterAdapter mPosterAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private Boolean isCommentsFragmentOpen;

    private DatabaseReference dref;
    private FirebaseAuth mauth;
    private ArrayList<FeedPost> listposts;

    private String currentuid;
    private OjassApplication ojassApplication;
    public ProgressDialog progressDialog;
    public static ArrayList<EventModel> data;
    public static HashMap<String, BranchModal> branchData;
    private FrameLayout homeContainer;
    private ViewPager viewPager;
    private CircleIndicator indicator;
    private SwipeRefreshLayout refreshLayout;
    private NestedScrollView scrollView;
    private boolean mScrollDown = false;
    private boolean isFragOpen = false;
    private Handler backHandler;
    private int backFlag = 0;
    private String currentVersion;
    String urlOfApp = "https://play.google.com/store/apps/details?id=ojass20.nitjsr.in.ojass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        init();
        initializeInstanceVariables();

        setSlider();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("PostNo", Integer.toString(0));
        editor.apply();
        fetchBranchHead();

        fetchFeedsDataFromFirebase();

        setUpNavigationDrawer();
        //setUpRecyclerView();

        setUpAnimationForImageView(mPullUp);
        detectTouchEvents();

        hidePullUpArrowOnScroll();
        compareAppVersion();
        refresh();

    }


    private void fetchBranchHead() {
        branchData = new HashMap<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Branches");
        ref.keepSynced(true);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                branchData.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    eventNames.add(ds.getKey());
                    String about = ds.child("about").getValue().toString();
                    ArrayList<BranchHeadModal> bh_list = new ArrayList<>();
                    for (DataSnapshot d : ds.child("head").getChildren()) {
                        String cn, name, url, wn;
                        name = d.child("name").getValue().toString();
                        cn = d.child("cn").getValue().toString();
                        wn = d.child("wn").getValue().toString();
                        url = d.child("url").getValue().toString();
                        bh_list.add(new BranchHeadModal(cn, name, url, wn));
                    }
                    branchData.put(ds.getKey(), new BranchModal(about, bh_list));
                }
                eventStuff();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void init() {
        homeContainer = findViewById(R.id.home_container);
        recyclerview_progress = findViewById(R.id.recycler_view_progress);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationDrawer = (NavigationView) findViewById(R.id.navigation_view);

        mDrwawerHeaderView = mNavigationDrawer.inflateHeaderView(R.layout.nav_header);
        mPullUp = findViewById(R.id.pull_up);

        mRecyclerView = findViewById(R.id.feed_recycler_view);
        viewPager = findViewById(R.id.viewpager_poster);
        indicator = findViewById(R.id.indicator_slider);
        refreshLayout = findViewById(R.id.swipe_refresh);
        scrollView = findViewById(R.id.nested_scroll_main);

    }

    private void initializeInstanceVariables() {
        builder = new AlertDialog.Builder(this);
        ojassApplication = OjassApplication.getInstance();
        listposts = new ArrayList<>();
        isCommentsFragmentOpen = false;

        mauth = FirebaseAuth.getInstance();
        currentuid = mauth.getCurrentUser().getUid();

        mAnimation = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.005f);

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

    void refresh() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mFeedAdapter != null)
                    mFeedAdapter.notifyDataSetChanged();
                if (mPosterAdapter != null)
                    mPosterAdapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    void hidePullUpArrowOnScroll() {
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if (scrollY > oldScrollY) {
                        //code to fetch more data for endless scrolling
                        if (mScrollDown) {
//                            Toast.makeText(ojassApplication, "SCroll down", Toast.LENGTH_SHORT).show();
                            TranslateAnimation tr = new TranslateAnimation(0.0f, 0.0f, 0, 30);
                            tr.setDuration(100);
                            mPullUp.startAnimation(tr);
                            mScrollDown = false;
                            mPullUp.setVisibility(View.INVISIBLE);
                        }
                    } else if (scrollY < oldScrollY) {
                        if (!mScrollDown) {
//                            Toast.makeText(ojassApplication, "SCroll up", Toast.LENGTH_SHORT).show();
                            mPullUp.setVisibility(View.VISIBLE);
                            TranslateAnimation tr = new TranslateAnimation(0.0f, 0.0f, 0, -30);
                            tr.setDuration(100);
                            mPullUp.startAnimation(tr);
                            mScrollDown = true;

                        }
                    }
                }
            }
        });

    }

    public void setIsCommentsFragmentOpen() {
        isCommentsFragmentOpen = true;
    }

    public void unsetIsCommentsFragmentOpen() {
        isCommentsFragmentOpen = false;
    }

    public void setSlider() {

        FirebaseDatabase dataref = FirebaseDatabase.getInstance();
        DatabaseReference imageRef = dataref.getReference(FIREBASE_REF_POSTERIMAGES);
        imageRef.keepSynced(true);
        imageRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        final int imageCount = (int) dataSnapshot.getChildrenCount();
                        String[] imageUrls = new String[imageCount];
                        String[] clickUrls = new String[imageCount];
                        int currIndex = 0;
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            imageUrls[currIndex] = dataSnapshot1.child(FIREBASE_REF_IMG_SRC).getValue().toString();
                            //clickUrls[currIndex] = dataSnapshot1.child(FIREBASE_REF_IMG_CLICK).getValue().toString();
                            Log.d("TAG", imageUrls[currIndex]);
                            currIndex++;
                        }
                        mPosterAdapter = new PosterAdapter(getApplicationContext(), imageUrls, clickUrls);
                        viewPager.setAdapter(mPosterAdapter);
                        indicator.setViewPager(viewPager);
                        viewPager.setOnPageChangeListener(MainActivity.this);
                        try {
                            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
                            mScroller.setAccessible(true);
                            mScroller.set(viewPager, new CustomScroller(viewPager.getContext(), BANNER_TRANSITION_DELAY));
                        } catch (Exception e) {
                        }

                        handler = new Handler(Looper.getMainLooper());
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                int currItem = viewPager.getCurrentItem();
                                if (currItem == imageCount - 1) {
                                    viewPager.setCurrentItem(0);
                                } else {
                                    viewPager.setCurrentItem(++currItem);
                                }
                            }
                        };
                        handler.postDelayed(runnable, BANNER_DELAY_TIME);
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (firstScroll) {
            firstScroll = false;
        } else {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            handler.postDelayed(runnable, BANNER_DELAY_TIME);
        }
    }

////    @Override
//    public void onLayoutClick(View v, int position) {
//
//    }

    @Override
    public void onCommentClick() {
//        CommentsFragment fragment = new CommentsFragment(this, , feedPosts.get(position).getPostid());
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.no_anim);
//        transaction.add(R.id.home_container, fragment);
//        transaction.commit();
    }

    private class CustomScroller extends Scroller {

        private int mDuration;

        public CustomScroller(Context context, int mDuration) {
            super(context);
            this.mDuration = mDuration;
        }

        public CustomScroller(Context context, Interpolator interpolator, int mDuration) {
            super(context, interpolator);
            this.mDuration = mDuration;
        }

        public CustomScroller(Context context, Interpolator interpolator, boolean flywheel, int mDuration) {
            super(context, interpolator, flywheel);
            this.mDuration = mDuration;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }
    }

    private void fetchFeedsDataFromFirebase() {
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
                    String timestamp = (ds.hasChild("timestamp")) ? ds.child("timestamp").getValue().toString() : "0";

                    FeedPost post = new FeedPost(timestamp, flag, post_id_temp, content, event_name, image_url, subevent_name, mlikes, mcomments);

                    Log.e("vila", post.getImageURL());
                    listposts.add(post);
                    Collections.sort(listposts);
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

//        setUpArrayList();
        setUpNavigationDrawer();
        setUpAnimationForImageView(mPullUp);
        detectTouchEvents();

    }

    private void eventStuff() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Events");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Initialising App data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        data = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data.clear();
                progressDialog.dismiss();
                try {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String about = ds.child("about").getValue(String.class);
                        String branch = ds.child("branch").getValue(String.class);
                        double ma = 0.0;
                        String bName = "";
                        for (int i = 0; i < eventNames.size(); i++) {
                            double match = getSimilarity(eventNames.get(i), branch);
                            if (match > ma) {
                                ma = match;
                                bName = eventNames.get(i);
                            }
                        }
                        branch = bName;
                        String name = ds.child("name").getValue(String.class);
                        if (SubEventsMap.containsKey(branch)) {
                            SubEventsMap.get(branch).add(name);
                            Log.e("Main", branch + "->" + name);
                        } else {
                            SubEventsMap.put(branch, new ArrayList<String>());
                            SubEventsMap.get(branch).add(name);
                            Log.e("Main", branch + "->" + name);
                        }

                        String details = ds.child("detail").getValue(String.class);
                        Long prize1 = ds.child("prize").child("first").getValue(Long.class);
                        Long prize2 = ds.child("prize").child("second").getValue(Long.class);
                        Long prize3 = ds.child("prize").child("third").getValue(Long.class);
                        Long prizeT = ds.child("prize").child("total").getValue(Long.class);

                        ArrayList<CoordinatorsModel> coordinatorsModelArrayList = new ArrayList<>();
                        coordinatorsModelArrayList.clear();

                        ArrayList<RulesModel> rulesModelArrayList = new ArrayList<>();
                        rulesModelArrayList.clear();
                        try {
                            for (DataSnapshot d : ds.child("coordinators").getChildren()) {
                                String n = d.child("name").getValue().toString();
                                String p = d.child("phone").getValue().toString();
                                coordinatorsModelArrayList.add(new CoordinatorsModel(n, p));
                            }

                            for (DataSnapshot d : ds.child("rules").getChildren()) {
                                if (d.exists()) {
                                    String s = d.child("text").getValue().toString();
                                    rulesModelArrayList.add(new RulesModel(s));
                                }
                            }
                        } catch (Exception e) {
                            Log.d("hello", ds.child("name").getValue().toString());
                        }
                        data.add(new EventModel(about, branch, details, name, prize1, prize2, prize3, prizeT, coordinatorsModelArrayList, rulesModelArrayList));
                        updateSubEventsArray();
                    }
                } catch (Exception e) {
                    if (progressDialog.isShowing()) progressDialog.dismiss();
//                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (progressDialog.isShowing()) progressDialog.dismiss();
            }
        });
    }


    private void setUpRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mFeedAdapter = new FeedAdapter(this, getSupportFragmentManager(), listposts, currentuid, this);
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
//                AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
//                anim.setDuration(100);
//                anim.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        mPullUp.setAlpha(0.0f);
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                });
//                mPullUp.startAnimation(anim);
                // create fragment
                openFragment();
            }
        });
    }

    @Override
    public void onCancel() {
        //show mPullUp
//        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
//        anim.setDuration(1000);
//        anim.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                mPullUp.setAlpha(1.0f);
//                mPullUp.setVisibility(View.VISIBLE);
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        mPullUp.startAnimation(anim);
        closeFragment();
    }


    private void openFragment() {
        HomeFragment homeFrag = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.no_anim);
        transaction.add(R.id.home_container, homeFrag);
        transaction.commit();
        isFragOpen = true;
    }

    private void closeFragment() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.home_container);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.no_anim, R.anim.slide_out_bottom);
        transaction.remove(f).commit();
        isFragOpen = false;
        isCommentsFragmentOpen = false;
    }

    private void setUpAnimationForImageView(ImageView mImageView) {
        mAnimation.setDuration(700);
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setInterpolator(new LinearInterpolator());
        mImageView.setAnimation(mAnimation);
    }


    private void setUpNavigationDrawer() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        TextView profile_name = mDrwawerHeaderView.findViewById(R.id.user_profile_name);
        profile_name.setText(mauth.getCurrentUser().getDisplayName());
        ImageView profile_picture = mDrwawerHeaderView.findViewById(R.id.user_profile_picture);
        if (mauth.getCurrentUser().getPhotoUrl() != null) {
            profile_picture.setImageDrawable(null);
            Glide.with(this).load(mauth.getCurrentUser().getPhotoUrl()).into(profile_picture);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        };

        profile_name.setOnClickListener(onClickListener);
        profile_picture.setOnClickListener(onClickListener);

        mDrwawerHeaderView.getBackground().setColorFilter(0x80000000, PorterDuff.Mode.MULTIPLY);


        //Uncomment below once all fragments have been created
        setupDrawerContent(mNavigationDrawer);

        mNavigationDrawer.getBackground().setColorFilter(0x80000000, PorterDuff.Mode.DARKEN);
        // headerView.getBackground().setColorFilter(0x80000000, PorterDuff.Mode.MULTIPLY);
        //Replacing back arrow with hamburger icon
        mDrawerToggle = setupDrawerToggle();
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
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
                        return false;
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
            case R.id.sponsor:
                startActivity(new Intent(MainActivity.this, SponsorActivity.class));
                break;
            case R.id.faqs:
                startActivity(new Intent(MainActivity.this, FaqActivity.class));
                break;
            case R.id.help:
                startActivity(new Intent(MainActivity.this, Help.class));
                break;
            case R.id.team:
                startActivity(new Intent(MainActivity.this, TeamActivity.class));
                break;
            case R.id.developers:
                Intent intent = new Intent(MainActivity.this, TeamActivity.class);
                intent.putExtra("DEV", "DEV");
                startActivity(intent);
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
                Intent intent = new Intent(this, NotificationActivity.class);
                intent.putExtra("Caller", 0);
                startActivity(intent);
                return true;
            case R.id.emergency:
                showList();
                return true;
            /**       case R.id.profile:
             startActivity(new Intent(this, ProfileActivity.class));
             return true;
             case R.id.emergency:
             showList();
             **/
        }

        return super.onOptionsItemSelected(item);
    }

    public void showList() {

        final ArrayList<String> emer = new ArrayList<>();
        emer.add("Emergency");
        emer.add("Police");
        emer.add("Fire");
        emer.add("Ambulance");
        //emer.add("Gas Leakage");
        //emer.add("Tourist-Helpline");
        //emer.add("Child-Helpline");
        //emer.add("Blood-Requirement");
        emer.add("Women-Helpline");
        //emer.add("Ambulance Network (Emergency or Non-Emergency)");


        final ArrayList<String> num = new ArrayList<>();

        num.add("112");
        num.add("100");
        num.add("102");
        num.add("108");
        //num.add("1906");
        //num.add("1363");
        //num.add("1098");
        //num.add("104");
        num.add("181");
        //num.add("09343180000");


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

    @SuppressLint("WrongConstant")
    @Override
    public void onBackPressed() {
        Log.d("hoe-hoe-hoe", "" + isCommentsFragmentOpen);
        if (isFragOpen || isCommentsFragmentOpen) {
            closeFragment();
            return;
        }
        backHandler = new Handler();

        if (backFlag == 1) {
            finish();
        }
        backFlag = 1;
        Toast.makeText(ojassApplication, R.string.backPress, 3000).show();
        backHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                backFlag = 0;
            }
        }, 3000);
    }

    private void compareAppVersion() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(this.getPackageName(), 0);
            currentVersion = pInfo.versionName;
            new GetCurrentVersion().execute();
        } catch (PackageManager.NameNotFoundException e1) {
            Toast.makeText(this, e1.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private class GetCurrentVersion extends AsyncTask<Void, Void, Void> {

        private String latestVersion;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(urlOfApp)
                        .timeout(30000)
//                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
//                        .referrer("http://www.google.com")
                        .get();
                if (document != null) {
                    Elements element = document.getElementsContainingOwnText("Current Version");
                    for (Element ele : element) {
                        if (ele.siblingElements() != null) {
                            Elements sibElemets = ele.siblingElements();
                            for (Element sibElemet : sibElemets) {
                                latestVersion = sibElemet.text();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (!TextUtils.isEmpty(currentVersion) && !TextUtils.isEmpty(latestVersion)) {
//                Log.d("hello", doc.toString());
                Log.d("hello", "Current : " + currentVersion + " Latest : " + latestVersion);
                if (currentVersion.compareTo(latestVersion) < 0) {
                    if (!isFinishing()) {
                        showUpdateDialog();
                    }
                }
            }
            super.onPostExecute(aVoid);
        }
    }

    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New update");
        builder.setMessage("We have changed since we last met. Let's get the updates.");
        builder.setCancelable(false);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=ojass20.nitjsr.in.ojass")));
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

}