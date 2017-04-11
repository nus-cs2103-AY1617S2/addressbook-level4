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

    public static final String MESSAGE_INCORRECT_FORMAT = "Invalid format for filter.";
    public static final String MESSAGE_USAGE =
              "\nFormat: filter [FILTER_TYPE] [FILTER_ARGUMENT] "
            + "\n[FILTER_TYPE] can be name, desc (description), status, before, after."
            + "\n[FILTER_ARGUMENT] can include a date, a status, a task description, or the name of a task."
            + "\nExample: filter after today / filter status completed";


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
            return new IncorrectCommand(MESSAGE_INCORRECT_FORMAT + MESSAGE_USAGE);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand("Invalid argument");
        }
    }

}
