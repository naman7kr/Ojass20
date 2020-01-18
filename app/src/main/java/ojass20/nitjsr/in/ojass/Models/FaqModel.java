package ojass20.nitjsr.in.ojass.Models;

public class FaqModel {

    String ques, ans;
    private boolean isExplandable;

    public FaqModel(String ques, String ans) {
        this.ques=ques;
        this.ans=ans;
        this.isExplandable = false;
    }

    public FaqModel()
    {

    }

    public String getAns() {
        return ans;
    }

    public String getQues() {
        return ques;
    }

    public void setExplandable(boolean explandable) {
        isExplandable = explandable;
    }

    public boolean isExplandable() {
        return isExplandable;
    }

}