package seedu.todolist.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.todolist.commons.core.UnmodifiableObservableList;
import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.ReadOnlyTodoList;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.todo.ReadOnlyTodo;
import seedu.todolist.model.todo.Todo;

/**
 * An Immutable TodoList that is serializable to XML format
 */
@XmlRootElement(name = "todolist")
public class XmlSerializableTodoList implements ReadOnlyTodoList {

    @XmlElement
    private List<XmlAdaptedTodo> todos;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableTodoList.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableTodoList() {
        todos = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableTodoList(ReadOnlyTodoList src) {
        this();
        todos.addAll(src.getTodoList().stream().map(XmlAdaptedTodo::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<ReadOnlyTodo> getTodoList() {
        final ObservableList<Todo> todos = this.todos.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return new UnmodifiableObservableList<>(todos);
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
