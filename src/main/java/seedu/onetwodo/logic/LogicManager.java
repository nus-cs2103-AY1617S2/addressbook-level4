package seedu.onetwodo.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.onetwodo.commons.core.ComponentManager;
import seedu.onetwodo.commons.core.LogsCenter;
import seedu.onetwodo.logic.commands.Command;
import seedu.onetwodo.logic.commands.CommandResult;
import seedu.onetwodo.logic.commands.exceptions.CommandException;
import seedu.onetwodo.logic.parser.Parser;
import seedu.onetwodo.model.DoneStatus;
import seedu.onetwodo.model.Model;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.storage.Storage;

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

        // Parse input to command
        Command command = parser.parseCommand(commandText);

        // Give command reference to model data
        command.setData(model);

        // Return result message from executing command
        return command.execute();
    }

    //@@author A0143029M
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTasksByDoneStatus() {
        DoneStatus doneStatus = model.getDoneStatus();
        switch (doneStatus) {
        case ALL:
            model.updateFilteredListToShowAll();
            break;
        case DONE:
            model.updateFilteredDoneTaskList();
            break;
        case UNDONE:
            model.updateFilteredUndoneTaskList();
            break;
        }
        return model.getFilteredTaskList();
    }
}
