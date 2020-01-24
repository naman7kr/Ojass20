package ojass20.nitjsr.in.ojass.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;

import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.ViewPager;

import ojass20.nitjsr.in.ojass.Adapters.TeamMemberAdapter;
import ojass20.nitjsr.in.ojass.Models.TeamMember;
import ojass20.nitjsr.in.ojass.R;
import ojass20.nitjsr.in.ojass.Utils.OnSwipeTouchListener;

import static ojass20.nitjsr.in.ojass.Utils.Utilities.setGlideImage;

public class TeamActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TeamMemberAdapter.OnClickItem, View.OnClickListener {
    private static final String TAG = "TeamActivity";
    Spinner teamSpinner;
    RecyclerView bottomList;
    TeamMemberAdapter adapter, upper_adapter;
    ArrayList<TeamMember> list, teamList;
    int FILTER = 0;
    boolean DEVELOPER = false;
    TeamMember MEMBER = null;
    ImageView team_back_button;
    Toolbar toolbar;
    LinearLayout swipeLayout;
    LinearLayoutManager manager1, manager2;
    //    RecyclerView team_upper_list;
    LinearSnapHelper btmSnap, topSnap;
    TabLayout tabLayout;
    ImageView imageView;
    LinearLayout linearLayout;
    ViewPager mPager;
    TeamMemberAdapter mAdapter;
    boolean spinner_bug_flag = false, spinner_bug_flag_for_onTabselect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        //to Initialize all class variables
        init();
        //check for developer page
        //to set Listeners
        setListeners();
//        setBottomList();
//        setUpperList();
//        setSwipeLayout();
//        syncRecyclerViewsAndTabs();
        fetchData();
        //to set Team Member's data
//        getData();
        //Temporary push data
//      fetch data
//      pushData();
        onBack();


    }
    public void pushData(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Team");
        String key = ref.push().getKey();
        ref.child(key).child("name").setValue("Prof. Karunesh Kumar Shukla");
        ref.child(key).child("call").setValue("");
        ref.child(key).child("desig").setValue("Director, NIT Jamshedpur");
        ref.child(key).child("facebook").setValue("");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://drive.google.com/uc?id=1JU7-OqX-dIv8VoI-Em1eYnSU1P9kpkUl");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(0);
        ref.child(key).child("whatsapp").setValue("");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Prof. Sanjay");
        ref.child(key).child("call").setValue("");
        ref.child(key).child("desig").setValue("Dean, Students Welfare");
        ref.child(key).child("facebook").setValue("");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://drive.google.com/uc?id=1JNff_hG_NkOxvPs_TFYk8b3eMQHUYPJE");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(0);
        ref.child(key).child("whatsapp").setValue("");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Dr. Satish Kumar");
        ref.child(key).child("call").setValue("");
        ref.child(key).child("desig").setValue("Prof. Incharge, OJASS");
        ref.child(key).child("facebook").setValue("");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://drive.google.com/uc?id=1OjELg2fwRRwpWqBrsf-QIlYhjlsTmwp1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(0);
        ref.child(key).child("whatsapp").setValue("");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Dr. Vishesh Ranjan Kar");
        ref.child(key).child("call").setValue("");
        ref.child(key).child("desig").setValue("Prof. Incharge, OJASS");
        ref.child(key).child("facebook").setValue("");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://drive.google.com/uc?id=1PQd0ctk31WoqSvvJ22k8KgMTQ4uAMxVV");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(0);
        ref.child(key).child("whatsapp").setValue("");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Onkar Kumar");
        ref.child(key).child("call").setValue("9472903552");
        ref.child(key).child("desig").setValue("Technical Secretary");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/onkar.kumar.7503");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1xSU1XPXYAB6Sg3QWScNZjK7fWvy28irU=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("https://instagram.com/onkuu__");
        ref.child(key).child("linkdin").setValue("https://www.linkedin.com/in/onkar-kumar-71404214a");
        ref.child(key).child("team").setValue(1);
        ref.child(key).child("whatsapp").setValue("9472903552");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Rishikesh Sengor");
        ref.child(key).child("call").setValue("7979870486");
        ref.child(key).child("desig").setValue("Joint Technical Secretary");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/rishinvincible");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1q4v6oGT3OKlDsSVCgNClLLiCvAkxtpki=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(1);
        ref.child(key).child("whatsapp").setValue("7295954464");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Jeet Kumar Yadav");
        ref.child(key).child("call").setValue("6201831446");
        ref.child(key).child("desig").setValue("General Secretary");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/jeetkumaryadav");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1SY4p_13nnAco7xsoghWFAjfTPGKmCUOW=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("https://instagram.com/zee.it?igshid=zob3ap5pv5t");
        ref.child(key).child("linkdin").setValue("https://www.linkedin.com/in/jeet-kumar-yadav-3978a9155");
        ref.child(key).child("team").setValue(1);
        ref.child(key).child("whatsapp").setValue("6201831446");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Poluri Chaitanya Prakash");
        ref.child(key).child("call").setValue("8555916281");
        ref.child(key).child("desig").setValue("Joint Secretary");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/polluri.chaitanyaprakash");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1l1Nfbw28CneAXQJFd1jES4yUDMXOBaL1=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(1);
        ref.child(key).child("whatsapp").setValue("9100496492");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Anurag Kumar");
        ref.child(key).child("call").setValue("8434342234");
        ref.child(key).child("desig").setValue("Finance secretary");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/anurag.k.bhardwaj1");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1H6776rYiDujdztRMEg0qOhAZvqAPdpjP=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("https://www.instagram.com/anuragkr98/?hl=en");
        ref.child(key).child("linkdin").setValue("https://www.linkedin.com/in/anurag-kumar-b1918b140");
        ref.child(key).child("team").setValue(1);
        ref.child(key).child("whatsapp").setValue("8434342234");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Akshay Kumar");
        ref.child(key).child("call").setValue("9122030876");
        ref.child(key).child("desig").setValue("Executive Sec");
        ref.child(key).child("facebook").setValue("https://m.facebook.com/akshay.akshit.3");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1JotKoYOGpmVuuh0csCu6-PKwVSfRmCXu=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("https://www.instagram.com/akshay.akshit/");
        ref.child(key).child("linkdin").setValue("https://www.linkedin.com/mwlite/me");
        ref.child(key).child("team").setValue(1);
        ref.child(key).child("whatsapp").setValue("9122030876");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Dheeravath Ganesh");
        ref.child(key).child("call").setValue("9010483613");
        ref.child(key).child("desig").setValue("EXECUTIVE SECRETARY");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/profile.php?id=100009259789749");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1w2SVWHQY4Ay2yFZc0Up_Y4JK-0TC3fVs=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(1);
        ref.child(key).child("whatsapp").setValue("9010483613");


        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Alluru Reddiah Reddy");
        ref.child(key).child("call").setValue("8074715088");
        ref.child(key).child("desig").setValue("Team Manager");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/ARREDDY03");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/17HB5plKGjsrPViKMkP1nr8bDi7oxLeij=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(6);
        ref.child(key).child("whatsapp").setValue("9490914614");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Sira Rama Krishna");
        ref.child(key).child("call").setValue("6301077784");
        ref.child(key).child("desig").setValue("Spokesperson");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/sivaramakrishna.gade.3");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1zrqKFaQ5xo0lLoO6kTdsX2v5svEOZrP7=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(7);
        ref.child(key).child("whatsapp").setValue("9515536779");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Shatanik Bose");
        ref.child(key).child("call").setValue("7903489465");
        ref.child(key).child("desig").setValue("Planning & Development");
        ref.child(key).child("facebook").setValue("https://m.facebook.com/shatanik.bose");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1wNaTPwy-dczahXNmk-C1uPU5xAHrdx8w=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("https://instagram.com/shatanikbose?igshid=8g6zs9x4qud8");
        ref.child(key).child("linkdin").setValue("https://www.linkedin.com/in/shatanik-bose-9738a4137?originalSubdomain=in");
        ref.child(key).child("team").setValue(8);
        ref.child(key).child("whatsapp").setValue("7654974223");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Aman Kumar");
        ref.child(key).child("call").setValue("7903764490");
        ref.child(key).child("desig").setValue("Planning & Development");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/echt.srivastava");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1LxlEF5aaKYPnaEgOtQQnJxRAz4RmFL0o=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(8);
        ref.child(key).child("whatsapp").setValue("9097319928");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Subhashish Mohan Kar");
        ref.child(key).child("call").setValue("7667511947");
        ref.child(key).child("desig").setValue("Planning & Development");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/subhashish.kar98");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1el_CmAlhuWYPEplC0accZ-rtezFllDu9=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("https://www.instagram.com/subhashish_18/");
        ref.child(key).child("linkdin").setValue("https://www.linkedin.com/in/subhashish-kar-946bab194");
        ref.child(key).child("team").setValue(8);
        ref.child(key).child("whatsapp").setValue("8292826588");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Shubham Gaurav");
        ref.child(key).child("call").setValue("7903544151");
        ref.child(key).child("desig").setValue("Corporate Relations and Marketing");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/shubham.gaurav.129");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1IAcSYiLXn0rtvMmTlq8YRz2kbbnW5R-L=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("https://www.instagram.com/propitious_pride/");
        ref.child(key).child("linkdin").setValue("https://www.linkedin.com/in/shubham-gaurav-140603153");
        ref.child(key).child("team").setValue(9);
        ref.child(key).child("whatsapp").setValue("9155677552");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Nidhaan Agarwal");
        ref.child(key).child("call").setValue("7990560751");
        ref.child(key).child("desig").setValue("Corporate Relations and Marketing");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/nidhaan.agarwal.5");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/126ggWcZf0H9-30wdneOOg3OEZx1m_qoF=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("https://www.linkedin.com/in/nidhaan-agarwal-758115131");
        ref.child(key).child("team").setValue(9);
        ref.child(key).child("whatsapp").setValue("7295867344");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Sachin Sourav");
        ref.child(key).child("call").setValue("8340157771");
        ref.child(key).child("desig").setValue("Corporate Relations and Marketing");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/sachin.sourav.9");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1Xen_zyIV596tmTahHNUHAf1gbgdAVxOg=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("https://www.linkedin.com/in/sachin-sourav-b89727161");
        ref.child(key).child("team").setValue(9);
        ref.child(key).child("whatsapp").setValue("8340157771");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Bijay Mahapatra");
        ref.child(key).child("call").setValue("8235698592");
        ref.child(key).child("desig").setValue("Corporate Relations and Marketing");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/Ojassnitjamshedpur/");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1BFI8S3dlX1IAw3EqVsL6Q9pFqIYti23m=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(9);
        ref.child(key).child("whatsapp").setValue("8235698592");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Naman Mishra");
        ref.child(key).child("call").setValue("8210714901");
        ref.child(key).child("desig").setValue("Workshop Planning");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/Ojassnitjamshedpur/");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/14WzvSIZDIYe8_SZMXBogK3PwEHQlXyZ2=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(10);
        ref.child(key).child("whatsapp").setValue("8756648429");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("P.Bhargav Kumar");
        ref.child(key).child("call").setValue("9486985502");
        ref.child(key).child("desig").setValue("Workshop Planning");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/Ojassnitjamshedpur/");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1Fby9Wh5CS30l5DUDCgMZgIAG1CFige-i=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(10);
        ref.child(key).child("whatsapp").setValue("9486985502");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Sandeep Yadav");
        ref.child(key).child("call").setValue("7992262914");
        ref.child(key).child("desig").setValue("Workshop Planning");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/sandeep.yadav.54379");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1LdwC9yY0FF4nj73RL_3xUPQVRiZJ7IhE=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("https://www.linkedin.com/in/sandeep-yadav-603295191");
        ref.child(key).child("team").setValue(10);
        ref.child(key).child("whatsapp").setValue("7277001848");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Rhea Srivastava");
        ref.child(key).child("call").setValue("9955543054");
        ref.child(key).child("desig").setValue("Digital PR");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/Ojassnitjamshedpur/");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/15vFKhaedRgZ-1oVqKfJfzjNX8Py96-Sr=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(11);
        ref.child(key).child("whatsapp").setValue("9955543054");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Amar Arjun Gaikwad");
        ref.child(key).child("call").setValue("8921467997");
        ref.child(key).child("desig").setValue("Digital PR");
        ref.child(key).child("facebook").setValue("https://m.facebook.com/amar.gaikwad.16144");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1dYYH0_0veROcScPXqYcUR7mDk5RDzG8-=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(11);
        ref.child(key).child("whatsapp").setValue("9447441366");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Mehendi Tubid");
        ref.child(key).child("call").setValue("7903436537");
        ref.child(key).child("desig").setValue("Digital PR");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/profile.php?id=100013646169905");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1MFimN5E0csD9VTXaz-_-rEcsGQuHI4TI=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(11);
        ref.child(key).child("whatsapp").setValue("7903436537");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Naman");
        ref.child(key).child("call").setValue("8210714901");
        ref.child(key).child("desig").setValue("APP");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/kumar.naman.707");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1wvqmAaNth0BY5fG6VspL1SsKrvffOaEm=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("https://www.linkedin.com/in/kumar-naman-55b356128");
        ref.child(key).child("team").setValue(12);
        ref.child(key).child("whatsapp").setValue("8756648429");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Anirudh");
        ref.child(key).child("call").setValue("8565950269");
        ref.child(key).child("desig").setValue("APP");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/anirudh.deep.5");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1sXbjGG2PhUYqKL5uApW4d0mXLm9ryuvZ=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(12);
        ref.child(key).child("whatsapp").setValue("8565950269");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Shubham");
        ref.child(key).child("call").setValue("8789813635");
        ref.child(key).child("desig").setValue("WEB");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/shubham.gaurav.129");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1JqihEF_UcwXcC-XD7pNatyilXgzllqJx=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("https://www.instagram.com/propitious_pride/");
        ref.child(key).child("linkdin").setValue("https://www.linkedin.com/in/shubham-gaurav-140603153");
        ref.child(key).child("team").setValue(12);
        ref.child(key).child("whatsapp").setValue("7766037424");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Mohit");
        ref.child(key).child("call").setValue("7352084456");
        ref.child(key).child("desig").setValue("WEB");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/mohit.k.jha.5");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1QGx4epx-2GE3jbgj_qQtbRtxDz3Nf46G=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(12);
        ref.child(key).child("whatsapp").setValue("9263593185");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Pratik Singh");
        ref.child(key).child("call").setValue("7000877611");
        ref.child(key).child("desig").setValue("Creative Team");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/pratik.singh.9887");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1gSYHc8Q6TVTlDn_CJSGQwiH1Uk4tvjuL=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("https://www.instagram.com/_pratik__singh_/");
        ref.child(key).child("linkdin").setValue("https://www.linkedin.com/in/pratik-singh-6163a1151");
        ref.child(key).child("team").setValue(13);
        ref.child(key).child("whatsapp").setValue("7000877611");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Pratik Ranjan");
        ref.child(key).child("call").setValue("7979837208");
        ref.child(key).child("desig").setValue("Creative Team");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/iampratikranjan");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://drive.google.com/uc?id=1f_iXEt1Qz5rNTfgnocAGgXG7ADAMUjv3");
        ref.child(key).child("insta").setValue("https://www.instagram.com/_the_hopeless_romantic_/");
        ref.child(key).child("linkdin").setValue("https://www.linkedin.com/in/impr2611/");
        ref.child(key).child("team").setValue(13);
        ref.child(key).child("whatsapp").setValue("8986732048");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Sudhanshu Prakash");
        ref.child(key).child("call").setValue("8862927236");
        ref.child(key).child("desig").setValue("Creative Team");
        ref.child(key).child("facebook").setValue("https://m.facebook.com/d4danton");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1SXLoCvnSyxf5UVvJ7wKB4iLiV65Rv2Hg=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("https://www.instagram.com/d4danton/");
        ref.child(key).child("linkdin").setValue("https://www.linkedin.com/in/sudhanshu-prakash-a05661158");
        ref.child(key).child("team").setValue(13);
        ref.child(key).child("whatsapp").setValue("8862927236");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Nivedita Gupta");
        ref.child(key).child("call").setValue("7004039328");
        ref.child(key).child("desig").setValue("Media Relations");
        ref.child(key).child("facebook").setValue("https://m.facebook.com/nivedita.gupta.73?ref=bookmarks");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1_HRZKH0Gu94khdBxYPGUesw9Hxa9yGs1=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("https://www.instagram.com/nivedita.apr21/");
        ref.child(key).child("linkdin").setValue("https://www.linkedin.com/in/nivedita-gupta-b91980a6");
        ref.child(key).child("team").setValue(14);
        ref.child(key).child("whatsapp").setValue("9431799032");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Usha Kumari");
        ref.child(key).child("call").setValue("9113121732");
        ref.child(key).child("desig").setValue("Media Relations");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/usha.rawani.3");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1IpkZtWJfsZ7zicXpI1fBhOeZupb0oo6S=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("https://www.instagram.com/day_spring23/");
        ref.child(key).child("linkdin").setValue("https://www.linkedin.com/in/usha-kumari-09aa6a152/");
        ref.child(key).child("team").setValue(14);
        ref.child(key).child("whatsapp").setValue("7070491015");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Gaurav Mridul");
        ref.child(key).child("call").setValue("7992425023");
        ref.child(key).child("desig").setValue("Public Relations");
        ref.child(key).child("facebook").setValue("https://m.facebook.com/gaurav.mridul?ref=bookmarks");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1pbHtBjxGSmf-4S6PpBSPWyMR_GeK8V3r=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(15);
        ref.child(key).child("whatsapp").setValue("7992425023");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Divyansh");
        ref.child(key).child("call").setValue("6299079553");
        ref.child(key).child("desig").setValue("Public Relations");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/divyansh.rock.56");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1OO1JHid54Uw5JjcL0QNc8u8AHA3Jzdu5=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(15);
        ref.child(key).child("whatsapp").setValue("6299079553");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Banoth Kishore");
        ref.child(key).child("call").setValue("8919845029");
        ref.child(key).child("desig").setValue("Public Relations");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/kishore.nayak.14289");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://drive.google.com/uc?id=1W98ryoetJYa8cfCd1Td59gQNAe4m9w20");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(15);
        ref.child(key).child("whatsapp").setValue("8919845029");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Prachi Fulzele");
        ref.child(key).child("call").setValue("7488309597");
        ref.child(key).child("desig").setValue("Public Relations");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/profile.php?id=100009340620995");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1r0PnQSCOUgo7Ay9ID8Yx1uTuCGT0oo9C=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(15);
        ref.child(key).child("whatsapp").setValue("9570155340");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Aditya Parihar");
        ref.child(key).child("call").setValue("7632013071");
        ref.child(key).child("desig").setValue("Transport Head");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/Ojassnitjamshedpur/");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("nill");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(16);
        ref.child(key).child("whatsapp").setValue("7632013071");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("KVS Dheeraj");
        ref.child(key).child("call").setValue("7382466832");
        ref.child(key).child("desig").setValue("Event Management");
        ref.child(key).child("facebook").setValue("dheerajkotla");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1tWQvePtTdGY_DS1CIIx84gQGD1q2N270=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(17);
        ref.child(key).child("whatsapp").setValue("7382466832");


        key = ref.push().getKey();
        ref.child(key).child("name").setValue("KSV Raaman");
        ref.child(key).child("call").setValue("8919758682");
        ref.child(key).child("desig").setValue("Event Management");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/raamansai");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1mqhEhj2YkQk8hHnTEEFcXojSOeSRJwtn=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(17);
        ref.child(key).child("whatsapp").setValue("9430755236");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Guntupalli Pavan Kumar");
        ref.child(key).child("call").setValue("8555910132");
        ref.child(key).child("desig").setValue("Event Management");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/profile.php?id=100003816556801");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/18ThS5VNOVqKVqNKUqYqGlKAt3Zysbl7L=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(17);
        ref.child(key).child("whatsapp").setValue("8555910132");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Boya Sai Nikhil");
        ref.child(key).child("call").setValue("8919032068");
        ref.child(key).child("desig").setValue("Event Management");
        ref.child(key).child("facebook").setValue("Sai Nikhil");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1yIC9vu7nMZuSiP-GIkQSLiPwrLI61gRo=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(17);
        ref.child(key).child("whatsapp").setValue("9908190474");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Amarnath Singh");
        ref.child(key).child("call").setValue("7992344253");
        ref.child(key).child("desig").setValue("Event Management");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/Ojassnitjamshedpur/");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1r51AN0IIdN51mjEcZB9VMgqZXHQmN0xM=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(17);
        ref.child(key).child("whatsapp").setValue("7992344253");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Cheerala Ganesh");
        ref.child(key).child("call").setValue("8210019371");
        ref.child(key).child("desig").setValue("Logistics");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/Ojassnitjamshedpur/");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("nill");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(18);
        ref.child(key).child("whatsapp").setValue("8210019371");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Katamnuni Rahul");
        ref.child(key).child("call").setValue("7013554184");
        ref.child(key).child("desig").setValue("Logistics");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/rahul.katamneni.94");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1VvMfcf5T0GsYHwWJyRytE_cx3Warnrzt=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(18);
        ref.child(key).child("whatsapp").setValue("7013554184");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Boya Kalyan");
        ref.child(key).child("call").setValue("8555884298");
        ref.child(key).child("desig").setValue("Logistics");
        ref.child(key).child("facebook").setValue("https://m.facebook.com/Kalyan.Nani01");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("nill");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(18);
        ref.child(key).child("whatsapp").setValue("9703203550");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Dannana Bhargav");
        ref.child(key).child("call").setValue("7989756275");
        ref.child(key).child("desig").setValue("Logistics");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/Ojassnitjamshedpur/");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1M-G2tHu528M0ZEBRwOu_yBkvLEvz5yEr=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(18);
        ref.child(key).child("whatsapp").setValue("7989756275");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Paidi Mohan Sai");
        ref.child(key).child("call").setValue("8639655733");
        ref.child(key).child("desig").setValue("Logistics");
        ref.child(key).child("facebook").setValue("https://m.facebook.com/mohan.sai.9028");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1P64ggOKLt-SlPpaZMI7hxB-NpklQqOFM=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(18);
        ref.child(key).child("whatsapp").setValue("7382060248");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Mansi");
        ref.child(key).child("call").setValue("8210493722");
        ref.child(key).child("desig").setValue("Hospitality");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/m.singh1913");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1xmQlCjgCdwxTLs9f9FDw-dENJYNdpjIw=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(19);
        ref.child(key).child("whatsapp").setValue("9973356195");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Shashi Bhushan Chandel");
        ref.child(key).child("call").setValue("9570981598");
        ref.child(key).child("desig").setValue("Hospitality");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/profile.php?id=100011425120825");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1-wioSlWIBmECKq1t2TnQepwNxXAZfNVv=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(19);
        ref.child(key).child("whatsapp").setValue("9570981598");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Sanjeev Suman");
        ref.child(key).child("call").setValue("9304105785");
        ref.child(key).child("desig").setValue("Hospitality");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/sajeev.suman.9");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/13jtjvbv7qf7YdnNCHl84gwc94wdJ6gc3=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(19);
        ref.child(key).child("whatsapp").setValue("8002021535");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Mallikarjun");
        ref.child(key).child("call").setValue("9471391405");
        ref.child(key).child("desig").setValue("Hospitality");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/vankaraboena.mallikarjun");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/14bCQ8Rx8Hm0JoztlxSeScCi6_7R4sfpv=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(19);
        ref.child(key).child("whatsapp").setValue("9471391405");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Uday Kumar Reddy");
        ref.child(key).child("call").setValue("8555907136");
        ref.child(key).child("desig").setValue("Hospitality");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/kamsani.uday");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("nill");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(19);
        ref.child(key).child("whatsapp").setValue("7337562023");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Parwez Akhtar");
        ref.child(key).child("call").setValue("8102951783");
        ref.child(key).child("desig").setValue("Decoration");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/parwez.akhtar.313");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1V7NpnldwckjTbQGc3Ddmx3bTRkKuI3dO=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(20);
        ref.child(key).child("whatsapp").setValue("8102951783");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Jajjuvarapur Sushma");
        ref.child(key).child("call").setValue("7903656844");
        ref.child(key).child("desig").setValue("Decoration");
        ref.child(key).child("facebook").setValue("Sushma Js");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://doc-08-bo-docs.googleusercontent.com/docs/securesc/4nqf0q1u5skcc8rv4jmi30ruk44gl9g4/sko2d9b8eicbqv9m8m1v4epc097qokmr/1579111200000/09127525360115958627/11794176718702994664/1OSouRH9M1xBafnKdNMdeq2QdxvpSAzxR?authuser=0");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(20);
        ref.child(key).child("whatsapp").setValue("9515905284");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Pranathi");
        ref.child(key).child("call").setValue("7013123170");
        ref.child(key).child("desig").setValue("Decoration");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/pranathi.botsa.75");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://drive.google.com/open?id=1ORK_2aie62BEuVUezqjNiDuT3XuKy2f-");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(20);
        ref.child(key).child("whatsapp").setValue("7013123170");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Nadiminti Manohar");
        ref.child(key).child("call").setValue("8317695624");
        ref.child(key).child("desig").setValue("Decoration");
        ref.child(key).child("facebook").setValue("MANOHAR NADIMINTI");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1H99ISkPUFuA-BIbtuXUSfs46LaJlgrCj=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(20);
        ref.child(key).child("whatsapp").setValue("9430724874");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Syed Sahil");
        ref.child(key).child("call").setValue("8688826888");
        ref.child(key).child("desig").setValue("Robotics");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/syedsahil3999");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1dqUwz4mubgMmLuY1Rbd3Ae8JavWCj9rY=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(21);
        ref.child(key).child("whatsapp").setValue("8688826888");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Injam Praveen Chodhary");
        ref.child(key).child("call").setValue("9182898525");
        ref.child(key).child("desig").setValue("Robotics");
        ref.child(key).child("facebook").setValue("injampraveenchowdary");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/18CHMN0Bl0q7NkSqhV-xspluCvOYVGIK7=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(21);
        ref.child(key).child("whatsapp").setValue("8008622191");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Cherukuri Pawan Sai");
        ref.child(key).child("call").setValue("8074768504");
        ref.child(key).child("desig").setValue("Robotics");
        ref.child(key).child("facebook").setValue("Pavan Sai Cherukuri");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/18uSb0nzUL6rBd1_IGPt7yuvd6X8Pxiyc=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(21);
        ref.child(key).child("whatsapp").setValue("9492127013");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Subham Kumar");
        ref.child(key).child("call").setValue("7004875034");
        ref.child(key).child("desig").setValue("Robotics");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/profile.php?id=100007885389982");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1eu7wnxyQ65TFfVvpG4pC71AgWmT5x9nC=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(21);
        ref.child(key).child("whatsapp").setValue("7004875034");

        key = ref.push().getKey();
        ref.child(key).child("name").setValue("Ketan Kumar Patel");
        ref.child(key).child("call").setValue("7903820129");
        ref.child(key).child("desig").setValue("Robotics");
        ref.child(key).child("facebook").setValue("https://www.facebook.com/Ojassnitjamshedpur/");
        ref.child(key).child("github").setValue("");
        ref.child(key).child("img").setValue("https://lh3.googleusercontent.com/u/0/d/1eP8su-KlMaPMK8yLws0C5ubXU2Ib4Z4x=w1157-h981-p-k-nu-iv1");
        ref.child(key).child("insta").setValue("");
        ref.child(key).child("linkdin").setValue("");
        ref.child(key).child("team").setValue(21);
        ref.child(key).child("whatsapp").setValue("8090826558");
    }
    private void init() {
       // swipeLayout = findViewById(R.id.swipe_layout);
        list = new ArrayList<>();
        teamList = new ArrayList<>();
        teamSpinner = findViewById(R.id.team_name);
        ArrayAdapter<CharSequence> spAdapter = new ArrayAdapter<CharSequence>(this,R.layout.spinner_item,getResources().getStringArray(R.array.team_names));
        spAdapter.setDropDownViewResource(R.layout.spinner_item_dropdown);
        teamSpinner.setAdapter(spAdapter);
//        bottomList=findViewById(R.id.teamMemberList);
//        team_upper_list = findViewById(R.id.team_upper_recycler_view);
        team_back_button = findViewById(R.id.team_back_button);
        tabLayout = findViewById(R.id.tabs);
        mPager = findViewById(R.id.team_viewpager);
    }

    private void setTabs() {

        for (int i = 0; i < teamList.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText("t" + (i + 1)));
        }
        mAdapter = new TeamMemberAdapter(this,this, teamList);
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(mPager);

        // set data
        for (int i = 0; i < teamList.size(); i++) {

            final int temp = i;

            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(R.layout.item_team_tab);
            imageView = tab.getCustomView().findViewById(R.id.single_team_member_image);
            linearLayout = tab.getCustomView().findViewById(R.id.singleItemTeamMember);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    onClickItem.onSelected(position);
                    mPager.setCurrentItem(temp);
                }
            });
            setGlideImage(this,teamList.get(i).img,imageView);
