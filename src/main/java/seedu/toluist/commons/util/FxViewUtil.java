package seedu.toluist.commons.util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
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

    /**
     * Apply anchor so that the node will take full width of parent
     * @param node a node
     */
    public static void makeFullWidth(Node node) {
        applyAnchorBoundaryParameters(node, 0.0, 0.0, 0.0, 0.0);
    }

    /**
     * Add style class to a node. Prevent duplicate style class from being added
     * @param node a node
     * @param styleClass a style class string
     */
    public static void addStyleClass(Node node, String styleClass) {
        if (!node.getStyleClass().contains(styleClass)) {
            node.getStyleClass().add(styleClass);
        }
    }

    /**
     * Remove a style class from a node
     * @param node a node
     * @param styleClass a style class string
     */
    public static void removeStyleClass(Node node, String styleClass) {
        node.getStyleClass().remove(styleClass);
    }

    /**
     * Sets the key combination on node.
     * @param keyCombination the KeyCombination value of the accelerator
     * @param handler the event handler
     */
    public static void setKeyCombination(Node node, KeyCombination keyCombination,
                                         EventHandler<ActionEvent> handler) {
        node.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (keyCombination.match(event)) {
                handler.handle(new ActionEvent());
                event.consume();
            }
        });
    }

}
