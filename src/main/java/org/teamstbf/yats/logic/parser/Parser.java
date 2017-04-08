package org.teamstbf.yats.logic.parser;

import static org.teamstbf.yats.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static org.teamstbf.yats.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.teamstbf.yats.logic.commands.AddCommand;
import org.teamstbf.yats.logic.commands.ChangeSaveLocationCommand;
import org.teamstbf.yats.logic.commands.ResetCommand;
import org.teamstbf.yats.logic.commands.ClearDoneCommand;
import org.teamstbf.yats.logic.commands.Command;
import org.teamstbf.yats.logic.commands.DeleteCommand;
import org.teamstbf.yats.logic.commands.EditCommand;
import org.teamstbf.yats.logic.commands.ExitCommand;
import org.teamstbf.yats.logic.commands.FindCommand;
import org.teamstbf.yats.logic.commands.HelpCommand;
import org.teamstbf.yats.logic.commands.IncorrectCommand;
import org.teamstbf.yats.logic.commands.ListCommand;
import org.teamstbf.yats.logic.commands.MarkDoneCommand;
import org.teamstbf.yats.logic.commands.MarkUndoneCommand;
import org.teamstbf.yats.logic.commands.RedoCommand;
import org.teamstbf.yats.logic.commands.ScheduleCommand;
import org.teamstbf.yats.logic.commands.SelectCommand;
import org.teamstbf.yats.logic.commands.UndoCommand;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput
     *            full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ResetCommand.COMMAND_WORD:
            return new ResetCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case MarkDoneCommand.COMMAND_WORD:
            return new MarkDoneCommandParser().parse(arguments);

        case MarkUndoneCommand.COMMAND_WORD:
            return new MarkUndoneCommandParser().parse(arguments);

        case ClearDoneCommand.COMMAND_WORD:
            return new ClearDoneCommand();

        case ChangeSaveLocationCommand.COMMAND_WORD:
            return new ChangeSaveLocationCommandParser().parse(arguments);

        case ScheduleCommand.COMMAND_WORD:
            return new ScheduleCommandParser().parse(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
