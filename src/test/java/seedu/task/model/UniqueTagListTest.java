package seedu.task.model;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.tag.UniqueTagList.DuplicateTagException;

public class UniqueTagListTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void initialize_DuplicateTags_fail() throws IllegalValueException {
        Tag[] tags = new Tag[] { new Tag("friends"), new Tag("work"), new Tag("personal"), new Tag("friends") };
        thrown.expect(DuplicateTagException.class);
        UniqueTagList testList = new UniqueTagList(tags);
    }

    @Test
    public void add_DuplicateTags_fail() throws IllegalValueException {
        Tag[] tags = new Tag[] { new Tag("friends"), new Tag("work"), new Tag("personal"), new Tag("shopping") };
        UniqueTagList testList = new UniqueTagList(tags);
        thrown.expect(DuplicateTagException.class);
        testList.add(new Tag("friends"));
    }

    @Test
    public void testEquals_Symmetric() throws IllegalValueException {
        Tag[] tags = new Tag[] { new Tag("friends"), new Tag("work"), new Tag("personal"), new Tag("shopping") };
        UniqueTagList testList1 = new UniqueTagList(tags);
        UniqueTagList testList2 = new UniqueTagList(tags);
        assertTrue(testList1.equals(testList2));
        assertTrue(testList1.hashCode() == testList2.hashCode());
    }

}
