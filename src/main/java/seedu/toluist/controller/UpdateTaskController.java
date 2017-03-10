package seedu.toluist.controller;
import java.time.LocalDateTime;
import java.util.HashMap;

import seedu.toluist.commons.util.DateTimeUtil;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;

/**
 * UpdateTaskController is responsible for updating a task
 */
public class UpdateTaskController extends TaskController {

    private static final String COMMAND_TEMPLATE = "^update"
            + "(\\s+(?<index>\\d+))?"
            + "(\\s+(?<description>.+))?\\s*";

    private static final String COMMAND_UPDATE_TASK = "update";

    private static final String RESULT_MESSAGE_UPDATE_TASK = "Task updated";

    public UpdateTaskController(Ui renderer) {
        super(renderer, COMMAND_TEMPLATE);
    }

    public CommandResult execute(String command) {
        logger.info(getClass().getName() + " will handle command");

        TodoList todoList = TodoList.load();
        CommandResult commandResult = new CommandResult("");

        HashMap<String, String> tokens = tokenize(command, true, true);

        String description = tokens.get(TASK_DESCRIPTION);

        String indexToken = tokens.get(TASK_VIEW_INDEX);
        Task task = getTask(indexToken);

        String startDateToken = tokens.get(TASK_START_DATE_KEYWORD);
        LocalDateTime startDateTime = DateTimeUtil.toDate(startDateToken);

        String endDateToken = tokens.get(TASK_END_DATE_KEYWORD);
        LocalDateTime endDateTime = DateTimeUtil.toDate(endDateToken);

        commandResult = update(todoList, task, description, startDateTime, endDateTime);

        if (todoList.save()) {
            uiStore.setTask(todoList.getTasks());
            renderer.render();
        }

        return commandResult;
    }

    private CommandResult update(TodoList todoList, Task task, String description,
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (!description.isEmpty()) {
            task.description = description;
        }
        if (endDateTime != null) {
            task.endDateTime = endDateTime;
            if (startDateTime != null) {
                task.startDateTime = startDateTime;
            }
        }
        return new CommandResult(RESULT_MESSAGE_UPDATE_TASK);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_UPDATE_TASK };
    }
}