//            Glide.with(this).asBitmap().fitCenter().load(teamList.get(i).img).into(imageView);

        }
        syncRecyclerViewsAndTabs();


    }

    private void onBack() {
        team_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        FILTER = position;
//        Log.e("onItemSelected: ", "level 167");
        if(spinner_bug_flag){
            //no extra work needed
            spinner_bug_flag=false;
        }
        else {
            spinner_bug_flag_for_onTabselect = true;
            for (int i = 0; i < teamList.size(); i++) {
                int team_no;
                if (position == 0) {
                    team_no = 0;
                } else if (position == 1) {
                    team_no = 1;
                } else {
                    team_no = (4 + position);
                }
                if (team_no == teamList.get(i).team) {
                    mPager.setCurrentItem(i);
                    break;
                }
            }
        }
        //filter();
    }

    private void syncRecyclerViewsAndTabs() {

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition());
                TeamMember temp = teamList.get(tab.getPosition());
                int team_no = temp.team;
                if(team_no==0){
                    team_no=0;
                }
                else if (team_no>=1 && team_no <=5){
                    team_no=1;
                }
                else{
                    team_no-=4;
                }
                if(!spinner_bug_flag_for_onTabselect) {
                    spinner_bug_flag = true;
                }
                else spinner_bug_flag_for_onTabselect=false;
                teamSpinner.setSelection(team_no);
