package seedu.toluist.ui.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import seedu.toluist.commons.util.FxViewUtil;
import seedu.toluist.ui.UiStore;

/**
 * Created by louis on 1/4/17.
 */
public class HelpListView extends UiView {

    private static final String FXML = "HelpListView.fxml";
    private static final double OFFSET_WRAPPING = 10;

    @FXML
    private ListView<String> helpListView;

    public HelpListView() {
        super(FXML);
        FxViewUtil.makeFullWidth(getRoot());
        helpListView.setCellFactory(help -> new HelpListCell());
        configureBindings();
    }

    private void configureBindings() {
        UiStore store = UiStore.getInstance();
        store.bind(this, store.getObservableHelp());
    }

    protected void viewDidMount() {
        UiStore store = UiStore.getInstance();
        ObservableList<String> helpList = FXCollections.observableArrayList();
        helpList.addAll(store.getObservableHelp());
        helpListView.setItems(helpList);
        getParent().setVisible(!helpList.isEmpty());
    }

    class HelpListCell extends ListCell<String> {

        @Override
        protected void updateItem(String helpString, boolean empty) {
            super.updateItem(helpString, empty);

            if (empty || helpString == null) {
                setGraphic(null);
                setText(null);
            } else {
                Text text = new Text(helpString);
                text.wrappingWidthProperty().bind(helpListView.widthProperty().subtract(OFFSET_WRAPPING));
                text.textProperty().bind(itemProperty());
                setPrefWidth(0);
                setGraphic(text);
            }
        }
    }
}
