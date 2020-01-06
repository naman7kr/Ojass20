package ojass20.nitjsr.in.ojass.Helpers;

public class HomePage {
    public String mTitle;
    private int mImageId;
    public String mCircleColor;
    public int mIndex;

    public HomePage(String mTitle, String mCircleColor, int mIndex, int mImageId) {
        this.mTitle = mTitle;
        this.mImageId = mImageId;
        this.mCircleColor = mCircleColor;
        this.mIndex = mIndex;
    }

    public String getmTitle() {
        return mTitle;
    }

    public int getmImageId() {
        return mImageId;
    }

    public String getmCircleColor() {
        return mCircleColor;
    }

    public int getmIndex() {
        return mIndex;
    }


    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmCircleColor(String mCircleColor) {
        this.mCircleColor = mCircleColor;
    }

    public void setmIndex(int mIndex) {
        this.mIndex = mIndex;
    }

}
