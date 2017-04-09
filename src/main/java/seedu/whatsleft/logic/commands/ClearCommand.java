package seedu.whatsleft.logic.commands;

import java.util.logging.Logger;

import seedu.whatsleft.commons.core.LogsCenter;
import seedu.whatsleft.logic.commands.exceptions.CommandException;
import seedu.whatsleft.model.ModelManager;
import seedu.whatsleft.model.ReadOnlyWhatsLeft;
import seedu.whatsleft.model.WhatsLeft;

//@@author A0148038A
/**
 * Clears events, tasks or everything in WhatsLeft.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": clear all events/tasks in WhatsLeft\n"
            + "Parameter: type of the activities to clear\n"
            + "If no type is specified, the whole WhatsLeft will be cleared\n"
            + "Example: " + COMMAND_WORD + " ev\n"
            + "Example: " + COMMAND_WORD + "";
    public static final String MESSAGE_SUCCESS = "WhatsLeft has been cleared!";
    public static final String CLEAR_EVENTS_SUCCESS = "Event list in WhatsLeft has been cleared!";
    public static final String CLEAR_TASKS_SUCCESS = "Task list in WhatsLeft has been cleared!";

    public final String typeToClear;
    private final Logger logger = LogsCenter.getLogger(ClearCommand.class);

    public ClearCommand(String typeToClear) {
        assert typeToClear.equals("") || typeToClear.equals("ev") || typeToClear.equals("ts");
        this.typeToClear = typeToClear;
    }

    //@@author A0148038A
    @Override
    public CommandResult execute() throws CommandException {
        logger.info("-------[Executing ClearCommand] " + this.toString());
        if (typeToClear.equals("ev")) {
            return clearingEvents();
        } else if (typeToClear.equals("ts")) {
            clearingTasks();
            return new CommandResult(CLEAR_TASKS_SUCCESS);
        } else {
            return clearingAll();
        }

    }

    //@@author A0148038A
    /**
     * Performs the clearing of all events and tasks
     * @return CommandResult on successful clear all
     */
    private CommandResult clearingAll() {
        //clear all events and tasks if no type is specified
        logger.info("-------[Executing ClearAllCommand] " + this.toString());
        assert model != null;
        ReadOnlyWhatsLeft currState = model.getWhatsLeft();
        ModelManager.setPreviousState(currState);
        model.resetData(new WhatsLeft());
        //store for undo operation
        model.storePreviousCommand("clear");
        return new CommandResult(MESSAGE_SUCCESS);
    }

    //@@author A0148038A
    /**
     * Performs the clearing of all tasks
     * @return CommandResult on successful clear all tasks
     */
    private void clearingTasks() {
        logger.info("-------[Executing ClearTaskCommand] " + this.toString());
        assert model != null;
        ReadOnlyWhatsLeft currState = model.getWhatsLeft();
        ModelManager.setPreviousState(currState);
        model.resetTask();
        //store for undo operation
        model.storePreviousCommand("clear");
    }

    /**
     * Performs the clearing of all events
     * @return CommandResult on successful clear all events
     */
    private CommandResult clearingEvents() {
        logger.info("-------[Executing ClearEventCommand] " + this.toString());
        assert model != null;
        ReadOnlyWhatsLeft currState = model.getWhatsLeft();
        ModelManager.setPreviousState(currState);
        model.resetEvent();
        //store for undo operation
        model.storePreviousCommand("clear");
        return new CommandResult(CLEAR_EVENTS_SUCCESS);
    }
}
