package seedu.taskboss.testutil;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.TaskBoss;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, groceries, jim, meeting, lunch, celebration, photoshoot, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Contact Alice Pauline")
                    .withInformation("123, Jurong West Ave 6, #08-111")
                    .withPriorityLevel("3")
                    .withCategories("Friends").build();
            groceries = new TaskBuilder().withName("Buy groceries")
                    .withInformation("Clementi MRT")
                    .withPriorityLevel("3")
                    .withCategories("Home", "Family").build();
            jim = new TaskBuilder().withName("Dinner with Jim").withPriorityLevel("1")
                    .withInformation("at Orchard").build();
            meeting = new TaskBuilder().withName("Hall Meeting").withPriorityLevel("2")
                    .withInformation("meeting room 1").build();
            lunch = new TaskBuilder().withName("Family lunch").withPriorityLevel("2")
                    .withInformation("Dad will fetch me").build();
            celebration = new TaskBuilder().withName("Postexam celebration").withPriorityLevel("2")
                    .withInformation("little tokyo").build();
            photoshoot = new TaskBuilder().withName("Hall Photoshoot").withPriorityLevel("2")
                    .withInformation("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withName("Meet Hoon Meier").withPriorityLevel("2")
                    .withInformation("little india").build();
            ida = new TaskBuilder().withName("Meet Ida Mueller").withPriorityLevel("2")
                    .withInformation("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskBossWithSampleData(TaskBoss ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, groceries, jim, meeting, lunch, celebration, photoshoot};
    }

    public TaskBoss getTypicalTaskBoss() {
        TaskBoss ab = new TaskBoss();
        loadTaskBossWithSampleData(ab);
        return ab;
    }
}
