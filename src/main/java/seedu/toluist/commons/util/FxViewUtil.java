package seedu.toluist.commons.util;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Contains utility methods for JavaFX views
 */
public class FxViewUtil {

    public static void applyAnchorBoundaryParameters(Node node, double left, double right, double top, double bottom) {
        AnchorPane.setBottomAnchor(node, bottom);
        AnchorPane.setLeftAnchor(node, left);
        AnchorPane.setRightAnchor(node, right);
        AnchorPane.setTopAnchor(node, top);
    }

    /**
     * Sets the given image as the icon for the given stage.
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    public static void setStageIcon(Stage stage, String iconSource) {
        stage.getIcons().setAll(AppUtil.getImage(iconSource));
    }

    //@@author A0131125Y
    /**
     * Apply anchor 0 to all 4 corners of a node, making it take the width of the parent
     * @param node a given node
     */
    public static void makeFullWidth(Node node) {
        applyAnchorBoundaryParameters(node, 0.0, 0.0, 0.0, 0.0);
    }

    /**
     * Add a style class to a node. Style class won't be re-added if the node already has it
     * @param node a given node
     * @param styleClass a given style class string
     */
    public static void addStyleClass(Node node, String styleClass) {
        if (!node.getStyleClass().contains(styleClass)) {
            node.getStyleClass().add(styleClass);
        }
    }
}
