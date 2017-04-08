package t09b1.today.ui;

/**
 * (Depreciated) CommandBox found in Main Window
 */
public class CommandBox {// extends UiPart<Region> {
    // private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    // private static final String FXML = "CommandBox.fxml";
    // public static final String ERROR_STYLE_CLASS = "error";
    //
    // private final Logic logic;
    //
    // @FXML
    // private JFXTextField commandTextFieldOld;
    //
    // public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic) {
    // super(FXML);
    // this.logic = logic;
    // addToPlaceholder(commandBoxPlaceholder);
    // commandTextFieldOld.requestFocus();
    // commandTextFieldOld.setLabelFloat(true);
    // }
    //
    // private void addToPlaceholder(AnchorPane placeHolderPane) {
    // SplitPane.setResizableWithParent(placeHolderPane, false);
    // placeHolderPane.getChildren().add(commandTextFieldOld);
    // FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
    // FxViewUtil.applyAnchorBoundaryParameters(commandTextFieldOld, 0.0, 0.0,
    // 0.0, 0.0);
    // }
    //
    // @FXML
    // private void handleCommandInputChanged() {
    // try {
    // CommandResult commandResult =
    // logic.execute(commandTextFieldOld.getText());
    //
    // // process result of the command
    // setStyleToIndicateCommandSuccess();
    // commandTextFieldOld.setText("");
    // logger.info("Result: " + commandResult.feedbackToUser);
    // raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
    // raise(new UpdateStatusBarEvent(commandResult.statusBarMessage));
    //
    // } catch (CommandException e) {
    // // handle command failure
    // setStyleToIndicateCommandFailure();
    // logger.info("Invalid command: " + commandTextFieldOld.getText());
    // raise(new NewResultAvailableEvent(e.getMessage()));
    // raise(new UpdateStatusBarEvent(e.getMessage()));
    // }
    // }
    //
    // /**
    // * Sets the command box style to indicate a successful command.
    // */
    // private void setStyleToIndicateCommandSuccess() {
    // commandTextFieldOld.getStyleClass().remove(ERROR_STYLE_CLASS);
    // }
    //
    // /**
    // * Sets the command box style to indicate a failed command.
    // */
    // private void setStyleToIndicateCommandFailure() {
    // commandTextFieldOld.getStyleClass().add(ERROR_STYLE_CLASS);
    // }
    //
    // /*
    // * public void setCommandMessage(String message) {
    // * this.commandMessage.setText(message); }
    // *
    // * @Subscribe public void handleUpdateStatusBarEvent(UpdateStatusBarEvent
    // * event) { logger.info(">>>>>>>>COMMAND MESSAGE" + event.getMessage());
    // * setCommandMessage(event.getMessage()); }
    // */

}
