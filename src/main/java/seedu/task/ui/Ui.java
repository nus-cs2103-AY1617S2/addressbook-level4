package seedu.task.ui;

import javafx.stage.Stage;

/**
 * API of UI component
 */
public interface Ui {

    public static final String ERROR_STYLE_CLASS = "error";

    public static final String COMPLETE_STYLE_CLASS = "complete";
    public static final String INCOMPLETE_STYLE_CLASS = "incomplete";

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /** Stops the UI. */
    void stop();

}
