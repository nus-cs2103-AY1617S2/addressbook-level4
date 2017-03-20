package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * Deletes an existing tag in the task manager.
 */
public class DeleteTagCommand extends Command {

    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes an existing tag in the task manager "
            + "Parameters: <tag_name>\n" + "Example: " + COMMAND_WORD + " work";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";

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
            ReadOnlyTask taskToEdit = lastShownList.get(index);
            boolean containsOldTag = false;
            Set<Tag> newTagList = new HashSet<Tag>();
            Set<Tag> tags = taskToEdit.getTags().toSet();
            for (Tag tag : tags) {
                if (tag.getTagName().toLowerCase()
                        .equals(oldTag.getTagName().toLowerCase())) {
                    containsOldTag = true;
                } else {
                    newTagList.add(tag);
                }
            }

            if (containsOldTag) {
                // TODO: Change Task constructor to TaskWithoutDeadline() or
                // TaskWithDeadline() based on task type
                Task newTask = new Task(taskToEdit.getName(),
                        new UniqueTagList(newTagList), taskToEdit.isDone());
                try {
                    model.updateTask(index, newTask);
                } catch (UniqueTaskList.DuplicateTaskException dpe) {
                    throw new CommandException(
                            EditCommand.MESSAGE_DUPLICATE_PERSON);
                }

            }
        }

        return new CommandResult(
                String.format(MESSAGE_DELETE_TAG_SUCCESS, oldTag.getTagName()));
    }
}
