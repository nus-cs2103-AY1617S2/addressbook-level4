package seedu.todolist.ui;

import static seedu.todolist.commons.core.GlobalConstants.DATE_FORMAT;

import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.todolist.model.todo.ReadOnlyTodo;

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
    //@@author A0163786N, A0163720M
    public TodoCard(ReadOnlyTodo todo, int displayedIndex) {
        super(FXML);
        name.setText(todo.getName().fullName);
        id.setText(displayedIndex + ". ");
        if (todo.getStartTime() != null) {
            start.setText(String.format("Start: %1$s",
                    new SimpleDateFormat(DATE_FORMAT).format(todo.getStartTime())));
        }
        if (todo.getEndTime() != null) {
            end.setText(String.format("End: %1$s",
                    new SimpleDateFormat(DATE_FORMAT).format(todo.getEndTime())));
        }
        if (todo.getCompleteTime() != null) {
            complete.setText(String.format("Completed at %1$s",
                    new SimpleDateFormat(DATE_FORMAT).format(todo.getCompleteTime())));
            complete.setStyle("-fx-text-fill: #00ad36;");
        } else {
            complete.setText("Not Complete");
            complete.setStyle("-fx-text-fill: #e20000;");
        }
        // If todo is complete, grey out the background
        if (todo.getCompleteTime() != null) {
            cardPane.setStyle("-fx-background-color: #c3cbcd");
        }
        initTags(todo);
    }

    private void initTags(ReadOnlyTodo todo) {
        todo.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
