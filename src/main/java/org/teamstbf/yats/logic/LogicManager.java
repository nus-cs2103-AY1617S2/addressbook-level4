package org.teamstbf.yats.logic;

import java.util.logging.Logger;

import org.teamstbf.yats.commons.core.ComponentManager;
import org.teamstbf.yats.commons.core.LogsCenter;
import org.teamstbf.yats.logic.commands.Command;
import org.teamstbf.yats.logic.commands.CommandResult;
import org.teamstbf.yats.logic.commands.exceptions.CommandException;
import org.teamstbf.yats.logic.parser.Parser;
import org.teamstbf.yats.model.Model;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.storage.Storage;

import javafx.collections.ObservableList;

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
    public ObservableList<ReadOnlyEvent> getFilteredTaskList() {
	return model.getFilteredTaskList();
    }
}
