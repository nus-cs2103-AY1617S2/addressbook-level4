package seedu.address.ui.view;

import javafx.scene.layout.Pane;
import seedu.address.ui.UiPart;

/**
 * UiComponent is basic building block for the Ui
 * It always require a parent Pane to be passed in the constructor
 * To show a UiComponent, {@link #render()} can be called
 */
abstract class UiComponent extends UiPart<Pane> {
    private final Pane parent;

    public UiComponent(String fxmlFileName, Pane parent) {
        super(fxmlFileName);
        this.parent = parent;
    }

    public void render() {
        parent.getChildren().setAll(getRoot());
        viewDidMount();
    }

    public Pane getParent() {
        return parent;
    }

    protected void viewDidMount() {} // do nothing by default
}
