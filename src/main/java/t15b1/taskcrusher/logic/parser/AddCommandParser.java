package t15b1.taskcrusher.logic.parser;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import static t15b1.taskcrusher.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static t15b1.taskcrusher.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static t15b1.taskcrusher.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static t15b1.taskcrusher.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static t15b1.taskcrusher.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import t15b1.taskcrusher.commons.exceptions.IllegalValueException;
import t15b1.taskcrusher.logic.commands.AddCommand;
import t15b1.taskcrusher.logic.commands.Command;
import t15b1.taskcrusher.logic.commands.HelpCommand;
import t15b1.taskcrusher.logic.commands.IncorrectCommand;

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

        //separate flag from name
        Matcher matcher = ADD_COMMAND_FORMAT.matcher(argsTokenizer.getPreamble().get());
        if (!matcher.matches()) {
        	return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        
        final String flag = matcher.group("flag");
        final String name = matcher.group("name");
        
        //parse time
        PrettyTimeParser timeParser = new PrettyTimeParser();
        Optional<String> deadline = argsTokenizer.getValue(PREFIX_DEADLINE);
        List<Date> parsedDeadline = null;
        if (deadline.isPresent()) {
        	parsedDeadline = timeParser.parse(deadline.get());        	
        }
        
        //empty string as default if no description
        String description = "";
        if (argsTokenizer.getValue(PREFIX_DESCRIPTION).isPresent()) {
        	description = argsTokenizer.getValue(PREFIX_DESCRIPTION).get();
        }
        
        switch (flag) {
        	case AddCommand.EVENT_FLAG:
        		//TODO when events are supported
        		return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        	case AddCommand.TASK_FLAG:
        		return addTask(argsTokenizer, name, parsedDeadline, description);
        	default:
        		//TODO error
        		return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }        
        
    }
    
    private Command addTask(ArgumentTokenizer argsTokenizer, String name, List<Date> parsedDeadline, String description) {
    	try {
    		return new AddCommand(
    				name,
    				parsedDeadline,
    				argsTokenizer.getValue(PREFIX_PRIORITY).get(),
    				description,
    				ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)));
    	} catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    private void addEvent() {
    	
    }

}
