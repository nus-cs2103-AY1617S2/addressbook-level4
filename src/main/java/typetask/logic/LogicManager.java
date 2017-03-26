package typetask.logic;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import typetask.commons.core.ComponentManager;
import typetask.commons.core.Config;
import typetask.commons.core.LogsCenter;
import typetask.commons.exceptions.DataConversionException;
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
    private final Storage storage;
    private final Config config;

    public LogicManager(Model model, Storage storage, Config config) {
        this.model = model;
        this.config = config;
        this.storage = storage;
        this.parser = new Parser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, IOException, DataConversionException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model, storage, config);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}
