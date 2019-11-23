package ojass20.nitjsr.in.ojass.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        recyclerView = findViewById(R.id.recycler);
        datalist = new ArrayList<>();

        ItineraryAdapter adapter = new ItineraryAdapter(this,datalist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}
