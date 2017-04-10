package seedu.taskit.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.taskit.commons.core.LogsCenter;
import seedu.taskit.commons.events.ui.NewResultAvailableEvent;
import seedu.taskit.commons.util.FxViewUtil;
import seedu.taskit.logic.Logic;
import seedu.taskit.logic.commands.CommandResult;
import seedu.taskit.logic.commands.ListCommand;
import seedu.taskit.logic.commands.exceptions.CommandException;

import static seedu.taskit.logic.parser.CliSyntax.ALL;
import static seedu.taskit.logic.parser.CliSyntax.DEADLINE;
import static seedu.taskit.logic.parser.CliSyntax.EVENT;
import static seedu.taskit.logic.parser.CliSyntax.FLOATING;
import static seedu.taskit.logic.parser.CliSyntax.OVERDUE;
import static seedu.taskit.logic.parser.CliSyntax.TODAY;
import static seedu.taskit.ui.MenuBarPanel.MENU_DEADLINE_TASK;
import static seedu.taskit.ui.MenuBarPanel.MENU_EVENT_TASK;
import static seedu.taskit.ui.MenuBarPanel.MENU_FLOATING_TASK;
import static seedu.taskit.ui.MenuBarPanel.MENU_OVERDUE_TASK;
import static seedu.taskit.ui.MenuBarPanel.MENU_TODAY_TASK;

public class CommandBox extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";
    public static final String ERROR_STYLE_CLASS = "error";

    private final Logic logic;

    @FXML
    private TextField commandTextField;

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

    //@@author A0141872E
    /**
     * display the relevant task lists based on the MenuBarSelectionChanged Event parameter
     */
    public void handleMenuBarSelectionChanged(String parameter) {
        try {
            String command = changeToListCommandFormat(parameter);
            CommandResult commandResult = logic.execute(command);
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException e) {
            // handle command failure
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    /**
     * change the parameter into correct ListCommand format
     */
    private String changeToListCommandFormat(String parameter) {
        switch (parameter) {

        case MENU_FLOATING_TASK:
            return ListCommand.COMMAND_WORD + " " + FLOATING;

        case MENU_EVENT_TASK:
            return ListCommand.COMMAND_WORD + " " + EVENT;

        case MENU_DEADLINE_TASK:
            return ListCommand.COMMAND_WORD + " " + DEADLINE;

        case MENU_TODAY_TASK:
            return ListCommand.COMMAND_WORD + " " + TODAY;

        case MENU_OVERDUE_TASK:
            return ListCommand.COMMAND_WORD + " " + OVERDUE;

        default:
            return ListCommand.COMMAND_WORD + " " + ALL;
        }
    }

}
