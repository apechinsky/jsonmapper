package org.jsonmapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation for json property annotation. Is used to map java property to json property.
 *
 * @author Q-APE
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonProperty {

    String FIELD_NAME = "FIELD_NAME";

    /**
     * JSON property name.
     *
     * <p>If value is not specified than field name will be used.</p>
     *
     * @return String representing JSON property name.
     */
    String value() default FIELD_NAME;

    /**
     * Specifies item class of collections or array types.
     *
     * @return Class item type
     */
    Class<?> itemClass() default JsonProperty.class;

}
