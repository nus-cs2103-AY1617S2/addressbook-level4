package seedu.doist.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.doist.commons.core.ComponentManager;
import seedu.doist.commons.core.LogsCenter;
import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.CommandResult;
import seedu.doist.logic.commands.exceptions.CommandException;
import seedu.doist.logic.parser.Parser;
import seedu.doist.model.Model;
import seedu.doist.model.task.ReadOnlyTask;
import seedu.doist.storage.Storage;

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

        model.saveCurrentToHistory();
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredPersonList() {
        return model.getFilteredTaskList();
    }

    public void undo() {
        model.recoverPreviousTodoList();
    }
    public void redo() {
        model.recoverNextTodoList();
    }
}
