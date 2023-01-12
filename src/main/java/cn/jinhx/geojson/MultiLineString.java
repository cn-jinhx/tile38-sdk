package cn.jinhx.geojson;

import java.util.List;

/**
 * @author y-z-f
 * @since 2023/1/1
 */
public class MultiLineString extends Geometry<List<LngLatAlt>> {

    public MultiLineString() {
    }

    public MultiLineString(List<LngLatAlt> line) {
        add(line);
    }

    @Override
    public <T> T accept(GeoJsonObjectVisitor<T> geoJsonObjectVisitor) {
        return geoJsonObjectVisitor.visit(this);
    }

    @Override
    public String toString() {
        return "MultiLineString{} " + super.toString();
    }
}
