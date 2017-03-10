package t15b1.taskcrusher.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import t15b1.taskcrusher.commons.core.ComponentManager;
import t15b1.taskcrusher.commons.core.LogsCenter;
import t15b1.taskcrusher.logic.commands.Command;
import t15b1.taskcrusher.logic.commands.CommandResult;
import t15b1.taskcrusher.logic.commands.exceptions.CommandException;
import t15b1.taskcrusher.logic.parser.Parser;
import t15b1.taskcrusher.model.Model;
import t15b1.taskcrusher.model.task.ReadOnlyTask;
import t15b1.taskcrusher.storage.Storage;

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
    public ObservableList<ReadOnlyTask> getFilteredPersonList() {
        return model.getFilteredTaskList();
    }
}
