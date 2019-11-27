package ojass20.nitjsr.in.ojass.Activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import java.util.ArrayList;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ojass20.nitjsr.in.ojass.Adapters.SubEventsAdapter;
import ojass20.nitjsr.in.ojass.Fragments.EventBottomSheet;
import ojass20.nitjsr.in.ojass.Models.SubEventsModel;
import ojass20.nitjsr.in.ojass.R;
import ojass20.nitjsr.in.ojass.Utils.Constants;
import ojass20.nitjsr.in.ojass.Utils.RecyclerClickInterface;

public class SubEventActivity extends AppCompatActivity {
    private RecyclerView rView;
    private ArrayList<SubEventsModel> data=new ArrayList<>();
    private int mainEventPosition;
    private ImageView iv;
    private FrameLayout frag_frame;
    private static boolean bottomSheetOpen=false;
    private static int toolbarIconColor = Color.BLACK;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_event);
        init();
        //get intent get main event
        mainEventPosition = getIntent().getIntExtra("event_id",0); //from intent


        rView.setLayoutManager(new LinearLayoutManager(this));

        manageToolbar();

        //on item click
        RecyclerClickInterface mInterface = new RecyclerClickInterface() {
            @Override
            public void onLayoutClick(View v, int position) {
                showBottomSheet();
                bottomSheetOpen = true;

                //Change toolbar
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);
                toolbar.setBackgroundColor(Color.BLACK);

            }
        };
        rView.setAdapter(new SubEventsAdapter(this,getData(),mInterface));
        //set Image
        iv.setImageResource(Constants.eventImg[mainEventPosition]);


    }

    private void manageToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(Constants.eventNames[mainEventPosition]);

        //manage toolbar icons and text color
        BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
        Palette.from(drawable.getBitmap()).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                Palette.Swatch textSwatch = palette.getDarkMutedSwatch();

                if(textSwatch!=null) {
                    toolbarIconColor = textSwatch.getTitleTextColor();
                    toolbar.setTitleTextColor(textSwatch.getTitleTextColor());
                }
            }
        });
    }

    private void init() {
        rView = findViewById(R.id.sub_rv);
        iv = findViewById(R.id.transition_img);
        frag_frame = findViewById(R.id.fragment_layout);
        toolbar = findViewById(R.id.toolbar);
    }

    ArrayList<SubEventsModel> getData(){
        String subEventsName[][] = Constants.SubEventsList;
        for(int i = 0; i<subEventsName[mainEventPosition].length; i++){
            data.add(new SubEventsModel(subEventsName[mainEventPosition][i]));
        }
        return data;
    }
    public void showBottomSheet() {

        EventBottomSheet bottomSheet = new EventBottomSheet();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_bottom,R.anim.no_anim);
        transaction.add(R.id.fragment_layout,bottomSheet);
        transaction.commit();

    }
    private void hideBottomSheet(){
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_layout);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.no_anim,R.anim.slide_out_bottom);
        transaction.remove(f).commit();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            if(bottomSheetOpen){
                hideBottomSheet();
                bottomSheetOpen = false;

                //change toolbar
                Drawable backArrow =  getResources().getDrawable(R.drawable.ic_arrow_back);
                backArrow.setColorFilter(toolbarIconColor, PorterDuff.Mode.SRC_ATOP);
                getSupportActionBar().setHomeAsUpIndicator(backArrow);
                toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));

            }else {
                //go to EventsActivity with transition
                finishAfterTransition();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
