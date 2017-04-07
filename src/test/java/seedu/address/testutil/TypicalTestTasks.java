package seedu.address.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask task11, task22, task33, task44, task55, task66, task77, task88, task99;

    public TypicalTestTasks() {
        try {
            task11 = new TaskBuilder().withName("Task11")
                    .withInformation("Information 1")
                    .withPriorityLevel("1")
                    .withDeadline("01-Jan-2017 @ 10:00:00")
                    .withTags("friends").build();
            task22 = new TaskBuilder().withName("Task22")
                    .withInformation("Information 2")
                    .withPriorityLevel("2")
                    .withDeadline("02-Feb-2017 @ 10:00")
                    .withTags("owesMoney", "friends").build();
            task33 = new TaskBuilder().withName("Task33")
                    .withDeadline("03-Mar-2017 @ 10:00")
                    .withPriorityLevel("3")
                    .withInformation("Information 3").build();
            task44 = new TaskBuilder().withName("Task44")
                    .withDeadline("04-Apr-2017 @ 10:00")
                    .withPriorityLevel("4")
                    .withInformation("Information 4").build();
            task55 = new TaskBuilder().withName("Task55")
                    .withDeadline("05-May-2017 @ 10:00")
                    .withPriorityLevel("1")
                    .withInformation("Information 5").build();
            task66 = new TaskBuilder().withName("Task66")
                    .withDeadline("06-Jun-2017 @ 10:00")
                    .withPriorityLevel("2")
                    .withInformation("Information 6").build();
            task77 = new TaskBuilder().withName("Task77")
                    .withDeadline("07-Jul-2017 @ 10:00")
                    .withPriorityLevel("3")
                    .withInformation("Information 7").build();

            // Manually added
            task88 = new TaskBuilder().withName("Task88")
                    .withDeadline("08-Aug-2017 @ 10:00")
                    .withPriorityLevel("4")
                    .withInformation("Information 8").build();

            task99 = new TaskBuilder().withName("Task99")
                    .withDeadline("09-Sep-2017 @ 10:00")
                    .withPriorityLevel("1")
                    .withInformation("Information 9").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskManager ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{task11, task22, task33, task44, task55, task66, task77};
    }

    public TaskManager getTypicalTaskManger() {
        TaskManager ab = new TaskManager();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
