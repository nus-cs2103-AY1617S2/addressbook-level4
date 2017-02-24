package seedu.address.controller;

import seedu.address.dispatcher.CommandResult;
import seedu.address.model.TodoList;

/**
 * AppController is responsible for rendering the initial UI
 */
public class AppController extends Controller {
    private static final String RESULT_MESSAGE = "App loaded";

    public CommandResult execute(String command) {
        final TodoList todoList = TodoList.getInstance();
        renderer.render(todoList);
        return new CommandResult(RESULT_MESSAGE);
    }
}
