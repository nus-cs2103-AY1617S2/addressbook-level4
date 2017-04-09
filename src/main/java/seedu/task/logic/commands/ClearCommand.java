package seedu.task.logic.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.YTomorrow;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

//@@author A0163848R
/**
 * Clears the task manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    //@@author A0164466X
    public static final String KEYWORD_ALL = "all";
    public static final String KEYWORD_COMPLETE = Tag.TAG_COMPLETE;
    public static final String KEYWORD_INCOMPLETE = Tag.TAG_INCOMPLETE;
    //@@author

    //@@author A0163848R
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all tasks, "
            + "or only completed tasts if the " + KEYWORD_COMPLETE + " keyword is specified.\n"
            + "Parameters: [keyword]\n"
            + "Keywords : "
            + KEYWORD_ALL
            + ", "
            + KEYWORD_COMPLETE
            + ", "
            + KEYWORD_INCOMPLETE
            + "\n"
            + "Example: " + COMMAND_WORD + " completed";

    public static final String MESSAGE_SUCCESS_ALL = "All tasks have been cleared!";
    //@@author A0164466X
    public static final String MESSAGE_SUCCESS_COMPLETE = "All completed tasks have been cleared!";
    public static final String MESSAGE_SUCCESS_PASSED = "All incompleted tasks have been cleared!";
    //@@author
    //@@author A0163848R
    public static final String MESSAGE_FAILURE = "Clear command not given any keywords!\n" + MESSAGE_USAGE;

    private Set<String> keywords;

    public ClearCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        assert model != null;

        if (keywords.contains(KEYWORD_ALL)) {

            model.resetData(new YTomorrow());

            return new CommandResult(MESSAGE_SUCCESS_ALL);
        }

        if (keywords.contains(KEYWORD_COMPLETE)) {

            List<ReadOnlyTask> filtered = new ArrayList<ReadOnlyTask>();
            for (ReadOnlyTask task : model.getTaskManager().getTaskList()) {
                try {
                    if (!task.getTags().contains(new Tag(Tag.TAG_COMPLETE))) {
                        filtered.add(task);
                    }
                } catch (IllegalValueException e) {
                    e.printStackTrace();
                }
            }
            YTomorrow filteredYTomorrow = new YTomorrow();
            try {
                filteredYTomorrow.setTasks(filtered);
            } catch (DuplicateTaskException e) {
                e.printStackTrace();
            }
            model.resetData(filteredYTomorrow);

            return new CommandResult(MESSAGE_SUCCESS_COMPLETE);
        }

        if (keywords.contains(KEYWORD_INCOMPLETE)) {

            List<ReadOnlyTask> filtered = new ArrayList<ReadOnlyTask>();
            for (ReadOnlyTask task : model.getTaskManager().getTaskList()) {
                if (!task.hasPassed()) {
                    filtered.add(task);
                }
            }
            YTomorrow filteredYTomorrow = new YTomorrow();
            try {
                filteredYTomorrow.setTasks(filtered);
            } catch (DuplicateTaskException e) {
                e.printStackTrace();
            }
            model.resetData(filteredYTomorrow);

            return new CommandResult(MESSAGE_SUCCESS_PASSED);
        }

        return new CommandResult(MESSAGE_FAILURE);
    }
}
