package seedu.toluist.commons.util;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CollectionUtilTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isAnyNullVarargs() {
        // no arguments
        assertFalse(CollectionUtil.isAnyNull());

        // any non-empty argument list
        assertFalse(CollectionUtil.isAnyNull(new Object(), new Object()));
        assertFalse(CollectionUtil.isAnyNull("test"));
        assertFalse(CollectionUtil.isAnyNull(""));

        // argument lists with just one null at the beginning
        assertTrue(CollectionUtil.isAnyNull((Object) null));
        assertTrue(CollectionUtil.isAnyNull(null, StringUtil.EMPTY_STRING, new Object()));
        assertTrue(CollectionUtil.isAnyNull(null, new Object(), new Object()));

        // argument lists with nulls in the middle
        assertTrue(CollectionUtil.isAnyNull(new Object(), null, null, "test"));
        assertTrue(CollectionUtil.isAnyNull(StringUtil.EMPTY_STRING, null, new Object()));

        // argument lists with one null as the last argument
        assertTrue(CollectionUtil.isAnyNull(StringUtil.EMPTY_STRING, new Object(), null));
        assertTrue(CollectionUtil.isAnyNull(new Object(), new Object(), null));

        // confirms nulls inside lists in the argument list are not considered
        List<Object> containingNull = Arrays.asList((Object) null);
        assertFalse(CollectionUtil.isAnyNull(containingNull, new Object()));
    }

    @Test
    public void isAnyNullVarargs_nullReference_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        CollectionUtil.isAnyNull((Object[]) null);
    }

    @Test
    public void isAnyNullCollection() {
        // lists containing nulls in the front
        assertTrue(CollectionUtil.isAnyNull(Arrays.asList((Object) null)));
        assertTrue(CollectionUtil.isAnyNull(Arrays.asList(null, new Object(), StringUtil.EMPTY_STRING)));

        // lists containing nulls in the middle
        assertTrue(CollectionUtil.isAnyNull(Arrays.asList("spam", null, new Object())));
        assertTrue(CollectionUtil.isAnyNull(Arrays.asList("spam", null, "eggs", null, new Object())));

        // lists containing nulls at the end
        assertTrue(CollectionUtil.isAnyNull(Arrays.asList("spam", new Object(), null)));
        assertTrue(CollectionUtil.isAnyNull(Arrays.asList(new Object(), null)));

        // empty list
        assertFalse(CollectionUtil.isAnyNull(Collections.emptyList()));

        // list with all non-null elements
        assertFalse(CollectionUtil.isAnyNull(Arrays.asList(new Object(), "ham", new Integer(1))));
        assertFalse(CollectionUtil.isAnyNull(Arrays.asList(new Object())));

        // confirms nulls inside nested lists are not considered
        List<Object> containingNull = Arrays.asList((Object) null);
        assertFalse(CollectionUtil.isAnyNull(Arrays.asList(containingNull, new Object())));
    }

    @Test
    public void isAnyNullCollection_nullReference_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        CollectionUtil.isAnyNull((Collection<Object>) null);
    }

    //@@author A0131125Y
    @Test
    public void isAnyPresent() throws Exception {
        // no arguments
        assertFalse(CollectionUtil.isAnyPresent());

        // any non-presentt argument list
        assertFalse(CollectionUtil.isAnyPresent(Optional.empty(), Optional.empty()));
        assertFalse(CollectionUtil.isAnyPresent(Optional.empty()));

        // argument lists with just one present at the beginning
        assertTrue(CollectionUtil.isAnyPresent(Optional.of(1)));
        assertTrue(CollectionUtil.isAnyPresent(Optional.of(false), Optional.empty(), Optional.empty()));

        // argument lists with present optional in the middle
        assertTrue(CollectionUtil.isAnyPresent(Optional.empty(), Optional.of(StringUtil.EMPTY_STRING),
                Optional.empty()));

        // argument lists with one present as the last argument
        assertTrue(CollectionUtil.isAnyPresent(Optional.empty(), Optional.empty(), Optional.of(true)));
    }

    //@@author
    @Test
    public void elementsAreUnique() throws Exception {
        // empty list
        assertAreUnique();

        // only one object
        assertAreUnique((Object) null);
        assertAreUnique(1);
        assertAreUnique(StringUtil.EMPTY_STRING);
        assertAreUnique("abc");

        // all objects unique
        assertAreUnique("abc", "ab", "a");
        assertAreUnique(1, 2);

        // some identical objects
        assertNotUnique("abc", "abc");
        assertNotUnique("abc", StringUtil.EMPTY_STRING, "abc", "ABC");
        assertNotUnique(StringUtil.EMPTY_STRING, "abc", "a", "abc");
        assertNotUnique(1, new Integer(1));
        assertNotUnique(null, 1, new Integer(1));
        assertNotUnique(null, null);
        assertNotUnique(null, "a", "b", null);
    }

    //@@author A0131125Y
    @Test
    public void elementsAreSimilar() {
        // empty collections
        assertTrue(CollectionUtil.elementsAreSimilar(new HashSet<>(), new HashSet<>()));

        // collections have the same elements in same order
        assertTrue(CollectionUtil.elementsAreSimilar(Arrays.asList(true, false), Arrays.asList(true, false)));

        // collections have the same elements in different order
        assertTrue(CollectionUtil.elementsAreSimilar(Arrays.asList(1, 2), Arrays.asList(2, 1)));

        // one collection has more different element
        assertFalse(CollectionUtil.elementsAreSimilar(Arrays.asList(1, 2, 3), Arrays.asList(2, 1)));
        assertFalse(CollectionUtil.elementsAreSimilar(Arrays.asList("a", "b"), Arrays.asList("a", "b", "c")));

        // one collection has more similar element
        assertFalse(CollectionUtil.elementsAreSimilar(Arrays.asList(1, 2, 1), Arrays.asList(2, 1)));
        assertFalse(CollectionUtil.elementsAreSimilar(Arrays.asList("a", "b"), Arrays.asList("a", "b", "a")));
    }

    @Test
    public void getStringRepresentation() {
        // empty collections
        assertEquals(CollectionUtil.getStringRepresentation(StringUtil.EMPTY_STRING, new HashSet<>()), "");

        // collections with items
        assertEquals(CollectionUtil.getStringRepresentation(", ", Arrays.asList(1, 2)), "1, 2");
        assertEquals(CollectionUtil.getStringRepresentation("-", Arrays.asList("a", "b")), "a-b");
    }

    //@@author
    private void assertAreUnique(Object... objects) {
        assertTrue(CollectionUtil.elementsAreUnique(Arrays.asList(objects)));
    }

    private void assertNotUnique(Object... objects) {
        assertFalse(CollectionUtil.elementsAreUnique(Arrays.asList(objects)));
    }
}
