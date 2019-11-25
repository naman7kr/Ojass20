package ojass20.nitjsr.in.ojass.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;

import ojass20.nitjsr.in.ojass.EventFragment;
import ojass20.nitjsr.in.ojass.R;

public class EventActivity extends AppCompatActivity {

    ViewPager mViewPager;
    TabLayout mTabLayout;
    View mHeader;

    private final int PAGES = 5;
    private final String [] PAGE_TITLES = {"I N F O", "D E T A I L S", "R U L E S", "P R I Z E",
        "C O O R D I N A T O R S"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mViewPager = findViewById(R.id.event_view_pager);
        mTabLayout = findViewById(R.id.event_tab_layout);
        mHeader = findViewById(R.id.event_header);

        setUpViewPager();
        setUpTabLayout();
        setUpAnimationForHeader();
    }

    private void setUpViewPager(){
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(
                getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                getHeaders(),
                getBodies());
        mViewPager.setAdapter(pagerAdapter);
    }

    private ArrayList<String> getHeaders(){
        ArrayList<String> list = new ArrayList<>(Arrays.asList(PAGE_TITLES).subList(0, PAGES));
        return list;
    }

    private ArrayList<String> getBodies(){
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0;i < PAGES;i ++){
            list.add("<p>The much awaited 2019 Ballon d'Or is just one week away. A week later the football arena will crown the best player of the year with players as well as pundints jointly casting their vote for the same</p> <p><b>Ayush Dubey</b> stated that the Ballon d'Or 2019 should deservedly be given to him for his vital contributions to the CSE football team in the URJA 2019 football practice sesssions.</p>");
        }
        return list;
    }

    private void setUpTabLayout(){
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setUpAnimationForHeader(){
        ValueAnimator animator = ValueAnimator.ofInt(0, 90);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int greyScaleValue = (int) animation.getAnimatedValue();
                mHeader.setBackgroundColor(Color.rgb(greyScaleValue, greyScaleValue, greyScaleValue));
            }
        });
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(10000);
        animator.start();
    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {

        ArrayList<EventFragment> fragments;
        ArrayList<String> pageHeaders, pageBodies;
        int PAGES;

        MyPagerAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<String> pageHeaders,
                       ArrayList<String> pageBodies) {
            super(fm, behavior);
            PAGES = pageHeaders.size();
            fragments = new ArrayList<>();
            this.pageHeaders = pageHeaders;
            this.pageBodies = pageBodies;

            setUpFragments();
        }

        private void setUpFragments(){
            for(int i = 0;i < getCount();i ++){
                EventFragment fragment = EventFragment.newInstance(pageHeaders.get(i), pageBodies.get(i));
                fragments.add(fragment);
            }
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return PAGES;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return pageHeaders.get(position);
        }
    }

}
