package seedu.address.testutil;

import seedu.address.model.tag.Tag;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.YTomorrow;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestPersons {

    public TestPerson alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    //@@author A0164032U
    public TypicalTestPersons() {
        try {
            alice = new PersonBuilder()
                    .withName("Alice Paul")
                    .withGroup("group1")
                    .withStartDate("01.01")
                    .withEndDate("tomorrow")
                    .withTags(Tag.TAG_INCOMPLETE)
                    .build();
            benson = new PersonBuilder()
                    .withName("Benson Meier")
                    .withGroup("311")
                    .withStartDate("01.01")
                    .withEndDate("12.11")
                    .withTags(Tag.TAG_INCOMPLETE)
                    .build();
            carl = new PersonBuilder()
                    .withName("Carl Kurz")
                    .withGroup("wall street")
                    .withStartDate("01.01")
                    .withEndDate("10.01")
                    .withTags(Tag.TAG_INCOMPLETE)
                    .build();
            daniel = new PersonBuilder()
                    .withName("Daniel Meier")
                    .withGroup("street")
                    .withStartDate("01.01")
                    .withEndDate("11.11")
                    .withTags(Tag.TAG_INCOMPLETE)
                    .build();
            elle = new PersonBuilder()
                    .withName("Elle Meyer")
                    .withGroup("michegan")
                    .withStartDate("01.01")
                    .withEndDate("05.05")
                    .withTags(Tag.TAG_INCOMPLETE)
                    .build();
            fiona = new PersonBuilder()
                    .withName("Fiona Kunz")
                    .withGroup("little tokyo")
                    .withStartDate("01.01")
                    .withEndDate("07.07")
                    .withTags(Tag.TAG_INCOMPLETE)
                    .build();
            george = new PersonBuilder()
                    .withName("George Best")
                    .withStartDate("01.01")
                    .withEndDate("04.12")
                    .withGroup("street")
                    .withTags(Tag.TAG_INCOMPLETE)
                    .build();

            // Manually added
            hoon = new PersonBuilder()
                    .withName("Hoon Meier")
                    .withStartDate("01.01")
                    .withEndDate("05.05")
                    .withGroup("little india")
                    .withTags("incomplete")
                    .build();
            ida = new PersonBuilder()
                    .withName("Ida Mueller")
                    .withStartDate("03.03")
                    .withEndDate("08.08")
                    .withGroup("chicago")
                    .withTags("incomplete")
                    .build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(YTomorrow ab) {
        for (TestPerson person : new TypicalTestPersons().getTypicalPersons()) {
            try {
                ab.addTask(new Task(person));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestPerson[] getTypicalPersons() {
        return new TestPerson[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public YTomorrow getTypicalAddressBook() {
        YTomorrow ab = new YTomorrow();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
