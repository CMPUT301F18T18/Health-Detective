package cmput301f18t18.health_detective.model.domain;

import android.support.annotation.NonNull;

public class Geolocation implements Comparable<Geolocation> {
    private double Latitude;
    private double Longitude;

    @Override
    public int compareTo(@NonNull Geolocation o) {
        return 0;
    }
}
