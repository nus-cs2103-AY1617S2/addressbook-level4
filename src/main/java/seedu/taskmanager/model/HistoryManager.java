package seedu.taskmanager.model;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.taskmanager.commons.core.ComponentManager;
import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.commons.events.model.TaskManagerChangedEvent;
import seedu.taskmanager.commons.events.storage.TaskManagerStorageDirectoryChangedEvent;
import seedu.taskmanager.commons.events.ui.JumpToListRequestEvent;
import seedu.taskmanager.logic.commands.AddCommand;
import seedu.taskmanager.logic.commands.RedoCommand;
import seedu.taskmanager.logic.commands.UndoCommand;
import seedu.taskmanager.storage.StorageManager;

// @@author A0140032E
/**
 * Represents the History of user commands in this session
 */
public class HistoryManager extends ComponentManager {

    private static HistoryManager instance = null;

    private static final int OFFSET_ONE_INDEX = -1;
    private static final int INVALID_INDEX = -1;
    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);

    private Model model;
    private ArrayList<HistoryNode> historyList;
    private ArrayList<HistoryNode> futureList;
    private ArrayList<String> historyCommands;
    private ArrayList<String> futureCommands;

    protected HistoryManager() {
        super();

        historyList = new ArrayList<HistoryNode>();
        futureList = new ArrayList<HistoryNode>();
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
        TaskManager taskManager = new TaskManager(model.getTaskManager());
        historyList.add(new HistoryNode(taskManager, INVALID_INDEX, INVALID_INDEX));
    }

    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent event) {
        TaskManager taskManager = new TaskManager(event.data);
        String commandText = new String(event.commandText);
        historyList.add(new HistoryNode(taskManager, INVALID_INDEX, parseIndex(commandText)));

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
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        if (!event.isSelectCommand) {
            historyList.get(historyList.size() - 1).setHighlightedIndex(event.targetIndex);
        }
    }

    @Subscribe
    public void handleTaskManagerStorageDirectoryChangedEvent(TaskManagerStorageDirectoryChangedEvent event) {
        reset();
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                ("Storage file location changed, resetting history. Histories = " + historyList.size() + " Futures = "
                        + futureList.size())));
    }

    public void reset() {
        historyList.clear();
        futureList.clear();
        historyCommands.clear();
        futureCommands.clear();
    }

    private HistoryItem getMostRecentHistory() {
        if (historyList.size() < 2) {
            throw new NullPointerException();
        }
        HistoryNode historyNode = historyList.remove(historyList.size() - 1);
        futureList.add(new HistoryNode(historyNode));
        String commandText = historyCommands.remove(historyCommands.size() - 1);
        futureCommands.add(new String(commandText));
        int indexToHighlight = historyNode.getParsedIndex() >= 0 ? historyNode.getParsedIndex()
                : historyList.get(historyList.size() - 1).getHighlightedIndex();
        HistoryItem history = new HistoryItem(historyList.remove(historyList.size() - 1), commandText,
                indexToHighlight);
        return history;
    }

    private HistoryItem getMostRecentFuture() {
        if (futureList.size() < 1) {
            throw new NullPointerException();
        }
        String commandText = futureCommands.remove(futureCommands.size() - 1);
        historyCommands.add(new String(commandText));
        HistoryNode historyNode = futureList.remove(futureList.size() - 1);
        HistoryItem history = new HistoryItem(historyNode, commandText, historyNode.getHighlightedIndex());
        return history;
    }

    public String undo() {
        HistoryItem t = getMostRecentHistory();
        model.resetData(t.getHistoryNode().getReadOnlyTaskManager());
        model.indicateJumpToListRequestEvent(t.getIndexToHighlight());
        return t.getCommandText();
    }

    public String redo() {
        HistoryItem t = getMostRecentFuture();
        model.resetData(t.getHistoryNode().getReadOnlyTaskManager());
        model.indicateJumpToListRequestEvent(t.getIndexToHighlight());
        return t.getCommandText();
    }

    private int parseIndex(String args) {
        String[] splitted = args.split(" ");
        try {
            if (splitted[0].equals(AddCommand.COMMAND_WORD)) {
                return INVALID_INDEX;
            }
            return Integer.parseInt(splitted[1]) + OFFSET_ONE_INDEX;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return INVALID_INDEX;
        }
    }
}

class HistoryItem {
    private HistoryNode historyNode;
    private String commandText;
    private int indexToHighlight;

    public HistoryItem(HistoryNode historyNode, String text, int indexToHighlight) {
        this.historyNode = historyNode;
        commandText = text;
        this.indexToHighlight = indexToHighlight;
    }

    public HistoryNode getHistoryNode() {
        return historyNode;
    }

    public String getCommandText() {
        return commandText;
    }

    public int getIndexToHighlight() {
        return indexToHighlight;
    }
}

class HistoryNode {
    private ReadOnlyTaskManager taskManager;
    private int highlightedIndex;
    private int parsedIndex;

    public HistoryNode(ReadOnlyTaskManager tm, int highlightedIndex, int parsedIndex) {
        taskManager = tm;
        this.highlightedIndex = highlightedIndex;
        this.parsedIndex = parsedIndex;
    }

    public HistoryNode(HistoryNode historyNode) {
        this.taskManager = historyNode.taskManager;
        this.highlightedIndex = historyNode.highlightedIndex;
        this.parsedIndex = historyNode.parsedIndex;
    }

    public ReadOnlyTaskManager getReadOnlyTaskManager() {
        return taskManager;
    }

    public int getHighlightedIndex() {
        return highlightedIndex;
    }

    public int getParsedIndex() {
        return parsedIndex;
    }

    public void setHighlightedIndex(int highlightedIndex) {
        this.highlightedIndex = highlightedIndex;
    }
}
