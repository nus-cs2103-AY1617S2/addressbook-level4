package seedu.address.ui.view;

import javafx.scene.layout.Pane;
import seedu.address.ui.UiPart;

import javax.swing.plaf.synth.Region;
import java.net.URL;

/**
 * Created by louis on 22/2/17.
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
