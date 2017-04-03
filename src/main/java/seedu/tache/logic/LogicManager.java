package seedu.tache.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import edu.emory.mathcs.backport.java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tache.commons.core.ComponentManager;
import seedu.tache.commons.core.Config;
import seedu.tache.commons.core.LogsCenter;
import seedu.tache.logic.commands.Command;
import seedu.tache.logic.commands.CommandResult;
import seedu.tache.logic.commands.exceptions.CommandException;
import seedu.tache.logic.parser.Parser;
import seedu.tache.model.Model;
import seedu.tache.model.task.ReadOnlyTask;
import seedu.tache.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;
    private Storage storage;
    private Config config;

    public LogicManager(Model model) {
        this.model = model;
        this.parser = new Parser();
    }

    public LogicManager(Model model, Storage storage, Config config) {
        this.model = model;
        this.storage = storage;
        this.config = config;
        this.parser = new Parser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        command.setStorage(storage);
        command.setConfig(config);
        return command.execute();
    }

    //@@author A0139925U
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        model.updateFilteredListToShowUncompleted();
        return model.getFilteredTaskList();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFullTaskList() {
        List<ReadOnlyTask> concatenated = new ArrayList<>();
        Collections.addAll(concatenated, model.getTaskManager().getTaskList().toArray());
        Collections.addAll(concatenated, model.getAllRecurringGhostTasks().toArray());
        return FXCollections.observableList(concatenated);
    }

    //@@author A0142255M
    @Override
    public String getFilteredTaskListType() {
        return model.getFilteredTaskListType();
    }

}
