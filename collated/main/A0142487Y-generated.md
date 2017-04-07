# A0142487Y-generated
###### \java\seedu\task\logic\parser\ThemeChangeCommandParser.java
``` java
public class ThemeChangeCommandParser extends CommandParser {

    public Command parse(String args) {
        return Theme.getTheme(args) == null ? new
                IncorrectCommand(String.format(ThemeChangeCommand.MESSAGE_FAILURE, args))
                : new ThemeChangeCommand(args);
    }

}
```
