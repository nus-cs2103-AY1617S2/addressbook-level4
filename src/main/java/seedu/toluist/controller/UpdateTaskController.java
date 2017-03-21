package seedu.toluist.controller;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.util.DateTimeUtil;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.controller.commons.IndexParser;
import seedu.toluist.controller.commons.TagParser;
import seedu.toluist.controller.commons.TaskTokenizer;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Tag;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;

/**
 * UpdateTaskController is responsible for updating a task
 */
public class UpdateTaskController extends Controller {

    private static final String COMMAND_TEMPLATE = "^update"
            + "(\\s+(?<index>\\d+))?"
            + "(\\s+(?<description>.+))?\\s*";

    private static final String COMMAND_UPDATE_TASK = "update";

    private static final String RESULT_MESSAGE_UPDATE_TASK = "Task updated";

    private static final Logger logger = LogsCenter.getLogger(UpdateTaskController.class);

    public CommandResult execute(String command) {
        logger.info(getClass().getName() + " will handle command");

        TodoList todoList = TodoList.load();
        CommandResult commandResult = new CommandResult("");

        HashMap<String, String> tokens = tokenize(command);

        String description = tokens.get(TaskTokenizer.TASK_DESCRIPTION);

        String indexToken = tokens.get(TaskTokenizer.TASK_VIEW_INDEX);
        List<Integer> indexes = IndexParser.splitStringToIndexes(indexToken, uiStore.getShownTasks().size());
        Task task = uiStore.getShownTasks(indexes).get(0);

        String startDateToken = tokens.get(TaskTokenizer.TASK_START_DATE_KEYWORD);
        LocalDateTime startDateTime = DateTimeUtil.parseDateString(startDateToken);

        String endDateToken = tokens.get(TaskTokenizer.TASK_END_DATE_KEYWORD);
        LocalDateTime endDateTime = DateTimeUtil.parseDateString(endDateToken);

        String tagsToken = tokens.get(TaskTokenizer.TASK_TAGS_KEYWORD);
        Set<Tag> tags = TagParser.parseTags(tagsToken);

        commandResult = update(task, description, startDateTime, endDateTime, tags);

        if (todoList.save()) {
            uiStore.setTasks(todoList.getTasks());
        }

        return commandResult;
    }

    public HashMap<String, String> tokenize(String command) {
        return TaskTokenizer.tokenize(COMMAND_TEMPLATE, command, true, true);
    }

    private CommandResult update(Task task, String description,
            LocalDateTime startDateTime, LocalDateTime endDateTime, Set<Tag> tags) {
        if (StringUtil.isPresent(description)) {
            task.setDescription(description);
        }
        if (endDateTime != null) {
            task.setEndDateTime(endDateTime);
            if (startDateTime != null) {
                task.setStartDateTime(startDateTime);
            }
        }
        if (!tags.isEmpty()) {
            task.replaceTags(tags);
        }
        return new CommandResult(RESULT_MESSAGE_UPDATE_TASK);
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_UPDATE_TASK };
    }
}
