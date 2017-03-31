package seedu.taskmanager.model;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.taskmanager.commons.core.ComponentManager;
import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.commons.events.model.TaskManagerChangedEvent;
import seedu.taskmanager.storage.StorageManager;

// @@author A0140032E
/**
 * Represents the History of user commands in this session
 */
public class HistoryManager extends ComponentManager{

    private final Model model;
    private static HistoryManager instance = null;
    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ArrayList<ReadOnlyTaskManager> historyList;
    private ArrayList<ReadOnlyTaskManager> futureList;
    
    public static final String INITIALIZATION_ERROR = "HistoryManager has not been initialized with a Model yet";


    protected HistoryManager(Model model) {
        super();
        this.model = model;

        historyList = new ArrayList<ReadOnlyTaskManager>();
        futureList = new ArrayList<ReadOnlyTaskManager>();

        TaskManager taskManager = new TaskManager(model.getTaskManager());
        historyList.add(taskManager);
    }

    public static HistoryManager createInstance(Model model) {
        instance = new HistoryManager(model);
        return instance;
    }
    public static HistoryManager getInstance() {
        if (instance == null) {
            throw new NullPointerException(INITIALIZATION_ERROR);
        }
        return instance;
    }

    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, updating history manager"));
        TaskManager taskManager = new TaskManager(event.data);
        historyList.add(taskManager);
    }
    
    private ReadOnlyTaskManager getMostRecentHistory() {
        if (historyList.size() < 2) {
            throw new NullPointerException();
        }
        ReadOnlyTaskManager t = historyList.remove(historyList.size() - 1);
        futureList.add(new TaskManager(t));
        return historyList.remove(historyList.size() - 1);
    }
    
    private ReadOnlyTaskManager getMostRecentFuture() {
        if (futureList.size() < 1) {
            throw new NullPointerException();
        }
        ReadOnlyTaskManager t = futureList.remove(futureList.size() - 1);
        return t;
    }
    
    public void undo() {
        ReadOnlyTaskManager t = getMostRecentHistory();
        model.resetData(t);
    }

    public void redo() {
        ReadOnlyTaskManager t = getMostRecentFuture();
        model.resetData(t);
    }
}
