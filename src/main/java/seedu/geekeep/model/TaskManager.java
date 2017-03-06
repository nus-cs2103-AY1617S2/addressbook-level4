package seedu.geekeep.model;

import javafx.collections.ObservableList;
import seedu.geekeep.commons.core.UnmodifiableObservableList;
import seedu.geekeep.model.task.ReadOnlyTask;
import seedu.geekeep.model.task.UniqueTaskList;

public class TaskManager implements ReadOnlyTaskManager {

    private final UniqueTaskList tasks;

    {
        tasks = new UniqueTaskList();
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        return new UnmodifiableObservableList<>(tasks.asObservableList());
    }
}
