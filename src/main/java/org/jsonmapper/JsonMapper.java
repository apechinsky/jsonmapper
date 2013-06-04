package org.jsonmapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.srplib.contract.Argument;
import org.srplib.conversion.Converter;
import org.srplib.conversion.ConverterRegistry;
import org.srplib.reflection.ReflectionUtils;

/**
 * A java object to JSON mapping tool.
 *
 * <p>Provides easy way to convert ordinary java objects to JSON format and vice versa.</p>
 *
 * <p>JSON properties may be customized with {@link JsonProperty} annotations.</p>
 *
 * <p>Usage example:
 * <pre>
 *  // Annotate an object
 *  // No need annotation for an object itself.
 *  class Person {
 *
 *      // Maps JSON property with name 'id'
 *      &#64;JsonProperty
 *      private int id;
 *
 *      // Maps JSON property with name 'name'
 *      &#64;JsonProperty("name")
 *      private String firstName
 *
 *      // Maps JSON nested property 'address.streetAddress'
 *      &#64;JsonProperty("address.streetAddress")
 *      private String address
 *
 *      // Maps organization from first array object 'memberships[0].organization'
 *      &#64;JsonProperty("memberships[0].organization")
 *      private String organization

 *      // Maps JSON array with name 'aliases'
 *      &#64;JsonProperty(value = "aliases", itemClass = String.class)
 *      private String[] aliases
 *
 *      // Maps nested JSON array with name 'children'
 *      &#64;JsonProperty(value = "children", itemClass = Person.class)
 *      private Person[] children
 *
 *      // Maps nested JSON array with name 'parents'. Pay attention that concrete collection class is specified.
 *      &#64;JsonProperty(value = "parents", itemClass = Person.class)
 *      private ArrayList&lt;Person&gt; parents;
 *  }
 *
 *  JsonMapper mapper = new JsonMapper();
 *  Person person = mapper.decode(jsonString);
 * </pre>
 *
 * </p>
 *
 * @author Q-APE
 */
public class JsonMapper {

    private ConverterRegistry converterRegistry;

    /**
     * Creates JSON mapper with specified registry of converters .
     *
     * @param converterRegistry ConverterRegistry a registry of {@link Converter}
     */
    public JsonMapper(ConverterRegistry converterRegistry) {
        Argument.checkNotNull(converterRegistry, "converterRegistry must not be null!");

        this.converterRegistry = converterRegistry;
    }

    /**
     * Creates JSON mapper with default registry of converters.
     */
    public JsonMapper() {
        this(new ConverterRegistry());
    }

    /**
     * Returns current registry of converters.
     *
     * @return ConverterRegistry registry of converters.
     */
    public ConverterRegistry getConverterRegistry() {
        return converterRegistry;
    }

    /**
     * Sets registry of converters for this mapper.
     *
     * @param converterRegistry ConverterRegistry registry of converters.
     */
    public void setConverterRegistry(ConverterRegistry converterRegistry) {
        this.converterRegistry = converterRegistry;
    }

