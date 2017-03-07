package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
<<<<<<< HEAD
import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.person.ReadOnlyTask;
<<<<<<< HEAD
=======
import seedu.address.commons.events.uiTaskPanelSelectionChangedEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.task.ReadOnlyTask;
>>>>>>> 52e701e877d7e50931eb3bb6a441c4f8af274322
=======
>>>>>>> parent of 9b5fb6b... test

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TaskListPanel.fxml";

    @FXML
<<<<<<< HEAD
<<<<<<< HEAD
    private ListView<ReadOnlyTask> personListView;

    public PersonListPanel(AnchorPane personListPlaceholder, ObservableList<ReadOnlyTask> personList) {
=======
    private ListView<ReadOnlyTask> taskListView;

    public TaskListPanel(AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> taskList) {
>>>>>>> 52e701e877d7e50931eb3bb6a441c4f8af274322
=======
    private ListView<ReadOnlyTask> personListView;

    public PersonListPanel(AnchorPane personListPlaceholder, ObservableList<ReadOnlyTask> personList) {
>>>>>>> parent of 9b5fb6b... test
        super(FXML);
        setConnections(taskList);
        addToPlaceholder(taskListPlaceholder);
    }

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> parent of 9b5fb6b... test
    private void setConnections(ObservableList<ReadOnlyTask> personList) {
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
=======
    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
>>>>>>> 52e701e877d7e50931eb3bb6a441c4f8af274322
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
<<<<<<< HEAD
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
=======
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
>>>>>>> 52e701e877d7e50931eb3bb6a441c4f8af274322
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

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> parent of 9b5fb6b... test
    class PersonListViewCell extends ListCell<ReadOnlyTask> {

        @Override
        protected void updateItem(ReadOnlyTask person, boolean empty) {
            super.updateItem(person, empty);
=======
    class TaskListViewCell extends ListCell<ReadOnlyTask> {

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);
>>>>>>> 52e701e877d7e50931eb3bb6a441c4f8af274322

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCard(task, getIndex() + 1).getRoot());
            }
        }
    }

}
