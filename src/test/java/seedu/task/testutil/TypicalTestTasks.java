package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskList;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

/**
 * Pre-built dummy tasks to be used for tests.
 */
public class TypicalTestTasks {

    //@@joshuaching A0163673Y
    public TestTask bear, cat, dog, elephant, fish, goose, horse, iguana, jaguar;
    //@@author

    public TypicalTestTasks() {
        try {
            //@@joshuaching A0163673Y
            bear = new TaskBuilder().withDescription("Walk the bear")
                    .withTags("urgent").withTaskId(200).build();
            cat = new TaskBuilder().withDescription("Walk the cat at zoo")
                    .withTags("feline", "urgent").withTaskId(201).build();
            dog = new TaskBuilder()
                    .withDescription("Walk the dog now").withTaskId(202).build();
            elephant = new TaskBuilder()
                    .withDescription("Walk the elephant at zoo").withTaskId(203).build();
            fish = new TaskBuilder().withDescription("Walk the fish")
                    .withTaskId(204).build();
            goose = new TaskBuilder().withDescription("Walk the goose")
                    .withTaskId(205).build();
            horse = new TaskBuilder()
                    .withDescription("Walk the horse").withTaskId(206).build();

            // Manually added
            iguana = new TaskBuilder().withDescription("Walk the iguana")
                    .withTaskId(207)
                    .build();
            jaguar = new TaskBuilder().withDescription("Walk the jaguar")
                    .withTaskId(208).build();
            //@@author
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskList ab) {
        for (TestTask person : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(person));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    //@@joshuaching A0163673Y
    public TestTask[] getTypicalTasks() {
        return new TestTask[]{bear, cat, dog, elephant, fish, goose, horse};
    }
    //@@author

    public TaskList getTypicalAddressBook() {
        TaskList ab = new TaskList();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
