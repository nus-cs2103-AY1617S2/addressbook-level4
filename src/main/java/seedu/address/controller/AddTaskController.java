package seedu.address.controller;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TodoList;
import seedu.address.model.task.Task;

/**
 * Created by louis on 21/2/17.
 */
public class AddTaskController extends Controller {

    public void execute(String command) {
        final TodoList todoList = TodoList.getInstance();
        Task newTask;
        try {
            newTask = new Task("Eat rice");
            todoList.addTask(newTask);
            renderer.render(todoList);
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
