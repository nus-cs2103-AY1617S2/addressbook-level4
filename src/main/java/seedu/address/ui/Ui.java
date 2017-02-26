package seedu.address.ui;

import javafx.stage.Stage;
import seedu.address.commons.core.Config;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /** Stops the UI. */
    void stop();

    void init(Config config);

}
