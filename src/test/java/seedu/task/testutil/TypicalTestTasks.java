package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskList;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Walk the dog")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Incomplete").build();
            benson = new TaskBuilder().withName("Walk the cat")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Incomplete").build();
            carl = new TaskBuilder().withName("Walk the cow")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Incomplete").build();
            daniel = new TaskBuilder().withName("Walk the nyan cat")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Incomplete").build();
            elle = new TaskBuilder().withName("Walk the fish")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Complete").build();
            fiona = new TaskBuilder().withName("Walk the lion")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Complete").build();
            george = new TaskBuilder().withName("Walk the elephant")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Complete").build();

            // Manually added
            hoon = new TaskBuilder().withName("Walk the tiger")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").build();
            ida = new TaskBuilder().withName("Walk the zebra")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskListWithSampleData(TaskList ab) {
        for (TestTask person : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(person));
            } catch (DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[] { alice, benson, carl, daniel, elle, fiona, george };
    }

    public TaskList getTypicalAddressBook() {
        TaskList ab = new TaskList();
        loadTaskListWithSampleData(ab);
        return ab;
    }
}
