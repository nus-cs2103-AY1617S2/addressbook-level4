//package guitests;
//
//import static org.junit.Assert.assertTrue;
//
//import org.junit.Test;
//
//import guitests.guihandles.TaskCardHandle;
//import seedu.task.commons.core.GoogleCalendar;
//import seedu.task.commons.core.Messages;
//import seedu.task.commons.exceptions.IllegalValueException;
//import seedu.task.logic.commands.SmartAddCommand;
//import seedu.task.model.tag.UniqueTagList;
//import seedu.task.model.tag.UniqueTagList.DuplicateTagException;
//import seedu.task.model.task.Date;
//import seedu.task.model.task.Location;
//import seedu.task.model.task.Name;
//import seedu.task.model.task.Remark;
//import seedu.task.testutil.TestTask;
//import seedu.task.testutil.TestUtil;
//
////@@author A0140063X
//public class SmartAddTest extends TaskManagerGuiTest {
//
//    @Test
//    public void smartAdd() throws DuplicateTagException, IllegalValueException {
//        TestTask[] currentList = td.getTypicalTasks();
//        GoogleCalendar.setTestGoogleCredentialFilePath();
//
//        //add task with all details
//        commandBox.runCommand(SmartAddCommand.COMMAND_WORD_1 + " Meet friends for dinner 6pm-8pm"
//                + " in Clementi r/Eat stingray t/friends");
//        TestTask taskToAdd = createTask("Meet friends for dinner in Clementi", "6pm", "8pm",
//                "Eat stingray", "Clementi", "friends", false, "");
//        assertSmartAddSuccess(taskToAdd, currentList);
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//
//        //add task without remark or tag
//        commandBox.runCommand(SmartAddCommand.COMMAND_WORD_1 + " Discuss presentation with group 10am-12pm"
//                + " in Meeting Room");
//        taskToAdd = createTask("Discuss presentation with group in Meeting Room", "10am", "12pm",
//                "", "Meeting Room", "", false, "");
//        assertSmartAddSuccess(taskToAdd, currentList);
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//
//        //add task without location, remark or tag, specify end time by stating duration
//        commandBox.runCommand(SmartAddCommand.COMMAND_WORD_1 + " Celebration Party fri 11pm 4 hours");
//        taskToAdd = createTask("Celebration Party", "fri 11pm", "sat 3am", "", "", "", false, "");
//        assertSmartAddSuccess(taskToAdd, currentList);
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//
//        //add duplicate task
//        commandBox.runCommand(SmartAddCommand.COMMAND_WORD_1 + " Celebration Party fri 11pm 4 hours");
//        taskToAdd = createTask("Celebration Party", "fri 11pm", "sat 3am", "", "", "", false, "");
//        assertResultMessage(SmartAddCommand.MESSAGE_DUPLICATE_TASK);
//        assertTrue(taskListPanel.isListMatching(currentList));
//
//        //add task without end date = assume duration 1hr
//        commandBox.runCommand(SmartAddCommand.COMMAND_WORD_1 + " Think about life sat 12am");
//        taskToAdd = createTask("Think about life", "sat 12am", "sat 1am", "", "", "", false, "");
//        assertSmartAddSuccess(taskToAdd, currentList);
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//
//        //add to empty list
//        commandBox.runCommand("clear");
//
//        //add task without start date = assume given date is start date and duration 1hr
//        commandBox.runCommand(SmartAddCommand.COMMAND_WORD_1 + " Meet with client 3pm");
//        taskToAdd = createTask("Meet with client", "3pm", "4pm", "", "", "", false, "");
//        assertSmartAddSuccess(taskToAdd);
//
//        //invalid command
//        commandBox.runCommand("smart add Johnny");
//        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
//    }
//
//    private TestTask createTask(String name, String startDate, String endDate, String remark,
//            String location, String tags, boolean isDone, String eventId)
//                    throws DuplicateTagException, IllegalValueException {
//        if (tags.trim().equals("")) {
//            return new TestTask(new Name(name), new Date(startDate), new Date(endDate),
//                    new Remark(remark), new Location(location),
//                    new UniqueTagList(), isDone, eventId);
//        } else {
//            return new TestTask(new Name(name), new Date(startDate), new Date(endDate),
//                    new Remark(remark), new Location(location),
//                    new UniqueTagList(tags.trim()), isDone, eventId);
//        }
//    }
//
//    private void assertSmartAddSuccess(TestTask taskToAdd, TestTask... currentList) {
//        //confirm the new card contains the right data
//        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
//        assertMatching(taskToAdd, addedCard);
//
//        //confirm the list now contains all previous tasks plus the new task
//        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
//        assertTrue(taskListPanel.isListMatching(expectedList));
//    }
//
//}
