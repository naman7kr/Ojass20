package ojass20.nitjsr.in.ojass.Models;

public class TeamMember {
    public String name,desig,whatsapp,facebook,github,call;
    public int team,img;

    public TeamMember(String name, String desig, String whatsapp, String facebook, String github, String call,int img, int team) {
        this.name = name;
        this.desig = desig;
        this.whatsapp = whatsapp;
        this.facebook = facebook;
        this.github = github;
        this.call = call;
        this.team = team;
        this.img=img;
    }
}
