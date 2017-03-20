package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyToDoList;
import seedu.address.model.ToDoList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Description;
import seedu.address.model.task.EndTime;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UrgencyLevel;
import seedu.address.model.task.Venue;

/**
 * Adds a Task to the address book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Task to the to-do list. "
            + "Parameters: TITLE @@VENUE from:STARTTIME to:ENDTIME **URGENCYLEVEL d:DESCRIPTION  ##[TAG]...\n"
            + "Example: " + COMMAND_WORD + " CS2103 Tutorial @@COM1-B110 from:March 8,10.00am to:March 8, 11.00am "
            + "**5 d:have to present V0.2 ##lesson ##project";

    public static final String MESSAGE_SUCCESS = "New Task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This Task already exists in the address book";

    private final Task toAdd;
    private ReadOnlyToDoList originalToDoList;
    private CommandResult commandResultToUndo;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String title, Optional<String> venue, Optional<String> starttime, Optional<String> endtime,
            Optional<String> urgencyLevel, Optional<String> description, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        Title tempTitle = new Title(title);
        Venue tempVenue = null;
        StartTime tempStartTime = null;
        EndTime tempEndTime = null;
        UrgencyLevel tempUrgencyLevel = null;
        Description tempDescription = null;

        if (venue.isPresent()) {
            tempVenue = new Venue(venue.get());
        }
        
        if (starttime.isPresent()) {
            tempStartTime = new StartTime(starttime.get());
        }
        
        if (endtime.isPresent()) {
            tempEndTime = new EndTime(endtime.get());
        }
        
        if (urgencyLevel.isPresent()) {
            tempUrgencyLevel = new UrgencyLevel(urgencyLevel.get());
        }
        
        if (description.isPresent()) {
            tempDescription = new Description(description.get());
        }

        this.toAdd = new Task(tempTitle, tempVenue, tempStartTime, tempEndTime, tempUrgencyLevel, tempDescription, new UniqueTagList(tagSet));
    }

    // @@author A0143648Y
    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            originalToDoList = new ToDoList(model.getToDoList());

            model.addTask(toAdd);
            commandResultToUndo = new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
            updateUndoLists();

            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

    @Override
    public void updateUndoLists() {
        if (previousToDoLists == null) {
            previousToDoLists = new ArrayList<ReadOnlyToDoList>(3);
            previousCommandResults = new ArrayList<CommandResult>(3);
        }
        if (previousToDoLists.size() >= 3) {
            previousToDoLists.remove(0);
            previousCommandResults.remove(0);
            previousToDoLists.add(originalToDoList);
            previousCommandResults.add(commandResultToUndo);
        } else {
            previousToDoLists.add(originalToDoList);
            previousCommandResults.add(commandResultToUndo);
        }
    }

}
