package seedu.jobs.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.jobs.commons.core.ComponentManager;
import seedu.jobs.commons.core.LogsCenter;
import seedu.jobs.logic.calendar.CalendarManager;
import seedu.jobs.logic.commands.Command;
import seedu.jobs.logic.commands.CommandResult;
import seedu.jobs.logic.commands.exceptions.CommandException;
import seedu.jobs.logic.parser.Parser;
import seedu.jobs.model.Model;
import seedu.jobs.model.task.ReadOnlyTask;
import seedu.jobs.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;
    private final CalendarManager calendar;

    public LogicManager(Model model, Storage storage, CalendarManager calendar) {
        this.model = model;
        this.parser = new Parser();
        this.calendar = calendar;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        command.setCalendar(calendar);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}
