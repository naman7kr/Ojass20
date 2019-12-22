package ojass20.nitjsr.in.ojass.Fragments;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import ojass20.nitjsr.in.ojass.Adapters.ViewPagerAdapter;
import ojass20.nitjsr.in.ojass.R;

public class EventBottomSheet extends Fragment {
    public static final String TAG = "EventBottomSheet";
    BottomSheetBehavior bottomSheetBehavior;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static EventBottomSheet newInstance() {
        return new EventBottomSheet();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.event_info_layout,container,false);
        viewPager =  view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout =  view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new AboutFragment(), "About");
        adapter.addFragment(new DetailsFragment(), "Details");
        adapter.addFragment(new RulesFragment(), "Rules");
        adapter.addFragment(new CoordianatorFragment(), "Coordinator");
        adapter.addFragment(new PrizeFragment(), "Prize");
        viewPager.setAdapter(adapter);
    }
}
