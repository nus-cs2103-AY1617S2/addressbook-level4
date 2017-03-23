package seedu.toluist.ui.view;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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
        UiStore.getInstance().bind(this, UiStore.getInstance().getObservableCommandText());
    }

    @Override
    protected void viewDidMount () {
        FxViewUtil.makeFullWidth(getRoot());
        FxViewUtil.makeFullWidth(commandTextField);
        commandTextField.setText(UiStore.getInstance().getObservableCommandText().getValue());
    }

    @FXML
    private void handleCommandInputEntered() {
        dispatcher.dispatchRecordingHistory(commandTextField.getText());
        UiStore.getInstance().setCommandText("");
    }

    @FXML
    private void handleCommandInputChanged() {
        UiStore.getInstance().setCommandText(commandTextField.getText());
    }
}
