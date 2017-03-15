package typetask.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import typetask.commons.core.ComponentManager;
import typetask.commons.core.LogsCenter;
import typetask.logic.commands.Command;
import typetask.logic.commands.CommandResult;
import typetask.logic.commands.exceptions.CommandException;
import typetask.logic.parser.Parser;
import typetask.model.Model;
import typetask.model.task.ReadOnlyTask;
import typetask.storage.Storage;

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
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}
