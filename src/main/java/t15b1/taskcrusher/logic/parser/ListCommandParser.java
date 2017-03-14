//package t15b1.taskcrusher.logic.parser;
//
//import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
//
//import static t15b1.taskcrusher.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static t15b1.taskcrusher.logic.parser.CliSyntax.PREFIX_DEADLINE;
//import static t15b1.taskcrusher.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
//import static t15b1.taskcrusher.logic.parser.CliSyntax.PREFIX_PRIORITY;
//import static t15b1.taskcrusher.logic.parser.CliSyntax.PREFIX_TAG;
//
//import java.util.Date;
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.Optional;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import t15b1.taskcrusher.commons.exceptions.IllegalValueException;
//import t15b1.taskcrusher.logic.commands.AddCommand;
//import t15b1.taskcrusher.logic.commands.Command;
//import t15b1.taskcrusher.logic.commands.HelpCommand;
//import t15b1.taskcrusher.logic.commands.IncorrectCommand;
//
///**
// * Parses input arguments and creates a new ListCommand object
// */
//public class ListCommandParser {
//	
//	private static final Pattern LIST_COMMAND_FORMAT = Pattern.compile("(?<flags>[teoc])(?<name>.+)");
//	
//    /**
//     * Parses the given {@code String} of arguments in the context of the AddCommand
//     * and returns an AddCommand object for execution.
//     */
//    public Command parse(String args) {
//        ArgumentTokenizer argsTokenizer =
//                new ArgumentTokenizer(PREFIX_DEADLINE);
//        argsTokenizer.tokenize(args);
//
//        //separate flag from name
//        Matcher matcher = LIST_COMMAND_FORMAT.matcher(argsTokenizer.getPreamble().get());
//        if (!matcher.matches()) {
//        	return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
//        }
//        
//        //separate flag from no flag TODO TEST this        
//        final String flag = matcher.group("flag");
//        final String name = matcher.group("name");
//        
//        //TODO multiple dates... well, up to two specifically?
//        //TODO call the correct list command? well, this really depends on DateQualifier.
//        
//        //cases: two flags, one flag, no flag... can you have switch sth && another thing?
//        
//        //parse time
//        PrettyTimeParser timeParser = new PrettyTimeParser();
//        Optional<String> deadline = argsTokenizer.getValue(PREFIX_DEADLINE);
//        List<Date> parsedDeadline = null;
//        if (deadline.isPresent()) {
//        	parsedDeadline = timeParser.parse(deadline.get());        	
//        }
//        
//        switch (flag) {
//        	case AddCommand.EVENT_FLAG:
//        		//TODO when events are supported
//        		return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//        	case AddCommand.TASK_FLAG:
//        		return addTask(argsTokenizer, name, parsedDeadline, priority, description);
//        	default:
//        		//TODO error
//        		return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//        }        
//        
//    }
//    
//    //TODO fix below    
//    private Command addTask(ArgumentTokenizer argsTokenizer, String name, List<Date> parsedDeadline, String priority, String description) {
//    	try {
//    		return new AddCommand(
//    				name,
//    				parsedDeadline,
//    				priority,
//    				description,
//    				ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)));
//    	} catch (NoSuchElementException nsee) {
//            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//        } catch (IllegalValueException ive) {
//            return new IncorrectCommand(ive.getMessage());
//        }
//    }
//
//}
