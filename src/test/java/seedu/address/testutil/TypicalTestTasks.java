package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withContent("Alice Pauline")
                    .withTaskDateTime("1/2/2013 9:00")
                    .withTags("friends").build();
            benson = new TaskBuilder().withContent("Benson Meier")
                    .withTaskDateTime("2/3/2014 10:00")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withContent("Carl Kurz").build();
            daniel = new TaskBuilder().withContent("Daniel Meier").build();
            elle = new TaskBuilder().withContent("Elle Meyer")
                    .withTaskDateTime("3/4/2015 1:00").build();
            fiona = new TaskBuilder().withContent("Fiona Kunz").build();
            george = new TaskBuilder().withContent("George Best").build();

            // Manually added
            hoon = new TaskBuilder().withContent("Hoon Meier").build();
            ida = new TaskBuilder().withContent("Ida Mueller").build();
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
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
