package seedu.address.controller;

import seedu.address.dispatcher.CommandResult;
import seedu.address.model.TodoList;
import seedu.address.ui.UiStore;

import java.util.Optional;

/**
 * AppController is responsible for rendering the initial UI
 */
public class AppController extends Controller {
    private static final String RESULT_MESSAGE = "App loaded";

    public CommandResult execute(String command) {
        Optional<TodoList> todoList = storage.load();
        if (todoList.isPresent()) {
            UiStore.getInstance().setTask(todoList.get().getTasks());
        }
        renderer.render();
        return new CommandResult(RESULT_MESSAGE);
    }
}
