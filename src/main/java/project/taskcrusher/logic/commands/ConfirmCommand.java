package project.taskcrusher.logic.commands;

import project.taskcrusher.commons.core.Messages;
import project.taskcrusher.commons.core.UnmodifiableObservableList;
import project.taskcrusher.logic.commands.exceptions.CommandException;
import project.taskcrusher.model.event.ReadOnlyEvent;
//import project.taskcrusher.model.event.UniqueEventList.EventNotFoundException;

public class ConfirmCommand extends Command {

    public static final String COMMAND_WORD = "confirm";

    // TODO update this as well
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Confirms a timeslot of an event identified by "
            + "the index number used in the last event listing and number of the timeslot\n"
            + "Parameters: FLAG (must be e) INDEX SLOT (last 2 must be positive integers)\n" + "Example: "
            + COMMAND_WORD + " e 1 1";

    public static final String MESSAGE_CONFIRM_TASK_SUCCESS = "Confirmed timeslot for event: %1$s";

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
            throw new CommandException("Slot DNE" + Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        System.out.println("It works");

        // try {
        // model.confirmEvent(targetIndex - 1, targetSlot - 1);
        // } catch (EventNotFoundException pnfe) {
        // assert false : "The target person cannot be missing";
        // }

        return new CommandResult(String.format(MESSAGE_CONFIRM_TASK_SUCCESS, eventToConfirm));

    }

}
