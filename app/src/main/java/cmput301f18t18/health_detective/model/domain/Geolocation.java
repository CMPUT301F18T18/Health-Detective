package cmput301f18t18.health_detective.model.domain;

public class Geolocation {
    private double Latitude;
    private double Longitude;


    public Geolocation(double lat, double lon) {
        Latitude = lat;
        Longitude = lon;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public void setBoth(double lat, double lon) {
        setLatitude(lat);
        setLongitude(lon);
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public double getDist(Geolocation geolocation) {
        // Return the distance from this to geolocation
        return 0;
    }

}
