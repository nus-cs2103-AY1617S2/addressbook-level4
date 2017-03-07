package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.todo.Name;
import seedu.address.model.todo.ReadOnlyTodo;
import seedu.address.model.todo.Todo;

/**
 * JAXB-friendly version of the Todo.
 */
public class XmlAdaptedTodo {

    @XmlElement(required = true)
    private String name;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedTodo.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTodo() {}


    /**
     * Converts a given Todo into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTodo
     */
    public XmlAdaptedTodo(ReadOnlyTodo source) {
        name = source.getName().fullName;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted todo object into the model's Todo object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted todo
     */
    public Todo toModelType() throws IllegalValueException {
        final List<Tag> todoTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            todoTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final UniqueTagList tags = new UniqueTagList(todoTags);
        return new Todo(name, tags);
    }
}
