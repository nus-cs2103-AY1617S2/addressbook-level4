package project.taskcrusher.logic.commands;

import java.util.List;

import project.taskcrusher.commons.core.Messages;
import project.taskcrusher.commons.core.UnmodifiableObservableList;
import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.logic.commands.exceptions.CommandException;
import project.taskcrusher.logic.parser.ParserUtil;
import project.taskcrusher.model.event.Event;
import project.taskcrusher.model.event.Location;
import project.taskcrusher.model.event.ReadOnlyEvent;
import project.taskcrusher.model.event.Timeslot;
import project.taskcrusher.model.event.UniqueEventList.EventNotFoundException;
import project.taskcrusher.model.shared.Priority;
import project.taskcrusher.model.task.Deadline;
import project.taskcrusher.model.task.ReadOnlyTask;
import project.taskcrusher.model.task.Task;
import project.taskcrusher.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0163962X
public class SwitchCommand extends Command {

    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches a task or event identified by"
            + "the index number used in the last event listing and number of the timeslot\n" + "to the opposite type\n"
            + "Parameters: FLAG (t or e) INDEX (must be positive integers) NEW TIMESLOT OR DEADLINE\n" + "Example: "
            + COMMAND_WORD + " e 1 d/tomorrow";

    public static final String MESSAGE_SWITCH_TASK_SUCCESS = "Switched the following task to event: %1$s";
    public static final String MESSAGE_SWITCH_EVENT_SUCCESS = "Switched the following event to task: %1$s";

    public final int targetIndex;
    public final String flag;
    public final String date;

    public SwitchCommand(String flag, int targetIndex, String date) {
        this.flag = flag;
        this.targetIndex = targetIndex;
        this.date = date;
    }

    @Override
    public CommandResult execute() throws CommandException {

        if (flag.equals(Task.TASK_FLAG)) {

            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

            if (lastShownList.size() < targetIndex) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyTask taskToSwitch = lastShownList.get(targetIndex - 1);

            try {
                List<Timeslot> timeslots = ParserUtil.parseAsTimeslots(date);
                model.switchTaskToEvent(taskToSwitch, new Event(taskToSwitch.getName(), timeslots,
                        new Location(Location.NO_LOCATION), taskToSwitch.getDescription(), taskToSwitch.getTags()));
            } catch (IllegalValueException ive) {
                throw new CommandException(ive.getMessage());
            } catch (TaskNotFoundException tnfe) {
                throw new CommandException(tnfe.getMessage());
            }

            return new CommandResult(String.format(MESSAGE_SWITCH_TASK_SUCCESS, taskToSwitch));

        } else {

            assert flag.equals(Event.EVENT_FLAG);

            UnmodifiableObservableList<ReadOnlyEvent> lastShownList = model.getFilteredEventList();

            if (lastShownList.size() < targetIndex) {
                throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
            }

            ReadOnlyEvent eventToSwitch = lastShownList.get(targetIndex - 1);

            try {
                model.switchEventToTask(eventToSwitch, new Task(eventToSwitch.getName(), new Deadline(date),
                        new Priority(Priority.NO_PRIORITY), eventToSwitch.getDescription(), eventToSwitch.getTags()));
            } catch (IllegalValueException ive) {
                throw new CommandException(ive.getMessage());
            } catch (EventNotFoundException enfe) {
                throw new CommandException(enfe.getMessage());
            }

            return new CommandResult(String.format(MESSAGE_SWITCH_EVENT_SUCCESS, eventToSwitch));

        }

    }

}
