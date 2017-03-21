package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.task.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

	private static final String FXML = "TaskListCard.fxml";

	@FXML
	private HBox cardPane;
	@FXML
	private Label taskName;
	@FXML
	private Label taskDate;
	@FXML
	private Label taskStartTime;
	@FXML
	private Label taskEndTime;
	@FXML
	private Label taskDescription;

	public TaskCard(ReadOnlyTask task, int displayedIndex) {
		super(FXML);
		taskDate.setText(displayedIndex + ". ");
		if (task.getTaskName() != null) {
			taskName.setText(task.getTaskName().toString());
		}
		if (task.getTaskDate() != null) {
			taskStartTime.setText(task.getTaskDate().toString());
		}
		if (task.getTaskStartTime() != null && task.getTaskEndTime() != null) {
			taskEndTime.setText(task.getTaskStartTime().toString() + "-" + task.getTaskEndTime());
		} else if (task.getTaskStartTime() != null) {
			taskEndTime.setText(task.getTaskStartTime().toString());
		} else if (task.getTaskEndTime() != null) {
			taskEndTime.setText(task.getTaskEndTime().toString());
		}
		if (task.getTaskDescription() != null) {
			taskDescription.setText(task.getTaskDescription().toString());
		}
	}
}
