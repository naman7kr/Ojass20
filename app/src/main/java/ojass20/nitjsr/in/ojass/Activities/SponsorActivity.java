package ojass20.nitjsr.in.ojass.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import ojass20.nitjsr.in.ojass.Adapters.SponserAdapter;
import ojass20.nitjsr.in.ojass.R;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SponsorActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    SponserAdapter sponserAdapter;
    ArrayList<Map<String,String>> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponser);
        recyclerView=findViewById(R.id.sponser_list);
//        staggeredGridLayoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(staggeredGridLayoutManager);
//         list=new ArrayList<Map<String,String>>();
//
//        sponserAdapter=new SponserAdapter(getData(),this);
//        recyclerView.setAdapter(sponserAdapter);
    }
    ArrayList<Map<String,String>> getData(){
       for(int i=0;i<50;i++){
            Map<String,String> m=new HashMap<>();
            m.put("Logo","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQoMDXxs_GBpG5H4MFkB7Bicg28C_pAOS_7W3uXsVUk11Ro-Aen");
            list.add(m);
        }
       return list;
    }
}
