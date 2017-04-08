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
 * Renames an existing tag in the task manager.
 */
public class RenameTagCommand extends Command {

    public static final String COMMAND_WORD = "renametag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Renames an existing tag in the task manager "
            + "Parameters: <tag_name> <new_tag_name>\n" + "Example: " + COMMAND_WORD + " parttime fulltime";

    public static final String MESSAGE_RENAME_TAG_SUCCESS = "Renamed Tag: %1$s to %2$s";
    public static final String MESSAGE_SUCCESS_STATUS_BAR = "Tag renamed successfully.";

    private final Tag oldTag;
    private final Tag newTag;

    /**
     * @param oldTagName
     *            is the name of the tag that will be renamed
     * @param newTagName
     *            is the name of the replacement tag
     */
    public RenameTagCommand(String oldTagName, String newTagName) throws IllegalValueException {
        assert oldTagName != null;
        assert newTagName != null;

        this.oldTag = new Tag(oldTagName);
        this.newTag = new Tag(newTagName);
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredListToShowAll();
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        for (int index = 0; index < lastShownList.size(); index++) {
            renameTagInTask(lastShownList, index);
        }

        return new CommandResult(String.format(MESSAGE_RENAME_TAG_SUCCESS, oldTag.getTagName(), newTag.getTagName()),
                MESSAGE_SUCCESS_STATUS_BAR);
    }

    /**
     * Takes a task from the list, replace the designated tags, and updates it
     * to model
     * 
     * @param lastShownList
     *            list from model
     * @param index
     *            index of task in model
     * @throws CommandException
     */
    private void renameTagInTask(List<ReadOnlyTask> lastShownList, int index) throws CommandException {
        ReadOnlyTask taskToEdit = lastShownList.get(index);
        boolean containsOldTag = false;
        Set<Tag> newTagList = new HashSet<Tag>();
        Set<Tag> tags = taskToEdit.getTags().toSet();
        for (Tag tag : tags) {
            if (tag.getTagName().equals(oldTag.getTagName())) {
                containsOldTag = true;
                newTagList.add(newTag);
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
