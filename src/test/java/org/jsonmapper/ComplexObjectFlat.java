package org.jsonmapper;

import org.jsonmapper.JsonProperty;

/**
 *
 * @author Q-APE
 */
public class ComplexObjectFlat {

    @JsonProperty("property1")
    public String property1;

    @JsonProperty("property2")
    public int property2;

    @JsonProperty("stringArray")
    public String[] stringArray;


    @JsonProperty("property3.property1")
    public String property3property1;

    @JsonProperty("property3.property2")
    public int property3property2;

    @JsonProperty("property3.stringArray")
    public String[] property3stringArray;


    @JsonProperty("objectArray[0].property1")
    public String objectArray0property1;

    @JsonProperty("objectArray[0].property2")
    public int objectArray0property2;

    @JsonProperty("objectArray[0].stringArray")
    public String[] objectArray0stringArray;


    @JsonProperty("objectArray[1].property1")
    public String objectArray1property1;

    @JsonProperty("objectArray[1].property2")
    public int objectArray1property2;

    @JsonProperty("objectArray[1].stringArray")
    public String[] objectArray1stringArray;

}
