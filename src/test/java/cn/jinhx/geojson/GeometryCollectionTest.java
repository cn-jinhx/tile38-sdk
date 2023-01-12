package cn.jinhx.geojson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.*;

public class GeometryCollectionTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSerialize() throws Exception {
        GeometryCollection gc = new GeometryCollection();
        gc.add(new Point(100, 0));
        gc.add(new LineString(new LngLatAlt(101, 0), new LngLatAlt(102, 1)));
        assertEquals("{\"type\":\"GeometryCollection\","
                        + "\"geometries\":[{\"type\":\"Point\",\"coordinates\":[100.0,0.0]},"
                        + "{\"type\":\"LineString\",\"coordinates\":[[101.0,0.0],[102.0,1.0]]}]}",
                mapper.writeValueAsString(gc));
    }

    @Test
    public void testDeserialize() throws Exception {
        GeometryCollection geometryCollection = mapper
                .readValue("{\"type\":\"GeometryCollection\","
                                + "\"geometries\":[{\"type\":\"Point\",\"coordinates\":[100.0,0.0]},"
                                + "{\"type\":\"LineString\",\"coordinates\":[[101.0,0.0],[102.0,1.0]]}]}",
                        GeometryCollection.class);
        assertNotNull(geometryCollection);
    }

    @Test
    public void testSubTypeDeserialize() throws Exception {
        FeatureCollection collection = mapper
                .readValue("{\"type\": \"FeatureCollection\","
                                + "  \"features\": ["
                                + "    {"
                                + "      \"type\": \"Feature\","
                                + "      \"geometry\": {"
                                + "        \"type\": \"GeometryCollection\","
                                + "        \"geometries\": ["
                                + "          {"
                                + "            \"type\": \"Point\","
                                + "            \"coordinates\": [100.0, 0.0]"
                                + "          }"
                                + "        ]"
                                + "      }"
                                + "    }"
                                + "  ]"
                                + "}",
                        FeatureCollection.class);
        assertNotNull(collection);
        assertTrue(collection.getFeatures().get(0).getGeometry() instanceof GeometryCollection);
    }
}
