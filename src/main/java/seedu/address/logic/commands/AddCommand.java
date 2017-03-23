package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Activity;
import seedu.address.model.person.ByDate;
import seedu.address.model.person.ByTime;
import seedu.address.model.person.Description;
import seedu.address.model.person.EndDate;
import seedu.address.model.person.EndTime;
import seedu.address.model.person.Event;
import seedu.address.model.person.Location;
import seedu.address.model.person.Priority;
import seedu.address.model.person.StartDate;
import seedu.address.model.person.StartTime;
import seedu.address.model.person.UniqueActivityList;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;


/**
 * Adds an activity to WhatsLeft.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an activity to WhatsLeft. "
            + "Parameters: DESCRIPTION p/PRIORITY l/LOCATION sd/STARTDATE ed/ENDDATE st/STARTTIME"
            + "et/ENDTIME bd/BYDATE bt/BYTIME \n"
            + "Event must have sd/STARTDATE, Task/Deadline must have p/PRIORITY \n"
            + "Example: " + COMMAND_WORD
            + " Project Discussion p/high l/discussion room t/formal";

    public static final String MESSAGE_SUCCESS = "New activity added: %1$s";
    public static final String MESSAGE_DUPLICATE_ACTIVITY = "This activity already exists in WhatsLeft";

    private final Event toAddEvent;
    private final Task toAddTask;
    //@@author A0110491U
    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */

    public AddCommand(String description, String priority, String starttime, String startdate, String endtime,
            String enddate, String bydate, String bytime, String location, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        if (priority != null) {
            this.toAddTask = new Task(
                    new Description(description),
                    new Priority(priority),
                    new ByDate(bydate),
                    new ByTime(bytime),
                    new Location(location),
                    new UniqueTagList(tagSet));
            this.toAddEvent = null;
        }

        if (startdate != null) {
            this.toAddEvent = new Event(
                    new Description(description),
                    new StartDate(startdate),
                    new EndDate(enddate),
                    new StartTime(starttime),
                    new EndTime(endtime),
                    new Location(location),
                    new UniqueTagList(tagSet));
            this.toAddTask = null;
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            if (toAddTask == null) {
                model.addEvent(toAddEvent);                
                return new CommandResult(String.format(MESSAGE_SUCCESS, toAddEvent));
            } else if (toAddEvent == null) {
                model.addTask(toAddTask);
                return new CommandResult(String.format(MESSAGE_SUCCESS, toAddTask));
            }
        } catch (UniqueEventList.DuplicateEventException || UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
        }

    }

}
