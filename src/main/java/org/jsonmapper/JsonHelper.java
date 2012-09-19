package org.jsonmapper;

import java.lang.reflect.Array;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.srplib.contract.Argument;
import org.srplib.contract.Assert;

/**
 * A helper class containing static utility method for {@link JsonMapper} and {@link JSONObject}.
 *
 * <ul>Key methods for {@link JSONObject}:
 *  <li>{@link #get(JSONObject, String)} - get property value (with dot syntax support)</li>
 *
 * </ul>
 *
 * @author Q-APE
 * @see JsonMapper
 */
public class JsonHelper {

    /**
     * Encodes specified object to json object.
     *
     * @param object Object to encode
     * @return JSONObject object
     * @throws org.json.JSONException if error occur during encoding
     */
    public static JSONObject jsonEncode(Object object) throws JSONException {
        JsonMapper jsonMapper = new JsonMapper();
        return jsonMapper.jsonEncode(object);
    }

    /**
     * Encodes specified object to json object.
     *
     * @param jsonObject JSONObject to decode
     * @param clazz Class a class containing field to json mapping
     * @return Object decoded object
     * @throws org.json.JSONException if error occured during decoding
     * @see JsonProperty
     */
    public static <T> T jsonDecode(JSONObject jsonObject, Class<T> clazz) throws JSONException {
        JsonMapper jsonMapper = new JsonMapper();
        return jsonMapper.jsonDecode(jsonObject, clazz);
    }

    /**
     * Returns value of specified property.
     *
     * <p>Unlike standard {@link JSONObject#get} method this method supports dot syntax.</p>
     *
     * @param jsonObject JSONObject json object
     * @param property String property name. Supports dot syntax. Supports indexed properties.
     * @param <T> return value type
     * @return Object return value
     * @throws JSONException if json exception is occured
     * @throws IllegalArgumentException if intermediate object is not JSONObject.
     */
    public static <T> T get(JSONObject jsonObject, String property) throws JSONException {
        Assert.checkNotNull(property, "Property name must not be null!");
        try {
            Object object = jsonObject;

            Path path = Path.fromDotString(property);

            for (int i = 0; i < path.getSize() - 1; i++) {
                String pathItem = path.get(i);

                object = getIndexed((JSONObject) object, pathItem);

                Argument.checkFalse(object instanceof JSONArray,
                    "JSON array is returned but not expected in property: '%s'", pathItem);
            }
            return (T)getIndexed((JSONObject) object, path.getLast());
        }
        catch (JsonNotFoundException e) {
            throw new JsonNotFoundException(String.format("Can't get value of property '%s'. ", property), e);
        }
        catch (ClassCastException e) {
            throw new IllegalArgumentException("Can't get value of property '" + property + "'.", e);
        }
    }


    private static boolean hasIndexed(JSONObject jsonObject, String property) throws JSONException {
        IndexedName indexedName = IndexedName.of(property);

        if (!jsonObject.has(indexedName.getName())) {
            return false;
        }

        boolean result = true;

        if (indexedName.isIndexed()) {
            Object value = jsonObject.get(indexedName.getName());
            Assert.checkTrue(value instanceof JSONArray, "JSONArray is expected '%'", indexedName);

            result = indexedName.getIndex() < ((JSONArray) value).length();
        }

        return result;
    }

    private static Object getIndexed(JSONObject jsonObject, String property) throws JSONException {
        IndexedName indexedName = IndexedName.of(property);

        if (!jsonObject.has(indexedName.getName())) {
            throw new JsonNotFoundException(String.format("Property '%s' not found.", indexedName));
        }

        Object value = jsonObject.get(indexedName.getName());

        if (indexedName.isIndexed()) {
            Assert.checkTrue(value instanceof JSONArray, "JSONArray is expected '%'", indexedName);
            value = ((JSONArray) value).get(indexedName.getIndex());
        }
        return value;
    }

    /**
     * Converts {@link JSONArray} to an array of specified class.
     *
     * @param jsonArray JSONArray json array
     * @param clazz Class array item class
     * @param <T> item type
     * @return an array of objects
     * @throws JSONException from underlying JSON library
     */
    public static <T> T[] toObjectArray(JSONArray jsonArray, Class<T> clazz) throws JSONException {
        T[] array = (T[]) Array.newInstance(clazz, jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            array[i] = (T) jsonArray.get(i);
        }
        return array;
    }

}
