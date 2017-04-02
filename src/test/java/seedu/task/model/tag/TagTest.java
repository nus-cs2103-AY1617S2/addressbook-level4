//@@author A0144813J
package seedu.task.model.tag;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;

public class TagTest {

    @Test
    public void equalsTest() {
        Tag firstTag, secondTag, thirdTag;
        try {
            firstTag = new Tag("everydayToDos");
            secondTag = new Tag("everydayToDos");
            thirdTag = new Tag("everydayToDo");
            assertTrue(firstTag.equals(secondTag));
            assertFalse(firstTag.equals(thirdTag));
        } catch (IllegalValueException e) {
        }
    }

}