//                Log.e("onTabSelected: ", "selection continues..." + tab.getPosition());


                Animation anim = AnimationUtils.loadAnimation(TeamActivity.this, R.anim.scale_in_tv);

                tabLayout.getTabAt(tab.getPosition()).getCustomView().findViewById(R.id.singleItemTeamMember).startAnimation(anim);
                anim.setFillAfter(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Animation anim = AnimationUtils.loadAnimation(TeamActivity.this, R.anim.scale_out_tv);
                tabLayout.getTabAt(tab.getPosition()).getCustomView().findViewById(R.id.singleItemTeamMember).startAnimation(anim);
                anim.setFillAfter(true);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void fetchData() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Team");
        ref.keepSynced(true);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teamList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    TeamMember m = ds.getValue(TeamMember.class);
//                    Log.d("pubg", "onDataChange: " + m.name);
                    if (!teamList.contains(m))
                        teamList.add(m);

                }
                setTabs();
//                adapter.notifyDataSetChanged();
//                upper_adapter.notifyDataSetChanged();
//                filter();
//                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setListeners() {
        if (!DEVELOPER) {

            teamSpinner.setOnItemSelectedListener(this);
        }
    }

    private void filter() {
//        Log.d(TAG, "filter: " + FILTER);
        teamList.clear();
        for (TeamMember member : list) {
            if (member.team == FILTER)
                teamList.add(member);
        }
        if (teamList.size() > 0)
            //onSelected(0);
        adapter.notifyDataSetChanged();
        upper_adapter.notifyDataSetChanged();
//        Log.e("TAG", String.valueOf(teamList.size()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onSelected(int position,boolean side) {
        //MEMBER=teamMember;
//        team_upper_list.scrollToPosition(position);
        //setCard();

        if(side){
            if(mPager.getCurrentItem()!=0){
                mPager.setCurrentItem(mPager.getCurrentItem()-1);
            }
        }
        else {
            if(mPager.getCurrentItem()!=teamList.size()-1){
                mPager.setCurrentItem(mPager.getCurrentItem()+1);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.team_member_facebook:
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(MEMBER.facebook));
                if (DEVELOPER)
                    viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(MEMBER.github));
                startActivity(viewIntent);
                break;
            case R.id.team_member_call:
                String phone = MEMBER.call;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
                break;
            case R.id.team_member_whatsapp:

                String url = "https://api.whatsapp.com/send?phone=" + MEMBER.whatsapp;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
        }
    }

