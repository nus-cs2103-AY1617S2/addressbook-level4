package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.task.model.task.ReadOnlyTask;

//@@author A0146757R
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
  private Label taskTime;
  @FXML
  private Label taskDescription;
  @FXML
  private Label taskStatus;
  @FXML
  private FlowPane tags;

  public TaskCard(ReadOnlyTask task, int displayedIndex) {
    super(FXML);

    id.setText(displayedIndex + ". ");
    if (task.getTaskName() != null) {
      taskName.setText(task.getTaskName().toString());
    }
    if (task.getTaskDate() != null) {
      taskDate.setText("Date : " + task.getTaskDate().toString());
    } else
      taskDate.setText("Date: -");
    if (task.getTaskStartTime() != null && task.getTaskEndTime() != null) {
      taskTime.setText("Time: " + task.getTaskStartTime().toString() + "-" + task.getTaskEndTime());
    } else if (task.getTaskStartTime() != null) {
      taskTime.setText("Start Time: " + task.getTaskStartTime().toString());
    } else if (task.getTaskEndTime() != null) {
      taskTime.setText("End Time: " + task.getTaskEndTime().toString());
    } else
      taskTime.setText("Time: -");
    if (task.getTaskDescription() != null) {

      taskDescription.setText("Description: " + task.getTaskDescription().toString());
    } else
      taskDescription.setText("Description: -");
    taskStatus.setText("Task Status: " + task.getTaskStatus().toString());

    initTags(task);
  }

  // @@author
  private void initTags(ReadOnlyTask task) {
    task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
  }
}
