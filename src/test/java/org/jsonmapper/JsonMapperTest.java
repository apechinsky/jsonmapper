package org.jsonmapper;

import org.json.JSONObject;
import org.junit.Test;
import org.srplib.conversion.ConverterRegistry;
import org.srplib.conversion.IntegerToLongConverter;
import org.srplib.conversion.StringToEnumConverter;

/**
 * @author Q-APE
 */
public class JsonMapperTest extends AbstractJsonMapperTest {

    @Test
    public void testAllTypesTestObjectMapping() throws Exception {
        JSONObject jsonObject = JsonTestUtils.loadJsonObject(getClass().getResource("AllTypesTestObject.json"));

        ConverterRegistry converterRegistry = new ConverterRegistry();
        converterRegistry.registerConverter(String.class, Sex.class, new StringToEnumConverter<Sex>(Sex.class));
        converterRegistry.registerConverter(Integer.class, Long.class, new IntegerToLongConverter());

        JsonMapper jsonMapper = new JsonMapper(converterRegistry);

        AllTypesTestObject decodedObject = jsonMapper.jsonDecode(jsonObject, AllTypesTestObject.class);
        AllTypesTestObject etalonObject = createTestObject();

        assertAllTypesObjectEquals(etalonObject, decodedObject);
    }

    @Test
    public void testComplexObjectMapping() throws Exception {
        JSONObject jsonObject = JsonTestUtils.loadJsonObject(getClass().getResource("ComplexObject.json"));

        JsonMapper jsonMapper = new JsonMapper();

        ComplexObject object = jsonMapper.jsonDecode(jsonObject, ComplexObject.class);

        assertComplexObject(object);
    }

    @Test
    public void testComplexObjectFlatMapping() throws Exception {
        JSONObject jsonObject = JsonTestUtils.loadJsonObject(getClass().getResource("ComplexObject.json"));

        JsonMapper jsonMapper = new JsonMapper();

        ComplexObjectFlat object = jsonMapper.jsonDecode(jsonObject, ComplexObjectFlat.class);

        assertComplexObjectFlat(object);
    }


//    @Test
//    public void testObjectToJsonMapping() throws Exception {
//
//        JSONObject jsonObject = loadJsonObject("AllTypesTestObject.json");
//
//        JsonMapper jsonMapper = JsonHelper.newJsonMapper();
//        jsonMapper.getConverterRegistry().
//            registerConverter(String.class, Sex.class, Converters.stringToEnumConverter(Sex.class));
//
//        AllTypesTestObject testObject = createTestObject();
//        JSONObject jsonObject1 = jsonMapper.jsonEncode(testObject);
//        System.out.println("JsonMapperTest.testObjectToJsonMapping");
//    }


}
