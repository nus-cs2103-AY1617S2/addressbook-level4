package seedu.watodo.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.watodo.commons.core.ComponentManager;
import seedu.watodo.commons.core.LogsCenter;
import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.Command;
import seedu.watodo.logic.commands.CommandResult;
import seedu.watodo.logic.commands.exceptions.CommandException;
import seedu.watodo.logic.parser.Parser;
import seedu.watodo.model.Model;
import seedu.watodo.model.task.ReadOnlyTask;
import seedu.watodo.storage.Storage;

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
    public CommandResult execute(String commandText) throws CommandException, IllegalValueException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);

        if (!commandText.equals("undo") && !commandText.equals("redo")) {
            model.addCommandToHistory(command);
            model.clearRedo();
        }

        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}
