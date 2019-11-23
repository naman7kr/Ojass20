package ojass20.nitjsr.in.ojass.Activities;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import ojass20.nitjsr.in.ojass.Adapters.FeedAdapter;
import ojass20.nitjsr.in.ojass.Helpers.HomePage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ojass20.nitjsr.in.ojass.Models.Comments;
import ojass20.nitjsr.in.ojass.Models.FeedPost;
import ojass20.nitjsr.in.ojass.Models.Likes;
import ojass20.nitjsr.in.ojass.R;

public class MainActivity extends AppCompatActivity implements
        GestureDetector.OnGestureListener {
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
    private GestureDetectorCompat mDetector;
    private ArrayList<HomePage> mItems;
    private int mInd;
    private ConstraintLayout mCl;
    private ArrayList<ImageView> mCircles;
    private TextView mSubHeading;
    private ProgressBar recyclerview_progress;

    private RecyclerView mRecyclerView;
    private FeedAdapter mFeedAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private DatabaseReference dref;
    private FirebaseAuth mauth;
    private ArrayList<FeedPost> listposts;

    private String currentuid="83";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        builder=new AlertDialog.Builder(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("PostNo",Integer.toString(0));
        editor.apply();

        mRecyclerView = findViewById(R.id.feed_recycler_view);
        listposts = new ArrayList<>();

        mauth=FirebaseAuth.getInstance();
        //currentuid=mauth.getCurrentUser().getUid();

        dref = FirebaseDatabase.getInstance().getReference().child("Feeds");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recyclerview_progress.setVisibility(View.VISIBLE);
                listposts.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //FeedPost post = ds.getValue(FeedPost.class);
                    String post_id_temp=ds.getKey();
                    boolean flag=false;

                    ArrayList<Likes> mlikes=new ArrayList<>();
                    for(DataSnapshot dslike: ds.child("likes").getChildren()){
                        //Likes like=dslike.getValue(Likes.class);
                        String temp=dslike.getValue().toString();
                        Likes like=new Likes(temp);
                        mlikes.add(like);
                        if(temp.equals(currentuid)){
                            flag=true;
                        }
                    }

                    ArrayList<Comments> mcomments=new ArrayList<>();
                    for(DataSnapshot dscomment: ds.child("comments").getChildren()){
                        Comments comment=dscomment.getValue(Comments.class);
                        mcomments.add(comment);
                    }

                    String content=ds.child("content").getValue().toString();
                    String event_name=ds.child("event").getValue().toString();
                    String subevent_name=ds.child("subEvent").getValue().toString();
                    String image_url=ds.child("imageURL").getValue().toString();



                    FeedPost post=new FeedPost(flag,post_id_temp,content,event_name,image_url,subevent_name,mlikes,mcomments);

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

        setUpArrayList();
        setUpNavigationDrawer();
        setUpAnimationForImageView(mPullUp);
        detectTouchEvents();


    }

    private void setUpRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        // dummy posts
//        FeedPost[] posts = {
//                new FeedPost("NeoDrishti", "Codiyapa", "Coders GET READY! Codiyapa practice session is going to be organized tonight 10:00PM - 12:00AM for MCA and B.Tech first year students to give them a taste of whats coming in the main event.\nHappy Coding!", "http://i.redd.it/aoa42xsj09s31.png"),
//                new FeedPost("Vishwa CodeGenesis", "Codemania", "Codemania Finals will start tomorrow at sharp 8:00 AM. Teams are required to be present in CC before time with their own laptops. Teams must bring their 25 page reference sheets with them as specified in the rules section.", null),
//                new FeedPost("NeoDrishti", "SimpySQL", "Simply SQL is about to start! \nRoom No. 304\nTime: 2PM\nRegister here : http://google.com", "http://www.bestmobile.pk/mobile-wallpapers/original/Original_1543075868pexels-photo-1173777.jpeg"),
//                new FeedPost("NeoDrishti", "Game of Troves", "Game of Troves has been started. \nGo on http://tinyurl.com/neogot19 for registration and submitting solutions", null)
//        };

        //Log.e("VIVZ", "set");
        mFeedAdapter = new FeedAdapter(this, listposts,currentuid);
        mRecyclerView.setAdapter(mFeedAdapter);
        recyclerview_progress.setVisibility(View.GONE);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String numpost = preferences.getString("PostNo", "");
        mRecyclerView.scrollToPosition(Integer.parseInt(numpost));
    }

    private void setUpArrayList() {
        mItems.add(new HomePage("EVENTS", "#FF0000", 0));
        mItems.add(new HomePage("GURUGYAN", "#00FF00", 1));
        mItems.add(new HomePage("ITINERARY", "#0000FF", 2));
        mItems.add(new HomePage("MAPS", "#FFCC00", 3));
    }

    private void detectTouchEvents() {
        mPullUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeading.setClickable(true);
                mPullUp.setEnabled(false);
                mPullDown.setEnabled(true);
                mPullDown.setVisibility(View.VISIBLE);
                mHeading.setVisibility(View.VISIBLE);
                mPullDown.setAlpha(0.0f);
                mHeading.setAllCaps(true);
                mHeading.setAlpha(0.0f);
                mHeading.animate().alpha(1.0f).setDuration(1000);
                mHeading.animate().alpha(1.0f).setDuration(1000);
                mSubHeading.animate().alpha(1.0f).setDuration(1000);
                mPullDown.animate().alpha(1.0f).setDuration(1000);
                mCl.animate().alpha(1.0f).setDuration(1000);
                mCl.setVisibility(View.VISIBLE);
                mHeading.setVisibility(View.VISIBLE);
                mSubHeading.setVisibility(View.VISIBLE);
                mPullUp.animate().alpha(0.0f).setDuration(1000);
                mRecyclerView.animate().alpha(0.0f).setDuration(1000);
                mRecyclerView.setVisibility(View.GONE);
                recyclerview_progress.setVisibility(View.GONE);
                mPullUp.setVisibility(View.GONE);
                setUpAnimationForImageView(mPullDown);
                mToolbar.setTitle("");
                setUpView(0);
            }
        });

        mPullDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mFlContent.setBackgroundColor(Color.parseColor("#ffffff"));
                mPullDown.setEnabled(false);
                mPullUp.setEnabled(true);
                mPullUp.setVisibility(View.VISIBLE);
                mPullUp.setAlpha(0.0f);
                mPullUp.animate().alpha(1.0f).setDuration(1000);
                mRecyclerView.animate().alpha(1.0f).setDuration(1000);
                mRecyclerView.setVisibility(View.VISIBLE);
                mPullDown.animate().alpha(0.0f).setDuration(1000);
                mHeading.animate().alpha(0.0f).setDuration(1000);
                mSubHeading.animate().alpha(0.0f).setDuration(1000);
                mCl.animate().alpha(0.0f).setDuration(1000);
                mCl.setVisibility(View.GONE);
                mPullDown.setVisibility(View.GONE);
                mHeading.setVisibility(View.GONE);
                mSubHeading.setVisibility(View.GONE);
                mToolbar.setTitle(getResources().getString(R.string.feeds));
                setUpAnimationForImageView(mPullDown);

                if(listposts.size()==0){
                    recyclerview_progress.setVisibility(View.VISIBLE);
                }
            }
        });
        mCl.setClickable(false);

        mHeading.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return true;
            }
        });

        mCl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    private void setUpView(int counter) {
        HomePage homePage = mItems.get(mInd);
        mHeading.setText(homePage.getmTitle());
        String mystring = new String("Ojass 20");
        SpannableString content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        mSubHeading.setText(content);
        ArrayList<ValueAnimator> animators = new ArrayList<>();
        for (int i = 0; i < mCircles.size(); i++) {
            mCircles.get(i).setColorFilter(Color.parseColor(mItems.get(i).getmCircleColor()));
            animators.add(animateView(mCircles.get(i), 500, counter * 60));
        }
        for (int i = 0; i < animators.size(); i++)
            animators.get(i).start();
    }

    private ValueAnimator animateView(final ImageView imageView, long duration, long angle) {
        ConstraintLayout.LayoutParams lP = (ConstraintLayout.LayoutParams) imageView.getLayoutParams();
        ValueAnimator anim = ValueAnimator.ofInt((int) lP.circleAngle, (int) lP.circleAngle + (int) angle);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) imageView.getLayoutParams();
                layoutParams.circleAngle = val;
                imageView.setLayoutParams(layoutParams);
            }
        });
        anim.setDuration(duration);
        anim.setInterpolator(new LinearInterpolator());
        return anim;
    }


    private void setUpAnimationForTextView(final int code, final long mainTime, String curr) {
        long tempTime = System.currentTimeMillis();
        if (tempTime - mainTime > 500) {
            mHeading.setText(mItems.get(mInd).getmTitle());
            return;
        }
        String temp = " ";
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
        recyclerview_progress=findViewById(R.id.recycler_view_progress);
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
        mItems = new ArrayList<>();
        mInd = 0;
        mDetector = new GestureDetectorCompat(this, this);
        mCl = findViewById(R.id.cl);
        mCircles = new ArrayList<>();
        mCircles.add((ImageView) findViewById(R.id.img1));
        mCircles.add((ImageView) findViewById(R.id.img2));
        mCircles.add((ImageView) findViewById(R.id.img3));
        mCircles.add((ImageView) findViewById(R.id.img4));
        mSubHeading = findViewById(R.id.sub_heading);

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
                startActivity(new Intent(this,ItineraryActivity.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            case R.id.emergency:
                showList();
        }

        return super.onOptionsItemSelected(item);
    }
    public void showList()
    {

        final ArrayList<String> emer=new ArrayList<>();
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


        final ArrayList<String> num=new ArrayList<>();

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
        final Intent intent =new Intent(Intent.ACTION_DIAL);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,emer);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent.setData(Uri.parse("tel:"+num.get(which)));
                startActivity(intent);

            }
        });

        AlertDialog dialog=builder.create();
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

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        switch (mInd) {
            case 0:
                Toast.makeText(MainActivity.this, "Events", Toast.LENGTH_LONG).show();
                break;
            case 1:
                startActivity(new Intent(MainActivity.this, GurugyanActivity.class));
                break;
            case 2:
                Toast.makeText(MainActivity.this, "Itinerary", Toast.LENGTH_LONG).show();
                break;
            case 3:
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
                break;
            default:
                Log.e(LOG_TAG, "Bhai sahab ye kis line mein aa gye aap?");
        }
        return true;
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
            mInd--;
            if (mInd < 0) {
                mInd = mItems.size() - 1;
                setUpView(3);
            } else
                setUpView(1);
            setUpAnimationForTextView(1, System.currentTimeMillis(), mHeading.getText().toString().toUpperCase());
        } else {
            mInd++;
            if (mInd >= mItems.size()) {
                mInd = 0;
                setUpView(-3);
            } else
                setUpView(-1);
            setUpAnimationForTextView(-1, System.currentTimeMillis(), mHeading.getText().toString().toUpperCase());
        }
        return true;
    }
}