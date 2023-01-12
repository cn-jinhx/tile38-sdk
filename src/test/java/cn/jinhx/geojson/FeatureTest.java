package cn.jinhx.geojson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FeatureTest {

    private Feature testObject = new Feature();
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void itShouldHaveProperties() throws Exception {
        assertNotNull(testObject.getProperties());
    }

    @Test
    public void itShouldSerializeFeature() throws Exception {
        assertEquals("{\"type\":\"Feature\",\"properties\":{},\"geometry\":null}",
                mapper.writeValueAsString(testObject));
    }
}
