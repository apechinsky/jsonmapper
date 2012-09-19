package org.jsonmapper;

/**
 * @author Q-APE
 */
public class NestedObject {

    @JsonProperty("stringField")
    private String stringField;

    @JsonProperty("intField")
    private int intField;

    public NestedObject() {
    }

    public NestedObject(String stringField, int intField) {
        this.stringField = stringField;
        this.intField = intField;
    }

    public String getStringField() {
        return stringField;
    }

    public int getIntField() {
        return intField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NestedObject object = (NestedObject) o;

        if (intField != object.intField) {
            return false;
        }
        if (stringField != null ? !stringField.equals(object.stringField) : object.stringField != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = stringField != null ? stringField.hashCode() : 0;
        result = 31 * result + intField;
        return result;
    }
}
