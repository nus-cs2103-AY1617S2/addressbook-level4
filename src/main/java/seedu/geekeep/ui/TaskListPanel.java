package seedu.geekeep.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.geekeep.commons.core.LogsCenter;
import seedu.geekeep.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.geekeep.commons.util.FxViewUtil;
import seedu.geekeep.model.task.ReadOnlyTask;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String EVENTFXML = "EventListPanel.fxml";
    private static final String FTASKFXML = "FloatingTaskListPanel.fxml";
    private static final String DEADLINEFXML = "DeadlineListPanel.fxml";

    @FXML
    private ListView<ReadOnlyTask> allListView;

    @FXML
    private ListView<ReadOnlyTask> upcomingListView;

    @FXML
    private ListView<ReadOnlyTask> completedListView;


    //empty panel for v0.2
    public TaskListPanel(String type, AnchorPane taskListPlaceholder) {
        super(getFxmlFromType(type));
        addToPlaceholder(taskListPlaceholder);
    }
    //usable for v0.2
    public TaskListPanel(String type, AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> allList) {
        super(getFxmlFromType(type));
        setConnections(allList);
        addToPlaceholder(taskListPlaceholder);
    }

    public TaskListPanel(AnchorPane taskListPlaceholder, String type, ObservableList<ReadOnlyTask>... categories) {
        super(getFxmlFromType(type));
        for (ObservableList<ReadOnlyTask> list : categories) {
            setConnections(list);
        }
        addToPlaceholder(taskListPlaceholder);
    }
    //strange
    private static String getFxmlFromType(String type) {
        if (type.equals("deadline")) {
            return DEADLINEFXML;
        } else if (type.equals("floatingTask")) {
            return FTASKFXML;
        } else {
            return EVENTFXML;
        }
    }
    //to-do to remove
    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        setConnections(taskList, allListView);
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList, ListView<ReadOnlyTask> taskListView) {
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new PersonListViewCell());
        setEventHandlerForSelectionChangeEvent(taskListView);
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent(ListView<ReadOnlyTask> taskListView) {
        taskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new PersonPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            upcomingListView.scrollTo(index);
            upcomingListView.getSelectionModel().clearAndSelect(index);
        });
    }

//  //to-do
//  public void scrollToVersion2() {
//
//  }

    class PersonListViewCell extends ListCell<ReadOnlyTask> {

        @Override
        protected void updateItem(ReadOnlyTask person, boolean empty) {
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
