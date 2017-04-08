package t09b1.today.logic.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import t09b1.today.commons.exceptions.IllegalValueException;
import t09b1.today.logic.commands.exceptions.CommandException;
import t09b1.today.model.tag.Tag;
import t09b1.today.model.tag.UniqueTagList;
import t09b1.today.model.task.ReadOnlyTask;
import t09b1.today.model.task.Task;
import t09b1.today.model.task.UniqueTaskList;

//@@author A0093999Y
/**
 * Deletes an existing tag in the task manager.
 */
public class DeleteTagCommand extends Command {

    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";
    public static final String MESSAGE_SUCCESS_STATUS_BAR = "Tag deleted successfully.";

    private final Tag oldTag;

    /**
     * @param oldTagName
     *            is the name of the tag to be deleted
     */
    public DeleteTagCommand(String oldTagName) throws IllegalValueException {
        assert oldTagName != null;

        this.oldTag = new Tag(oldTagName);
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredListToShowAll();
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        for (int index = 0; index < lastShownList.size(); index++) {
            removeTagFromTask(lastShownList, index);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, oldTag.getTagName()),
                MESSAGE_SUCCESS_STATUS_BAR);
    }

    /**
     * Takes a task from the list, deletes the designated tag, and updates it to
     * model
     * 
     * @param lastShownList
     *            list from model
     * @param index
     *            index of task in model
     * @throws CommandException
     */
    private void removeTagFromTask(List<ReadOnlyTask> lastShownList, int index) throws CommandException {
        ReadOnlyTask taskToEdit = lastShownList.get(index);
        boolean containsOldTag = false;
        Set<Tag> newTagList = new HashSet<Tag>();
        Set<Tag> tags = taskToEdit.getTags().toSet();
        for (Tag tag : tags) {
            if (tag.getTagName().equals(oldTag.getTagName())) {
                containsOldTag = true;
            } else {
                newTagList.add(tag);
            }
        }

        if (containsOldTag) {
            try {
                Task newTask = Task.createTask(taskToEdit.getName(), new UniqueTagList(newTagList),
                        taskToEdit.getDeadline(), taskToEdit.getStartingTime(), taskToEdit.isDone(),
                        taskToEdit.isManualToday());
                model.updateTask(index, newTask);
            } catch (UniqueTaskList.DuplicateTaskException dpe) {
                throw new CommandException(EditCommand.MESSAGE_DUPLICATE_TASK);
            } catch (IllegalValueException e) {
                // Should not happen
                throw new CommandException(e.getMessage());
            }

        }
    }
}
