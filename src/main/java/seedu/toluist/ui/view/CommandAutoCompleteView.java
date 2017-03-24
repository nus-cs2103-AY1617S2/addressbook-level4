package seedu.toluist.ui.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import seedu.toluist.commons.core.Config;
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
    private static final String SELECTED_SUGGESTION_STYLE_CLASS = "selected";
    private static final String SUGGESTION_STYLE_CLASS = "command-suggestion";

    @FXML
    private VBox suggestedCommandList;

    public CommandAutoCompleteView() {
        super(FXML);
        UiStore store = UiStore.getInstance();
        store.bind(this, store.getObservableSuggestedCommands());
        store.bind(this, store.getObservableSuggestedCommandIndex());
    }

    @Override
    protected void viewDidMount() {
        AnchorPane.setBottomAnchor(getRoot(), 0.0);
        AnchorPane.setLeftAnchor(getRoot(), 0.0);
        AnchorPane.setRightAnchor(getRoot(), 0.0);

        suggestedCommandList.getChildren().clear();

        UiStore store = UiStore.getInstance();
        ObservableList<String> observableSuggestedCommands = store.getObservableSuggestedCommands();

        for (int i = 0; i < observableSuggestedCommands.size(); i++) {
            addCommandSuggestion(observableSuggestedCommands.get(i), i);
        }
    }

    private void addCommandSuggestion(String suggestion, int index) {
        Label label = new Label(suggestion);
        label.setPrefHeight(Control.USE_COMPUTED_SIZE);
        AnchorPane.setLeftAnchor(label, 0.0);
        AnchorPane.setRightAnchor(label, 0.0);
        FxViewUtil.addStyleClass(label, SUGGESTION_STYLE_CLASS);
        if (index == UiStore.getInstance().getObservableSuggestedCommandIndex().get()) {
            FxViewUtil.addStyleClass(label, SELECTED_SUGGESTION_STYLE_CLASS);
        }
        suggestedCommandList.getChildren().add(label);
    }
}
