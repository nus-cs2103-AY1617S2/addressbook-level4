package seedu.toluist.ui.view;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import seedu.toluist.commons.util.FxViewUtil;
import seedu.toluist.dispatcher.Dispatcher;
import seedu.toluist.ui.UiStore;

public class CommandBox extends UiView {
    private static final String STYLE_CLASS_ERROR = "error";
    private static final String FXML = "CommandBox.fxml";
    private final Dispatcher dispatcher;

    @FXML
    private TextField commandTextField;

    public CommandBox(Dispatcher dispatcher) {
        super(FXML);
        this.dispatcher = dispatcher;
        configureBindings();
        configureKeyCombinations();
    }

    @Override
    protected void viewDidMount () {
        FxViewUtil.makeFullWidth(getRoot());
        FxViewUtil.makeFullWidth(commandTextField);
        UiStore store = UiStore.getInstance();
        commandTextField.setText(store.getObservableCommandText().getValue());
        commandTextField.end();
    }

    private void configureBindings() {
        UiStore store = UiStore.getInstance();
        store.bind(this, store.getObservableCommandText());
        commandTextField.textProperty()
                .addListener((observable, oldValue, newValue) -> handleCommandInputChanged(newValue));
    }

    private void configureKeyCombinations() {
        FxViewUtil.setKeyCombination(commandTextField, new KeyCodeCombination(KeyCode.TAB),
            event -> handleCommandInputAutoComplete());
        FxViewUtil.setKeyCombination(commandTextField, new KeyCodeCombination(KeyCode.ENTER),
            event -> handleCommandInputSelectSuggestedCommand());
    }

    private void dispatchCommand() {
        dispatcher.dispatchRecordingHistory(commandTextField.getText());
        commandTextField.setText("");
    }

    private void handleCommandInputChanged(String newCommand) {
        List<String> suggestedCommands = new ArrayList(dispatcher.getPredictedCommands(newCommand));
        UiStore.getInstance().setSuggestedCommands(suggestedCommands);
        if (!newCommand.isEmpty() && suggestedCommands.isEmpty()) {
            FxViewUtil.addStyleClass(commandTextField, STYLE_CLASS_ERROR);
        } else {
            FxViewUtil.removeStyleClass(commandTextField, STYLE_CLASS_ERROR);
        }
    }

    private void handleCommandInputAutoComplete() {
        UiStore store = UiStore.getInstance();
        store.incrementSuggestedCommandIndex();

        List<String> suggestedCommands = store.getObservableSuggestedCommands();
        if (suggestedCommands.size() == 1) {
            commandTextField.setText(suggestedCommands.get(0));
            commandTextField.end();
        }
    }

    /**
     * Handle selection of suggested command
     * If current text matches suggested command, or if no suggested command selected, dispatch the command
     * Otherwise auto-complete it to match the command
     */
    private void handleCommandInputSelectSuggestedCommand() {
        UiStore store = UiStore.getInstance();
        List<String> suggestedCommands = store.getObservableSuggestedCommands();
        int index = store.getObservableSuggestedCommandIndex().get();

        if (suggestedCommands.isEmpty()) {
            return;
        }

        if (index == UiStore.INVALID_SUGGESTION_INDEX
                || suggestedCommands.get(index).equals(commandTextField.getText())) {
            dispatchCommand();
            return;
        }

        store.setCommandText(suggestedCommands.get(index));
    }
}
