package ojass20.nitjsr.in.ojass;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class EventFragment extends Fragment {

    private TextView mHeader, mBody;
    private String mHeaderText, mBodyText;

    public EventFragment(){
        // Empty Constructor
    }

    private EventFragment(String headerText, String bodyText) {
        mHeaderText = headerText;
        mBodyText = bodyText;
    }

    public static EventFragment newInstance(String headerText, String bodyText) {
        EventFragment fragment = new EventFragment(headerText, bodyText);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mLayoutView = inflater.inflate(R.layout.fragment_event, container, false);
        mHeader = mLayoutView.findViewById(R.id.event_fragment_header);
        mBody = mLayoutView.findViewById(R.id.event_fragment_body);

        setHeader(mHeaderText);
        setBody(mBodyText);

        return mLayoutView;
    }

    private void setHeader(String text){
        mHeader.setText(text);
    }

    private void setBody(String text){
        mBody.setText(Html.fromHtml(text));
    }

}
