package seedu.task.logic.commands;

import static seedu.task.commons.core.Messages.MESSSAGE_INVALID_TIMING_ORDER;

import java.util.HashSet;
import java.util.Set;

import seedu.task.commons.exceptions.IllegalTimingOrderException;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.Model;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Description;
import seedu.task.model.task.Priority;
import seedu.task.model.task.RecurringFrequency;
import seedu.task.model.task.RecurringTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.Timing;
import seedu.task.model.task.UniqueTaskList;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: TASK_NAME p/PRIORITY_LEVEL sd/DATE ed/DATE [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " Study for midterm p/1 sd/04/03/2017 ed/06/03/2017 t/study t/midterm";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;
    private RecurringTask toAddRecur = null;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String priority, String startTiming, String endTiming, String recurFreq, Set<String> tags)
            throws IllegalValueException, IllegalTimingOrderException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        Description description = new Description(name);
        Priority pri = new Priority(priority);
        Timing startTime = new Timing(startTiming);
        Timing endTime = new Timing(endTiming);
        UniqueTagList tagList = new UniqueTagList(tagSet);
        boolean recurring = (recurFreq != null);

        this.toAdd = new Task(
                description,
                pri,
                startTime,
                endTime,
                tagList,
                recurring
                );

        if (recurFreq != null) {
            this.toAddRecur = new RecurringTask(
                    description,
                    pri,
                    startTime,
                    endTime,
                    tagList,
                    new RecurringFrequency(recurFreq)
                    );
        }

        if (!Timing.checkTimingOrder(toAdd.getStartTiming(), toAdd.getEndTiming())) {
            throw new IllegalTimingOrderException(MESSSAGE_INVALID_TIMING_ORDER);
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            if (toAddRecur != null) {
                Model.recurringTaskList.add(toAddRecur);
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

}
