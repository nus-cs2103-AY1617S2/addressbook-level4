package w10b3.todolist.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import w10b3.todolist.commons.core.LogsCenter;
import w10b3.todolist.commons.core.UnmodifiableObservableList;
import w10b3.todolist.commons.exceptions.IllegalValueException;
import w10b3.todolist.model.ReadOnlyToDoList;
import w10b3.todolist.model.tag.Tag;
import w10b3.todolist.model.task.ReadOnlyTask;
import w10b3.todolist.model.task.Task;

/**
 * An Immutable ToDoList that is serializable to XML format
 */
@XmlRootElement(name = "ToDoList")
public class XmlSerializableToDoList implements ReadOnlyToDoList {

    private static final Logger logger = LogsCenter.getLogger(XmlSerializableToDoList.class);

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableToDoList.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableToDoList() {
        tasks = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableToDoList(ReadOnlyToDoList src) {
        this();
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }
    //@@author A0122017Y
    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        final ObservableList<Task> tasks = this.tasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                logger.info("Task format invalid.");
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return new UnmodifiableObservableList<>(tasks);
    }
    //@@

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
