package org.teamstbf.yats.logic.commands;

//@@author A0138952W
/**
 * Lists all persons in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_WORD_EXTENTION = "by";
    public static final String COMMAND_WORD_SUFFIX_END = "end";
    public static final String COMMAND_WORD_SUFFIX_START = "start";
    public static final String COMMAND_WORD_SUFFIX_DEADLINE = "deadline";
    public static final String COMMAND_WORD_SUFFIX_LOCATION = "location";
    public static final String COMMAND_WORD_SUFFIX_DONE = "done";
    public static final String COMMAND_WORD_SUFFIX_TAG = "tag";;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all undone tasks. However, "
            + "should you want to search for specific attributes, please use: " + COMMAND_WORD + " "
            + COMMAND_WORD_EXTENTION + "start / end / deadline/ location / tag followed by"
            + " the string that you want to search for.";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
