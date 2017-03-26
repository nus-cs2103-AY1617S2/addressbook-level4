package seedu.tache.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.tache.model.task.DateTime;
import seedu.tache.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String START_DATE_INDICATOR = "Start Date: ";
    private static final String START_TIME_INDICATOR = "Start Time: ";
    private static final String END_DATE_INDICATOR = "End Date: ";
    private static final String END_TIME_INDICATOR = "End Time: ";
    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;

    @FXML
    private Label id;

    @FXML
    private Label name;

    @FXML
    private FlowPane datesAndTimes;

    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        id.setText(Integer.toString(displayedIndex) + ". ");
        name.setText(task.getName().toString());
        if (task.getStartDateTime().isPresent()) {
            DateTime start = task.getStartDateTime().get();
            datesAndTimes.getChildren().add(new Label(START_DATE_INDICATOR + start.getDateOnly()));
            datesAndTimes.getChildren().add(new Label(START_TIME_INDICATOR + start.getTimeOnly()));
        }
        if (task.getEndDateTime().isPresent()) {
            DateTime end = task.getEndDateTime().get();
            datesAndTimes.getChildren().add(new Label(END_DATE_INDICATOR + end.getDateOnly()));
            datesAndTimes.getChildren().add(new Label(END_TIME_INDICATOR + end.getTimeOnly()));
        }
        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
