# A0138909R
###### /java/seedu/doit/commons/core/CommandSettings.java
``` java
public class CommandSettings implements Serializable {

    private String add;
    private String delete;
    private String done;
    private String edit;
    private String mark;
    private String clear;
    private String exit;
    private String find;
    private String help;
    private String list;
    private String load;
    private String redo;
    private String save;
    private String select;
    private String set;
    private String sort;
    private String undo;
    private String unmark;

    private static CommandSettings instance = null;

    public static CommandSettings getInstance() {
        if (instance == null) {
            instance = new CommandSettings();
        }
        return instance;
    }

    protected CommandSettings() {
        this.add = AddCommand.COMMAND_WORD;
        this.delete = DeleteCommand.COMMAND_WORD;
        this.edit = EditCommand.COMMAND_WORD;
        this.done = DoneCommand.COMMAND_WORD;
        this.clear = ClearCommand.COMMAND_WORD;
        this.exit = ExitCommand.COMMAND_WORD;
        this.find = FindCommand.COMMAND_WORD;
        this.help = HelpCommand.COMMAND_WORD;
        this.list = ListCommand.COMMAND_WORD;
        this.load = LoadCommand.COMMAND_WORD;
        this.mark = MarkCommand.COMMAND_WORD;
        this.redo = RedoCommand.COMMAND_WORD;
        this.save = SaveCommand.COMMAND_WORD;
        this.select = SelectCommand.COMMAND_WORD;
        this.set = SetCommand.COMMAND_WORD;
        this.sort = SortCommand.COMMAND_WORD;
        this.undo = UndoCommand.COMMAND_WORD;
        this.unmark = UnmarkCommand.COMMAND_WORD;
    }

    // Getter
    public String getAdd() {
        return this.add;
    }

    public String getDelete() {
        return this.delete;
    }

    public String getDone() {
        return this.done;
    }

    public String getEdit() {
        return this.edit;
    }

    public String getMark() {
        return this.mark;
    }

    public String getUnmark() {
        return this.unmark;
    }

    public String getClear() {
        return this.clear;
    }

    public String getExit() {
        return this.exit;
    }

    public String getFind() {
        return this.find;
    }

    public String getHelp() {
        return this.help;
    }

    public String getList() {
        return this.list;
    }

    public String getLoad() {
        return this.load;
    }

    public String getRedo() {
        return this.redo;
    }

    public String getSave() {
        return this.save;
    }

    public String getSelect() {
        return this.select;
    }

    public String getSet() {
        return this.set;
    }

    public String getSort() {
        return this.sort;
    }

    public String getUndo() {
        return this.undo;
    }

    // Setter
    public static void setInstance(CommandSettings commandSettings) {
        instance = commandSettings;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setUnmark(String unmark) {
        this.unmark = unmark;
    }

    public void setClear(String clear) {
        this.clear = clear;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }

    public void setFind(String find) {
        this.find = find;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public void setList(String list) {
        this.list = list;
    }

    public void setLoad(String load) {
        this.load = load;
    }

    public void setRedo(String redo) {
        this.redo = redo;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setUndo(String undo) {
        this.undo = undo;
    }

    public void setCommand(String oldCommand, String newCommand)
            throws NoSuchCommandException, CommandExistedException {
        if (doesCommandExist(newCommand)) {
            throw new CommandExistedException(MESSAGE_UNKNOWN_COMMAND);
        } else if (AddCommand.COMMAND_WORD.equals(oldCommand) || this.add.equals(oldCommand)) {
            setAdd(newCommand);
        } else if (EditCommand.COMMAND_WORD.equals(oldCommand) || this.edit.equals(oldCommand)) {
            setEdit(newCommand);
        } else if (SelectCommand.COMMAND_WORD.equals(oldCommand) || this.select.equals(oldCommand)) {
            setSelect(newCommand);
        } else if (DoneCommand.COMMAND_WORD.equals(oldCommand) || this.done.equals(oldCommand)) {
            setDone(newCommand);
        } else if (MarkCommand.COMMAND_WORD.equals(oldCommand) || this.mark.equals(oldCommand)) {
            setMark(newCommand);
        } else if (UnmarkCommand.COMMAND_WORD.equals(oldCommand) || this.unmark.equals(oldCommand)) {
            setUnmark(newCommand);
        } else if (SortCommand.COMMAND_WORD.equals(oldCommand) || this.sort.equals(oldCommand)) {
            setSort(newCommand);
        } else if (DeleteCommand.COMMAND_WORD.equals(oldCommand) || this.delete.equals(oldCommand)) {
            setDelete(newCommand);
        } else if (ClearCommand.COMMAND_WORD.equals(oldCommand) || this.clear.equals(oldCommand)) {
            setClear(newCommand);
        } else if (FindCommand.COMMAND_WORD.equals(oldCommand) || this.find.equals(oldCommand)) {
            setFind(newCommand);
        } else if (ListCommand.COMMAND_WORD.equals(oldCommand) || this.list.equals(oldCommand)) {
            setList(newCommand);
        } else if (LoadCommand.COMMAND_WORD.equals(oldCommand) || this.load.equals(oldCommand)) {
            setLoad(newCommand);
        } else if (ExitCommand.COMMAND_WORD.equals(oldCommand) || this.exit.equals(oldCommand)) {
            setExit(newCommand);
        } else if (HelpCommand.COMMAND_WORD.equals(oldCommand) || this.help.equals(oldCommand)) {
            setHelp(newCommand);
        } else if (SaveCommand.COMMAND_WORD.equals(oldCommand) || this.save.equals(oldCommand)) {
            setSave(newCommand);
        } else if (UndoCommand.COMMAND_WORD.equals(oldCommand) || this.undo.equals(oldCommand)) {
            setUndo(newCommand);
        } else if (RedoCommand.COMMAND_WORD.equals(oldCommand) || this.redo.equals(oldCommand)) {
            setRedo(newCommand);
        } else if (SetCommand.COMMAND_WORD.equals(oldCommand) || this.set.equals(oldCommand)) {
            setSet(newCommand);
        } else {
            throw new NoSuchCommandException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    public boolean doesCommandExist(String command) {
        if (doesCommandExistInDefault(command)) {
            return true;
        }
        if (doesCommandExistInAlias(command)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the command exist in default command words
     *
     * @param command
     * @return true if it exists else false
     */
    public boolean doesCommandExistInDefault(String command) {
        switch (command) {

        case AddCommand.COMMAND_WORD:
        case EditCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_WORD:
        case DoneCommand.COMMAND_WORD:
        case MarkCommand.COMMAND_WORD:
        case UnmarkCommand.COMMAND_WORD:
        case SortCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_WORD:
        case FindCommand.COMMAND_WORD:
        case ListCommand.COMMAND_WORD:
        case LoadCommand.COMMAND_WORD:
        case ExitCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_WORD:
        case SaveCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_WORD:
        case SetCommand.COMMAND_WORD:
            return true;
        }
        return false;
    }

    /**
     * Checks if the command exist in CommandSettings
     *
     * @param command
     * @return true if it exists else false
     */
    public boolean doesCommandExistInAlias(String command) {
        return this.add.equals(command) || this.delete.equals(command) || this.edit.equals(command)
                || this.done.equals(command) || this.clear.equals(command) || this.exit.equals(command)
                || this.find.equals(command) || this.help.equals(command) || this.list.equals(command)
                || this.load.equals(command) || this.mark.equals(command) || this.redo.equals(command)
                || this.save.equals(command) || this.select.equals(command) || this.set.equals(command)
                || this.sort.equals(command) || this.undo.equals(command) || this.unmark.equals(command);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CommandSettings)) {
            return false;
        }
        CommandSettings other = (CommandSettings) obj;
        if (this.add == null) {
            if (other.add != null) {
                return false;
            }
        } else if (!this.add.equals(other.add)) {
            return false;
        }
        if (this.clear == null) {
            if (other.clear != null) {
                return false;
            }
        } else if (!this.clear.equals(other.clear)) {
            return false;
        }
        if (this.delete == null) {
            if (other.delete != null) {
                return false;
            }
        } else if (!this.delete.equals(other.delete)) {
            return false;
        }
        if (this.done == null) {
            if (other.done != null) {
                return false;
            }
        } else if (!this.done.equals(other.done)) {
            return false;
        }
        if (this.edit == null) {
            if (other.edit != null) {
                return false;
            }
        } else if (!this.edit.equals(other.edit)) {
            return false;
        }
        if (this.exit == null) {
            if (other.exit != null) {
                return false;
            }
        } else if (!this.exit.equals(other.exit)) {
            return false;
        }
        if (this.find == null) {
            if (other.find != null) {
                return false;
            }
        } else if (!this.find.equals(other.find)) {
            return false;
        }
        if (this.help == null) {
            if (other.help != null) {
                return false;
            }
        } else if (!this.help.equals(other.help)) {
            return false;
        }
        if (this.list == null) {
            if (other.list != null) {
                return false;
            }
        } else if (!this.list.equals(other.list)) {
            return false;
        }
        if (this.load == null) {
            if (other.load != null) {
                return false;
            }
        } else if (!this.load.equals(other.load)) {
            return false;
        }
        if (this.mark == null) {
            if (other.mark != null) {
                return false;
            }
        } else if (!this.mark.equals(other.mark)) {
            return false;
        }
        if (this.redo == null) {
            if (other.redo != null) {
                return false;
            }
        } else if (!this.redo.equals(other.redo)) {
            return false;
        }
        if (this.save == null) {
            if (other.save != null) {
                return false;
            }
        } else if (!this.save.equals(other.save)) {
            return false;
        }
        if (this.select == null) {
            if (other.select != null) {
                return false;
            }
        } else if (!this.select.equals(other.select)) {
            return false;
        }
        if (this.set == null) {
            if (other.set != null) {
                return false;
            }
        } else if (!this.set.equals(other.set)) {
            return false;
        }
        if (this.sort == null) {
            if (other.sort != null) {
                return false;
            }
        } else if (!this.sort.equals(other.sort)) {
            return false;
        }
        if (this.undo == null) {
            if (other.undo != null) {
                return false;
            }
        } else if (!this.undo.equals(other.undo)) {
            return false;
        }
        if (this.unmark == null) {
            if (other.unmark != null) {
                return false;
            }
        } else if (!this.unmark.equals(other.unmark)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Add : " + this.add + "\n");
        sb.append("Delete : " + this.delete + "\n");
        sb.append("Edit : " + this.edit + "\n");
        sb.append("Done : " + this.done + "\n");
        sb.append("Clear : " + this.clear + "\n");
        sb.append("Exit : " + this.exit + "\n");
        sb.append("Find : " + this.find + "\n");
        sb.append("Help : " + this.help + "\n");
        sb.append("List : " + this.list + "\n");
        sb.append("Load : " + this.load + "\n");
        sb.append("Mark : " + this.mark + "\n");
        sb.append("Redo : " + this.redo + "\n");
        sb.append("Save : " + this.save + "\n");
        sb.append("Select : " + this.select + "\n");
        sb.append("Set : " + this.set + "\n");
        sb.append("Sort : " + this.sort + "\n");
        sb.append("Undo : " + this.undo + "\n");
        sb.append("Unmark : " + this.unmark);
        return sb.toString();
    }

}
```
###### /java/seedu/doit/commons/events/storage/SetCommandEvent.java
``` java
public class SetCommandEvent extends BaseEvent {
    private String oldCommand;
    private String newCommand;

    public SetCommandEvent(String oldCommand, String newCommand) {
        this.oldCommand = oldCommand;
        this.newCommand = newCommand;
    }

    @Override
    public String toString() {
        return "Changed " + this.oldCommand + " into " + this.newCommand;
    }

}
```
###### /java/seedu/doit/commons/events/storage/TaskManagerLoadChangedEvent.java
``` java
package seedu.doit.commons.events.storage;

import java.util.Optional;

import seedu.doit.commons.events.BaseEvent;
import seedu.doit.model.ReadOnlyTaskManager;

public class TaskManagerLoadChangedEvent extends BaseEvent {
    private Optional<ReadOnlyItemManager> data;
    private String filePath;

    public TaskManagerLoadChangedEvent(Optional<ReadOnlyItemManager> newData, String filePath) {
        this.data = newData;
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public Optional<ReadOnlyItemManager> getData() {
        return this.data;
    }

    @Override
    public String toString() {
        return "Loaded data from: " + this.filePath;
    }
}
```
###### /java/seedu/doit/commons/events/storage/TaskManagerSaveChangedEvent.java
``` java
public class TaskManagerSaveChangedEvent extends BaseEvent {
    private ReadOnlyItemManager data;
    private String filePath;

    public TaskManagerSaveChangedEvent(ReadOnlyItemManager data, String filePath) {
        this.data = data;
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public ReadOnlyItemManager getData() {
        return this.data;
    }

    @Override
    public String toString() {
        return "New save location: " + this.filePath;
    }
}
```
###### /java/seedu/doit/commons/exceptions/EmptyTaskManagerStackException.java
``` java
/**
 * Signals an error caused by empty task manager redo or undo stacks.
 */
public class EmptyTaskManagerStackException extends Exception {
    public EmptyTaskManagerStackException(String message) {
        super(message);
    }
}
```
###### /java/seedu/doit/logic/commands/ClearCommand.java
``` java
/**
 * Clears all the tasks.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "All tasks has been cleared!";

    @Override
    public CommandResult execute() {
        assert this.model != null;
        this.model.clearData();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/doit/logic/commands/exceptions/CommandExistedException.java
``` java
public class CommandExistedException extends Exception {
    public CommandExistedException(String message) {
        super(message);
    }
}
```
###### /java/seedu/doit/logic/commands/exceptions/NoSuchCommandException.java
``` java
public class NoSuchCommandException extends Exception {
    public NoSuchCommandException(String message) {
        super(message);
    }
}
```
###### /java/seedu/doit/logic/commands/RedoCommand.java
``` java
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n" + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Task redone.";
    public static final String MESSAGE_FAILURE = "Unable to redo. There is nothing to redo. ";

    // public static Command toRedo;

    @Override
    public CommandResult execute() throws CommandException {
        assert this.model != null;
        try {
            this.model.redo();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyTaskManagerStackException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }
}
```
###### /java/seedu/doit/logic/commands/SaveCommand.java
``` java
/**
 * Adds a task to the task manager.
 */
