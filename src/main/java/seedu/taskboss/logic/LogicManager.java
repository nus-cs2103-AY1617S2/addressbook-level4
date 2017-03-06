package seedu.taskboss.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.taskboss.commons.core.ComponentManager;
import seedu.taskboss.commons.core.LogsCenter;
import seedu.taskboss.logic.commands.Command;
import seedu.taskboss.logic.commands.CommandResult;
import seedu.taskboss.logic.commands.exceptions.CommandException;
import seedu.taskboss.logic.parser.Parser;
import seedu.taskboss.model.Model;
import seedu.taskboss.model.task.ReadOnlyPerson;
import seedu.taskboss.storage.Storage;

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
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }
}
