package seedu.taskboss.testutil;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.TaskBoss;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask taskA, taskB, taskC, taskD, taskE, taskF, taskG, taskH, taskI, taskJ, taskK;

    public TypicalTestTasks() {
        try {
            taskA = new TaskBuilder().withName("Attend project meeting")
                    .withInformation("finish report")
                    .withPriorityLevel("Yes")
                    .withStartDateTime("Feb 18, 2017 5pm")
                    .withEndDateTime("Mar 28, 2017 5pm")
                    .withCategories("personal").build();
            taskB = new TaskBuilder().withName("Birthday party")
                    .withInformation("ABCRestaurant, 311, Clementi Ave 2, #02-25")
                    .withPriorityLevel("No")
                    .withStartDateTime("Feb 23, 2017 10pm")
                    .withEndDateTime("Jun 28, 2017 5pm")
                    .withCategories("family", "personal").build();
            taskC = new TaskBuilder().withName("Clean house").withPriorityLevel("Yes")
                    .withStartDateTime("Feb 19, 2017 11pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withInformation("clean bedroom, study and kitchen").build();
            taskD = new TaskBuilder().withName("Design the poster").withPriorityLevel("Yes")
                    .withStartDateTime("Feb 20, 2017 11.30pm")
                    .withEndDateTime("Apr 28, 2017 3pm")
                    .withInformation("print the poster, submit to manager").build();
            taskE = new TaskBuilder().withName("Ensure code quality").withPriorityLevel("No")
                    .withStartDateTime("Feb 22, 2017 5pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withInformation("check with Jack").build();
            taskF = new TaskBuilder().withName("Fix bugs in code").withPriorityLevel("No")
                    .withStartDateTime("Feb 21, 2017 1pm")
                    .withEndDateTime("Dec 10, 2017 5pm")
                    .withInformation("bugs in: part A and part D").build();
            taskG = new TaskBuilder().withName("Game project player testing").withPriorityLevel("Yes")
                    .withStartDateTime("Jan 1, 2017 5pm")
                    .withEndDateTime("Nov 28, 2017 5pm")
                    .withInformation("Finish all the UI and model before the test").build();

            // Manually added
            taskH = new TaskBuilder().withName("Have dinner with Hoon Meier").withPriorityLevel("Yes")
                    .withStartDateTime("Feb 19, 2018 5pm")
                    .withEndDateTime("Feb 28, 2018 5pm")
                    .withInformation("little india").build();
            taskI = new TaskBuilder().withName("Invite friends").withPriorityLevel("Yes")
                    .withStartDateTime("Feb 19, 2019 5pm")
                    .withEndDateTime("Feb 28, 2019 5pm")
                    .withInformation("chicago ave").build();
            taskJ = new TaskBuilder().withName("Join the LeaderTeam").withPriorityLevel("Yes")
                    .withStartDateTime("next sat 5pm")
                    .withEndDateTime("tomorrow")
                    .withInformation("Silicon Valley").build();
            taskK = new TaskBuilder().withName("Kelly wedding").withPriorityLevel("Yes")
                    .withStartDateTime("Dec 20 2019")
                    .withEndDateTime("Dec 21 2019")
                    .withInformation("Remember to bring the gift").build();
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
        return new TestTask[]{taskC, taskE, taskA, taskD, taskB, taskG, taskF};
    }

    public TaskBoss getTypicalTaskBoss() {
        TaskBoss ab = new TaskBoss();
        loadTaskBossWithSampleData(ab);
        return ab;
    }
}
