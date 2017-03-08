package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.Date;

/**
 *
 */
public class TaskBuilder {

  private TestTask task;

  public TaskBuilder() {
    this.task = new TestTask();
  }

  /**
   * Initializes the TaskBuilder with the data of {@code taskToCopy}.
   */
  public TaskBuilder(TestTask taskToCopy) {
    this.task = new TestTask(taskToCopy);
  }

  public TaskBuilder withName(String name) throws IllegalValueException {
    this.task.setName(new Name(name));
    return this;
  }

  public TaskBuilder withTags(String... tags) throws IllegalValueException {
    task.setTags(new UniqueTagList());
    for (String tag : tags) {
      task.getTags().add(new Tag(tag));
    }
    return this;
  }

  public TaskBuilder withDate(String date) throws IllegalValueException {
    this.task.setDate(new Date(date));
    return this;
  }

  public TestTask build() {
    return this.task;
  }

}
