package ojass20.nitjsr.in.ojass.Activities;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import ojass20.nitjsr.in.ojass.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomScrollFragment extends Fragment implements DeveloperRecyclerViewAdapter.OnNoteListener {

    private ArrayList<String> mNames = new ArrayList<>();
    DeveloperRecyclerViewAdapter developerRecyclerViewAdapter;
    private DatabaseReference nameRef;
    FragmentNote fragmentNote;

    private static final String TAG = "BottomScrollFragment";

    public BottomScrollFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View nameView = inflater.inflate(R.layout.fragment_bottom_scroll, container, false);
        nameRef = FirebaseDatabase.getInstance().getReference().child("Developers");
        //for retreiving name from database
        retrieveName();
        RecyclerView nameRecyclerView = nameView.findViewById(R.id.nameRecyclerView);
        nameRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        developerRecyclerViewAdapter = new DeveloperRecyclerViewAdapter(mNames,getActivity(),this);
        nameRecyclerView.setAdapter(developerRecyclerViewAdapter);
        return nameView;
    }

    private void retrieveName() {
        nameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    mNames.add(((DataSnapshot)iterator.next()).getKey());
                }
                developerRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onNoteClick(int position) {
        Log.i(TAG, "onNoteClick: reached" + String.valueOf(position)+ " "+ mNames.get(position));
        Toast.makeText(getActivity(), mNames.get(position), Toast.LENGTH_SHORT).show();
        fragmentNote.onFragmentNoteClick(mNames.get(position));
    }

    public interface FragmentNote{
        void onFragmentNoteClick(String name);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof FragmentNote)
            fragmentNote = (FragmentNote)context;
        else
        {
            throw new RuntimeException(context.toString() + "must be implemented");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        fragmentNote = null;
    }
}
