package seedu.toluist.controller;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.logging.Logger;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.util.DateTimeUtil;
import seedu.toluist.controller.commons.IndexTokenizer;
import seedu.toluist.controller.commons.TaskTokenizer;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;

/**
 * UpdateTaskController is responsible for updating a task
 */
public class UpdateTaskController extends Controller {

    private static final String COMMAND_TEMPLATE = "^update"
            + "(\\s+(?<index>\\d+))?"
            + "(\\s+(?<description>.+))?\\s*";

    private static final String COMMAND_UPDATE_TASK = "update";

    private static final String RESULT_MESSAGE_UPDATE_TASK = "Task updated";

    private Logger logger = LogsCenter.getLogger(getClass());

    public UpdateTaskController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        logger.info(getClass().getName() + " will handle command");

        TodoList todoList = TodoList.load();
        CommandResult commandResult = new CommandResult("");

        TaskTokenizer taskTokenizer = new TaskTokenizer(COMMAND_TEMPLATE);
        HashMap<String, String> tokens = taskTokenizer.tokenize(command, true, true);

        String description = tokens.get(TaskTokenizer.TASK_DESCRIPTION);

        String indexToken = tokens.get(TaskTokenizer.TASK_VIEW_INDEX);
        int index = IndexTokenizer.getIndex(indexToken);
        Task task = uiStore.getTask(index);

        String startDateToken = tokens.get(TaskTokenizer.TASK_START_DATE_KEYWORD);
        LocalDateTime startDateTime = DateTimeUtil.parseDateString(startDateToken);

        String endDateToken = tokens.get(TaskTokenizer.TASK_END_DATE_KEYWORD);
        LocalDateTime endDateTime = DateTimeUtil.parseDateString(endDateToken);

        commandResult = update(task, description, startDateTime, endDateTime);

        if (todoList.save()) {
            uiStore.setTask(todoList.getTasks());
            renderer.render();
        }

        return commandResult;
    }

    private CommandResult update(Task task, String description,
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (!description.isEmpty()) {
            task.setDescription(description);
        }
        if (endDateTime != null) {
            task.setEndDateTime(endDateTime);
            if (startDateTime != null) {
                task.setStartDateTime(startDateTime);
            }
        }
        return new CommandResult(RESULT_MESSAGE_UPDATE_TASK);
    }

    @Override
    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_UPDATE_TASK };
    }
}
