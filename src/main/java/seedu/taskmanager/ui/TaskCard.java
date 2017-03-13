package seedu.taskmanager.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.taskmanager.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label taskname;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
//    @FXML
//    private FlowPane categories;
    
    private final StringProperty empty = new SimpleStringProperty("");

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        taskname.setText(task.getTaskName().fullTaskName);
        id.setText(displayedIndex + ". ");
        	if((task.getDate().value) == "EMPTY_FIELD") {
        		date.textProperty().bind(empty);
        	} else {
                date.setText(task.getDate().value);
        	}	
        	if((task.getStartTime().value) == "EMPTY_FIELD") {
        		startTime.textProperty().bind(empty);
        	} else {
            	startTime.setText(task.getStartTime().value);
        	}        	
        	if((task.getEndTime().value) == "EMPTY_FIELD") {
        		endTime.textProperty().bind(empty);
        	} else {
                endTime.setText(task.getEndTime().value);
        	}        	
 //        initCategory(task);
    }
/*
    private void initCategory(ReadOnlyTask task) {
        task.getCategories().forEach(category -> categories.getChildren().add(new Label(category.categoryName)));
    }
*/
}
