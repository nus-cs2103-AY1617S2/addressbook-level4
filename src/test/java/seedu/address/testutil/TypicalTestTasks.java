package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, hoon, ida;
    //public TestTask alice, benson, carl, fiona, hoon;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withTitle("Alice Pauline")
                    .withContent("Alice Pauline content")
                    .withTaskDateTime("1/2/2013 9:00")
                    .withTags("friends").build();
            benson = new TaskBuilder().withTitle("Benson Meier")
                    .withContent("Benson Meier content")
                    .withTaskDateTime("2/3/2014 10:00")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withTitle("Carl Kurz").withContent("Carl Kurz content").build();
            daniel = new TaskBuilder().withTitle("Daniel Meier").build();
            elle = new TaskBuilder().withTitle("Elle Meyer")
                    .withTaskDateTime("3/4/2015 1:00").build();
            fiona = new TaskBuilder().withTitle("Fiona Kunz")
                    .withContent("Fiona Kunz content").build();
            //george = new TaskBuilder().withTitle("George Best").build();

            // Manually added
            hoon = new TaskBuilder().withTitle("Hoon Meier").build();
            ida = new TaskBuilder().withTitle("Ida Mueller").build();
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
//        return new TestTask[]{alice, benson, carl, fiona};
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona};
    }

    public AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