//    private void setData() {
//        //Technical Secretary
//        list.add(new TeamMember("Onkar Kumar","Technical Secretary","","https://www.facebook.com/onkar.kumar.7503","","https://www.linkedin.com/in/onkar-kumar-71404214a","","","https://lh3.googleusercontent.com/u/0/d/1xSU1XPXYAB6Sg3QWScNZjK7fWvy28irU=w1157-h981-p-k-nu-iv1",0));
//        //Joint Technical Secretary
//        list.add(new TeamMember("Rishikesh Sengor","","","","","","","","https://lh3.googleusercontent.com/u/0/d/1q4v6oGT3OKlDsSVCgNClLLiCvAkxtpki=w1157-h981-p-k-nu-iv1",1));
//        //Finance Secretary
//        list.add(new TeamMember("Anurag Kumar","Finance secretary","","https://www.facebook.com/anurag.k.bhardwaj1","https://www.instagram.com/anuragkr98/?hl=en","https://www.linkedin.com/in/anurag-kumar-b1918b140","","","https://lh3.googleusercontent.com/u/0/d/1YD3DMzUIRtVnsCJlh8HscCj8np2plI7s=w1157-h981-p-k-nu-iv1",3));
//        //General Secretary
//        list.add(new TeamMember("Jeet Kumar Yadav","General Secretary","","https://www.facebook.com/jeetkumaryadav","https://instagram.com/zee.it?igshid=zob3ap5pv5t","https://www.linkedin.com/in/jeet-kumar-yadav-3978a9155","","","https://lh3.googleusercontent.com/u/0/d/1SY4p_13nnAco7xsoghWFAjfTPGKmCUOW=w1157-h981-p-k-nu-iv1",2));
//        //joint Secretary
//        list.add(new TeamMember("Polury Chaitanya Prakash","","","","","","","","https://lh3.googleusercontent.com/u/0/d/1l1Nfbw28CneAXQJFd1jES4yUDMXOBaL1=w1157-h981-p-k-nu-iv1",4));
//        //Executive Secretary
//        list.add(new TeamMember("Dheeravath Ganesh","EXECUTIVE SECRETARY","","","","","","","https://lh3.googleusercontent.com/u/0/d/1w2SVWHQY4Ay2yFZc0Up_Y4JK-0TC3fVs=w1157-h981-p-k-nu-iv1",5));
//        list.add(new TeamMember("Akshay Kumar","Executive Sec","","https://m.facebook.com/akshay.akshit.3","https://www.instagram.com/akshay.akshit/","https://www.linkedin.com/mwlite/me","","","https://lh3.googleusercontent.com/u/0/d/1JotKoYOGpmVuuh0csCu6-PKwVSfRmCXu=w1157-h981-p-k-nu-iv1",5));
//        //Team Co-Ordinator
//        list.add(new TeamMember("Alluru Reddiah Reddy","","","","","","","","https://lh3.googleusercontent.com/u/0/d/17HB5plKGjsrPViKMkP1nr8bDi7oxLeij=w1157-h981-p-k-nu-iv1",6));
//        //Spokesperson
//        list.add(new TeamMember("Gade Sira Rama Krishna","","","","","","","","",7));
//        //Planning & Development
//        list.add(new TeamMember("Shatanik Bose","","","https://m.facebook.com/shatanik.bose","https://instagram.com/shatanikbose?igshid=8g6zs9x4qud8","https://www.linkedin.com/in/shatanik-bose-9738a4137?originalSubdomain=in","",""," https://lh3.googleusercontent.com/u/0/d/1wNaTPwy-dczahXNmk-C1uPU5xAHrdx8w=w1157-h981-p-k-nu-iv1",8));
//        list.add(new TeamMember("Aman Kumar","","","","","","","","https://lh3.googleusercontent.com/u/0/d/1LxlEF5aaKYPnaEgOtQQnJxRAz4RmFL0o=w1157-h981-p-k-nu-iv1",8));
//        list.add(new TeamMember("Subhashish Mohan Kar","Planning & Development","","https://www.facebook.com/subhashish.kar98","https://www.instagram.com/subhashish_18/","https://www.linkedin.com/in/subhashish-kar-946bab194","","","https://lh3.googleusercontent.com/u/0/d/1P64ggOKLt-SlPpaZMI7hxB-NpklQqOFM=w1157-h981-p-k-nu-iv1",8));
//        //Corporate Affairs
//        list.add(new TeamMember("Shubham Gaurav","Corporate Relations and Marketing","","https://www.facebook.com/shubham.gaurav.129","https://www.instagram.com/propitious_pride/","https://www.linkedin.com/in/shubham-gaurav-140603153","","","https://lh3.googleusercontent.com/u/0/d/1IAcSYiLXn0rtvMmTlq8YRz2kbbnW5R-L=w1157-h981-p-k-nu-iv1",9));
//        list.add(new TeamMember("Nidhaan Agarwal","Corporate affairs","","https://www.facebook.com/nidhaan.agarwal.5","","https://www.linkedin.com/in/nidhaan-agarwal-758115131","","","https://lh3.googleusercontent.com/u/0/d/126ggWcZf0H9-30wdneOOg3OEZx1m_qoF=w1157-h981-p-k-nu-iv1",9));
//        list.add(new TeamMember("Sachin Sourav","Corporate Affairs","","https://www.facebook.com/sachin.sourav.9","","https://www.linkedin.com/in/sachin-sourav-b89727161","","","https://lh3.googleusercontent.com/u/0/d/1Xen_zyIV596tmTahHNUHAf1gbgdAVxOg=w1157-h981-p-k-nu-iv1",9));
//        list.add(new TeamMember("Bijay Mahapatra","","","","","","","","https://lh3.googleusercontent.com/u/0/d/1BFI8S3dlX1IAw3EqVsL6Q9pFqIYti23m=w1157-h981-p-k-nu-iv1",9));
//        //Workshop Planning
//        list.add(new TeamMember("Naman Mishra","","","","","","","","https://lh3.googleusercontent.com/u/0/d/14WzvSIZDIYe8_SZMXBogK3PwEHQlXyZ2=w1157-h981-p-k-nu-iv1",10));
//        list.add(new TeamMember("P.Bhargav Kumar","","","","","","","","https://lh3.googleusercontent.com/u/0/d/1M-G2tHu528M0ZEBRwOu_yBkvLEvz5yEr=w1157-h981-p-k-nu-iv1",10));
//        list.add(new TeamMember("Sandeep Yadav","Workshop Planning","","https://www.facebook.com/sandeep.yadav.54379","","https://www.linkedin.com/in/sandeep-yadav-603295191","","","https://lh3.googleusercontent.com/u/0/d/1LdwC9yY0FF4nj73RL_3xUPQVRiZJ7IhE=w1157-h981-p-k-nu-iv1",10));
//        //Digital PR & Planning
//        list.add(new TeamMember("Mehendi Tubid","Digital PR","","https://www.facebook.com/profile.php?id=100013646169905","","","","","https://lh3.googleusercontent.com/u/0/d/1MFimN5E0csD9VTXaz-_-rEcsGQuHI4TI=w1157-h981-p-k-nu-iv1",11));
//        list.add(new TeamMember("Rhea Srivastava","Digital PR","","","","","","","https://lh3.googleusercontent.com/u/0/d/15vFKhaedRgZ-1oVqKfJfzjNX8Py96-Sr=w1157-h981-p-k-nu-iv1",11));
//        list.add(new TeamMember("Amar Arjun Gaikwad","Digital PR","","","","","","","https://lh3.googleusercontent.com/u/0/d/1dYYH0_0veROcScPXqYcUR7mDk5RDzG8-=w1157-h981-p-k-nu-iv1",11));
//        //Application & Web Development
//        list.add(new TeamMember("Naman","APP","","https://www.facebook.com/kumar.naman.707","","https://www.linkedin.com/in/kumar-naman-55b356128","","","https://lh3.googleusercontent.com/u/0/d/1wvqmAaNth0BY5fG6VspL1SsKrvffOaEm=w1157-h981-p-k-nu-iv1",12));
//        list.add(new TeamMember("Anirudh","APP","","","","","","","https://lh3.googleusercontent.com/u/0/d/1sXbjGG2PhUYqKL5uApW4d0mXLm9ryuvZ=w1157-h981-p-k-nu-iv1",12));
//        list.add(new TeamMember("Shubham","WEB","","https://www.facebook.com/shubham.gaurav.129","https://www.instagram.com/propitious_pride/","https://www.linkedin.com/in/shubham-gaurav-140603153","","","https://lh3.googleusercontent.com/u/0/d/1IAcSYiLXn0rtvMmTlq8YRz2kbbnW5R-L=w1157-h981-p-k-nu-iv1",12));
//        list.add(new TeamMember("Mohit","WEB","","","","","","","https://lh3.googleusercontent.com/u/0/d/1QGx4epx-2GE3jbgj_qQtbRtxDz3Nf46G=w1157-h981-p-k-nu-iv1",12));
//        //Creative Team
//        list.add(new TeamMember("Pratik Singh","","","https://www.facebook.com/pratik.singh.9887","https://www.instagram.com/_pratik__singh_/","https://www.linkedin.com/in/pratik-singh-6163a1151","","","https://lh3.googleusercontent.com/u/0/d/1gSYHc8Q6TVTlDn_CJSGQwiH1Uk4tvjuL=w1157-h981-p-k-nu-iv1",13));
//        list.add(new TeamMember("Pratik Ranjan","","","https://www.facebook.com/iampratikranjan","https://www.instagram.com/_the_hopeless_romantic_/","https://www.linkedin.com/in/impr2611/","","","https://lh3.googleusercontent.com/u/0/d/1gSYHc8Q6TVTlDn_CJSGQwiH1Uk4tvjuL=w1157-h981-p-k-nu-iv1",13));
//        list.add(new TeamMember("Sudhanshu Prakash","","","https://m.facebook.com/d4danton","https://www.instagram.com/d4danton/","https://www.linkedin.com/in/sudhanshu-prakash-a05661158","","","https://lh3.googleusercontent.com/u/0/d/1SXLoCvnSyxf5UVvJ7wKB4iLiV65Rv2Hg=w1157-h981-p-k-nu-iv1",13));
//        //Media Relations
//        list.add(new TeamMember("Nivedita Gupta","","","https://m.facebook.com/nivedita.gupta.73?ref=bookmarks","https://www.instagram.com/nivedita.apr21/","https://www.linkedin.com/in/nivedita-gupta-b91980a6","","","https://lh3.googleusercontent.com/u/0/d/1_HRZKH0Gu94khdBxYPGUesw9Hxa9yGs1=w1157-h981-p-k-nu-iv1",14));
//        list.add(new TeamMember("Usha Kumari","","","https://www.facebook.com/usha.rawani.3","https://www.instagram.com/day_spring23/","https://www.linkedin.com/in/usha-kumari-09aa6a152/","","","https://lh3.googleusercontent.com/u/0/d/1IpkZtWJfsZ7zicXpI1fBhOeZupb0oo6S=w1157-h981-p-k-nu-iv1",14));
//        //App team
//        list.add(new TeamMember("Naman","APP","","https://www.facebook.com/kumar.naman.707","","https://www.linkedin.com/in/kumar-naman-55b356128","","","https://lh3.googleusercontent.com/u/0/d/1wvqmAaNth0BY5fG6VspL1SsKrvffOaEm=w1157-h981-p-k-nu-iv1",100));
//        list.add(new TeamMember("Anirudh","APP","","","","","","","https://lh3.googleusercontent.com/u/0/d/1sXbjGG2PhUYqKL5uApW4d0mXLm9ryuvZ=w1157-h981-p-k-nu-iv1",100));
//
////        filter();
//    }
}
