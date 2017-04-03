package seedu.opus.sync;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

import com.google.api.client.auth.oauth2.Credential;
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
import seedu.opus.commons.exceptions.IllegalValueException;
import seedu.opus.model.tag.UniqueTagList;
import seedu.opus.model.task.Name;
import seedu.opus.model.task.Note;
import seedu.opus.model.task.Status;
import seedu.opus.model.task.Status.Flag;
import seedu.opus.model.task.Task;

public class syncServiceGtask implements syncService {
    private static HttpTransport HTTP_TRANSPORT;
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    private static final java.io.File DATA_STORE_DIR = new java.io.File("data/credentials");
    private static final String CLIENT_ID = "972603165301-kls9usprmd2fpaelvrd0937dlcj43g6f.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "07B1QJ73rQECWSoIjAPHMDNG";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Arrays.asList(TasksScopes.TASKS);

    private final Logger logger = LogsCenter.getLogger(syncServiceGtask.class);

    private static final String APPLICATION_NAME = "Opus";
    private static final String TASK_STATUS_COMPLETE = "completed";
    private static final String TASK_STATUS_INCOMPLETE = "needsAction";
    private static final String SYNC_ERROR_MESSAGE = "Exception encountered while syncing";

    private com.google.api.services.tasks.Tasks service;
    private TaskList opusTaskList;

    private ArrayBlockingQueue<Task> addTaskQueue;
    private ArrayBlockingQueue<Task> deleteTaskQueue;
    private ArrayBlockingQueue<Task> updateTaskQueue;

    private LinkedBlockingDeque<List<Task>> taskListDeque;

    private List<Task> pullTaskList;

    private boolean isRunning;

    public syncServiceGtask() {
        addTaskQueue = new ArrayBlockingQueue<Task>(50);
        deleteTaskQueue = new ArrayBlockingQueue<Task>(50);
        updateTaskQueue = new ArrayBlockingQueue<Task>(50);
        taskListDeque = new LinkedBlockingDeque<List<Task>>();
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        this.isRunning = false;
    }

    @Override
    public void start() {
        logger.info("Starting Google Task");
        this.isRunning = true;
        try {
            service = getTasksService();
            opusTaskList = getOpusTasks();
            //Executors.newSingleThreadExecutor().execute(() -> processAddTaskQueue());
            //Executors.newSingleThreadExecutor().execute(() -> processDeleteTaskQueue());
            //Executors.newSingleThreadExecutor().execute(() -> processUpdateTaskQueue());
            //Executors.newSingleThreadExecutor().execute(() -> processUpdateTaskListDeque());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        this.isRunning = false;
    }

    @Override
    public void addTask(Task taskToAdd) {
        assert taskToAdd != null;
        this.addTaskQueue.add(taskToAdd);
    }

    @Override
    public void deleteTask(Task taskToDelete) {
        assert taskToDelete != null;
        this.deleteTaskQueue.add(taskToDelete);
    }

    @Override
    public void updateTask(Task taskToUpdate) {
        assert taskToUpdate != null;
        this.updateTaskQueue.add(taskToUpdate);
    }

    @Override
    public List<Task> pullTaskList() {
        return pullTaskList;
    }

    @Override
    public void updateTaskList(List<Task> taskList) {
        assert taskList != null;
        this.taskListDeque.addFirst(taskList);
    }

    private void processUpdateTaskListDeque() {
        assert service != null;
        assert opusTaskList != null;

        while (isRunning) {
            try {
                List<Task> taskList = this.taskListDeque.takeFirst();
                this.taskListDeque.clear();

                Tasks currentTaskList = service.tasks().list(opusTaskList.getId()).execute();
                if ( !currentTaskList.getItems().isEmpty()) {
                    for (com.google.api.services.tasks.model.Task task : currentTaskList.getItems()) {
                        service.tasks().delete(opusTaskList.getId(), task.getId()).execute();
                    }
                }
                for (Task taskToPush : taskList) {
                    com.google.api.services.tasks.model.Task googleAdaptedTask = toGoogleAdaptedTask(taskToPush);
                    com.google.api.services.tasks.model.Task result = service
                                                                      .tasks()
                                                                      .insert(opusTaskList.getId(), googleAdaptedTask)
                                                                      .execute();
                    logger.info(result.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void pullFromGtask() throws IllegalValueException {
        try {
            List<Task> tasks = new ArrayList<Task>();
            Tasks gtasks = service.tasks().list(opusTaskList.getId()).execute();
            for (com.google.api.services.tasks.model.Task gtask : gtasks.getItems()) {
                tasks.add(toModelType(gtask));
            }
            //pullTaskList.add(tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Credential authorize() throws IOException {
        GoogleClientSecrets.Details clientSecretsDetails = new GoogleClientSecrets.Details();
        clientSecretsDetails.setClientId(CLIENT_ID);
        clientSecretsDetails.setClientSecret(CLIENT_SECRET);
        GoogleClientSecrets clientSecrets = new GoogleClientSecrets().setInstalled(clientSecretsDetails);
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,
                                            JSON_FACTORY, clientSecrets, SCOPES)
                                            .setDataStoreFactory(DATA_STORE_FACTORY)
                                            .build();

        Credential credential = null;
        try {
            credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(APPLICATION_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());

        return credential;
    }

    private com.google.api.services.tasks.Tasks getTasksService() throws IOException {
        Credential credential = authorize();
        return new com.google.api.services.tasks.Tasks.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private TaskList getOpusTasks() {
        try {
            TaskLists taskList = service.tasklists().list().execute();
            List<TaskList> items = taskList.getItems();
            for (TaskList entry: items) {
                if (entry.getTitle().equals(APPLICATION_NAME)) {
                    TaskList tasks = service.tasklists().get(entry.getId()).execute();
                    return tasks;
                }
            }
            return createOpusTaskList();
        } catch (IOException e) {
            System.out.println("failed");
            e.printStackTrace();
            throw new NoSuchElementException();
        }
    }

    public TaskList createOpusTaskList() {
        TaskList opusTaskList = new TaskList();
        opusTaskList.setTitle("Opus");
        try {
            return service.tasklists().insert(opusTaskList).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private com.google.api.services.tasks.model.Task toGoogleAdaptedTask(Task source) {
        com.google.api.services.tasks.model.Task googleAdaptedTask = new com.google.api.services.tasks.model.Task();

        googleAdaptedTask.setTitle(source.getName().toString());
        //googleAdaptedTask.setId(BaseEncoding.base32Hex().omitPadding().encode(source.getId().getBytes()));
        //System.out.println(BaseEncoding.base32Hex().omitPadding().encode(source.getId().getBytes()));
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

    private Task toModelType(com.google.api.services.tasks.model.Task source) throws IllegalValueException {
        try {
            Name name = new Name(source.getTitle());
            String id = source.getId();
            Status status = source.getStatus().equals(TASK_STATUS_COMPLETE)
                            ? new Status(Status.Flag.COMPLETE.toString())
                            : new Status(Status.Flag.INCOMPLETE.toString());
            Note note = new Note(source.getNotes());
            seedu.opus.model.task.DateTime endDate = new seedu.opus.model.task.DateTime(source.getDue()
                                                                                        .toStringRfc3339());
            UniqueTagList tags = new UniqueTagList();
            return new Task(name, null, status, note, null, endDate, tags);
        } catch (IllegalValueException e) {
            throw new IllegalValueException(SYNC_ERROR_MESSAGE);
        }
    }
}
