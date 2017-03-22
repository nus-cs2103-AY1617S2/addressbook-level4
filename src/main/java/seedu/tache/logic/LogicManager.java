package seedu.tache.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.tache.commons.core.ComponentManager;
import seedu.tache.commons.core.LogsCenter;
import seedu.tache.logic.commands.Command;
import seedu.tache.logic.commands.CommandResult;
import seedu.tache.logic.commands.exceptions.CommandException;
import seedu.tache.logic.parser.Parser;
import seedu.tache.model.Model;
import seedu.tache.model.task.ReadOnlyTask;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;

    public LogicManager(Model model) {
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
    //@@author A0139925U
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        model.updateFilteredListToShowUncompleted();
        return model.getFilteredTaskList();
    }

    //@@author A0142255M
    @Override
    public String getTimedTasks() {
        int count = 0;
        ObservableList<ReadOnlyTask> taskList = getFilteredTaskList();
        for (ReadOnlyTask task : taskList) {
            if (task.getStartDateTime().isPresent() || task.getEndDateTime().isPresent()) {
                count++;
            }
        }
        return Integer.toString(count);
    }

    @Override
    public String getFloatingTasks() {
        int count = 0;
        ObservableList<ReadOnlyTask> taskList = getFilteredTaskList();
        for (ReadOnlyTask task : taskList) {
            if (!task.getStartDateTime().isPresent() && !task.getEndDateTime().isPresent()) {
                count++;
            }
        }
        return Integer.toString(count);
    }

}
