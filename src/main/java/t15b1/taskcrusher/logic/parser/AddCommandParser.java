package t15b1.taskcrusher.logic.parser;

import static t15b1.taskcrusher.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static t15b1.taskcrusher.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static t15b1.taskcrusher.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static t15b1.taskcrusher.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static t15b1.taskcrusher.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import t15b1.taskcrusher.commons.exceptions.IllegalValueException;
import t15b1.taskcrusher.logic.commands.AddCommand;
import t15b1.taskcrusher.logic.commands.Command;
import t15b1.taskcrusher.logic.commands.HelpCommand;
import t15b1.taskcrusher.logic.commands.IncorrectCommand;
import t15b1.taskcrusher.model.shared.Description;
import t15b1.taskcrusher.model.task.Deadline;
import t15b1.taskcrusher.model.task.Priority;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {
	
	private static final Pattern ADD_COMMAND_FORMAT = Pattern.compile("(?<flag>[te])(?<name>.+)");
	
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DEADLINE, PREFIX_TAG, PREFIX_PRIORITY, PREFIX_DESCRIPTION);
        argsTokenizer.tokenize(args);

        Matcher matcher = ADD_COMMAND_FORMAT.matcher(argsTokenizer.getPreamble().get());
        if (!matcher.matches()) {
        	return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        
        final String flag = matcher.group("flag");
        final String name = matcher.group("name");
        
      //TODO Modify below for events; will need PrettyTimeParser
        String deadline = Deadline.NO_DEADLINE;
        Optional<String> rawDeadline = argsTokenizer.getValue(PREFIX_DEADLINE);
        if (rawDeadline.isPresent()) {
        	deadline = rawDeadline.get();        	
        }        
        
        String priority = Priority.NO_PRIORITY;
        Optional<String> rawPriority = argsTokenizer.getValue(PREFIX_PRIORITY);
        if (rawPriority.isPresent()) {
        	priority = rawPriority.get();
        }
        
        String description = Description.NO_DESCRIPTION;
        Optional<String> rawDescription = argsTokenizer.getValue(PREFIX_DESCRIPTION);
        if (rawDescription.isPresent()) {
        	description = rawDescription.get();
        }
        
        Set<String> tags = ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG));
        
        switch (flag) {
        	case AddCommand.EVENT_FLAG:
        		//TODO when events are supported
        		return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        	case AddCommand.TASK_FLAG:
        		return addTask(name, deadline, priority, description, tags);
        	default:
        		//TODO fix messages
        		return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }        
        
    }
    
    private Command addTask(String name, String deadline, String priority, String description, Set<String> tags) {
    	try {
    		return new AddCommand(
    				name,
    				deadline,
    				priority,
    				description,
    				tags);
    	} catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    private void addEvent() {
    	//TODO when events are supported
    }

}
