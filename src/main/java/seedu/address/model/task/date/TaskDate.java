//@@author A0144885R
package seedu.address.model.task.date;

/**
 * An interface represents date, time or a timeperiod.
 */
public interface TaskDate {

    /* Floating taskDate contains no value */
    public boolean isFloating();
    public boolean hasPassed();
    public boolean isHappeningToday();
    public boolean isHappeningTomorrow();

    public DateValue getBeginning();
    public DateValue getEnding();
}
