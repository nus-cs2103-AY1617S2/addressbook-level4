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
import seedu.address.commons.events.ui.LeftPanelSelecttionChangedEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.label.Label;
import seedu.address.model.task.ReadOnlyTask;

public class LeftPanel extends UiPart<Region> {

    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "LeftPanel.fxml";

    @FXML
    private javafx.scene.control.Label todayListView;

    @FXML
    private javafx.scene.control.Label calendarListView;

    @FXML
    private ListView<Label> labelListView;

    public LeftPanel(AnchorPane leftListPlaceholder, ObservableList<ReadOnlyTask> taskList, ObservableList<Label> labelList) {
        super(FXML);
        setConnections(labelList);
        setTodayListView(taskList);
        setCalendarListView(taskList);
        addToPlaceholder(leftListPlaceholder);
    }

    public void setTodayListView(ObservableList<ReadOnlyTask> taskList) {
        todayListView.setText("Today " + taskList.size());
    }

    public void setCalendarListView(ObservableList<ReadOnlyTask> taskList) {
        calendarListView.setText("Calendar");
    }

    public void setConnections(ObservableList<Label> labelList) {
        labelListView.setItems(labelList);
        labelListView.setCellFactory(listView -> new LabelListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        labelListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in label left list panel changed to : '" + newValue + "'");
                        raise(new LeftPanelSelecttionChangedEvent());
                    }
                });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            labelListView.scrollTo(index);
            labelListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class LabelListViewCell extends ListCell<Label> {

        @Override
        protected void updateItem(Label label, boolean empty) {
            super.updateItem(label, empty);

            if (empty || label == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new LabelCard(label).getRoot());
            }
        }
    }
}
