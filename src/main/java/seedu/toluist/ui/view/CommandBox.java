//@@author A0131125Y
package seedu.toluist.ui.view;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import seedu.toluist.commons.util.FxViewUtil;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.dispatcher.Dispatcher;
import seedu.toluist.ui.UiStore;
import seedu.toluist.ui.commons.CommandResult;

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
        CommandResult commandResult = UiStore.getInstance().getObservableCommandResult().getValue();
        if (commandResult.getCommandResultType() == CommandResult.CommandResultType.FAILURE) {
            FxViewUtil.addStyleClass(commandTextField, STYLE_CLASS_ERROR);
        }
    }

    private void configureBindings() {
        UiStore store = UiStore.getInstance();
        commandTextField.textProperty().bindBidirectional(store.getCommandInputProperty());
        commandTextField.textProperty()
            .addListener(((observable, oldValue, newValue) -> handleCommandInputChanged(newValue)));
        store.bind(this, store.getObservableCommandResult());
    }

    private void configureKeyCombinations() {
        FxViewUtil.setKeyCombination(commandTextField, new KeyCodeCombination(KeyCode.TAB),
            event -> handleCommandInputAutoComplete());
        FxViewUtil.setKeyCombination(commandTextField, new KeyCodeCombination(KeyCode.ENTER),
            event -> handleCommandInputSelectSuggestedCommand());
    }

    private void dispatchCommand() {
        dispatcher.dispatchRecordingHistory(commandTextField.getText());
        UiStore.getInstance().setCommandInput("");
    }

    private void handleCommandInputChanged(String newCommand) {
        List<String> suggestedCommands = new ArrayList(dispatcher.getSuggestions(newCommand));
        UiStore uiStore = UiStore.getInstance();
        uiStore.setSuggestedCommands(suggestedCommands);
        FxViewUtil.removeStyleClass(commandTextField, STYLE_CLASS_ERROR);
    }

    private void handleCommandInputAutoComplete() {
        UiStore store = UiStore.getInstance();
        store.incrementSuggestedCommandIndex();

        List<String> suggestedCommands = store.getObservableSuggestedCommands();
        if (suggestedCommands.size() == 1) {
            store.setCommandInput(
                    StringUtil.replaceLastWord(commandTextField.getText(), suggestedCommands.get(0)));
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
        String commandText = commandTextField.getText();

        if (suggestedCommands.isEmpty()
                || index == UiStore.INDEX_INVALID_SUGGESTION
                || suggestedCommands.get(index).equalsIgnoreCase(StringUtil.getLastWord(commandText))) {
            dispatchCommand();
            return;
        }

        store.setCommandInput(StringUtil.replaceLastWord(commandText, suggestedCommands.get(index)));
        Platform.runLater(() -> commandTextField.end());
    }
}
