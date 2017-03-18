package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.task.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
	private static final String TASK_NAME_FIELD_ID = "#taskName";
	private static final String TASK_DATE_FIELD_ID = "#taskDate";
	private static final String TASK_START_TIME_FIELD_ID = "#taskStartTime";
	private static final String TASK_END_TIME_FIELD_ID = "#taskEndTime";
	private static final String TASK_DESCRIPTION_FIELD_ID = "#taskDescription";

	private Node node;

	public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
		super(guiRobot, primaryStage, null);
		this.node = node;
	}

	protected String getTextFromLabel(String fieldId) {
		return getTextFromLabel(fieldId, node);
	}

	public String getFullTaskName() {
		return getTextFromLabel(TASK_NAME_FIELD_ID);
	}

	public String getTaskDate() {
		return getTextFromLabel(TASK_DATE_FIELD_ID);
	}

	public String getTaskStartTime() {
		return getTextFromLabel(TASK_START_TIME_FIELD_ID);
	}

	public String getTaskEndTime() {
		return getTextFromLabel(TASK_END_TIME_FIELD_ID);
	}

	public String getTaskDescription() {
		return getTextFromLabel(TASK_DESCRIPTION_FIELD_ID);
	}

	public boolean isSameTask(ReadOnlyTask task) {
		return getFullTaskName().equals(task.getTaskName().fullTaskName)
				&& getTaskStartTime().equals(task.getTaskDate().value)
				&& getTaskEndTime().equals(task.getTaskEndTime().value)
				&& getTaskDate().equals(task.getTaskDate().value)
				&& getTaskDescription().equals(task.getTaskDescription());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TaskCardHandle) {
			TaskCardHandle handle = (TaskCardHandle) obj;
			return getFullTaskName().equals(handle.getFullTaskName())
					&& getTaskStartTime().equals(handle.getTaskStartTime())
					&& getTaskEndTime().equals(handle.getTaskEndTime()) && getTaskDate().equals(handle.getTaskDate())
					&& getTaskDescription().equals(handle.getTaskDescription());
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return getFullTaskName() + " " + getTaskDate();
	}
}
