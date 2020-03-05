package ojass20.nitjsr.in.ojass.Models;

public class Row {
    private int img;
    private String url;
    public Row(int img,String url){
        this.img=img;
        this.url=url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
