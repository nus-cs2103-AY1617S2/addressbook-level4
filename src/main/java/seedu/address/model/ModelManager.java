package seedu.address.model;

import java.util.Date;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.ReadOnlyTask.TaskType;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the task manager data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
	private static final Logger logger = LogsCenter
			.getLogger(ModelManager.class);

	private final TaskManager taskManager;
	private final FilteredList<ReadOnlyTask> filteredTasks;

	/**
	 * Initializes a ModelManager with the given taskManager and userPrefs.
	 */
	public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
		super();
		assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

		logger.fine("Initializing with task manager: " + taskManager
				+ " and user prefs " + userPrefs);

		this.taskManager = new TaskManager(taskManager);
		filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
	}

	public ModelManager() {
		this(new TaskManager(), new UserPrefs());
	}

	@Override
	public void resetData(ReadOnlyTaskManager newData) {
		taskManager.resetData(newData);
		indicateTaskManagerChanged();
	}

	@Override
	public ReadOnlyTaskManager getTaskManager() {
		return taskManager;
	}

	/** Raises an event to indicate the model has changed */
	private void indicateTaskManagerChanged() {
		raise(new TaskManagerChangedEvent(taskManager));
	}

	@Override
	public synchronized void deleteTask(ReadOnlyTask target)
			throws TaskNotFoundException {
		taskManager.removeTask(target);
		indicateTaskManagerChanged();
	}

	@Override
	public synchronized void addTask(Task task)
			throws UniqueTaskList.DuplicateTaskException {
		taskManager.addTask(task);
		updateFilteredListToShowAll();
		indicateTaskManagerChanged();
	}

	@Override
	public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
			throws UniqueTaskList.DuplicateTaskException {
		assert editedTask != null;

		int taskManagerIndex = filteredTasks
				.getSourceIndex(filteredTaskListIndex);
		taskManager.updateTask(taskManagerIndex, editedTask);
		indicateTaskManagerChanged();
	}

	@Override
	public void updateSaveLocation() {
		indicateTaskManagerChanged();
	}

	// =========== Filtered Task List Accessors
	// =============================================================

	@Override
	public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
		return new UnmodifiableObservableList<>(filteredTasks);
	}

	@Override
	public void updateFilteredListToShowAll() {
		filteredTasks.setPredicate(null);
	}

	@Override
	public void updateFilteredTaskList(Set<String> keywords, Date date) {
		if (date == null) {
			updateFilteredTaskList(
					new PredicateExpression(new NameQualifier(keywords)));
		} else {
			updateFilteredTaskList(new PredicateExpression(
					new NameAndDateQualifier(keywords, date)));
		}
	}

	private void updateFilteredTaskList(Expression expression) {
		filteredTasks.setPredicate(expression::satisfies);
	}

	// ========== Inner classes/interfaces used for filtering
	// =================================================

	interface Expression {
		boolean satisfies(ReadOnlyTask task);

		@Override
		String toString();
	}

	private class PredicateExpression implements Expression {

		private final Qualifier qualifier;

		PredicateExpression(Qualifier qualifier) {
			this.qualifier = qualifier;
		}

		@Override
		public boolean satisfies(ReadOnlyTask task) {
			return qualifier.run(task);
		}

		@Override
		public String toString() {
			return qualifier.toString();
		}
	}

	interface Qualifier {
		boolean run(ReadOnlyTask task);

		@Override
		String toString();
	}

	private class NameQualifier implements Qualifier {
		private Set<String> nameKeyWords;

		NameQualifier(Set<String> nameKeyWords) {
			this.nameKeyWords = nameKeyWords;
		}

		@Override
		public boolean run(ReadOnlyTask task) {
			return nameKeyWords.stream()
					.filter(keyword -> {
						boolean check = false;
						check = check || StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword);
						for (Tag tag : task.getTags().toSet()) {
							check = check || StringUtil.containsWordIgnoreCase(tag.getTagName(), keyword);
						}
						return check;
					})
					.findAny()
					.isPresent();
		}

		@Override
		public String toString() {
			return "name=" + String.join(", ", nameKeyWords);
		}
	}

	private class NameAndDateQualifier implements Qualifier {
		private Set<String> nameKeyWords;
		private Date date;

		NameAndDateQualifier(Set<String> nameKeyWords, Date date) {
			this.nameKeyWords = nameKeyWords;
			this.date = date;
		}

		@Override
		public boolean run(ReadOnlyTask task) {
			if (task.getTaskType() != null
					&& task.getTaskType() != TaskType.TaskWithNoDeadline) {
				return nameKeyWords.stream()
						.filter(keyword -> (StringUtil.containsWordIgnoreCase(
								task.getName().fullName, keyword)
								|| task.getDeadline().isSameDay(date)))
						.findAny().isPresent();
			} else {
				return nameKeyWords.stream()
						.filter(keyword -> StringUtil.containsWordIgnoreCase(
								task.getName().fullName, keyword))
						.findAny().isPresent();
			}
		}

		@Override
		public String toString() {
			return "name=" + String.join(", ", nameKeyWords);
		}

	}
}
