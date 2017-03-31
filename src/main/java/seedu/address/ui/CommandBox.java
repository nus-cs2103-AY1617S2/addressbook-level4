package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.ScrollingEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class CommandBox extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String EMPTY_STRING = "";

    private final Logic logic;

    private ArrayList<String> history = new ArrayList<String>();
    private int historyIndex = 0;

    @FXML
    private TextField commandTextField;

    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic) {
        super(FXML);
        this.logic = logic;
        // Preempt history with empty string
        history.add(EMPTY_STRING);
        addToPlaceholder(commandBoxPlaceholder);
        registerAsAnEventHandler(this);
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    //@@author A0144885R
    @FXML
    private void handleKeyReleased(KeyEvent event) {
        switch (event.getCode()) {

        case UP:
        case KP_UP:
            moveUpHistoryStack();
            break;

        case DOWN:
        case KP_DOWN:
            moveDownHistoryStack();
            break;

        case PAGE_UP:
            broadcastPageUpEvents();
            break;

        case PAGE_DOWN:
            broadcastPageDownEvents();
            break;

        case ENTER:
            handleInputEntered();
            break;

        default:
            break;
        }
    }

    private void broadcastPageUpEvents() {
        EventsCenter.getInstance().post(new ScrollingEvent(ScrollingEvent.SCROLL_UP));
    }

    private void broadcastPageDownEvents() {
        EventsCenter.getInstance().post(new ScrollingEvent(ScrollingEvent.SCROLL_DOWN));
    }

    private void moveUpHistoryStack() {
        historyIndex = Math.max(historyIndex - 1, 0);
        String text = history.get(historyIndex);
        commandTextField.setText(text);
        commandTextField.end();
        logger.info("Key UP pressed: text changed to " + text);
    }

    private void moveDownHistoryStack() {
        historyIndex = Math.min(historyIndex + 1, history.size() - 1);
        String text = history.get(historyIndex);
        commandTextField.setText(text);
        commandTextField.end();
        logger.info("Key DOWN pressed: text changed to " + text);
    }

    private void handleInputEntered() {
        // Update history
        history.set(history.size() - 1, commandTextField.getText());

        try {
            CommandResult commandResult = logic.execute(commandTextField.getText());
            history.add(EMPTY_STRING);
            historyIndex = history.size() - 1;

            // process result of the command
            setStyleToIndicateCommandSuccess();
            commandTextField.clear();
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException e) {
            // handle command failure
            historyIndex = history.size() - 1;
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }
    //@@author


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
