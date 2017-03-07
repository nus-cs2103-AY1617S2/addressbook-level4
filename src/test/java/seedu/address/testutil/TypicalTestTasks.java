package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask task1, task2, task3, task4, task5, task6, task7, task8, task9;

    public TypicalTestTasks() {
        try {
            task1 = new TaskBuilder().withTitle("Complete task 1")
                    .withDeadline("11-11-2017").withLabels("friends").build();
            task2 = new TaskBuilder().withTitle("Complete task 2").withDeadline("11-11-2017")
                    .withLabels("owesMoney", "friends").build();
            task3 = new TaskBuilder().withTitle("Complete task 3").withDeadline("11-11-2017").build();
            task4 = new TaskBuilder().withTitle("Complete task 4").withDeadline("11-11-2017").build();
            task5 = new TaskBuilder().withTitle("Complete task 5").withDeadline("11-11-2017").build();
            task6 = new TaskBuilder().withTitle("Complete task 6").withDeadline("11-11-2017").build();
            task7 = new TaskBuilder().withTitle("Complete task 7").withDeadline("11-11-2017").build();

            // Manually added
            task8 = new TaskBuilder().withTitle("Complete task 8").withDeadline("11-11-2017").build();
            task9 = new TaskBuilder().withTitle("Complete task 9").withDeadline("11-11-2017").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskManager ab) {
        for (TestTask person : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(person));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{task1, task2, task3, task4, task5, task6, task7};
    }

    public TaskManager getTypicalAddressBook() {
        TaskManager ab = new TaskManager();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
