//@@author A0131125Y
package seedu.toluist.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.function.Predicate;

/**
 * Predicates to filter tasks on switching tab
 */
public class TaskSwitchPredicate {
    public static final Predicate<Task> PREDICATE_ALL = task -> true;
    public static final Predicate<Task> PREDICATE_INCOMPLETE = task -> !task.isCompleted();
    public static final Predicate<Task> PREDICATE_COMPLETED = task -> task.isCompleted();
    public static final Predicate<Task> PREDICATE_TODAY = task -> {
        ZonedDateTime startOfTodayDatetime = ZonedDateTime.now().toLocalDate()
                .atStartOfDay(ZoneId.systemDefault());
        LocalDateTime startOfToday =  LocalDateTime
                .ofInstant(startOfTodayDatetime.toInstant(), ZoneId.systemDefault());

        ZonedDateTime endOfTodayDatetime = ZonedDateTime.now().toLocalDate()
                .atStartOfDay(ZoneId.systemDefault())
                .plusDays(1).minusSeconds(1);
        LocalDateTime endOfToday =  LocalDateTime
                .ofInstant(endOfTodayDatetime.toInstant(), ZoneId.systemDefault());

        return task.isWithinInterval(startOfToday, endOfToday);
    };
    public static final Predicate<Task> PREDICATE_NEXT_7_DAYS = task -> {
        ZonedDateTime startOfTomorrowDatetime = ZonedDateTime.now().toLocalDate()
                .plusDays(1).atStartOfDay(ZoneId.systemDefault());
        LocalDateTime startOfTomorrow =  LocalDateTime
                .ofInstant(startOfTomorrowDatetime.toInstant(), ZoneId.systemDefault());

        ZonedDateTime endOf7DaysDatetime = ZonedDateTime.now().toLocalDate()
                .atStartOfDay(ZoneId.systemDefault())
                .plusDays(8).minusSeconds(1);
        LocalDateTime endOf7Days =  LocalDateTime
                .ofInstant(endOf7DaysDatetime.toInstant(), ZoneId.systemDefault());

        return task.isWithinInterval(startOfTomorrow, endOf7Days);
    };
    public static final TaskSwitchPredicate SWITCH_PREDICATE_ALL =
            new TaskSwitchPredicate(PREDICATE_ALL, "ALL");
    public static final TaskSwitchPredicate SWITCH_PREDICATE_INCOMPLETE =
            new TaskSwitchPredicate(PREDICATE_INCOMPLETE, "INCOMPLETE");
    public static final TaskSwitchPredicate COMPLETED_SWITCH_PREDICATE =
            new TaskSwitchPredicate(PREDICATE_COMPLETED, "COMPLETED");
    public static final TaskSwitchPredicate SWITCH_PREDICATE_TODAY =
            new TaskSwitchPredicate(PREDICATE_TODAY, "TODAY");
    public static final TaskSwitchPredicate SWITCH_PREDICATE_NEXT_7_DAYS =
            new TaskSwitchPredicate(PREDICATE_NEXT_7_DAYS, "NEXT 7 DAYS");

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