public class SaveCommand extends Command {

    public static final String XML_FILE_TYPE = ".xml";

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves all tasks to a new file location and name. "
            + "Parameters: FILE_PATH_IN_DOIT_FILE/FILE_NAME.xml\n" + "Example: " + COMMAND_WORD
            + " save/xml/in/this/file/as/name.xml";

    public static final String MESSAGE_SUCCESS = " Tasks saved at %1$s";
    public static final String MESSAGE_DUPLICATE_FILE = "Another file already exists in the file path!";
    public static final String MESSAGE_NOT_XML_FILE = "It must be a .xml file!\n" + MESSAGE_USAGE;
    public static final String MESSAGE_USING_SAME_FILE = " is the current file you are choosing. "
            + "It will be auto saved.";
    public static final String MESSAGE_CANNOT_CREATE_FILE = "Cannot create the .xml file!\n"
            + "Maybe you have the : character in file name.";
    public static final String MESSAGE_INVALID_FILE_NAME = "Invalid file path!\nCannot contain characters"
            + " * ? \" < > |\n" + MESSAGE_USAGE;
    private static final Logger logger = LogsCenter.getLogger(SaveCommand.class);
    public final String saveFilePath;

    /**
     * Creates an SaveCommand.
     */
    public SaveCommand(String newFilePath) {
        assert newFilePath != null;
        this.saveFilePath = newFilePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert this.model != null;
        File file = new File(this.saveFilePath);
        if (this.saveFilePath.equals(this.storage.getTaskManagerFilePath())) {
            logger.info(this.saveFilePath + "is current file path. Do not need to save.");
            throw new CommandException(this.saveFilePath + MESSAGE_USING_SAME_FILE);
        }
        if (!FileUtil.isValidPath(this.saveFilePath)) {
            logger.info("Invalid File Name: " + this.saveFilePath);
            throw new CommandException(MESSAGE_INVALID_FILE_NAME);
        }
        if (!this.saveFilePath.endsWith(XML_FILE_TYPE)) {
            logger.info("File not of type xml: " + this.saveFilePath);
            throw new CommandException(MESSAGE_NOT_XML_FILE);
        }
        if (file.exists()) {
            logger.info("Duplicate file path: " + this.saveFilePath);
            throw new CommandException(MESSAGE_DUPLICATE_FILE);
        }
        try {
            FileUtil.createIfMissing(file);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_CANNOT_CREATE_FILE);
        }
        TaskManagerSaveChangedEvent event = new TaskManagerSaveChangedEvent(this.model.getTaskManager(),
                this.saveFilePath);
        logger.info("Created event : " + event.toString());
        EventsCenter.getInstance().post(event);
        this.storage.handleTaskManagerSaveChangedEvent(event);
        logger.info("---------------------------------------HANDLED event : " + event.toString());
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.saveFilePath));

    }

}
```
###### /java/seedu/doit/logic/commands/SetCommand.java
``` java
public class SetCommand extends Command {
    public static final String COMMAND_ALREADY_EXISTS = "Cannot change into a command that already exists!";
    public static final String NO_SUCH_COMMAND_TO_CHANGE = "There exists no such command to change!";
    public static final String COMMAND_WORD = "set";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the command identified by the default command word to a new command word\n"
            + "Parameters: OLD/DEFAULT_COMMAND_WORD NEW_COMMAND_WORD\n" + "Example: " + COMMAND_WORD + " add adding";
    public static final String MESSAGE_SET_TASK_SUCCESS = "Set command: %1$s";
    public final String newCommand;
    public final String oldCommand;
    private static final Logger logger = LogsCenter.getLogger(SetCommand.class);

