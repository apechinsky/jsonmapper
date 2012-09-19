package org.jsonmapper;

import org.jsonmapper.Path;
import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

/**
 *
 * @author Q-APE
 */
public class PathTest {

    @Test
    public void testGetFromString() throws Exception {
        Path path = Path.fromDotString("1.2.3");

        Assert.assertThat(path.getSize(), is(3));

        Assert.assertThat(path.get(0), is("1"));
        Assert.assertThat(path.get(1), is("2"));
        Assert.assertThat(path.get(2), is("3"));
    }

    @Test
    public void testGetFromStringSingleItem() throws Exception {
        Path path = Path.fromDotString("1");

        Assert.assertThat(path.getSize(), is(1));
        Assert.assertThat(path.get(0), is("1"));
    }

    @Test
    public void testGetFromStringEmpty() throws Exception {
        Path path = Path.fromDotString("");

        Assert.assertThat(path.getSize(), is(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetFromStringNullString() throws Exception {
        Path.fromDotString(null);
    }

    @Test
    public void testIsComplex() throws Exception {
        Path path;

        path = Path.fromDotString("1.2.3");
        Assert.assertThat(path.isComplex(), is(true));

        path = Path.fromDotString("1");
        Assert.assertThat(path.isComplex(), is(false));

        path = Path.fromDotString("");
        Assert.assertThat(path.isComplex(), is(false));
    }

    @Test
    public void testGetParent() throws Exception {
        Path path;

        path = Path.fromDotString("1.2.3");
        Assert.assertThat(path.getParent(), is(Path.fromDotString("1.2")));

        path = Path.fromDotString("1");
        Assert.assertThat(path.getParent(), is(Path.fromDotString("")));

        path = Path.fromDotString("");
        Assert.assertThat(path.getParent(), nullValue());
    }

    @Test
    public void testGetParentOnEmptyPath() throws Exception {
        Path path = Path.fromDotString("");
        Assert.assertThat(path.getParent(), nullValue());
    }

    @Test
    public void testGetAncestor() throws Exception {

        Path path = Path.fromDotString("1.2.3");

        Assert.assertThat(path.getAncestor(3), is(Path.fromDotString("1.2.3")));
        Assert.assertThat(path.getAncestor(2), is(Path.fromDotString("1.2")));
        Assert.assertThat(path.getAncestor(1), is(Path.fromDotString("1")));
        Assert.assertThat(path.getAncestor(0), is(Path.fromDotString("")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAncestorNegativeIndex() throws Exception {
        Path path = Path.fromDotString("1.2.3");
        path.getAncestor(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAncestorIndexGreateThanSize() throws Exception {
        Path path = Path.fromDotString("1.2.3");
        path.getAncestor(5);
    }

    @Test
    public void testGetLast() throws Exception {
        Path path;

        path = Path.fromDotString("1.2.3");
        Assert.assertThat(path.getLast(), is("3"));

        path = Path.fromDotString("1");
        Assert.assertThat(path.getLast(), is("1"));

        path = Path.fromDotString("");
        Assert.assertThat(path.getLast(), nullValue());
    }
}
