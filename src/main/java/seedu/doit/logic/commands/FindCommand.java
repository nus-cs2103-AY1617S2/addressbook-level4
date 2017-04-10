package seedu.doit.logic.commands;

import java.util.Set;

//@@author A0146809W
/**
 * Finds and lists all tasks in task manager whose name contains any of the
 * argument keywords. Keyword matching is case sensitive.
 */
public class FindCommand extends Command {
    public static final String COMMAND_RESULT = "";
    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_EXAMPLES = COMMAND_WORD + " n/Task A p/high e/ 12/30/2020 23:59\n" + COMMAND_WORD
            + " p/low s/ 12/30/2020 23:59\n" + COMMAND_WORD + " t/project";
    public static final String COMMAND_PARAMETER = "n/TASK NAME p/PRIORITY s/START DATE&TIME "
            + "e/END DATE&TIME  d/ADDITIONAL DESCRIPTION [t/TAG]...\n"
            + "Date format must be MM/DD/YYYY. Time format must be HH:MM.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: " + COMMAND_PARAMETER + "\n" + "Example: " + COMMAND_EXAMPLES;

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
        this.model.updateFilteredTaskList(this.namekeywords, this.prioritykeywords, this.desckeywords,
                this.tagskeywords, this.startTimekeywords, this.endTimekeywords);
        return new CommandResult(getMessageForTaskListShownSummary(this.model.getFilteredTaskList().size()));

    }

    public static String getName() {
        return COMMAND_WORD;
    }

    public static String getParameter() {
        return COMMAND_PARAMETER;
    }

    public static String getResult() {
        return COMMAND_RESULT;
    }

    public static String getExample() {
        return COMMAND_EXAMPLES;
    }
}
