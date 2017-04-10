package seedu.taskmanager.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.taskmanager.commons.core.ComponentManager;
import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.CommandResult;
import seedu.taskmanager.logic.commands.exceptions.CommandException;
import seedu.taskmanager.logic.parser.Parser;
import seedu.taskmanager.model.Model;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new Parser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ObservableList<ReadOnlyTask> getInitialFilteredTaskList() {
        model.updateFilteredTaskListForInitialView();
        return model.getFilteredTaskList();
    }
}
