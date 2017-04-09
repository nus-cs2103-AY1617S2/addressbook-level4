# A0131125Y-reused
###### /java/seedu/toluist/commons/core/GuiSettings.java
``` java
package seedu.toluist.commons.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the GUI settings.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 600;
    private static final double DEFAULT_WIDTH = 500;

    private Double windowWidth;
    private Double windowHeight;
    private Point windowCoordinates;

    public GuiSettings() {
        this.windowWidth = DEFAULT_WIDTH;
        this.windowHeight = DEFAULT_HEIGHT;
        this.windowCoordinates = null; // null represent no coordinates
    }

    public GuiSettings(Double windowWidth, Double windowHeight, int xPosition, int yPosition) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.windowCoordinates = new Point(xPosition, yPosition);
    }

    public Double getWindowWidth() {
        return windowWidth;
    }

    public Double getWindowHeight() {
        return windowHeight;
    }

    public Point getWindowCoordinates() {
        return windowCoordinates;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GuiSettings)) { //this handles null as well.
            return false;
        }

        GuiSettings o = (GuiSettings) other;

        return Objects.equals(windowWidth, o.windowWidth)
                && Objects.equals(windowHeight, o.windowHeight)
                && Objects.equals(windowCoordinates, o.windowCoordinates);
    }
}
```
###### /java/seedu/toluist/ui/commons/CommandResult.java
``` java
package seedu.toluist.ui.commons;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public enum CommandResultType {
        SUCCESS, FAILURE
    }

    private final String feedbackToUser;
    private final CommandResultType type;

    public CommandResult(String feedbackToUser) {
        assert feedbackToUser != null;
        this.feedbackToUser = feedbackToUser;
        this.type = CommandResultType.SUCCESS;
    }

    public CommandResult(String feedbackToUser, CommandResultType type) {
        assert feedbackToUser != null;
        this.feedbackToUser = feedbackToUser;
        this.type = type;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public CommandResultType getCommandResultType() {
        return type;
    }
}
```
###### /java/seedu/toluist/ui/Ui.java
``` java
package seedu.toluist.ui;

import javafx.stage.Stage;
import seedu.toluist.dispatcher.Dispatcher;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /** Stops the UI. */
    void stop();

    void init(Dispatcher dispatcher);
}
```
