package cn.jinhx.geojson;

/**
 * @author y-z-f
 * @since 2023/1/1
 */
public class LineString extends MultiPoint {

    public LineString() {
    }

    public LineString(LngLatAlt... points) {
        super(points);
    }

    @Override
    public <T> T accept(GeoJsonObjectVisitor<T> geoJsonObjectVisitor) {
        return geoJsonObjectVisitor.visit(this);
    }

    @Override
    public String toString() {
        return "LineString{} " + super.toString();
    }
}
