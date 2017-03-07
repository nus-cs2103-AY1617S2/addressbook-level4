package t16b4.yats.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import t16b4.yats.commons.core.ComponentManager;
import t16b4.yats.commons.core.LogsCenter;
import t16b4.yats.logic.commands.Command;
import t16b4.yats.logic.commands.CommandResult;
import t16b4.yats.logic.commands.exceptions.CommandException;
import t16b4.yats.logic.parser.Parser;
import t16b4.yats.model.Model;
import t16b4.yats.model.item.ReadOnlyItem;
import t16b4.yats.storage.Storage;

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
    public ObservableList<ReadOnlyItem> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }
}
