package seedu.address.controller;

import seedu.address.dispatcher.CommandResult;
import seedu.address.model.TodoList;

/**
 * Created by louis on 22/2/17.
 */
public class AppController extends Controller {
    private static String RESULT_MESSAGE = "App loaded";

    public CommandResult execute(String command) {
        final TodoList todoList = TodoList.getInstance();
        renderer.render(todoList);
        return new CommandResult(RESULT_MESSAGE);
    }
}
