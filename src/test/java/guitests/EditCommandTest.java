package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.whatsleft.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.TaskCardHandle;

import seedu.whatsleft.commons.core.Messages;
import seedu.whatsleft.logic.commands.EditCommand;
import seedu.whatsleft.model.activity.ByDate;
import seedu.whatsleft.model.activity.Priority;
import seedu.whatsleft.model.activity.StartTime;
import seedu.whatsleft.model.tag.Tag;
import seedu.whatsleft.testutil.EventBuilder;
import seedu.whatsleft.testutil.TaskBuilder;
import seedu.whatsleft.testutil.TestEvent;
import seedu.whatsleft.testutil.TestTask;
import seedu.whatsleft.testutil.TestUtil;

//@@author A0148038A
/*
* GUI test for EditCommand
*/
public class EditCommandTest extends WhatsLeftGuiTest {

    // The list of events in the event list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestEvent[] filteredEventList = TestUtil.getFilteredTestEvents(te.getTypicalEvents());

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] filteredTaskList = TestUtil.getFilteredTestTasks(tt.getTypicalTasks());

    @Test
    public void editEventWithAllFieldsSpecified() throws Exception {
        String detailsToEdit = "CS2102 Demostration st/0900 sd/220617 et/1000 ed/220617 l/I Cube ta/demo";
        int filteredEventIndex = 1;
        int eventListPanelIndex = 1;
        TestEvent editedEvent = new EventBuilder().withDescription("CS2102 Demostration")
                 .withStartTime("0900").withStartDate("220617").withEndTime("1000").withEndDate("220617")
                .withLocation("I Cube").withTags("demo").build();

        assertEditEventSuccess(eventListPanelIndex, filteredEventIndex, detailsToEdit, editedEvent);
    }

    @Test
    public void editEventWithoutAllFieldsSpecified() throws Exception {
        String detailsToEdit = "l/NUS";

        //edit the last event in the event list
        int filteredEventIndex = filteredEventList.length;
        int eventListPanelIndex = filteredEventIndex;
        TestEvent eventToEdit = filteredEventList[filteredEventIndex - 1];
        TestEvent editedEvent = new EventBuilder(eventToEdit).withLocation("NUS").build();

        assertEditEventSuccess(eventListPanelIndex, filteredEventIndex, detailsToEdit, editedEvent);
    }

    @Test
    public void editEventClearTags() throws Exception {
        String detailsToEdit = "ta/";

        //edit the middle event in the event list
        int filteredEventIndex = filteredEventList.length / 2;
        int eventListPanelIndex = filteredEventIndex;
        TestEvent eventToEdit = filteredEventList[filteredEventIndex - 1];
        TestEvent editedEvent = new EventBuilder(eventToEdit).withTags().build();

        assertEditEventSuccess(eventListPanelIndex, filteredEventIndex, detailsToEdit, editedEvent);
    }

    @Test
    public void editEventWithInvalidEndDateTime() {
        //edit an event with invalid end date time
        commandBox.runCommand("edit ev 1 sd/060717 ed/050717");
        assertResultMessage(EditCommand.MESSAGE_ILLEGAL_EVENT_END_DATETIME);
    }

    @Test
    public void editEventToClash() {
        filteredEventList = TestUtil.addEventsToList(filteredEventList, te.workshop);
        commandBox.runCommand(te.workshop.getAddCommand());
        filteredEventList = TestUtil.getFilteredTestEvents(filteredEventList);
        commandBox.runCommand("edit ev 1 Latex Workshop st/0730 sd/280617 et/0930 ed/280617 l/YIH ta/");
        int filteredEventListIndex = 1;
        TestEvent editedEvent = te.clashedWorkshop;

        // confirm the new card contains the right data
        EventCardHandle editedCard = eventListPanel.navigateToEvent(editedEvent.getAsText());
        assertMatchingEvent(editedEvent, editedCard);

        // confirm the list now contains all previous events plus the event with updated details
        filteredEventList[filteredEventListIndex - 1] = editedEvent;
        filteredEventList = TestUtil.getFilteredTestEvents(filteredEventList);
        assertTrue(eventListPanel.isListMatching(filteredEventList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_SUCCESS_CLASH, editedEvent));
    }

    @Test
    public void editEventAfterFind() throws Exception {
        commandBox.runCommand("find CS2103");

        String detailsToEdit = "l/Changi Airport";
        int filteredEventIndex = 2;
        int eventListPanelIndex = 1;
        TestEvent eventToEdit = filteredEventList[filteredEventIndex - 1];
        TestEvent editedEvent = new EventBuilder(eventToEdit).withLocation("Changi Airport").build();

        assertEditEventSuccess(eventListPanelIndex, filteredEventIndex, detailsToEdit, editedEvent);
    }

    @Test
    public void editWithoutTypeSpecified() {
        commandBox.runCommand("edit 1 l/NTU");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void editWithInvalidIndex() {
        // invalid task index
        int invalidIndex = filteredEventList.length + 1;
        commandBox.runCommand("edit ev " + invalidIndex + " st/1200");
        assertResultMessage(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        // invalid task index
        invalidIndex = filteredTaskList.length + 1;
        commandBox.runCommand("edit ts " + invalidIndex + " bt/2230");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void editWithNoFieldSpecified() {
        commandBox.runCommand("edit ev 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);

        commandBox.runCommand("edit ts 1 ");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void editEventWithInvalidValues() {
        commandBox.runCommand("edit ev 1 st/3000");
        assertResultMessage(StartTime.MESSAGE_STARTTIME_CONSTRAINTS);

        commandBox.runCommand("edit ev 1 ta/*");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void editDuplicateEvent() {
        commandBox.runCommand("edit ev 1 CS2103 Tutorial st/0900 sd/280717 et/1000 ed/280717 l/COM1-B103 ta/demo");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void editTaskWithAllFieldsSpecified() throws Exception {
        String detailsToEdit = "CS2102 Project Report p/high bt/2200 bd/210617 l/IVLE ta/PDF";
        int filteredTaskIndex = 1;
        int taskListPanelIndex = filteredTaskIndex;
        TestTask editedTask = new TaskBuilder().withDescription("CS2102 Project Report")
                 .withPriority("high").withByTime("2200").withByDate("210617")
                 .withLocation("IVLE").withTags("PDF").build();

        assertEditTaskSuccess(taskListPanelIndex, filteredTaskIndex, detailsToEdit, editedTask);
    }

    @Test
    public void editTaskWithoutAllFieldsSpecified() throws Exception {
        String detailsToEdit = "bt/2330";

        //edit the last task in the task list
        int filteredTaskIndex = filteredTaskList.length;
        int taskListPanelIndex = filteredTaskIndex;
        TestTask taskToEdit = filteredTaskList[filteredTaskIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withByTime("2330").build();

        assertEditTaskSuccess(taskListPanelIndex, filteredTaskIndex, detailsToEdit, editedTask);
    }

    @Test
    public void editTaskWithInvalidValues() {
        commandBox.runCommand("edit ts 1 p/important");
        assertResultMessage(Priority.MESSAGE_PRIORITY_CONSTRAINTS);

        commandBox.runCommand("edit ts 1 bd/320917");
        assertResultMessage(ByDate.MESSAGE_BYDATE_CONSTRAINTS);
    }

    @Test
    public void editDuplicateTask() {
        //check command word case insensitive
        commandBox.runCommand("edit ts 1 LSM Project Report p/high bt/2300 bd/010517 l/IVLE ta/softcopy");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    /**
     * Checks whether the edited event has the correct updated details.
     *
     * @param panelListIndex index of task to edit in the current list shown in the event panel
     * @param filteredEventListIndex index of event to edit in filtered event list
     * @param detailsToEdit details to edit the event with as input to the edit command
     * @param editedEvent the expected event after editing the event's details
     */
    private void assertEditEventSuccess(int eventListPanelIndex, int filteredEventListIndex,
            String detailsToEdit, TestEvent editedEvent) {
        commandBox.runCommand("edit ev " + eventListPanelIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        EventCardHandle editedCard = eventListPanel.navigateToEvent(editedEvent.getAsText());
        assertMatchingEvent(editedEvent, editedCard);

        // confirm the list now contains all previous events plus the event with updated details
        filteredEventList[filteredEventListIndex - 1] = editedEvent;
        filteredEventList = TestUtil.getFilteredTestEvents(filteredEventList);
        assertTrue(eventListPanel.isListMatching(filteredEventList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
    }

    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param panelListIndex index of task to edit in the current list shown in task list panel
     * @param filteredTaskListIndex index of task to edit in filtered task list
     * @param detailsToEdit details to edit the event with as input to the edit command
     * @param editedTask the expected event after editing the task's details
     */
    private void assertEditTaskSuccess(int taskListPanelIndex, int filteredTaskListIndex,
            String detailsToEdit, TestTask editedTask) {
        commandBox.runCommand("edit ts " + taskListPanelIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getAsText());
        assertMatchingTask(editedTask, editedCard);

        // confirm the list now contains all previous events plus the event with updated details
        filteredTaskList[filteredTaskListIndex - 1] = editedTask;
        filteredTaskList = TestUtil.getFilteredTestTasks(filteredTaskList);
        assertTrue(taskListPanel.isListMatching(filteredTaskList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
