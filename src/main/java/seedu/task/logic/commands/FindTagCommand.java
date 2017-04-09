package seedu.task.logic.commands;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;

//@@author A0139322L

/**
 *  Find and lists all tasks in the task list which contain the specified tag.
 *  Only 1 tag can be searched at a time.
 *  Tag matching is case sensitive.
 */
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "findtag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks which contain "
            + "the specified tag (case-sensitive, restricted to 1 tag) and displays them as a list with index "
            + "numbers.\n"
            + "Parameters: TAG\n"
            + "Example: " + COMMAND_WORD + " School";
    public static final String MESSAGE_TOO_MANY_ARGUMENTS = "Only 1 tag can be searched at a time!";

    private final String tagName;

    public FindTagCommand(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public CommandResult execute() throws CommandException, IllegalValueException {
        if (!(tagName.length() > 0 && tagName.split("\\s+").length == 2)) {
            System.out.println(tagName.length());
            System.out.println(tagName.split("\\s+").length);
            throw new IllegalValueException(MESSAGE_TOO_MANY_ARGUMENTS);
        }
        model.updateFilteredTagTaskList(tagName);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}

//@@author
