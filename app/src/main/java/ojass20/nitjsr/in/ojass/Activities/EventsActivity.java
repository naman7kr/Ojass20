package ojass20.nitjsr.in.ojass.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import ojass20.nitjsr.in.ojass.Adapters.EventsGridAdapter;
import ojass20.nitjsr.in.ojass.Models.EventsDisplayModel;
import ojass20.nitjsr.in.ojass.Utils.Constants;
import ojass20.nitjsr.in.ojass.Utils.PinchAlphaInterface;
import ojass20.nitjsr.in.ojass.Utils.ZoomLayout;
import ojass20.nitjsr.in.ojass.R;

public class EventsActivity extends AppCompatActivity implements PinchAlphaInterface {
    private static final int NO_OF_COLUMNS = 4;
    private static final float INIT_ALPHA = 0.3f;
    ArrayList<EventsDisplayModel> data=new ArrayList<>();
    EventsGridAdapter mAdapter;
    int width;
    float alphaVal=0;
    ZoomLayout zoomLayout;
    private Toolbar toolbar;
    private static float limitAlpha=1.4f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        init();

        getSupportActionBar().setTitle("Events");

        setZoomableGridView();




    }

    private void setZoomableGridView() {
        zoomLayout.setLayoutManager(new GridLayoutManager(this,NO_OF_COLUMNS));

        //getting width of device
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;

        //getting event names and images
        for(int i=0;i<Constants.eventNames.length;i++){
            data.add(new EventsDisplayModel(Constants.eventImg[i],Constants.eventNames[i],INIT_ALPHA));
        }

        mAdapter = new EventsGridAdapter(this,width,data);
        zoomLayout.setAdapter(mAdapter);
    }

    void init(){
        zoomLayout = findViewById(R.id.events_grid);
        toolbar = findViewById(R.id.events_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void ScaleFactorToAlpha(float scaleFactor) {
//        Log.e("TAG"," "+scaleFactor);
        alphaVal = (float) ((scaleFactor-1)*(1.0/(limitAlpha-1)))+INIT_ALPHA;
        if(alphaVal>1){
            alphaVal=1;
        }else if(alphaVal<0){
            alphaVal=0;
        }
        data.clear();
//        Log.e("TAG"," "+alphaVal);
        for(int i=0;i<Constants.eventNames.length;i++){
            data.add(new EventsDisplayModel(Constants.eventImg[i],Constants.eventNames[i],alphaVal));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.events_menu,menu);
        //TODO: implement search
        MenuItem searchItem = menu.findItem(R.id.search_events);
        return true;
    }
}
