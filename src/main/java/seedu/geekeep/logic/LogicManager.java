package seedu.geekeep.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.geekeep.commons.core.ComponentManager;
import seedu.geekeep.commons.core.LogsCenter;
import seedu.geekeep.logic.commands.Command;
import seedu.geekeep.logic.commands.CommandResult;
import seedu.geekeep.logic.commands.exceptions.CommandException;
import seedu.geekeep.logic.parser.Parser;
import seedu.geekeep.model.Model;
import seedu.geekeep.model.task.ReadOnlyTask;
import seedu.geekeep.storage.Storage;

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
    public ObservableList<ReadOnlyTask> getFilteredPersonList() {
        return model.getFilteredTaskList();
    }
}
