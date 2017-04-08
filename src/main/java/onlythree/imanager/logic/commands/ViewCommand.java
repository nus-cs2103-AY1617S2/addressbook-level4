package onlythree.imanager.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

//@@author A0135998H
/**
 * View all tasks in the view list to the user.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String TYPE_ALL = "";
    public static final String TYPE_DONE = "d";
    public static final String TYPE_FLOATING = "f";
    public static final String TYPE_OVERDUE = "o";
    public static final String TYPE_PENDING = "p";
    public static final String TYPE_TODAY = "t";

    private static ArrayList<String> validCommands = new ArrayList<String>(Arrays.asList(
            TYPE_ALL, TYPE_DONE, TYPE_FLOATING, TYPE_OVERDUE, TYPE_PENDING, TYPE_TODAY));

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": View a type of specified tasks.\n"
            + "Parameters: TYPE\n"
            + "Example: " + COMMAND_WORD + TYPE_DONE;


    public static final String MESSAGE_SUCCESS_VIEW_ALL_TASKS = "List All Tasks";
    public static final String MESSAGE_SUCCESS_VIEW_DONE_TASKS = "List all Done Tasks";
    public static final String MESSAGE_SUCCESS_VIEW_FLOATING_TASKS = "List all Floating Tasks";
    public static final String MESSAGE_SUCCESS_VIEW_OVERDUE_TASKS = "List all Overdue Tasks";
    public static final String MESSAGE_SUCCESS_VIEW_PENDING_TASKS = "List all Pending Tasks";
    public static final String MESSAGE_SUCCESS_VIEW_TODAY_TASKS = "List all Today's Tasks";

    private final String typeOfList;

    public ViewCommand(String typeOfList) {
        this.typeOfList = typeOfList;
    }

    public static boolean isValidCommand(String command) {
        return validCommands.contains(command);
    }

    @Override
    public CommandResult execute() {
        switch(typeOfList) {
        case TYPE_ALL:
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_SUCCESS_VIEW_ALL_TASKS);
        case TYPE_DONE:
            model.updateFilteredListToShowDone();
            return new CommandResult(MESSAGE_SUCCESS_VIEW_DONE_TASKS);
        case TYPE_FLOATING:
            model.updateFilteredListToShowFloating();
            return new CommandResult(MESSAGE_SUCCESS_VIEW_FLOATING_TASKS);
        case TYPE_OVERDUE:
            model.updateFilteredListToShowOverdue();
            return new CommandResult(MESSAGE_SUCCESS_VIEW_OVERDUE_TASKS);
        case TYPE_PENDING:
            model.updateFilteredListToShowPending();
            return new CommandResult(MESSAGE_SUCCESS_VIEW_PENDING_TASKS);
        case TYPE_TODAY:
            model.updateFilteredListToShowToday();
            return new CommandResult(MESSAGE_SUCCESS_VIEW_TODAY_TASKS);
        default:
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_SUCCESS_VIEW_ALL_TASKS);
        }
    }
}
