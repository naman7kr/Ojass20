package ojass20.nitjsr.in.ojass.Models;

public class NotificationModal {
    private String header,body;
    private boolean isExplandable;

    public NotificationModal(String header, String body) {
        this.header = header;
        this.body = body;
        isExplandable = false;
    }

    public void setExplandable(boolean explandable) {
        isExplandable = explandable;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public boolean isExplandable() {
        return isExplandable;
    }
}
