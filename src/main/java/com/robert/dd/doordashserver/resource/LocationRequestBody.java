package com.robert.dd.doordashserver.resource;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class LocationRequestBody {

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    /**
     * Radius in meters
     */
    @NotNull
    @Min(1)
    @Max(20000) // 20km
    private double radius = 20000;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
