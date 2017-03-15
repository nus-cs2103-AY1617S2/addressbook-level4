package guitests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import seedu.doist.commons.exceptions.IllegalValueException;
import seedu.doist.logic.commands.ListCommand;
import seedu.doist.model.tag.Tag;
import seedu.doist.model.tag.UniqueTagList;
import seedu.doist.model.task.ReadOnlyTask;

public class ListCommandTest extends DoistGUITest {

    @Test
    public void testListUnderValidTagName() {
        try {
            commandBox.runCommand("list \\under friends");
            UniqueTagList tagList = new UniqueTagList();
            tagList.add(new Tag("friends"));
            assertListUnderTags(tagList);
        } catch (IllegalValueException exception) {
            fail();
        }
    }

    @Test
    public void testListUnderInvalidTagName() {
        commandBox.runCommand("list \\under !@#$%^");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    private void assertListUnderTags(UniqueTagList tagList) {
        List<ReadOnlyTask> displayedList = personListPanel.getListView().getItems();
        for (ReadOnlyTask task : displayedList) {
            boolean doesContainAny = false;
            for (Tag tag : tagList) {
                if (task.getTags().contains(tag)) {
                    doesContainAny = true;
                }
            }
            assertTrue(doesContainAny);
        }
        assertResultMessage(ListCommand.getSuccessMessageListUnder(tagList));
    }
}
