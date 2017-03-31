package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyWhatsLeft;
import seedu.address.model.WhatsLeft;

//@@author A0148038A
/**
 * Clears WhatsLeft.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": clear all events/tasks in WhatsLeft"
            + "Parameter: type of the activities to clear"
            + "If no type is specified, the whole WhatsLeft will be cleared"
            + "clear/clear ev/clear ts\n"
            + "Example: " + COMMAND_WORD + " ev\n"
            + "Example: " + COMMAND_WORD + "";
    public static final String MESSAGE_SUCCESS = "WhatsLeft has been cleared!";

    public final String typeToClear;

    public ClearCommand(String typeToClear) {
        assert typeToClear.equals("") || typeToClear.equals("ev") || typeToClear.equals("ts");
        this.typeToClear = typeToClear;
    }


    @Override
    public CommandResult execute() throws CommandException {
        if (typeToClear.equals("")) {
            assert model != null;
            ReadOnlyWhatsLeft currState = model.getWhatsLeft();
            ModelManager.setPreviousState(currState);
            model.resetData(new WhatsLeft());
            //store for undo operation
            model.storePreviousCommand("clear");
            return new CommandResult(MESSAGE_SUCCESS);
        } else if (typeToClear.equals("ev")) {
            assert model != null;
            ReadOnlyWhatsLeft currState = model.getWhatsLeft();
            ModelManager.setPreviousState(currState);
            model.resetEvent();
            //store for undo operation
            model.storePreviousCommand("clear");
            return new CommandResult(MESSAGE_SUCCESS);
        } else if (typeToClear.equals("ts")) {
            assert model != null;
            ReadOnlyWhatsLeft currState = model.getWhatsLeft();
            ModelManager.setPreviousState(currState);
            model.resetTask();
            //store for undo operation
            model.storePreviousCommand("clear");
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_TYPE);
        }

    }
}
