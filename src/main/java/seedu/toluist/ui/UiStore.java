package seedu.toluist.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import seedu.toluist.model.Task;
import seedu.toluist.model.TaskSwitchPredicate;
import seedu.toluist.ui.commons.CommandResult;
import seedu.toluist.ui.view.UiView;

/**
 * UiStore acts like a "single source of truth" / view model for the Ui
 * Ui will observe for changes to `shownTasks` and re-render on state change
 */
public class UiStore {
    private static UiStore instance;

    private ArrayList<Task> allTasks = new ArrayList<>();
    private ObjectProperty<TaskSwitchPredicate> observableSwitchPredicate =
            new SimpleObjectProperty<>(TaskSwitchPredicate.INCOMPLETE_SWITCH_PREDICATE);
    private ObservableList<Task> shownTasks = FXCollections.observableArrayList();
    private ObjectProperty<CommandResult> observableCommandResult =
            new SimpleObjectProperty<>(new CommandResult(""));
    private SimpleStringProperty observableCommandText = new SimpleStringProperty("");
    private ObservableList<String> observableSuggestedCommands = FXCollections.observableArrayList();
    private SimpleIntegerProperty observableSuggestedCommandIndex = new SimpleIntegerProperty(-1);

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
        observableList.addListener((ListChangeListener) (c -> view.render()));
    }

    /**
     * Bind view to an observable value. View will re-render on value change
     * @param view a UiView
     * @param observableValue an observable value
     */
    public void bind(UiView view, ObservableValue<?> observableValue) {
        observableValue.addListener(c ->  view.render());
    }

    public void setObservableSwitchPredicate(TaskSwitchPredicate switchPredicate) {
        observableSwitchPredicate.setValue(switchPredicate);
        changeShownTasks();
    }

    public ObservableValue<TaskSwitchPredicate> getObservableSwitchPredicate() {
        return observableSwitchPredicate;
    }

    public void setCommandResult(CommandResult commandResult) {
        observableCommandResult.setValue(commandResult);
    }

    public ObservableValue<String> getObservableCommandText() {
        return observableCommandText;
    }

    public void setSuggestedCommands(List<String> suggestedCommands) {
        observableSuggestedCommandIndex.set(-1);
        observableSuggestedCommands.setAll(suggestedCommands);
    }

    public ObservableList<String> getObservableSuggestedCommands() {
        return observableSuggestedCommands;
    }

    public void setCommandText(String commandText) {
        observableCommandText.set(commandText);
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

    public void setTasks(ArrayList<Task> tasks) {
        // Sorted by default
        Collections.sort(tasks);
        this.allTasks = tasks;
        changeShownTasks();
    }

    public ArrayList<Task> getTasks() {
        return allTasks;
    }

    /**
     * Returns list of tasks are currently shown on the Ui
     */
    public ArrayList<Task> getShownTasks() {
        return new ArrayList(Arrays.asList(shownTasks.toArray()));
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
        return shownTasks;
    }

    private void changeShownTasks() {
        shownTasks.setAll(allTasks.stream()
                .filter(observableSwitchPredicate.getValue().getPredicate()).collect(Collectors.toList()));
    }
}
