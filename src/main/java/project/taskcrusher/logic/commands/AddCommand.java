package project.taskcrusher.logic.commands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.logic.commands.exceptions.CommandException;
import project.taskcrusher.model.event.Event;
import project.taskcrusher.model.event.Location;
import project.taskcrusher.model.event.ReadOnlyEvent;
import project.taskcrusher.model.event.Timeslot;
import project.taskcrusher.model.event.UniqueEventList;
import project.taskcrusher.model.shared.Description;
import project.taskcrusher.model.shared.Name;
import project.taskcrusher.model.shared.Priority;
import project.taskcrusher.model.tag.Tag;
import project.taskcrusher.model.tag.UniqueTagList;
import project.taskcrusher.model.task.Deadline;
import project.taskcrusher.model.task.Task;
import project.taskcrusher.model.task.UniqueTaskList;

//@@author A0163962X
/**
 * Adds a task to user inbox.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task or an event to the active list.\n"
            + "For events: " + COMMAND_WORD + " " + Event.EVENT_FLAG
            + " NAME d/START_DATE to END_DATE [l/LOCATION] [//DESCRIPTION] [t/TAG]\n" + "For tasks: " + COMMAND_WORD
            + " " + Task.TASK_FLAG + " NAME [d/DEADLINE] [p/PRIORITY] [//DESCRIPTION] [t/TAG]";

    public static final String MESSAGE_TASK_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_EVENT_SUCCESS = "New event added: %1$s";

    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the active list";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the active list";

    public static final String MESSAGE_EVENT_CLASHES = "This event clashes with a preexisting event";

    private final Task taskToAdd;
    private final Event eventToAdd;
    public boolean force = false;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String name, String deadline, String priority, String description, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.taskToAdd = new Task(new Name(name), new Deadline(deadline), new Priority(priority),
                new Description(description), new UniqueTagList(tagSet));
        this.eventToAdd = null;
    }

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String name, List<Timeslot> timeslots, String priority, String location, String description,
            Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.eventToAdd = new Event(new Name(name), new ArrayList<Timeslot>(timeslots), new Priority(priority),
                new Location(location), new Description(description), new UniqueTagList(tagSet));
        this.taskToAdd = null;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        assert !(eventToAdd == null && taskToAdd == null);
        try {
            if (eventToAdd != null) {

                List<? extends ReadOnlyEvent> preexistingEvents = model.getUserInbox().getEventList();
                if (!force && eventToAdd.hasOverlappingEvent(preexistingEvents)) { // allow
                                                                                   // for
                                                                                   // force
                                                                                   // adding
                    throw new CommandException(MESSAGE_EVENT_CLASHES);
                }

                model.addEvent(eventToAdd);
                return new CommandResult(String.format(MESSAGE_EVENT_SUCCESS, eventToAdd));
            } else {
                model.addTask(taskToAdd);
                return new CommandResult(String.format(MESSAGE_TASK_SUCCESS, taskToAdd));
            }
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (UniqueEventList.DuplicateEventException e) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

    }

}
