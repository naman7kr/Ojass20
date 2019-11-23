package ojass20.nitjsr.in.ojass.Models;

import android.widget.LinearLayout;

import java.util.ArrayList;

public class FeedPost {

    private String event;
    private String subEvent;
    private String content;
    private String imageURL;
    private ArrayList<Likes> likes;
    private ArrayList<Comments> comments;
    private String postid;
    private boolean is_already_liked;

    public FeedPost(){

    }

    public FeedPost(boolean is_liked,String mpostid, String content,String event,  String imageURL, String subEvent,
                    ArrayList<Likes> mlikes,ArrayList<Comments> mcomments) {
        this.is_already_liked=is_liked;
        this.event = event;
        this.subEvent = subEvent;
        this.content = content;
        this.imageURL = imageURL;
        this.postid=mpostid;

        likes=new ArrayList<>();
        comments=new ArrayList<>();
        this.likes=mlikes;
        this.comments=mcomments;

    }

    public void setIs_already_liked(boolean is_already_liked) {
        this.is_already_liked = is_already_liked;
    }

    public boolean isIs_already_liked() {
        return is_already_liked;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostid() {
        return postid;
    }


    public void setLikes(ArrayList<Likes> likes) {
        this.likes = likes;
    }

    public void setComments(ArrayList<Comments> comments) {
        this.comments = comments;
    }

    public ArrayList<Likes> getLikes() {
        return likes;
    }

    public ArrayList<Comments> getComments() {
        return comments;
    }

    public String getEvent() {
        return event;
    }

    public String getSubEvent() {
        return subEvent;
    }

    public String getContent() {
        return content;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setSubEvent(String subEvent) {
        this.subEvent = subEvent;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
