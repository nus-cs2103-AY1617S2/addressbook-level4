package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskList;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * TODO
 */
public class TypicalTestTasks {

    // TODO naming of tasks as names does not seem to be a good idea, and not descriptive enough
    // for example helpMe and iAmCode are to be manually added in test cases but it is not clear
    // conversely the name shows what the task contains, so it can be helpful
    public TestTask amuseFriend, bet, count, dog, elephant, flipTable, goondu, helpMe, iAmCode;

    public TypicalTestTasks() {
        try {
            amuseFriend = new TaskBuilder().withName("Amuse friend").withTags("friends").build();
            // TODO flip the order of tags and test cases can fail, see TestUtil::compareCardAndPerson
            // using list compare instead of set compare
            bet = new TaskBuilder().withName("Bet dog race").withTags("luck", "money").build();
            count = new TaskBuilder().withName("Count chickens before they hatch").build();
            dog = new TaskBuilder().withName("Dog naming").build();
            elephant = new TaskBuilder().withName("Elephant riding").build();
            flipTable = new TaskBuilder().withName("Flip table").build();
            goondu = new TaskBuilder().withName("Goondu goon").build();

            // Manually added
            helpMe = new TaskBuilder().withName("Help me").build();
            iAmCode = new TaskBuilder().withName("I am code").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskListWithSampleData(TaskList taskList) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                taskList.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{amuseFriend, bet, count, dog, elephant, flipTable, goondu};
    }

    public TaskList getTypicalTaskList() {
        TaskList taskList = new TaskList();
        loadTaskListWithSampleData(taskList);
        return taskList;
    }
}
