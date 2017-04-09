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
	public static final String COMMAND_WORD_SUFFIX_TAG = "tag";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
			+ "by the index number used in the last task listing. "
			+ "Existing values will be overwritten by the input values.\n"
			+ "Parameters: INDEX (must be a positive integer) [s/START_TIME] [e/END_TIME] [d/DESCRIPTION] [t/TAGS]...\n"
			+ "Example: " + COMMAND_WORD + " 1 s/10:00am,10/10/2017 e/5:00pm,10/10/2017 d/lots of work to do t/school";

	public static final String MESSAGE_SUCCESS = "Listed all tasks";

	@Override
	public CommandResult execute() {
		model.updateFilteredListToShowAll();
		return new CommandResult(MESSAGE_SUCCESS);
	}
}
