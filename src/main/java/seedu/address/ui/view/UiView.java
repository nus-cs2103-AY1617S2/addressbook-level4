package seedu.address.ui.view;

import javafx.scene.layout.Pane;
import seedu.address.ui.UiPart;

/**
 * UiView is basic building block for the Ui
 * To show a UiView, {@link #render()} needs to be expicitly called
 * Also a parent node is required through {@link #setParent(Pane)}
 */
abstract class UiView extends UiPart<Pane> {
    private Pane parent;

    public UiView (String fxmlFileName) {
        super(fxmlFileName);
    }

    public void render() {
        reset();
        viewDidMount();
    }

    public void setParent(Pane newParent) {
        if (parent == newParent) {
            return;
        }

        reset();

        parent = newParent;
        newParent.getChildren().add(getRoot());
    }

    public Pane getParent() {
        return parent;
    }

    protected void viewDidMount() {} // do nothing by default

    private void reset() {
        if (parent != null) {
            parent.getChildren().remove(getRoot());
            parent.getChildren().add(getRoot());
        }
    }
}
