package seedu.doist.testutil;

import seedu.doist.commons.exceptions.IllegalValueException;
import seedu.doist.model.TodoList;
import seedu.doist.model.task.Task;
import seedu.doist.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask laundry, homework, chores, work, school, groceries, shopping, email, exercise;

    public TypicalTestTasks() {
        try {
            laundry = new TaskBuilder().withName("Do laundry").build();
            homework = new TaskBuilder().withName("Complete math homework").withTags("school", "math").build();
            chores = new TaskBuilder().withName("Clean up house").withPriority("VERYIMPORTANT").build();
            work = new TaskBuilder().withName("Schedule meeting with boss").withPriority("IMPORTANT").build();
            school = new TaskBuilder().withName("Submit chemistry assignment").build();
            groceries = new TaskBuilder().withName("Pick up milk").build();
            shopping = new TaskBuilder().withName("Buy new clock").build();

            // Manually added
            email = new TaskBuilder().withName("Send emails to client").build();
            exercise = new TaskBuilder().withName("Go for a run").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadDoistWithSampleData(TodoList ab) {
        for (TestTask person : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(person));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{laundry, homework, chores, work, school, groceries, shopping};
    }

    public TodoList getTypicalAddressBook() {
        TodoList ab = new TodoList();
        loadDoistWithSampleData(ab);
        return ab;
    }
}
