package project.taskcrusher.logic.commands;

import project.taskcrusher.commons.core.Messages;
import project.taskcrusher.commons.core.UnmodifiableObservableList;
import project.taskcrusher.logic.commands.exceptions.CommandException;
import project.taskcrusher.model.event.ReadOnlyEvent;

//@@author A0163962X
public class ConfirmCommand extends Command {

    public static final String COMMAND_WORD = "confirm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Confirms a timeslot of an event identified by "
            + "the index number used in the last event listing and number of the timeslot\n"
            + "Parameters: FLAG (must be e) INDEX SLOT (last 2 must be positive integers)\n" + "Example: "
            + COMMAND_WORD + " e 1 1";

    public static final String MESSAGE_CONFIRM_EVENT_SUCCESS = "Confirmed timeslot for: %1$s";

    public final int targetIndex;
    public final int targetSlot;

    public ConfirmCommand(int targetIndex, int targetSlot) {
        this.targetIndex = targetIndex;
        this.targetSlot = targetSlot;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyEvent> lastShownList = model.getFilteredEventList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        ReadOnlyEvent eventToConfirm = lastShownList.get(targetIndex - 1);

        if (eventToConfirm.getTimeslots().size() < targetSlot) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_SLOT_DISPLAYED_INDEX);
        }

        model.confirmEventTime(targetIndex - 1, targetSlot - 1);

        return new CommandResult(String.format(MESSAGE_CONFIRM_EVENT_SUCCESS, eventToConfirm));

    }

}
