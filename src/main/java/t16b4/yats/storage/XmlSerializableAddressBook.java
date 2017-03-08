package t16b4.yats.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import t16b4.yats.commons.core.UnmodifiableObservableList;
import t16b4.yats.commons.exceptions.IllegalValueException;
import t16b4.yats.model.ReadOnlyTaskManager;
import t16b4.yats.model.item.Task;
import t16b4.yats.model.item.Event;
import t16b4.yats.model.item.ReadOnlyEvent;
import t16b4.yats.model.item.ReadOnlyItem;
import t16b4.yats.model.tag.Tag;

/**
 * An Immutable TaskManager that is serializable to XML format
 */
@XmlRootElement(name = "YATS")
public class XmlSerializableAddressBook implements ReadOnlyTaskManager {

    @XmlElement
    private List<XmlAdaptedPerson> tasks;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        tasks = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyTaskManager src) {
        this();
        tasks.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<ReadOnlyEvent> getPersonList() {
        final ObservableList<Event> persons = this.tasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return new UnmodifiableObservableList<>(persons);
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
