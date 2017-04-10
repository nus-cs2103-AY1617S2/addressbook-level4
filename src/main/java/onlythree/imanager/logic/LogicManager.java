package onlythree.imanager.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import onlythree.imanager.commons.core.ComponentManager;
import onlythree.imanager.commons.core.LogsCenter;
import onlythree.imanager.logic.commands.Command;
import onlythree.imanager.logic.commands.CommandResult;
import onlythree.imanager.logic.commands.exceptions.CommandException;
import onlythree.imanager.logic.parser.Parser;
import onlythree.imanager.model.Model;
import onlythree.imanager.model.task.ReadOnlyTask;
import onlythree.imanager.storage.Storage;

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
