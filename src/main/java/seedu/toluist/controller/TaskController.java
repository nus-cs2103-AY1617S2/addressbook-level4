package seedu.toluist.controller;

import java.util.logging.Logger;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.ui.Ui;

/**
 * TaskController is responsible for management of task
 */
public abstract class TaskController extends Controller {

    private String commandTemplate;

    protected Logger logger = LogsCenter.getLogger(getClass());

    public TaskController(Ui renderer) {
        super(renderer);
    }

    public TaskController(Ui renderer, String commandTemplate) {
        super(renderer);
        this.commandTemplate = commandTemplate;
    }

    @Override
    public boolean matchesCommand(String command) {
        return command.matches(commandTemplate);
    }

    @Override
    public abstract CommandResult execute(String command);
}
