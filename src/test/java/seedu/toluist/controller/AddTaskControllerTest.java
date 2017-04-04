//@@author A0131125Y
package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import seedu.toluist.commons.exceptions.InvalidCommandException;
import seedu.toluist.model.Task;
import seedu.toluist.testutil.TypicalTestTodoLists;

/**
 * Tests for AddTaskController
 */
public class AddTaskControllerTest extends ControllerTest {

    protected Controller controllerUnderTest() {
        return new AddTaskController();
    }

    @Test
    public void addFloatingTask() throws InvalidCommandException {
        String taskDescription = "learn how to catch a pokemon";
        Task floatingTask = new Task(taskDescription);
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put("description", taskDescription);
        controller.execute(tokens);

        ArrayList<Task> tasksThatShouldExist = new ArrayList<>();
        for (Task task : (new TypicalTestTodoLists()).getTypicalTasks()) {
            tasksThatShouldExist.add(task);
        }
        tasksThatShouldExist.add(floatingTask);
        assertTasksExist(tasksThatShouldExist.toArray(new Task[3]));
    }
}
