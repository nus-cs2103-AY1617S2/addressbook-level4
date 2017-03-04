package seedu.toluist.controller;

import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;
import seedu.toluist.ui.UiStore;

/**
 * ListController is responsible for rendering the initial UI
 */
public class ListController extends Controller {
    private static final String RESULT_MESSAGE = "App loaded";
    private static final String COMMAND_WORD = "list";

    public ListController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        TodoList todoList = TodoList.load();
        UiStore.getInstance().setTask(todoList.getTasks());
        renderer.render();
        return new CommandResult(RESULT_MESSAGE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }
}
