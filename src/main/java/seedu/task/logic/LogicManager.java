package seedu.task.logic;

import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.storage.Storage;
import seedu.logic.parser.Parser;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.CommandResult;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.Model;
import seedu.task.model.TaskBook;
import seedu.task.model.task.ReadOnlyTask;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;
    private static Stack<TaskBook> undoStack, redoStack;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new Parser();
        undoStack = new Stack<TaskBook>();
        redoStack = new Stack<TaskBook>();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        if(command.isMutable()) {
            undoStack.push(new TaskBook(model.getTaskBook()));
            redoStack.clear();
        }
        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredPersonList() {
        return model.getFilteredTaskList();
    }
}
