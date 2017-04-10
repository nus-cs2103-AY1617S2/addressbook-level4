package seedu.jobs.commons.events.model;

import seedu.jobs.commons.events.BaseEvent;
import seedu.jobs.model.task.ReadOnlyTask;
import seedu.jobs.model.task.Task;

public class EditCommandEvent extends BaseEvent {

    private ReadOnlyTask taskToEdit;
    private Task editedTask;

    public EditCommandEvent(ReadOnlyTask taskToEdit, Task editedTask) {
        this.taskToEdit = taskToEdit;
        this.editedTask = editedTask;
    }

    public ReadOnlyTask getTaskToEdit() {
        return taskToEdit;
    }

    public Task getEditedTask() {
        return editedTask;
    }

    @Override
    public String toString() {

        return null;
    }

}
