package seedu.jobs.model.task;

public class Description {
	
	public static final String DEFAULT_VALUE = "";
	public final String value;
	
	public Description(String description){
		this.value = (description==null) ? DEFAULT_VALUE : description;
	}
	
	@Override
	public String toString(){
		return this.value;
	}
	
	@Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && this.value.equals(((Description) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
