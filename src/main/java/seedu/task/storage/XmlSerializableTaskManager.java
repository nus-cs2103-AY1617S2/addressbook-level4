package seedu.task.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;

/**
 * An Immutable Task Manager that is serializable to XML format
 */
@XmlRootElement(name = "taskmanager")
public class XmlSerializableTaskManager implements ReadOnlyTaskManager {

	@XmlElement
	private List<XmlAdaptedTask> tasks;

	/**
	 * Creates an empty XmlSerializableTaskManager. This empty constructor is
	 * required for marshalling.
	 */
	public XmlSerializableTaskManager() {
		tasks = new ArrayList<>();
	}

	/**
	 * Conversion
	 */
	public XmlSerializableTaskManager(ReadOnlyTaskManager src) {
		this();
		tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
	}

	@Override
	public ObservableList<ReadOnlyTask> getTaskList() {
		final ObservableList<Task> persons = this.tasks.stream().map(p -> {
			try {
				return p.toModelType();
			} catch (IllegalValueException e) {
				e.printStackTrace();
				// TODO: better error handling
				return null;
			}
		}).collect(Collectors.toCollection(FXCollections::observableArrayList));
		return new UnmodifiableObservableList<>(persons);
	}

}
