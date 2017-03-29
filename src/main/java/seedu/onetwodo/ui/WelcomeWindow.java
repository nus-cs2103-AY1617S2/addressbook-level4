package seedu.onetwodo.ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import javafx.stage.Stage;

import seedu.onetwodo.commons.core.GuiSettings;
import seedu.onetwodo.model.UserPrefs;

public class WelcomeWindow extends UiPart<Region> {

    private static final String FXML = "WelcomeWindow.fxml";
    private static final String FONT_AVENIR = "/fonts/avenir-light.ttf";
    private static final int MIN_HEIGHT = 500;
    private static final int MIN_WIDTH = 550;

    private Stage secondaryStage;
    // private Logic logic;

    @FXML
    private Label label;

    public WelcomeWindow(Stage secondaryStage) {
        super(FXML);

        // Set dependencies
        this.secondaryStage = secondaryStage;

        // Configure the UI
        setTitle("Welcome screen");
        setWindowSize();
        Scene scene = new Scene(getRoot());
        secondaryStage.setScene(scene);
    }

    private void setTitle(String appTitle) {
        secondaryStage.setTitle(appTitle);
    }

    private void setWindowSize() {
        secondaryStage.setMinHeight(MIN_HEIGHT);
        secondaryStage.setMinWidth(MIN_WIDTH);
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(secondaryStage.getWidth(), secondaryStage.getHeight(), (int) secondaryStage.getX(),
                (int) secondaryStage.getY());
    }

    void show() {
        secondaryStage.show();
    }

}
