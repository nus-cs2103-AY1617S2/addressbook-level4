package seedu.todolist.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.todolist.commons.util.FxViewUtil;
import seedu.todolist.model.task.Task;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private static final String FXML = "PersonListPanel.fxml";

    @FXML
    private ListView<Task> taskListView;

    public PersonListPanel(AnchorPane personListPlaceholder, ObservableList<Task> personList) {
        super(FXML);
        setConnections(personList);
        addToPlaceholder(personListPlaceholder);
    }

    private void setConnections(ObservableList<Task> personList) {
        taskListView.setItems(personList);
        taskListView.setCellFactory(listView -> new PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        taskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new TaskPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class PersonListViewCell extends ListCell<Task> {

        @Override
        protected void updateItem(Task person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
