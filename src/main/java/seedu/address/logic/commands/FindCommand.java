package seedu.address.logic.commands;

import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.JumpToEventListRequestEvent;
import seedu.address.commons.events.ui.JumpToTaskListRequestEvent;

/**
 * Finds and lists all activities in WhatsLeft whose description contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all activities whose description contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    //@@author A0110491U
    @Override
    public CommandResult execute() {
        model.updateFilteredEventList(keywords);
        model.updateFilteredTaskList(keywords);
        model.storePreviousCommand("");
        EventsCenter.getInstance().post(new JumpToEventListRequestEvent(0));
        EventsCenter.getInstance().post(new JumpToTaskListRequestEvent(0));
        return new CommandResult(getMessageForActivityListShownSummary(model.getFilteredEventList().size() +
                model.getFilteredTaskList().size()));
    }

}
