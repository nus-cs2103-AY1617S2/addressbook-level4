package seedu.toluist.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.util.DateTimeUtil;
import seedu.toluist.controller.commons.TagParser;
import seedu.toluist.controller.commons.TaskTokenizer;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Tag;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;

/**
 * AddTaskController is responsible for adding a task (and event)
 */
public class AddTaskController extends Controller {
    private static final Logger logger = LogsCenter.getLogger(AddTaskController.class);

    private static final String COMMAND_TEMPLATE = "^add"
            + "(\\s+(?<description>.+))?";

    private static final String COMMAND_ADD_TASK = "add";

    private static final String RESULT_MESSAGE_ADD_TASK = "New task added";

    public AddTaskController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        logger.info(getClass().getName() + " will handle command");

        TodoList todoList = TodoList.load();
        CommandResult commandResult = new CommandResult("");

        HashMap<String, String> tokens = tokenize(command);

        String description = tokens.get(TaskTokenizer.TASK_DESCRIPTION);

        String startDateToken = tokens.get(TaskTokenizer.TASK_START_DATE_KEYWORD);
        LocalDateTime startDateTime = DateTimeUtil.parseDateString(startDateToken);

        String endDateToken = tokens.get(TaskTokenizer.TASK_END_DATE_KEYWORD);
        LocalDateTime endDateTime = DateTimeUtil.parseDateString(endDateToken);

        String tagsToken = tokens.get(TaskTokenizer.TASK_TAGS_KEYWORD);
        Set<Tag> tags = TagParser.parseTags(tagsToken);

        commandResult = add(todoList, description, startDateTime, endDateTime, tags);

        if (todoList.save()) {
            uiStore.setTask(todoList.getTasks());
            renderer.render();
        }

        return commandResult;
    }

    public HashMap<String, String> tokenize(String command) {
        return TaskTokenizer.tokenize(COMMAND_TEMPLATE, command, false, true);
    }

    private CommandResult add(TodoList todoList, String description,
            LocalDateTime startDateTime, LocalDateTime endDateTime, Set<Tag> tags) {
        Task task = new Task(description, startDateTime, endDateTime, tags);
        todoList.add(task);
        return new CommandResult(RESULT_MESSAGE_ADD_TASK);
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_ADD_TASK };
    }
}
