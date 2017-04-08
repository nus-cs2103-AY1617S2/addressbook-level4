package seedu.task.logic.commands;

import java.util.Set;

/**
 * Finds & lists all tasks in the incomplete task list with name or information contains any of the argument keywords.
 * Keywords matching is NOT case sensitive.
 * Near match cases are allowed.
 */

public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks "
            + "whose names or information contain any of "
            + "the specified keywords (NOT case-sensitive) and displays them as a list with index numbers.\n"
            + "Near match cases are allowed.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " birthday homework picnic";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    //@@author A0135762A
    @Override
    public CommandResult execute() {
        //Find Exact Match case(s) first, and calculate the list size.
        model.updateExactFilteredTaskList(keywords);
        int temp = model.getFilteredTaskList().size();

        model.updateFilteredTaskList(keywords);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size())
                + " Including " + temp + " Exact Match case(s) & "
                + (model.getFilteredTaskList().size() - temp) + " Near Match case(s).");
    }
    //@@author

}
