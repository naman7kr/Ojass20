package ojass20.nitjsr.in.ojass.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ojass20.nitjsr.in.ojass.DetailsFragment;
import ojass20.nitjsr.in.ojass.R;

public class MainActivity extends AppCompatActivity implements BottomScrollFragment.FragmentNote{

    BottomScrollFragment bottomScrollFragment;
    DetailsFragment detailsFragment;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //startActivity(new Intent(MainActivity.this,RegistrationPage.class));
        initializingFragment();

    }

    //setting up fragment for developers page
    public void initializingFragment(){
        bottomScrollFragment = new BottomScrollFragment();
        detailsFragment = new DetailsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout,bottomScrollFragment)
                .replace(R.id.detailsFragment, detailsFragment)
                .commit();
    }

    @Override
    public void onFragmentNoteClick(String name) {
        Log.i(TAG, "onFragmentNoteClick: " + name);
        detailsFragment.updateTheDetails(name);
    }

}
