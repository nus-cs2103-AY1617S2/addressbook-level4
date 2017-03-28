package seedu.todolist.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.todolist.commons.core.ComponentManager;
import seedu.todolist.commons.core.Config;
import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.logic.commands.Command;
import seedu.todolist.logic.commands.CommandResult;
import seedu.todolist.logic.commands.exceptions.CommandException;
import seedu.todolist.logic.parser.Parser;
import seedu.todolist.model.Model;
import seedu.todolist.model.ReadOnlyToDoList;
import seedu.todolist.model.ToDoList;
import seedu.todolist.model.task.Task;
import seedu.todolist.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;
    private final Config config;
    private final Storage storage;
    private UndoManager undoManager;

    public LogicManager(Model model, Storage storage, Config config) {
        this.model = model;
        this.parser = new Parser();
        this.undoManager = new UndoManager(model);
        this.config = config;
        this.storage = storage;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model, undoManager, config, storage);
        if (command.isMutating()) {
            undoManager.addMutatingTask(command);
        }
        CommandResult result = command.execute();
        if (command.isMutating()) {
            ReadOnlyToDoList currentState = new ToDoList(model.getToDoList());
            undoManager.setCurrentStateForMutatingTask(currentState);
        }
        return result;
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}
