//@@author A0131125Y
package seedu.toluist.ui;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import javafx.util.Pair;
import seedu.toluist.commons.core.Config;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.model.Task;
import seedu.toluist.model.TaskSwitchPredicate;
import seedu.toluist.ui.commons.CommandResult;
import seedu.toluist.ui.view.UiView;

/**
 * UiStore acts like a "single source of truth" / view model for the Ui
 * Ui will observe for changes to `shownTasks` and re-render on state change
 */
public class UiStore {
    public static final int INDEX_INVALID_SUGGESTION = -1;

    private static UiStore instance;

    private ObjectProperty<TaskSwitchPredicate> observableSwitchPredicate =
            new SimpleObjectProperty<>(TaskSwitchPredicate.SWITCH_PREDICATE_INCOMPLETE);
    private ObservableList<Task> observableTasks = FXCollections.observableArrayList();
    private ObjectProperty<Pair<String, List<String>>> observableHelp =
            new SimpleObjectProperty<>(new Pair(null, new ArrayList()));
    private ObjectProperty<CommandResult> observableCommandResult =
            new SimpleObjectProperty<>(new CommandResult(StringUtil.EMPTY_STRING));
    private StringProperty commandInputProperty = new SimpleStringProperty(StringUtil.EMPTY_STRING);
    private StringProperty observableStoragePath =
            new SimpleStringProperty(Config.getInstance().getTodoListFilePath());
    private ObservableList<String> observableSuggestedCommands = FXCollections.observableArrayList();
    private SimpleIntegerProperty observableSuggestedCommandIndex =
            new SimpleIntegerProperty(INDEX_INVALID_SUGGESTION);
    private SimpleDoubleProperty observableCommandTextWidth = new SimpleDoubleProperty(0);
    private Task lastEditedTask;

    public static UiStore getInstance() {
        if (instance == null) {
            instance = new UiStore();
        }
        return instance;
    }

    private UiStore() {}

    /**
     * Bind view to an observable list. View will re-render on list change
     * @param view a UiView
     * @param observableList an observable list
     */
    public void bind(UiView view, ObservableList<?> observableList) {
        WeakReference<UiView> weakView = new WeakReference<>(view);
        observableList.addListener((ListChangeListener) (c -> {
            Platform.runLater(() -> {
                if (weakView.get() != null) {
                    weakView.get().render();
                }
            });
        }));
    }

    /**
     * Bind view to an observable value. View will re-render on value change
     * @param view a UiView
     * @param observableValue an observable value
     */
    public void bind(UiView view, ObservableValue<?> observableValue) {
        WeakReference<UiView> weakView = new WeakReference<>(view);
        observableValue.addListener(c -> {
            Platform.runLater(() -> {
                if (weakView.get() != null) {
                    weakView.get().render();
                }
            });
        });
    }

    public void setObservableSwitchPredicate(TaskSwitchPredicate switchPredicate) {
        observableSwitchPredicate.setValue(switchPredicate);
    }

    public ObservableValue<TaskSwitchPredicate> getObservableSwitchPredicate() {
        return observableSwitchPredicate;
    }

    public void setCommandResult(CommandResult commandResult) {
        observableCommandResult.setValue(commandResult);
    }

    public void setSuggestedCommands(List<String> suggestedCommands) {
        observableSuggestedCommandIndex.set(INDEX_INVALID_SUGGESTION);
        observableSuggestedCommands.setAll(suggestedCommands);
    }

    public void setStoragePath(String storagePath) {
        observableStoragePath.setValue(storagePath);
    }

    public ObservableValue<String> getObservableStoragePath() {
        return observableStoragePath;
    }

    public ObservableList<String> getObservableSuggestedCommands() {
        return observableSuggestedCommands;
    }

    public void setCommandInput(String commandInput) {
        commandInputProperty.setValue(commandInput);
    }

    public StringProperty getCommandInputProperty() {
        return commandInputProperty;
    }

    public ObservableValue<CommandResult> getObservableCommandResult() {
        return observableCommandResult;
    }

    public void incrementSuggestedCommandIndex() {
        if (observableSuggestedCommands.isEmpty()) {
            return;
        }

        observableSuggestedCommandIndex.set((observableSuggestedCommandIndex.get() + 1) %
                observableSuggestedCommands.size());
    }

    public ObservableIntegerValue getObservableSuggestedCommandIndex() {
        return observableSuggestedCommandIndex;
    }

    public void setCommandTextWidth(double width) {
        observableCommandTextWidth.setValue(width);
    }

    public DoubleProperty getObservableCommandTextWidth() {
        return observableCommandTextWidth;
    }

    public void setTasks(ArrayList<Task> tasks) {
        setTasks(tasks, null);
    }

    /**
     * Overloaded method to setTasks and last editted task at the same time
     * @params tasks tasks to replace current task
     * @param lastEditedTask last editted task
     */
    public void setTasks(ArrayList<Task> tasks, Task lastEditedTask) {
        // Sorted by default
        Collections.sort(tasks);
        this.lastEditedTask = lastEditedTask;
        observableTasks.clear();
        observableTasks.setAll(tasks);
    }

    public ArrayList<Task> getTasks() {
        return observableTasks.stream().collect(Collectors.toCollection(ArrayList::new));
    }

    public Task getLastEditedTask() {
        return lastEditedTask;
    }

    /**
     * Returns list of tasks are currently shown on the Ui
     */
    public ArrayList<Task> getShownTasks() {
        return getTasks().stream().filter(observableSwitchPredicate.get()
                .getPredicate()).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Task> getShownTasks(List<Integer> indexes) {
        List<Task> shownTasks = getShownTasks();
        ArrayList<Task> tasks = new ArrayList<>();
        for (int index : indexes) {
            if (index < 1 || index > shownTasks.size()) {
                continue;
            }
            tasks.add(shownTasks.get(index - 1));
        }
        return tasks;
    }

    public ObservableList<Task> getObservableTasks() {
        return observableTasks;
    }

    /**
     * Find the index of the task in shownTasks
     * @param task
     * @return the index if it is found, else -1
     */
    public int getTaskIndex(Task task) {
        return getShownTasks().indexOf(task);
    }

    //@@author A0162011A
    public ObservableValue<Pair<String, List<String>>> getObservableHelp() {
        return observableHelp;
    }

    /**
     * method to setHelp
     * @param heading general or help or specific command
     * @param helpList details to replace current help
     */
    public void setHelp(String heading, List<String> helpList) {
        observableHelp.setValue(new Pair(heading, helpList));
    }
}
