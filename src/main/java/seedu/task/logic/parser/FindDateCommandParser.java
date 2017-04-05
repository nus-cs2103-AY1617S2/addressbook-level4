//package seedu.task.logic.parser;
//
////@@author A0139975J
//import seedu.task.commons.exceptions.IllegalValueException;
//import seedu.task.logic.commands.Command;
//import seedu.task.logic.commands.FindDateCommand;
//import seedu.task.logic.commands.IncorrectCommand;
//
////@@author A0139975J
//public class FindDateCommandParser extends CommandParser {
//
//    public static final String DEFAULT_DATE = "DEFAULT_DATE";
//    public static final String MESSAGE_DATE_CONSTRAINTS = "Date format invalid, try dates like,"
//            + " tomorrow at 5pm or 4th April." + " Check that Month is before date," + " MM/DD/YY or MM-DD-YY";
//
//    // @@author A0139975J
//    @Override
//    public Command parse(String date) {
//        // TODO Auto-generated method stub
//        try {
//            return new FindDateCommand(date);
//        } catch (IllegalValueException ive) {
//            // TODO Auto-generated catch block
//            return new IncorrectCommand(ive.getMessage());
//        }
//    }

}
