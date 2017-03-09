package seedu.toluist.controller;

import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Task;
import seedu.toluist.ui.Ui;
import seedu.toluist.ui.UiStore;

/**
 * TaskController is responsible for management of task
 */
public abstract class TaskController extends Controller {

    private String commandTemplate;

    protected static final String TASK_VIEW_INDEX = "index";
    protected static final String TASK_DESCRIPTION = "description";
    protected static final String TASK_START_DATE_KEYWORD = "startdate/";
    protected static final String TASK_END_DATE_KEYWORD = "enddate/";

    protected static final int START_INDEX = 0;
    protected static final int INVALID_INDEX = -1;

    protected Logger logger = LogsCenter.getLogger(getClass());

    public TaskController(Ui renderer) {
        super(renderer);
    }

    public TaskController(Ui renderer, String commandTemplate) {
        super(renderer);
        this.commandTemplate = commandTemplate;
    }

    public HashMap<String, String> tokenize(String commandArgs, Boolean hasIndex, Boolean hasDescription) {
        Pattern pattern = Pattern.compile(commandTemplate);
        Matcher matcher = pattern.matcher(commandArgs.trim());
        matcher.find();
        HashMap<String, String> tokens = new HashMap<>();
        if (hasIndex) {
            tokens.put(TASK_VIEW_INDEX, matcher.group(TASK_VIEW_INDEX));
        }
        if (hasDescription) {
            String description = matcher.group(TASK_DESCRIPTION);
            int startDateIndex = description.lastIndexOf(TASK_START_DATE_KEYWORD);
            int endDateIndex = description.lastIndexOf(TASK_END_DATE_KEYWORD);
            String startDate = null;
            String endDate = null;
            if (endDateIndex != INVALID_INDEX) {
                // Is task with deadline
                endDate = description.substring(endDateIndex + TASK_END_DATE_KEYWORD.length());
                description = description.substring(START_INDEX, endDateIndex);
                if (startDateIndex != INVALID_INDEX) {
                    // Is event
                    startDate = description.substring(startDateIndex + TASK_START_DATE_KEYWORD.length());
                    description = description.substring(START_INDEX, startDateIndex);
                }
            } // Else it is a floating task (and we are not dealing with task with only start date and no end date)

            tokens.put(TASK_DESCRIPTION, description);
            tokens.put(TASK_START_DATE_KEYWORD, startDate);
            tokens.put(TASK_END_DATE_KEYWORD, endDate);
        }
        return tokens;
    }

    public Task getTask(String indexToken) {
        int index = indexToken != null ? Integer.parseInt(indexToken) - 1 : INVALID_INDEX;
        Task task = indexToken != null
                ? UiStore.getInstance().getTasks().get(index)
                : null;
        return task;
    }

    @Override
    public boolean matchesCommand(String command) {
        return command.matches(commandTemplate);
    }

    @Override
    public abstract CommandResult execute(String command);
}
