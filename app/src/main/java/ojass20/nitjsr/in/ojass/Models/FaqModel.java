package ojass20.nitjsr.in.ojass.Models;

public class FaqModel {

    String ques,ans;


    public FaqModel(String ques, String ans) {

        this.ques=ques;
        this.ans=ans;


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
}