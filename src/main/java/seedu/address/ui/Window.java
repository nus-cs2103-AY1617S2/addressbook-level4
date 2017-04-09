package seedu.address.ui;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.UserPrefs;

public abstract class Window extends UiPart<Region> {

    protected Stage stage;

    public Window(String fxmlFileUrl, Stage stage) {
        super(fxmlFileUrl);

        this.stage = stage;
    }

    public Window(String fxmlFileName) {
        super(fxmlFileName);

        stage = createDialogStage("New Window", null, new Scene(getRoot()));
    }

    public Stage getStage() {
        return stage;
    }

    protected void setTitle(String appTitle) {
        stage.setTitle(appTitle);
    }

    /**
     * Sets the given image as the icon of the main window.
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    protected void setIcon(String iconSource) {
        FxViewUtil.setStageIcon(stage, iconSource);
    }

    /**
     * Sets the default size based on user preferences.
     */
    protected void setWindowDefaultSize(UserPrefs prefs) {
        stage.setHeight(prefs.getGuiSettings().getWindowHeight());
        stage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            stage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            stage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    protected void setWindowMinSize(int height, int width) {
        stage.setMinHeight(height);
        stage.setMinWidth(width);
    }

    protected void show() {
        LOGGER.fine("Showing " + stage.getTitle());
        stage.show();
    }

    protected void hide() {
        stage.hide();
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    protected void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }
}
