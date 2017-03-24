package seedu.toluist.ui.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import seedu.toluist.commons.core.SwitchConfig;
import seedu.toluist.commons.util.FxViewUtil;
import seedu.toluist.model.Task;
import seedu.toluist.model.TaskSwitchPredicate;
import seedu.toluist.ui.UiStore;

import java.util.Observable;

/**
 * View to display suggest command
 */
public class CommandAutoCompleteView extends UiView {

    private static final String FXML = "CommandAutoCompleteView.fxml";

    @FXML
    private ListView<String> suggestedCommandListView;

    public CommandAutoCompleteView() {
        super(FXML);
        UiStore store = UiStore.getInstance();
        ObservableList<String> list = FXCollections.observableArrayList("yes", "please", "can", "you");
        suggestedCommandListView.setItems(list);
        suggestedCommandListView.setCellFactory(listView -> new CommandAutoCompleteView.ViewCell());
    }

    class ViewCell extends ListCell<String> {

        @Override
        protected void updateItem(String text, boolean empty) {
            super.updateItem(text, empty);

            if (empty) {
                setGraphic(null);
                setText(null);
            } else {
                Label label = new Label();
                label.setText(text);
                setGraphic(label);
            }
        }
    }

    @Override
    protected void viewDidMount() {
        AnchorPane.setBottomAnchor(getRoot(), 0.0);
        AnchorPane.setLeftAnchor(getRoot(), 0.0);
        AnchorPane.setRightAnchor(getRoot(), 0.0);
    }
}
