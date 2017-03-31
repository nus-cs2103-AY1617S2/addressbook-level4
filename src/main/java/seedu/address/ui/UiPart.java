package seedu.address.ui;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.BaseEvent;

/**
 * Represents a distinct part of the UI. e.g. Windows, dialogs, panels, status bars, etc.
 * It contains a scene graph with a root node of type {@code T}.
 */
public abstract class UiPart<T> {

    /** Resource folder where FXML files are stored. */
    public static final String FXML_FILE_FOLDER = "/view/";
    protected static final Logger LOGGER = LogsCenter.getLogger(HelpWindow.class);

    private FXMLLoader fxmlLoader;

    /**
     * Constructs a UiPart with the specified FXML file URL.
     * The FXML file must not specify the {@code fx:controller} attribute.
     */
    public UiPart(URL fxmlFileUrl) {
        assert fxmlFileUrl != null;
        fxmlLoader = new FXMLLoader(fxmlFileUrl);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        setStyleSheet(ThemeManager.DEFAULT_STYLESHEET);
    }

    /**
     * Constructs a UiPart using the specified FXML file within {@link #FXML_FILE_FOLDER}.
     * @see #UiPart(URL)
     */
    public UiPart(String fxmlFileName) {
        this(fxmlFileName != null ? MainApp.class.getResource(FXML_FILE_FOLDER + fxmlFileName) : null);
    }

    //@@author A0163848R
    /**
     * Sets the css file used for the UIPart's FXML.
     */
    public void setStyleSheet(String path) {
        ((Parent) fxmlLoader.getRoot()).getStylesheets().add(path);
    }
    //@@author

    /**
     * Returns the root object of the scene graph of this UiPart.
     */
    public T getRoot() {
        return fxmlLoader.getRoot();
    }

    /**
     * Raises the event via {@link EventsCenter#post(BaseEvent)}
     * @param event
     */
    protected void raise(BaseEvent event) {
        EventsCenter.getInstance().post(event);
    }

    /**
     * Registers the object as an event handler at the {@link EventsCenter}
     * @param handler usually {@code this}
     */
    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }

    /**
     * Creates a modal dialog.
     * @param title Title of the dialog.
     * @param parentStage The owner stage of the dialog.
     * @param scene The scene that will contain the dialog.
     * @return the created dialog, not yet made visible.
     */
    protected Stage createDialogStage(String title, Stage parentStage, Scene scene) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(parentStage);
        dialogStage.setScene(scene);
        return dialogStage;
    }

}
