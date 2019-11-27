package ojass20.nitjsr.in.ojass.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ojass20.nitjsr.in.ojass.R;

public class AboutFragment extends Fragment {
    private TextView abt;
    private LinearLayout abt_layout;
//    private BtmNavVisCallback mCallback;

    //variables
    private DatabaseReference mRef;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about,container,false);
        init(view);

//        abt_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCallback.onCallback();
//            }
//        });
        getData();
        return view;
    }
    void init(View view){
        abt = view.findViewById(R.id.text_about);
        abt_layout = view.findViewById(R.id.abt_layout);
        mRef = FirebaseDatabase.getInstance().getReference("Events");
    }
    void getData(){
//       for(EventModel em: HomeActivity.data){
//           if(em.getName()!=null) {
//               if (em.getName().compareToIgnoreCase(SubEventsActivity.event_name) == 0) {
//                   Log.e("ABOUT",em.getName());
//                   abt.setText(Html.fromHtml(em.getAbout()));
//                   break;
//               }
//           }
//        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        mCallback = (BtmNavVisCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mCallback = null;
    }
}
