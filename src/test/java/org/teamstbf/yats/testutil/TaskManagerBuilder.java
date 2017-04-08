package org.teamstbf.yats.testutil;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.model.TaskManager;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.UniqueEventList;
import org.teamstbf.yats.model.tag.Tag;

/**
 * A utility class to help with building TaskManager objects. Example usage:
 * <br>
 * {@code TaskManager tb = new TaskManagerBuilder().withEvent("Abdicate the throne", "Usorp the throne").withTag("Betrayal").build();}
 */
public class TaskManagerBuilder {

	private TaskManager taskManager;

	public TaskManagerBuilder(TaskManager taskManager) {
		this.taskManager = taskManager;
	}

	public TaskManagerBuilder withEvent(Event event) {
		taskManager.addEvent(event);
		return this;
	}

	public TaskManagerBuilder withTag(String tagName) throws IllegalValueException {
		taskManager.addTag(new Tag(tagName));
		return this;
	}

	public TaskManager build() {
		return taskManager;
	}
}
