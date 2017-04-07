# A0146738U-reused
###### \java\seedu\bulletjournal\commons\events\model\FilePathChangedEvent.java
``` java
package seedu.bulletjournal.commons.events.model;

import seedu.bulletjournal.commons.events.BaseEvent;

/** Indicates the file path of the task master should change. */
public class FilePathChangedEvent extends BaseEvent {

    public final String newFilePath;

    public FilePathChangedEvent(String newFilePath) {
        this.newFilePath = newFilePath;
    }

    @Override
    public String toString() {
        return "File path changes to :" + newFilePath;
    }
}
```
###### \java\seedu\bulletjournal\commons\events\ui\FailedCommandAttemptedEvent.java
``` java

/**
 * Indicates an attempt to execute a failed command
 */
public class FailedCommandAttemptedEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\bulletjournal\commons\events\ui\IncorrectCommandAttemptedEvent.java
``` java

/**
 * Indicates an attempt to execute an incorrect command
 */
public class IncorrectCommandAttemptedEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\bulletjournal\logic\commands\ChangeDirectoryCommand.java
``` java

/**
 * Changes the file path of the file and exports all existing data to the
 * specified file.
 */
public class ChangeDirectoryCommand extends Command {

    public static final String COMMAND_WORD = "change";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the directory for the tasklist."
            + "Parameters: FILE_PATH\n" + "Example: " + COMMAND_WORD + " D:\t.xml";

    public static final String MESSAGE_SUCCESS = "Alert: This operation is irreversible."
            + "\nFile path successfully changed to : %1$s";
    public static final String MESSAGE_IO_ERROR = "Error when saving/reading file...";
    public static final String MESSAGE_CONVENSION_ERROR = "Wrong file type/Invalid file path detected.";

    private final String filePath;

    public ChangeDirectoryCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() {
        try {
            if (!filePath.endsWith(".xml")) {
                throw new DataConversionException(null);
            }
            XmlTodoListStorage newFile = new XmlTodoListStorage(filePath);
            newFile.saveTodoList(model.getTodoList(), filePath);
            model.changeDirectory(filePath);
            Config config = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE).get();
            config.setBulletJournalFilePath(filePath);
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
            return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
        } catch (DataConversionException dce) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_CONVENSION_ERROR);
        } catch (IOException ioe) {
            indicateAttemptToExecuteFailedCommand();
            return new CommandResult(MESSAGE_IO_ERROR);
        }
    }

}
```
###### \java\seedu\bulletjournal\logic\commands\Command.java
``` java
    /**
     * Raises an event to indicate an attempt to execute an incorrect command
     */
    protected void indicateAttemptToExecuteIncorrectCommand() {
        EventsCenter.getInstance().post(new IncorrectCommandAttemptedEvent());
    }

    /**
     * Raises an event to indicate an attempt to execute a failed command
     */
    protected void indicateAttemptToExecuteFailedCommand() {
        EventsCenter.getInstance().post(new FailedCommandAttemptedEvent());
    }
```
###### \java\seedu\bulletjournal\ui\StatusBarFooter.java
``` java
    @Subscribe
    public void handleFilePathChangeEvent(FilePathChangedEvent fpce) {
        setSaveLocation(fpce.newFilePath);
    }
```
