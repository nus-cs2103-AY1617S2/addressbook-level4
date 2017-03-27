package seedu.todolist.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.commons.events.ui.TodoPanelSelectionChangedEvent;
import seedu.todolist.commons.util.FxViewUtil;
import seedu.todolist.model.todo.ReadOnlyTodo;

/**
 * Panel containing the list of todos.
 */
public class TodoListPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TodoListPanel.class);
    private static final String FXML = "TodoListPanel.fxml";

    @FXML
    private ListView<ReadOnlyTodo> todoListView;

    public TodoListPanel(AnchorPane todoListPlaceholder, ObservableList<ReadOnlyTodo> todoList) {
        super(FXML);
        setConnections(todoList);
        addToPlaceholder(todoListPlaceholder);
    }

    private void setConnections(ObservableList<ReadOnlyTodo> todoList) {
        todoListView.setItems(todoList);
        todoListView.setCellFactory(listView -> new TodoListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        // SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        todoListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in todo list panel changed to : '" + newValue + "'");
                        raise(new TodoPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            todoListView.scrollTo(index);
            todoListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class TodoListViewCell extends ListCell<ReadOnlyTodo> {

        @Override
        protected void updateItem(ReadOnlyTodo todo, boolean empty) {
            super.updateItem(todo, empty);

            if (empty || todo == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TodoCard(todo, getIndex() + 1).getRoot());
            }
        }
    }

}
