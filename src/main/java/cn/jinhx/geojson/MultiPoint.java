package cn.jinhx.geojson;

/**
 * 多坐标点对象
 * @author y-z-f
 * @since 2023/1/1
 */
public class MultiPoint extends Geometry<LngLatAlt> {

    public MultiPoint() {
    }

    public MultiPoint(LngLatAlt... points) {
        super(points);
    }

    @Override
    public <T> T accept(GeoJsonObjectVisitor<T> geoJsonObjectVisitor) {
        return geoJsonObjectVisitor.visit(this);
    }

    @Override
    public String toString() {
        return "MultiPoint{} " + super.toString();
    }
}
