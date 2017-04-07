package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.whatsleft.testutil.TestEvent;
import seedu.whatsleft.testutil.TestTask;
import seedu.whatsleft.testutil.TestUtil;

public class UndoCommandTest extends WhatsLeftGuiTest {
    //@@author A0110491U
    @Test
    public void undoPreviousAddCommandSuccess() {
        //add one event
        TestEvent[] currentEventList = te.getTypicalEvents();
        currentEventList = TestUtil.filterExpectedTestEventList(currentEventList);
        TestTask[] currentTaskList = tt.getTypicalTasks();
        currentTaskList = TestUtil.filterExpectedTestTaskList(currentTaskList);
        TestEvent eventToAdd = te.consultation;
        assertAddActivityUndoSuccess(eventToAdd.getAddCommand(), currentTaskList, currentEventList);

        //add one task
        TestEvent[] currentEventList2 = te.getTypicalEvents();
        currentEventList2 = TestUtil.filterExpectedTestEventList(currentEventList);
        TestTask[] currentTaskList2 = tt.getTypicalTasks();
        currentTaskList2 = TestUtil.filterExpectedTestTaskList(currentTaskList);
        TestTask taskToAdd = tt.homework;
        assertAddActivityUndoSuccess(taskToAdd.getAddCommand(), currentTaskList2, currentEventList2);

    }

    //@@author A0110491U
    @Test
    public void undoPreviousEditCommandSuccess() {
        //edit an event
        TestEvent[] currentEventList = te.getTypicalEvents();
        currentEventList = TestUtil.filterExpectedTestEventList(currentEventList);
        TestTask[] currentTaskList = tt.getTypicalTasks();
        currentTaskList = TestUtil.filterExpectedTestTaskList(currentTaskList);
        String eventDetailsToEdit = "ev 1 sd/friday ed/friday";
        assertEditActivityUndoSuccess(eventDetailsToEdit, currentTaskList, currentEventList);

        //edit a task
        TestEvent[] currentEventList2 = te.getTypicalEvents();
        currentEventList2 = TestUtil.filterExpectedTestEventList(currentEventList);
        TestTask[] currentTaskList2 = tt.getTypicalTasks();
        currentTaskList2 = TestUtil.filterExpectedTestTaskList(currentTaskList);
        String taskDetailsToEdit = "ts 2 by/friday p/low";
        assertEditActivityUndoSuccess(taskDetailsToEdit, currentTaskList2, currentEventList2);
    }

    //@@author A0110491U
    @Test
    public void undoPreviousDeleteCommandSuccess() {
        //delete an event
        TestEvent[] currentEventList = te.getTypicalEvents();
        currentEventList = TestUtil.filterExpectedTestEventList(currentEventList);
        TestTask[] currentTaskList = tt.getTypicalTasks();
        currentTaskList = TestUtil.filterExpectedTestTaskList(currentTaskList);
        String eventDetailsToDelete = "ev 2";
        assertDeleteActivityUndoSuccess(eventDetailsToDelete, currentTaskList, currentEventList);

        //delete a task
        currentEventList = TestUtil.filterExpectedTestEventList(currentEventList);
        currentTaskList = TestUtil.filterExpectedTestTaskList(currentTaskList);
        String taskDetailsToDelete = "ts 3";
        assertDeleteActivityUndoSuccess(taskDetailsToDelete, currentTaskList, currentEventList);
    }

    //@@author A0110491U
    @Test
    public void undoPreviousClearCommandSuccess() {
        //clear all
        TestEvent[] currentEventList = te.getTypicalEvents();
        currentEventList = TestUtil.filterExpectedTestEventList(currentEventList);
        TestTask[] currentTaskList = tt.getTypicalTasks();
        currentTaskList = TestUtil.filterExpectedTestTaskList(currentTaskList);
        String clearcommand = "clear";
        assertClearActivityUndoSuccess(clearcommand, currentTaskList, currentEventList);

        //clear events
        currentEventList = TestUtil.filterExpectedTestEventList(currentEventList);
        currentTaskList = TestUtil.filterExpectedTestTaskList(currentTaskList);
        String clearevent = "clear ev";
        assertClearActivityUndoSuccess(clearevent, currentTaskList, currentEventList);

        //clear tasks
        currentEventList = TestUtil.filterExpectedTestEventList(currentEventList);
        currentTaskList = TestUtil.filterExpectedTestTaskList(currentTaskList);
        String cleartask = "clear ts";
        assertClearActivityUndoSuccess(cleartask, currentTaskList, currentEventList);

    }

    //@@author A0110491U
    /**
     * Asserts undo a previous clear command is successful
     * @param clearcommand
     * @param taskslist
     * @param eventslist
     */
    private void assertClearActivityUndoSuccess(String clearcommand, TestTask[] taskslist,
            TestEvent[] eventslist) {
        TestTask[] originalTasks = taskslist;
        TestEvent[] originalEvents = eventslist;
        commandBox.runCommand(clearcommand);
        commandBox.runCommand("undo");
        assertTrue(eventListPanel.isListMatching(originalEvents));
        assertTrue(taskListPanel.isListMatching(originalTasks));
    }

    //@@author A0110491U
    /**
     * Asserts undo a previous edit command is successful
     * @param eventDetailsToEdit
     * @param taskslist
     * @param eventslist
     */
    private void assertEditActivityUndoSuccess(String eventDetailsToEdit, TestTask[] taskslist,
            TestEvent[] eventslist) {
        TestTask[] originalTasks = taskslist;
        TestEvent[] originalEvents = eventslist;
        commandBox.runCommand("edit " + eventDetailsToEdit);
        commandBox.runCommand("undo");
        assertTrue(eventListPanel.isListMatching(originalEvents));
        assertTrue(taskListPanel.isListMatching(originalTasks));
    }

    //@@author A0110491U
    /**
     * Asserts undo a previous delete command is successful
     * @param todelete
     * @param taskslist
     * @param eventslist
     */
    private void assertDeleteActivityUndoSuccess(String todelete, TestTask[] taskslist,
            TestEvent[] eventslist) {
        TestTask[] originalTasks = taskslist;
        TestEvent[] originalEvents = eventslist;
        commandBox.runCommand("delete " + todelete);
        commandBox.runCommand("undo");
        assertTrue(eventListPanel.isListMatching(originalEvents));
        assertTrue(taskListPanel.isListMatching(originalTasks));
    }

    //@@author A0110491U
    /**
     * Asserts undo a previous add command is successful
     * @param addactivity
     * @param taskslist
     * @param eventslist
     */
    public void assertAddActivityUndoSuccess(String addactivity, TestTask[] taskslist, TestEvent[] eventslist) {
        TestTask[] originalTasks = taskslist;
        TestEvent[] originalEvents = eventslist;
        commandBox.runCommand(addactivity);
        commandBox.runCommand("undo");
        assertTrue(eventListPanel.isListMatching(originalEvents));
        assertTrue(taskListPanel.isListMatching(originalTasks));

    }

}
