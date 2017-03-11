package seedu.toluist.controller;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.controller.commons.TaskTokenizer;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;

/**
 * DeleteTaskController is responsible for deleting a task
 */
public class DeleteTaskController extends Controller {

    private static final String COMMAND_TEMPLATE = "^delete"
            + "(\\s+(?<index>.+))?\\s*";

    private static final String COMMAND_DELETE_TASK = "delete";

    private static final String RESULT_MESSAGE_DELETE_TASK = "Task deleted";

    private Logger logger = LogsCenter.getLogger(getClass());

    public DeleteTaskController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        logger.info(getClass().getName() + " will handle command");

        TodoList todoList = TodoList.load();
        CommandResult commandResult = new CommandResult("");

        TaskTokenizer taskTokenizer = new TaskTokenizer(COMMAND_TEMPLATE);
        HashMap<String, String> tokens = taskTokenizer.tokenize(command, true, false);

        String indexToken = tokens.get(TaskTokenizer.TASK_VIEW_INDEX);
        List<Integer> indexes = StringUtil.splitIndexes(indexToken, todoList.getTasks().size());
        List<Task> tasks = uiStore.getTasks(indexes);
        commandResult = delete(todoList, tasks);

        if (todoList.save()) {
            uiStore.setTask(todoList.getTasks());
            renderer.render();
        }

        return commandResult;
    }

    private CommandResult delete(TodoList todoList, List<Task> tasks) {
        String message = "";
        for (Task task: tasks) {
            message += delete(todoList, task).getFeedbackToUser() + "\n";
        }
        return new CommandResult(message);
    }

    private CommandResult delete(TodoList todoList, Task task) {
        todoList.remove(task);
        return new CommandResult(RESULT_MESSAGE_DELETE_TASK);
    }

    @Override
    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_DELETE_TASK };
    }
}
