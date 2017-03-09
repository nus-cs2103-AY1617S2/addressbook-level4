package seedu.toluist.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.toluist.commons.util.DateTimeUtil;
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

        HashMap<String, String> tokens = tokenize(command);

        String description = tokens.get(TASK_DESCRIPTION);
        String startDateToken = tokens.get(TASK_START_DATE_KEYWORD);
        String endDateToken = tokens.get(TASK_END_DATE_KEYWORD);
        System.out.println("Description Token: " + description);
        System.out.println("Start Date Token: " + startDateToken);
        System.out.println("End Date Token: " + endDateToken);

        LocalDateTime startDateTime = DateTimeUtil.toDate(startDateToken);
        LocalDateTime endDateTime = DateTimeUtil.toDate(endDateToken);

        commandResult = add(todoList, description, startDateTime, endDateTime);

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
        tokens.put(TASK_DESCRIPTION, description);
        tokens.put(TASK_START_DATE_KEYWORD, startDate);
        tokens.put(TASK_END_DATE_KEYWORD, endDate);
        return tokens;
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
