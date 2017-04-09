package seedu.tache.logic.commands;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.tache.commons.core.EventsCenter;
import seedu.tache.commons.events.ui.JumpToListRequestEvent;
import seedu.tache.commons.exceptions.IllegalValueException;

import seedu.tache.logic.commands.exceptions.CommandException;

import seedu.tache.model.recurstate.RecurState.RecurInterval;

import seedu.tache.model.tag.Tag;
import seedu.tache.model.tag.UniqueTagList;

import seedu.tache.model.task.DateTime;
import seedu.tache.model.task.Name;
import seedu.tache.model.task.Task;
import seedu.tache.model.task.UniqueTaskList;
import seedu.tache.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "add";
    public static final String SHORT_COMMAND_WORD = "a";
    public static final String TAG_SEPARATOR = "t/";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. \n"
            + "Parameters: NAME [from <START DATE & TIME>] [to <END DATE & TIME>] [t/TAG1 TAG2...]\n"
            + "Example: " + COMMAND_WORD
            + " Orientation week camp from 25/7/16 0800 to 28/7/2016 0900 t/ HighPriority Events";

    public static final String MESSAGE_SUCCESS = "New task added: \n%1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String MESSAGE_TASK_NOT_FOUND = "%1$s no longer exists in the task manager.";

    private final Task toAdd;
    private boolean commandSuccess;

    //@@author A0150120H
    /**
     * Creates an AddCommand using raw name, start date & time, end date & time, and tags values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String nameStr, Optional<String> startDateTimeStr, Optional<String> endDateTimeStr,
            Set<String> tagsStr) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tagsStr) {
            tagSet.add(new Tag(tagName));
        }
        Name name = new Name(nameStr);

        Optional<DateTime> startDateTime = Optional.empty();
        Optional<DateTime> endDateTime = Optional.empty();
        if (startDateTimeStr.isPresent()) {
            startDateTime = Optional.of(new DateTime(startDateTimeStr.get()));
        }
        if (endDateTimeStr.isPresent()) {
            endDateTime = Optional.of(new DateTime(endDateTimeStr.get()));
        }

        UniqueTagList tagList = new UniqueTagList(tagSet);
        this.toAdd = new Task(name, startDateTime, endDateTime, tagList, true, false,
                                    RecurInterval.NONE, new ArrayList<Date>());
        commandSuccess = false;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            commandSuccess = true;
            undoHistory.push(this);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(model.getFilteredTaskListIndex(toAdd)));
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

    @Override
    public boolean isUndoable() {
        return commandSuccess;
    }

    @Override
    public String undo() throws CommandException {
        try {
            model.deleteTask(toAdd);
            return String.format(MESSAGE_SUCCESS, toAdd);
        } catch (TaskNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_TASK_NOT_FOUND, toAdd));
        }
    }
}

