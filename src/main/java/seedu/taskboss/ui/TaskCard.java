package seedu.taskboss.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.taskboss.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label priorityLevel;
    @FXML
    private Label startDateTime;
    @FXML
    private Label endDateTime;
    @FXML
    private Label information;
    @FXML
    private FlowPane categories;

    public TaskCard(ReadOnlyTask task, int displayedIndex) throws ParseException {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        priorityLevel.setText(task.getPriorityLevel().value);

        // Set dates to desired format for UI
        String rawStartDateTime = task.getStartDateTime().value;
        SimpleDateFormat originalDf = new SimpleDateFormat("EEE MMM dd kk:mm:ss zzz yyyy");
        Date startDate = originalDf.parse(rawStartDateTime);
        SimpleDateFormat convertedDf = new SimpleDateFormat("MMM dd, yyyy K:mm aa");
        startDateTime.setText(convertedDf.format(startDate));
        String rawEndDateTime = task.getEndDateTime().value;
        Date endDate = originalDf.parse(rawEndDateTime);
        endDateTime.setText(convertedDf.format(endDate));

        information.setText(task.getInformation().value);

        initCategories(task);
    }

    private void initCategories(ReadOnlyTask task) {
        task.getCategories().forEach(category -> categories.getChildren().add(new Label(category.categoryName)));
    }
}
