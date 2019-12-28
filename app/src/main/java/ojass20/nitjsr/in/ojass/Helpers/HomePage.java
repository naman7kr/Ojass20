package ojass20.nitjsr.in.ojass.Helpers;

public class HomePage {
    public String mTitle;
    //private String mBackGround;
    public String mCircleColor;
    public int mIndex;
    public  int img;

    public HomePage(String mTitle, String mCircleColor, int mIndex,int img) {
        this.mTitle = mTitle;
        //this.mBackGround = mBackGround;
        this.mCircleColor = mCircleColor;
        this.mIndex = mIndex;
        this.img = img;
    }

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



    public int getImg() {
        return img;
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

    public void setImg(int img) {
        this.img = img;
    }
}
