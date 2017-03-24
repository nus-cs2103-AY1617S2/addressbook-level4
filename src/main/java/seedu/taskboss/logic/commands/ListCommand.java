package seedu.taskboss.logic.commands;

import seedu.taskboss.commons.core.EventsCenter;
import seedu.taskboss.commons.events.ui.JumpToListRequestEvent;

/**
 * Lists all tasks in TaskBoss to the user.
 */
public class ListCommand extends Command {

    private static final int INDEX_FIRST_TASK = 0;
    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_WORD_SHORT = "l";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        EventsCenter.getInstance().post(new JumpToListRequestEvent(INDEX_FIRST_TASK));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
