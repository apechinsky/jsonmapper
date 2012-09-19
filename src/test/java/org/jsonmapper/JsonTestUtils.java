package org.jsonmapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Q-APE
 */
public class JsonTestUtils {

    public static JSONObject loadJsonObject(URL resource) throws IOException, JSONException {
        InputStream inputStream = null;
        try {
            inputStream = resource.openStream();
            String json = IOUtils.toString(inputStream);
            return new JSONObject(json);
        }
        finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

}
