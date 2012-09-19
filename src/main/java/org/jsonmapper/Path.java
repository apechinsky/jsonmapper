package org.jsonmapper;

import java.util.Arrays;

import org.srplib.contract.Argument;
import org.srplib.contract.Utils;

/**
 * Represents path-like structure.
 *
 * @author Q-APE
 */
public class Path {

    private static final String DEFAULT_SEPARATOR = ".";

    private String[] path;

    /**
     * Creates path from specified dotted string.
     *
     * @param complexProperty String dot syntax property.
     * @return Path path instance
     */
    public static Path fromDotString(String complexProperty) {
        Argument.checkNotNull(complexProperty, "complexProperty");

        String[] path = Utils.isBlank(complexProperty)
            ? new String[0]
            : complexProperty.split("\\.");

        return new Path(path);
    }

    /**
     * Creates path from specified path elements.
     *
     * @param path String[] массив свойств
     */
    public Path(String[] path) {
        Argument.checkNotNull(path, "path");

        this.path = path;
    }

    /**
     * Returns path size.
     *
     * @return path item count.
     */
    public int getSize() {
        return path.length;
    }

    /**
     * Tests if path is complex (contains more than 1 items).
     *
     * @return true if path size is greate than 1
     */
    public boolean isComplex() {
        return getSize() > 1;
    }

    /**
     * Returns parent path.
     *
     * <pre>
     *     Path.fromDotString("parent.parent.name").getParent() = "parent.parent"
     * </pre>
     *
     * @return Path parent path or {@code null} if a path size is 0.
     */
    public Path getParent() {
        if (getSize() < 1) {
            return null;
        }
        return getAncestor(path.length - 1);
    }

    /**
     * Returns path of specified ancestor.
     *
     * @param index last item index (exclusive)
     * @return Path parent path.
     * @throws IllegalArgumentException if index is negative or index is greater than path size
     */
    public Path getAncestor(int index) {
        assertBounds(index);

        String[] newPath = subarray(path, 0, index);
        return new Path(newPath);
    }

    private void assertBounds(int index) {
        Argument.checkTrue(index >= 0, "ancestor index must be >= 0");
        Argument.checkTrue(index <= getSize(), "ancestor index must be <= than path size.");
    }

    /**
     * Returns last path item.
     *
     * @return String last path item or {@code null} if path is empty.
     */
    public String getLast() {
        if (getSize() < 1) {
            return null;
        }
        return path[path.length - 1];
    }

    /**
     * Returns path item with specified index.
     *
     * @param index item idex (zero based)
     * @return String path item
     * @throws IllegalArgumentException if index is negative or index is greater than path size
     */
    public String get(int index) {
        assertBounds(index);
        return path[index];
    }

    public String toString() {
        return join(path, 0, getSize(), DEFAULT_SEPARATOR);
    }

    private static String[] subarray(String[] source, int startIndex, int length) {
        String[] newArray = new String[length];
        System.arraycopy(source, startIndex, newArray, 0, length);
        return newArray;
    }

    private String join(String[] path, int startIndex, int endIndex, String separator) {
        StringBuffer sb = new StringBuffer();
        for (int i = startIndex; i < endIndex; i++) {
            sb.append(path[i]);
            if (i < endIndex - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Path path1 = (Path) o;

        return Arrays.equals(path, path1.path);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(path);
    }
}
