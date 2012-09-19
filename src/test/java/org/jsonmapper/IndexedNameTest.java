package org.jsonmapper;

import org.jsonmapper.IndexedName;
import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;

/**
 *
 * @author Q-APE
 */
public class IndexedNameTest {

    @Test
    public void testSimpleName() throws Exception {
        IndexedName name = IndexedName.of("name");
        Assert.assertThat(name.getName(), is("name"));
        Assert.assertThat(name.getIndex(), is(IndexedName.NO_INDEX));
        Assert.assertThat(name.isIndexed(), is(false));
    }

    @Test
    public void testIndexedName() throws Exception {
        IndexedName name = IndexedName.of("name[3]");
        Assert.assertThat(name.getName(), is("name"));
        Assert.assertThat(name.getIndex(), is(3));
        Assert.assertThat(name.isIndexed(), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoClosingBrace() throws Exception {
        IndexedName.of("name[");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoOpenBrace() throws Exception {
        IndexedName.of("name]");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoIndex() throws Exception {
        IndexedName.of("name[]");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoName() throws Exception {
        IndexedName.of("[]");
    }
}
