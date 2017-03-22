package seedu.watodo.testutil;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.model.TaskManager;
import seedu.watodo.model.task.Task;
import seedu.watodo.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask eat, study, shop, code, play, sleep;
    
    public TypicalTestTasks() {
        try {
            eat = new TaskBuilder().withDescription("Time to eat").build(); //TODO continue
        
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
        return new TestTask[]{eat, study, sleep};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}
