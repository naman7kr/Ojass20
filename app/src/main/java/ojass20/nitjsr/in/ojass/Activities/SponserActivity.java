package ojass20.nitjsr.in.ojass.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import ojass20.nitjsr.in.ojass.Adapters.SponserAdapter;
import ojass20.nitjsr.in.ojass.R;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Map;

public class SponserActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    SponserAdapter sponserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponser);
        recyclerView=findViewById(R.id.sponser_list);
        staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        ArrayList<Map<String,String>> list=new ArrayList<Map<String,String>>();

        sponserAdapter=new SponserAdapter(list,this);
        recyclerView.setAdapter(sponserAdapter);
    }
}
