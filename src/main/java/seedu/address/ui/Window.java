package seedu.address.ui;

import javafx.scene.Scene;
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
        logger.fine("Showing " + stage.getTitle());
        stage.show();
    }

    protected void hide() {
        stage.hide();
    }

}
