package seedu.toluist.ui.view;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.util.FxViewUtil;
import seedu.toluist.commons.util.StringUtil;
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
        UiStore store = UiStore.getInstance();
        store.bind(this, store.getObservableCommandText());
        commandTextField.textProperty().addListener((observable, oldValue, newValue) ->
                handleCommandInputChanged(newValue));
        FxViewUtil.setKeyCombination(commandTextField.getParent(), new KeyCodeCombination(KeyCode.TAB),
                event -> store.incrementSuggestedCommandIndex());
    }

    @Override
    protected void viewDidMount () {
        FxViewUtil.makeFullWidth(getRoot());
        FxViewUtil.makeFullWidth(commandTextField);
        UiStore store = UiStore.getInstance();
        commandTextField.setText(store.getObservableCommandText().getValue());
        commandTextField.end();
    }

    @FXML
    private void handleCommandInputEntered() {
        dispatcher.dispatchRecordingHistory(commandTextField.getText());
        commandTextField.setText("");
    }

    private void handleCommandInputChanged(String newCommand) {
        String commandFirstWord = newCommand.trim().split("\\s+")[0];

        if (!StringUtil.isPresent(commandFirstWord)) {
            UiStore.getInstance().setSuggestedCommands(new ArrayList<>());
            return;
        }

        ArrayList<String> commandWords = new ArrayList(new TreeSet(dispatcher.getControllerKeywords()));
        List<String> filteredCommandWords = commandWords.stream()
                .filter(commandWord -> commandWord.startsWith(commandFirstWord)).collect(Collectors.toList());
        UiStore.getInstance().setSuggestedCommands(filteredCommandWords);
    }
}
