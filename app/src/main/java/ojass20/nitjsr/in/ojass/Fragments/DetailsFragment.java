package ojass20.nitjsr.in.ojass.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ojass20.nitjsr.in.ojass.Activities.MainActivity;
import ojass20.nitjsr.in.ojass.Activities.SubEventActivity;
import ojass20.nitjsr.in.ojass.R;

public class DetailsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details,container,false);
        createUI(view);
        return view;
    }

    private void createUI(View view) {

        TextView t2=view.findViewById(R.id.text_details);
        t2.setText(Html.fromHtml(MainActivity.data.get(SubEventActivity.position).getDetails()));
    }
}
