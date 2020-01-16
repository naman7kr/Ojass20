package ojass20.nitjsr.in.ojass.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;

import ojass20.nitjsr.in.ojass.Adapters.TeamMemberAdapter;
import ojass20.nitjsr.in.ojass.Models.TeamMember;
import ojass20.nitjsr.in.ojass.R;

public class TeamActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TeamMemberAdapter.OnClickItem, View.OnClickListener {
    private static final String TAG ="TeamActivity" ;
    Spinner teamSpinner;
    RecyclerView recyclerView;
    TeamMemberAdapter adapter;
    ArrayList<TeamMember> list,teamList;
    int FILTER=0;
    boolean DEVELOPER=false;
    TeamMember MEMBER=null;
    ImageView profilepic,call,facebook,whatsapp;
    TextView name,designation;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        //to Initialize all class variables
        init();
        //check for developer page
        check();
        //to set Listeners
        setListeners();


        fetchData();
        //to set Team Member's data
//        setData();
        //to set card
        setCard();
        //Temporary push data
//      fetch data
 //    pushData();

    }

    private void fetchData() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Team");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    TeamMember m = ds.getValue(TeamMember.class);
                    Log.d("pubg", "onDataChange: "+m.name);
                    if(!list.contains(m))
                        list.add(m);

                }
                filter();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void pushData(){

        list.add(new TeamMember("Gaurav Mridul","Public Relations","","","","","","","https://lh3.googleusercontent.com/u/0/d/1pbHtBjxGSmf-4S6PpBSPWyMR_GeK8V3r=w1157-h981-p-k-nu-iv1",15));
        list.add(new TeamMember("Divyansh","Public Relations","","","","","","","https://lh3.googleusercontent.com/u/0/d/1OO1JHid54Uw5JjcL0QNc8u8AHA3Jzdu5=w1157-h981-p-k-nu-iv1",15));
        list.add(new TeamMember("Banoth Kishore","Public Relations","","","","","","","",15));
        list.add(new TeamMember("Prachi Fulzele","Public Relations","","","","","","","https://lh3.googleusercontent.com/u/0/d/1r0PnQSCOUgo7Ay9ID8Yx1uTuCGT0oo9C=w1157-h981-p-k-nu-iv1",15));

        list.add(new TeamMember("Aditya Parihar","Transport Head","","","","","","","",16));

        list.add(new TeamMember("Amarnath Singh","Event Management","","","","","","","https://lh3.googleusercontent.com/u/0/d/1r51AN0IIdN51mjEcZB9VMgqZXHQmN0xM=w1157-h981-p-k-nu-iv1",17));
        list.add(new TeamMember("Guntupalli Pavan Kumar","Event Management","","","","","","","https://lh3.googleusercontent.com/u/0/d/18ThS5VNOVqKVqNKUqYqGlKAt3Zysbl7L=w1157-h981-p-k-nu-iv1",17));
        list.add(new TeamMember("Kotla Venkata Sai Krishna Dheeraj","Event Management","","","","","","","https://lh3.googleusercontent.com/u/0/d/1tWQvePtTdGY_DS1CIIx84gQGD1q2N270=w1157-h981-p-k-nu-iv1",17));
        list.add(new TeamMember("Boya Sai Nikhil","Event Management","","","","","","","https://lh3.googleusercontent.com/u/0/d/1yIC9vu7nMZuSiP-GIkQSLiPwrLI61gRo=w1157-h981-p-k-nu-iv1",17));
        list.add(new TeamMember("Kilaru Sai Venkata Raaman","Event Management","","","","","","","https://lh3.googleusercontent.com/u/0/d/1mqhEhj2YkQk8hHnTEEFcXojSOeSRJwtn=w1157-h981-p-k-nu-iv1",17));

        list.add(new TeamMember("Katamnuni Rahul","Logistics","","","","","","","https://lh3.googleusercontent.com/u/0/d/1VvMfcf5T0GsYHwWJyRytE_cx3Warnrzt=w1157-h981-p-k-nu-iv1",18));
        list.add(new TeamMember("Boya Kalyan","Logistics","","","","","","","",18));
        list.add(new TeamMember("Dannana Bhargav","Logistics","","","","","","","https://lh3.googleusercontent.com/u/0/d/1M-G2tHu528M0ZEBRwOu_yBkvLEvz5yEr=w1157-h981-p-k-nu-iv1",18));
        list.add(new TeamMember("Paidi Mohan Sai","Logistics","","","","","","","https://lh3.googleusercontent.com/u/0/d/1P64ggOKLt-SlPpaZMI7hxB-NpklQqOFM=w1157-h981-p-k-nu-iv1",18));
        list.add(new TeamMember("Cheerala Ganesh","Logistics","","","","","","","",18));

        list.add(new TeamMember("Mansi","Hospitality","","","","","","","https://lh3.googleusercontent.com/u/0/d/1xmQlCjgCdwxTLs9f9FDw-dENJYNdpjIw=w1157-h981-p-k-nu-iv1",19));
        list.add(new TeamMember("Shashi Bhushan Chandel","Hospitality","","","","","","","https://lh3.googleusercontent.com/u/0/d/1-wioSlWIBmECKq1t2TnQepwNxXAZfNVv=w1157-h981-p-k-nu-iv1",19));
        list.add(new TeamMember("Sanjeev Suman","Hospitality","","","","","","","https://lh3.googleusercontent.com/u/0/d/13jtjvbv7qf7YdnNCHl84gwc94wdJ6gc3=w1157-h981-p-k-nu-iv1",19));
        list.add(new TeamMember("Mallikarjun","Hospitality","","","","","","","https://lh3.googleusercontent.com/u/0/d/14bCQ8Rx8Hm0JoztlxSeScCi6_7R4sfpv=w1157-h981-p-k-nu-iv1",19));
        list.add(new TeamMember("Uday Kumar Reddy","Hospitality","","","","","","","",19));

        list.add(new TeamMember("Parwez Akhtar","Decoration","","","","","","","https://lh3.googleusercontent.com/u/0/d/1V7NpnldwckjTbQGc3Ddmx3bTRkKuI3dO=w1157-h981-p-k-nu-iv1",20));
        list.add(new TeamMember("Jajjuvarapur Sushma","Decoration","","","","","","","",20));
        list.add(new TeamMember("Pranathi","Decoration","","","","","","","",20));
        list.add(new TeamMember("Nadiminti Manohar","Decoration","","","","","","","https://lh3.googleusercontent.com/u/0/d/1H99ISkPUFuA-BIbtuXUSfs46LaJlgrCj=w1157-h981-p-k-nu-iv1",20));

        list.add(new TeamMember("Syed Sahil","Robotics","","","","","","","https://lh3.googleusercontent.com/u/0/d/1dqUwz4mubgMmLuY1Rbd3Ae8JavWCj9rY=w1157-h981-p-k-nu-iv1",21));
        list.add(new TeamMember("Injam Praveen Chodhary","Robotics","","","","","","","https://lh3.googleusercontent.com/u/0/d/18CHMN0Bl0q7NkSqhV-xspluCvOYVGIK7=w1157-h981-p-k-nu-iv1",21));
        list.add(new TeamMember("Cherukuri Pawan Sai","Robotics","","","","","","","https://lh3.googleusercontent.com/u/0/d/18uSb0nzUL6rBd1_IGPt7yuvd6X8Pxiyc=w1157-h981-p-k-nu-iv1",21));
        list.add(new TeamMember("Subham Kumar","Robotics","","","","","","","https://lh3.googleusercontent.com/u/0/d/1eu7wnxyQ65TFfVvpG4pC71AgWmT5x9nC=w1157-h981-p-k-nu-iv1",21));
        list.add(new TeamMember("Ketan Kumar Patel","Robotics","","","","","","","https://lh3.googleusercontent.com/u/0/d/1eP8su-KlMaPMK8yLws0C5ubXU2Ib4Z4x=w1157-h981-p-k-nu-iv1",21));






        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Team");

        for(TeamMember m:list){
            Log.d("pushdata", "pushData: "+m.name);
            reference.push().setValue(m);
        }

    }
    private void devloperPage() {
        DEVELOPER=true;
        FILTER=100;
        teamSpinner.setVisibility(View.INVISIBLE);
        facebook.setImageResource(R.drawable.github);
        filter();
    }

    private void check() {
        if(getIntent().hasExtra("DEV")){
            devloperPage();
        }
    }

    private void setData() {
        //Technical Secretary
        list.add(new TeamMember("Onkar Kumar","Technical Secretary","","https://www.facebook.com/onkar.kumar.7503","","https://www.linkedin.com/in/onkar-kumar-71404214a","","","https://lh3.googleusercontent.com/u/0/d/1xSU1XPXYAB6Sg3QWScNZjK7fWvy28irU=w1157-h981-p-k-nu-iv1",0));
        //Joint Technical Secretary
        list.add(new TeamMember("Rishikesh Sengor","","","","","","","","https://lh3.googleusercontent.com/u/0/d/1q4v6oGT3OKlDsSVCgNClLLiCvAkxtpki=w1157-h981-p-k-nu-iv1",1));
        //Finance Secretary
        list.add(new TeamMember("Anurag Kumar","Finance secretary","","https://www.facebook.com/anurag.k.bhardwaj1","https://www.instagram.com/anuragkr98/?hl=en","https://www.linkedin.com/in/anurag-kumar-b1918b140","","","https://lh3.googleusercontent.com/u/0/d/1YD3DMzUIRtVnsCJlh8HscCj8np2plI7s=w1157-h981-p-k-nu-iv1",3));
        //General Secretary
        list.add(new TeamMember("Jeet Kumar Yadav","General Secretary","","https://www.facebook.com/jeetkumaryadav","https://instagram.com/zee.it?igshid=zob3ap5pv5t","https://www.linkedin.com/in/jeet-kumar-yadav-3978a9155","","","https://lh3.googleusercontent.com/u/0/d/1SY4p_13nnAco7xsoghWFAjfTPGKmCUOW=w1157-h981-p-k-nu-iv1",2));
        //joint Secretary
        list.add(new TeamMember("Polury Chaitanya Prakash","","","","","","","","https://lh3.googleusercontent.com/u/0/d/1l1Nfbw28CneAXQJFd1jES4yUDMXOBaL1=w1157-h981-p-k-nu-iv1",4));
        //Executive Secretary
        list.add(new TeamMember("Dheeravath Ganesh","EXECUTIVE SECRETARY","","","","","","","https://lh3.googleusercontent.com/u/0/d/1w2SVWHQY4Ay2yFZc0Up_Y4JK-0TC3fVs=w1157-h981-p-k-nu-iv1",5));
        list.add(new TeamMember("Akshay Kumar","Executive Sec","","https://m.facebook.com/akshay.akshit.3","https://www.instagram.com/akshay.akshit/","https://www.linkedin.com/mwlite/me","","","https://lh3.googleusercontent.com/u/0/d/1JotKoYOGpmVuuh0csCu6-PKwVSfRmCXu=w1157-h981-p-k-nu-iv1",5));
        //Team Co-Ordinator
        list.add(new TeamMember("Alluru Reddiah Reddy","","","","","","","","https://lh3.googleusercontent.com/u/0/d/17HB5plKGjsrPViKMkP1nr8bDi7oxLeij=w1157-h981-p-k-nu-iv1",6));
        //Spokesperson
        list.add(new TeamMember("Gade Sira Rama Krishna","","","","","","","","",7));
        //Planning & Development
        list.add(new TeamMember("Shatanik Bose","","","https://m.facebook.com/shatanik.bose","https://instagram.com/shatanikbose?igshid=8g6zs9x4qud8","https://www.linkedin.com/in/shatanik-bose-9738a4137?originalSubdomain=in","",""," https://lh3.googleusercontent.com/u/0/d/1wNaTPwy-dczahXNmk-C1uPU5xAHrdx8w=w1157-h981-p-k-nu-iv1",8));
        list.add(new TeamMember("Aman Kumar","","","","","","","","https://lh3.googleusercontent.com/u/0/d/1LxlEF5aaKYPnaEgOtQQnJxRAz4RmFL0o=w1157-h981-p-k-nu-iv1",8));
        list.add(new TeamMember("Subhashish Mohan Kar","Planning & Development","","https://www.facebook.com/subhashish.kar98","https://www.instagram.com/subhashish_18/","https://www.linkedin.com/in/subhashish-kar-946bab194","","","https://lh3.googleusercontent.com/u/0/d/1P64ggOKLt-SlPpaZMI7hxB-NpklQqOFM=w1157-h981-p-k-nu-iv1",8));
        //Corporate Affairs
        list.add(new TeamMember("Shubham Gaurav","Corporate Relations and Marketing","","https://www.facebook.com/shubham.gaurav.129","https://www.instagram.com/propitious_pride/","https://www.linkedin.com/in/shubham-gaurav-140603153","","","https://lh3.googleusercontent.com/u/0/d/1IAcSYiLXn0rtvMmTlq8YRz2kbbnW5R-L=w1157-h981-p-k-nu-iv1",9));
        list.add(new TeamMember("Nidhaan Agarwal","Corporate affairs","","https://www.facebook.com/nidhaan.agarwal.5","","https://www.linkedin.com/in/nidhaan-agarwal-758115131","","","https://lh3.googleusercontent.com/u/0/d/126ggWcZf0H9-30wdneOOg3OEZx1m_qoF=w1157-h981-p-k-nu-iv1",9));
        list.add(new TeamMember("Sachin Sourav","Corporate Affairs","","https://www.facebook.com/sachin.sourav.9","","https://www.linkedin.com/in/sachin-sourav-b89727161","","","https://lh3.googleusercontent.com/u/0/d/1Xen_zyIV596tmTahHNUHAf1gbgdAVxOg=w1157-h981-p-k-nu-iv1",9));
        list.add(new TeamMember("Bijay Mahapatra","","","","","","","","https://lh3.googleusercontent.com/u/0/d/1BFI8S3dlX1IAw3EqVsL6Q9pFqIYti23m=w1157-h981-p-k-nu-iv1",9));
        //Workshop Planning
        list.add(new TeamMember("Naman Mishra","","","","","","","","https://lh3.googleusercontent.com/u/0/d/14WzvSIZDIYe8_SZMXBogK3PwEHQlXyZ2=w1157-h981-p-k-nu-iv1",10));
        list.add(new TeamMember("P.Bhargav Kumar","","","","","","","","https://lh3.googleusercontent.com/u/0/d/1M-G2tHu528M0ZEBRwOu_yBkvLEvz5yEr=w1157-h981-p-k-nu-iv1",10));
        list.add(new TeamMember("Sandeep Yadav","Workshop Planning","","https://www.facebook.com/sandeep.yadav.54379","","https://www.linkedin.com/in/sandeep-yadav-603295191","","","https://lh3.googleusercontent.com/u/0/d/1LdwC9yY0FF4nj73RL_3xUPQVRiZJ7IhE=w1157-h981-p-k-nu-iv1",10));
        //Digital PR & Planning
        list.add(new TeamMember("Mehendi Tubid","Digital PR","","https://www.facebook.com/profile.php?id=100013646169905","","","","","https://lh3.googleusercontent.com/u/0/d/1MFimN5E0csD9VTXaz-_-rEcsGQuHI4TI=w1157-h981-p-k-nu-iv1",11));
        list.add(new TeamMember("Rhea Srivastava","Digital PR","","","","","","","https://lh3.googleusercontent.com/u/0/d/15vFKhaedRgZ-1oVqKfJfzjNX8Py96-Sr=w1157-h981-p-k-nu-iv1",11));
        list.add(new TeamMember("Amar Arjun Gaikwad","Digital PR","","","","","","","https://lh3.googleusercontent.com/u/0/d/1dYYH0_0veROcScPXqYcUR7mDk5RDzG8-=w1157-h981-p-k-nu-iv1",11));
        //Application & Web Development
        list.add(new TeamMember("Naman","APP","","https://www.facebook.com/kumar.naman.707","","https://www.linkedin.com/in/kumar-naman-55b356128","","","https://lh3.googleusercontent.com/u/0/d/1wvqmAaNth0BY5fG6VspL1SsKrvffOaEm=w1157-h981-p-k-nu-iv1",12));
        list.add(new TeamMember("Anirudh","APP","","","","","","","https://lh3.googleusercontent.com/u/0/d/1sXbjGG2PhUYqKL5uApW4d0mXLm9ryuvZ=w1157-h981-p-k-nu-iv1",12));
        list.add(new TeamMember("Shubham","WEB","","https://www.facebook.com/shubham.gaurav.129","https://www.instagram.com/propitious_pride/","https://www.linkedin.com/in/shubham-gaurav-140603153","","","https://lh3.googleusercontent.com/u/0/d/1IAcSYiLXn0rtvMmTlq8YRz2kbbnW5R-L=w1157-h981-p-k-nu-iv1",12));
        list.add(new TeamMember("Mohit","WEB","","","","","","","https://lh3.googleusercontent.com/u/0/d/1QGx4epx-2GE3jbgj_qQtbRtxDz3Nf46G=w1157-h981-p-k-nu-iv1",12));
        //Creative Team
        list.add(new TeamMember("Pratik Singh","","","https://www.facebook.com/pratik.singh.9887","https://www.instagram.com/_pratik__singh_/","https://www.linkedin.com/in/pratik-singh-6163a1151","","","https://lh3.googleusercontent.com/u/0/d/1gSYHc8Q6TVTlDn_CJSGQwiH1Uk4tvjuL=w1157-h981-p-k-nu-iv1",13));
        list.add(new TeamMember("Pratik Ranjan","","","https://www.facebook.com/iampratikranjan","https://www.instagram.com/_the_hopeless_romantic_/","https://www.linkedin.com/in/impr2611/","","","https://lh3.googleusercontent.com/u/0/d/1gSYHc8Q6TVTlDn_CJSGQwiH1Uk4tvjuL=w1157-h981-p-k-nu-iv1",13));
        list.add(new TeamMember("Sudhanshu Prakash","","","https://m.facebook.com/d4danton","https://www.instagram.com/d4danton/","https://www.linkedin.com/in/sudhanshu-prakash-a05661158","","","https://lh3.googleusercontent.com/u/0/d/1SXLoCvnSyxf5UVvJ7wKB4iLiV65Rv2Hg=w1157-h981-p-k-nu-iv1",13));
        //Media Relations
        list.add(new TeamMember("Nivedita Gupta","","","https://m.facebook.com/nivedita.gupta.73?ref=bookmarks","https://www.instagram.com/nivedita.apr21/","https://www.linkedin.com/in/nivedita-gupta-b91980a6","","","https://lh3.googleusercontent.com/u/0/d/1_HRZKH0Gu94khdBxYPGUesw9Hxa9yGs1=w1157-h981-p-k-nu-iv1",14));
        list.add(new TeamMember("Usha Kumari","","","https://www.facebook.com/usha.rawani.3","https://www.instagram.com/day_spring23/","https://www.linkedin.com/in/usha-kumari-09aa6a152/","","","https://lh3.googleusercontent.com/u/0/d/1IpkZtWJfsZ7zicXpI1fBhOeZupb0oo6S=w1157-h981-p-k-nu-iv1",14));
        //App team
        list.add(new TeamMember("Naman","APP","","https://www.facebook.com/kumar.naman.707","","https://www.linkedin.com/in/kumar-naman-55b356128","","","https://lh3.googleusercontent.com/u/0/d/1wvqmAaNth0BY5fG6VspL1SsKrvffOaEm=w1157-h981-p-k-nu-iv1",100));
        list.add(new TeamMember("Anirudh","APP","","","","","","","https://lh3.googleusercontent.com/u/0/d/1sXbjGG2PhUYqKL5uApW4d0mXLm9ryuvZ=w1157-h981-p-k-nu-iv1",100));

//        filter();
    }

    private void setCard()
    {
        if(MEMBER!=null)
        {
            name.setText(MEMBER.name);
            designation.setText(MEMBER.desig);
            Glide.with(TeamActivity.this).asBitmap().load(MEMBER.img).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(profilepic);
        }


        //profilepic.setImageResource(MEMBER.img);
    }

    private void setListeners() {
        if(!DEVELOPER)
        teamSpinner.setOnItemSelectedListener(this);
        whatsapp.setOnClickListener(this);
        call.setOnClickListener(this);
        facebook.setOnClickListener(this);
    }

    private void init() {
        list=new ArrayList<>();
        teamList=new ArrayList<>();
        teamSpinner=findViewById(R.id.team_name);
        recyclerView=findViewById(R.id.teamMemberList);
        adapter=new TeamMemberAdapter(TeamActivity.this,this,teamList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);

        profilepic=findViewById(R.id.profile_image_team_member);
        call=findViewById(R.id.team_member_call);
        facebook=findViewById(R.id.team_member_facebook);
        whatsapp=findViewById(R.id.team_member_whatsapp);

        name=findViewById(R.id.team_member_name);
        designation=findViewById(R.id.team_member_designation);
        toolbar=findViewById(R.id.teamPageToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        FILTER=position;
        filter();
    }

    private void filter() {
        Log.d(TAG, "filter: "+FILTER);
        teamList.clear();
        for(TeamMember member:list){
            if(member.team==FILTER)
                teamList.add(member);
        }
        if(teamList.size()>0)
            onSelected(teamList.get(0));
        adapter.notifyDataSetChanged();
        Log.e("TAG", String.valueOf(teamList.size()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onSelected(TeamMember teamMember) {
        MEMBER=teamMember;
        setCard();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.team_member_facebook:
                Intent viewIntent = new Intent("android.intent.action.VIEW",Uri.parse(MEMBER.facebook));
                if(DEVELOPER)
                    viewIntent=new Intent("android.intent.action.VIEW",Uri.parse(MEMBER.github));
                startActivity(viewIntent);
                break;
            case R.id.team_member_call:
                String phone = MEMBER.call;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
                break;
            case R.id.team_member_whatsapp:
                String url = "https://api.whatsapp.com/send?phone="+MEMBER.whatsapp;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
        }
    }
}
