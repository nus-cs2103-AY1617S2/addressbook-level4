package seedu.toluist.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.toluist.storage.JsonStorage;
import seedu.toluist.storage.Storage;

/**
 * TodoList Model
 */
public class TodoList {

    protected static Storage storage = JsonStorage.getInstance();

    private static TodoList currentTodoList = new TodoList();

    private ArrayList<Task> allTasks = new ArrayList<>();

    public ArrayList<Task> getTasks() {
        return allTasks;
    }

    public static TodoList load() {
        Optional<TodoList> todoListOptional = storage.load();

        // Save data to file again if data is not found on disk
        if (!todoListOptional.isPresent()) {
            currentTodoList.save();
        } else {
            currentTodoList = todoListOptional.get();
        }

        return storage.load().orElse(currentTodoList);
    }

    public boolean save() {
        currentTodoList = this;
        return storage.save(this);
    }

    public void add(Task task) {
        // Don't allow duplicate tasks
        if (allTasks.indexOf(task) > -1) {
            return;
        }

        allTasks.add(task);
    }

    public void remove(Task task) {
        allTasks.remove(task);
    }
    
    public Task get(Task task) {
        return allTasks.get(allTasks.indexOf(task));
    }    

    public void update(int index, String description) {
        if (index >= 0 && index < allTasks.size()) {
            allTasks.get(index).description = description;
        }
    }

    /**
     * Returns list of tasks based on predicate
     * @param predicate a predicate
     * @return a list of task
     */
    public ArrayList<Task> getFilterTasks(Predicate<Task> predicate) {
        List<Task> taskList = getTasks().stream().filter(predicate).collect(Collectors.toList());
        return new ArrayList<>(taskList);
    }
}
