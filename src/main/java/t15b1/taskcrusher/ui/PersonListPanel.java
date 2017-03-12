package t15b1.taskcrusher.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import t15b1.taskcrusher.commons.core.LogsCenter;
import t15b1.taskcrusher.commons.events.ui.PersonPanelSelectionChangedEvent;
import t15b1.taskcrusher.commons.util.FxViewUtil;
import t15b1.taskcrusher.model.task.ReadOnlyTask;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private static final String FXML = "PersonListPanel.fxml";

    @FXML
    private ListView<ReadOnlyTask> personListView;

    public PersonListPanel(AnchorPane personListPlaceholder, ObservableList<ReadOnlyTask> personList) {
        super(FXML);
        setConnections(personList);
        addToPlaceholder(personListPlaceholder);
    }

    private void setConnections(ObservableList<ReadOnlyTask> personList) {
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        personListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new PersonPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            personListView.scrollTo(index);
            personListView.getSelectionModel().clearAndSelect(index);
        });
    }

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
