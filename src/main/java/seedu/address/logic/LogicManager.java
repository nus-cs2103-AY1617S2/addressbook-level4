package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Parser;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.storage.Storage;

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
        return model.getFilteredPersonList();
    }
    
    //@@author A0164889E
    @Override
    public ObservableList<ReadOnlyTask> getFilteredPersonListComplete() {
        return model.getFilteredPersonListComplete();
    }
    
    //@@author A0163848R
    @Override
    public ReadOnlyTaskManager getYTomorrow() {
        return model.getAddressBook();
    }
    
    @Override
    public void setYTomorrow(ReadOnlyTaskManager set) {
        model.resetData(set);
    }
    
    @Override
    public void importYTomorrow(ReadOnlyTaskManager add) {
        model.mergeYTomorrow(add);
    }
}
