package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.DeadlineTaskCardHandle;
import guitests.guihandles.EventTaskCardHandle;
import guitests.guihandles.FloatingTaskCardHandle;
import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.logic.commands.AddCommand;
import seedu.taskmanager.logic.parser.AddCommandParser;
import seedu.taskmanager.testutil.TestTask;
import seedu.taskmanager.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {

    // @@author A0141102H
    @Test
    public void add() {
        // add one floating task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.sampleEvent;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();

        // add another task (deadline)
        taskToAdd = td.sampleDeadline;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();

        // add duplicate task
        commandBox.runCommand(td.sampleDeadline.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(eventTaskListPanel.isListMatching(currentList));
        assertTrue(deadlineTaskListPanel.isListMatching(currentList));
        assertTrue(floatingTaskListPanel.isListMatching(currentList));

        // add to empty list
        commandBox.runCommand("CLEAR");
        assertAddSuccess(td.eatBreakfast);

        // invalid command
        commandBox.runCommand("ADDS Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    // @@author A0139520L
    @Test
    public void addEventWithClashingTimeSlotsSuccess() {

        String clashFeedback = "Clash with task: Index ";
        setTimeClashingTestEnvironment();

        // add clashing event of currently existing one day event
        TestTask[] currentList = td.getTypicalTestEventsForBlockingTimeSlots();
        TestTask taskToAdd = td.sampleClashBetweenOneDayEvent;
        assertAddOneDayEventSuccess(taskToAdd, currentList);
        assertResultMessage(clashFeedback + "3\n" + String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd) + "\n"
                + "Task added at index: 4");

        commandBox.runCommand("UNDO");
        currentList = TestUtil.removeTasksFromList(currentList, taskToAdd);

        // add clashing event between existing multiple day event
        taskToAdd = td.sampleClashBetweenMultipleDaysEvent;
        assertAddOneDayEventSuccess(taskToAdd, currentList);
        assertResultMessage(clashFeedback + "2\n" + String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd) + "\n"
                + "Task added at index: 3");

        commandBox.runCommand("UNDO");

        // add clashing event spanning across existing multiple day event
        taskToAdd = td.sampleClashAcrossMultipleDaysEvent;
        assertAddSuccess(taskToAdd, currentList);
        assertResultMessage(clashFeedback + "3\n" + String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd) + "\n"
                + "Task added at index: 2");

        commandBox.runCommand("UNDO");
        // add clashing event at start of existing multiple day event
        taskToAdd = td.sampleClashStartOfMultipleDaysEvent;
        assertAddOneDayEventSuccess(taskToAdd, currentList);
        assertResultMessage(clashFeedback + "3\n" + String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd) + "\n"
                + "Task added at index: 2");

        commandBox.runCommand("UNDO");

        // add clashing event at end of existing multiple day event
        taskToAdd = td.sampleClashEndOfMultipleDaysEvent;
        assertAddOneDayEventSuccess(taskToAdd, currentList);
        assertResultMessage(clashFeedback + "2\n" + String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd) + "\n"
                + "Task added at index: 3");

        commandBox.runCommand("UNDO");

        // add non-clashing event on same day as existing event
        taskToAdd = td.sampleNoClashSameDayEvent;
        assertAddSuccess(taskToAdd, currentList);
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd) + "\n" + "Task added at index: 4");

        commandBox.runCommand("UNDO");
        // add non-clashing event on separate day as existing event
        taskToAdd = td.sampleNoClashSeparateDayEvent;
        assertAddOneDayEventWithBufferSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd) + "\n" + "Task added at index: 5");

    }

    private void setTimeClashingTestEnvironment() {
        commandBox.runCommand("CLEAR");
        commandBox.runCommand(td.eventTestMon.getOneDayEventAddCommand());
        commandBox.runCommand(td.eventTestTuesThurs.getAddCommand());
        commandBox.runCommand(td.eventTestThurs.getOneDayEventAddCommand());
        commandBox.runCommand(td.eventTestFriSat.getAddCommand());
    }

    // @@author A0139520L
    @Test
    public void addFromToEventWithoutTime() {
        // add one floating task
        commandBox.runCommand("ADD Hell week FROM 08/04/17 TO 11/04/17 CATEGORY rekt");
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.fromToEventWithoutTime;
        assertAddCommand(taskToAdd, currentList);
    }

    // @@author A0139520L
    @Test
    public void addOnToEventWithTime() {
        // add one floating task
        commandBox.runCommand("ADD Hell week ON 08/04/17 TO 1200 CATEGORY rekt");
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.onToEventWithEndTime;
        assertAddCommand(taskToAdd, currentList);
    }

    @Test
    public void addInvalidEvents() {
        commandBox.runCommand("ADD event ON 08/02/17 1200 TO 1000");
        assertResultMessage("Invalid input of time, start time has to be earlier than end time");

        commandBox.runCommand("ADD event ON 08/02/17 1200 TO 08/08/17 1000");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        commandBox.runCommand("ADD DedLine BY 08/04/17 1200 thursday");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        commandBox.runCommand("ADD deadline BY 08/04/17 2500");
        assertResultMessage(AddCommandParser.INVALID_TIME);
    }

    @Test
    public void byDeadlineTask() {

        // adding deadline without endtime
        commandBox.runCommand("ADD Deadline BY 08/04/17");
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.byDeadlineWithoutTime;
        assertAddCommand(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();

        // adding deadline with endtime
        commandBox.runCommand("ADD DedLine BY 08/04/17 1200");
        taskToAdd = td.byDeadlineWithTime;
        assertAddCommand(taskToAdd, currentList);

    }

    // @@author A0142418L
    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        // confirm the new card contains the right data
        if (taskToAdd.isEventTask()) {
            EventTaskCardHandle addedCard = eventTaskListPanel.navigateToEventTask(taskToAdd.getTaskName().toString());
            assertMatching(taskToAdd, addedCard);
        } else {
            if (taskToAdd.isDeadlineTask()) {
                DeadlineTaskCardHandle addedCard = deadlineTaskListPanel
                        .navigateToDeadlineTask(taskToAdd.getTaskName().toString());
                assertMatching(taskToAdd, addedCard);
            } else {
                if (taskToAdd.isFloatingTask()) {
                    FloatingTaskCardHandle addedCard = floatingTaskListPanel
                            .navigateToFloatingTask(taskToAdd.getTaskName().toString());
                    assertMatching(taskToAdd, addedCard);
                }
            }
        }

        // confirm the list now contains all previous tasks plus the new
        // task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();
        assertTrue(eventTaskListPanel.isListMatching(expectedList));
        assertTrue(deadlineTaskListPanel.isListMatching(expectedList));
        assertTrue(floatingTaskListPanel.isListMatching(expectedList));
    }

    // @@author A0139520L
    private void assertAddOneDayEventSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getOneDayEventAddCommand());

        // confirm the new card contains the right data
        if (taskToAdd.isEventTask()) {
            EventTaskCardHandle addedCard = eventTaskListPanel.navigateToEventTask(taskToAdd.getTaskName().toString());
            assertMatching(taskToAdd, addedCard);
        } else {
            if (taskToAdd.isDeadlineTask()) {
                DeadlineTaskCardHandle addedCard = deadlineTaskListPanel
                        .navigateToDeadlineTask(taskToAdd.getTaskName().toString());
                assertMatching(taskToAdd, addedCard);
            } else {
                if (taskToAdd.isFloatingTask()) {
                    FloatingTaskCardHandle addedCard = floatingTaskListPanel
                            .navigateToFloatingTask(taskToAdd.getTaskName().toString());
                    assertMatching(taskToAdd, addedCard);
                }
            }
        }

        // confirm the list now contains all previous tasks plus the new
        // task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();
        assertTrue(eventTaskListPanel.isListMatching(expectedList));
        assertTrue(deadlineTaskListPanel.isListMatching(expectedList));
        assertTrue(floatingTaskListPanel.isListMatching(expectedList));
    }

    private void assertAddOneDayEventWithBufferSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getOneDayEventWithBufferAddCommand());

        // confirm the new card contains the right data
        if (taskToAdd.isEventTask()) {
            EventTaskCardHandle addedCard = eventTaskListPanel.navigateToEventTask(taskToAdd.getTaskName().toString());
            assertMatching(taskToAdd, addedCard);
        } else {
            if (taskToAdd.isDeadlineTask()) {
                DeadlineTaskCardHandle addedCard = deadlineTaskListPanel
                        .navigateToDeadlineTask(taskToAdd.getTaskName().toString());
                assertMatching(taskToAdd, addedCard);
            } else {
                if (taskToAdd.isFloatingTask()) {
                    FloatingTaskCardHandle addedCard = floatingTaskListPanel
                            .navigateToFloatingTask(taskToAdd.getTaskName().toString());
                    assertMatching(taskToAdd, addedCard);
                }
            }
        }

        // confirm the list now contains all previous tasks plus the new
        // task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();
        assertTrue(eventTaskListPanel.isListMatching(expectedList));
        assertTrue(deadlineTaskListPanel.isListMatching(expectedList));
        assertTrue(floatingTaskListPanel.isListMatching(expectedList));
    }

    private void assertAddCommand(TestTask taskToAdd, TestTask... currentList) {

        if (taskToAdd.isEventTask()) {
            EventTaskCardHandle addedCard = eventTaskListPanel.navigateToEventTask(taskToAdd.getTaskName().toString());
            assertMatching(taskToAdd, addedCard);
        } else {
            if (taskToAdd.isDeadlineTask()) {
                DeadlineTaskCardHandle addedCard = deadlineTaskListPanel
                        .navigateToDeadlineTask(taskToAdd.getTaskName().toString());
                assertMatching(taskToAdd, addedCard);
            } else {
                if (taskToAdd.isFloatingTask()) {
                    FloatingTaskCardHandle addedCard = floatingTaskListPanel
                            .navigateToFloatingTask(taskToAdd.getTaskName().toString());
                    assertMatching(taskToAdd, addedCard);
                }
            }
        }

        // confirm the list now contains all previous tasks plus the new
        // task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();
        assertTrue(eventTaskListPanel.isListMatching(expectedList));
        assertTrue(deadlineTaskListPanel.isListMatching(expectedList));
        assertTrue(floatingTaskListPanel.isListMatching(expectedList));
    }

}
