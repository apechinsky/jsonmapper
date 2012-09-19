package org.jsonmapper;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Assert;
import static org.hamcrest.CoreMatchers.is;

/**
 * Base class for testing json implementations.
 *
 * @author Q-APE
 */
public abstract class AbstractJsonMapperTest {


    protected void assertAllTypesObjectEquals(AllTypesTestObject expected, AllTypesTestObject actual) {
        Assert.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    protected void assertComplexObject(ComplexObject object) {
        Assert.assertThat(object.property1, is("value1"));
        Assert.assertThat(object.property2, is(1));

        Assert.assertThat(object.property3, is(ComplexObject.SubObject.class));

        Assert.assertThat(object.property3.property1, is("value31"));
        Assert.assertThat(object.property3.property2, is(2));
        Assert.assertThat(object.property3.stringArray, is(new String[] {"value1", "value2", "value3"}));

        Assert.assertThat(object.stringArray, is(new String[] {"value1", "value2", "value3"}));

        Assert.assertThat(object.objectArray.length, is(2));

        Assert.assertThat(object.objectArray[0].property1, is("objectArray[0].property1"));
        Assert.assertThat(object.objectArray[0].property2, is(31));
        Assert.assertThat(object.objectArray[0].stringArray, is(new String[] {"value1", "value2", "value3"}));

        Assert.assertThat(object.objectArray[1].property1, is("objectArray[1].property1"));
        Assert.assertThat(object.objectArray[1].property2, is(32));
        Assert.assertThat(object.objectArray[1].stringArray, is(new String[] {"value1", "value2", "value3"}));
    }

    protected void assertComplexObjectFlat(ComplexObjectFlat object) {
        Assert.assertThat(object.property1, is("value1"));
        Assert.assertThat(object.property2, is(1));

        Assert.assertThat(object.property3property1, is("value31"));
        Assert.assertThat(object.property3property2, is(2));
        Assert.assertThat(object.property3stringArray, is(new String[] {"value1", "value2", "value3"}));

        Assert.assertThat(object.stringArray, is(new String[] {"value1", "value2", "value3"}));

        Assert.assertThat(object.objectArray0property1, is("objectArray[0].property1"));
        Assert.assertThat(object.objectArray0property2, is(31));
        Assert.assertThat(object.objectArray0stringArray, is(new String[] {"value1", "value2", "value3"}));

        Assert.assertThat(object.objectArray1property1, is("objectArray[1].property1"));
        Assert.assertThat(object.objectArray1property2, is(32));
        Assert.assertThat(object.objectArray1stringArray, is(new String[] {"value1", "value2", "value3"}));
    }


    public static AllTypesTestObject createTestObject() {

        // Warning!!! Content of this object should match AllTypesTestObject.json
        AllTypesTestObject object = new AllTypesTestObject();

        object.setBooleanField(true);
        object.setBooleanFieldArray(new boolean[] {true, false, true, false});
        object.setBooleanFieldWrapper(new Boolean(true));
        object.setBooleanFieldWrapperArray(new Boolean[] {true, true, false, true});

        object.setLongField(12345678901234L);
        object.setLongFieldArray(new long[] {35, 36, 37, 38});
        object.setLongFieldWrapper(35L);
        object.setLongFieldWrapperArray(new Long[] {35L, 36L, 37L, 38L});

        object.setDoubleField(123.456);
        object.setDoubleFieldArray(new double[] {123.456, 456.123});
        object.setDoubleFieldWrapper(123.456);
        object.setDoubleFieldWrapperArray(new Double[] {123.456, 456.123});

        object.setStringField("test string value");
        object.setStringFieldArray(new String[] {"string value1", "string value2", "string value3"});

        object.setLongFieldArrayNull(null);
        object.setLongFieldWrapperNull(null);
        object.setStringFieldNull(null);
        object.setStringFieldArrayNull(null);

        object.setNestedObject(new NestedObject("string value", 12345));

        object.setSex(Sex.FEMALE);

        return object;
    }


}
