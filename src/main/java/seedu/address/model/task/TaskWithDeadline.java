package seedu.address.model.task;

import java.util.Calendar;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.UniqueTagList;

public class TaskWithDeadline extends Task {

  static final String MESSAGE_DATETIME_CONSTRAINTS = "Deadline should be after starting time.";

  Deadline deadline = null;
  StartingTime startingTime = null;

  /*
   * starting time may be null
   */
  public TaskWithDeadline(Name name, UniqueTagList tags, Calendar deadline, boolean isDeadlineMissingDate,
      boolean isDeadlineMissingTime, Calendar startingTime, boolean isStartingTimeMissingDate,
      boolean isStartingTimeMissingTime) throws IllegalValueException {
    super(name, tags);
    this.deadline = new Deadline(deadline, isDeadlineMissingDate, isDeadlineMissingTime);
    if (startingTime != null)
      this.startingTime = new StartingTime(startingTime, isStartingTimeMissingDate, isStartingTimeMissingTime);
    validateDateTime();
  }

  /**
   * validates deadline and starting time
   * 
   * @throws IllegalValueException
   */
  private void validateDateTime() throws IllegalValueException {
    if (this.startingTime != null && this.startingTime.isAfter(this.deadline))
      throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
  }

  public TaskWithDeadline(ReadOnlyTask source) {
    super(source);
  }
}
