package seedu.whatsleft.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.whatsleft.commons.core.ComponentManager;
import seedu.whatsleft.commons.core.LogsCenter;
import seedu.whatsleft.logic.commands.Command;
import seedu.whatsleft.logic.commands.CommandResult;
import seedu.whatsleft.logic.commands.exceptions.CommandException;
import seedu.whatsleft.logic.parser.Parser;
import seedu.whatsleft.model.Model;
import seedu.whatsleft.model.activity.ReadOnlyEvent;
import seedu.whatsleft.model.activity.ReadOnlyTask;
import seedu.whatsleft.storage.Storage;

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
    public ObservableList<ReadOnlyEvent> getFilteredEventList() {
        return model.getFilteredEventList();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}
