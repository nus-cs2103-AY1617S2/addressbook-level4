package seedu.doit.logic.commands;

import java.util.Set;

//@@author A0146809W
/**
 * Finds and lists all tasks in task manager whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
        + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
        + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
        + "Example: " + COMMAND_WORD + " Task A Project 3 Print 10 pages";

    private final Set<String> namekeywords;
    private final Set<String> startTimekeywords;
    private final Set<String> endTimekeywords;
    private final Set<String> prioritykeywords;
    private final Set<String> tagskeywords;
    private final Set<String> desckeywords;

    public FindCommand(Set<String> namekeywords, Set<String> startTimekeywords, Set<String> endTimekeywords,
                       Set<String> prioritykeywords, Set<String> tagskeywords, Set<String> desckeywords) {
        this.namekeywords = namekeywords;
        this.startTimekeywords = startTimekeywords;
        this.endTimekeywords = endTimekeywords;
        this.prioritykeywords = prioritykeywords;
        this.tagskeywords = tagskeywords;
        this.desckeywords = desckeywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(namekeywords, prioritykeywords, desckeywords, tagskeywords, startTimekeywords,
                endTimekeywords);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
