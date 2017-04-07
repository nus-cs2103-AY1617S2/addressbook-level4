package seedu.bulletjournal.model;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.bulletjournal.commons.core.ComponentManager;
import seedu.bulletjournal.commons.core.LogsCenter;
import seedu.bulletjournal.commons.events.model.FilePathChangedEvent;
import seedu.bulletjournal.commons.events.model.TodoListChangedEvent;
import seedu.bulletjournal.logic.commands.RedoCommand;
import seedu.bulletjournal.logic.commands.UndoCommand;
import seedu.bulletjournal.storage.StorageManager;

/**
 * Represents the History of user commands in this session
 */
public class HistoryManager extends ComponentManager {

    private Model model;
    private static HistoryManager instance = null;
    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ArrayList<ReadOnlyTodoList> historyList;
    private ArrayList<ReadOnlyTodoList> futureList;
    private ArrayList<String> historyCommands;
    private ArrayList<String> futureCommands;

    protected HistoryManager() {
        super();

        historyList = new ArrayList<ReadOnlyTodoList>();
        futureList = new ArrayList<ReadOnlyTodoList>();
        historyCommands = new ArrayList<String>();
        futureCommands = new ArrayList<String>();
    }

    public static HistoryManager getInstance() {
        if (instance == null) {
            instance = new HistoryManager();
        }
        return instance;
    }

    public void init(Model model) {
        this.model = model;
        TodoList taskManager = new TodoList(model.getTodoList());
        historyList.add(taskManager);
    }

    @Subscribe
    public void handleTaskManagerChangedEvent(TodoListChangedEvent event) {
        TodoList taskManager = new TodoList(event.data);
        String commandText = new String(event.commandText);
        historyList.add(taskManager);
        if (!commandText.equals(RedoCommand.COMMAND_WORD) && !commandText.equals(UndoCommand.COMMAND_WORD)) {
            historyCommands.add(commandText);
        }
        if (!(commandText.equals(RedoCommand.COMMAND_WORD) || commandText.equals(UndoCommand.COMMAND_WORD))) {
            futureList.clear();
            futureCommands.clear();
        }
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                ("Local data changed, updating history manager. Histories = " + historyList.size() + " Futures = "
                        + futureList.size())));
    }

    @Subscribe
    public void handleTaskManagerStorageDirectoryChangedEvent(FilePathChangedEvent event) {
        historyList.clear();
        futureList.clear();
        historyCommands.clear();
        futureCommands.clear();
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                ("Storage file location changed, resetting history. Histories = " + historyList.size() + " Futures = "
                        + futureList.size())));
    }

    private HistoryItemPair getMostRecentHistory() {
        if (historyList.size() < 2) {
            throw new NullPointerException();
        }
        ReadOnlyTodoList taskManager = historyList.remove(historyList.size() - 1);
        futureList.add(new TodoList(taskManager));
        String commandText = historyCommands.remove(historyCommands.size() - 1);
        futureCommands.add(new String(commandText));
        HistoryItemPair history = new HistoryItemPair(historyList.remove(historyList.size() - 1), commandText);
        return history;
    }

    private HistoryItemPair getMostRecentFuture() {
        if (futureList.size() < 1) {
            throw new NullPointerException();
        }
        String commandText = futureCommands.remove(futureCommands.size() - 1);
        historyCommands.add(new String(commandText));
        HistoryItemPair history = new HistoryItemPair(futureList.remove(futureList.size() - 1), commandText);
        return history;
    }

    public String undo() {
        HistoryItemPair t = getMostRecentHistory();
        model.resetData(t.getTodoList());
        return t.getCommandText();
    }

    public String redo() {
        HistoryItemPair t = getMostRecentFuture();
        model.resetData(t.getTodoList());
        return t.getCommandText();
    }
}

class HistoryItemPair {
    private ReadOnlyTodoList taskManager;
    private String commandText;

    public HistoryItemPair(ReadOnlyTodoList tm, String text) {
        taskManager = tm;
        commandText = text;
    }

    public ReadOnlyTodoList getTodoList() {
        return taskManager;
    }

    public String getCommandText() {
        return commandText;
    }
}
