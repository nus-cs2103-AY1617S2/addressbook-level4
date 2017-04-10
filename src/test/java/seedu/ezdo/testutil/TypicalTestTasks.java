package seedu.ezdo.testutil;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.model.EzDo;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.UniqueTaskList;

//@@author A0139177W
/**
 * Generates the tasks for testing.
 */
public class TypicalTestTasks {

    private static final String MESSAGE_NOT_POSSIBLE = "not possible";
    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida, jack, kappa, leroy, megan;
    public TestTask alice2, benson2, carl2, daniel2, elle2, fiona2, george2;

    /**
     * Generates test tasks for testing.
     */
    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline")
                    .withPriority("1")
                    .withStartDate("12/12/2016 11:22")
                    .withDueDate("14/03/2017 22:33")
                    .withRecur("daily")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier")
                    .withPriority("")
                    .withStartDate("11/11/2015 14:33")
                    .withDueDate("12/02/2017 23:22")
                    .withRecur("weekly")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz")
                    .withPriority("2")
                    .withStartDate("12/12/2013 05:32")
                    .withDueDate("13/03/2014 00:34")
                    .withRecur("monthly")
                    .build();
            daniel = new TaskBuilder().withName("Daniel Meier")
                    .withPriority("2")
                    .withStartDate("12/12/2012 18:22")
                    .withDueDate("14/04/2016 15:17")
                    .withRecur("yearly")
                    .build();
            elle = new TaskBuilder().withName("Elle Meyer")
                    .withPriority("3")
                    .withStartDate("01/03/2010 00:33")
                    .withDueDate("15/05/2015 20:20")
                    .withRecur("")
                    .build();
            fiona = new TaskBuilder().withName("Fiona Kunz")
                    .withPriority("2")
                    .withStartDate("12/12/2015 14:15")
                    .withDueDate("16/06/2016 17:17")
                    .withRecur("")
                    .build();
            george = new TaskBuilder().withName("George Best")
                    .withPriority("3")
                    .withStartDate("02/07/2012 04:55")
                    .withDueDate("17/07/2015 22:22")
                    .withRecur("daily")
                    .build();
            alice2 = new TaskBuilder().withName("Alice Pauline")
                    .withPriority("1")
                    .withStartDate("12/12/2016 11:22")
                    .withDueDate("14/03/2017 22:33")
                    .withRecur("")
                    .withTags("friends").build();
            benson2 = new TaskBuilder().withName("Benson Meier")
                    .withPriority("")
                    .withStartDate("11/11/2015 14:33")
                    .withDueDate("12/02/2017 23:22")
                    .withRecur("")
                    .withTags("owesMoney", "friends").build();
            carl2 = new TaskBuilder().withName("Carl Kurz")
                    .withPriority("2")
                    .withStartDate("12/12/2013 05:32")
                    .withDueDate("13/03/2014 00:34")
                    .withRecur("")
                    .build();
            daniel2 = new TaskBuilder().withName("Daniel Meier")
                    .withPriority("2")
                    .withStartDate("12/12/2012 18:22")
                    .withDueDate("14/04/2016 15:17")
                    .withRecur("")
                    .build();
            elle2 = new TaskBuilder().withName("Elle Meyer")
                    .withPriority("3")
                    .withStartDate("01/03/2010 00:33")
                    .withDueDate("15/05/2015 20:20")
                    .withRecur("")
                    .build();
            fiona2 = new TaskBuilder().withName("Fiona Kunz")
                    .withPriority("2")
                    .withStartDate("12/12/2015 14:15")
                    .withDueDate("16/06/2016 17:17")
                    .withRecur("")
                    .build();
            george2 = new TaskBuilder().withName("George Best")
                    .withPriority("3")
                    .withStartDate("02/07/2012 04:55")
                    .withDueDate("17/07/2015 22:22")
                    .withRecur("")
                    .build();
            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier")
                    .withPriority("1")
                    .withStartDate("12/01/2017 11:12")
                    .withDueDate("18/08/2017 17:44")
                    .withRecur("yearly")
                    .build();
            ida = new TaskBuilder().withName("Ida Mueller")
                    .withPriority("2")
                    .withStartDate("12/12/2016 13:31")
                    .withDueDate("19/09/2017 02:00")
                    .withRecur("")
                    .build();
            jack = new TaskBuilder().withName("Jack Bauer")
                    .withPriority("2")
                    .withStartDate("12/1/2013 00:00")
                    .withDueDate("12/09/2015 15:15")
                    .withRecur("daily")
                    .build();
            kappa = new TaskBuilder().withName("Kappa Sushi")
                    .withPriority("")
                    .withStartDate("")
                    .withDueDate("")
                    .withRecur("monthly")
                    .build();
            leroy = new TaskBuilder().withName("Leroy Jenkins")
                    .withPriority("")
                    .withStartDate("")
                    .withDueDate("")
                    .withRecur("yearly")
                    .build();
            megan = new TaskBuilder().withName("Megan Fox")
                    .withPriority("")
                    .withStartDate("")
                    .withDueDate("")
                    .withRecur("")
                    .build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : MESSAGE_NOT_POSSIBLE;
        }
    }

    /**
     * Loads ezDo with sample test tasks.
     */
    public static void loadEzDoWithSampleData(EzDo ez) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ez.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : MESSAGE_NOT_POSSIBLE;
            }
        }
    }

    /**
     * Loads ezDo with sample non-recurring test tasks.
     */
    public static void loadEzDoWithSampleDataNonRecurring(EzDo ez) {
        for (TestTask task : new TypicalTestTasks().getTypicalNonRecurringTasks()) {
            try {
                ez.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : MESSAGE_NOT_POSSIBLE;
            }
        }
    }

    /**
     * Returns sample test tasks.
     */
    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    /**
     * Returns sample non recurring test tasks.
     */
    public TestTask[] getTypicalNonRecurringTasks() {
        return new TestTask[]{alice2, benson2, carl2, daniel2, elle2, fiona2, george2};
    }

    /**
     * Returns sample done test tasks.
     */
    public TestTask[] getTypicalDoneTasks() {
        return new TestTask[]{};
    }

    /**
     * Retrieves ezDo loaded with sample test tasks.
     */
    public EzDo getTypicalEzDo() {
        EzDo ez = new EzDo();
        loadEzDoWithSampleData(ez);
        return ez;
    }

}
//@@author
