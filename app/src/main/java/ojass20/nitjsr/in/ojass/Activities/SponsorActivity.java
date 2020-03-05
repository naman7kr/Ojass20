package ojass20.nitjsr.in.ojass.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import ojass20.nitjsr.in.ojass.Adapters.SponserAdapter;
import ojass20.nitjsr.in.ojass.Adapters.StaggeredRecyclerAdapter;
import ojass20.nitjsr.in.ojass.Models.Row;
import ojass20.nitjsr.in.ojass.R;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class SponsorActivity extends AppCompatActivity {

    private RecyclerView staggered;
    private StaggeredRecyclerAdapter staggeredRecyclerAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponser);
        staggered=findViewById(R.id.staggeredrv);
        staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        staggered.setLayoutManager(staggeredGridLayoutManager);
        List<Row> lst=new ArrayList<>();
        lst.add(new Row(R.drawable.amazon,"https://www.amazon.in/"));
        lst.add(new Row(R.drawable.appple,"https://www.apple.com/in/"));
        lst.add(new Row(R.drawable.micro,"https://www.microsoft.com/en-in"));
        lst.add(new Row(R.drawable.mircro,"https://www.microsoft.com/en-in"));
        lst.add(new Row(R.drawable.amazon,"https://www.amazon.in/"));
        lst.add(new Row(R.drawable.appple,"https://www.apple.com/in/"));
        lst.add(new Row(R.drawable.micro,"https://www.microsoft.com/en-in"));
        lst.add(new Row(R.drawable.mircro,"https://www.microsoft.com/en-in"));
        lst.add(new Row(R.drawable.amazon,"https://www.amazon.in/"));
        lst.add(new Row(R.drawable.appple,"https://www.apple.com/in/"));
        lst.add(new Row(R.drawable.micro,"https://www.microsoft.com/en-in"));
        lst.add(new Row(R.drawable.mircro,"https://www.microsoft.com/en-in"));
        lst.add(new Row(R.drawable.amazon,"https://www.amazon.in/"));
        lst.add(new Row(R.drawable.appple,"https://www.apple.com/in/"));
        lst.add(new Row(R.drawable.micro,"https://www.microsoft.com/en-in"));
        lst.add(new Row(R.drawable.mircro,"https://www.microsoft.com/en-in"));



        staggeredRecyclerAdapter= new StaggeredRecyclerAdapter(this,lst);
        staggered.setAdapter(staggeredRecyclerAdapter);

    }
}
