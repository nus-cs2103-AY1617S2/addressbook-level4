//package seedu.task.logic.commands;
//
//import seedu.task.commons.exceptions.IllegalValueException;
//import seedu.task.model.task.Date;
//
//
//
//public class FindDateCommand extends Command {
//
//    public static final String COMMAND_WORD_1 = "finddate";
//
//    public static final String MESSAGE_LISTBYDONE_SUCCESS = "Listed all tasks with specified date";
//    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ": Lists all tasks with specified date in KIT.\n"
//            + "Example: " + COMMAND_WORD_1;
//
//
//    private final Date date;
//    //@@author A0139975J
//    public FindDateCommand(String date) throws IllegalValueException {
//        this.date = new Date(date);
//    }
//    //@@author A0139975J
//    @Override
//    public CommandResult execute() {
//        model.sortTaskList();
//        model.updateFilteredTaskList(this.date);
//        return new CommandResult(getMessageForDoneTaskListShownSummary(model.getFilteredTaskList().size()));
//    }
//
//}
