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


    @Override
    public CommandResult execute() throws CommandException {
        logger.info("-------[Executing ClearCommand] " + this.toString());
        if (typeToClear.equals("ev")) {
            executeClearEvent();
            return new CommandResult(CLEAR_EVENTS_SUCCESS);
        } else if (typeToClear.equals("ts")) {
            executeClearTask();
            return new CommandResult(CLEAR_TASKS_SUCCESS);
        } else {
            //clear all events and tasks if no type is specified
            executeClearAll();
            return new CommandResult(MESSAGE_SUCCESS);
        }

    }

    public void executeClearEvent() {
        logger.info("-------[Executing ClearEventCommand] " + this.toString());
        assert model != null;
        ReadOnlyWhatsLeft currState = model.getWhatsLeft();
        ModelManager.setPreviousState(currState);
        model.resetEvent();
        //store for undo operation
        model.storePreviousCommand("clear");
    }

    public void executeClearTask() {
        logger.info("-------[Executing ClearTaskCommand] " + this.toString());
        assert model != null;
        ReadOnlyWhatsLeft currState = model.getWhatsLeft();
        ModelManager.setPreviousState(currState);
        model.resetTask();
        //store for undo operation
        model.storePreviousCommand("clear");
    }

    public void executeClearAll() {
        logger.info("-------[Executing ClearAllCommand] " + this.toString());
        assert model != null;
        ReadOnlyWhatsLeft currState = model.getWhatsLeft();
        ModelManager.setPreviousState(currState);
        model.resetData(new WhatsLeft());
        //store for undo operation
        model.storePreviousCommand("clear");
    }
}
