package ojass20.nitjsr.in.ojass;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    String mName="",mEmail="",mLevel="",mSession="",mBranch="";
    DatabaseReference profRef;
    TextView profile_name,profile_email,level,branch,session;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View detailsFrag =  inflater.inflate(R.layout.fragment_details, container, false);
        initialize(detailsFrag);
        return detailsFrag;
    }

    private void initialize(View view){
        profile_name = view.findViewById(R.id.profile_name);
        profile_email = view.findViewById(R.id.profile_email);
        level = view.findViewById(R.id.level);
        branch = view.findViewById(R.id.branch);
        session = view.findViewById(R.id.session);
        profRef = FirebaseDatabase.getInstance().getReference().child("Developers");
    }


    public void updateTheDetails(String name){
        mName = name;
        profile_name.setText(mName);
        getUserInfo();
    }

    private void getUserInfo(){
        profRef.child(mName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    mEmail = dataSnapshot.child("email").getValue().toString();
                    profile_email.setText(mEmail);
                    mLevel = dataSnapshot.child("level").getValue().toString();
                    level.setText(mLevel);
                    mSession = dataSnapshot.child("session").getValue().toString();
                    session.setText(mSession);
                    mBranch = dataSnapshot.child("branch").getValue().toString();
                    branch.setText(mBranch);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
