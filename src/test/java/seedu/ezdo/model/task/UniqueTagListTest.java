package seedu.ezdo.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.ezdo.model.tag.Tag;
import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.tag.UniqueTagList.DuplicateTagException;

public class UniqueTagListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void add_duplicate_throwsDuplicateTagException() throws Exception {
        thrown.expect(DuplicateTagException.class);
        UniqueTagList spy = spy(UniqueTagList.class);
        Tag tag = new Tag("lol");
        when(spy.contains(tag)).thenReturn(true);
        spy.add(tag);
    }

    @Test
    public void equals_same_true() {
        UniqueTagList utl = new UniqueTagList();
        assertTrue(utl.equals(utl));
    }

    @Test
    public void equals_notUtl_false() {
        UniqueTagList utl = new UniqueTagList();
        assertFalse(utl.equals(new Integer(1)));
    }
}
