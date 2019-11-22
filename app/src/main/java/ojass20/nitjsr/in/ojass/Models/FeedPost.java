package ojass20.nitjsr.in.ojass.Models;

import android.widget.LinearLayout;

public class FeedPost {

    private String event;
    private String subEvent;
    private String content;
    private String imageURL;

    public FeedPost(){

    }

    public FeedPost(String content,String event,  String imageURL, String subEvent) {
        this.event = event;
        this.subEvent = subEvent;
        this.content = content;
        this.imageURL = imageURL;
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
