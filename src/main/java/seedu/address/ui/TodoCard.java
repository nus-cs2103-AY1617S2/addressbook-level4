package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public TodoCard(ReadOnlyTodo todo, int displayedIndex) {
        super(FXML);
        name.setText(todo.getName().fullName);
        id.setText(displayedIndex + ". ");
        initTags(todo);
    }

    private void initTags(ReadOnlyTodo todo) {
        todo.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
