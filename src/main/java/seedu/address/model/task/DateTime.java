package seedu.address.model.task;

import org.ocpsoft.prettytime.PrettyTime;

/**
 * Represents a Task's date and time for a deadline or a starting time in the
 * task manager.
 */
public abstract class DateTime {

  PrettyTime dateTime;

  public PrettyTime getDateTime() {
    return dateTime;
  }

  public Boolean isAfter(DateTime dt) {
    return dateTime.getReference().after(dt.getDateTime().getReference());
  }

}