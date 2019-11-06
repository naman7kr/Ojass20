package ojass20.nitjsr.in.ojass.Helpers;

public class HomePage {
    private String mTitle;
    //private String mBackGround;
    private String mCircleColor;
    private int mIndex;

    public String getmTitle() {
        return mTitle;
    }

//    public String getmBackGround() {
//        return mBackGround;
//    }

    public String getmCircleColor() {
        return mCircleColor;
    }

    public int getmIndex() {
        return mIndex;
    }

    public HomePage(String mTitle, String mCircleColor, int mIndex) {
        this.mTitle = mTitle;
        //this.mBackGround = mBackGround;
        this.mCircleColor = mCircleColor;
        this.mIndex = mIndex;
    }
}
