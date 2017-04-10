package seedu.taskit.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.taskit.commons.core.UnmodifiableObservableList;
import seedu.taskit.commons.exceptions.IllegalValueException;
import seedu.taskit.model.ReadOnlyTaskManager;
import seedu.taskit.model.tag.Tag;
import seedu.taskit.model.task.ReadOnlyTask;
import seedu.taskit.model.task.Task;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook implements ReadOnlyTaskManager {

    @XmlElement
    private List<XmlAdaptedTag> tags;
    @XmlElement
    private List<XmlAdaptedTask> tasks;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        tags = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyTaskManager src) {
        this();
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        final ObservableList<Task> tasks = this.tasks.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return new UnmodifiableObservableList<>(tasks);
    }

    @Override
    public ObservableList<Tag> getTagList() {
        final ObservableList<Tag> tags = this.tags.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return new UnmodifiableObservableList<>(tags);
    }

}
