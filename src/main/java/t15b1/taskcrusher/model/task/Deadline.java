package t15b1.taskcrusher.model.task;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import t15b1.taskcrusher.commons.exceptions.IllegalValueException;

/**
 * Represents a deadline for a task. Empty deadline means no deadline.
 */
public class Deadline {

	public static final String MESSAGE_DEADLINE_CONSTRAINTS = "Deadline provided must be a relative or absolute date";
	public static final String NO_DEADLINE = "";

	public String deadline;

	/**
	 * Creates a Deadline using the String passed
	 * 
	 * @param deadline
	 * @throws IllegalValueException
	 */
	public Deadline(String deadline) throws IllegalValueException {
		assert deadline != null;

		if (deadline.equals(NO_DEADLINE)) {
			this.deadline = NO_DEADLINE;
		} else if (isValidDeadline(deadline)) {
			this.deadline = deadline;
		} else {
			throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
		}

	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof Deadline // instanceof handles nulls
						// TODO: change it so that it deals with Optional
						&& this.deadline.equals(((Deadline) other).deadline));
	}

	/**
	 * Returns Deadline in the form of Optional<Date>
	 * 
	 * @return Deadline in the form of Optional<Date>, empty Optional<Date> if
	 *         no deadline
	 */
	public Optional<Date> getDate() {

		Optional<Date> deadlineAsDate = Optional.empty();

		if (this.hasDeadline()) {
			PrettyTimeParser dateParser = new PrettyTimeParser();
			List<Date> deadline = dateParser.parse(this.deadline);
			deadlineAsDate = Optional.of(deadline.get(0));
		}

		return deadlineAsDate;
	}

	@Override
	public String toString() {
		return this.deadline;
	}

	/**
	 * Checks whether a deadline is valid
	 * 
	 * @param deadlineToCheck
	 * @return true if deadline exists and is on or after time of checking,
	 *         false if no deadline, multiple deadlines or deadline is before
	 *         time of checking
	 */
	private boolean isValidDeadline(String deadlineToCheck) {
		assert deadlineToCheck != null;

		Date rightNow = new Date();
		PrettyTimeParser dateParser = new PrettyTimeParser();
		List<Date> parsedDeadline = dateParser.parse(deadlineToCheck);

		if (parsedDeadline.size() != 1) {
			return false;
		}

		Date deadline = parsedDeadline.get(0);

		if (!deadline.before(rightNow)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean hasDeadline() {
		return !deadline.equals(NO_DEADLINE);
	}

}
