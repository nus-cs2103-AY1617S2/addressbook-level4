# A0142487Y-reused
###### /java/seedu/task/MainApp.java
``` java
    protected UserPrefs initPrefs(Config config) {
        assert config != null;

        String prefsFilePath = config.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty TaskManager");
            initializedPrefs = new UserPrefs();
        }

        // Update prefs file in case it was missing to begin with or there are
        // new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

```
###### /java/seedu/task/model/ModelManager.java
``` java
    private class TagQualifier implements Qualifier {

        private String tagKeyWord;

        TagQualifier(String tagKeyWord) {
            this.tagKeyWord = tagKeyWord;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return CollectionUtil.doesAnyStringMatch(task.getTags().getGenericCollection(), tagKeyWord);
        }

        @Override
        public String toString() {
            return "Tag=" +  tagKeyWord;
        }
    }

```
###### /java/seedu/task/ui/BrowserPanel.java
``` java
    /**
     * @param placeholder The AnchorPane where the BrowserPanel must be inserted
     */
    public BrowserPanel(AnchorPane placeholder, String fxml) {
        super(fxml);
        placeholder.setOnKeyPressed(Event::consume); // To prevent triggering events for typing inside the
                                                     // loaded Web page.
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().add(browser);
    }

//    public void loadTaskPage(ReadOnlyTask task) {
//        loadPage("https://www.google.com.sg/maps/place/" + task.getLocation().value.replaceAll(" ", "+"));
//    }

```
###### /java/seedu/task/ui/CommandBox.java
``` java
    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic, String fxml) {
        super(fxml);
        this.logic = logic;
        addToPlaceholder(commandBoxPlaceholder);
    }

```
###### /java/seedu/task/ui/MainWindow.java
``` java
    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic, String fxml) {
        super(fxml);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;
        this.userPrefs = prefs;

        // Configure the UI
        setTitle(config.getAppTitle());
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);

        setAccelerators();
        setScroll();
    }

```
###### /java/seedu/task/ui/ResultDisplay.java
``` java
    public ResultDisplay(AnchorPane placeHolder, String fxml) {
        super(fxml);
        resultDisplay.textProperty().bind(displayed);
        FxViewUtil.applyAnchorBoundaryParameters(resultDisplay, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(mainPane);
        registerAsAnEventHandler(this);
    }

```
###### /java/seedu/task/ui/StatusBarFooter.java
``` java
    public StatusBarFooter(AnchorPane placeHolder, String saveLocation, String fxml) {
        super(fxml);
        addToPlaceholder(placeHolder);
        setSyncStatus("Not updated yet in this session");
        setSaveLocation("./" + saveLocation);
        registerAsAnEventHandler(this);
    }

```
###### /java/seedu/task/ui/TaskCard.java
``` java
    public TaskCard(ReadOnlyTask task, int displayedIndex, String fxml) {
        super(fxml);
//        plane.setText(displayedIndex + ". " + task.getName().fullName);
//        plane.setFont(Font.font("Verdana", FontWeight.BOLD,20));
        //plane.setCollapsible(true);
      //prohibit animating
     // plane.setAnimated(false);
        plane.setExpanded(false);
        this.status = false;
        name.setText(displayedIndex + ". " + task.getName().fullName);
        name.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        //id.setText(displayedIndex + ". ");
        startDate.setText(task.getStartDate().toString());
        endDate.setText(task.getEndDate().toString());
        loc.setText(task.getLocation().value);
        if (task.isDone()) {
            //done.setText("Done");
            name.setTextFill(Color.GREEN);
            Image image = new Image(MainApp.class.getResourceAsStream("/images/tick.png"));
            name.setGraphic(new ImageView(image));
            name.setContentDisplay(ContentDisplay.RIGHT);
            //done.setFont(Font.font("Verdana", FontWeight.BOLD,20));
        } else {
            //done.setText("Not Done");
            //done.setTextFill(Color.RED);
            //Image image = new Image(MainApp.class.getResourceAsStream("/images/cross.png"));
            //name.setGraphic(new ImageView(image));
            //name.setContentDisplay(ContentDisplay.RIGHT);
            //done.setFont(Font.font("Verdana", FontWeight.BOLD,20));
        }
        remark.setText(task.getRemark().value);
        initTags(task);
    }
```
###### /java/seedu/task/ui/TaskListPanel.java
``` java
    public TaskListPanel(AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> taskList, String fxml,
            Theme theme) {
        super(fxml);
        this.theme = theme;
        setConnections(taskList);
        addToPlaceholder(taskListPlaceholder);
    }

```
###### /resources/view/CommandBoxDark.fxml
``` fxml
<?import java.net.URL?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.AnchorPane?>

<AnchorPane styleClass="anchor-pane-with-border" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <TextField fx:id="commandTextField" onAction="#handleCommandInputChanged" promptText="Enter command here..." styleClass="darktext-field">
      <stylesheets>
         <URL value="@DarkTheme.css" />
         <URL value="@Extensions.css" />
      </stylesheets></TextField>
   <stylesheets>
      <URL value="@DarkTheme.css" />
      <URL value="@Extensions.css" />
   </stylesheets>
</AnchorPane>
```
###### /resources/view/CommandBoxLight.fxml
``` fxml
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.control.TextField?>
<AnchorPane styleClass="anchor-pane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1"
            stylesheets="@DarkTheme.css">
   <TextField fx:id="commandTextField" onAction="#handleCommandInputChanged" promptText="Enter command here..."/>
</AnchorPane>

```
###### /resources/view/DarkTheme.css
``` css
.background {
    -fx-background-color: derive(#1d1d1d, 20.0%);
}

.label {
    //-fx-font-size: 11pt;
    //-fx-font-family: "Segoe UI Semibold";
    //-fx-text-fill: #555555;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11.0pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: white;
    -fx-opacity: 1.0;
}

.label-header {
    -fx-font-size: 32.0pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 1.0;
}

.text-field {
    -fx-font-size: 12.0pt;
    -fx-font-family: "Segoe UI Semibold";
}

.darktext-field{
	-fx-font-size: 12.0pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: silver;
    -fx-background-color: derive(#1d1d1d, 35.0%);
}


.tab-pane {
    -fx-padding: 0.0 0.0 0.0 1.0;
}

.tab-pane .tab-header-area {
    -fx-padding: 0.0 0.0 0.0 0.0;
    -fx-min-height: 0.0;
    -fx-max-height: 0.0;
}

.table-view {
    -fx-base: #1d1d1d;
    -fx-control-inner-background: #1d1d1d;
    -fx-background-color: #1d1d1d;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5.0;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35.0;
    -fx-border-width: 0.0 0.0 1.0 0.0;
    -fx-background-color: transparent;
    -fx-border-color:
        transparent
        transparent
        derive(-fx-base, 80.0%)
        transparent;
    -fx-border-insets: 0.0 10.0 1.0 0.0;
}

.table-view .column-header .label {
    -fx-font-size: 20.0pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-alignment: center-left;
    -fx-opacity: 1.0;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-border-color: transparent #1d1d1d transparent #1d1d1d;
    -fx-background-color: transparent, derive(#1d1d1d, 10.0%);
}

.split-pane {
    -fx-border-radius: 1.0;
    -fx-border-width: 1.0;
    -fx-background-color: derive(#1d1d1d, 20.0%);
}

.list-cell {
    -fx-label-padding: 0.0 0.0 0.0 0.0;
    -fx-graphic-text-gap : 0.0;
    -fx-padding: 0.0 0.0 0.0 0.0;
    -fx-background-color: derive(#1d1d1d, 20.0%);
}

.list-cell .label {
    //-fx-text-fill: #010504;
}

.cell_big_label {
   -fx-font-size: 20.0px;
   //-fx-text-fill: #010504;
}

.cell_small_label {
    -fx-font-size: 16.0px;
    -fx-text-fill: white;
    -fx-background-color: derive(#1d1d1d, 20.0%);
}

.anchor-pane {
     -fx-background-color: derive(#1d1d1d, 20.0%);
}

.anchor-pane-with-border {
     -fx-background-color: derive(#1d1d1d, 20.0%);
     -fx-border-color: derive(#1d1d1d, 10.0%);
     -fx-border-top-width: 1.0px;
}

.status-bar {
    -fx-background-color: derive(#1d1d1d, 20.0%);
    -fx-text-fill: black;
}

.darkresult-display {
	-fx-background-color: derive(#1d1d1d, 20.0%);
}

.darkresult-display .label {
    -fx-text-fill: white !important;
}

.status-bar .label {
    -fx-text-fill: white;
}

.status-bar-with-border {
    -fx-background-color: derive(#1d1d1d, 30.0%);
    -fx-border-color: derive(#1d1d1d, 25.0%);
    -fx-border-width: 1.0px;
}

.status-bar-with-border .label {
    -fx-text-fill: white;
}

.grid-pane {
    -fx-background-color: derive(#1d1d1d, 30.0%);
    -fx-border-color: derive(#1d1d1d, 30.0%);
    -fx-border-width: 1.0px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#1d1d1d, 30.0%);
}

.context-menu {
    -fx-background-color: derive(#1d1d1d, 50.0%);
}

.context-menu .label {
    -fx-text-fill: white;
}

.menu-bar {
    -fx-background-color: derive(#1d1d1d, 20.0%);
}

.menu-bar .label {
    -fx-font-size: 14.0pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: black;
}

.darktext-area {
    -fx-font-size: 15.0;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: white;
    -fx-display-caret:true;
}

.darktext-area .content {
    -fx-background-color: derive(#1d1d1d, 20.0%);
}

.list-view {
	-fx-background-color: derive(#1d1d1d, 5.0%);
}

.v-box{
	-fx-background-color:derive(#1d1d1d, 20.0%);
}

.title-pane{
	-fx-text-fill: #dde2e3;
	 -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
	 -fx-font-size :24;
}

.titled-pane:focused {
	-fx-text-fill: derive(#dde2e3,10%);

}

.titled-pane > .title
{
	-fx-background-color: derive(#1d1d1d, 15.0%);
	-fx-background-insets: 0.0, 1.0, 2.0;
	-fx-background-radius: 5.0 5.0 0.0 0.0, 4.0 4.0 0.0 0.0, 3.0 3.0 0.0 0.0;
	-fx-padding: 0.166667em 0.833333em 0.25em 0.833333em; /* 2 10 3 10 */
}

.titled-pane:focused > .title {
	-fx-background-color: derive(#1d1d1d, 30.0%);
		 -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
	  -fx-font-size :24;
	  -fx-font-weight: bold;
}



/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5.0 22.0 5.0 22.0;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2.0;
    -fx-background-radius: 0.0;
    -fx-background-color: #1d1d1d;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11.0pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0.0 0.0 0.0 0.0, 0.0, 1.0, 2.0;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: white;
  -fx-text-fill: #1d1d1d;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1.0, 1.0;
    -fx-border-style: solid, segments(1.0, 1.0);
    -fx-border-radius: 0.0, 0.0;
    -fx-border-insets: 1.0 1.0 1.0 1.0, 0.0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #1d1d1d;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30.0%);
}

.dialog-pane {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14.0px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#1d1d1d, 25.0%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18.0px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar .thumb {
    -fx-background-color: derive(#1d1d1d, 50.0%);
    -fx-background-insets: 3.0;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0.0 0.0 0.0 0.0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1.0 8.0 1.0 8.0;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8.0 1.0 8.0 1.0;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-color: #d6d6d6;
    -fx-border-width: 1.0 1.0 1.0 1.0;
}

#commandTypeLabel {
    -fx-font-size: 11.0px;
    -fx-text-fill: #F70D1A;
}

#filterField, #taskListPanel, #taskWebpage {
    -fx-effect: innershadow(gaussian, black, 10.0, 0.0, 0.0, 0.0);
}

#tags {
    -fx-hgap: 7.0;
    -fx-vgap: 3.0;
}

#tags .label {
    -fx-text-fill: silver;
    -fx-background-color: #383838;
    -fx-padding: 1.0 3.0 1.0 3.0;
    -fx-border-radius: 2.0;
    -fx-background-radius: 2.0;
    -fx-font-size: 16.0;
}


```
###### /resources/view/MainWindowDark.fxml
``` fxml
<?import java.net.URL?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Menu?>
<?import javafx.scene.control.MenuBar?>
<?import javafx.scene.control.MenuItem?>
<?import javafx.scene.control.SplitPane?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <stylesheets>
      <URL value="@DarkTheme.css" />
      <URL value="@Extensions.css" />
   </stylesheets>
   <children>
      <MenuBar VBox.vgrow="NEVER">
         <menus>
            <Menu mnemonicParsing="false" text="File">
               <items>
                  <MenuItem mnemonicParsing="false" onAction="#handleExit" text="Exit" />
               </items>
            </Menu>
            <Menu mnemonicParsing="false" text="Help">
               <items>
                  <MenuItem fx:id="helpMenuItem" mnemonicParsing="false" onAction="#handleHelp" text="Help" />
                  <MenuItem fx:id="helpFormatMenuItem" mnemonicParsing="false" onAction="#handleHelpFormat" text="Summary" />
               </items>
            </Menu>
            <Menu mnemonicParsing="false" text="Shortcut">
              <items>
                <MenuItem fx:id="quickAddMenuItem" mnemonicParsing="false" onAction="#handleAdd" text="Quick Add" />
                  <MenuItem fx:id="quickUndoMenuItem" mnemonicParsing="false" onAction="#handleUndo" text="Quick Undo" />
                  <MenuItem fx:id="quickDoneMenuItem" mnemonicParsing="false" onAction="#handleDone" text="Quick Done" />
                  <MenuItem fx:id="quickEditMenuItem" mnemonicParsing="false" onAction="#handleEdit" text="Quick Edit" />
                  <MenuItem fx:id="quickSelectMenuItem" mnemonicParsing="false" onAction="#handleSelect" text="Quick Select" />
                  <MenuItem fx:id="quickSaveMenuItem" mnemonicParsing="false" onAction="#handleSave" text="Quick Save" />
                  <MenuItem fx:id="quickLoadMenuItem" mnemonicParsing="false" onAction="#handleLoad" text="Quick Load" />
                  <MenuItem fx:id="quickScrollDownMenuItem" mnemonicParsing="false" onAction="#handleScrollDown" text="Quick Scroll Down" />
                  <MenuItem fx:id="quickScrollUpMenuItem" mnemonicParsing="false" onAction="#handleScrollUp" text="Quick Scroll Up" />
              </items>
            </Menu>
         </menus>
      </MenuBar>
      <AnchorPane fx:id="commandBoxPlaceholder" styleClass="anchor-pane-with-border" VBox.vgrow="NEVER">
         <padding>
            <Insets bottom="5.0" left="10.0" right="10.0" top="5.0" />
         </padding>
      </AnchorPane>
       <AnchorPane fx:id="resultDisplayPlaceholder" maxHeight="100" minHeight="100" prefHeight="100" styleClass="anchor-pane-with-border" VBox.vgrow="NEVER">
           <padding>
               <Insets bottom="5.0" left="10.0" right="10.0" top="5.0" />
           </padding>
       </AnchorPane>
      <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.4" VBox.vgrow="ALWAYS">
         <items>
            <VBox fx:id="taskList" minWidth="340" prefWidth="340">
                <padding>
                    <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
                </padding>
               <children>
                  <AnchorPane fx:id="taskListPanelPlaceholder" VBox.vgrow="ALWAYS" />
               </children>
            </VBox>
            <AnchorPane fx:id="browserPlaceholder" prefWidth="340">
                <padding>
                    <Insets bottom="10" left="10" right="10" top="10" />
                </padding>
            </AnchorPane>
         </items>
      </SplitPane>
      <AnchorPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER" />
   </children>
</VBox>
```
###### /resources/view/MainWindowLight.fxml
``` fxml
<?import java.net.URL?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Menu?>
<?import javafx.scene.control.MenuBar?>
<?import javafx.scene.control.MenuItem?>
<?import javafx.scene.control.SplitPane?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <MenuBar VBox.vgrow="NEVER">
         <menus>
            <Menu mnemonicParsing="false" text="File">
               <items>
                  <MenuItem mnemonicParsing="false" onAction="#handleExit" text="Exit" />
               </items>
            </Menu>
            <Menu mnemonicParsing="false" text="Help">
               <items>
                  <MenuItem fx:id="helpMenuItem" mnemonicParsing="false" onAction="#handleHelp" text="Help" />
                  <MenuItem fx:id="helpFormatMenuItem" mnemonicParsing="false" onAction="#handleHelpFormat" text="Summary" />
               </items>
            </Menu>
            <Menu mnemonicParsing="false" text="Shortcut">
              <items>
                <MenuItem fx:id="quickAddMenuItem" mnemonicParsing="false" onAction="#handleAdd" text="Quick Add" />
                  <MenuItem fx:id="quickUndoMenuItem" mnemonicParsing="false" onAction="#handleUndo" text="Quick Undo" />
                  <MenuItem fx:id="quickDoneMenuItem" mnemonicParsing="false" onAction="#handleDone" text="Quick Done" />
                  <MenuItem fx:id="quickEditMenuItem" mnemonicParsing="false" onAction="#handleEdit" text="Quick Edit" />
                  <MenuItem fx:id="quickSelectMenuItem" mnemonicParsing="false" onAction="#handleSelect" text="Quick Select" />
                  <MenuItem fx:id="quickSaveMenuItem" mnemonicParsing="false" onAction="#handleSave" text="Quick Save" />
                  <MenuItem fx:id="quickLoadMenuItem" mnemonicParsing="false" onAction="#handleLoad" text="Quick Load" />
                  <MenuItem fx:id="quickScrollDownMenuItem" mnemonicParsing="false" onAction="#handleScrollDown" text="Quick Scroll Down" />
                  <MenuItem fx:id="quickScrollUpMenuItem" mnemonicParsing="false" onAction="#handleScrollUp" text="Quick Scroll Up" />
              </items>
            </Menu>
         </menus>
         <stylesheets>
            <URL value="@Extensions.css" />
            <URL value="@LightTheme.css" />
         </stylesheets>
      </MenuBar>
      <AnchorPane fx:id="commandBoxPlaceholder" styleClass="anchor-pane" VBox.vgrow="NEVER">
         <padding>
            <Insets bottom="5.0" left="10.0" right="10.0" top="5.0" />
         </padding>
      </AnchorPane>
       <AnchorPane fx:id="resultDisplayPlaceholder" maxHeight="100" minHeight="100" prefHeight="100" styleClass="anchor-pane-with-border" VBox.vgrow="NEVER">
           <padding>
               <Insets bottom="5.0" left="10.0" right="10.0" top="5.0" />
           </padding>
       </AnchorPane>
      <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.4" VBox.vgrow="ALWAYS">
         <items>
            <VBox fx:id="taskList" minWidth="340" prefWidth="340">
                <padding>
                    <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
                </padding>
               <children>
                  <AnchorPane fx:id="taskListPanelPlaceholder" VBox.vgrow="ALWAYS" />
               </children>
            </VBox>
            <AnchorPane fx:id="browserPlaceholder" prefWidth="340">
                <padding>
                    <Insets bottom="10" left="10" right="10" top="10" />
                </padding>
            </AnchorPane>
         </items>
      </SplitPane>
      <AnchorPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER" />
   </children>
   <stylesheets>
      <URL value="@Extensions.css" />
      <URL value="@LightTheme.css" />
   </stylesheets>
</VBox>
```
###### /resources/view/ResultDisplayDark.fxml
``` fxml
<?import java.net.URL?>
<?import javafx.scene.control.TextArea?>
<?import javafx.scene.layout.AnchorPane?>

<AnchorPane fx:id="placeHolder" style="-fx-background-color: black;" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <AnchorPane fx:id="mainPane" styleClass="darktext-area">
        <TextArea fx:id="resultDisplay" editable="false" styleClass="darktext-area">
         <stylesheets>
            <URL value="@DarkTheme.css" />
            <URL value="@Extensions.css" />
         </stylesheets>
      </TextArea>
      <stylesheets>
         <URL value="@Extensions.css" />
         <URL value="@DarkTheme.css" />
      </stylesheets>
    </AnchorPane>
   <stylesheets>
      <URL value="@DarkTheme.css" />
      <URL value="@Extensions.css" />
   </stylesheets>
</AnchorPane>
```
###### /resources/view/ResultDisplayLight.fxml
``` fxml
<?import java.net.URL?>
<?import javafx.scene.control.TextArea?>
<?import javafx.scene.layout.AnchorPane?>

<AnchorPane fx:id="placeHolder" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <AnchorPane fx:id="mainPane">
        <TextArea fx:id="resultDisplay" editable="false" styleClass="result-display">
         <stylesheets>
            <URL value="@DarkTheme.css" />
            <URL value="@Extensions.css" />
         </stylesheets></TextArea>
    </AnchorPane>
   <stylesheets>
      <URL value="@DarkTheme.css" />
      <URL value="@Extensions.css" />
   </stylesheets>
</AnchorPane>
```
###### /resources/view/StatusBarFooterDark.fxml
``` fxml
<?import javafx.scene.layout.*?>
<?import org.controlsfx.control.StatusBar?>
<GridPane styleClass="grid-pane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" stylesheets="@DarkTheme.css">
<columnConstraints>
  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
</columnConstraints>
 <children>
      <StatusBar styleClass="anchor-pane" fx:id="syncStatus" minWidth="0.0"/>
      <StatusBar styleClass="anchor-pane" fx:id="saveLocationStatus" minWidth="0.0" GridPane.columnIndex="1"/>
 </children>
</GridPane>
```
###### /resources/view/StatusBarFooterLight.fxml
``` fxml
<?import javafx.scene.layout.*?>
<?import org.controlsfx.control.StatusBar?>
<GridPane styleClass="grid-pane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" stylesheets="@DarkTheme.css">
<columnConstraints>
  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
</columnConstraints>
 <children>
      <StatusBar styleClass="anchor-pane" fx:id="syncStatus" minWidth="0.0"/>
      <StatusBar styleClass="anchor-pane" fx:id="saveLocationStatus" minWidth="0.0" GridPane.columnIndex="1"/>
 </children>
</GridPane>
```
###### /resources/view/TaskListCardDark.fxml
``` fxml
<?import java.net.URL?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TitledPane?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <children>
        <GridPane HBox.hgrow="ALWAYS">
            <columnConstraints>
                <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="150.0" />
            </columnConstraints>
            <children>
            <TitledPane fx:id="plane" styleClass="title-pane">
               <content>
                      <VBox alignment="CENTER_LEFT" minHeight="105.0" styleClass="v-box">
                          <stylesheets>
                              <URL value="@DarkTheme.css" />
                              <URL value="@Extensions.css" />
                          </stylesheets>
                          <padding>
                              <Insets bottom="5" left="15" right="5" top="5" />
                          </padding>

                          <children>
                              <Label fx:id="startDate" styleClass="cell_small_label" text="\$startDate" />
                              <Label fx:id="endDate" styleClass="cell_small_label" text="\$endDate" />
                              <Label fx:id="loc" styleClass="cell_small_label" text="\$loc" />
                              <Label fx:id="remark" styleClass="cell_small_label" text="\$remark" />
                          </children>
                      </VBox>
               </content>
               <graphic>
                  <HBox>
                     <children>
                        <Label fx:id="name" text="\$title" textFill="WHITE" />
                              <FlowPane fx:id="tags">
                           <HBox.margin>
                              <Insets left="10.0" />
                           </HBox.margin></FlowPane>
                     </children>
                  </HBox>
               </graphic>
            </TitledPane>
            </children>
         <rowConstraints>
            <RowConstraints />
         </rowConstraints>
        </GridPane>
    </children>
   <stylesheets>
      <URL value="@DarkTheme.css" />
      <URL value="@Extensions.css" />
   </stylesheets>
</HBox>
```
###### /resources/view/TaskListCardLight.fxml
``` fxml
<?import java.net.URL?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TitledPane?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <children>
        <GridPane HBox.hgrow="ALWAYS">
            <columnConstraints>
                <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="150.0" />
            </columnConstraints>
            <children>
            <TitledPane fx:id="plane">
               <content>
                      <VBox alignment="CENTER_LEFT" minHeight="105.0" styleClass="V-box">
                          <padding>
                              <Insets bottom="5" left="15" right="5" top="5" />
                          </padding>

                                <children>
                              <Label fx:id="startDate" styleClass="cell_small_label" text="\$startDate" />
                              <Label fx:id="endDate" styleClass="cell_small_label" text="\$endDate" />
                              <Label fx:id="loc" styleClass="cell_small_label" text="\$loc" />
                              <Label fx:id="remark" styleClass="cell_small_label" text="\$remark" />
                          </children>
                      </VBox>
               </content>
               <graphic>
                  <HBox>
                     <children>
                        <Label fx:id="name" text="\$title" />
                              <FlowPane fx:id="tags" styleClass="content">
                           <HBox.margin>
                              <Insets left="10.0" />
                           </HBox.margin></FlowPane>
                     </children>
                  </HBox>
               </graphic>
            </TitledPane>
            </children>
         <rowConstraints>
            <RowConstraints />
         </rowConstraints>
        </GridPane>
    </children>
   <stylesheets>
      <URL value="@LightTheme.css" />
      <URL value="@Extensions.css" />
   </stylesheets>
</HBox>
```
###### /resources/view/TaskListPanelDark.fxml
``` fxml
<?import java.net.URL?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox styleClass="anchor-pane-with-border" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <stylesheets>
        <URL value="@Extensions.css" />
        <URL value="@DarkTheme.css" />
    </stylesheets>
    <children>
        <ListView fx:id="taskListView" styleClass="background" VBox.vgrow="ALWAYS">
         <stylesheets>
            <URL value="@DarkTheme.css" />
            <URL value="@Extensions.css" />
         </stylesheets></ListView>
    </children>
</VBox>
```
###### /resources/view/TaskListPanelLight.fxml
``` fxml
<?import java.net.URL?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <stylesheets>
        <URL value="@Extensions.css" />
        <URL value="@LightTheme.css" />
    </stylesheets>
    <children>
        <ListView fx:id="taskListView" VBox.vgrow="ALWAYS" />
    </children>
</VBox>
```