    /**
     * Encodes specified object to json object.
     *
     * @param object Object to encode
     * @return JSONObject object
     * @throws org.json.JSONException if error occur during encoding
     */
    public JSONObject jsonEncode(Object object) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        throw new UnsupportedOperationException("Isn't implemented yet!");

//        Class<?> clazz = object.getClass();
//        for (Field field : getAnnotatedFields(clazz, JsonProperty.class)) {
//            JsonProperty jsonPropertyAnnotation = field.getAnnotation(JsonProperty.class);
//            String property = jsonPropertyAnnotation.value();
//            Object value;
//            if (isPrimitiveOrWrapper(field)) {
//                value = BeanHelper.getFieldValue(field, object);
//            }
//            else if (field.getType().isArray()) {
//                value = BeanHelper.getFieldValue(field, object);
//            }
//            else if (Collection.class.isAssignableFrom(field.getType())) {
//                value = BeanHelper.getFieldValue(field, object);
//            }
//            else {
//                value = jsonEncode(BeanHelper.getFieldValue(field, object));
//            }
//            jsonObject.put(property, value);
//        }
//        return jsonObject;
    }

    /**
     * Decodes specified JSON object to java object
     *
     * @param jsonObject JSONObject to decode
     * @param clazz Class a class containing field to json mapping
     * @return Object decoded object
     * @throws org.json.JSONException if error occured during decoding
     * @see JsonProperty
     */
    public <T> T jsonDecode(JSONObject jsonObject, Class<T> clazz) throws JSONException {
        T object = ReflectionUtils.newInstance(clazz);

        for (Field field : getAnnotatedFields(clazz, JsonProperty.class)) {
            JsonProperty jsonPropertyAnnotation = field.getAnnotation(JsonProperty.class);
            String jsonPropertyName = jsonPropertyAnnotation.value();

            // TODO: maybe it's better to add "Optional or Required" attribute?
//            if (!jsonObject.has(jsonPropertyName)) {
//                continue;
//            }

            Object jsonValue;
            try {
                jsonValue = JsonHelper.get(jsonObject, jsonPropertyName);
            }
            catch (JsonNotFoundException e) {
                continue;
            }

            Class itemClass = getItemClass(jsonPropertyAnnotation);
            Object propertyValue =
                decodeProperty(field.getName(), field.getType(), jsonPropertyName, jsonValue, itemClass);

            ReflectionUtils.setFieldValue(field, object, propertyValue);
        }

        return object;
    }
    /**
     * Decodes specified JSON object to java object
     *
     * @param jsonArray JSONArray JSON array to decode
     * @param itemClass Class a class of item
     * @return Object decoded object
     * @throws org.json.JSONException if error occured during decoding
     * @see JsonProperty
     */
    public <T> List<T> jsonDecode(JSONArray jsonArray, Class<T> itemClass) throws JSONException {
        List<T> result = new LinkedList<T>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            result.add(jsonDecode(jsonObject, itemClass));
        }
        return result;
    }

    private Object decodeProperty(String fieldName, Class<?> fieldType, String jsonPropertyName, Object jsonValue,
        Class<?> itemClass) throws JSONException {

        Object result;

        if (JSONObject.NULL.equals(jsonValue)) {
            result = null;
        }
        else if (jsonValue instanceof JSONObject) {
            result = jsonDecode((JSONObject) jsonValue, fieldType);
        }
        else if (jsonValue instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) jsonValue;

            // field must be a collection or array
            ensureFieldIsArrayOrCollection(fieldType, fieldName, jsonPropertyName);

            // Guessing collection/array item class
            Class effectiveItemClass = determineItemClass(itemClass, fieldType, jsonArray);

            if (effectiveItemClass == null && jsonArray.length() > 0) {
                throw new IllegalStateException(
                    String.format("Can't determine item class for collection. JSON property '%s' java property '%s'." +
                        "Check itemClass value in JsonProperty annotation.", jsonPropertyName, fieldName));
            }

            List<Object> objects = decodeArrayProperty(fieldName, jsonPropertyName, jsonArray, effectiveItemClass);

            if (fieldType.isArray()) {
                result = toArray(fieldType, objects);
            }
            else if (Collection.class.isAssignableFrom(fieldType)) {
                result = toCollection((Class<? extends Collection>) fieldType, objects);
            }
            else {
                // Target field type is checked for collection or array at the beginning
                throw new AssertionError("Object field isn't a collection or array");
            }
        }
        else {
            Converter converter = getConverter(fieldType, jsonValue.getClass());
            result = converter == null ? jsonValue : converter.convert(jsonValue);
        }
        return result;
    }


    private List<Field> getAnnotatedFields(Class<?> type, Class<? extends Annotation> annotation) {
        List<Field> annotatedFields = new LinkedList<Field>();
        for (Field field : type.getDeclaredFields()) {
            JsonProperty jsonPropertyAnnotation = field.getAnnotation(JsonProperty.class);
            if (jsonPropertyAnnotation != null) {
                annotatedFields.add(field);
            }
        }
        return annotatedFields;
    }

    /**
     * Converts list of objects to collection of specified type.
     *
     * @param fieldType Class target collection type
     * @param objects List list of objects
     * @return Collection a collection which type is fieldType
     */
    private Collection toCollection(Class<? extends Collection> fieldType, List<Object> objects) {
        Collection collection = createCollection(fieldType);
        collection.addAll(objects);
        return collection;
    }

    /**
     * Converts list of objects to array.
     *
     * <p>Unlike Collection.toArray() this method is able to work with arrays of primitives.</p>
     *
     * @param fieldType Class component type.
     * @param objects List list of objects
     * @return Object array as object
     */
    private Object toArray(Class<?> fieldType, List<Object> objects) {
        Object result = newInstance(fieldType, objects.size());
        for (int i = 0; i < objects.size(); i++) {
            Object object = objects.get(i);
            Array.set(result, i, object);
        }
        return result;
    }

    private Object newInstance(Class<?> fieldType, int length) {
        return fieldType.isArray()
            ? Array.newInstance(fieldType.getComponentType(), length)
            : ReflectionUtils.newInstance(fieldType);
    }

    private List<Object> decodeArrayProperty(String fieldName, String jsonPropertyName, JSONArray jsonArray,
        Class itemClass) throws JSONException {

        List<Object> objects = new LinkedList<Object>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Object jsonArrayItem = jsonArray.get(i);
            Object itemValue = decodeProperty(fieldName, itemClass, jsonPropertyName, jsonArrayItem, null);
            objects.add(itemValue);
        }
        return objects;
    }

    private void ensureFieldIsArrayOrCollection(Class<?> fieldType, String fieldName, String jsonPropertyName) {
        if (!fieldType.isArray() && !Collection.class.isAssignableFrom(fieldType)) {
            throw new IllegalStateException(
                String.format("JSON property '%s' is an array but mapped property '%s' " +
                    "neither array nor a collection.", jsonPropertyName, fieldName));
        }
    }

    private Class determineItemClass(Class<?> itemClass, Class<?> fieldType, JSONArray jsonArray) throws JSONException {
        // Item class is specified explicitly by JsonProperty annotation
        Class effectiveItemClass = itemClass;

        // Try to determine item class from field type. This is only possible if type is an array.
        if (effectiveItemClass == null) {
            effectiveItemClass = getItemTypeFromField(fieldType);
        }

        // The last chance to determine item class. If json array isn't empty and contains non JSONObject values
        // we can assume that values are primitives, primitive wrappers or Strings.
        if (effectiveItemClass == null) {
            Class<?> arrayItemClass = getItemTypeFromJsonArray(jsonArray);
            if (arrayItemClass != null && !JSONObject.class.isAssignableFrom(arrayItemClass)) {
                effectiveItemClass = arrayItemClass;
            }
        }
        return effectiveItemClass;
    }

    /**
     * Returns item class of specified type. This method can determine item class if type is an array.
     *
     * @param type Class a type to check
     * @return Class item class or <code>null</code> if null can't be determined.
     */
    private Class<?> getItemTypeFromField(Class<?> type) {
        return type.isArray() ? type.getComponentType() : null;
    }

    /**
     * Determines type of items of JSONArray (if possible)
     *
     * @param jsonArray JSONArray json array to test
     * @return Class type of items or <code>null</code> if type can't be determined.
     * @throws JSONException in case of JSON encoding errors
     */
    private Class<?> getItemTypeFromJsonArray(JSONArray jsonArray) throws JSONException {
        Class<?> result = null;
        if (jsonArray.length() > 0) {
            Object itemValue = jsonArray.get(0);
            result = itemValue.getClass();
        }
        return result;
    }

    private Class getItemClass(JsonProperty annotation) {
        // JsonProperty class acts as an indicator that itemClass value isn't specified.
        return annotation.itemClass() == JsonProperty.class ? null : annotation.itemClass();
    }

    private Converter<?, ?> getConverter(Class<?> source, Class<?> target) {
        return converterRegistry.getConverter(target, source);
    }

    private static <T extends Collection> T createCollection(Class<T> type) {
        return type.isInterface() ? type.cast(new ArrayList()) : ReflectionUtils.newInstance(type);
    }

}
