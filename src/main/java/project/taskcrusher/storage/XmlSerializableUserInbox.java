package project.taskcrusher.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.taskcrusher.commons.core.UnmodifiableObservableList;
import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.model.ReadOnlyUserInbox;
import project.taskcrusher.model.event.Event;
import project.taskcrusher.model.event.ReadOnlyEvent;
import project.taskcrusher.model.tag.Tag;
import project.taskcrusher.model.task.ReadOnlyTask;
import project.taskcrusher.model.task.Task;

/**
 * An Immutable User Inbox that is serializable to XML format
 */
@XmlRootElement(name = "userinbox")
public class XmlSerializableUserInbox implements ReadOnlyUserInbox {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<XmlAdaptedEvent> events;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableUserInbox.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableUserInbox() {
        tasks = new ArrayList<>();
        events = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableUserInbox(ReadOnlyUserInbox src) {
        this();
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        events.addAll(src.getEventList().stream().map(XmlAdaptedEvent::new).collect(Collectors.toList()));
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
    public ObservableList<ReadOnlyEvent> getEventList() {
        final ObservableList<Event> events = this.events.stream().map(event -> {
            try {
                return event.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return new UnmodifiableObservableList<>(events);
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
