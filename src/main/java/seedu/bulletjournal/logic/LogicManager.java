package seedu.bullletjournal.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.bullletjournal.commons.core.ComponentManager;
import seedu.bullletjournal.commons.core.LogsCenter;
import seedu.bullletjournal.logic.commands.Command;
import seedu.bullletjournal.logic.commands.CommandResult;
import seedu.bullletjournal.logic.commands.exceptions.CommandException;
import seedu.bullletjournal.logic.parser.Parser;
import seedu.bullletjournal.model.Model;
import seedu.bullletjournal.model.task.ReadOnlyTask;
import seedu.bullletjournal.storage.Storage;

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
}
