package seedu.taskmanager.logic.commands;

import static seedu.taskmanager.ui.MainWindow.TAB_DONE_INDEX;
import static seedu.taskmanager.ui.MainWindow.TAB_TO_DO_INDEX;

import seedu.taskmanager.commons.core.EventsCenter;
import seedu.taskmanager.commons.events.ui.TabSelectionChangedEvent;
import seedu.taskmanager.commons.exceptions.IllegalValueException;

/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    // @@author A0131278H
    public static final String LIST_KEYWORD_DONE = "done";
    public static final String LIST_KEYWORD_TO_DO = "todo";
    public static final String LIST_KEYWORD_DEFAULT = "all";

    public static final String MESSAGE_SUCCESS_ALL = "Listed all tasks";
    public static final String MESSAGE_SUCCESS_DONE = "Listed all done tasks";
    public static final String MESSAGE_SUCCESS_TO_DO = "Listed all to do tasks";
    public static final String MESSAGE_SUCCESS_INVALID_KEYWORD = "Invalid keyword. " + MESSAGE_SUCCESS_ALL
            + " in current tab by default";

    private final String listKeyword;

    /**
     * @param listKeyword
     *            the keyword to filter and list tasks by
     */
    public ListCommand(String listKeyword) throws IllegalValueException {
        this.listKeyword = listKeyword;
    }

    @Override
    public CommandResult execute() {

        model.updateFilteredListToShowAll();
        switch (listKeyword) {
        case LIST_KEYWORD_DONE:
            EventsCenter.getInstance().post(new TabSelectionChangedEvent(TAB_DONE_INDEX));
            return new CommandResult(MESSAGE_SUCCESS_DONE);
        case LIST_KEYWORD_TO_DO:
            EventsCenter.getInstance().post(new TabSelectionChangedEvent(TAB_TO_DO_INDEX));
            return new CommandResult(MESSAGE_SUCCESS_TO_DO);
        case LIST_KEYWORD_DEFAULT:
            return new CommandResult(MESSAGE_SUCCESS_ALL);
        default:
            return new CommandResult(MESSAGE_SUCCESS_INVALID_KEYWORD);
        }
    }
    // @@author
}
