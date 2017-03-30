package seedu.address.ui;

import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.model.Model;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /** Stops the UI. */
    void stop();

    /** Sets the logic of the UI */
    public void setLogic(Logic logic);

    /** Sets the model of the UI */
    public void setModel(Model model);
}
