package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
<<<<<<< HEAD:src/main/java/seedu/address/storage/XmlSerializableTaskManager.java
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.person.Task;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.tag.Tag;
=======
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285:src/main/java/seedu/address/storage/XmlSerializableAddressBook.java

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
<<<<<<< HEAD:src/main/java/seedu/address/storage/XmlSerializableTaskManager.java
public class XmlSerializableTaskManager implements ReadOnlyTaskManager {
=======
public class XmlSerializableAddressBook implements ReadOnlyTaskManager {
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285:src/main/java/seedu/address/storage/XmlSerializableAddressBook.java

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableTaskManager() {
        tasks = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
<<<<<<< HEAD:src/main/java/seedu/address/storage/XmlSerializableTaskManager.java
    public XmlSerializableTaskManager(ReadOnlyTaskManager src) {
=======
    public XmlSerializableAddressBook(ReadOnlyTaskManager src) {
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285:src/main/java/seedu/address/storage/XmlSerializableAddressBook.java
        this();
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    @Override
<<<<<<< HEAD:src/main/java/seedu/address/storage/XmlSerializableTaskManager.java
    public ObservableList<ReadOnlyTask> getTaskList() {
        final ObservableList<Task> tasks = this.tasks.stream().map(p -> {
=======
    public ObservableList<ReadOnlyTask> getPersonList() {
        final ObservableList<Task> persons = this.persons.stream().map(p -> {
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285:src/main/java/seedu/address/storage/XmlSerializableAddressBook.java
            try {
                return p.toModelType();
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
