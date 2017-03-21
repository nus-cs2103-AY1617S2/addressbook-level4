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
    private Label id;
	@FXML
	private Label taskDate;
	@FXML
	private Label taskStartTime;
	@FXML
	private Label taskEndTime;
	@FXML
	private Label taskDescription;
	@FXML
    private FlowPane tags;

	
	public TaskCard(ReadOnlyTask task, int displayedIndex) {
		super(FXML);
		
		id.setText(displayedIndex + ". ");
		if (task.getTaskName() != null) {
			taskName.setText(task.getTaskName().fullTaskName);
		}
		if (task.getTaskDate() != null) {
			taskStartTime.setText(task.getTaskDate().value);
		}
		if (task.getTaskStartTime() != null && task.getTaskEndTime() != null) {
			taskEndTime.setText(task.getTaskStartTime().value + "-" + task.getTaskEndTime().value);
		}
		if (task.getTaskDescription() != null) {
			taskDescription.setText(task.getTaskDescription());
		}
		initTags(task);
	}
	private void initTags(ReadOnlyTask task) {
       task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
