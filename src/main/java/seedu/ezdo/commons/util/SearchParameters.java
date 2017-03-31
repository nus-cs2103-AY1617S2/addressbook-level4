package seedu.ezdo.commons.util;

import java.util.Optional;
import java.util.Set;

import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.TaskDate;

public class SearchParameters {
	private Set<String> namesToCompare;
	private Optional<Priority> priorityToCompare;
	private Optional<TaskDate> startDateToCompare;
	private Optional<TaskDate> dueDateToCompare;
	private Set<String> tagsToCompare;
	private boolean startBefore = false;
	private boolean dueBefore = false;
	private boolean startAfter = false;
	private boolean dueAfter = false;
	
	public Set<String> getNames() {
		return namesToCompare;
	}

	public Optional<Priority> getPriority() {
		return priorityToCompare;
	}

	public Optional<TaskDate> getStartDate() {
		return startDateToCompare;
	}

	public Optional<TaskDate> getDueDate() {
		return dueDateToCompare;
	}

	public Set<String> getTags() {
		return tagsToCompare;
	}

	public boolean getStartBefore() {
		return startBefore;
	}

	public boolean getdueBefore() {
		return dueBefore;
	}

	public boolean getStartAfter() {
		return startAfter;
	}

	public boolean getDueAfter() {
		return dueAfter;
	}

	public static class Builder {
		// required parameters
		private Set<String> namesToCompare;
		private Optional<Priority> priorityToCompare;
		private Optional<TaskDate> startDateToCompare;
		private Optional<TaskDate> dueDateToCompare;
		private Set<String> tagsToCompare;
		private boolean startBefore = false;
		private boolean dueBefore = false;
		private boolean startAfter = false;
		private boolean dueAfter = false;

		public Builder() {  

		}

		public Builder name(Set<String> names) {
			namesToCompare = names;
			return this;
		}

		public Builder priority(Optional<Priority> priority) {
			priorityToCompare = priority;
			return this;
		}

		public Builder startDate(Optional<TaskDate> findStartDate) {
			startDateToCompare = findStartDate;
			return this;
		}

		public Builder dueDate(Optional<TaskDate> dueDate) {
			dueDateToCompare = dueDate;
			return this;
		}

		public Builder tags(Set<String> tags) {
			tagsToCompare = tags;
			return this;
		}

		public Builder startBefore(boolean before) {
			startBefore = before;
			return this;
		}

		public Builder dueBefore(boolean before) {
			dueBefore = before;
			return this;
		}

		public Builder startAfter(boolean after) {
			startAfter = after;
			return this;
		}

		public Builder dueAfter(boolean after) {
			dueAfter = after;
			return this;
		}

		public SearchParameters build() {
			return new SearchParameters(this);
		}
	}

	private SearchParameters(Builder builder) {
		namesToCompare = builder.namesToCompare;
		priorityToCompare = builder.priorityToCompare;
		startDateToCompare = builder.startDateToCompare;
		dueDateToCompare = builder.dueDateToCompare;
		tagsToCompare = builder.tagsToCompare;
		startBefore = builder.startBefore;
		dueBefore = builder.dueBefore;
		startAfter = builder.startAfter;
		dueAfter = builder.dueAfter;
	}
}
