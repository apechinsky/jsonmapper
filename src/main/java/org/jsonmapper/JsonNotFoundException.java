package org.jsonmapper;

import org.json.JSONException;

/**
 * This exception is thrown when JSON property is not found.
 *
 * @author Q-APE
 */
public class JsonNotFoundException extends JSONException {

    private Throwable throwable;

    public JsonNotFoundException(String message) {
        super(message);
    }

    public JsonNotFoundException(String message, Throwable throwable) {
        super(message);
        this.throwable = throwable;
    }

    @Override
    public Throwable getCause() {
        return throwable;
    }
}
