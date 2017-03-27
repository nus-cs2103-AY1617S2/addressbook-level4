package todolist.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import todolist.commons.core.ComponentManager;
import todolist.commons.core.LogsCenter;
import todolist.logic.commands.Command;
import todolist.logic.commands.CommandResult;
import todolist.logic.commands.exceptions.CommandException;
import todolist.logic.parser.Parser;
import todolist.model.Model;
import todolist.model.task.ReadOnlyTask;
import todolist.storage.Storage;

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
