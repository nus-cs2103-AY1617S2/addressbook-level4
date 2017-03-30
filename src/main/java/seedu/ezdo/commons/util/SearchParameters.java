package seedu.ezdo.commons.util;

import java.util.Optional;
import java.util.Set;

import seedu.ezdo.model.todo.DueDate;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.StartDate;

public class SearchParameters {
	private Set<String> namesToCompare;
	private Optional<Priority> priorityToCompare;
	private Optional<StartDate> startDateToCompare;
	private Optional<DueDate> dueDateToCompare;
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

	public Optional<StartDate> getStartDate() {
		return startDateToCompare;
	}

	public Optional<DueDate> getDueDate() {
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
		private Optional<StartDate> startDateToCompare;
		private Optional<DueDate> dueDateToCompare;
		private Set<String> tagsToCompare;
		private boolean startBefore = false;
		private boolean dueBefore = false;
		private boolean startAfter = false;
		private boolean dueAfter = false;

		public Builder(Set<String> names, Optional<Priority> priority, Optional<StartDate> startDate,
	    		 Optional<DueDate> dueDate, Set<String> tags) {  
	       this.namesToCompare = names;  
	       this.priorityToCompare = priority;
	       this.startDateToCompare = startDate;
	       this.dueDateToCompare = dueDate;
	       this.tagsToCompare = tags;
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
