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

/**
 * Add floating tasks, tasks with deadlines and events into the todo list.
 */
public class AddTaskController extends TaskController {

    private static final String COMMAND_TEMPLATE = "^add"
            + "(\\s+(?<fullDescription>.+))?\\s*";

    private static final String TASK_FULL_DESCRIPTION = "fullDescription";
    private static final String TASK_DESCRIPTION = "description";
    private static final String TASK_START_DATE = "startdate/";
    private static final String TASK_END_DATE = "enddate/";

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

        String description = tokens.get(TASK_DESCRIPTION);
        String startDateToken = tokens.get(TASK_START_DATE);
        String endDateToken = tokens.get(TASK_END_DATE);
        System.out.println("Description Token: " + description);
        System.out.println("Start Date Token: " + startDateToken);
        System.out.println("End Date Token: " + endDateToken);

        LocalDateTime startDateTime = toDate(startDateToken);
        LocalDateTime endDateTime = toDate(endDateToken);

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
        logger.info(matcher.toString());
        matcher.find();
        String fullDescription = matcher.group(TASK_FULL_DESCRIPTION);
        int startDateIndex = fullDescription.lastIndexOf(TASK_START_DATE);
        int endDateIndex = fullDescription.lastIndexOf(TASK_END_DATE);
        String startDate = null;
        String endDate = null;
        if (endDateIndex != -1) {
            // Is task with deadline
            endDate = fullDescription.substring(endDateIndex + TASK_END_DATE.length());
            fullDescription = fullDescription.substring(0, endDateIndex);
            if (startDateIndex != -1) {
                // Is event
                startDate = fullDescription.substring(startDateIndex + TASK_START_DATE.length());
                fullDescription = fullDescription.substring(0, startDateIndex);
            }
        } // Else it is a floating task (we are not dealing with task with only start date and no end date)
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put(TASK_DESCRIPTION, fullDescription);
        tokens.put(TASK_START_DATE, startDate);
        tokens.put(TASK_END_DATE, endDate);
        return tokens;
    }

    @Override
    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    private CommandResult add(TodoList todoList, String description,
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        todoList.add(new Task(description, startDateTime, endDateTime));
        return new CommandResult(RESULT_MESSAGE_ADD_TASK);
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
        return new String[] { COMMAND_ADD_TASK };
    }
}
