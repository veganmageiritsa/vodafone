package com.vodafone.dto;

import com.vodafone.domain.PointTwoD;

/**
 * Created by ktmle on 30/10/2018.
 */
public class Point2DDto {
    private int locationId;
    private  double x;    // x coordinate
    private  double y;    // y coordinate
    private int frequency;

    public Point2DDto(PointTwoD pointTwoD) {
        this.locationId = pointTwoD.getLocationId();
        this.x = pointTwoD.x();
        this.y = pointTwoD.y();
        this.frequency = pointTwoD.getFrequency();
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
