package seedu.doist.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.doist.commons.core.ComponentManager;
import seedu.doist.commons.core.LogsCenter;
import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.CommandResult;
import seedu.doist.logic.commands.exceptions.CommandException;
import seedu.doist.logic.parser.Parser;
import seedu.doist.model.AliasListMapModel;
import seedu.doist.model.ConfigModel;
import seedu.doist.model.Model;
import seedu.doist.model.task.ReadOnlyTask;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final AliasListMapModel aliasModel;
    private final ConfigModel configModel;
    private final Parser parser;

    //@@author A0140887W
    public LogicManager(Model model, AliasListMapModel aliasModel, ConfigModel configModel) {
        this.model = model;
        this.aliasModel = aliasModel;
        this.configModel = configModel;
        this.parser = new Parser(aliasModel);
    }

    //@@author A0147980U
    @Override
    public CommandResult execute(String commandText) throws CommandException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model, aliasModel, configModel);

        CommandResult result = command.execute();
        if (result.isMutating) {
            model.saveCurrentToHistory();
        }
        return result;
    }

    //@@author
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    //@@author A0147980U
    public List<String> getAllCommandWords() {
        ArrayList<String> allCommandWords = new ArrayList<String>();
        Set<String> allDefaultCommandWords = aliasModel.getDefaultCommandWordSet();
        for (String defaultCommandWords : allDefaultCommandWords) {
            allCommandWords.addAll(aliasModel.getValidCommandList(defaultCommandWords));
        }
        return allCommandWords;
    }

    //@@author A0147620L
    public ArrayList<String> getAllNames() {
        return model.getAllNames();
    }
}
