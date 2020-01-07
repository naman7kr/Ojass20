package ojass20.nitjsr.in.ojass.Models;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ojass20.nitjsr.in.ojass.Activities.FaqActivity;

public class TitleCreater {
    public static TitleCreater _titleCreator;

    public static List<TitleParent> _titleParents;

    public TitleCreater(Context context) {
        _titleParents=new ArrayList<>();
        _titleParents.clear();

        for(int i = 0; i< FaqActivity.data.size(); i++)
        {
            _titleParents.add(new TitleParent(FaqActivity.data.get(i).getQues()));

        }
    }

    public static TitleCreater get(Context context)
    {
        if(_titleCreator == null)
            _titleCreator=new TitleCreater(context);
        return _titleCreator;
    }

    public List<TitleParent> getAll() {
        return _titleParents;
    }
}