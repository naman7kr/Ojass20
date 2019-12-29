package ojass20.nitjsr.in.ojass.Activities;


import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import ojass20.nitjsr.in.ojass.DeveloperDetailsFragment;
import ojass20.nitjsr.in.ojass.R;

public class DeveloperActivity extends AppCompatActivity implements BottomScrollFragment.FragmentNote{

    BottomScrollFragment bottomScrollFragment;
    DeveloperDetailsFragment developerDetailsFragment;
    private static final String TAG = "DeveloperActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        //startActivity(new Intent(DeveloperActivity.this,RegistrationPage.class));
        initializingFragment();

    }

    //setting up fragment for developers page
    public void initializingFragment(){
        bottomScrollFragment = new BottomScrollFragment();
        developerDetailsFragment = new DeveloperDetailsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout,bottomScrollFragment)
                .replace(R.id.detailsFragment, developerDetailsFragment)
                .commit();
    }

    @Override
    public void onFragmentNoteClick(String name) {
        Log.i(TAG, "onFragmentNoteClick: " + name);
        developerDetailsFragment.updateTheDetails(name);
    }

}
