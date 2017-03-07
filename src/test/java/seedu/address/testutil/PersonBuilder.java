package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.todo.Address;
import seedu.address.model.todo.Email;
import seedu.address.model.todo.Name;
import seedu.address.model.todo.Phone;

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

    public TodoBuilder withTags(String ... tags) throws IllegalValueException {
        todo.setTags(new UniqueTagList());
        for (String tag: tags) {
            todo.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TodoBuilder withAddress(String address) throws IllegalValueException {
        this.todo.setAddress(new Address(address));
        return this;
    }

    public TodoBuilder withPhone(String phone) throws IllegalValueException {
        this.todo.setPhone(new Phone(phone));
        return this;
    }

    public TodoBuilder withEmail(String email) throws IllegalValueException {
        this.todo.setEmail(new Email(email));
        return this;
    }

    public TestTodo build() {
        return this.todo;
    }

}
