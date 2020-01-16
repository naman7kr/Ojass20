package ojass20.nitjsr.in.ojass.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.util.ArrayList;

import ojass20.nitjsr.in.ojass.Adapters.ItineraryAdapter;
import ojass20.nitjsr.in.ojass.Models.ItinerraryModal;
import ojass20.nitjsr.in.ojass.R;

public class ItineraryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ItinerraryModal> datalist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

        setComingSoon();

        recyclerView = findViewById(R.id.recycler);
        datalist = new ArrayList<>();

        ItineraryAdapter adapter = new ItineraryAdapter(this,datalist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private void setComingSoon() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setTitle("Sponsors");
    }
}
