package cn.jinhx.geojson;

import cn.jinhx.geojson.jackson.LngLatAltDeserializer;
import cn.jinhx.geojson.jackson.LngLatAltSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author y-z-f
 * @date 2023/1/1
 */
@JsonDeserialize(using = LngLatAltDeserializer.class)
@JsonSerialize(using = LngLatAltSerializer.class)
public class LngLatAlt implements Serializable{

    private double longitude;
    private double latitude;
    private double altitude = Double.NaN;
    private double[] additionalElements = new double[0];

    public LngLatAlt() {
    }

    public LngLatAlt(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public LngLatAlt(double longitude, double latitude, double altitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }

    /**
     * 构造经纬度以及附加属性
     * @param longitude 经度
     * @param latitude 纬度
     * @param altitude 海拔
     * @param additionalElements 附加属性
     */
    public LngLatAlt(double longitude, double latitude, double altitude, double... additionalElements) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;

        setAdditionalElements(additionalElements);
        checkAltitudeAndAdditionalElements();
    }

    public boolean hasAltitude() {
        return !Double.isNaN(altitude);
    }

    public boolean hasAdditionalElements() {
        return additionalElements.length > 0;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
        checkAltitudeAndAdditionalElements();
    }

    public double[] getAdditionalElements() {
        return additionalElements;
    }

    public void setAdditionalElements(double... additionalElements) {
        if (additionalElements != null) {
            this.additionalElements = additionalElements;
        } else {
            this.additionalElements = new double[0];
        }

        for(double element : this.additionalElements) {
            if (Double.isNaN(element)) {
                throw new IllegalArgumentException("No additional elements may be NaN.");
            }
            if (Double.isInfinite(element)) {
                throw new IllegalArgumentException("No additional elements may be infinite.");
            }
        }

        checkAltitudeAndAdditionalElements();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LngLatAlt)) {
            return false;
        }
        LngLatAlt lngLatAlt = (LngLatAlt)o;
        return Double.compare(lngLatAlt.latitude, latitude) == 0 && Double.compare(lngLatAlt.longitude, longitude) == 0
                && Double.compare(lngLatAlt.altitude, altitude) == 0 &&
                Arrays.equals(lngLatAlt.getAdditionalElements(), additionalElements);
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(longitude);
        int result = (int)(temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int)(temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(altitude);
        result = 31 * result + (int)(temp ^ (temp >>> 32));
        for(double element : additionalElements) {
            temp = Double.doubleToLongBits(element);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
        }
        return result;
    }

    @Override
    public String toString() {
        String s =  "LngLatAlt{" + "longitude=" + longitude + ", latitude=" + latitude + ", altitude=" + altitude;

        if (hasAdditionalElements()) {
            s += ", additionalElements=[";

            String suffix = "";
            for (Double element : additionalElements) {
                if (element != null) {
                    s += suffix + element;
                    suffix = ", ";
                }
            }
            s += ']';
        }
        s += '}';
        return s;
    }

    private void checkAltitudeAndAdditionalElements() {
        if (!hasAltitude() && hasAdditionalElements()) {
            throw new IllegalArgumentException("Additional Elements are only valid if Altitude is also provided.");
        }
    }
}
