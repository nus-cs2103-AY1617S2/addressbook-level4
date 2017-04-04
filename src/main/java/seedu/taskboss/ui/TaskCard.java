package seedu.taskboss.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.taskboss.commons.util.AppUtil;
import seedu.taskboss.model.task.PriorityLevel;
import seedu.taskboss.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String RECURRENCE_NONE = "NONE";
    private static final String PATH_IMAGE_URGENT = "/images/urgentImage.png";
    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private ImageView priorityLevel;
    @FXML
    private Label startDateTime;
    @FXML
    private Label endDateTime;
    @FXML
    private Label information;
    @FXML
    private Label recurrence;
    @FXML
    private FlowPane categories;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        initPriority(task);
        initStartDateTime(task);
        initEndDateTime(task);
        information.setText(task.getInformation().value);
        initRecurrence(task);
        initCategories(task);
    }

    /**
     * Initialises priority level
     */
    private void initPriority(ReadOnlyTask task) {
        PriorityLevel priority = task.getPriorityLevel();
        Image urgentImage = AppUtil.getImage(PATH_IMAGE_URGENT);
        if (priority.value.equals(PriorityLevel.PRIORITY_HIGH_VALUE)) {
            priorityLevel.setImage(urgentImage);
        } else {
            priorityLevel.setVisible(false);
        }
    }

    /**
     * Initialises start date time
     */
    private void initStartDateTime(ReadOnlyTask task) {
        String startDate = task.getStartDateTime().value;
        if (startDate.isEmpty()) {
            startDateTime.setText("Start: " + startDate);
            startDateTime.setVisible(false);
        } else {
            startDateTime.setText("Start: " + task.getStartDateTime().value);
        }
    }

    /**
     * Initialises end date time
     */
    private void initEndDateTime(ReadOnlyTask task) {
        String endDate = task.getEndDateTime().value;
        if (endDate.isEmpty()) {
            endDateTime.setText("End: " + endDate);
            endDateTime.setVisible(false);
        } else {
            endDateTime.setText("End: " + task.getEndDateTime().value);
        }
    }

    /**
     * Initialises recurrence
     */
    private void initRecurrence(ReadOnlyTask task) {
        String recurrenceType = task.getRecurrence().toString();
        if (recurrenceType.equals(RECURRENCE_NONE)) {
            recurrence.setText(task.getRecurrence().toString());
            recurrence.setVisible(false);
        } else {
            recurrence.setText(task.getRecurrence().toString());
        }
    }

    private void initCategories(ReadOnlyTask task) {
        task.getCategories().forEach(category -> categories.getChildren().add(new Label(category.categoryName)));
    }
}
