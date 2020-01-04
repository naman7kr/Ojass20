package ojass20.nitjsr.in.ojass.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ojass20.nitjsr.in.ojass.Adapters.NotificationAdapter;
import ojass20.nitjsr.in.ojass.Models.NotificationModal;
import ojass20.nitjsr.in.ojass.R;

import static ojass20.nitjsr.in.ojass.Utils.Constants.FIREBASE_REF_NOTIF;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressDialog p;
    Spinner spinner;
    DatabaseReference ref;
    ArrayList<NotificationModal> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        recyclerView = findViewById(R.id.recycler_view);
        spinner = findViewById(R.id.spinner_feed);

        p=new ProgressDialog(this);
        data = new ArrayList<>();

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

    }
    public void onItemSelect()
    {

        p.setMessage("Loading Feed....");
        p.setCancelable(true);
        p.show();
        ref= FirebaseDatabase.getInstance().getReference().child(FIREBASE_REF_NOTIF).child(spinner.getSelectedItem().toString());
        ref.keepSynced(true);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                p.dismiss();
                data.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                   String header = ds.child("ques").getValue().toString();
                   String body = ds.child("ans").getValue().toString();

                   data.add(new NotificationModal(header, body));
                }

                NotificationAdapter adapter = new NotificationAdapter(NotificationActivity.this,data);
                recyclerView.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
