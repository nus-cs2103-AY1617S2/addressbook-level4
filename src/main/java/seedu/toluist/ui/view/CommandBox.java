package seedu.toluist.ui.view;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.util.FxViewUtil;
import seedu.toluist.dispatcher.CommandDispatcher;

public class CommandBox extends UiView {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";
    public static final String ERROR_STYLE_CLASS = "error";

    private final CommandDispatcher dispatcher = CommandDispatcher.getInstance();

    @FXML
    private TextField commandTextField;

    public CommandBox() {
        super(FXML);
    }

    @Override
    protected void viewDidMount () {
        FxViewUtil.makeFullWidth(getRoot());
        FxViewUtil.makeFullWidth(commandTextField);
    }

    @FXML
    private void handleCommandInputChanged() {
        dispatcher.dispatch(commandTextField.getText());
        setStyleToIndicateCommandSuccess();
        commandTextField.setText("");
//        logger.info("Result: " + commandResult.feedbackToUser);
//        raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
    }


    /**
     * Sets the command box style to indicate a successful command.
     */
    private void setStyleToIndicateCommandSuccess() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        commandTextField.getStyleClass().add(ERROR_STYLE_CLASS);
    }
}
