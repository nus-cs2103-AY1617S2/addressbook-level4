package seedu.doit.commons.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the GUI settings.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 700;
    private static final double DEFAULT_WIDTH = 1100;

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
        return this.windowWidth;
    }

    public Double getWindowHeight() {
        return this.windowHeight;
    }

    public Point getWindowCoordinates() {
        return this.windowCoordinates;
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

        return Objects.equals(this.windowWidth, o.windowWidth)
            && Objects.equals(this.windowHeight, o.windowHeight)
            && Objects.equals(this.windowCoordinates.x, o.windowCoordinates.x)
            && Objects.equals(this.windowCoordinates.y, o.windowCoordinates.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.windowWidth, this.windowHeight, this.windowCoordinates);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Width : " + this.windowWidth + "\n");
        sb.append("Height : " + this.windowHeight + "\n");
        sb.append("Position : " + this.windowCoordinates);
        return sb.toString();
    }
}
