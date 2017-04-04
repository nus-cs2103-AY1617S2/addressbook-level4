import java.util.regex.Pattern;

public class CliSyntaxUnused {
    //@@author A0141928B-unused
    //Supposed to be in CliSyntax.java to check if the user specified a valid path and file name
    //Unused because I'm using another way to check for validity in SaveCommandParser.java
    public static final Pattern RELATIVE_PATH_ARGS_FORMAT =
            Pattern.compile("<name>\\.<extension>"); //incomplete
    public static final Pattern ABSOLUTE_PATH_ARGS_FORMAT =
            Pattern.compile("<name>\\.<extension>"); //incomplete
    public static final Pattern FILE_NAME_ARGS_FORMAT =
            Pattern.compile("\\p{Alnum}"); //Must be alphanumeric
}
