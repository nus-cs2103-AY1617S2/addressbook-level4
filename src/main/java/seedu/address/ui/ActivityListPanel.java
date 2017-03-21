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
import seedu.address.commons.events.ui.ActivityPanelSelectionChangedEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.person.ReadOnlyActivity;

/**
 * Panel containing the list of activities.
 */
public class ActivityListPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(ActivityListPanel.class);
    private static final String FXML = "ActivityListPanel.fxml";

    @FXML
    private ListView<ReadOnlyActivity> activityListView;

    public ActivityListPanel(AnchorPane activityListPlaceholder, ObservableList<ReadOnlyActivity> activityList) {
        super(FXML);
        setConnections(activityList);
        addToPlaceholder(activityListPlaceholder);
    }

    private void setConnections(ObservableList<ReadOnlyActivity> activityList) {
        activityListView.setItems(activityList);
        activityListView.setCellFactory(listView -> new ActivityListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        activityListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in activity list panel changed to : '" + newValue + "'");
                        raise(new ActivityPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            activityListView.scrollTo(index);
            activityListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class ActivityListViewCell extends ListCell<ReadOnlyActivity> {

        @Override
        protected void updateItem(ReadOnlyActivity activity, boolean empty) {
            super.updateItem(activity, empty);

            if (empty || activity == null) {
                setGraphic(null);
                setText(null);
            } else if (activity.getFromDate().value!=null){
                setGraphic(new EventCard(activity, getIndex() + 1).getRoot());
            } else if (activity.getPriority().value!=null){
                setGraphic(new ActivityCard(activity, getIndex() + 1).getRoot());
            } else{
                setGraphic(new DeadlineCard(activity, getIndex() + 1).getRoot());
            }
        }
    }

}
