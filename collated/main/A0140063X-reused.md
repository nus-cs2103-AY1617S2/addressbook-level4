# A0140063X-reused
###### /java/seedu/task/commons/core/GoogleCalendar.java
``` java
public class GoogleCalendar {
    public static final String calendarId = "primary";
    private static final Logger logger = LogsCenter.getLogger(LogsCenter.class);

    private static final String APPLICATION_NAME =
        "Keep It Tidy";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/keep-it-tidy");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-quickstart
     */
    private static final List<String> SCOPES =
        Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
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
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        logger.info("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
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
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

```
###### /java/seedu/task/logic/parser/GetGoogleCalendarCommandParser.java
``` java
public class GetGoogleCalendarCommandParser extends CommandParser {

    @Override
    public Command parse(String args) {
        return new GetGoogleCalendarCommand();
    }

}
```
###### /java/seedu/task/logic/parser/PostGoogleCalendarCommandParser.java
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
###### /java/seedu/task/logic/parser/RedoCommandParser.java
``` java
public class RedoCommandParser extends CommandParser {

    @Override
    public Command parse(String args) {
        return new RedoCommand();
    }

}
```
###### /java/seedu/task/logic/parser/UndoCommandParser.java
``` java
public class UndoCommandParser extends CommandParser {

    @Override
    public Command parse(String args) {
        return new UndoCommand();
    }

}
```
