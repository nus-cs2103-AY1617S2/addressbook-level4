package seedu.taskmanager.ui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.taskmanager.model.task.ReadOnlyTask;

//@@author A0142418L
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label taskname;
    @FXML
    private Label id;
    @FXML
    private ListView<ListView<Label>> detailsList;
    private ObservableList<ListView<Label>> listRows = FXCollections.observableArrayList();
    private ObservableList<Label> topLabels = FXCollections.observableArrayList();
    private ObservableList<Label> botLabels = FXCollections.observableArrayList();
    private ListView<Label> topRow = new ListView<Label>();
    private ListView<Label> botRow = new ListView<Label>();
    @FXML
    private Label markedCompleted;
    @FXML
    private FlowPane categories;


    private final StringProperty empty = new SimpleStringProperty("");
    private final StringProperty completed = new SimpleStringProperty("Completed");

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        taskname.setText(task.getTaskName().fullTaskName);
        id.setText(displayedIndex + ". ");

        if (isMultipleDateEvent(task)) {

            Label startDate = new Label();
            startDate.setText("Start Date: " + task.getStartDate().value + "   ");
            startDate.setStyle("-fx-font-size: 11px");
            topLabels.add(startDate);
            Label startTime = new Label();
            startTime.setText("Start Time: " + task.getStartTime().value);
            startTime.setStyle("-fx-font-size: 11px");
            topLabels.add(startTime);
            Label endDate = new Label();
            endDate.setText("End Date: " + task.getEndDate().value + "     ");
            endDate.setStyle("-fx-font-size: 11px");
            botLabels.add(endDate);
            Label endTime = new Label();
            endTime.setText("End Time: " + task.getEndTime().value);
            endTime.setStyle("-fx-font-size: 11px");
            botLabels.add(endTime);
            
            topRow.setItems(topLabels);
            botRow.setItems(botLabels);
            
            topRow.setOrientation(Orientation.HORIZONTAL);
            botRow.setOrientation(Orientation.HORIZONTAL);
            
            topRow.prefHeightProperty().bind(Bindings.size(topLabels).multiply(14));
            botRow.prefHeightProperty().bind(Bindings.size(botLabels).multiply(14));
            
            listRows.add(topRow);
            listRows.add(botRow);
            
            detailsList.setItems(listRows);
            }

        if (isSameDateEvent(task)) {
            Label date = new Label();
            date.setText("Date: " + task.getStartDate().value);
            date.setStyle("-fx-font-size: 11px");
            topLabels.add(date);
            Label startTime = new Label();
            startTime.setText("Start Time: " + task.getStartTime().value + "   ");
            startTime.setStyle("-fx-font-size: 11px");
            botLabels.add(startTime);
            Label endTime = new Label();
            endTime.setText("End Time: " + task.getEndTime().value);
            endTime.setStyle("-fx-font-size: 11px");
            botLabels.add(endTime);
            
            topRow.setItems(topLabels);
            botRow.setItems(botLabels);

            topRow.setOrientation(Orientation.HORIZONTAL);
            botRow.setOrientation(Orientation.HORIZONTAL);
            
            topRow.prefHeightProperty().bind(Bindings.size(topLabels).multiply(14));
            botRow.prefHeightProperty().bind(Bindings.size(botLabels).multiply(14));

            listRows.add(topRow);
            listRows.add(botRow);
            
            detailsList.setItems(listRows);
        }

        if (isDeadlineEvent(task)) {
            Label dueDate = new Label();
            dueDate.setText("Due by: " + task.getEndDate().value + "   ");
            dueDate.setStyle("-fx-font-size: 11px");
            topLabels.add(dueDate);
            Label dueTime = new Label();
            dueTime.setText(task.getEndTime().value);
            dueTime.setStyle("-fx-font-size: 11px");

            topLabels.add(dueTime);

            topRow.setItems(topLabels);

            topRow.setOrientation(Orientation.HORIZONTAL);

            topRow.prefHeightProperty().bind(Bindings.size(topLabels).multiply(14));

            listRows.add(topRow);

            detailsList.setItems(listRows);
        }

        if (task.getIsMarkedAsComplete()) {
            markedCompleted.textProperty().bind(completed);
        } else {
            markedCompleted.textProperty().bind(empty);
        }
        initCategory(task);
    }
    
    private boolean isMultipleDateEvent(ReadOnlyTask task) {
        if (!task.getStartDate().value.equals(task.getEndDate().value) && !task.getStartDate().value.equals("EMPTY_FIELD")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isDeadlineEvent(ReadOnlyTask task) {
        if (isStartDateEmpty(task) && isStartTimeEmpty(task) && !isEndDateEmpty(task) && !isEndTimeEmpty(task)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isStartDateEmpty(ReadOnlyTask task) {
        if ((task.getStartDate().value).equals("EMPTY_FIELD")) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean isStartTimeEmpty(ReadOnlyTask task) {
        if ((task.getStartTime().value).equals("EMPTY_FIELD")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isEndDateEmpty(ReadOnlyTask task) {
        if ((task.getEndDate().value).equals("EMPTY_FIELD")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isEndTimeEmpty(ReadOnlyTask task) {
        if ((task.getEndTime().value).equals("EMPTY_FIELD")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isSameDateEvent(ReadOnlyTask task) {
        if ((task.getStartDate().value).equals(task.getEndDate().value) && (!task.getStartDate().value.equals("EMPTY_FIELD"))) {
            return true;
        } else {
            return false;
        }
    }

    private void initCategory(ReadOnlyTask task) {
        task.getCategories().forEach(category -> categories.getChildren().add(new Label(category.categoryName)));
    }

}
