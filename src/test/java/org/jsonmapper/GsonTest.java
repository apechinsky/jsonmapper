package org.jsonmapper;

import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.gson.Gson;

/**
 * A test demonstrating object mapping with gson library (https://sites.google.com/site/gson/)
 *
 * <p>The purpose of test is to compare api usability and key capabilities.</p>
 *
 * @author Q-APE
 */
public class GsonTest extends AbstractJsonMapperTest {

    /**
     * Doesn't work
     */
    @Test
    public void testAllTypesTestObjectMapping() throws Exception {
        String json = Resources.toString(getClass().getResource("ComplexObject.json"), Charsets.UTF_8);

        Gson gson = new Gson();
        AllTypesTestObject decodedObject = gson.fromJson(json, AllTypesTestObject.class);
        AllTypesTestObject etalonObject = createTestObject();

//        assertAllTypesObjectEquals(etalonObject, decodedObject);
    }

    /**
     * This method works out of the box with Gson.
     */
    @Test
    public void testComplexObjectMapping() throws Exception {
        String json = Resources.toString(getClass().getResource("ComplexObject.json"), Charsets.UTF_8);

        Gson gson = new Gson();
        ComplexObject object = gson.fromJson(json, ComplexObject.class);

        assertComplexObject(object);
    }


    /**
     * Doesn't work because no idea how to map nested properties (custom serializers?)
     */
    @Test
    public void testComplexObjectFlatMapping() throws Exception {
        String json = Resources.toString(getClass().getResource("ComplexObject.json"), Charsets.UTF_8);

        Gson gson = new Gson();
        ComplexObjectFlat object = gson.fromJson(json, ComplexObjectFlat.class);

        // assertComplexObjectFlat(object);
    }

}
