package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.ReadOnlyTask.TaskType;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskWithDeadline;
import seedu.address.model.task.TaskWithoutDeadline;
import seedu.address.model.task.UniqueTaskList;

/**
 * Renames an existing tag in the task manager.
 */
public class RenameTagCommand extends Command {

    public static final String COMMAND_WORD = "renametag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Renames an existing tag in the task manager "
            + "Parameters: <tag_name> <new_tag_name>\n" + "Example: "
            + COMMAND_WORD + " parttime fulltime";

    public static final String MESSAGE_RENAME_TAG_SUCCESS = "Renamed Tag: %1$s to %2$s";

    private final Tag oldTag;
    private final Tag newTag;

    /**
     * @param oldTagName
     *            is the name of the tag that will be renamed
     * @param newTagName
     *            is the name of the replacement tag
     */
    public RenameTagCommand(String oldTagName, String newTagName)
            throws IllegalValueException {
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
                Task newTask = null;
                if (taskToEdit.getTaskType() == TaskType.TaskWithNoDeadline) {
                    newTask = new TaskWithoutDeadline(taskToEdit.getName(),
                            new UniqueTagList(newTagList), taskToEdit.isDone());
                } else {
                    try {
                        newTask = new TaskWithDeadline(taskToEdit);
                        newTask.setTags(new UniqueTagList(newTagList));
                    } catch (IllegalValueException e) {
                        System.exit(1);
                    }
                }
                try {
                    model.updateTask(index, newTask);
                } catch (UniqueTaskList.DuplicateTaskException dpe) {
                    throw new CommandException(
                            EditCommand.MESSAGE_DUPLICATE_PERSON);
                }

            }
        }

        return new CommandResult(String.format(MESSAGE_RENAME_TAG_SUCCESS,
                oldTag.getTagName(), newTag.getTagName()));
    }
}
