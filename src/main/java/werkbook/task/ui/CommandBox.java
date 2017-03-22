package werkbook.task.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import werkbook.task.commons.core.CommandTexts;
import werkbook.task.commons.core.LogsCenter;
import werkbook.task.commons.events.ui.NewResultAvailableEvent;
import werkbook.task.commons.util.FxViewUtil;
import werkbook.task.logic.Logic;
import werkbook.task.logic.commands.CommandResult;
import werkbook.task.logic.commands.exceptions.CommandException;

public class CommandBox extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";
    public static final String ERROR_STYLE_CLASS = "error";

    private final Logic logic;

    @FXML
    private ComboBox<String> commandTextField;

    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic) {
        super(FXML);
        this.logic = logic;
        addToPlaceholder(commandBoxPlaceholder);
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);

        //**author A0140462R
        commandTextField.getEditor().setOnKeyReleased(event -> {
            if (!(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.UP)) {
                handleInputMethodTextChanged(event);
                commandTextField.getEditor().positionCaret(commandTextField.getEditor().getText().length() + 1);
            }
        });

        commandTextField.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleCommandInputChanged();
            }
        });

    }

    @FXML
    private void handleCommandInputChanged() {
        try {
            CommandResult commandResult = logic.execute(commandTextField.getEditor().getText());

            // process result of the command
            setStyleToIndicateCommandSuccess();
            commandTextField.getEditor().clear();
            commandTextField.getEditor().setText("");
            commandTextField.getSelectionModel().clearSelection();
            commandTextField.hide();
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException e) {
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getEditor().getText());
            commandTextField.hide();
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    private void handleInputMethodTextChanged(KeyEvent event) {
        String userInput = new String(commandTextField.getEditor().getText());

        if (commandTextField.getSelectionModel().getSelectedItem() != null) {
            userInput.concat(event.getText());
        }
        commandTextField.getItems().clear();
        ArrayList<String> suggestions = new ArrayList<String>();
        for (String s : CommandTexts.COMMAND_TEXT_LIST) {
            if (s.contains(userInput)) {
                suggestions.add(s);
            }
        }
        commandTextField.getItems().addAll(suggestions);
        commandTextField.getEditor().setText(userInput);
        commandTextField.getEditor().positionCaret(userInput.length() + 1);
        commandTextField.show();
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
