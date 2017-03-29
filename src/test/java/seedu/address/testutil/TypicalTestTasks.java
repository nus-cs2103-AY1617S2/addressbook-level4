package seedu.address.testutil;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.StartEndDateTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.exceptions.InvalidDurationException;
import seedu.address.model.task.exceptions.PastDateTimeException;

/**
 * TODO
 */
public class TypicalTestTasks {

    // TODO naming of tasks as names does not seem to be a good idea, and not descriptive enough
    // for example helpMe and iAmCode are to be manually added in test cases but it is not clear
    // conversely the name shows what the task contains, so it can be helpful
    public TestTask amuseFriend, bet, count, dog, elephant, flipTable, goondu, helpMe, iAmCode;

    //@@author A0140023E
    public TypicalTestTasks() {
        // Starting Test Date Time is set to one day after today so that dates in the past is not
        // generated to prevent a PastDateTimeException from occuring. Furthermore the precision
        // is truncated to seconds as Natty does not parse milliseconds
        ZonedDateTime startTestDateTime = ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(1);
        try {
            // floating task with a tag
            amuseFriend = new TaskBuilder().withName("Amuse friend").withTags("friends").build();
            // TODO flip the order of tags and test cases can fail, see TestUtil::compareCardAndPerson
            // using list compare instead of set compare
            // floating task with two tags
            bet = new TaskBuilder().withName("Bet dog race").withTags("luck", "money").build();
            // floating task with no tags
            count = new TaskBuilder().withName("Count chickens before they hatch").build();
            // task with deadlines
            dog = new TaskBuilder().withName("Dog naming")
                    .withDeadline(new Deadline(startTestDateTime.plusDays(3))).build();
            // task with start and end dates
            elephant = new TaskBuilder().withName("Elephant riding")
                    .withStartEndDateTime(new StartEndDateTime(startTestDateTime.plusHours(2),
                                                               startTestDateTime.plusHours(3)))
                    .build();
            // some other random floating tasks
            flipTable = new TaskBuilder().withName("Flip table").build();
            goondu = new TaskBuilder().withName("Goondu goon").build();

            // Manually added
            // TODO maybe to use non-floating tasks here
            helpMe = new TaskBuilder().withName("Help me").build();
            iAmCode = new TaskBuilder().withName("I am code").build();
        } catch (PastDateTimeException e) {
            e.printStackTrace();
            assert false : "not possible";
        } catch (InvalidDurationException e) {
            e.printStackTrace();
            assert false : "not possible";
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    //@@author
    public static void loadTaskListWithSampleData(TaskList taskList) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                taskList.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{amuseFriend, bet, count, dog, elephant, flipTable, goondu};
    }

    public TaskList getTypicalTaskList() {
        TaskList taskList = new TaskList();
        loadTaskListWithSampleData(taskList);
        return taskList;
    }
}
