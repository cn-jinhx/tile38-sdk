package cn.jinhx.geojson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author y-z-f
 * @since 2023/1/1
 */
public abstract class Geometry<T> extends GeoJsonObject {
    protected List<T> coordinates = new ArrayList<>();

    public Geometry() {
    }

    public Geometry(T... elements) {
        for (T coordinate : elements) {
            coordinates.add(coordinate);
        }
    }

    public Geometry<T> add(T elements) {
        coordinates.add(elements);
        return this;
    }

    public List<T> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<T> coordinates) {
        this.coordinates = coordinates;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Geometry)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Geometry geometry = (Geometry)o;
        return !(coordinates != null ? !coordinates.equals(geometry.coordinates) : geometry.coordinates != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (coordinates != null ? coordinates.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Geometry{" + "coordinates=" + coordinates + "} " + super.toString();
    }
}
