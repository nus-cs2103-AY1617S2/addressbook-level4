package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Description;
import seedu.address.model.person.EndTime;
import seedu.address.model.person.Event;
import seedu.address.model.person.StartDate;
import seedu.address.model.person.Location;
import seedu.address.model.person.Priority;
import seedu.address.model.person.StartTime;
import seedu.address.model.person.EndDate;
import seedu.address.model.person.UniqueActivityList;
import seedu.address.model.person.UniqueEventList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Adds an activity to WhatsLeft.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "add";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to WhatsLeft. "
            + "Parameters: DESCRIPTION [st/STARTTIME] sd/STARTDATE [et/ENDTIME] [ed/ENDDATE] [l/LOCATION] [t/TAG]...\n"
            + "Event must have sd/STARTDATE"
            + "Example: " + COMMAND_WORD
            + " Enrichment Talk st/18:30 sd/2017-03-22 et/20:00 l/LT28 4 t/talk";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in WhatsLeft";

    private final Event toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */

    public AddEventCommand(String description, String startTime, String startDate, String endTime,
            String endDate, String location, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Event(
                new Description(description),
                new StartDate(startDate),
                new EndDate(endDate),
                new StartTime(startTime),
                new EndTime(endTime),
                new Location(location),
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addEvent(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueEventList.DuplicateEventException e) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

    }

}
