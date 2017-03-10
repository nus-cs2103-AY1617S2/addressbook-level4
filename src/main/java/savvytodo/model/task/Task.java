package savvytodo.model.task;

import java.util.Objects;

import savvytodo.commons.util.CollectionUtil;
import savvytodo.model.category.UniqueCategoryList;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Priority priority;
    private Description description;
    private Address address;

    private UniqueCategoryList categories;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Priority phone, Description email, Address address, UniqueCategoryList categories) {
        assert !CollectionUtil.isAnyNull(name, phone, email, address, categories);
        this.name = name;
        this.priority = phone;
        this.description = email;
        this.address = address;
        this.categories = new UniqueCategoryList(categories); //protect internal categories from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getPriority(), source.getDescription(), source.getAddress(),
                source.getCategories());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setPhone(Priority phone) {
        assert phone != null;
        this.priority = phone;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    public void setEmail(Description email) {
        assert email != null;
        this.description = email;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    public void setAddress(Address address) {
        assert address != null;
        this.address = address;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public UniqueCategoryList getCategories() {
        return new UniqueCategoryList(categories);
    }

    /**
     * Replaces this task's categories with the categories in the argument category list.
     */
    public void setCategories(UniqueCategoryList replacement) {
        categories.setCategories(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setPhone(replacement.getPriority());
        this.setEmail(replacement.getDescription());
        this.setAddress(replacement.getAddress());
        this.setCategories(replacement.getCategories());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, priority, description, address, categories);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
