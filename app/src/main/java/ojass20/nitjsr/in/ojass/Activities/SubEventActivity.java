package ojass20.nitjsr.in.ojass.Activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ojass20.nitjsr.in.ojass.Adapters.SubEventsAdapter;
import ojass20.nitjsr.in.ojass.Fragments.EventBottomSheet;
import ojass20.nitjsr.in.ojass.Models.SubEventsModel;
import ojass20.nitjsr.in.ojass.R;
import ojass20.nitjsr.in.ojass.Utils.Constants;
import ojass20.nitjsr.in.ojass.Utils.RecyclerClickInterface;

public class SubEventActivity extends AppCompatActivity {
    private LinearLayout mAboutLayout, mHeadLayout;
    private RecyclerView rView;
    private ArrayList<SubEventsModel> data = new ArrayList<>();
    private int mainEventPosition;
    private ImageView iv;
    private FloatingActionButton mFab;
    private FrameLayout frag_frame;
    private static boolean bottomSheetOpen = false;
    private static int toolbarIconColor = Color.BLACK;
    private Animation fadeIn, fadeOut;
    Toolbar toolbar;
    public static int position;
    public static String actionBarTitle;
    private boolean isOpen;
    private ArrayList<LinearLayout> mLL;
    private ArrayList<ImageView> mProfile;
    private ArrayList<ImageView> mWhatsapp;
    private ArrayList<ImageView> mCall;
    private TextView mAbout, mHeading;
    private View mDivider1, mDivider2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_event);
        init();
        //get intent get main event
        mainEventPosition = getIntent().getIntExtra("event_id", 0); //from intent


        rView.setLayoutManager(new LinearLayoutManager(this));

        manageToolbar();

        //on item click
        RecyclerClickInterface mInterface = new RecyclerClickInterface() {
            @Override
            public void onLayoutClick(View v, int position) {
                showBottomSheet();
                bottomSheetOpen = true;

                getPostion(position);
            }
        };
        rView.setAdapter(new SubEventsAdapter(this, getData(), mInterface));
        //set Image
        iv.setImageResource(Constants.eventImg[mainEventPosition]);


        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    mHeadLayout.startAnimation(fadeOut);
                    mAboutLayout.startAnimation(fadeOut);
                    mHeadLayout.setVisibility(View.INVISIBLE);
                    mAboutLayout.setVisibility(View.INVISIBLE);
                    mHeadLayout.setClickable(false);
                    mAboutLayout.setClickable(false);
                    isOpen = false;
                    mFab.setImageDrawable(getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
                } else {
                    mHeadLayout.startAnimation(fadeIn);
                    mAboutLayout.startAnimation(fadeIn);
                    mHeadLayout.setVisibility(View.VISIBLE);
                    mAboutLayout.setVisibility(View.VISIBLE);
                    mHeadLayout.setClickable(true);
                    mAboutLayout.setClickable(true);
                    isOpen = true;
                    mFab.setImageDrawable(getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
//                    TranslateAnimation tr = new TranslateAnimation(0.0f, 0.0f, 0, 30);
//                    tr.setDuration(100);
//                    mFab.startAnimation(tr);
                }
            }
        });

        mHeadLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rect displayRectangle = new Rect();
                Window window = SubEventActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                final AlertDialog.Builder builder = new AlertDialog.Builder(SubEventActivity.this, R.style.CustomAlertDialog);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_layout, viewGroup, false);
                dialogView.setMinimumWidth((int) (displayRectangle.width()/1.5f * 1f));
                dialogView.setMinimumHeight((int) (displayRectangle.height()/1.2f * 1f));
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                mLL = new ArrayList<>();
                mProfile = new ArrayList<>();
                mCall = new ArrayList<>();
                mWhatsapp = new ArrayList<>();
                mHeading = dialogView.findViewById(R.id.heading);
                String s = "Event Heads";
                mHeading.setText(s);

                mLL.add((LinearLayout) dialogView.findViewById(R.id.one));
                mLL.add((LinearLayout) dialogView.findViewById(R.id.two));
                mLL.add((LinearLayout) dialogView.findViewById(R.id.three));

                mProfile.add((ImageView) dialogView.findViewById(R.id.img1));
                mProfile.add((ImageView) dialogView.findViewById(R.id.img2));
                mProfile.add((ImageView) dialogView.findViewById(R.id.img3));

                mWhatsapp.add((ImageView) dialogView.findViewById(R.id.whatsapp1));
                mWhatsapp.add((ImageView) dialogView.findViewById(R.id.whatsapp2));
                mWhatsapp.add((ImageView) dialogView.findViewById(R.id.whatsapp3));

                mCall.add((ImageView) dialogView.findViewById(R.id.call1));
                mCall.add((ImageView) dialogView.findViewById(R.id.call2));
                mCall.add((ImageView) dialogView.findViewById(R.id.call3));

                mDivider1 = dialogView.findViewById(R.id.divider1);
                mDivider2 = dialogView.findViewById(R.id.divider2);
                alertDialog.show();
            }
        });

        mAboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rect displayRectangle = new Rect();
                Window window = SubEventActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                final AlertDialog.Builder builder = new AlertDialog.Builder(SubEventActivity.this, R.style.CustomAlertDialog);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_layout, viewGroup, false);
                dialogView.setMinimumWidth((int) (displayRectangle.width() * 1f));
                dialogView.setMinimumHeight((int) (displayRectangle.height()/1.2f * 1f));
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                mAbout = dialogView.findViewById(R.id.about_data);
                mAbout.setVisibility(View.VISIBLE);
                mHeading = dialogView.findViewById(R.id.heading);
                String s = "About the event";
                mHeading.setText(s);
                alertDialog.show();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        if (bottomSheetOpen) {
            hideBottomSheet();
            bottomSheetOpen = false;
        } else {
            finishAfterTransition();
        }
    }

    private void manageToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(Constants.eventNames[mainEventPosition]);

        //manage toolbar icons and text color
        BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
        Palette.from(drawable.getBitmap()).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                Palette.Swatch textSwatch = palette.getDarkMutedSwatch();

                if (textSwatch != null) {
                    toolbarIconColor = textSwatch.getTitleTextColor();
                    toolbar.setTitleTextColor(textSwatch.getTitleTextColor());
                }
            }
        });
    }

    private void init() {
        rView = findViewById(R.id.sub_rv);
        iv = findViewById(R.id.transition_img);
        frag_frame = findViewById(R.id.fragment_layout);
        toolbar = findViewById(R.id.toolbar);
        mHeadLayout = findViewById(R.id.head_layout);
        mAboutLayout = findViewById(R.id.about_layout);
        mFab = findViewById(R.id.fab);
        isOpen = false;
        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
    }

    ArrayList<SubEventsModel> getData() {
        String subEventsName[][] = Constants.SubEventsList;
        for (int i = 0; i < subEventsName[mainEventPosition].length; i++) {
            data.add(new SubEventsModel(subEventsName[mainEventPosition][i]));
        }
        return data;
    }

    public void showBottomSheet() {

        EventBottomSheet bottomSheet = new EventBottomSheet();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.no_anim);
        transaction.add(R.id.fragment_layout, bottomSheet);
        transaction.commit();

        //Change toolbar
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);
        toolbar.setBackgroundColor(Color.BLACK);
    }

    private void hideBottomSheet() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_layout);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.no_anim, R.anim.slide_out_bottom);
        transaction.remove(f).commit();

        //change toolbar
        Drawable backArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
        backArrow.setColorFilter(toolbarIconColor, PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);
        toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (bottomSheetOpen) {
                hideBottomSheet();
                bottomSheetOpen = false;

            } else {
                //go to EventsActivity with transition
                finishAfterTransition();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void getPostion(int pos) {
        actionBarTitle = Constants.SubEventsList[mainEventPosition][pos];
        switch (Constants.SubEventsList[mainEventPosition][pos]) {
            case "High Voltage Concepts (HVC)":
                position = 0;
                break;
            case "Mat Sim":
                position = 1;
                break;
            case "Tame the pyhton":
                position = 2;
                break;
            case "Codiyapa":
                position = 3;
                break;
            case "Game of Troves":
                position = 4;
                break;
            case "Scratch Easy":
                position = 5;
                break;
            case "SimplySql":
                position = 6;
                break;
            case "Counter Strike- Global Offensive":
                position = 7;
                break;
            case "DOTA":
                position = 8;
                break;
            case "FIFA":
                position = 9;
                break;
            case "NFS Most Wanted":
                position = 10;
                break;
            case "Angry Birds":
                position = 11;
                break;
            case "Pratibimb":
                position = 12;
                break;
            case "Elixir of Electricity":
                position = 13;
                break;
            case "Electro-Q":
                position = 14;
                break;
            case "Nexus":
                position = 15;
                break;
            case "Pro-Lo-Co":
                position = 16;
                break;
            case "Who Am I":
                position = 17;
                break;
            case "NSCET":
                position = 18;
                break;
            case "Jagriti":
                position = 19;
                break;
            case "Samvedna":
                position = 20;
                break;
            case "Artifact":
                position = 21;
                break;
            case "Industrial Tycoon":
                position = 22;
                break;
            case "M&I Quiz":
                position = 23;
                break;
            case "MICAV":
                position = 24;
                break;
            case "DronaGyan":
                position = 25;
                break;
            case "Utpreaks":
                position = 26;
                break;
            case "Analog Hunter":
                position = 27;
                break;
            case "Codesense":
                position = 28;
                break;
            case "Digizone":
                position = 29;
                break;
            case "Labyrinth":
                position = 30;
                break;
            case "Embetrix":
                position = 31;
                break;
            case "Web Weaver":
                position = 32;
                break;
            case "Sudo Code":
                position = 33;
                break;
            case "Jigyasa":
                position = 34;
                break;
            case "Algorithma":
                position = 35;
                break;
            case "Ansys":
                position = 36;
                break;
            case "Netkraft":
                position = 37;
                break;
            case "Spectra":
                position = 38;
                break;
            case "Tukvilla":
                position = 39;
                break;
            case "Metal Trivia":
                position = 40;
                break;
            case "K.O.":
                position = 41;
                break;
            case "Exposicion":
                position = 42;
                break;
            case "Code-O-Soccer":
                position = 43;
                break;
            case "Codemania":
                position = 44;
                break;
            case "Autoquiz":
                position = 45;
                break;
            case "Enigma":
                position = 46;
                break;
            case "Junkyard Wars":
                position = 47;
                break;
            case "Teenpreneur":
                position = 48;
                break;
            case "Prakshepan":
                position = 49;
                break;
            case "Samveg":
                position = 50;
                break;
            case "Agnikund":
                position = 51;
                break;
            case "Acumen":
                position = 52;
                break;
            case "Pipe-o-Mania":
                position = 53;
                break;
            case "ABC":
                position = 54;
                break;
            case "Corporate Roadies":
                position = 55;
                break;
            case "Let's Start Up":
                position = 56;
                break;
            case "Neetishastra":
                position = 57;
                break;
            case "TechArt":
                position = 58;
                break;
            case "Accelodrome":
                position = 59;
                break;
            case "Touch Down the plane":
                position = 60;
                break;
            case "Mad Ad":
                position = 61;
                break;
            case "SCI FI":
                position = 62;
                break;
            case "Tech-Know":
                position = 63;
                break;
            case "The Great Ojass Race":
                position = 64;
                break;
            case "App Droid":
                position = 65;
                break;
            case "Director's Cut":
                position = 66;
                break;
            case "LiveCS":
                position = 67;
                break;
            case "Innovision":
                position = 68;
                break;
            case "Wolf of Dalal Street":
                position = 69;
                break;
            case "Kurukshetra":
                position = 70;
                break;
            case "Tachyon":
                position = 71;
                break;
            case "Shapeshifter":
                position = 72;
                break;
            case "360 Mania":
                position = 73;
                break;
            case "MAC FIFA":
                position = 74;
                break;
            case "Battleship":
                position = 75;
                break;
            case "Bizzathlon":
                position = 76;
                break;
            case "Exempler":
                position = 77;
                break;
            case "Metropolis":
                position = 78;
                break;
            case "Archmadeease":
                position = 79;
                break;
            case "Sanrachna":
                position = 80;
                break;
            case "Lens View":
                position = 81;
                break;
            case "ScrapStar":
                position = 82;
                break;


        }

    }

}
