package seedu.opus.sync;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.tasks.TasksScopes;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.TaskLists;
import com.google.api.services.tasks.model.Tasks;

import seedu.opus.commons.core.LogsCenter;
import seedu.opus.model.task.Status.Flag;
import seedu.opus.model.task.Task;
import seedu.opus.sync.exceptions.SyncException;

//@@author A0148087W
/**
 * Handles all sync operations for Google Task sync integration
 *
 */
public class SyncServiceGtask extends SyncService {

    public static final java.io.File DATA_STORE_DIR = new java.io.File("data/credentials");
    public static final java.io.File DATA_STORE_FILE = new java.io.File(DATA_STORE_DIR + "/StoredCredential");

    private static HttpTransport httpTransport;
    private static FileDataStoreFactory dataStoreFactory;
    private static final String CLIENT_ID = "972603165301-kls9usprmd2fpaelvrd0937dlcj43g6f.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "07B1QJ73rQECWSoIjAPHMDNG";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Arrays.asList(TasksScopes.TASKS);

    private final Logger logger = LogsCenter.getLogger(SyncServiceGtask.class);

    private static final String APPLICATION_NAME = "Opus";
    private static final String TASK_STATUS_COMPLETE = "completed";
    private static final String TASK_STATUS_INCOMPLETE = "needsAction";

    private static final String SYNC_ERROR_CONNECT = "Failed to connect to Google Task.\n"
                                                    + "Please check your internet connection.\n"
                                                    + "Type \"sync on\" to login again.";
    private static final String SYNC_ERROR_LOGIN = "Failed to login to Google Task.\n"
                                                    + "Please check your internet connection and"
                                                    + " authorize Opus to access Google Task.\n"
                                                    + "Type \"sync on\" to login again.";

    private com.google.api.services.tasks.Tasks service;
    private TaskList opusTaskList;
    private LinkedBlockingDeque<List<Task>> taskListDeque;
    private boolean isRunning;

    public SyncServiceGtask() {
        taskListDeque = new LinkedBlockingDeque<List<Task>>();
        this.isRunning = false;
    }

