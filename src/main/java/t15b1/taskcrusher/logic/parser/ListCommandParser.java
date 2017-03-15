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
import t15b1.taskcrusher.logic.commands.ListCommand;
import t15b1.taskcrusher.model.shared.Description;
import t15b1.taskcrusher.model.task.Deadline;
import t15b1.taskcrusher.model.task.Priority;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class ListCommandParser {
	
    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DEADLINE);
        argsTokenizer.tokenize(args);
        
        String flag = ListCommand.NO_FLAG;
        Optional<String> rawFlag = argsTokenizer.getPreamble();
        if (rawFlag.isPresent()) {
        	flag = rawFlag.get();
        }
        
        //TODO Modify below for events; will need PrettyTimeParser
        String deadline = Deadline.NO_DEADLINE;
        Optional<String> rawDeadline = argsTokenizer.getValue(PREFIX_DEADLINE);
        if (rawDeadline.isPresent()) {
        	deadline = rawDeadline.get();        	
        }
        
        switch (flag) {
        	case ListCommand.NO_FLAG:
        		return listTask(deadline, false, false);
        	case ListCommand.TASK_FLAG:
        		return listTask(deadline, true, false);
        	case ListCommand.EVENT_FLAG:
        		//TODO when events supported
        		return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        	case ListCommand.OVERDUE_FLAG:
        		//TODO return listTask(deadline, true, true);
        	case ListCommand.COMPLETE_FLAG:
        		//TODO when complete supported
        		return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        	default:
        		//TODO fix messages
        		return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }        
        
    }
    
    private Command listTask(String deadline, boolean listTasksOnly, boolean listEventsOnly) {
    	try {
    		return new ListCommand(deadline, listTasksOnly, listEventsOnly);
    	} catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    private void listEvent() {
    	//TODO when events are supported
    }

}
