package seedu.toluist.ui.view;

import javafx.scene.layout.Pane;
import seedu.toluist.ui.UiPart;

/**
 * UiView is basic building block for the Ui
 * To show a UiView, {@link #render()} needs to be explicitly called
 * Also a parent node is required through {@link #setParent(Pane)}
 */
public class UiView extends UiPart<Pane> {
    private Pane parent;

    public UiView(String fxmlFileName) {
        super(fxmlFileName);
    }

    public void render() {
        viewDidMount();
    }

    public void setParent(Pane newParent) {
        if (parent == newParent) {
            return;
        }

        parent = newParent;
        newParent.getChildren().add(getRoot());
    }

    public Pane getParent() {
        return parent;
    }

    protected void viewDidMount() {} // do nothing by default
}