    public SetCommand(String oldCommand, String newCommand) {
        this.oldCommand = oldCommand;
        this.newCommand = newCommand;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            this.model.commandSet(this.oldCommand, this.newCommand);
            logger.info(this.oldCommand + " command in command settings became: " + this.newCommand);
        } catch (NoSuchCommandException nsce) {
            throw new CommandException(NO_SUCH_COMMAND_TO_CHANGE);
        } catch (CommandExistedException cee) {
            throw new CommandException(COMMAND_ALREADY_EXISTS);
        }
        EventsCenter.getInstance().post(new SetCommandEvent(this.oldCommand, this.newCommand));
        return new CommandResult(String.format(MESSAGE_SET_TASK_SUCCESS, this.oldCommand + " into " + this.newCommand));

    }
}
```
###### /java/seedu/doit/logic/commands/UndoCommand.java
``` java
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n" + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Task undone.";
    public static final String MESSAGE_FAILURE = "Unable to undo. There is nothing to undo.\n"
            + "You cannot undo a save, load, find, set and list";

    // public static Command toUndo;

    @Override
    public CommandResult execute() throws CommandException {
        assert this.model != null;
        try {
            this.model.undo();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyTaskManagerStackException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }
}
```
###### /java/seedu/doit/logic/LogicManager.java
``` java
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        this.parser = new Parser();
    }
