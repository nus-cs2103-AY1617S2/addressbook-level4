package org.teamstbf.yats.ui;

import java.util.logging.Logger;

import org.teamstbf.yats.commons.core.LogsCenter;
import org.teamstbf.yats.commons.events.ui.EventPanelSelectionChangedEvent;
import org.teamstbf.yats.commons.util.FxViewUtil;
import org.teamstbf.yats.model.item.ReadOnlyEvent;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "PersonListPanel.fxml";

    private static final String FXMLPERSON = "PersonListCard.fxml";
    private static final String FXMLPERSONDONE = "PersonListCardDone.fxml";

    @FXML
    private ListView<ReadOnlyEvent> personListView;

    public TaskListPanel(AnchorPane eventListPlaceholder, ObservableList<ReadOnlyEvent> observableList) {
        super(FXML);
        setConnections(observableList);
        addToPlaceholder(eventListPlaceholder);
    }

    private void setConnections(ObservableList<ReadOnlyEvent> observableList) {
        personListView.setItems(observableList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        personListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new EventPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            personListView.scrollTo(index);
            personListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class PersonListViewCell extends ListCell<ReadOnlyEvent> {

        @Override
        protected void updateItem(ReadOnlyEvent person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                if (person.getIsDone().getValue().equals("Yes")) {
                    setGraphic(new TaskCard(person, getIndex() + 1, FXMLPERSONDONE).getRoot());
                } else {
                    setGraphic(new TaskCard(person, getIndex() + 1, FXMLPERSON).getRoot());
                }
            }
        }
    }

}
