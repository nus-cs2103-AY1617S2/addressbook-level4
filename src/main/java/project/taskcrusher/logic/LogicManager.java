package project.taskcrusher.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import project.taskcrusher.commons.core.ComponentManager;
import project.taskcrusher.commons.core.LogsCenter;
import project.taskcrusher.logic.commands.Command;
import project.taskcrusher.logic.commands.CommandResult;
import project.taskcrusher.logic.commands.exceptions.CommandException;
import project.taskcrusher.logic.parser.Parser;
import project.taskcrusher.model.Model;
import project.taskcrusher.model.event.ReadOnlyEvent;
import project.taskcrusher.model.task.ReadOnlyTask;
import project.taskcrusher.storage.Storage;

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

        ///to refresh the overdue status for events and tasks
        model.updateOverdueStatus();
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredPersonList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ObservableList<ReadOnlyEvent> getFilteredEventList() {
        return model.getFilteredEventList();
    }
}
