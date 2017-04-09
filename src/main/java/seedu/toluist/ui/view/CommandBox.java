//@@author A0131125Y
package seedu.toluist.ui.view;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.text.Text;
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

    /**
     * Dispatch the command text to the Dispatcher
     */
    private void dispatchCommand() {
        dispatcher.dispatchRecordingHistory(commandTextField.getText());
        UiStore.getInstance().setCommandInput(StringUtil.EMPTY_STRING);
    }

    /**
     * Handler for command text change
     * @param newCommand the new command text
     */
    private void handleCommandInputChanged(String newCommand) {
        List<String> suggestedCommands = new ArrayList(dispatcher.getSuggestions(newCommand));
        UiStore uiStore = UiStore.getInstance();
        uiStore.setSuggestedCommands(suggestedCommands);
        uiStore.setCommandTextWidth(getTextWidth(newCommand));
        FxViewUtil.removeStyleClass(commandTextField, STYLE_CLASS_ERROR);
    }

    /**
     * Handle command auto complete on TAB
     */
    private void handleCommandInputAutoComplete() {
        UiStore store = UiStore.getInstance();
        store.incrementSuggestedCommandIndex();

        List<String> suggestedCommands = store.getObservableSuggestedCommands();
        if (suggestedCommands.size() == 1) {
            setCommandTextField(
                    StringUtil.replaceLastComponent(commandTextField.getText(), suggestedCommands.get(0)));
        }
    }

    /**
     * Handle selection of suggested command
     * If no suggestions are selected dispatch the command
     * Otherwise auto-complete it to match the command
     */
    private void handleCommandInputSelectSuggestedCommand() {
        UiStore store = UiStore.getInstance();
        List<String> suggestedCommands = store.getObservableSuggestedCommands();
        int index = store.getObservableSuggestedCommandIndex().get();
        String commandText = commandTextField.getText();

        if (index == UiStore.INDEX_INVALID_SUGGESTION) {
            dispatchCommand();
            return;
        }

        setCommandTextField(StringUtil.replaceLastComponent(commandText, suggestedCommands.get(index)));
    }

    /**
     * Set command text to text field. Also move the caret to the end
     * @param command command string
     */
    private void setCommandTextField(String command) {
        commandTextField.setText(command);
        commandTextField.end();
    }

    /**
     * Estimate the width of certain command text
     * @param text the text string
     * @return width in double
     */
    private double getTextWidth(String text) {
        Text dummyText = new Text(text);
        return dummyText.getBoundsInLocal().getWidth();
    }
}
