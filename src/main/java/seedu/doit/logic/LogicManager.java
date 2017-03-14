package seedu.doit.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.doit.commons.core.ComponentManager;
import seedu.doit.commons.core.LogsCenter;
import seedu.doit.logic.commands.Command;
import seedu.doit.logic.commands.CommandResult;
import seedu.doit.logic.commands.exceptions.CommandException;
import seedu.doit.logic.parser.Parser;
import seedu.doit.model.Model;
import seedu.doit.model.item.ReadOnlyEvent;
import seedu.doit.model.item.ReadOnlyFloatingTask;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.storage.Storage;

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
        this.logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = this.parser.parseCommand(commandText);
        command.setData(this.model);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return this.model.getFilteredTaskList();
    }

    @Override
    public ObservableList<ReadOnlyEvent> getFilteredEventList() {
        return this.model.getFilteredEventList();
    }

    @Override
    public ObservableList<ReadOnlyFloatingTask> getFilteredFloatingTaskList() {
        return this.model.getFilteredFloatingTaskList();
    }
}
