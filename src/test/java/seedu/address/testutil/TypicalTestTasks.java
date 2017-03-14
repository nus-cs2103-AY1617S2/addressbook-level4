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
            alice = new TaskBuilder().withTitle("Alice Pauline")
                    .withInstruction("123, Jurong West Ave 6, #08-111").withPriority("1")
                    .withDate("140317")
                    .withTags("friends").build();
            benson = new TaskBuilder().withTitle("Benson Meier").withInstruction("311, Clementi Ave 2, #02-25")
                    .withPriority("1").withDate("140317")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withTitle("Carl Kurz").withDate("140317")
                    .withPriority("1").withInstruction("wall street").build();
            daniel = new TaskBuilder().withTitle("Daniel Meier").withDate("140317")
                    .withPriority("1").withInstruction("10th street").build();
            elle = new TaskBuilder().withTitle("Elle Meyer").withDate("140317")
                    .withPriority("1").withInstruction("michegan ave").build();
            fiona = new TaskBuilder().withTitle("Fiona Kunz").withDate("140317")
                    .withPriority("1").withInstruction("little tokyo").build();
            george = new TaskBuilder().withTitle("George Best").withDate("140317")
                    .withPriority("1").withInstruction("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withTitle("Hoon Meier").withDate("140317")
                    .withPriority("1").withInstruction("little india").build();
            ida = new TaskBuilder().withTitle("Ida Mueller").withDate("140317")
                    .withPriority("1").withInstruction("chicago ave").build();
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
