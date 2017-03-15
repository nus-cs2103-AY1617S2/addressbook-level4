package seedu.task.testutil;

import seedu.task.model.person.Address;
import seedu.task.model.person.Date;
import seedu.task.model.person.Email;
import seedu.task.model.person.Name;
import seedu.task.model.person.Phone;
import seedu.task.model.person.ReadOnlyPerson;
import seedu.task.model.person.Time;
import seedu.task.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyPerson {

    private Name name;
    private Address address;
    private Email email;
    private Phone phone;
    private UniqueTagList tags;
    private Name taskName;
    private Date date;
    private Time startTime;
    private Time endTime;
    private String description;
    
    public TestPerson() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestPerson(TestPerson personToCopy) {
        this.name = personToCopy.getName();
        this.phone = personToCopy.getPhone();
        this.email = personToCopy.getEmail();
        this.address = personToCopy.getAddress();
        this.tags = personToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Phone getPhone() {
        return phone;
    }

    @Override
    public Email getEmail() {
        return email;
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
    
    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        sb.append("a/" + this.getAddress().value + " ");
        sb.append("p/" + this.getPhone().value + " ");
        sb.append("e/" + this.getEmail().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

	@Override
	public Name getTaskName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getStartTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getEndTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
}
