package seedu.taskboss.logic.commands;

import seedu.taskboss.commons.core.EventsCenter;
import seedu.taskboss.commons.events.ui.JumpToCategoryListEvent;
import seedu.taskboss.commons.events.ui.JumpToListRequestEvent;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.category.Category;

/**
 * Lists all tasks in TaskBoss to the user.
 */
public class ListCommand extends Command {

    private static final int INDEX_FIRST_TASK = 0;
    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_WORD_SHORT = "l";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    @Override
    public CommandResult execute() throws IllegalValueException {
        model.updateFilteredListToShowAll();
        EventsCenter.getInstance().post(new JumpToListRequestEvent(INDEX_FIRST_TASK));
        EventsCenter.getInstance().post(new JumpToCategoryListEvent(new Category("Alltasks")));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
