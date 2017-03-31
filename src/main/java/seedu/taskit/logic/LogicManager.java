package seedu.taskit.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.taskit.commons.core.ComponentManager;
import seedu.taskit.commons.core.LogsCenter;
import seedu.taskit.logic.commands.Command;
import seedu.taskit.logic.commands.CommandResult;
import seedu.taskit.logic.commands.exceptions.CommandException;
import seedu.taskit.logic.parser.Parser;
import seedu.taskit.model.Model;
import seedu.taskit.model.task.ReadOnlyTask;
import seedu.taskit.storage.Storage;

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

    //@@author A0141011J
    @Override
    public CommandResult execute(String commandText) throws CommandException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        //commandList.addCommand(command);
        //command.setCommandHistory(commandList);
        if (command.isUndoable()) {
            model.save();
        }
        return command.execute();
    }
    //@@author

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}
