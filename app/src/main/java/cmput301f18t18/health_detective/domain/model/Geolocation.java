package cmput301f18t18.health_detective.domain.model;

/**
 * Class to store info and methods relating to geolocations
 */
public class Geolocation {
    private double latitude;
    private double longitude;


    public Geolocation(double lat, double lon) {
        this.setCordinate(lat, lon);
    }

    public void setCordinate(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    public double getlatitude() {
        return latitude;
    }

    public double getlongitude() {
        return longitude;
    }
}
