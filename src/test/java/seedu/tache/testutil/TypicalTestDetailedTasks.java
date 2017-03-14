package seedu.tache.testutil;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.model.TaskManager;
import seedu.tache.model.task.DetailedTask;
import seedu.tache.model.task.UniqueDetailedTaskList;

public class TypicalTestDetailedTasks {

    public TestDetailedTask taskA, taskB, taskC, taskH, taskI;

    public TypicalTestDetailedTasks() {
        try {
            taskA = new DetailedTaskBuilder().withName("Walk the Dog")
                    .withStartDate("14 April 2017")
                    .withEndDate("14 April 2017")
                    .withTime("17:00")
                    .withDuration("2Hrs")
                    .withTags("MediumPriority").build();
            taskB = new DetailedTaskBuilder().withName("Buy Medicine")
                    .withStartDate("15 April 2017")
                    .withEndDate("-")
                    .withTime("12:00")
                    .withDuration("-")
                    .withTags("LowPriority").build();
            taskC = new DetailedTaskBuilder().withName("Submit Project Proposal")
                    .withStartDate("-")
                    .withEndDate("17 April 2017")
                    .withTime("15:00")
                    .withDuration("-")
                    .withTags("HighPriority").build();

            // Manually added
            taskH = new DetailedTaskBuilder().withName("Hoon Meier").build();
            taskI = new DetailedTaskBuilder().withName("Ida Mueller").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {
        for (TestDetailedTask task : new TypicalTestDetailedTasks().getTypicalDetailedTasks()) {
            try {
                ab.addDetailedTask(new DetailedTask(task));
            } catch (UniqueDetailedTaskList.DuplicateDetailedTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestDetailedTask[] getTypicalDetailedTasks() {
        return new TestDetailedTask[]{taskA, taskB, taskC, taskH, taskI};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
