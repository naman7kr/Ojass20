package ojass20.nitjsr.in.ojass.Activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ojass20.nitjsr.in.ojass.Adapters.FAQAdapter;
import ojass20.nitjsr.in.ojass.Models.FaqModel;
import ojass20.nitjsr.in.ojass.Models.TitleChild;
import ojass20.nitjsr.in.ojass.Models.TitleCreater1;
import ojass20.nitjsr.in.ojass.Models.TitleParent;
import ojass20.nitjsr.in.ojass.R;
import ojass20.nitjsr.in.ojass.fragments.SubscribeFragment;


public class FeedActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FAQAdapter adapter;
    //DatabaseReference ref;
    public static ArrayList<FaqModel> data;
    ProgressDialog p;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        Picasso.with(this).load(R.drawable.ojass_bg).fit().into((ImageView)findViewById(R.id.iv_feed));

        spinner = findViewById(R.id.spinner_feed);

        recyclerView=(RecyclerView)findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        data=new ArrayList<>();

        p=new ProgressDialog(this);
        //onItemSelect();




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                onItemSelect();
                //  Toast.makeText(getApplication(),spinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        findViewById(R.id.ib_back_feed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.ib_subscribe_feed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubscribeFragment detailsfragment=new SubscribeFragment();
                detailsfragment.show(getSupportFragmentManager(),"Subscribe");
            }
        });

    }

    public void onItemSelect()
    {

//        p.setMessage("Loading Feed....");
//        p.setCancelable(true);
//        p.show();
//        ref= FirebaseDatabase.getInstance().getReference().child(FIREBASE_REF_NOTIFICATIONS).child(spinner.getSelectedItem().toString());
//        ref.keepSynced(true);
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                p.dismiss();
//                data.clear();
//                for(DataSnapshot ds: dataSnapshot.getChildren())
//                {
//                    FaqModel q = ds.getValue(FaqModel.class);
//                    data.add(q);
//                    // Toast.makeText(FAQActivity.this,"Q"+q.getQuestion()+"\nA:"+q.getAns(),Toast.LENGTH_SHORT).show();
//
//                }
//
//                adapter = new FAQAdapter(FeedActivity.this,initData());
//                adapter.setParentClickableViewAnimationDefaultDuration();
//                adapter.setParentAndIconExpandOnClick(true);
//                recyclerView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//


    }



    private List<ParentObject> initData() {
        TitleCreater1 titleCreater= new TitleCreater1(FeedActivity.this);
        //titleCreater= TitleCreater.get(this);
        List<TitleParent> titles= TitleCreater1._titleParents;
        List<ParentObject> parentObject = new ArrayList<>();
        //Toast.makeText(FeedActivity.this,"Title:"+titles.size(),Toast.LENGTH_SHORT).show();
        int i=data.size()-1;
        for(TitleParent title:titles)
        {
            List<Object> childList = new ArrayList<>();
            //childList.add(new TitleChild(("It is LSE web style to title a page of FAQs 'Frequently asked questions (FAQs)'. While the abbreviation is in quite common usage this ensures that there can be no mistaking what they are")));
            childList.add(new TitleChild(data.get(i--).getAns()));
            title.setChildObjectList(childList);
            parentObject.add(title);
        }
        return parentObject;
    }














}