package seedu.taskmanager.model;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.taskmanager.commons.core.ComponentManager;
import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.commons.events.model.TaskManagerChangedEvent;
import seedu.taskmanager.commons.events.storage.TaskManagerStorageDirectoryChangedEvent;
import seedu.taskmanager.logic.commands.RedoCommand;
import seedu.taskmanager.logic.commands.UndoCommand;
import seedu.taskmanager.storage.StorageManager;

// @@author A0140032E
/**
 * Represents the History of user commands in this session
 */
public class HistoryManager extends ComponentManager {

    private Model model;
    private static HistoryManager instance = null;
    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ArrayList<ReadOnlyTaskManager> historyList;
    private ArrayList<ReadOnlyTaskManager> futureList;
    private ArrayList<String> historyCommands;
    private ArrayList<String> futureCommands;

    protected HistoryManager() {
        super();

        historyList = new ArrayList<ReadOnlyTaskManager>();
        futureList = new ArrayList<ReadOnlyTaskManager>();
        historyCommands = new ArrayList<String>();
        futureCommands = new ArrayList<String>();
        TaskManager taskManager = new TaskManager(model.getTaskManager());
        historyList.add(taskManager);
    }

    public static HistoryManager getInstance() {
        if (instance == null) {
            instance = new HistoryManager();
        }
        return instance;
    }

    public void init(Model model) {
        this.model = model;
    }

    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent event) {
        TaskManager taskManager = new TaskManager(event.data);
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
    public void handleTaskManagerStorageDirectoryChangedEvent(TaskManagerStorageDirectoryChangedEvent event) {
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
        ReadOnlyTaskManager taskManager = historyList.remove(historyList.size() - 1);
        futureList.add(new TaskManager(taskManager));
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
        HistoryItemPair history = new HistoryItemPair(futureList.remove(futureList.size() - 1),
                commandText);
        return history;
    }

    public String undo() {
        HistoryItemPair t = getMostRecentHistory();
        model.resetData(t.getTaskManager());
        return t.getCommandText();
    }

    public String redo() {
        HistoryItemPair t = getMostRecentFuture();
        model.resetData(t.getTaskManager());
        return t.getCommandText();
    }
}

class HistoryItemPair {
    private ReadOnlyTaskManager taskManager;
    private String commandText;

    public HistoryItemPair(ReadOnlyTaskManager tm, String text) {
        taskManager = tm;
        commandText = text;
    }

    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    public String getCommandText() {
        return commandText;
    }
}
