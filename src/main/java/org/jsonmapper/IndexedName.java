package org.jsonmapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.srplib.contract.Argument;

/**
 * Object model for indexed name such as property[1], myArray[6] etc.
 *
 * <p>Can handle non-indexed names as well as.</p>
 *
 * @author Q-APE
 */
public class IndexedName {

    private static final Pattern PATTERN = Pattern.compile("(\\w+)(\\[(\\d+)\\])?");

    public static final int NO_INDEX = -1;


    private final String name;

    private final int index;

    /**
     * Creates indexed name from string representation.
     *
     * @param indexedName String simple or indexed name
     * @return IndexedName indexed name object
     * @throws IllegalArgumentException if property is not valid indexed property
     */
    public static IndexedName of(String indexedName) {
        Argument.checkNotNull(indexedName, "name must not be null!");

        Matcher matcher = PATTERN.matcher(indexedName);

        Argument.checkTrue(matcher.matches(), "Invalid name syntax '%s'", indexedName);

        String name = matcher.group(1);
        String indexString = matcher.group(3);
        int index = indexString == null ? NO_INDEX : Integer.parseInt(indexString);

        return new IndexedName(name, index);
    }

    /**
     * Creates indexed property.
     *
     * @param name String name
     * @param index int index
     */
    public IndexedName(String name, int index) {
        Argument.checkNotNull(name, "name must not be null!");
        Argument.checkTrue(index >= -1, "index shold be > 0 or IndexedName.NO_INDEX!");

        this.name = name;
        this.index = index;
    }

    /**
     * Tests if name is indexed.
     *
     * @return true if name is indexed
     */
    public boolean isIndexed() {
        return index != NO_INDEX;
    }

    /**
     * Returns name without index.
     *
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns index.
     *
     * @return int index, or -1 if name is not indexed
     */
    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return isIndexed() ? String.format("%s[%d]", name, index) : name;
    }
}
