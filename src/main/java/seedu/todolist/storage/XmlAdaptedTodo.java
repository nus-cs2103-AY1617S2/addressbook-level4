package seedu.todolist.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.commons.util.StringUtil;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.tag.UniqueTagList;
import seedu.todolist.model.todo.Name;
import seedu.todolist.model.todo.ReadOnlyTodo;
import seedu.todolist.model.todo.Todo;

/**
 * JAXB-friendly version of the Todo.
 */
public class XmlAdaptedTodo {

    @XmlElement(required = true)
    private String name;

    @XmlElement(required = true)
    private String startTime = "";

    @XmlElement(required = true)
    private String endTime = "";

    @XmlElement(required = true)
    private String completeTime = "";

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
        if (source.getStartTime() != null) {
            startTime = source.getStartTime().toString();
        }
        if (source.getEndTime() != null) {
            endTime = source.getEndTime().toString();
        }
        if (source.getCompleteTime() != null) {
            completeTime = source.getCompleteTime().toString();
        }
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }
    //@@author A0163786N
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
        Date startTime = null;
        Date endTime = null;
        Date completeTime = null;
        if (!this.startTime.isEmpty()) {
            try {
                startTime = StringUtil.parseDate(this.startTime, "EEE MMM dd HH:mm:ss zzz yyyy");
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
        }
        if (!this.endTime.isEmpty()) {
            try {
                endTime = StringUtil.parseDate(this.endTime, "EEE MMM dd HH:mm:ss zzz yyyy");
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
        }
        if (!this.completeTime.isEmpty()) {
            try {
                completeTime = StringUtil.parseDate(this.completeTime, "EEE MMM dd HH:mm:ss zzz yyyy");
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
        }
        return new Todo(name, startTime, endTime, completeTime, tags);
    }
}
