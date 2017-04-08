package org.teamstbf.yats.logic.commands;

import java.util.HashSet;
import java.util.Set;

import org.teamstbf.yats.commons.core.UnmodifiableObservableList;
import org.teamstbf.yats.model.item.IsDone;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.UniqueEventList.EventNotFoundException;

// @@author A0139448U
/**
 * Clears the done tasks in the task manager.
 */
public class ClearDoneCommand extends Command {

	public static final String COMMAND_WORD = "clear";
	public static final String MESSAGE_SUCCESS = "All done tasks have been cleared!";
	public static final String MESSAGE_ALREADY_CLEAR = "There are no done tasks to clear!";
	private static final String TASK_DONE_IDENTIFIER = "Yes";

	@Override
	public CommandResult execute() {
		Set<String> doneTaskIdentifier = new HashSet<String>();
		doneTaskIdentifier.add(TASK_DONE_IDENTIFIER);
		model.updateFilteredListToShowDone(doneTaskIdentifier);

		UnmodifiableObservableList<ReadOnlyEvent> lastShownList = model.getFilteredTaskList();

		int formerSize = lastShownList.size();
		model.saveImageOfCurrentTaskManager();

		for (int index = 0; index < lastShownList.size(); index++) {
			if (lastShownList.get(index).getIsDone().getValue().equals(IsDone.ISDONE_DONE)) {
				ReadOnlyEvent eventToDelete = lastShownList.get(index);
				try {
					model.deleteEvent(eventToDelete);
				} catch (EventNotFoundException pnfe) {
					assert false : "The target task cannot be missing";
				}
				index--;
			}
		}
		if (formerSize == lastShownList.size()) {
			model.updateFilteredListToShowAll();
			return new CommandResult(MESSAGE_ALREADY_CLEAR);
		}
		model.updateFilteredListToShowAll();
		return new CommandResult(MESSAGE_SUCCESS);
	}

}
