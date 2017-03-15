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

	public final String deadline;

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
	
	/**
	 * Creates a Deadline using the String passed. isNew means that this Deadline object is added from user input
	 * as opposed to being loaded from storage, and therefore the checking of whether the deadline date
	 * is in the past should NOT be bypassed. 
	 * 
	 * @param deadline
	 * @throws IllegalValueException
	 */
	public Deadline(String deadline, boolean isNew) throws IllegalValueException {
		assert deadline != null;

		if (deadline.equals(NO_DEADLINE)) {
			this.deadline = NO_DEADLINE;
		} else if (isValidDeadline(deadline, isNew)) {
			this.deadline = deadline;
		} else {
			throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
		}

	}

	@Override
	public String toString() {
		if (hasDeadline()) {
			return getDate().get().toString();
		} else {
			return Deadline.NO_DEADLINE;			
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

	/**
	 * Checks whether a deadline is valid
	 * 
	 * @param deadlineToCheck
	 * @return true if deadline exists and is on or after time of checking,
	 *         false if no deadline, multiple deadlines or deadline is before
	 *         time of checking
	 */
	public static boolean isValidDeadline(String deadlineToCheck) {
		assert deadlineToCheck != null;

		Date rightNow = new Date();
		PrettyTimeParser dateParser = new PrettyTimeParser();
		List<Date> parsedDeadline = dateParser.parse(deadlineToCheck);

//		//TODO replace parsing with set deadlines to avoid ambiguity/multiple deadlines
//		if (parsedDeadline.size() != 1) {
//			return false;
//		}

		Date deadline = parsedDeadline.get(0);

		if (!deadline.before(rightNow)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks whether a deadline is valid
	 * 
	 * @param deadlineToCheck
	 * @return true if deadline exists and is on or after time of checking,
	 *         false if no deadline, multiple deadlines or deadline is before
	 *         time of checking
	 */
	private boolean isValidDeadline(String deadlineToCheck, boolean isNew) {
		assert deadlineToCheck != null;

		Date rightNow = new Date();
		PrettyTimeParser dateParser = new PrettyTimeParser();
		List<Date> parsedDeadline = dateParser.parse(deadlineToCheck);

//		//TODO replace parsing with set deadlines to avoid ambiguity/multiple deadlines
//		if (parsedDeadline.size() != 1) {
//			return false;
//		}

		Date deadline = parsedDeadline.get(0);
		
		if (!isNew) {
			return true;
		}

		if (!deadline.before(rightNow)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasDeadline() {
		return !deadline.equals(NO_DEADLINE);
	}

}
