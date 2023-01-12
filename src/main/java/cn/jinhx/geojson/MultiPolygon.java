package cn.jinhx.geojson;

import java.util.List;

/**
 * @author y-z-f
 * @since 2023/1/1
 */
public class MultiPolygon extends Geometry<List<List<LngLatAlt>>> {

    public MultiPolygon() {
    }

    public MultiPolygon(Polygon polygon) {
        add(polygon);
    }

    public MultiPolygon add(Polygon polygon) {
        coordinates.add(polygon.getCoordinates());
        return this;
    }

    @Override
    public <T> T accept(GeoJsonObjectVisitor<T> geoJsonObjectVisitor) {
        return geoJsonObjectVisitor.visit(this);
    }

    @Override
    public String toString() {
        return "MultiPolygon{} " + super.toString();
    }
}
