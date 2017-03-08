package savvytodo.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import savvytodo.commons.core.ComponentManager;
import savvytodo.commons.core.LogsCenter;
import savvytodo.logic.commands.Command;
import savvytodo.logic.commands.CommandResult;
import savvytodo.logic.commands.exceptions.CommandException;
import savvytodo.logic.parser.Parser;
import savvytodo.model.Model;
import savvytodo.model.task.ReadOnlyTask;
import savvytodo.storage.Storage;

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
