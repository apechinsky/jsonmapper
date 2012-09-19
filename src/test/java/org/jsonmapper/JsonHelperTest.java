package org.jsonmapper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import static org.hamcrest.CoreMatchers.is;

import junit.framework.TestCase;

/**
 * Test class for JsonHelper
 *
 * @author Q-APE
 */
public class JsonHelperTest extends TestCase {

    public void testGetSimpleProperty() throws Exception {
        JSONObject json = JsonTestUtils.loadJsonObject(getClass().getResource("ComplexObject.json"));

        String property1 = JsonHelper.get(json, "property1");
        Assert.assertThat(property1, is("value1"));

        Integer property2 = JsonHelper.get(json, "property2");
        Assert.assertThat(property2, is(1));

        JSONArray jsonArray = (JSONArray) JsonHelper.get(json, "stringArray");
        Object[] stringArray = JsonHelper.toObjectArray(jsonArray, String.class);
        Assert.assertThat(stringArray, is(new Object[] {"value1", "value2", "value3"}));
    }

    public void testGetIndexedProperty() throws Exception {
        JSONObject json = JsonTestUtils.loadJsonObject(getClass().getResource("ComplexObject.json"));

        String string = JsonHelper.get(json, "stringArray[1]");
        Assert.assertThat(string, is("value2"));
    }

    public void testGetNestedProperty() throws Exception {
        JSONObject json = JsonTestUtils.loadJsonObject(getClass().getResource("ComplexObject.json"));

        String property31 = JsonHelper.get(json, "property3.property1");
        Assert.assertThat(property31, is("value31"));

        JSONArray jsonArray = (JSONArray) JsonHelper.get(json, "property3.stringArray");
        Object[] stringArray = JsonHelper.toObjectArray(jsonArray, String.class);
        Assert.assertThat(stringArray, is(new Object[] {"value1", "value2", "value3"}));
    }

    public void testGetStress() throws Exception {
        JSONObject json = JsonTestUtils.loadJsonObject(getClass().getResource("ComplexObject.json"));

        String string = JsonHelper.get(json, "objectArray[1].stringArray[2]");
        Assert.assertThat(string, is("value3"));
    }
}
