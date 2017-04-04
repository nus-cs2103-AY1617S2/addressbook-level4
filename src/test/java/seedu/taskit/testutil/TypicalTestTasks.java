// @@author A0163996J

package seedu.taskit.testutil;

import seedu.taskit.commons.exceptions.IllegalValueException;
import seedu.taskit.model.AddressBook;
import seedu.taskit.model.task.Task;
import seedu.taskit.model.task.UniqueTaskList;

public class TypicalTestTasks {
    public TestTask hw1, hw2, lunch, interview, meeting, shopping, assignment;

    public TypicalTestTasks() {
        try {
            interview = new TaskBuilder().withTitle("Interview for big company")
                    .withPriority("high")
                    .withTags("career").build();
            hw1 = new TaskBuilder().withTitle("Do HW 1")
                    .withPriority("medium")
                    .withTags("school").build();
            hw2 = new TaskBuilder().withTitle("Do HW 2")
                    .withPriority("medium")
                    .withTags("school").build();
            lunch = new TaskBuilder().withTitle("Lunch with Bob")
                    .withPriority("low")
                    .withTags("leisure", "friends").build();
            shopping = new TaskBuilder().withTitle("Shopping with friends")
                    .withPriority("low")
                    .withTags("leisure", "friends").build();

            // Manually added
            meeting = new TaskBuilder().withTitle("Software Engineering Meeting")
                    .withPriority("low")
                    .withTags("school").build();
            assignment = new TaskBuilder().withTitle("CS3230 Assignment")
                    .withPriority("low")
                    .withTags("school").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{hw1, hw2, lunch, interview, shopping};
    }

    public AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
