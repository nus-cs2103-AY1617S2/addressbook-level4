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

    @Test
    public void testListFinished() {
        commandBox.runCommand("ls finished");
        assertListFinished();
    }

    @Test
    public void testListPending() {
        commandBox.runCommand("list pending");
        assertListPending();
    }

    @Test
    public void testListFinishedUnderValidTagName() {
        try {
            commandBox.runCommand("list finished \\under health");
            assertListFinished();
            UniqueTagList tagList = new UniqueTagList();
            tagList.add(new Tag("health"));
            assertListUnderTags(tagList);
            assertListFinished();
        } catch (IllegalValueException exception) {
            fail();
        }
    }

    @Test
    public void testListPendingUnderValidTagName() {
        try {
            commandBox.runCommand("list pending \\under health");
            assertListFinished();
            UniqueTagList tagList = new UniqueTagList();
            tagList.add(new Tag("health"));
            assertListUnderTags(tagList);
            assertListPending();
        } catch (IllegalValueException exception) {
            fail();
        }
    }

    private void assertListUnderTags(UniqueTagList tagList) {
        List<ReadOnlyTask> displayedList = taskListPanel.getListView().getItems();
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

    private void assertListFinished() {
        List<ReadOnlyTask> displayedList = taskListPanel.getListView().getItems();
        for (ReadOnlyTask task : displayedList) {
            assertTrue(task.getFinishedStatus().getIsFinished());
        }
    }

    private void assertListPending() {
        // TODO: should add check for start time to differentiate between pending and overdue
        List<ReadOnlyTask> displayedList = taskListPanel.getListView().getItems();
        for (ReadOnlyTask task : displayedList) {
            assertTrue(!task.getFinishedStatus().getIsFinished());
        }
    }
}