    @Override
    public void start() throws SyncException {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
            logger.info("---[GTask]: Starting Google Task");

            service = getTasksService();
            opusTaskList = getOpusTasks();
            logger.info("---[GTask]: Successfully initialised Google Task Service");
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            DATA_STORE_FILE.delete();
            raiseSyncExceptionEvent(new SyncException(SYNC_ERROR_LOGIN));
        }
        Executors.newSingleThreadExecutor().execute(() -> processUpdateTaskListDeque());
        this.isRunning = true;
    }

    @Override
    public void stop() {
        this.isRunning = false;
        logger.info("---[GTask]: Stopping Google Task");
    }

    @Override
    public void updateTaskList(List<Task> taskList) {
        assert taskList != null;
        this.taskListDeque.addFirst(taskList);
        logger.info("---[GTask]: New data added to Google Task update queue");
    }

    /**
     * Processes the taskList received from Model and pushes it to Google Task.
     * All current tasks in the user's google account is cleared and Tasks from TaskList is inserted
     * This method must be run in a separate thread from the main thread
     */
    private void processUpdateTaskListDeque() {
        assert service != null;
        assert opusTaskList != null;
        logger.info("---[GTask]: Launched GTask Push thread");
        while (isRunning) {
            try {
                List<Task> taskList = this.taskListDeque.takeFirst();
                logger.info("GTask: Processing push task queue");
                Tasks currentTaskList = listTasksFromGtask(this.opusTaskList.getId());

                if (!currentTaskList.getItems().isEmpty()) {
                    for (com.google.api.services.tasks.model.Task task : currentTaskList.getItems()) {
                        deleteTaskFromGtask(task);
                    }
                }
                for (Task taskToPush : taskList) {
                    insertTasktoGtask(taskToPush);
                }
                this.taskListDeque.clear();
            } catch (IOException | InterruptedException e) {
                this.isRunning = false;
                raiseSyncExceptionEvent(new SyncException(SYNC_ERROR_CONNECT));
            }
        }
    }

    /**
     * Launches a Google Task authorization query page using the user's default web browser
     * @return Credential
     * @throws IOException
     * @throws SyncException
     */
    private Credential authorize() throws SyncException, IOException  {
        GoogleClientSecrets.Details clientSecretsDetails = new GoogleClientSecrets.Details();
        clientSecretsDetails.setClientId(CLIENT_ID);
        clientSecretsDetails.setClientSecret(CLIENT_SECRET);
        GoogleClientSecrets clientSecrets = new GoogleClientSecrets().setInstalled(clientSecretsDetails);
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport,
                                            JSON_FACTORY, clientSecrets, SCOPES)
                                            .setDataStoreFactory(dataStoreFactory)
                                            .build();

        Credential credential = null;
        try {
            credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(APPLICATION_NAME);
        } catch (Exception e) {
            e.printStackTrace();
            deauthorize();
            this.isRunning = false;
            throw new SyncException(SYNC_ERROR_LOGIN);
        }
        logger.info("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Initialize Google Task API Service
     * @return
     * @throws IOException
     * @throws SyncException
     */
    private com.google.api.services.tasks.Tasks getTasksService() throws IOException, SyncException {
        Credential credential = authorize();
        return new com.google.api.services.tasks.Tasks.Builder(httpTransport, JSON_FACTORY, credential)
                                                              .setApplicationName(APPLICATION_NAME)
                                                              .build();
    }

    /**
     * Search for Opus' task list in user's Google Task account. If unavailable, creates a new one.
     * @return reference to Opus Task List
     * @throws IOException
     * @throws SyncException
     */
    private TaskList getOpusTasks() throws SyncException {
        try {
            TaskLists taskList = getAllTaskListFromGtask();
            List<TaskList> items = taskList.getItems();
            for (TaskList entry: items) {
                if (entry.getTitle().equals(APPLICATION_NAME)) {
                    TaskList tasks = getTaskListFromGtask(entry);
                    return tasks;
                }
            }
            return createOpusTaskList();
        } catch (TokenResponseException e) {
            deauthorize();
            this.isRunning = false;
            throw new SyncException(SYNC_ERROR_LOGIN);
        } catch (IOException e) {
            e.printStackTrace();
            this.isRunning = false;
            throw new SyncException(SYNC_ERROR_CONNECT);
        }
    }

    /**
     * Creates a Opus Task List in user's Google Task account
     * @return
     * @throws SyncException
     */
    private TaskList createOpusTaskList() throws SyncException {
        TaskList opusTaskList = new TaskList();
        opusTaskList.setTitle("Opus");
        try {
            return insertTaskListToGtask(opusTaskList);
        } catch (TokenResponseException e) {
            deauthorize();
            this.isRunning = false;
            throw new SyncException(SYNC_ERROR_LOGIN);
        } catch (IOException e) {
            this.isRunning = false;
            throw new SyncException(SYNC_ERROR_CONNECT);
        }
    }

    /**
     * Converts Model Task Object to Google Task format
     * @param source
     * @return
     */
    private com.google.api.services.tasks.model.Task toGoogleAdaptedTask(Task source) {
        com.google.api.services.tasks.model.Task googleAdaptedTask = new com.google.api.services.tasks.model.Task();

        googleAdaptedTask.setTitle(source.getName().toString());
        googleAdaptedTask.setCompleted(null);
        if (source.getNote().isPresent()) {
            googleAdaptedTask.setNotes(source.getNote().get().toString());
        }
        if (source.getEndTime().isPresent()) {
            Date deadline = Date.from(source.getEndTime().get().dateTime.atZone(ZoneId.systemDefault()).toInstant());
            googleAdaptedTask.setDue(new DateTime(deadline));
        }
        Date updated = Date.from(Instant.now());
        googleAdaptedTask.setUpdated(new DateTime(updated));

        googleAdaptedTask.setStatus(source.getStatus().equals(Flag.COMPLETE)
                                    ? TASK_STATUS_COMPLETE
                                    : TASK_STATUS_INCOMPLETE);
        return googleAdaptedTask;
    }

    /**
     * Deauthorize Opus by deleting the storedCredential file
     */
    public void deauthorize() {
        DATA_STORE_FILE.delete();
    }

    /**
     * Raise a event to notify user when can exception is encountered while syncing
     * @param syncException
     */
    public void raiseSyncExceptionEvent(SyncException syncException) {
        this.syncManager.raiseSyncEvent(syncException);
    }

    //====================Google Task API Service Call Methods==============================================

    /**
     * API service call to insert new task to Google Task
     * @param taskToPush
     * @return result
     * @throws IOException
     */
    private com.google.api.services.tasks.model.Task insertTasktoGtask(Task taskToPush) throws IOException {
        com.google.api.services.tasks.model.Task googleAdaptedTask = toGoogleAdaptedTask(taskToPush);
        com.google.api.services.tasks.model.Task result = service
                                                          .tasks()
                                                          .insert(opusTaskList.getId(), googleAdaptedTask)
                                                          .execute();
        logger.info("---[GTask]: Insert task result:\n" + result.toPrettyString());
        return result;
    }

    /**
     * API service call to delete task from Google Task
     * @param taskToDelete
     * @throws IOException
     */
    private void deleteTaskFromGtask(com.google.api.services.tasks.model.Task taskToDelete) throws IOException {
        service.tasks().delete(opusTaskList.getId(), taskToDelete.getId()).execute();
        logger.info("---[GTask]: Deleting Task - " + taskToDelete.getTitle());
    }

    /**
     * API service call to retrieve Tasks list from Google Task
     * @return
     * @throws IOException
     */
    private Tasks listTasksFromGtask(String taskListId) throws IOException {
        return service.tasks().list(taskListId).execute();
    }

    /**
     * API service call to retrieve all user Task list from Google Task
     * @return
     * @throws IOException
     */
    private TaskLists getAllTaskListFromGtask() throws IOException {
        return service.tasklists().list().execute();
    }

    /**
     * API Service call to retrieve a specific Task List from Google Task
     * @param entry
     * @return
     * @throws IOException
     */
    private TaskList getTaskListFromGtask(TaskList entry) throws IOException {
        return service.tasklists().get(entry.getId()).execute();
    }

    /**
     * API service call to insert a new Task List to Google Task
     * @param taskList
     * @return
     * @throws IOException
     */
    private TaskList insertTaskListToGtask(TaskList taskList) throws IOException {
        return service.tasklists().insert(taskList).execute();
    }
}
