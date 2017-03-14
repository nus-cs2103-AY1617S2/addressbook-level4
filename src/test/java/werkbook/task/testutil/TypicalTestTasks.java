package werkbook.task.testutil;

import werkbook.task.commons.exceptions.IllegalValueException;
import werkbook.task.model.TaskList;
import werkbook.task.model.task.Task;
import werkbook.task.model.task.UniqueTaskList.DuplicateTaskException;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Walk the dog")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Incomplete").build();
            benson = new TaskBuilder().withName("Walk the cat")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Incomplete").build();
            carl = new TaskBuilder().withName("Walk the cow")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Incomplete").build();
            daniel = new TaskBuilder().withName("Walk the nyan cat")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Incomplete").build();
            elle = new TaskBuilder().withName("Walk the fish")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Complete").build();
            fiona = new TaskBuilder().withName("Walk the lion")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Complete").build();
            george = new TaskBuilder().withName("Walk the elephant")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Complete").build();

            // Manually added
            hoon = new TaskBuilder().withName("Walk the tiger")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Incomplete").build();
            ida = new TaskBuilder().withName("Walk the zebra")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Incomplete").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskListWithSampleData(TaskList taskList) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                taskList.addTask(new Task(task));
            } catch (DuplicateTaskException e) {
                assert false : "not possible";
            } catch (IllegalValueException ive) {
                assert false : "illegal values";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[] { alice, benson, carl, daniel, elle, fiona, george };
    }

    public TaskList getTypicalTaskList() {
        TaskList ab = new TaskList();
        loadTaskListWithSampleData(ab);
        return ab;
    }
}
