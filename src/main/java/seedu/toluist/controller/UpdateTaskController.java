package seedu.toluist.controller;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;
import seedu.toluist.ui.UiStore;

public class UpdateTaskController extends TaskController {

    private static final String COMMAND_TEMPLATE = "^update"
            + "(\\s+(?<index>\\d+))?"
            + "(\\s+(?<description>.+))?\\s*";

    private static final String TASK_VIEW_INDEX = "index";
    private static final String TASK_DESCRIPTION = "description";
    private static final String TASK_START_DATE_KEYWORD = "startdate/";
    private static final String TASK_END_DATE_KEYWORD = "enddate/";

    private static final String COMMAND_UPDATE_TASK = "update";

    private static final String RESULT_MESSAGE_UPDATE_TASK = "Task updated";

    public UpdateTaskController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        logger.info(getClass().getName() + " will handle command");

        TodoList todoList = TodoList.load();
        CommandResult commandResult = new CommandResult("");

        HashMap<String, String> tokens = tokenize(command);

        String description = tokens.get(TASK_DESCRIPTION);
        String indexToken = tokens.get(TASK_VIEW_INDEX);
        String startDateToken = tokens.get(TASK_START_DATE_KEYWORD);
        String endDateToken = tokens.get(TASK_END_DATE_KEYWORD);
        LocalDateTime startDateTime = toDate(startDateToken);
        LocalDateTime endDateTime = toDate(endDateToken);
        int index = indexToken != null ? Integer.parseInt(indexToken) - 1 : -1;
        Task task = indexToken != null
                ? UiStore.getInstance().getTasks().get(index)
                : null;
        System.out.println("Index Token: " + index);
        System.out.println("Description Token: " + description);
        System.out.println("Start Date Token: " + startDateToken);
        System.out.println("End Date Token: " + endDateToken);

        commandResult = update(todoList, task, description, startDateTime, endDateTime);

        if (todoList.save()) {
            uiStore.setTask(todoList.getTasks());
            renderer.render();
        }

        return commandResult;
    }

    @Override
    public HashMap<String, String> tokenize(String commandArgs) {
        Pattern pattern = Pattern.compile(COMMAND_TEMPLATE);
        Matcher matcher = pattern.matcher(commandArgs.trim());
        matcher.find();
        String description = matcher.group(TASK_DESCRIPTION);
        int startDateIndex = description.lastIndexOf(TASK_START_DATE_KEYWORD);
        int endDateIndex = description.lastIndexOf(TASK_END_DATE_KEYWORD);
        String startDate = null;
        String endDate = null;
        if (endDateIndex != -1) {
            // Is task with deadline
            endDate = description.substring(endDateIndex + TASK_END_DATE_KEYWORD.length());
            description = description.substring(0, endDateIndex);
            if (startDateIndex != -1) {
                // Is event
                startDate = description.substring(startDateIndex + TASK_START_DATE_KEYWORD.length());
                description = description.substring(0, startDateIndex);
            }
        } // Else it is a floating task (we are not dealing with task with only start date and no end date)
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put(TASK_VIEW_INDEX, matcher.group(TASK_VIEW_INDEX));
        tokens.put(TASK_DESCRIPTION, description);
        tokens.put(TASK_START_DATE_KEYWORD, startDate);
        tokens.put(TASK_END_DATE_KEYWORD, endDate);
        return tokens;
    }

    @Override
    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
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
        // Save in model, validate that it is either floating, event or deadline task.
        return new CommandResult(RESULT_MESSAGE_UPDATE_TASK);
    }

    private LocalDateTime toDate(String dateString) {
        if (dateString == null) {
            return null;
        } else {
            Parser parser = new Parser();
            List<DateGroup> dateGroups = parser.parse(dateString);
            if (dateGroups.isEmpty()) {
                return null;
            } else {
                DateGroup dateGroup = dateGroups.get(0);
                List<Date> dates = dateGroup.getDates();
                if (dates.isEmpty()) {
                    return null;
                } else {
                    Date date = dates.get(0);
                    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
                }
            }
        }
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_UPDATE_TASK };
    }
}
