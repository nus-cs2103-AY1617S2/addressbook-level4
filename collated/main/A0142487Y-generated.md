# A0142487Y-generated
###### /java/seedu/task/logic/parser/CommandParser.java
``` java
import seedu.task.logic.commands.Command;
/**
 * An abstract CommandParser super class
 * The implementation of the parse method is up to its subclasses
 * @author Xu
 *
 */
public abstract class CommandParser {

    public abstract Command parse(String args);

}
```
###### /java/seedu/task/logic/parser/ThemeChangeCommandParser.java
``` java
public class ThemeChangeCommandParser extends CommandParser {

    public Command parse(String args) {
        return Theme.getTheme(args) == null ? new
                IncorrectCommand(String.format(ThemeChangeCommand.MESSAGE_FAILURE, args))
                : new ThemeChangeCommand(args);
    }

}
```
