# A0140063X-reused
###### \java\seedu\task\commons\core\GoogleCalendar.java
``` java
public class GoogleCalendar {
    public static final String CALENDAR_ID = "primary";
    public static final String CONNECTION_FAIL_MESSAGE = "Unable to connect to Google.";
    private static final Logger logger = LogsCenter.getLogger(LogsCenter.class);

    private static final String APPLICATION_NAME =
        "Keep It Tidy";

    /** Directory to store user credentials for this application. */
    private static java.io.File dataStoreDir = new java.io.File(
        System.getProperty("user.home"), ".credentials/keep-it-tidy");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory dataStoreFactory;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-quickstart
     */
    private static final List<String> SCOPES =
        Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(dataStoreDir);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                GoogleCalendar.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(dataStoreFactory)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        logger.info("Credentials saved to " + dataStoreDir.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException
     */
    public static com.google.api.services.calendar.Calendar
        getCalendarService() throws IOException {
        Credential credential = authorize();
        return new com.google.api.services.calendar.Calendar.Builder(
                httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

```
###### \java\seedu\task\logic\commands\SmartAddCommand.java
``` java
    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(0));
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

}
```
###### \java\seedu\task\logic\parser\GetGoogleCalendarCommandParser.java
``` java
public class GetGoogleCalendarCommandParser extends CommandParser {

    @Override
    public Command parse(String args) {
        return new GetGoogleCalendarCommand();
    }

}
```
###### \java\seedu\task\logic\parser\PostGoogleCalendarCommandParser.java
``` java
public class PostGoogleCalendarCommandParser extends CommandParser {

    @Override
    public Command parse(String args) {
        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            return new  PostGoogleCalendarCommand(PostGoogleCalendarCommand.NO_INDEX);
        }

        return new PostGoogleCalendarCommand(index.get());
    }
}
```
###### \java\seedu\task\logic\parser\RedoCommandParser.java
``` java
public class RedoCommandParser extends CommandParser {

    @Override
    public Command parse(String args) {
        return new RedoCommand();
    }

}
```
###### \java\seedu\task\logic\parser\SmartAddCommandParser.java
``` java
/**
 * Parses input arguments and creates a new QuickAddCommand object
 */
public class SmartAddCommandParser extends CommandParser {

    private String remark;

    /**
     * Parses the given {@code String} of arguments in the context of the
     * QuickAddCommand and returns an QuickAddCommand object for execution.
     */
    @Override
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_REMARK, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        Map<Prefix, List<String>> tokenizedArguments = argsTokenizer.getTokenizedArguments();
        remark = tokenizedArguments.containsKey(PREFIX_REMARK) ? argsTokenizer.getValue(PREFIX_REMARK).get()
                : "";
        try {
            return new SmartAddCommand(argsTokenizer.getPreamble().get().replace("\\", ""), remark.replace("\\", ""),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)));
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SmartAddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (IOException ioe) {
            return new IncorrectCommand(ioe.getMessage());
        }
    }

}
```
###### \java\seedu\task\logic\parser\UndoCommandParser.java
``` java
public class UndoCommandParser extends CommandParser {

    @Override
    public Command parse(String args) {
        return new UndoCommand();
    }

}
```
