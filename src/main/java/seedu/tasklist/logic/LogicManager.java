package seedu.tasklist.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.tasklist.commons.core.ComponentManager;
import seedu.tasklist.commons.core.LogsCenter;
import seedu.tasklist.logic.commands.Command;
import seedu.tasklist.logic.commands.CommandResult;
import seedu.tasklist.logic.commands.exceptions.CommandException;
import seedu.tasklist.logic.parser.Parser;
import seedu.tasklist.model.Model;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.storage.Storage;

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
        model.setUserInput(commandText);
        Command command = parser.parseCommand(commandText);

        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    //@@author A0143355J
    @Override
    public ObservableList<ReadOnlyTask> getTodayTaskList() {
        return model.getTodayTaskList();
    }

    //@@author A0143355J
    @Override
    public ObservableList<ReadOnlyTask> getTomorrowTaskList() {
        return model.getTomorrowTaskList();
    }
}
