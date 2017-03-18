package seedu.address.testutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.todo.Name;
/**
 *
 */
public class TodoBuilder {

    private TestTodo todo;

    public TodoBuilder() {
        this.todo = new TestTodo();
    }

    /**
     * Initializes the TodoBuilder with the data of {@code todoToCopy}.
     */
    public TodoBuilder(TestTodo todoToCopy) {
        this.todo = new TestTodo(todoToCopy);
    }

    public TodoBuilder withName(String name) throws IllegalValueException {
        this.todo.setName(new Name(name));
        return this;
    }

    public TodoBuilder withStartTime(String strDateTime) throws IllegalValueException {
        try {
            this.todo.setStartTime(new SimpleDateFormat("yy-MM-dd'T'HH:mm").parse(strDateTime));
            return this;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TodoBuilder withEndTime(String strDateTime) throws IllegalValueException {
        try {
            this.todo.setEndTime(new SimpleDateFormat("yy-MM-dd'T'HH:mm").parse(strDateTime));
            return this;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public TodoBuilder withTags(String ... tags) throws IllegalValueException {
        UniqueTagList tempList = new UniqueTagList();
        for (String tag: tags) {
            tempList.add(new Tag(tag));
        }
        todo.setTags(tempList);
        return this;
    }

    public TestTodo build() {
        return this.todo;
    }

}
