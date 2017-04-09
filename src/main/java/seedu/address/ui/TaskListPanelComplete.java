package seedu.address.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.task.ReadOnlyTask;

//@@author A0164889E
/**
 * Panel containing the list of complete tasks.
 */
public class TaskListPanelComplete extends UiPart<Region> {
    private static final String FXML = "PersonListPanelComplete.fxml";

    @FXML
    private ListView<ReadOnlyTask> personListViewComplete;

    public TaskListPanelComplete(AnchorPane personListCompletePlaceholder, ObservableList<ReadOnlyTask> personList) {
        super(FXML);
        setConnections(personList);
        addToPlaceholder(personListCompletePlaceholder);
    }

    private void setConnections(ObservableList<ReadOnlyTask> personList) {
        personListViewComplete.setItems(personList);
        personListViewComplete.setCellFactory(listView -> new PersonListViewCellComplete());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        personListViewComplete.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        LOGGER.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new TaskPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            personListViewComplete.scrollTo(index);
            personListViewComplete.getSelectionModel().clearAndSelect(index);
        });
    }

    class PersonListViewCellComplete extends ListCell<ReadOnlyTask> {

        @Override
        protected void updateItem(ReadOnlyTask person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCardComplete(person, getIndex() + 1).getRoot());
            }
        }
    }

}
