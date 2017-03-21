package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.atteo.evo.inflector.English;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.core.Messages;
import seedu.toluist.commons.util.CollectionUtil;
import seedu.toluist.controller.commons.IndexParser;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;
import seedu.toluist.ui.UiStore;

/**
 * Mark Controller is responsible for marking task complete or incomplete
 */
public class MarkController extends Controller {
    private static final String RESULT_MESSAGE_COMPLETED_SUCCESS = "%s %s marked completed";
    private static final String RESULT_MESSAGE_INCOMPLETE_SUCCESS = "%s %s marked incomplete";
    private static final String COMMAND_TEMPLATE = "mark(\\s+(?<markType>(complete|incomplete)))?(?<index>.*)?\\s*";
    private static final String COMMAND_WORD = "mark";

    private static final String MARK_TERM = "markType";
    private static final String INDEX_TERM = "index";
    private static final String MARK_COMPLETE = "complete";
    private static final String MARK_INCOMPLETE = "incomplete";
    private static final Logger logger = LogsCenter.getLogger(MarkController.class);

    public MarkController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        logger.info(getClass().toString() + " will handle command");

        HashMap<String, String> tokens = tokenize(command);
        String indexToken = tokens.get(INDEX_TERM);
        String markTypeToken = tokens.get(MARK_TERM);
        List<Integer> indexes = IndexParser.splitStringToIndexes(indexToken,
                UiStore.getInstance().getShownTasks().size());

        if (indexes.isEmpty()) {
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_INDEX);
        }

        CommandResult commandResult;
        if (Objects.equals(markTypeToken, MARK_INCOMPLETE)) {
            commandResult = mark(indexes, false);
        } else {
            commandResult = mark(indexes, true);
        }

        TodoList todoList = TodoList.load();
        if (!todoList.save()) {
            return new CommandResult(Messages.MESSAGE_SAVING_FAILURE);
        }
        UiStore.getInstance().setTask(todoList.getTasks());
        renderer.render();
        return commandResult;
    }

    private CommandResult mark(List<Integer> taskIndexes, boolean isCompleted) {
        ArrayList<Task> tasks = UiStore.getInstance().getShownTasks(taskIndexes);
        for (Task task : tasks) {
            task.setCompleted(isCompleted);
        }
        String indexString = CollectionUtil.toString(", ", taskIndexes);
        String messageTemplate = isCompleted
                ? RESULT_MESSAGE_COMPLETED_SUCCESS
                : RESULT_MESSAGE_INCOMPLETE_SUCCESS;
        return new CommandResult(String.format(messageTemplate,
                English.plural("Task", taskIndexes.size()), indexString));
    }

    public HashMap<String, String> tokenize(String command) {
        Pattern pattern = Pattern.compile(COMMAND_TEMPLATE);
        Matcher matcher = pattern.matcher(command.trim());
        matcher.find();
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put(MARK_TERM, matcher.group(MARK_TERM));
        tokens.put(INDEX_TERM, matcher.group(INDEX_TERM));
        return tokens;
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }
}
