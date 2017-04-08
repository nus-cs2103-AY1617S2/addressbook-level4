package seedu.doit.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.reflections.Reflections;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.util.FxViewUtil;
import seedu.doit.logic.commands.Command;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Help";
    private final Stage dialogStage;
    private static final double COMMAND_COLUMN_WIDTH = 0.15;
    private static final double PARAMETER_COLUMN_WIDTH = 0.25;
    private static final double RESULT_COLUMN_WIDTH = 0.3;
    private static final double EXAMPLE_COLUMN_WIDTH = 0.3;
    private static ObservableList<Map<CommandColumns, String>> commandList = FXCollections.observableArrayList();

    @FXML
    private AnchorPane helpWindowRoot;

    @FXML
    private TableView<Map<CommandColumns, String>> commandTable;

    @FXML
    private TableColumn<Map<CommandColumns, String>, String> commandColumn;

    @FXML
    private TableColumn<Map<CommandColumns, String>, String> parameterColumn;

    @FXML
    private TableColumn<Map<CommandColumns, String>, String> resultColumn;

    @FXML
    private TableColumn<Map<CommandColumns, String>, String> exampleColumn;

    private enum CommandColumns {
        COMMAND, PARAMETER, RESULT, EXAMPLE
    }

    @FXML
    private void initialize() {
        this.commandColumn.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().get(CommandColumns.COMMAND)));
        this.parameterColumn.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().get(CommandColumns.PARAMETER)));
        this.resultColumn.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().get(CommandColumns.RESULT)));
        this.exampleColumn.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().get(CommandColumns.EXAMPLE)));
        this.commandTable.setItems(commandList);
        this.commandTable.setEditable(false);
    }
    public HelpWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot(), 1020, 350);
        //Null passed as the parent stage to make it non-modal.
        this.dialogStage = createDialogStage(TITLE, null, scene);
        FxViewUtil.setStageIcon(this.dialogStage, ICON);
    }

    public void configure() {
        this.commandColumn.prefWidthProperty().bind(this.commandTable.widthProperty().multiply(COMMAND_COLUMN_WIDTH));
        this.parameterColumn.prefWidthProperty().bind
            (this.commandTable.widthProperty().multiply(PARAMETER_COLUMN_WIDTH));
        this.resultColumn.prefWidthProperty().bind(this.commandTable.widthProperty().multiply(RESULT_COLUMN_WIDTH));
        this.exampleColumn.prefWidthProperty().bind(this.commandTable.widthProperty().multiply(EXAMPLE_COLUMN_WIDTH));
        loadHelpList();
    }

    public void show() {
        logger.fine("Showing help page about the application.");
        this.dialogStage.showAndWait();
    }

    /**
     * Uses Java reflection followed by Java stream.map() to retrieve all commands for listing on the Help
     * window dynamically
     */
    private void loadHelpList() {
        new Reflections("seedu.doit").getSubTypesOf(Command.class)
                .stream()
                .map(s -> {
                    try {
                        Map<CommandColumns, String> map = new HashMap<>();
                        map.put(CommandColumns.COMMAND, s.getMethod("getName").invoke(null).toString());
                        map.put(CommandColumns.PARAMETER, s.getMethod("getParameter").invoke(null).toString());
                        map.put(CommandColumns.RESULT, s.getMethod("getResult").invoke(null).toString());
                        map.put(CommandColumns.EXAMPLE, s.getMethod("getExample").invoke(null).toString());
                        return map;
                    } catch (NullPointerException e) {
                        return null;
                        // Suppress this exception are we expect some Commands to not conform to these methods
                    } catch (Exception e) {
                        logger.severe("Java reflection for Command class failed");
                        throw new RuntimeException();
                    }
                })
                .filter(p -> p != null) // remove nulls
                .sorted((lhs, rhs) -> lhs.get(CommandColumns.COMMAND).compareTo(rhs.get(CommandColumns.COMMAND)))
                .forEach(m -> commandList.add(m));


    }
}
