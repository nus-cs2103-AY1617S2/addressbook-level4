package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

public class TypicalTestTasks {
    public TestTask hw, lunch, interview;

    public TypicalTestTasks() {
        try {
            hw = new TaskBuilder().withTitle("Do HW")
                    .withTags("school").build();
            lunch = new TaskBuilder().withTitle("Lunch with Bob")
                    .withTags("leisure", "friends").build();
            interview = new TaskBuilder().withTitle("Interview for big company")
                    .withTags("career").build();
                   
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
        return new TestTask[]{hw, lunch, interview};
    }

    public AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
