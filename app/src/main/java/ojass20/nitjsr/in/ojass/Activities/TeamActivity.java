package ojass20.nitjsr.in.ojass.Activities;

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
import android.widget.Toast;

import java.lang.reflect.Member;
import java.util.ArrayList;

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
        //to set Listeners
        setListeners();
        //to set Team Member's data
        setData();
        //to set card
        setCard();
    }

    private void setData() {
        list.add(new TeamMember("Member1","something","","","ll","",R.drawable.joker,0));
        list.add(new TeamMember("Member2","something","as","ff","ll","00",R.drawable.joker2,0));
        list.add(new TeamMember("Member3","something","as","ff","ll","00",R.drawable.joker,0));
        list.add(new TeamMember("Member4","something","as","ff","ll","00",R.drawable.joker2,0));

        list.add(new TeamMember("Member4","something","as","ff","ll","00",R.drawable.joker,1));
        list.add(new TeamMember("Member4","something","as","ff","ll","00",R.drawable.joker2,2));

        list.add(new TeamMember("Member1","something","as","ff","ll","00",R.drawable.joker,3));
        list.add(new TeamMember("Member2","something","as","ff","ll","00",R.drawable.joker2,4));
        list.add(new TeamMember("Member3","something","as","ff","ll","00",R.drawable.joker,5));
        list.add(new TeamMember("Member4","something","as","ff","ll","00",R.drawable.joker2,6));

        list.add(new TeamMember("Member4","something","as","ff","ll","00",R.drawable.joker,7));
        list.add(new TeamMember("Member4","something","as","ff","ll","00",R.drawable.joker2,8));
        list.add(new TeamMember("Member1","something","as","ff","ll","00",R.drawable.joker,9));
        list.add(new TeamMember("Member2","something","as","ff","ll","00",R.drawable.joker2,10));
        list.add(new TeamMember("Member3","something","as","ff","ll","00",R.drawable.joker,11));
        list.add(new TeamMember("Member4","something","as","ff","ll","00",R.drawable.joker2,12));

        list.add(new TeamMember("Member4","something","as","ff","ll","00",R.drawable.joker,13));
        list.add(new TeamMember("Member4","something","as","ff","ll","00",R.drawable.joker2,14));


        filter();
    }

    private void setCard()
    {
        name.setText(MEMBER.name);
        designation.setText(MEMBER.desig);

        profilepic.setImageResource(MEMBER.img);
    }

    private void setListeners() {
        teamSpinner.setOnItemSelectedListener(this);
        whatsapp.setOnClickListener(this);
        call.setOnClickListener(this);
        facebook.setOnClickListener(this);
    }

    private void init() {
        list=new ArrayList<TeamMember>();
        teamList=new ArrayList<TeamMember>();
        teamSpinner=findViewById(R.id.team_name);
        recyclerView=findViewById(R.id.teamMemberList);
        adapter=new TeamMemberAdapter(this,teamList);
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
        teamList.clear();
        for(TeamMember member:list){
            if(member.team==FILTER)
                teamList.add(member);
        }
        if(teamList.size()>0)
            onSelected(teamList.get(0));
        adapter.notifyDataSetChanged();
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
