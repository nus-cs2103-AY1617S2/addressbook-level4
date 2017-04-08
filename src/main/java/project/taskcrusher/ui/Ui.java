package project.taskcrusher.ui;

import javafx.stage.Stage;
import project.taskcrusher.logic.Logic;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /** Stops the UI. */
    void stop();

    /** Refreshes logic. Currently used when MainApp handles storagefile changed event*/
    void setLogic(Logic newLogic);

}
