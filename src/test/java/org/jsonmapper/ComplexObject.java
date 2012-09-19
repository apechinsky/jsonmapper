package org.jsonmapper;

import org.jsonmapper.JsonProperty;

/**
 *
 * @author Q-APE
 */
public class ComplexObject {

    @JsonProperty("property1")
    public String property1;

    @JsonProperty("property2")
    public int property2;

    @JsonProperty("property3")
    public SubObject property3;

    @JsonProperty("stringArray")
    public String[] stringArray;

    @JsonProperty("objectArray")
    public SubObject[] objectArray;

    public static class SubObject {

        @JsonProperty("property1")
        public String property1;

        @JsonProperty("property2")
        public int property2;

        @JsonProperty("stringArray")
        public String[] stringArray;

    }

}
