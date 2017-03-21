package seedu.taskboss.testutil;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.TaskBoss;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida, johnny, kelvin;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline")
                    .withInformation("123, Jurong West Ave 6, #08-111")
                    .withPriorityLevel("Yes")
                    .withStartDateTime("Feb 18, 2017 5pm")
                    .withEndDateTime("Mar 28, 2017 5pm")
                    .withCategories("friends").build();
            benson = new TaskBuilder().withName("Benson Meier")
                    .withInformation("311, Clementi Ave 2, #02-25")
                    .withPriorityLevel("No")
                    .withStartDateTime("Feb 23, 2017 10pm")
                    .withEndDateTime("Jun 28, 2017 5pm")
                    .withCategories("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPriorityLevel("Yes")
                    .withStartDateTime("Feb 19, 2017 11pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withInformation("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPriorityLevel("Yes")
                    .withStartDateTime("Feb 20, 2017 11.30pm")
                    .withEndDateTime("Apr 28, 2017 3pm")
                    .withInformation("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withPriorityLevel("Yes")
                    .withStartDateTime("Feb 22, 2017 5pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withInformation("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withPriorityLevel("Yes")
                    .withStartDateTime("Feb 21, 2017 1pm")
                    .withEndDateTime("Dec 10, 2017 5pm")
                    .withInformation("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withPriorityLevel("Yes")
                    .withStartDateTime("Jan 1, 2017 5pm")
                    .withEndDateTime("Nov 28, 2017 5pm")
                    .withInformation("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withPriorityLevel("Yes")
                    .withStartDateTime("Feb 19, 2018 5pm")
                    .withEndDateTime("Feb 28, 2018 5pm")
                    .withInformation("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withPriorityLevel("Yes")
                    .withStartDateTime("Feb 19, 2017 5pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withInformation("chicago ave").build();
            johnny = new TaskBuilder().withName("Johnny Depp").withPriorityLevel("Yes")
                    .withStartDateTime("next sat 5pm")
                    .withEndDateTime("tomorrow")
                    .withInformation("Silicon Valley").build();
            kelvin = new TaskBuilder().withName("Kelvin Koo").withPriorityLevel("Yes")
                    .withStartDateTime("next tue 10am")
                    .withEndDateTime("next wed")
                    .withInformation("clementi ave 2").build();
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
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskBoss getTypicalTaskBoss() {
        TaskBoss ab = new TaskBoss();
        loadTaskBossWithSampleData(ab);
        return ab;
    }
}
