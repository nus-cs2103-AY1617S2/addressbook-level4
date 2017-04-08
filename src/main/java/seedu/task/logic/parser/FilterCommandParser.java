package seedu.task.logic.parser;

import java.util.function.Predicate;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.FilterCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.model.task.TaskDateAfterPredicate;
import seedu.task.model.task.TaskDateBeforePredicate;
import seedu.task.model.task.TaskDescriptionPredicate;
import seedu.task.model.task.TaskNamePredicate;
import seedu.task.model.task.TaskStatusPredicate;

//@@author A0163845X
public class FilterCommandParser {

    public Command parse(String arguments) {
        try {
            arguments = arguments.toLowerCase();
            arguments = arguments.trim();
            int predicateSplitPoint = arguments.indexOf(' ');
            String predicateType = arguments.substring(0, predicateSplitPoint);
            String predicateArgument = arguments.substring(predicateSplitPoint, arguments.length());
            predicateArgument = predicateArgument.trim();
            predicateType = predicateType.trim();
            System.out.println(predicateType);
            System.out.println(predicateArgument);
            Predicate<Object> pred;
            switch (predicateType) {
            case TaskStatusPredicate.PREDICATE_WORD:
                pred = new TaskStatusPredicate(predicateArgument);
                break;
            case TaskNamePredicate.PREDICATE_WORD:
                pred = new TaskNamePredicate(predicateArgument);
                break;
            case TaskDateBeforePredicate.PREDICATE_WORD:
                pred = new TaskDateBeforePredicate(predicateArgument);
                break;
            case TaskDateAfterPredicate.PREDICATE_WORD:
                pred = new TaskDateAfterPredicate(predicateArgument);
                break;
            case TaskDescriptionPredicate.PREDICATE_WORD:
                pred = new TaskDescriptionPredicate(predicateArgument);
                break;
            default:
                return new IncorrectCommand("Invalid predicate type, can use status or name");
            }
            return new FilterCommand(pred);
        } catch (StringIndexOutOfBoundsException e) {
            return new IncorrectCommand("Invalid format for filter. filter [type] [arguments]");
        } catch (IllegalValueException ive) {
            return new IncorrectCommand("Invalid argument");
        }
    }

}