```
###### /java/seedu/doit/logic/parser/LoadCommandParser.java
``` java
package seedu.doit.logic.parser;

import java.util.logging.Logger;

import seedu.doit.commons.core.LogsCenter;
import seedu.doit.logic.commands.Command;
import seedu.doit.logic.commands.LoadCommand;

public class LoadCommandParser implements CommandParser {
    private static final Logger logger = LogsCenter.getLogger(LoadCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the
     * LoadCommand and returns an LoadCommand object for execution.
     */
    @Override
    public Command parse(String args) {
        assert args != null;
        String filePath = args.trim();
        logger.info("Argument for load is " + filePath);
        return new LoadCommand(filePath);

    }

}
```
###### /java/seedu/doit/logic/parser/Parser.java
``` java
    /**
     * Parses user input into command for execution.
     *
     * @param userInput
     *            full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        final CommandSettings commandSettings = CommandSettings.getInstance();

        if (isAddCommandWord(commandWord, commandSettings)) {
            return new AddCommandParser().parse(arguments);
        } else if (isEditCommandWord(commandWord, commandSettings)) {
            return new EditCommandParser().parse(arguments);
        } else if (inSelectCommandWord(commandWord, commandSettings)) {
            return new SelectCommandParser().parse(arguments);
        } else if (isDoneCommandWord(commandWord, commandSettings)) {
            return new DoneCommand();
        } else if (isMarkCommandWord(commandWord, commandSettings)) {
            return new MarkCommandParser().parse(arguments);
        } else if (isUnmarkCommandWord(commandWord, commandSettings)) {
            return new UnmarkCommandParser().parse(arguments);
        } else if (isSortCommandWord(commandWord, commandSettings)) {
            return new SortCommandParser().parse(arguments);
        } else if (isDeleteCommandWord(commandWord, commandSettings)) {
            return new DeleteCommandParser().parse(arguments);
        } else if (isClearCommandWord(commandWord, commandSettings)) {
            return new ClearCommand();
        } else if (isFindCommandWord(commandWord, commandSettings)) {
            return new FindCommandParser().parse(arguments);
        } else if (isListCommandWord(commandWord, commandSettings)) {
            return new ListCommand();
        } else if (isExitCommandWord(commandWord, commandSettings)) {
            return new ExitCommand();
        } else if (isHelpCommandWord(commandWord, commandSettings)) {
            return new HelpCommand();
        } else if (isSaveCommandWord(commandWord, commandSettings)) {
            return new SaveCommandParser().parse(arguments);
        } else if (isLoadCommandWord(commandWord, commandSettings)) {
            return new LoadCommandParser().parse(arguments);
        } else if (isUndoCommandWord(commandWord, commandSettings)) {
            return new UndoCommand();
        } else if (isRedoCommandWord(commandWord, commandSettings)) {
            return new RedoCommand();
        } else if (isSetCommandWord(commandWord, commandSettings)) {
            return new SetCommandParser().parse(arguments);
        } else {
            logger.info(commandWord + " add command in command settings is: " + commandSettings.toString());
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * @param commandWord
     * @param commandSettings
     * @return
     */
    public boolean isSetCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return SetCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getSet().equals(commandWord);
    }

    /**
     * @param commandWord
     * @param commandSettings
     * @return
     */
    public boolean isRedoCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return RedoCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getRedo().equals(commandWord);
    }

    /**
     * @param commandWord
     * @param commandSettings
     * @return
     */
    public boolean isUndoCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return UndoCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getUndo().equals(commandWord);
    }

    /**
     * @param commandWord
     * @param commandSettings
     * @return
     */
    public boolean isLoadCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return LoadCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getLoad().equals(commandWord);
    }

    /**
     * @param commandWord
     * @param commandSettings
     * @return
     */
    public boolean isSaveCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return SaveCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getSave().equals(commandWord);
    }

    /**
     * @param commandWord
     * @param commandSettings
     * @return
     */
    public boolean isHelpCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return HelpCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getHelp().equals(commandWord);
    }

    /**
     * @param commandWord
     * @param commandSettings
     * @return
     */
    public boolean isExitCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return ExitCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getExit().equals(commandWord);
    }

    /**
     * @param commandWord
     * @param commandSettings
     * @return
     */
    public boolean isListCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return ListCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getList().equals(commandWord);
    }

    /**
     * @param commandWord
     * @param commandSettings
     * @return
     */
    public boolean isFindCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return FindCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getFind().equals(commandWord);
    }

    /**
     * @param commandWord
     * @param commandSettings
     * @return
     */
    public boolean isClearCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return ClearCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getClear().equals(commandWord);
    }

    /**
     * @param commandWord
     * @param commandSettings
     * @return
     */
    public boolean isDeleteCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return DeleteCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getDelete().equals(commandWord);
    }

    /**
     * @param commandWord
     * @param commandSettings
     * @return
     */
    public boolean isSortCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return SortCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getSort().equals(commandWord);
    }

    /**
     * @param commandWord
     * @param commandSettings
     * @return
     */
    public boolean isUnmarkCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return UnmarkCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getUnmark().equals(commandWord);
    }

    /**
     * @param commandWord
     * @param commandSettings
     * @return
     */
    public boolean isMarkCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return MarkCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getMark().equals(commandWord);
    }

    /**
     * @param commandWord
     * @param commandSettings
     * @return
     */
    public boolean isDoneCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return DoneCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getDone().equals(commandWord);
    }

    /**
     * @param commandWord
     * @param commandSettings
     * @return
     */
    public boolean inSelectCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return SelectCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getSelect().equals(commandWord);
    }

    /**
     * @param commandWord
     * @param commandSettings
     * @return
     */
    public boolean isEditCommandWord(final String commandWord, CommandSettings commandSettings) {
        return EditCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getEdit().equals(commandWord);
    }

    /**
     * @param commandWord
     * @param commandSettings
     * @return
     */
    public boolean isAddCommandWord(final String commandWord, CommandSettings commandSettings) {
        return AddCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getAdd().equals(commandWord);
    }

}
```
###### /java/seedu/doit/logic/parser/SaveCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class SaveCommandParser implements CommandParser {
    private static final Logger logger = LogsCenter.getLogger(SaveCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the
     * SaveCommand and returns an SaveCommand object for execution.
     */
    @Override
    public Command parse(String args) {
        assert args != null;
        String filePath = args.trim();
        logger.info("Argument for save is " + filePath);
        return new SaveCommand(filePath);

    }

}
```
###### /java/seedu/doit/logic/parser/SetCommandParser.java
``` java
public class SetCommandParser {
    private static final int MAX_STRING_WORDS = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the
     * SetCommand and returns an SetCommand object for execution.
     */
    public Command parse(String args) {
        String[] splited = args.trim().split("\\s+");

        if (splited.length != MAX_STRING_WORDS) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE));
        }

        return new SetCommand(splited[0], splited[1]);
    }
}
```
###### /java/seedu/doit/MainApp.java
``` java
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyItemManager> taskManagereOptional;
        ReadOnlyItemManager initialTaskManagerData;

        try {
            taskManagereOptional = storage.readTaskManager();
            if (!taskManagereOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample TaskManager");
            }
            initialTaskManagerData = taskManagereOptional.orElseGet(SampleDataUtil::getSampleTaskManager);
            storage.saveTaskManager(initialTaskManagerData);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty TaskManager");
            initialTaskManagerData = new TaskManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty TaskManager");
            initialTaskManagerData = new TaskManager();
        }

        return new ModelManager(initialTaskManagerData, userPrefs);
    }
```
###### /java/seedu/doit/model/InputStack.java
``` java
package seedu.doit.model;

import java.util.Stack;
import java.util.logging.Logger;

import seedu.doit.commons.core.LogsCenter;

public class InputStack {
    public static final String EMPTY_STRING = "";
    private static final Stack<String> mainStack = new Stack<String>();
    private static Stack<String> upStack = new Stack<String>();
    private static Stack<String> downStack = new Stack<String>();
    private static final Logger logger = LogsCenter.getLogger(InputStack.class);
    private static InputStack instance = null;

    protected InputStack() {
    }

    public static InputStack getInstance() {
        if (instance == null) {
            instance = new InputStack();
        }
        return instance;
    }

    public void addToUpStack(String input) {
        upStack.push(input);
    }

    public void addToDownStack(String input) {
        downStack.push(input);
    }

    public void addToMainStack(String input) {
        if (!input.trim().isEmpty()) {
            mainStack.push(input);
            logger.info("downstack cleared");
            clearDownStack();
        }
    }

    /**
     * When up is pressed in command box
     *
     * @return new input if stack is not empty else return the same input
     */
    public String pressedUp(String input) {
        if (mainStack.isEmpty()) {
            logger.info("UP main stack is empty");
            return input;
        } else if (downStack.isEmpty()) {
            logger.info("UP down stack is empty");
            upStack = (Stack<String>) mainStack.clone();
        }
        if (upStack.isEmpty()) {
            logger.info("UP up stack is empty");
            return input;
        }
        logger.info("UP adding (" + input + ") to downstack");
        addToDownStack(input);
        logger.info("UP up " + upStack.peek() + " down " + downStack.peek());
        String newInput = upStack.pop();
        logger.info("UP output (" + newInput + ")");
        return newInput;
    }

    /**
     * When down is pressed in command box
     *
     * @return new input if stack is not empty else return the same input
     */
    public String pressedDown(String input) {
        if (downStack.isEmpty()) {
            logger.info("DOWN down stack is empty");
            return EMPTY_STRING;
        }
        logger.info("DOWN adding (" + input + ") to upstack");
        addToUpStack(input);
        logger.info("DOWN up " + upStack.peek() + " down " + downStack.peek());
        String newInput = downStack.pop();
        logger.info("DOWN output (" + newInput + ")");
        return newInput;
    }

    /**
     * Clears Down stack
     */
    public void clearDownStack() {
        downStack.clear();
    }

}
```
###### /java/seedu/doit/model/Model.java
``` java
    /**
     * Undo the previous undoable command.
     *
     * Undo command is not undoable
     *
     * @throws EmptyTaskManagerStackException
     */
    void undo() throws EmptyTaskManagerStackException;

    /**
     * Reverse the undo command.
     *
     * Only undo command is redoable
     *
     * @throws EmptyTaskManagerStackException
     */
    void redo() throws EmptyTaskManagerStackException;

    /**
     * Clears all the data into a data with no tasks
     */
    void clearData();

    /**
     * Sets a default or old command word into a new command word
     *
     * @param oldCommand
     *            default command or old command word
     * @param newCommand
     *            new command word
     * @throws NoSuchCommandException
     *             if oldCommand does not exists
     * @throws CommandExistedException
     *             if newCommand already exists
     */

    void commandSet(String oldCommand, String newCommand) throws NoSuchCommandException, CommandExistedException;

    /**
     * Clears existing backing model and replaces with the provided new data
     * without saving.
     */
    void resetDataWithoutSaving(ReadOnlyItemManager newData);

    /**
     * Clears existing backing model and replaces with the provided new data
     * without saving for loading and updates the redo and undo stack.
     */
    void loadData(ReadOnlyItemManager newData);

```
###### /java/seedu/doit/model/ModelManager.java
``` java
    /**
     * Updates the filteredTasks after the taskmanager have changed
     */
    public void updateFilteredTasks() {
        this.filteredTasks = new FilteredList<ReadOnlyTask>(this.taskManager.getTaskList());
    }

```
###### /java/seedu/doit/model/ModelManager.java
``` java
    @Override
    public void clearData() {
        logger.info("clears all tasks in model manager");
        taskManagerStack.addToUndoStack(this.getTaskManager());
        taskManagerStack.clearRedoStack();
        this.taskManager.resetData(new TaskManager());
        indicateTaskManagerChanged();
    }

```
###### /java/seedu/doit/model/ModelManager.java
``` java
    @Override
    public void undo() throws EmptyTaskManagerStackException {
        this.taskManager.resetData(taskManagerStack.loadOlderTaskManager(this.getTaskManager()));
        indicateTaskManagerChanged();
    }

    @Override
    public void redo() throws EmptyTaskManagerStackException {
        this.taskManager.resetData(taskManagerStack.loadNewerTaskManager(this.getTaskManager()));
        indicateTaskManagerChanged();
    }

    @Override
    public void commandSet(String oldCommand, String newCommand)
            throws NoSuchCommandException, CommandExistedException {
        CommandSettings.getInstance().setCommand(oldCommand, newCommand);
        logger.info(CommandSettings.getInstance().toString());
    }

```
###### /java/seedu/doit/model/TaskManagerStack.java
``` java
public class TaskManagerStack {
    public static final String NOTHING_TO_REDO = "There is nothing to redo!";
    public static final String NOTHING_TO_UNDO = "There is nothing to undo!";
    private static final Stack<ReadOnlyItemManager> undoStack = new Stack<ReadOnlyItemManager>();
    private static final Stack<ReadOnlyItemManager> redoStack = new Stack<ReadOnlyItemManager>();
    private static final Logger logger = LogsCenter.getLogger(TaskManagerStack.class);
    private static TaskManagerStack instance = null;
    // private static final int STACK_SIZE = 10;

    protected TaskManagerStack() {
    }

    public static TaskManagerStack getInstance() {
        if (instance == null) {
            instance = new TaskManagerStack();
        }
        return instance;
    }

    public void addToUndoStack(ReadOnlyItemManager readOnlyItemManager) {
        ReadOnlyItemManager oldReadOnlyTaskManager = new TaskManager(readOnlyItemManager);
        undoStack.push(oldReadOnlyTaskManager);
    }

    /**
     * When there is a need to undo a command this is called
     *
     * @return the undid task manager
     * @throws EmptyTaskManagerStackException
     *             if there is an empty undostack
     */
    public ReadOnlyItemManager loadOlderTaskManager(ReadOnlyItemManager readOnlyItemManager)
            throws EmptyTaskManagerStackException {
        if (undoStack.isEmpty()) {
            logger.info(NOTHING_TO_UNDO);
            throw new EmptyTaskManagerStackException(NOTHING_TO_UNDO);
        }
        ReadOnlyItemManager newReadOnlyTaskManager = new TaskManager(readOnlyItemManager);
        redoStack.push(newReadOnlyTaskManager);
        ReadOnlyItemManager undidTaskManager = undoStack.pop();
        return undidTaskManager;
    }

    /**
     * When there is a need to redo a command this is called
     *
     * @return the redid task manager
     * @throws EmptyTaskManagerStackException
     *             if there is an empty redostack
     */
    public ReadOnlyItemManager loadNewerTaskManager(ReadOnlyItemManager readOnlyItemManager)
            throws EmptyTaskManagerStackException {
        if (redoStack.isEmpty()) {
            logger.info(NOTHING_TO_REDO);
            throw new EmptyTaskManagerStackException(NOTHING_TO_REDO);
        }
        ReadOnlyItemManager oldReadOnlyTaskManager = new TaskManager(readOnlyItemManager);
        undoStack.push(oldReadOnlyTaskManager);
        ReadOnlyItemManager redidTaskManager = redoStack.pop();
        return redidTaskManager;
    }

    /**
     * Clears redo stack
     */
    public void clearRedoStack() {
        redoStack.clear();
    }

    /**
     * Clears undo stack
     */
    public void clearUndoStack() {
        undoStack.clear();
    }

}
```
###### /java/seedu/doit/model/UserPrefs.java
``` java
    public CommandSettings getCommandSettings() {
        if (this.commandSettings == null) {
            this.commandSettings = CommandSettings.getInstance();
        }
        CommandSettings.setInstance(this.commandSettings);
        return this.commandSettings;
    }

```
###### /java/seedu/doit/model/UserPrefs.java
``` java
    public void updateCommandSetting(CommandSettings commandSettings) {
        this.commandSettings = commandSettings;
    }

```
###### /java/seedu/doit/model/UserPrefs.java
``` java
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof UserPrefs)) {
            return false;
        }
        UserPrefs other = (UserPrefs) obj;
        if (this.commandSettings == null) {
            if (other.commandSettings != null) {
                return false;
            }
        } else if (!this.commandSettings.equals(other.commandSettings)) {
            return false;
        }
        if (this.guiSettings == null) {
            if (other.guiSettings != null) {
                return false;
            }
        } else if (!this.guiSettings.equals(other.guiSettings)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.commandSettings == null) ? 0 : this.commandSettings.hashCode());
        result = (prime * result) + ((this.guiSettings == null) ? 0 : this.guiSettings.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return this.guiSettings.toString() + "\n" + this.getCommandSettings().toString();
    }
```
###### /java/seedu/doit/storage/Storage.java
``` java
    /**
     * Creates a new file path for the Task Manager to save. Saves the current
     * version of the Task Manager to the hard disk at the new location. Creates
     * the data file if it is missing. Raises {@link DataSavingExceptionEvent}
     * if there was an error during saving.
     */
    void handleTaskManagerSaveChangedEvent(TaskManagerSaveChangedEvent event);

    /**
     * Loads an existing file path for the Task Manager.
     */
    void handleTaskManagerLoadChangedEvent(TaskManagerLoadChangedEvent event);
```
###### /java/seedu/doit/storage/StorageManager.java
``` java
    @Override
    @Subscribe
    public void handleTaskManagerSaveChangedEvent(TaskManagerSaveChangedEvent event) {
        String newPath = event.getFilePath();
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Directory changed, saving to new directory at: " + newPath));
        String oldPath = this.config.getTaskManagerFilePath();
        this.config.setTaskManagerFilePath(newPath);
        setTaskManagerFilePath(newPath);
        try {
            copyTaskManager(oldPath, newPath);
            ConfigUtil.saveConfig(this.config, Config.DEFAULT_CONFIG_FILE);
        } catch (IOException ioe) {
            this.config.setTaskManagerFilePath(oldPath);
            setTaskManagerFilePath(oldPath);
            ioe.printStackTrace();
        }
        try {
            saveTaskManager(event.getData());
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }

    }

    @Override
    @Subscribe
    public void handleTaskManagerLoadChangedEvent(TaskManagerLoadChangedEvent event) {
        String newPath = event.getFilePath();
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Directory changed, saving to new directory at: " + newPath));
        String oldPath = this.config.getTaskManagerFilePath();
        this.config.setTaskManagerFilePath(newPath);
        setTaskManagerFilePath(newPath);
        try {
            ConfigUtil.saveConfig(this.config, Config.DEFAULT_CONFIG_FILE);
        } catch (IOException ioe) {
            logger.warning("IOException thrown in Load Changed Event.");
            this.config.setTaskManagerFilePath(oldPath);
            setTaskManagerFilePath(oldPath);
            ioe.printStackTrace();
        }
    }

    @Override
    public void setTaskManagerFilePath(String filePath) {
        this.taskManagerStorage.setTaskManagerFilePath(filePath);
    }

    @Override
    public void copyTaskManager(String oldPath, String newPath) throws IOException {
        this.taskManagerStorage.copyTaskManager(oldPath, newPath);
    }
```
###### /java/seedu/doit/storage/TaskManagerStorage.java
``` java
    /**
     * Changes the file path of the data file.
     */
    void setTaskManagerFilePath(String filePath);

    /**
     * Returns TaskManager data as a {@link ReadOnlyItemManager}. Returns
     * {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException
     *             if the data in storage is not in the expected format.
     * @throws IOException
     *             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyItemManager> readTaskManager() throws DataConversionException, IOException;

    /**
     * @see #getTaskManagerFilePath()
     */
    Optional<ReadOnlyItemManager> readTaskManager(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyItemManager} to the storage.
     *
     * @param taskManager
     *            cannot be null.
     * @throws IOException
     *             if there was any problem writing to the file.
     */
    void saveTaskManager(ReadOnlyItemManager taskManager) throws IOException;

    /**
     * @see #saveTaskManager(ReadOnlyItemManager)
     */
    void saveTaskManager(ReadOnlyItemManager taskManager, String filePath) throws IOException;

```
###### /java/seedu/doit/storage/TaskManagerStorage.java
``` java
    /**
     * Copies the file in oldPath to file in newPath
     *
     * @param oldPath
     * @param newPath
     * @throws IOException
     *             if there is error when copying files
     */
    void copyTaskManager(String oldPath, String newPath) throws IOException;
```
###### /java/seedu/doit/storage/XmlAdaptedTask.java
``` java
    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source
     *            future changes to this will not affect the created
     *            XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        this.name = source.getName().fullName;
        this.priority = source.getPriority().value;
        if (source.hasStartTime()) {
            this.startTime = source.getStartTime().value;
        } else {
            this.startTime = null;
        }
        if (source.hasEndTime()) {
            this.deadline = source.getDeadline().value;
        } else {
            this.deadline = null;
        }
        this.description = source.getDescription().value;
        this.tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            this.tagged.add(new XmlAdaptedTag(tag));
        }
        this.isDone = source.getIsDone();
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task
     * object.
     *
     * @throws IllegalValueException
     *             if there were any data constraints violated in the adapted
     *             task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : this.tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Priority priority = new Priority(this.priority);
        final StartTime startTime = new StartTime(this.startTime);
        final EndTime deadline = new EndTime(this.deadline);
        final Description description = new Description(this.description);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, priority, startTime, deadline, description, tags, this.isDone);
    }
```
###### /java/seedu/doit/storage/XmlTaskManagerStorage.java
``` java
    @Override
    public void copyTaskManager(String oldPath, String newPath) throws IOException {
        assert oldPath != null;
        assert newPath != null;
        try {
            logger.info("Copying file.");
            Files.copy(Paths.get(oldPath), Paths.get(newPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            logger.info("I/O Exception when copying file.");
            throw new IOException("Error when copying file.");
        }
    }
```
###### /java/seedu/doit/ui/CommandBox.java
``` java
    @FXML
    private void handleCommandInputChanged() {
        String currentText = this.commandTextField.getText();
        this.inputs.addToMainStack(currentText);
        try {
            CommandResult commandResult = this.logic.execute(currentText);
            // process result of the command
            setStyleToIndicateCommandSuccess();
            setCommandBoxText("");
            this.logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException e) {
            // handle command failure
            setStyleToIndicateCommandFailure();
            setCommandBoxText("");
            this.logger.info("Invalid command: " + this.commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

```
