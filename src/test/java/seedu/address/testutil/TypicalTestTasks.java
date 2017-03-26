package seedu.address.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask task1, task2, task3, task4, task5, task6, task7, task8, task9;

    public TypicalTestTasks() {
        try {
            task1 = new TaskBuilder().withName("Task1")
                    .withInformation("Information 1")
                    .withPriorityLevel("1")
                    .withDeadline("01-Jan-2017")
                    .withTags("friends").build();
            task2 = new TaskBuilder().withName("Task2")
                    .withInformation("Information 2")
                    .withPriorityLevel("2")
                    .withDeadline("02-Feb-2017")
                    .withTags("owesMoney", "friends").build();
            task3 = new TaskBuilder().withName("Task3")
                    .withDeadline("03-Mar-2017")
                    .withPriorityLevel("3")
                    .withInformation("Information 3").build();
            task4 = new TaskBuilder().withName("Task4")
                    .withDeadline("04-Apr-2017")
                    .withPriorityLevel("4")
                    .withInformation("Information 4").build();
            task5 = new TaskBuilder().withName("Task5")
                    .withDeadline("05-May-2017")
                    .withPriorityLevel("1")
                    .withInformation("Information 5").build();
            task6 = new TaskBuilder().withName("Task6")
                    .withDeadline("06-Jun-2017")
                    .withPriorityLevel("2")
                    .withInformation("Information 6").build();
            task7 = new TaskBuilder().withName("Task7")
                    .withDeadline("07-Jul-2017")
                    .withPriorityLevel("3")
                    .withInformation("Information 7").build();

            // Manually added
            task8 = new TaskBuilder().withName("Task8")
                    .withDeadline("08-Aug-2017")
                    .withPriorityLevel("4")
                    .withInformation("Information 8").build();

            task9 = new TaskBuilder().withName("Task9")
                    .withDeadline("09-Sep-2017")
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
        return new TestTask[]{task1, task2, task3, task4, task5, task6, task7};
    }

    public TaskManager getTypicalTaskManger() {
        TaskManager ab = new TaskManager();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
