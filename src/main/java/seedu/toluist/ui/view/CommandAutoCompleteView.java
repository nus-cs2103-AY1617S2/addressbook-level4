package seedu.toluist.ui.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.VBox;
import seedu.toluist.commons.util.FxViewUtil;
import seedu.toluist.ui.UiStore;

/**
 * View to display suggest command
 */
public class CommandAutoCompleteView extends UiView {

    private static final String FXML = "CommandAutoCompleteView.fxml";
    private static final String STYLE_CLASS_SELECTED_SUGGESTION = "selected";
    private static final String STYLE_CLASS_SUGGESTION = "command-suggestion";
    private static final double SUGGESTION_HEIGHT = 20;

    @FXML
    private VBox suggestedCommandList;

    public CommandAutoCompleteView() {
        super(FXML);
        configureBindings();
    }

    private void configureBindings() {
        UiStore store = UiStore.getInstance();
        store.bind(this, store.getObservableSuggestedCommands());
        store.bind(this, store.getObservableSuggestedCommandIndex());
        store.bind(this, store.getObservableCommandTextWidth());
    }

    @Override
    protected void viewDidMount() {
        suggestedCommandList.getChildren().clear();
        UiStore store = UiStore.getInstance();
        ObservableList<String> observableSuggestedCommands = store.getObservableSuggestedCommands();
        getRoot().setVisible(!observableSuggestedCommands.isEmpty());
        for (int i = 0; i < observableSuggestedCommands.size(); i++) {
            addCommandSuggestion(observableSuggestedCommands.get(i), i);
        }
        getRoot().translateXProperty().setValue(
                Math.min(store.getObservableCommandTextWidth().getValue() / 2,
                        getParent().getWidth() / 2 - getRoot().getWidth()));
    }

    /**
     * Add command suggestion row to root
     * @param suggestion suggestion
     * @param index row index
     */
    private void addCommandSuggestion(String suggestion, int index) {
        Label label = new Label(suggestion);
        label.setPrefHeight(SUGGESTION_HEIGHT);
        AnchorPane.setLeftAnchor(label, 0.0);
        AnchorPane.setRightAnchor(label, 0.0);
        FxViewUtil.addStyleClass(label, STYLE_CLASS_SUGGESTION);
        if (index == UiStore.getInstance().getObservableSuggestedCommandIndex().get()) {
            FxViewUtil.addStyleClass(label, STYLE_CLASS_SELECTED_SUGGESTION);
        }
        suggestedCommandList.getChildren().add(label);
    }
}
