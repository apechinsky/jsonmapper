package org.jsonmapper;

/**
 * @author Q-APE
 */
public class AllTypesTestObject {

    @JsonProperty("booleanField")
    private boolean booleanField;
    @JsonProperty("booleanFieldArray")
    private boolean[] booleanFieldArray;
    @JsonProperty("booleanFieldWrapper")
    private Boolean booleanFieldWrapper;
    @JsonProperty("booleanFieldWrapperArray")
    private Boolean[] booleanFieldWrapperArray;

    @JsonProperty("longField")
    private long longField;
    @JsonProperty("longFieldArray")
    private long[] longFieldArray;
    @JsonProperty("longFieldWrapper")
    private Long longFieldWrapper;
    @JsonProperty("longFieldWrapperArray")
    private Long[] longFieldWrapperArray;

    @JsonProperty("doubleField")
    private double doubleField;
    @JsonProperty("doubleFieldArray")
    private double[] doubleFieldArray;
    @JsonProperty("doubleFieldWrapper")
    private Double doubleFieldWrapper;
    @JsonProperty("doubleFieldWrapperArray")
    private Double[] doubleFieldWrapperArray;

    @JsonProperty("stringField")
    private String stringField;
    @JsonProperty("stringFieldArray")
    private String[] stringFieldArray;

    @JsonProperty("stringFieldNull")
    private String stringFieldNull = "";
    @JsonProperty("stringFieldArrayNull")
    private String[] stringFieldArrayNull = new String[0];

    @JsonProperty("longFieldArrayNull")
    private long[] longFieldArrayNull = new long[0];
    @JsonProperty("longFieldWrapperNull")
    private Long longFieldWrapperNull = Long.MAX_VALUE;

    @JsonProperty("nestedObject")
    private NestedObject nestedObject;

    @JsonProperty("sex")
    private Sex sex;

    public AllTypesTestObject() {
    }

    public boolean getBooleanField() {
        return booleanField;
    }

    public void setBooleanField(boolean booleanField) {
        this.booleanField = booleanField;
    }

    public long getLongField() {
        return longField;
    }

    public void setLongField(long longField) {
        this.longField = longField;
    }

    public double getDoubleField() {
        return doubleField;
    }

    public void setDoubleField(double doubleField) {
        this.doubleField = doubleField;
    }

    public boolean[] getBooleanFieldArray() {
        return booleanFieldArray;
    }

    public void setBooleanFieldArray(boolean[] booleanFieldArray) {
        this.booleanFieldArray = booleanFieldArray;
    }

    public long[] getLongFieldArray() {
        return longFieldArray;
    }

    public void setLongFieldArray(long[] longFieldArray) {
        this.longFieldArray = longFieldArray;
    }

    public double[] getDoubleFieldArray() {
        return doubleFieldArray;
    }

    public void setDoubleFieldArray(double[] doubleFieldArray) {
        this.doubleFieldArray = doubleFieldArray;
    }

    public Boolean getBooleanFieldWrapper() {
        return booleanFieldWrapper;
    }

    public void setBooleanFieldWrapper(Boolean booleanFieldWrapper) {
        this.booleanFieldWrapper = booleanFieldWrapper;
    }

    public Long getLongFieldWrapper() {
        return longFieldWrapper;
    }

    public void setLongFieldWrapper(Long longFieldWrapper) {
        this.longFieldWrapper = longFieldWrapper;
    }

    public Double getDoubleFieldWrapper() {
        return doubleFieldWrapper;
    }

    public void setDoubleFieldWrapper(Double doubleFieldWrapper) {
        this.doubleFieldWrapper = doubleFieldWrapper;
    }

    public Boolean[] getBooleanFieldWrapperArray() {
        return booleanFieldWrapperArray;
    }

    public void setBooleanFieldWrapperArray(Boolean[] booleanFieldWrapperArray) {
        this.booleanFieldWrapperArray = booleanFieldWrapperArray;
    }

    public Long[] getLongFieldWrapperArray() {
        return longFieldWrapperArray;
    }

    public void setLongFieldWrapperArray(Long[] longFieldWrapperArray) {
        this.longFieldWrapperArray = longFieldWrapperArray;
    }

    public Double[] getDoubleFieldWrapperArray() {
        return doubleFieldWrapperArray;
    }

    public void setDoubleFieldWrapperArray(Double[] doubleFieldWrapperArray) {
        this.doubleFieldWrapperArray = doubleFieldWrapperArray;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public String[] getStringFieldArray() {
        return stringFieldArray;
    }

    public void setStringFieldArray(String[] stringFieldArray) {
        this.stringFieldArray = stringFieldArray;
    }

    public long[] getIntFieldArrayNull() {
        return longFieldArrayNull;
    }

    public void setIntFieldArrayNull(long[] longFieldArrayNull) {
        this.longFieldArrayNull = longFieldArrayNull;
    }

    public Long getIntFieldWrapperNull() {
        return longFieldWrapperNull;
    }

    public void setIntFieldWrapperNull(Long longFieldWrapperNull) {
        this.longFieldWrapperNull = longFieldWrapperNull;
    }

    public String getStringFieldNull() {
        return stringFieldNull;
    }

    public void setStringFieldNull(String stringFieldNull) {
        this.stringFieldNull = stringFieldNull;
    }

    public String[] getStringFieldArrayNull() {
        return stringFieldArrayNull;
    }

    public void setStringFieldArrayNull(String[] stringFieldArrayNull) {
        this.stringFieldArrayNull = stringFieldArrayNull;
    }

    public NestedObject getNestedObject() {
        return nestedObject;
    }

    public void setNestedObject(NestedObject nestedObject) {
        this.nestedObject = nestedObject;
    }

    public void setLongFieldArrayNull(long[] longFieldArrayNull) {
        this.longFieldArrayNull = longFieldArrayNull;
    }

    public void setLongFieldWrapperNull(Long longFieldWrapperNull) {
        this.longFieldWrapperNull = longFieldWrapperNull;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public long[] getLongFieldArrayNull() {
        return longFieldArrayNull;
    }

    public Long getLongFieldWrapperNull() {
        return longFieldWrapperNull;
    }

    public Sex getSex() {
        return sex;
    }

}
