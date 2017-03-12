package seedu.doist.testutil;

import seedu.doist.commons.exceptions.IllegalValueException;
import seedu.doist.model.TodoList;
import seedu.doist.model.task.Task;
import seedu.doist.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida, taskWithPriority;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline").build();  //.withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPriority("VERY IMPORTANT").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPriority("IMPORTANT").build();
            taskWithPriority = new TaskBuilder().withName("a task with priority").withPriority("IMPORTANT").build();
            elle = new TaskBuilder().withName("Elle Meyer").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").build();
            george = new TaskBuilder().withName("George Best").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").build();
            ida = new TaskBuilder().withName("Ida Mueller").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TodoList ab) {
        for (TestTask person : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(person));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TodoList getTypicalAddressBook() {
        TodoList ab = new TodoList();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
