package cmput301f18t18.health_detective.domain.model;

public class Geolocation {
    private double latitude;
    private double longitude;


    public Geolocation(double lat, double lon) {
        latitude = lat;
        longitude = lon;
    }

    public void setlatitude(double latitude) {
        latitude = latitude;
    }

    public void setlongitude(double longitude) {
        longitude = longitude;
    }

    public void setBoth(double lat, double lon) {
        setlatitude(lat);
        setlongitude(lon);
    }

    public double getlatitude() {
        return latitude;
    }

    public double getlongitude() {
        return longitude;
    }

    public double getDist(Geolocation geolocation) {
        // Return the distance from this to geolocation
        return 0;
    }

}
