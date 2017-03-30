package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;

//@@author A0105287E
public class SortTest extends TaskManagerGuiTest {

    @Test
    public void sort_taskByCompletion_success() throws IllegalValueException, IllegalDateTimeValueException {
        commandBox.runCommand("clear"); //clears the default list

        TestTask task1 = new TaskBuilder().withTitle("Complete task 2").withStartTime("10-10-2017 0100")
                .withDeadline("11-11-2017 2300").withLabels("owesMoney", "friends")
                .withStatus(true).build();
        TestTask task2 = new TaskBuilder().withTitle("Complete task 5").withStartTime("10-10-2017 0100")
                .withDeadline("11-11-2017 2300").withLabels("owesMoney", "friends")
                .withStatus(false).build();

        commandBox.runCommand(task1.getAddCommand()); //default status will be false
        commandBox.runCommand(task2.getAddCommand());
        commandBox.runCommand("mark 1 completed"); //list should be reordered when marked complete

        assertTrue(taskListPanel.isListMatching(new TestTask[]{task2, task1}));
    }

    @Test
    public void sort_completedTaskByDate_success() throws IllegalValueException, IllegalDateTimeValueException {
        commandBox.runCommand("clear"); //clears the default list

        TestTask task1 = new TaskBuilder().withTitle("Complete task 2").withStartTime("10-10-2017 0100")
                .withDeadline("11-11-2017 2300").withLabels("owesMoney", "friends")
                .withStatus(true).build();
        TestTask task2 = new TaskBuilder().withTitle("Complete task 5").withStartTime("9-9-2017 0100")
                .withDeadline("11-11-2017 2300").withLabels("owesMoney", "friends")
                .withStatus(true).build(); //should be placed on top of task1 because it starts earlier

        commandBox.runCommand(task1.getAddCommand()); //default status will be false
        commandBox.runCommand(task2.getAddCommand());
        commandBox.runCommand("mark 1 completed"); //list should be reordered when marked complete
        commandBox.runCommand("mark 1 completed");

        assertTrue(taskListPanel.isListMatching(new TestTask[]{task2, task1}));
    }

    @Test
    public void sort_incompleteTaskByDate_success() throws IllegalValueException, IllegalDateTimeValueException {
        commandBox.runCommand("clear"); //clears the default list

        TestTask task1 = new TaskBuilder().withTitle("Complete task 2").withStartTime("10-10-2017 0100")
                .withDeadline("11-11-2017 2300").withLabels("owesMoney", "friends")
                .withStatus(false).build();
        TestTask task2 = new TaskBuilder().withTitle("Complete task 5").withStartTime("9-9-2017 0100")
                .withDeadline("11-11-2017 2300").withLabels("owesMoney", "friends")
                .withStatus(false).build(); //should be placed on top of task1 because it starts earlier

        commandBox.runCommand(task1.getAddCommand()); //default status will be false
        commandBox.runCommand(task2.getAddCommand());

        assertTrue(taskListPanel.isListMatching(new TestTask[]{task2, task1}));
    }

    /* TODO: Fix in V0.5 after consulting Zhi Yuan
    @Test
    public void sort_testBookingByDate_success() throws IllegalValueException, CommandException,
                                                                IllegalDateTimeValueException {
        commandBox.runCommand("clear"); //clears the default list

        TestTask task1 = new TaskBuilder().withTitle("Complete task 2")
                .withBookings("9-9-2017 0100 to 10-10-2017 0100", "11-11-2017 0100 to 11-11-2017 2300")
                .withStatus(false).build();
        TestTask task2 = new TaskBuilder().withTitle("Complete task 2").withStartTime("10-10-2017 0100")
                .withDeadline("11-11-2017 2300").withLabels("owesMoney", "friends")
                .withStatus(false).build();; //should be placed on top of task1 because it starts earlier

        commandBox.runCommand("book Complete task 2 on 9-9-2017 0100 to 10-10-2017 0100,"
                + " 11-11-2017 2am to 11pm"); //default status will be false
        commandBox.runCommand(task2.getAddCommand());

        assertTrue(taskListPanel.isListMatching(new TestTask[]{task2, task1}));
    }
    */
}
