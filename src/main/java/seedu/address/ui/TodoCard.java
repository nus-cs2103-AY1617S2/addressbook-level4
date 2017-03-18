package seedu.address.ui;

import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CompleteCommand;
import seedu.address.model.todo.ReadOnlyTodo;

public class TodoCard extends UiPart<Region> {

    private static final String FXML = "TodoListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label start;
    @FXML
    private Label end;
    @FXML
    private Label complete;
    @FXML
    private FlowPane tags;

    public TodoCard(ReadOnlyTodo todo, int displayedIndex) {
        super(FXML);
        name.setText(todo.getName().fullName);
        id.setText(displayedIndex + ". ");
        if (todo.getStartTime() != null) {
            start.setText("Start: " + todo.getStartTime().toString());
        }
        if (todo.getEndTime() != null) {
            end.setText("End: " + todo.getEndTime().toString());
        }
        if (todo.getCompleteTime() != null) {
            complete.setText(String.format("Completed at %1$s",
                    new SimpleDateFormat(CompleteCommand.COMPLETE_TIME_FORMAT).format(todo.getCompleteTime())));
            complete.setStyle("-fx-text-fill: #00ad36;");
        } else {
            complete.setText("Not Complete");
            complete.setStyle("-fx-text-fill: #e20000;");
        }
        initTags(todo);
    }

    private void initTags(ReadOnlyTodo todo) {
        todo.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
