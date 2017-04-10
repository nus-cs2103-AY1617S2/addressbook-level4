package seedu.geekeep.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.geekeep.commons.core.UnmodifiableObservableList;
import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.model.ReadOnlyGeeKeep;
import seedu.geekeep.model.tag.Tag;
import seedu.geekeep.model.task.ReadOnlyTask;
import seedu.geekeep.model.task.Task;

/**
 * An Immutable GeeKeep that is serializable to XML format
 */
@XmlRootElement(name = "geekeep")
public class XmlSerializableGeeKeep implements ReadOnlyGeeKeep {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableGeeKeep.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableGeeKeep() {
        tasks = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableGeeKeep(ReadOnlyGeeKeep src) {
        this();

        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));

        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {

        final ObservableList<Task> tasks = this.tasks.stream().map(p -> {

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
