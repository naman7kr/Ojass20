package ojass20.nitjsr.in.ojass.Models;

public class ItinerraryModal {
    private String name;
    private String time;
    private String venue;

    public ItinerraryModal(String name, String time, String venue) {
        this.name = name;
        this.time = time;
        this.venue = venue;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getVenue() {
        return venue;
    }
}
