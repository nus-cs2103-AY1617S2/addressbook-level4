package seedu.taskList.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.taskList.commons.core.ComponentManager;
import seedu.taskList.commons.core.LogsCenter;
import seedu.taskList.logic.commands.Command;
import seedu.taskList.logic.commands.CommandResult;
import seedu.taskList.logic.commands.exceptions.CommandException;
import seedu.taskList.logic.parser.Parser;
import seedu.taskList.model.Model;
import seedu.taskList.model.task.ReadOnlyTask;
import seedu.taskList.storage.Storage;

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
}
