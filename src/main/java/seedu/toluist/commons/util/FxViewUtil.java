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

    public static void makeFullWidth(Node node) {
        applyAnchorBoundaryParameters(node, 0.0, 0.0, 0.0, 0.0);
    }

    public static void addStyleClass(Node node, String styleClass) {
        if (!node.getStyleClass().contains(styleClass)) {
            node.getStyleClass().add(styleClass);
        }
    }
}
