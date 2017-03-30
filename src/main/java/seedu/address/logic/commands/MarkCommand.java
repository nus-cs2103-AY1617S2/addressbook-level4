package seedu.address.logic.commands;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.model.task.Time;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

//@@author A0164394Y

/**
 * Finds and lists all tasks in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the task completed or not completed \n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " task_number completed OR not_completed";

    private final Set<String> keywords;

    public MarkCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        
        String[] parameters = keywords.toArray(new String[keywords.size()]);
        int filteredTaskListIndex = Integer.parseInt(parameters[0]);
        
        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task editedTask = createEditedTask(taskToEdit, parameters[1]);
        
        try {
            model.markTask(filteredTaskListIndex,editedTask);
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
                                             String status) {
        assert taskToEdit != null;
        int flag;
        if (status.equals("completed"))
            flag = 1;
        else
            flag = 0;
        Status stat = new Status(flag);
        Name updatedName = taskToEdit.getName();
        Time updatedTime = taskToEdit.getTime();
        Status updatedStatus = stat;
        Priority updatedPriority = taskToEdit.getPriority();
        UniqueTagList updatedTags = taskToEdit.getTags();
        return new Task(updatedName, updatedTime, updatedPriority, updatedTags, updatedStatus);
    }
}
