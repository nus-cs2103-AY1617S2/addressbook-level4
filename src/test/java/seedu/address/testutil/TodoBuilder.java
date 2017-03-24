package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddCommand;
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
            this.todo.setStartTime(StringUtil.parseDate(strDateTime,  AddCommand.DATE_FORMAT));
            return this;
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        return null;
    }
    public TodoBuilder withEndTime(String strDateTime) throws IllegalValueException {
        try {
            this.todo.setEndTime(StringUtil.parseDate(strDateTime, AddCommand.DATE_FORMAT));
            return this;
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        return null;
    }
    //@@author A0163786N
    public TodoBuilder withCompleteTime(String strDateTime) throws IllegalValueException {
        try {
            this.todo.setCompleteTime(StringUtil.parseDate(strDateTime, AddCommand.DATE_FORMAT));
            return this;
        } catch (IllegalValueException e) {
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
