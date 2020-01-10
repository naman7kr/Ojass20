package ojass20.nitjsr.in.ojass.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;

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
import java.util.HashMap;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ojass20.nitjsr.in.ojass.Adapters.SubEventsAdapter;
import ojass20.nitjsr.in.ojass.Fragments.EventBottomSheet;
import ojass20.nitjsr.in.ojass.Models.BranchHeadModal;
import ojass20.nitjsr.in.ojass.Models.SubEventsModel;
import ojass20.nitjsr.in.ojass.R;
import ojass20.nitjsr.in.ojass.Utils.Constants;
import ojass20.nitjsr.in.ojass.Utils.RecyclerClickInterface;

import static ojass20.nitjsr.in.ojass.Utils.Constants.SubEventsList;

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
    private ArrayList<TextView> mName;
    private TextView mAbout, mHeading;
    private View mDivider1, mDivider2;
    private ArrayList<String> mSubEventName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_event);
        init();
        //get intent get main event
        mainEventPosition = getIntent().getIntExtra("event_id", 0); //from intent
        mSubEventName = getIntent().getStringArrayListExtra("sub_event_name");
        rView.setLayoutManager(new LinearLayoutManager(this));

        manageToolbar();

        //on item click
        RecyclerClickInterface mInterface = new RecyclerClickInterface() {
            @Override
            public void onLayoutClick(View v, int position) {
                getSupportActionBar().setTitle(SubEventsList.get(mainEventPosition).get(position));
                showBottomSheet();
                bottomSheetOpen = true;
                getPostion(position);
            }
        };

        if (mSubEventName != null) {
            showBottomSheet();
            bottomSheetOpen = true;
            getPostion(mSubEventName.get(0));
        }

        rView.setAdapter(new SubEventsAdapter(this, getData(), mInterface));
        //set Image
        iv.setImageResource(Constants.eventImg[mainEventPosition]);


        mFab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
                final ArrayList<BranchHeadModal> bhNames = MainActivity.branchData.get(Constants.eventNames.get(mainEventPosition)).getBranchHead();
                dialogView.setMinimumWidth((int) (displayRectangle.width() / 1.5f * 1f));
                dialogView.setMinimumHeight((int) (displayRectangle.height() / 5f * 1f));
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                mLL = new ArrayList<>();
                mProfile = new ArrayList<>();
                mCall = new ArrayList<>();
                mWhatsapp = new ArrayList<>();
                mName = new ArrayList<>();
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

                mName.add((TextView) dialogView.findViewById(R.id.name1));
                mName.add((TextView) dialogView.findViewById(R.id.name2));
                mName.add((TextView) dialogView.findViewById(R.id.name3));

                mCall.add((ImageView) dialogView.findViewById(R.id.call1));
                mCall.add((ImageView) dialogView.findViewById(R.id.call2));
                mCall.add((ImageView) dialogView.findViewById(R.id.call3));

                mDivider1 = dialogView.findViewById(R.id.divider1);
                mDivider2 = dialogView.findViewById(R.id.divider2);
                if (bhNames.size() == 1) {
                    mLL.get(0).setVisibility(View.VISIBLE);
                    mName.get(0).setText(bhNames.get(0).getName());
                    final Intent intent = new Intent(Intent.ACTION_DIAL);
                    for (int j = 0; j < 1; j++) {
                        final int i = j;
                        mCall.get(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                intent.setData(Uri.parse("tel:" + bhNames.get(i).getCn()));
                                startActivity(intent);
                            }
                        });
                        mWhatsapp.get(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String url = "https://api.whatsapp.com/send?phone=" + "+91" + bhNames.get(i).getWn();
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            }
                        });
                    }
                } else if (bhNames.size() == 2) {
                    mLL.get(0).setVisibility(View.VISIBLE);
                    mLL.get(1).setVisibility(View.VISIBLE);
                    mName.get(0).setText(bhNames.get(0).getName());
                    mName.get(1).setText(bhNames.get(1).getName());
                    mDivider1.setVisibility(View.VISIBLE);
                    final Intent intent = new Intent(Intent.ACTION_DIAL);
                    for (int j = 0; j < 2; j++) {
                        final int i = j;
                        mCall.get(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                intent.setData(Uri.parse("tel:" + bhNames.get(i).getCn()));
                                startActivity(intent);
                            }
                        });
                        mWhatsapp.get(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String url = "https://api.whatsapp.com/send?phone=" + "+91" + bhNames.get(i).getWn();
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            }
                        });
                    }
                } else {
                    mLL.get(0).setVisibility(View.VISIBLE);
                    mLL.get(1).setVisibility(View.VISIBLE);
                    mLL.get(2).setVisibility(View.VISIBLE);
                    mDivider1.setVisibility(View.VISIBLE);
                    mDivider2.setVisibility(View.VISIBLE);
                    mName.get(0).setText(bhNames.get(0).getName());
                    mName.get(1).setText(bhNames.get(1).getName());
                    mName.get(2).setText(bhNames.get(2).getName());
                    final Intent intent = new Intent(Intent.ACTION_DIAL);
                    for (int j = 0; j < 3; j++) {
                        final int i = j;
                        mCall.get(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                intent.setData(Uri.parse("tel:" + bhNames.get(i).getCn()));
                                startActivity(intent);
                            }
                        });
                        mWhatsapp.get(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String url = "https://api.whatsapp.com/send?phone=" + "+91" + bhNames.get(i).getWn();
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            }
                        });
                    }
                }
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
                dialogView.setMinimumHeight((int) (displayRectangle.height() * 1f));
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                mAbout = dialogView.findViewById(R.id.about_data);
                mAbout.setVisibility(View.VISIBLE);
                mHeading = dialogView.findViewById(R.id.heading);
                String s = "About " + Constants.eventNames.get(mainEventPosition);
                mHeading.setText(s);
                mAbout.setText(MainActivity.branchData.get(Constants.eventNames.get(mainEventPosition)).getAbout());
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
        toolbar.setTitle(Constants.eventNames.get(mainEventPosition));

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
        HashMap<Integer, ArrayList<String>> subEventsName = SubEventsList;
        for (int i = 0; i < subEventsName.get(mainEventPosition).size(); i++) {
            data.add(new SubEventsModel(subEventsName.get(mainEventPosition).get(i)));
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
        toolbar.setTitle(Constants.eventNames.get(mainEventPosition));
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

    public void getPostion(int pos) {
        String event = SubEventsList.get(mainEventPosition).get(pos).trim();
        Log.e("this", "" + event);
        for (int i = 0; i < MainActivity.data.size(); i++) {
            try {
                if (event.equalsIgnoreCase(MainActivity.data.get(i).getName().trim())) {
                    position = i;
                    break;
                }
            } catch (Exception e) {
                Log.e("Sub", e.getLocalizedMessage());
            } finally {
                continue;
            }
        }
    }

    public void getPostion(String subEvent) {
        for (int i = 0; i < MainActivity.data.size(); i++) {
            try {
                if (subEvent.equalsIgnoreCase(MainActivity.data.get(i).getName().trim())) {
                    position = i;
                    break;
                }
            } catch (Exception e) {
                Log.e("Sub", e.getLocalizedMessage());
            } finally {
                continue;
            }
        }
    }
}
