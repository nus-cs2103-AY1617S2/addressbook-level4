package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskList;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

/**
 * Pre-built dummy tasks to be used for tests.
 */
public class TypicalTestTasks {

    //@@author A0163673Y
    public TestTask bear, cat, dog, elephant, fish, goose, horse, iguana, jaguar;
    //@@author
    //@@author A0163744B
    public TestTask junk, kale, letOutDog;
    //@@author

    //@@author A0163673Y
    public TypicalTestTasks() {
        try {
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
            junk = new TaskBuilder()
                    .withDescription("Junk to give away")
                    .withCompletion(true)
                    .withDuration("01/01/2017 0000", "01/01/2017 0100")
                    .withDueDate("01/01/2017 0100")
                    .withTaskId(300)
                    .build();
            kale = new TaskBuilder()
                    .withDescription("Kale for salad")
                    .withDuration("01/03/2017 0800", "01/03/2017 1200")
                    .withDueDate("01/03/2017 1800")
                    .withTaskId(301)
                    .build();
            letOutDog = new TaskBuilder()
                    .withDescription("Let out dog")
                    .withDuration("01/02/2017 0600", "01/02/2017 0630")
                    .withDueDate("01/02/2017 1100")
                    .withTaskId(302)
                    .build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }
    //@@author

    public static void loadAddressBookWithSampleData(TaskList ab, TestTask[] data) {
        for (TestTask person : data) {
            try {
                ab.addTask(new Task(person));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    //@@author A0163744B
    public TestTask[] getTypicalTasksWithDates() {
        return new TestTask[]{junk, kale, letOutDog};
    }

    public TaskList getTypicalAddressBookWithDates() {
        TaskList ab = new TaskList();
        loadAddressBookWithSampleData(ab, getTypicalTasksWithDates());
        return ab;
    }
    //@@author

    //@@author A0163673Y
    public TestTask[] getTypicalTasks() {
        return new TestTask[]{bear, cat, dog, elephant, fish, goose, horse};
    }
    //@@author

    public TaskList getTypicalAddressBook() {
        TaskList ab = new TaskList();
        loadAddressBookWithSampleData(ab, new TypicalTestTasks().getTypicalTasks());
        return ab;
    }
}
