package seedu.toluist.controller;

import java.util.Optional;

import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.UiStore;

/**
 * ListController is responsible for rendering the initial UI
 */
public class ListController extends Controller {
    private static final String RESULT_MESSAGE = "App loaded";

    public CommandResult execute(String command) {
        Optional<TodoList> todoList = TodoList.load();
        if (todoList.isPresent()) {
            UiStore.getInstance().setTask(todoList.get().getTasks());
        }
        renderer.render();
        return new CommandResult(RESULT_MESSAGE);
    }
}
