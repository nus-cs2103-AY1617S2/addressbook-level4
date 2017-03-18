package seedu.toluist.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.function.Predicate;

/**
 * Predicates to filter tasks on switching tab
 */
public class TaskSwitchPredicate {
    public static final Predicate<Task> ALL_PREDICATE = task -> true;
    public static final Predicate<Task> INCOMPLETE_PREDICATE = task -> !task.isCompleted();
    public static final Predicate<Task> COMPLETED_PREDICATE = task -> task.isCompleted();
    public static final Predicate<Task> TODAY_PREDICATE = task -> {
        ZonedDateTime startOfTodayDatetime = ZonedDateTime.now().toLocalDate().atStartOfDay(ZoneId.systemDefault());
        LocalDateTime startOfToday =  LocalDateTime.ofInstant(startOfTodayDatetime.toInstant(), ZoneId.systemDefault());

        ZonedDateTime endOfTodayDatetime = ZonedDateTime.now().toLocalDate()
                .atStartOfDay(ZoneId.systemDefault())
                .plusDays(1).minusSeconds(1);
        LocalDateTime endOfToday =  LocalDateTime.ofInstant(endOfTodayDatetime.toInstant(), ZoneId.systemDefault());

        return task.isWithinInterval(startOfToday, endOfToday);
    };
    public static final Predicate<Task> WITHIN_7_DAYS_PREDICATE = task -> {
        ZonedDateTime startOfTodayDatetime = ZonedDateTime.now().toLocalDate().atStartOfDay(ZoneId.systemDefault());
        LocalDateTime startOfToday =  LocalDateTime.ofInstant(startOfTodayDatetime.toInstant(), ZoneId.systemDefault());

        ZonedDateTime endOf7DaysDatetime = ZonedDateTime.now().toLocalDate()
                .atStartOfDay(ZoneId.systemDefault())
                .plusDays(8).minusSeconds(1);
        LocalDateTime endOf7Days =  LocalDateTime.ofInstant(endOf7DaysDatetime.toInstant(), ZoneId.systemDefault());

        return task.isWithinInterval(startOfToday, endOf7Days);
    };
    public static final TaskSwitchPredicate ALL_SWITCH_PREDICATE =
            new TaskSwitchPredicate(ALL_PREDICATE, "All");
    public static final TaskSwitchPredicate INCOMPLETE_SWITCH_PREDICATE =
            new TaskSwitchPredicate(INCOMPLETE_PREDICATE, "Incomplete");
    public static final TaskSwitchPredicate COMPLETED_SWITCH_PREDICATE =
            new TaskSwitchPredicate(COMPLETED_PREDICATE, "Completed");
    public static final TaskSwitchPredicate TODAY_SWITCH_PREDICATE =
            new TaskSwitchPredicate(TODAY_PREDICATE, "Today");
    public static final TaskSwitchPredicate WITHIN_7_DAYS_SWITCH_PREDICATE =
            new TaskSwitchPredicate(WITHIN_7_DAYS_PREDICATE, "Within 7 Days");

    private final Predicate<Task> predicate;
    private final String displayName;

    public TaskSwitchPredicate(Predicate<Task> predicate, String displayName) {
        this.predicate = predicate;
        this.displayName = displayName;
    }

    public Predicate<Task> getPredicate() {
        return predicate;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskSwitchPredicate // instanceof handles nulls
                && predicate.equals(((TaskSwitchPredicate) other).predicate)
                && displayName.equals(((TaskSwitchPredicate) other).displayName));
    }
}
