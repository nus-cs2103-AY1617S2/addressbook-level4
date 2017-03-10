package seedu.toluist.controller;

import java.time.LocalDateTime;
import java.util.HashMap;

import seedu.toluist.commons.util.DateTimeUtil;
import seedu.toluist.controller.commons.TaskTokenizer;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;

/**
 * AddTaskController is responsible for adding a task (and event)
 */
public class AddTaskController extends TaskController {

    private static final String COMMAND_TEMPLATE = "^add"
            + "(\\s+(?<description>.+))?";

    private static final String COMMAND_ADD_TASK = "add";

    private static final String RESULT_MESSAGE_ADD_TASK = "New task added";

    public AddTaskController(Ui renderer) {
        super(renderer, COMMAND_TEMPLATE);
    }

    public CommandResult execute(String command) {
        logger.info(getClass().getName() + " will handle command");

        TodoList todoList = TodoList.load();
        CommandResult commandResult = new CommandResult("");

        TaskTokenizer taskTokenizer = new TaskTokenizer(COMMAND_TEMPLATE);
        HashMap<String, String> tokens = taskTokenizer.tokenize(command, false, true);

        String description = tokens.get(TaskTokenizer.TASK_DESCRIPTION);

        String startDateToken = tokens.get(TaskTokenizer.TASK_START_DATE_KEYWORD);
        LocalDateTime startDateTime = DateTimeUtil.toDate(startDateToken);

        String endDateToken = tokens.get(TaskTokenizer.TASK_END_DATE_KEYWORD);
        LocalDateTime endDateTime = DateTimeUtil.toDate(endDateToken);

        commandResult = add(todoList, description, startDateTime, endDateTime);

        if (todoList.save()) {
            uiStore.setTask(todoList.getTasks());
            renderer.render();
        }

        return commandResult;
    }

    private CommandResult add(TodoList todoList, String description,
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        todoList.add(new Task(description, startDateTime, endDateTime));
        return new CommandResult(RESULT_MESSAGE_ADD_TASK);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_ADD_TASK };
    }
}
