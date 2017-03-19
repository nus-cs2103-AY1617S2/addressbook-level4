package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Description;
import seedu.address.model.task.EndTime;
import seedu.address.model.task.Event;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Title;
import seedu.address.model.task.UniqueEventList;
import seedu.address.model.task.UrgencyLevel;
import seedu.address.model.task.Venue;

/**
 * Adds a Event to the address book.
 */
public class AddEventCommand extends AddCommand {


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": ";

    public static final String MESSAGE_SUCCESS = "New Event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This Event already exists in the to-do list";

    private final Event toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddEventCommand(String title, Optional<String> venue, String starttime, String endtime, 
            Optional<String> urgencyLevel, Optional<String> description, Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        Title tempTitle = new Title(title);
        Venue tempVenue = venue.isPresent()? new Venue(venue.get()) : null;
        StartTime tempStartTime = new StartTime(starttime);
        EndTime tempEndTime = new EndTime(endtime);
        UrgencyLevel tempUrgencyLevel = urgencyLevel.isPresent()? new UrgencyLevel(urgencyLevel.get()) : null;
        Description tempDescription = description.isPresent()? new Description(description.get()) : null;
        UniqueTagList tagList = new UniqueTagList(tagSet);

        this.toAdd = new Event(tempTitle, tempVenue, tempStartTime, tempEndTime, tempUrgencyLevel, tempDescription, false, tagList);
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
