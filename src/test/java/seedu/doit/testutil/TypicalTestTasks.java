//@@author A0146809W
package seedu.doit.testutil;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.model.TaskManager;
import seedu.doit.model.item.Task;
import seedu.doit.model.item.UniqueTaskList;

/**
 * Typical Test Tasks
 */
public class TypicalTestTasks {
    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida, aF, bF, cF, aE, bE, cE;

    public TypicalTestTasks() {
        try {
            this.alice = new TaskBuilder().withName("Alice Pauline").withDescription("123, Jurong West Ave 6, #08-111")
                    .withDeadline("friday").withPriority("low").withTags("friends").build();
            this.benson = new TaskBuilder().withName("Benson Meier").withDescription("311, Clementi Ave 2, #02-25")
                    .withDeadline("thursday").withPriority("med").withTags("owesMoney", "friends").build();
            this.carl = new TaskBuilder().withName("Carl Kurz").withPriority("high").withDeadline("next wednesday")
                    .withDescription("wall street").build();
            this.daniel = new TaskBuilder().withName("Daniel Meier").withPriority("high").withDeadline("tomorrow")
                    .withDescription("10th street").build();
            this.elle = new TaskBuilder().withName("Elle Meyer").withPriority("low").withDeadline("next tuesday")
                    .withDescription("michegan ave").build();
            this.fiona = new TaskBuilder().withName("Fiona Kunz").withPriority("med").withDeadline("sunday")
                    .withDescription("little tokyo").build();
            this.george = new TaskBuilder().withName("George Best").withPriority("high").withDeadline("3/20/17")
                    .withDescription("4th street").build();
            this.aF = new TaskBuilder().withName("AAAAAFloating").withPriority("med").withDescription("l").build();
            this.bF = new TaskBuilder().withName("BBBBBFloating").withPriority("med").withDescription("l").build();
            this.cF = new TaskBuilder().withName("CCCCCFloating").withPriority("med").withDescription("l").build();
            this.aE = new TaskBuilder().withName("AAAAAEvent").withPriority("med").withDeadline("3/20/17")
                    .withStartTime("3/19/17").withDescription("l").build();
            this.bE = new TaskBuilder().withName("BBBBBEvent").withPriority("med").withDeadline("3/20/17")
                    .withStartTime("3/19/17").withDescription("l").build();
            this.cE = new TaskBuilder().withName("CCCCCEvent").withPriority("med").withDeadline("3/20/17")
                    .withStartTime("3/19/17").withDescription("l").build();
            // Manually added
            this.hoon = new TaskBuilder().withName("Hoon Meier").withPriority("low").withDeadline("3/14/18")
                    .withDescription("little india").build();
            this.ida = new TaskBuilder().withName("Ida Mueller").withPriority("med").withDeadline("04/04/17")
                    .withDescription("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[] { this.alice, this.benson, this.carl, this.daniel, this.elle, this.fiona, this.george,
                this.aE, this.bE, this.cE, this.aF, this.bF, this.cF };
    }

    public static TestTask getEventTestTask() throws IllegalValueException {
        return new TaskBuilder().withName("meeting").withStartTime("yesterday").withDescription("event")
                .withPriority("high").withDeadline("tomorrow").withTags("tttttttttag").build();
    }

    public static TestTask getDeadlineTestTask() throws IllegalValueException {
        return new TaskBuilder().withName("due soon").withDescription("task").withPriority("high")
                .withDeadline("tomorrow").withTags("tttttttttag").build();
    }

    public static TestTask getFloatingTestTask() throws IllegalValueException {
        return new TaskBuilder().withName("anytime").withDescription("fTask").withPriority("high")
                .withTags("tttttttttag").build();
    }

    public static TestTask getDeadlineTestTaskWithNoDescription() throws IllegalValueException {
        return new TaskBuilder().withName("noDescription").withDescription("").withPriority("high")
                .withDeadline("next week").withTags("tagme").build();
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
