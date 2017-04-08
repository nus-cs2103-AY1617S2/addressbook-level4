package guitests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import seedu.doist.commons.core.Messages;
import seedu.doist.commons.exceptions.IllegalValueException;
import seedu.doist.logic.commands.ListCommand;
import seedu.doist.model.tag.Tag;
import seedu.doist.model.tag.UniqueTagList;
import seedu.doist.model.task.ReadOnlyTask;
import seedu.doist.testutil.TestTask;

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
    public void testListOverdue() {
        commandBox.runCommand("list overdue");
        assertListOverdue();
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
            assertResultMessage(ListCommand.getSuccessMessageListUnder(ListCommand.MESSAGE_PENDING, tagList));
        } catch (IllegalValueException exception) {
            fail();
        }
    }

    //@@author A0147620L
    @Test
    public void testListFromToValidDates() {
        //Add a new event, with the dates
        TestTask taskToAdd = td.movie;
        commandBox.runCommand(taskToAdd.getAddCommand());

        //List in an interval that CONTAINS this task"
        commandBox.runCommand("list \\from dec 1st 2016 \\to feb 2017");
        String dateStringFrom = "Dec 1 00:00:00 2016";
        String dateStringTo = "Feb 1 00:00:00 2017";
        // The default medium/short DateFormat
        DateFormat format = new SimpleDateFormat("MMM dd kk:mm:ss yyyy");
        try {
            //Check if all tasks listed are in this interval
            assertListDate(stringToDate(format, dateStringFrom), stringToDate(format, dateStringTo));
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    @Test
    public void testListFromToInValidDates() {
        //List in an interval that is wrong
        commandBox.runCommand("list \\from feb 2017 \\to dec 1st 2016");

        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void testListBeforeDates() {
        //Add a new event, with the dates
        TestTask taskToAdd = td.movie;
        commandBox.runCommand(taskToAdd.getAddCommand());

        //List in an interval that is completely before this task"
        commandBox.runCommand("list \\from Oct 1st 2016 \\to Dec 2016");
        String dateStringFrom = "Oct 1 00:00:00 2016";
        String dateStringTo = "Dec 1 00:00:00 2016";
        // The default medium/short DateFormat
        DateFormat format = new SimpleDateFormat("MMM dd kk:mm:ss yyyy");
        try {
            //Check if all tasks listed are in this interval
            assertListDate(stringToDate(format, dateStringFrom), stringToDate(format, dateStringTo));
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    @Test
    public void testListAfterDates() {
        //Add a new event, with the dates
        TestTask taskToAdd = td.movie;
        commandBox.runCommand(taskToAdd.getAddCommand());

        //List in an interval that is completely after this task"
        commandBox.runCommand("list \\from Oct 1st 2017 \\to Dec 2017");
        String dateStringFrom = "Oct 1 00:00:00 2017";
        String dateStringTo = "Dec 1 00:00:00 2017";
        // The default medium/short DateFormat
        DateFormat format = new SimpleDateFormat("MMM dd kk:mm:ss yyyy");
        try {
            //Check if all tasks listed are in this interval
            assertListDate(stringToDate(format, dateStringFrom), stringToDate(format, dateStringTo));
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }
    //@@author

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
    }

    private void assertListFinished() {
        List<ReadOnlyTask> displayedList = taskListPanel.getListView().getItems();
        for (ReadOnlyTask task : displayedList) {
            assertTrue(task.getFinishedStatus().getIsFinished());
        }
    }

    private void assertListPending() {
        List<ReadOnlyTask> displayedList = taskListPanel.getListView().getItems();
        for (ReadOnlyTask task : displayedList) {
            assertTrue(!task.getFinishedStatus().getIsFinished() && !task.getDates().isPast());
        }
    }

    private void assertListOverdue() {
        List<ReadOnlyTask> displayedList = taskListPanel.getListView().getItems();
        for (ReadOnlyTask task : displayedList) {
            assertTrue(!task.getFinishedStatus().getIsFinished() && task.getDates().isPast());
        }
    }

    private void assertListDate(Date startDate, Date endDate) {
        List<ReadOnlyTask> displayedList = taskListPanel.getListView().getItems();
        for (ReadOnlyTask task : displayedList) {
            assertValidDate(startDate, endDate, task.getDates().getStartDate(), task.getDates().getEndDate());
        }
    }

    /** Function that checks if start and end date, overlap with the targetStart and targetEnd dates **/
    private void assertValidDate (Date targetStart, Date targetEnd, Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            assertTrue(false);
        } else if (startDate.compareTo(targetStart) < 0 &&
                endDate.compareTo(targetEnd) < 0) {
            assertTrue(false);
        } else if (startDate.compareTo(targetStart) > 0 &&
                endDate.compareTo(targetEnd) > 0) {
            assertTrue(false);
        } else {
            assertTrue(true);
        }
    }

    public Date stringToDate(DateFormat format, String dateString) throws IllegalValueException {
        try {
            Date date = format.parse(dateString);
            return date;
        } catch (ParseException pe) {
            throw new IllegalValueException(dateString + " is not a valid date");
        }
    }
}
