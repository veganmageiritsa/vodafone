


package com.vodafone.xml.point;

import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "latitude","longitude"
})
@XmlRootElement(name = "NearestNeighborRequest")
public class NearestNeighborRequest {

    @XmlElement(required = true)
    protected double latitude;
    @XmlElement(required = true)
    protected double longitude;

    /**
     * Gets the value of the latitude property.
     *
     * @return
     *     possible object is
     *     {@link Double }
     *
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the value of the latitude property.
     *
     * @param latitude
     *     allowed object is
     *     {@link Double }
     *
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
