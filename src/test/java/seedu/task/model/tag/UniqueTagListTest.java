//@@author A0144813J
package seedu.task.model.tag;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.tag.UniqueTagList.DuplicateTagException;

public class UniqueTagListTest {

    @Test
    public void equalsTest() {
        UniqueTagList firstTagList, secondTagList;
        try {
            firstTagList = new UniqueTagList("tag1", "tag2", "tag3", "tag4");
            secondTagList = new UniqueTagList("tag2", "tag1", "tag3", "tag4");
            assertTrue(firstTagList.equals(firstTagList));
            assertFalse(firstTagList.equals(secondTagList));
            assertTrue(firstTagList.equalsOrderInsensitive(secondTagList));
        } catch (DuplicateTagException e) {
        } catch (IllegalValueException e) {
        }
    }

}
