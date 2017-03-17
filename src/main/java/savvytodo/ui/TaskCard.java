package savvytodo.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import savvytodo.commons.util.StringUtil;
import savvytodo.model.task.DateTime;
import savvytodo.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label status;
    @FXML
    private Label dateTimeRecur;
    @FXML
    private Label description;
    @FXML
    private FlowPane categories;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().name);
        id.setText(displayedIndex + ". ");
        description.setText(task.getDescription().value);
        priority.setText(task.getPriority().value);
        dateTimeRecur.setText(getDateTimeRecur(task));
        status.setText(task.isCompleted().toString());
        initCategories(task);
    }

    private String getDateTimeRecur(ReadOnlyTask task) {
        if (task.getDateTime().toString().equalsIgnoreCase(StringUtil.EMPTY_STRING)) {
            return "This is a floating task";
        } else if (task.getDateTime().startValue.equalsIgnoreCase(StringUtil.EMPTY_STRING)
                || task.getDateTime().startValue == null) {
            return "Deadline: " + task.getDateTime().toString();
        } else {
            return "Event: " + task.getDateTime().toString() + DateTime.DATETIME_STRING_CONNECTOR
                    + task.getRecurrence().toString();
        }
    }

    private void initCategories(ReadOnlyTask task) {
        task.getCategories().forEach(category -> categories.getChildren().add(new Label(category.categoryName)));
    }
}
