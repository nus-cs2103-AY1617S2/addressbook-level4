package org.teamstbf.yats.logic.commands;

import org.teamstbf.yats.commons.core.UnmodifiableObservableList;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.UniqueEventList.EventNotFoundException;

/**
 * Clears the done tasks in the task manager.
 */
// @@author A0139448U
public class ClearDoneCommand extends Command {

	public static final String COMMAND_WORD = "clear";
	public static final String MESSAGE_SUCCESS = "All done tasks have been cleared!";

	@Override
	public CommandResult execute() {
		UnmodifiableObservableList<ReadOnlyEvent> lastShownList = model.getFilteredTaskList();

		for (int index = 0; index < lastShownList.size(); index++) {
			if (lastShownList.get(index).isTaskDone()) {
				ReadOnlyEvent eventToDelete = lastShownList.get(index);
				try {
					model.deleteEvent(eventToDelete);
				} catch (EventNotFoundException pnfe) {
					assert false : "The target task cannot be missing";
				}
				index--;
			}
		}
		return new CommandResult(MESSAGE_SUCCESS);
	}

}
