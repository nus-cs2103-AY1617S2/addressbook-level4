package seedu.task.model.person;

import java.util.Objects;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Person implements ReadOnlyPerson {

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Name taskName;
    private Date date;
    private Time startTime;
    private Time endTime;
    private String description;
    
    public static final String MESSAGE_INVALID_TIME = "Start time can't be after end time";

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    public Person(Name taskName, Date date, Time startTime, Time endTime, String description) {
    	this.taskName = taskName;
    	this.date = date;
    	this.startTime = startTime;
    	this.endTime = endTime;
    	this.description = description;
    	try {
	    	this.name = new Name("PLACEHOLDER NAME, SHOULD NOT SEE");
	    	this.phone = new Phone("123");
	    	this.email = new Email("asdfads@gmail.com");
	    	this.address = new Address("22 Acacia Avenue");
	    	tags = new UniqueTagList();
    	} catch (IllegalValueException e) {
	    	System.out.println("error");
	    }
    	
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getTaskName(), source.getDate(), source.getStartTime(), source.getEndTime(), source.getDescription());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setPhone(Phone phone) {
        assert phone != null;
        this.phone = phone;
    }

    @Override
    public Phone getPhone() {
        return phone;
    }

    public void setEmail(Email email) {
        assert email != null;
        this.email = email;
    }

    @Override
    public Email getEmail() {
        return email;
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
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        //tags.setTags(replacement);
    }

    /**
     * Updates this person with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyPerson replacement) {
        assert replacement != null;
        
        try {
	        this.setTaskName(replacement.getTaskName());
	        this.setDate(replacement.getDate());
	        this.setStartTime(replacement.getStartTime());
	        this.setEndTime(replacement.getEndTime());
	        this.setDescription(replacement.getDescription());
        } catch (IllegalValueException ive) {
        	System.out.println("error");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    public Name getTaskName() {
    	return taskName;
    }
    public Date getDate() {
    	return date;
    }
    public Time getStartTime() {
    	return startTime;
    }
    public Time getEndTime() {
    	return endTime;
    }
    public String getDescription() {
    	return description;
    }
    public void setTaskName(Name taskName) {
    	this.taskName = taskName;
    }
    public void setDate(Date date) {
    	this.date = date;
    }
    public void setStartTime(Time startTime) throws IllegalValueException {
    	if (this.endTime == null || this.endTime.compareTo(startTime) >= 0) {
    		this.startTime = startTime;
    	} else {
    		throw new IllegalValueException(MESSAGE_INVALID_TIME);
    	}
    }
    public void setEndTime(Time endTime) throws IllegalValueException {
    	if (this.startTime == null || this.startTime.compareTo(endTime) <= 0) {
    		this.endTime = endTime;
    	} else {
    		throw new IllegalValueException(MESSAGE_INVALID_TIME);
    	}
    }
    public void setDescription(String description) {
    	this.description = description;
    }

}
