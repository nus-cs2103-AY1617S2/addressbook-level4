package seedu.taskboss.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.taskboss.commons.core.UnmodifiableObservableList;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.ReadOnlyTaskBoss;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.task.ReadOnlyTask;
import seedu.taskboss.model.task.Task;

/**
 * An Immutable TaskBoss that is serializable to XML format
 */
@XmlRootElement(name = "taskboss")
public class XmlSerializableTaskBoss implements ReadOnlyTaskBoss {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<XmlAdaptedCategory> categories;

    /**
     * Creates an empty XmlSerializableTaskBoss.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableTaskBoss() {
        tasks = new ArrayList<>();
        categories = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableTaskBoss(ReadOnlyTaskBoss src) {
        this();
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        categories.addAll(src.getCategoryList().stream().map(XmlAdaptedCategory::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        final ObservableList<Task> persons = this.tasks.stream().map(p -> {
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
    public ObservableList<Category> getCategoryList() {
        final ObservableList<Category> categories = this.categories.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return new UnmodifiableObservableList<>(categories);
    }

}
