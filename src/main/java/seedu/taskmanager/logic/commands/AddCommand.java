package seedu.taskmanager.logic.commands;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.logic.commands.exceptions.CommandException;
import seedu.taskmanager.model.tag.Tag;
import seedu.taskmanager.model.tag.UniqueTagList;
import seedu.taskmanager.model.task.Description;
import seedu.taskmanager.model.task.EndDate;
import seedu.taskmanager.model.task.StartDate;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.Title;
import seedu.taskmanager.model.task.UniqueTaskList;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: TITLE [s/STARTDATE] [e/ENDDATE] [d/DESCRIPTION]  [#TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " Submit report s/01/01/2011 e/02/02/2022 d/A great report is on its way #friends #owesMoney";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";
    // @@author A0140032E
    public static final String MESSAGE_DATE_ORDER_CONSTRAINTS = "Start Date should be earlier or same as End Date";
    // @@author
    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String title, Optional<String> startDate, Optional<String> endDate,
            Optional<String> description, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        // @@author A0140032E
        if (startDate.isPresent() && endDate.isPresent() &&
                new StartDate(startDate.get()).after(new EndDate(endDate.get()))) {
            throw new IllegalValueException(MESSAGE_DATE_ORDER_CONSTRAINTS);
        }
        this.toAdd = new Task(
                new Title(title),
                startDate.isPresent() && !startDate.get().trim().equals("") ?
                        Optional.of(new StartDate(startDate.get())) : Optional.empty(),
                endDate.isPresent() && !endDate.get().trim().equals("") ?
                        Optional.of(new EndDate(endDate.get())) : Optional.empty(),
                description.isPresent() && !description.get().trim().equals("") ?
                        Optional.of(new Description(description.get())) : Optional.empty(),
                new UniqueTagList(tagSet)
        );
        // @@author
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

}
