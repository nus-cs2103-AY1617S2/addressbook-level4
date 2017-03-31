package savvytodo.ui;

import javafx.stage.Stage;
import savvytodo.logic.Logic;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /** Stops the UI. */
    void stop();

    //@@author A0140036X
    /** Updates UI with new logic */
    void setLogic(Logic logic);
}
