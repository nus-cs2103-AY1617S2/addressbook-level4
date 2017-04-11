package seedu.task.model.task;

import java.util.function.Predicate;

import seedu.task.commons.exceptions.IllegalValueException;

public class TaskDateBeforePredicate implements Predicate<Object> {
    public static final String PREDICATE_WORD = "before";
    // @@author A0163845X

    private TaskDate date;

    public TaskDateBeforePredicate(String date) throws IllegalValueException {
        this.date = new TaskDate(date);
        System.out.println(date);
    }

    @Override
    public boolean test(Object arg0) {

        Task task = (Task) arg0;
        System.out.println(date);

        System.out.println(task.getTaskDate());
        if (task.getTaskDate() != null)
            System.out.println(task.getTaskDate().compareTo(date));

        if (task.getTaskDate() != null && date.compareTo(task.getTaskDate()) >= 0) {
            return true;
        } else {
            return false;
        }
    }

}
