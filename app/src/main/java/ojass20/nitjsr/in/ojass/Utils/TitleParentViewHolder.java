package ojass20.nitjsr.in.ojass.Utils;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import ojass20.nitjsr.in.ojass.R;

public class TitleParentViewHolder extends ParentViewHolder {

    public TextView _textView;
    public TitleParentViewHolder(View itemView) {
        super(itemView);
        _textView=(TextView)itemView.findViewById(R.id.parentTitle);
    }
}