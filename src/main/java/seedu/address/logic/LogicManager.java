package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Parser;
import seedu.address.model.Model;
import seedu.address.model.task.ReadOnlyTask;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;

    public LogicManager(Model model) {
        this.model = model;
        this.parser = new Parser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException {

        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = getCommand(commandText);
        CommandResult result = executeCommand(commandText, command);
        return result;
    }

    private Command getCommand(String commandText) {
        Command command = parser.parseCommand(commandText.trim(), this);
        command.setData(model);
        return command;
    }

    // @@author A0139388M
    private CommandResult executeCommand(String commandText, Command toExecute) throws CommandException {
        try {
            if (!(toExecute instanceof UndoCommand)) {

                if (toExecute instanceof RedoCommand) {
                    commandText = ((RedoCommand) toExecute).getToRedo();
                    toExecute = getCommand(commandText);
                } else {
                    model.clearRedoCommandHistory();
                }

                model.saveCurrentState(commandText.trim());
            }
            /*
             * if (toExecute instanceof DeleteCommand) { // Play animation
             * before deleting int taskIndex = ((DeleteCommand)
             * toExecute).targetIndex; model.getFilteredTaskList().get(taskIndex
             * - 1).setAnimation(2); raise(new
             * TaskManagerChangedEvent(model.getTaskManager())); try {
             * Thread.sleep(3000); } catch (InterruptedException e) { // TODO
             * Auto-generated catch block e.printStackTrace(); } }
             */
            return toExecute.execute();
        } catch (CommandException e) {
            if (!(toExecute instanceof UndoCommand)) {
                model.discardCurrentState();
            }

            throw e;
        }
    }
    // @@author

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public void prepareTaskList(ObservableList<ReadOnlyTask> taskListToday, ObservableList<ReadOnlyTask> taskListFuture,
            ObservableList<ReadOnlyTask> taskListCompleted) {
        model.prepareTaskList(taskListToday, taskListFuture, taskListCompleted);
    }

    @Override
    public int parseUIIndex(String uiIndex) {
        return model.parseUIIndex(uiIndex);
    }

    @Override
    public boolean isValidUIIndex(String uiIndex) {
        return model.isValidUIIndex(uiIndex);
    }

    /*
     * Gets UI index by absolute index
     */
    @Override
    public String getUIIndex(int index) {
        return model.getUIIndex(index);
    }
}
