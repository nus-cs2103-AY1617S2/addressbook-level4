package w10b3.todolist.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import w10b3.todolist.commons.core.ComponentManager;
import w10b3.todolist.commons.core.LogsCenter;
import w10b3.todolist.logic.commands.Command;
import w10b3.todolist.logic.commands.CommandResult;
import w10b3.todolist.logic.commands.exceptions.CommandException;
import w10b3.todolist.logic.parser.Parser;
import w10b3.todolist.model.Model;
import w10b3.todolist.model.task.ReadOnlyTask;
import w10b3.todolist.storage.Storage;

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
    // @@ A0143648Y
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredEventList() {
        return model.getFilteredEventList();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredFloatList() {
        return model.getFilteredFloatList();
    }
}
