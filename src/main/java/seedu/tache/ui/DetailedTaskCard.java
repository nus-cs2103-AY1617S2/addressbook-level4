package seedu.tache.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.tache.model.task.ReadOnlyDetailedTask;

public class DetailedTaskCard extends UiPart<Region> {

    private static final String FXML = "DetailedTaskListCard.fxml";

    @FXML
    private HBox cardPane;

    @FXML
    private Label id;

    @FXML
    private Label name;

    @FXML
    private Label startDate;

    @FXML
    private Label endDate;

    @FXML
    private Label startTime;

    @FXML
    private Label endTime;

    @FXML
    private FlowPane tags;

    public DetailedTaskCard(ReadOnlyDetailedTask detailedTask, int displayedIndex) {
        super(FXML);
        id.setText(toAlphabetic(displayedIndex) + ". ");
        name.setText(detailedTask.getName().toString());
        startDate.setText(detailedTask.getStartDate().toString());
        endDate.setText(detailedTask.getEndDate().toString());
        startTime.setText(detailedTask.getStartTime().toString());
        endTime.setText(detailedTask.getEndTime().toString());
        initTags(detailedTask);
    }

    private void initTags(ReadOnlyDetailedTask detailedTask) {
        detailedTask.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private static String toAlphabetic(int index) {
        String result = "";
        while (--index >= 0) {
            result = (char) ('A' + index % 26) + result;
            index /= 26;
        }
        return result;
    }
}
