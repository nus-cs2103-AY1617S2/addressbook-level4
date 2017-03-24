package seedu.toluist.ui.view;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.util.FxViewUtil;
import seedu.toluist.dispatcher.Dispatcher;
import seedu.toluist.ui.UiStore;

public class CommandBox extends UiView {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
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
        FxViewUtil.setKeyCombination(commandTextField, new KeyCodeCombination(KeyCode.RIGHT),
            event -> handleCommandInputSelectSuggestedCommand());
    }

    @FXML
    private void handleCommandInputEntered() {
        dispatcher.dispatchRecordingHistory(commandTextField.getText());
        commandTextField.setText("");
    }

    private void handleCommandInputChanged(String newCommand) {
        UiStore.getInstance().setSuggestedCommands(
                new ArrayList(dispatcher.getPredictedCommands(newCommand)));
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

    private void handleCommandInputSelectSuggestedCommand() {
        UiStore store = UiStore.getInstance();
        List<String> suggestedCommands = store.getObservableSuggestedCommands();

        if (suggestedCommands.isEmpty()) {
            return;
        }

        int index = store.getObservableSuggestedCommandIndex().get();
        if (index > -1) {
            store.setCommandText(suggestedCommands.get(index));
        }
    }
}
