package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Parser;
import seedu.address.model.Model;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;
    private final Storage storage;
    private final Config config;

    public LogicManager(Model model, Storage storage, Config config) {
        this.model = model;
        this.parser = new Parser();
        this.storage = storage;
        this.config = config;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText.trim());
        command.setData(model);
        command.setStorage(storage);
        command.setConfig(config);
        try {
            if (!(command instanceof UndoCommand)) {
                model.saveCurrentState(commandText.trim());
            }
            CommandResult result = command.execute();

            return result;
        } catch (CommandException e) {
            if (!(command instanceof UndoCommand)) {
                model.discardCurrentState();
            }

            throw e;
        }
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public void prepareTaskList(ObservableList<ReadOnlyTask> taskListToday, ObservableList<ReadOnlyTask> taskListFuture,
            ObservableList<ReadOnlyTask> taskListCompleted) {
        model.prepareTaskList(taskListToday, taskListFuture, taskListCompleted);
    }
}
