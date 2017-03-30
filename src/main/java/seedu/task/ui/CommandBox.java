package seedu.task.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.ui.NewResultAvailableEvent;
import seedu.task.commons.util.FxViewUtil;
import seedu.task.logic.Logic;
import seedu.task.logic.commands.CommandResult;
import seedu.task.logic.commands.exceptions.CommandException;

public class CommandBox extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBoxDefault.fxml";
    protected static final String FXML_Light = "CommandBoxLight.fxml";
    protected static final String FXML_Dark = "CommandBoxDark.fxml";
    public static final String ERROR_STYLE_CLASS = "error";

    private final Logic logic;

    @FXML
    private TextField commandTextField;

    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic) {
        super(FXML);
        this.logic = logic;
        addToPlaceholder(commandBoxPlaceholder);
    }
  //@@author A0142487Y-reused
    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic, String fxml) {
        super(fxml);
        this.logic = logic;
        addToPlaceholder(commandBoxPlaceholder);
    }

    //@@author
    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    @FXML
    private void handleCommandInputChanged() {
        try {
            CommandResult commandResult = logic.execute(commandTextField.getText());

            // process result of the command
            setStyleToIndicateCommandSuccess();
            commandTextField.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException e) {
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
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

    //@@author A0142939W
    /**
     * Sets the text in command box.
     */
    public void setText(String text) {
        commandTextField.setText(text);
    }

    //@@author A0142939W
    /**
     * Sets the current focus to be the textbox
     */
    public void requestFocus() {
        commandTextField.requestFocus();
    }

    //@@author A0142939W
    /**
     * Sets the focus to be at the end of the textbox
     */
    public void end() {
        commandTextField.end();
    }

    //@@author A0142939W
    /**
     * Types the given text in the commandbox and sets focus there
     */
    public void type(String text) {
        setText(text);
        requestFocus();
        end();
    }

}
