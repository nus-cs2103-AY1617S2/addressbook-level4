package seedu.todolist.model.task.parser;

import static seedu.todolist.logic.parser.CliSyntax.PREFIX_END;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_START;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.logic.parser.ArgumentTokenizer;
import seedu.todolist.logic.parser.ParserUtil;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.tag.UniqueTagList;
import seedu.todolist.model.task.EndTask;
import seedu.todolist.model.task.EndTime;
import seedu.todolist.model.task.FloatingTask;
import seedu.todolist.model.task.Name;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.StartEndTask;
import seedu.todolist.model.task.StartTask;
import seedu.todolist.model.task.StartTime;

/*
 * Parse input into a suitable type of Task
 */
public class TaskParser {
	
	public static final String MESSAGE_INVALID_TASK = "Name must be present";
	
	/**
     * Parses the given {@code String} of input that contains parameters
     * of a Task object and calls the suitable TaskParser based on the parameters.
     */
	public static Task parseTask(String taskInput) throws IllegalValueException {
		ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_START, PREFIX_END, PREFIX_TAG);
        argsTokenizer.tokenize(taskInput);
        String name = argsTokenizer.getPreamble().get();
        String startTime = (argsTokenizer.getValue(PREFIX_START).isPresent() ? 
        		argsTokenizer.getValue(PREFIX_START).get() 
        		: null);
        String endTime = (argsTokenizer.getValue(PREFIX_END).isPresent() ? 
        		argsTokenizer.getValue(PREFIX_END).get() 
        		: null);
        Set<String> tags = ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG));
        
        if (name == null) {
        	throw new IllegalValueException(MESSAGE_INVALID_TASK);
        }

        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        
        if (startTime != null && endTime != null) {
        	return new StartEndTask(new Name(name), 
        			new StartTime(startTime), 
        			new EndTime(endTime), 
        			new UniqueTagList(tagSet));
        } else if (startTime != null && endTime == null) {
        	return new StartTask(new Name(name), 
        			new StartTime(startTime),
        			new UniqueTagList(tagSet));
        } else if (startTime == null && endTime != null) {
        	return new EndTask(new Name(name), 
        			new EndTime(endTime), 
        			new UniqueTagList(tagSet));
        } else {
        	return new FloatingTask(new Name(name), 
        			new UniqueTagList(tagSet));
        }
	}
	
	/*
	 * An overloaded method that takes in a Name object,
	 * a StartTime object, an EndTime object and a set of Tags
	 * and create a suitable Task object.
	 * @@author A0141647E 
	 */
	public static Task parseTask(Name name, StartTime startTime,
			EndTime endTime, UniqueTagList uniqueTagList) {
		assert name != null;
		
		if (startTime != null && endTime != null) {
        	return new StartEndTask(name, startTime, endTime, uniqueTagList);
        } else if (startTime != null && endTime == null) {
        	return new StartTask(name, startTime, uniqueTagList);
        } else if (startTime == null && endTime != null) {
        	return new EndTask(name, endTime, uniqueTagList);
        } else {
        	return new FloatingTask(name, uniqueTagList);
        }
	}
	
	/*
	 * Another overloaded method that takes in a Task object
	 * and create a copy of it.
	 * @@author A0141647E
	 */
	public static Task parseTask(Task taskToCopy) {
		assert taskToCopy != null;
		
		Name name = taskToCopy.getName();
		StartTime startTime = taskToCopy.getStartTime();
		EndTime endTime = taskToCopy.getEndTime();
		UniqueTagList uniqueTagList = taskToCopy.getTags();
		
		if (startTime != null && endTime != null) {
        	return new StartEndTask(name, startTime, endTime, uniqueTagList);
        } else if (startTime != null && endTime == null) {
        	return new StartTask(name, startTime, uniqueTagList);
        } else if (startTime == null && endTime != null) {
        	return new EndTask(name, endTime, uniqueTagList);
        } else {
        	return new FloatingTask(name, uniqueTagList);
        }
	}
}
