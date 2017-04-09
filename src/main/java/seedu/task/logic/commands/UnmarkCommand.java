package seedu.task.logic.commands;

import java.util.List;

import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.tag.UniqueTagList.DuplicateTagException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

//@@author A0163848R-reused
/**
 * Command that marks task as incomplete
 */
public class UnmarkCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks as incomplete the task identified "
            + "by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    //@@author A0164466X
    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked task as incomplete: %1$s";
    //@@author
    public static final String MESSAGE_DUPLICATE_TASK = "This task is already incomplete.";

    private final int filteredTaskListIndex;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public UnmarkCommand(int filteredTaskListIndex) {
        assert filteredTaskListIndex > 0;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        ReadOnlyTask editedTask = createUnmarkedTask(taskToEdit);

        try {
            model.updateTask(filteredTaskListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToEdit));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited to be complete.
     */
    private static ReadOnlyTask createUnmarkedTask(ReadOnlyTask taskToEdit) {
        assert taskToEdit != null;

        UniqueTagList updatedTags =
                taskToEdit
                .getTags()
                .except(UniqueTagList.build(
                        Tag.TAG_COMPLETE,
                        Tag.TAG_INCOMPLETE));

        try {
            updatedTags.add(new Tag(Tag.TAG_INCOMPLETE));
        } catch (DuplicateTagException e) {
            e.printStackTrace();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        return new Task(taskToEdit.getName(),
                taskToEdit.getStartDate(), taskToEdit.getEndDate(), taskToEdit.getGroup(), updatedTags);
    }
}
