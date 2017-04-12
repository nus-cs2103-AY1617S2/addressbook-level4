package seedu.todolist.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.todolist.commons.core.ComponentManager;
import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.logic.commands.Command;
import seedu.todolist.logic.commands.CommandResult;
import seedu.todolist.logic.commands.exceptions.CommandException;
import seedu.todolist.logic.parser.Parser;
import seedu.todolist.model.Model;
import seedu.todolist.model.todo.ReadOnlyTodo;
import seedu.todolist.storage.Storage;

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
    public ObservableList<ReadOnlyTodo> getFilteredTodoList() {
        return model.getFilteredTodoList();
    }
}
