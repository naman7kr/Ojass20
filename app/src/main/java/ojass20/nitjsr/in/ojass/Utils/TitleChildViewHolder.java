package ojass20.nitjsr.in.ojass.Utils;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import ojass20.nitjsr.in.ojass.R;

public class TitleChildViewHolder extends ChildViewHolder {

    public TextView option1;

    public TitleChildViewHolder(View itemView) {
        super(itemView);
        option1=(TextView)itemView.findViewById(R.id.option1);
    }
}