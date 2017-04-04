package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask mathAssgn, buyStove, writeEssay, buyRiceCooker, finishLab, markCode, nightRun, hoon, ida;

    public TypicalTestTasks() {
        try {
            mathAssgn = new TaskBuilder().withName("Do math assignment").withTags("math").build();
            buyStove = new TaskBuilder().withName("Buy Meier stove").withTags("appliance", "stove").build();
            writeEssay = new TaskBuilder().withName("Write english essay").build();
            buyRiceCooker = new TaskBuilder().withName("Buy Meier rice cooker").build();
            finishLab = new TaskBuilder().withName("Complete CS2106 Lab Assignment").build();
            markCode = new TaskBuilder().withName("Mark CS1010S").build();
            nightRun = new TaskBuilder().withName("Go for a night run").build();

            // Manually added
            hoon = new TaskBuilder().withName("Meet Prof Hoon Meier").build();
            ida = new TaskBuilder().withName("Meet Prof Ida Mueller").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new FloatingTask(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[] { mathAssgn, buyStove, writeEssay, buyRiceCooker, finishLab, markCode, nightRun };
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
