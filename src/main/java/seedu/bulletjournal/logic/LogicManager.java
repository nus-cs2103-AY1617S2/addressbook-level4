package seedu.bulletjournal.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.bulletjournal.commons.core.ComponentManager;
import seedu.bulletjournal.commons.core.LogsCenter;
import seedu.bulletjournal.logic.commands.Command;
import seedu.bulletjournal.logic.commands.CommandResult;
import seedu.bulletjournal.logic.commands.exceptions.CommandException;
import seedu.bulletjournal.logic.parser.Parser;
import seedu.bulletjournal.model.Model;
import seedu.bulletjournal.model.task.ReadOnlyTask;
import seedu.bulletjournal.storage.Storage;

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
    public ObservableList<ReadOnlyTask> getUndoneTaskList() {
        return model.getUndoneTaskList();
    }
}
