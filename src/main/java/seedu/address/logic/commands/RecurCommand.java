package seedu.address.logic.commands;

import java.time.LocalDate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.ui.JumpToCalendarEventEvent;
import seedu.address.commons.events.ui.JumpToEventListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyWhatsLeft;
import seedu.address.model.person.EndDate;
import seedu.address.model.person.Event;
import seedu.address.model.person.ReadOnlyEvent;
import seedu.address.model.person.StartDate;
import seedu.address.model.person.UniqueEventList.DuplicateEventException;

public class RecurCommand extends Command {

    public final int targetIndex;
    public final String frequency;
    public final int occur;

    public static final String COMMAND_WORD = "recur";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": make an event a recurring one.\n"
            + "Parameters: INDEX (must be a positive integer) FREQUENCY (daily or weekly) NUMBER OF TIMES"
            + " (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 5 daily 3";
    public static final String MESSAGE_ADD_RECUR_ACTIVITY_SUCCESS = "This %1$s recurring activity has"
            + " been added to WhatsLeft";
    public static final String MESSAGE_DUPLICATE_ACTIVITY = "Cannot add Activity due to duplicate found";

    // @@author A0110491U
    public RecurCommand(int targetIndex, String frequency, int occur) {
        this.targetIndex = targetIndex;
        this.frequency = frequency;
        this.occur = occur;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyEvent> lastShownEventList = model.getFilteredEventList();
        ReadOnlyWhatsLeft currState = model.getWhatsLeft();
        ModelManager.setPreviousState(currState);

        if (lastShownEventList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }
        EventsCenter.getInstance().post(new JumpToEventListRequestEvent(targetIndex - 1));
        EventsCenter.getInstance().post(new JumpToCalendarEventEvent(lastShownEventList.get(targetIndex - 1)));
        ReadOnlyEvent selected = lastShownEventList.get(targetIndex - 1);
        LocalDate startdate = selected.getStartDate().value;
        LocalDate enddate = selected.getEndDate().value;
        LocalDate nextsd = startdate;
        LocalDate nexted = enddate;
        if (frequency.equals("daily")) {
            for (int i = 0; i < occur; i++) {
                System.out.println(i);
                nextsd = nextsd.plusDays(1);
                nexted = nexted.plusDays(1);
                Event nextOccur = new Event(selected.getDescription(), selected.getStartTime(),
                        new StartDate(nextsd), selected.getEndTime(), new EndDate(nexted),
                        selected.getLocation(), selected.getTags());
                try {
                    model.addEvent(nextOccur);
                } catch (DuplicateEventException e) {
                    throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
                }
            }
            model.storePreviousCommand("add");
            return new CommandResult(String.format(MESSAGE_ADD_RECUR_ACTIVITY_SUCCESS, selected));
        } else if (frequency.equals("weekly")) {
            for (int i = 0; i < occur; i++) {
                nextsd = nextsd.plusDays(7);
                nexted = nexted.plusDays(7);
                Event nextOccur = new Event(selected.getDescription(), selected.getStartTime(),
                        new StartDate(nextsd), selected.getEndTime(), new EndDate(nexted),
                        selected.getLocation(), selected.getTags());
                try {
                    model.addEvent(nextOccur);
                } catch (DuplicateEventException e) {
                    throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
                }
            }
            model.storePreviousCommand("add");
            return new CommandResult(String.format(MESSAGE_ADD_RECUR_ACTIVITY_SUCCESS, selected));
        }
        return new CommandResult(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
    //@@author
}


