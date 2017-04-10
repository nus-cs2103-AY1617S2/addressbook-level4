# YATS Yet Another Task Scheduler - Developer Guide

By : `Team T16-B4`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Setting Up](#1-setting-up)
2. [Design](#2-design)
3. [Implementation](#3-implementation)
4. [Testing](#4-testing)
5. [Dev Ops](#5-dev-ops)

* [Appendix A: User Stories](#appendix-a-user-stories)
* [Appendix B: Use Cases](#appendix-b-use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c-non-functional-requirements)
* [Appendix D: Glossary](#appendix-d-glossary)
* [Appendix E : Product Survey](#appendix-e-product-survey)


## 1. Setting up

### 1.1. Prerequisites

1. **JDK `1.8.0_60`**  or later<br>

    > Having any Java 8 version is not enough. <br>
    This app will not work with earlier versions of Java 8.

2. **Eclipse** IDE
3. **e(fx)clipse** UI plugin for Eclipse (Do the steps 2 onwards given in
   [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious))
4. **Buildship Gradle Integration** plugin from the Eclipse Marketplace
5. **M2Eclipse** Maven support plugin for Eclipse (Do the steps in Installation give in
   [this page](http://www.eclipse.org/m2e/))
6. **Checkstyle Plug-in** plugin from the Eclipse Marketplace


### 1.2. Importing the project into Eclipse

0. Fork this repo, and clone the fork to your computer
1. Open Eclipse (Note: Ensure you have installed the **e(fx)clipse** and **buildship** plugins as given
   in the prerequisites above)
2. Click `File` > `Import`
3. Click `Gradle` > `Gradle Project` > `Next` > `Next`
4. Click `Browse`, then locate the project's directory
5. Click `Finish`

  > * If you are asked whether to 'keep' or 'overwrite' config files, choose to 'keep'.
  > * Depending on your connection speed and server load, it can even take up to 30 minutes for the set up to finish
      (This is because Gradle downloads library files from servers during the project set up process)
  > * If Eclipse auto-changed any settings files during the import process, you can discard those changes.

### 1.3. Configuring Checkstyle
1. Click `Project` -> `Properties` -> `Checkstyle` -> `Local Check Configurations` -> `New...`
2. Choose `External Configuration File` under `Type`
3. Enter an arbitrary configuration name e.g. taskmanager
4. Import checkstyle configuration file found at `config/checkstyle/checkstyle.xml`
5. Click OK once, go to the `Main` tab, use the newly imported check configuration.
6. Tick and select `files from packages`, click `Change...`, and select the `resources` package
7. Click OK twice. Rebuild project if prompted

> Note to click on the `files from packages` text after ticking in order to enable the `Change...` button

### 1.4. Troubleshooting project setup

**Problem: Eclipse reports compile errors after new commits are pulled from Git**

* Reason: Eclipse fails to recognize new files that appeared due to the Git pull.
* Solution: Refresh the project in Eclipse:<br>
  Right click on the project (in Eclipse package explorer), choose `Gradle` -> `Refresh Gradle Project`.

**Problem: Eclipse reports some required libraries missing**

* Reason: Required libraries may not have been downloaded during the project import.
* Solution: [Run tests using Gradle](UsingGradle.md) once (to refresh the libraries).


## 2. Design

### 2.1. Architecture

<img src="images/Architecture.png" width="600"><br>
_Figure 2.1.1 : Architecture Diagram_

The **_Architecture Diagram_** given above explains the high-level design of the App.
Given below is a quick overview of each component.

> Tip: The `.pptx` files used to create diagrams in this document can be found in the [diagrams](diagrams/) folder.
> To update a diagram, modify the diagram in the pptx file, select the objects of the diagram, and choose `Save as picture`.

`Main` has only one class called [`MainApp`](../src/main/java/org/teamstbf/yats/MainApp.java). It is responsible for,

* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup method where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.
Two of those classes play important roles at the architecture level.

* `EventsCenter` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design)
* `LogsCenter` : Used by many classes to write log messages to the App's log file.

The rest of the App consists of four components.

* [**`UI`**](#ui-component) : The UI of the App.
* [**`Logic`**](#logic-component) : The command executor.
* [**`Model`**](#model-component) : Holds the data of the App in-memory.
* [**`Storage`**](#storage-component) : Reads data from, and writes data to, the hard disk.

Each of the four components

* Defines its _API_ in an `interface` with the same name as the Component.
* Exposes its functionality using a `{Component Name}Manager` class.

For example, the `Logic` component (see the class diagram given below) defines it's API in the `Logic.java`
interface and exposes its functionality using the `LogicManager.java` class.<br>
<img src="images/LogicClassDiagram.png" width="800"><br>
_Figure 2.1.2 : Class Diagram of the Logic Component_

#### Events-Driven nature of the design

The _Sequence Diagram_ below shows how the components interact for the scenario where the user issues the
command `delete 1`.

<img src="images\SDforDeleteTask.png" width="800"><br>
_Figure 2.1.3a : Component interactions for `delete 1` command (part 1)_

>Note how the `Model` simply raises a `TaskManagerChangedEvent` when the Task Manager data are changed,
 instead of asking the `Storage` to save the updates to the hard disk.

The diagram below shows how the `EventsCenter` reacts to that event, which eventually results in the updates
being saved to the hard disk and the status bar of the UI being updated to reflect the 'Last Updated' time. <br>
<img src="images\SDforDeleteTaskEventHandling.png" width="800"><br>
_Figure 2.1.3b : Component interactions for `delete 1` command (part 2)_

> Note how the event is propagated through the `EventsCenter` to the `Storage` and `UI` without `Model` having
  to be coupled to either of them. This is an example of how this Event Driven approach helps us reduce direct
  coupling between components.

The sections below give more details of each component.

### 2.2. UI component

<img src="images/UiClassDiagram.png" width="800"><br>
_Figure 2.2.1 : Structure of the UI Component_

**API** : [`Ui.java`](../src/main/java/org/teamstbf/yats/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `TaskListPanel`,
`StatusBarFooter`, `MultiViewPanel` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.
The `MultiViewPanel` is made up of two tabs, which includes `Done Task View` and `Calendar View`. 

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/org/teamstbf/yats/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component,

* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raised from various parts of the App and updates the UI accordingly.
* Elaborating on the `MultiViewPanel`: 
* 1. `MultiViewPanel` shows a list of done tasks by allowing the internal `filteredEvents` list to be duplicated into two additional
list: `calendarList` and `doneList`. Both of these list synchronize to `filteredEvents` list whenever there are changes to the primary
list.
* 2. However, a known issue that arises is when there are more than one tasks with the same name in the primary list and secondary list. When
executing a `delete` command, the command deletes the task with the earliest end time regardless of status.

### 2.3. Logic component

<img src="images/LogicClassDiagram.png" width="800"><br>
_Figure 2.3.1 : Structure of the Logic Component_

**API** : [`Logic.java`](../src/main/java/org/teamstbf/yats/logic/Logic.java)

1. `Logic` uses the `Parser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a task) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
 API call.<br>
<img src="images/DeleteTaskSdForLogic.png" width="800"><br>
_Figure 2.3.1 : Interactions Inside the Logic Component for the `delete 1` Command_

### 2.4. Model component

<img src="images/ModelClassDiagram.png" width="800"><br>
_Figure 2.4.1 : Structure of the Model Component_

**API** : [`Model.java`](../src/main/java/org/teamstbf/yats/model/Model.java)

The `Model`,

* stores a `UserPref` object that represents the user's preferences.
* stores the Task Manager data.
* exposes a `UnmodifiableObservableList<ReadOnlyEvent>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

### 2.5. Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>
_Figure 2.5.1 : Structure of the Storage Component_

**API** : [`Storage.java`](../src/main/java/org/teamstbf/yats/storage/Storage.java)

The `Storage` component,

* can save `UserPref` objects in json format and read it back.
* can save the Task Manager data in xml format and read it back.

### 2.6. Common classes

Classes used by multiple components are in the `org.teamstbf.yats.commons` package.

## 3. Implementation

### 3.1. Logging

We are using `java.util.logging` package for logging. The `LogsCenter` class is used to manage the logging levels
and logging destinations.

* The logging level can be controlled using the `logLevel` setting in the configuration file
  (See [Configuration](#configuration))
* The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to
  the specified logging level
* Currently log messages are output through: `Console` and to a `.log` file.

**Logging Levels**

* `SEVERE` : Critical problem detected which may possibly cause the termination of the application
* `WARNING` : Can continue, but with caution
* `INFO` : Information showing the noteworthy actions by the App
* `FINE` : Details that is not usually noteworthy but may be useful in debugging
  e.g. print the actual list instead of just its size

### 3.2. Configuration

Certain properties of the application can be controlled (e.g App name, logging level) through the configuration file
(default: `config.json`):

### 3.3 Undo/Redo Command

The Undo and Redo commands were implemented by saving immutable states of the TaskManager dataset into two stacks, an undo stack
and a redo stack. Every time a action is taken that will mutate the data stored inside the TaskManager, a state of the previous data is saved by through
copying the TaskManager object and pushing this into an Undo stack. When the undo command is taken, the ModelManager class pops the
last TaskManager state from the undo stack and replaces the current data with this state. Before it replaces the data, the ModelManager \
class also saves the current data as a state and pushes this onto the redo stack. This way, the undo can be reversed by popping from the redo stack.
Two implementation decisions were made - the first is that everytime a new action is taken (not undo or redo), the redo stack will be cleared or else there might be
logical errors in state transformation (the redo stack will contain old states). The second is that there should be no more than 20 saved states of the
task manager, and if there are 20 saved states, 10 of the earliest saved states will be deleted.

### 3.4 Scheduling Command
In YATS, we implement a schedule command to automatically schedule tasks based on available timeslots. Scheduling is made based on the following
constraints - i) the scheduled time must not overlap with any other event in the list ii) it must be between 8am and 6pm iii) must not be on a saturday or
sunday The algorithm. These constraints were implemented as we wanted to ensure a very specific
use case for schedule - we wanted to allow the user to schedule tasks automatically into their workday. This allows them
to quickly slot their ad-hoc events into their own timetable, without worrying about when to put each event. To accomplish scheduling,
we first searches for open intervals in the list of events. Open intervals are intervals not taken up by any event, and thus can be used for scheduling.
We then have to check if the open interval is appropriate for scheduling the event based on the constraints of 8am to 6pm, and that it is on a weekday.
First, we describe the method of finding open intervals. The list is sorted by start time and then we iterate through this sorted list,
keeping the max of its accompanying end time saved in a variable and checking if the maximum end time is smaller than the next events start time. 
If current maximum end time at the current event is smaller than the start time of the next event, it is then provable that this is an open interval.
This is because there is no event with an end time after this interval (since we took the max of all preceding end times of the first start times), 
and no event with a start time or end time before this interval (since the array is sorted by start time, and every events end time must be later 
than its start time). Thus, this must be an open interval which we can then check whether it is appropriately sized and
the correct day for scheduling. This problem is complex as because we are looking for valid timings between 8am and 6pm, and the boundaries of an open interval 
span across multiple days. To solve this problem, we first break down the possible times of boundaries into 3 cases, i)
Case 1 - Less than 10 Hours , ii) Case 2 - More than 34 Hours , iii) Case 3 - In between 10 Hours and 34 Hours. In case i), because of the time period from 6pm to 8am is 10 Hours, 
any timing less than 10 hours must only have one valid day 
for scheduling. The theoretical upper bound for a single day timeslot is actually 14 hours, but in this case we do not what to deal with wrapped timings and
thus we pick a boundary of 10 hours. Thus, we can check just that day alone to see if there is a valid time slot there. Case ii - 34 hours)
Next, if the timing is more than 34 hours, there must be a valid 10 hour block where no event exists. This is because 34 hours encompasses at
least one complete time block, and because we know this is an open interval, then by pigeonhole principle there must be an empty 10 hour
block in a 34 hour range. Finally, the most complex case is between 10 Hours and 34 Hours. Here, we must first check if there are one or two
valid days to analyse. Then, we find the appropriate boundaries for one or two days, and check if either of these are valid times for scheduling.
Lastly, our final check is that the scheduled day is not a saturday or a sunday. Thus, from these set of rules, we can find an appropriately sized open interval with the 
constraints of being within 8am to 6pm, be on a saturday and sunday and not overlap with any other timeslot.

## 4. Testing

Tests can be found in the `./src/test/java` folder.

**In Eclipse**:

* To run all tests, right-click on the `src/test/java` folder and choose
  `Run as` > `JUnit Test`
* To run a subset of tests, you can right-click on a test package, test class, or a test and choose
  to run as a JUnit test.

**Using Gradle**:

* See [UsingGradle.md](UsingGradle.md) for how to run tests using Gradle.

We have two types of tests:

1. **GUI Tests** - These are _System Tests_ that test the entire App by simulating user actions on the GUI.
   These are in the `guitests` package.

2. **Non-GUI Tests** - These are tests not involving the GUI. They include,
   1. _Unit tests_ targeting the lowest level methods/classes. <br>
      e.g. `org.teamstbf.yats.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units
     (those code units are assumed to be working).<br>
      e.g. `org.teamstbf.yats.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as
      how the are connected together.<br>
      e.g. `org.teamstbf.yats.logic.LogicManagerTest`

#### Headless GUI Testing
Thanks to the [TestFX](https://github.com/TestFX/TestFX) library we use,
 our GUI tests can be run in the _headless_ mode.
 In the headless mode, GUI tests do not show up on the screen.
 That means the developer can do other things on the Computer while the tests are running.<br>
 See [UsingGradle.md](UsingGradle.md#running-tests) to learn how to run tests in headless mode.

### 4.1. Troubleshooting tests

 **Problem: Tests fail because NullPointException when AssertionError is expected**

 * Reason: Assertions are not enabled for JUnit tests.
   This can happen if you are not using a recent Eclipse version (i.e. _Neon_ or later)
 * Solution: Enable assertions in JUnit tests as described
   [here](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option). <br>
   Delete run configurations created when you ran tests earlier.

## 5. Dev Ops

### 5.1. Build Automation

See [UsingGradle.md](UsingGradle.md) to learn how to use Gradle for build automation.

### 5.2. Continuous Integration

We use [Travis CI](https://travis-ci.org/) and [AppVeyor](https://www.appveyor.com/) to perform _Continuous Integration_ on our projects.
See [UsingTravis.md](UsingTravis.md) and [UsingAppVeyor.md](UsingAppVeyor.md) for more details.

### 5.3. Publishing Documentation

See [UsingGithubPages.md](UsingGithubPages.md) to learn how to use GitHub Pages to publish documentation to the
project site.

### 5.4. Making a Release

Here are the steps to create a new release.

 1. Generate a JAR file [using Gradle](UsingGradle.md#creating-the-jar-file).
 2. Tag the repo with the version number. e.g. `v0.1`
 2. [Create a new release using GitHub](https://help.github.com/articles/creating-releases/)
    and upload the JAR file you created.

### 5.5. Converting Documentation to PDF format

We use [Google Chrome](https://www.google.com/chrome/browser/desktop/) for converting documentation to PDF format,
as Chrome's PDF engine preserves hyperlinks used in webpages.

Here are the steps to convert the project documentation files to PDF format.

 1. Make sure you have set up GitHub Pages as described in [UsingGithubPages.md](UsingGithubPages.md#setting-up).
 1. Using Chrome, go to the [GitHub Pages version](UsingGithubPages.md#viewing-the-project-site) of the
    documentation file. <br>
    e.g. For [UserGuide.md](UserGuide.md), the URL will be `https://<your-username-or-organization-name>.github.io/main/docs/UserGuide.html`.
 1. Click on the `Print` option in Chrome's menu.
 1. Set the destination to `Save as PDF`, then click `Save` to save a copy of the file in PDF format. <br>
    For best results, use the settings indicated in the screenshot below. <br>
    <img src="images/chrome_save_as_pdf.png" width="300"><br>
    _Figure 5.4.1 : Saving documentation as PDF files in Chrome_

### 5.6. Managing Dependencies

A project often depends on third-party libraries. For example, YATS depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>


## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the App
`* * *` | user | Add a task with a name and deadline and then schedule it myself | Know when I need to do this task
`* * *` | user | Add a task with just a title | Add tasks in quick succession and edit the deadlines at a later time
`* * *` | user | Search for the task that I would want to schedule | Find out whether the task has already been scheduled
`* * *` | user | Search for information (using any string) about a current task which will display all the relevant info about the task | Find the task without looking through the whole list
`* * *` | user | Edit the deadline, name and schedule of any task | Update any task as required, as well as marking it as done and deleting it
`* * *` | user | Undo my actions | revert any changes done accidentally.
`* * *` | user | Redo my actions | revert any undo command.
`* * *` | user | Mark task as done based on index | Clear done tasks if needed
`* * *` | user | List all tasks by deadlines | View which tasks needs to be completed first
`* * *` | user | Change my save location | I can choose where my data file is saved
`* * ` | user | Mark task as done based name, or any unique identifying string for that task | Clear done tasks if needed
`* * ` | user | List all tasks based on importance | View which tasks have higher priorities
`* * ` | user | Schedule a recurring (daily/weekly/monthly..) | Not add the recurring tasks manually
`* * ` | user | Get a calendar view list of my current blocked out times and what I have to do | Know what else I can add
`* * ` | user | Set alarms for certain schedules | Be reminded of deadlines of my tasks
`* * ` | user | Schedule my tasks with constraints | Avoid scheduling tasks during my works time and leave break time in between tasks
`* *` | user | Add subtasks into my main task | Have a more structured view of the main task
`* *` | user | Star my tasks | Mark out the more urgent tasks to be done
`* *` | user | Ask the task manager to schedule my tasks | Just enter the tasks with a deadline and decide the scheduling later
`* *` | user | Add descriptions to my task | have additional details of my tasks
`* *` | user | Mark the task as done/require follow-up | Have an up-to-date record of the my tasks
`* *` | user | Group my tasks together with a common tag | Organized my tasks according to their groups in the scheduler
`* *` | user | Have pop-up notifications of near deadlines | Be reminded of tasks with deadlines approaching
`* *` | user | Have heads-up display of tasks that are about to due | Be reminded of tasks to be done
`* *` | advanced user| Sort my tasks according to deadlines, dates or title | Have a better overviews of tasks scheduled
`* *` | advanced user | Use shorter versions of commands/Flexi-command | Type commands easier and faster
`* *` | advanced user | Keep a list of done task, marked as done(strike-out) | Keep track of what I have already done
`* *` | advance user | Clear all my done tasks | Clear done tasks from scheduler
`* *` | advanced user | Collapse my tasks into groups in the listed view | Have a clearer overview of all my task groups
`* ` | user | Get suggestions when a task should be scheduled | Automatically click on which date and time I would like it scheduled
`* ` | user | Specify locations that are linked to Google Maps | Know the best way to get there from my current location (Workplace)
`* ` | user | Automatically reschedule a task I am supposed to do now | No worry about when to schedule the task
`* ` | user | Have my favourite calendar integrated to the program | Import my existing schedules to the task manager
`* ` | user | Mark a task as done with one button click | Save time when marking tasks as done
`* ` | user | Autocomplete my typing | Type commands/descriptions faster
`* ` | user | Synchronize with multiple devices | Export and import my task list among different devices for easier access
`* ` | user | Have tasks inferred from email's contents | Add tasks with details more easily


## Appendix B : Use Cases

(For all use cases below, the **System** is the `TaskScheduler` and the **Actor** is the `user`, unless specified otherwise)


#### Use case: Add Non-Recurring Task With Deadline

**MSS**

1. User requests to add a task with a title, and deadline, optional description, optional description, optional tags
2. TaskManager creates a task
3. TaskManager shows the added task's details and that the task was successfully added <br>
Use case ends.

**Extensions**

1a. User specifies a deadline with date and time

> 1a1. TaskManager stores the deadline <br>
> Use case resumes at step 2

2a. User specifies either the date or the time of deadline

> 2a1. TaskManager creates a Task object with a current date or time <br>
> Use case resumes at step 3


#### Use case: Add Non-recurring Task With Start and End Time

**MSS**

1. User requests to add a task with a title, either a start time or an end time or both, and an optional description, optional location and optional tags
2. TaskManager creates a task
3. TaskManager shows the added task's details and that the event was successfully added <br>
Use case ends.

**Extensions**

1a. User specifies either a start time or end time
> 2a1. TaskManager assumes that the duration is 2 hours and fill the start or end time accordingly <br>
> Use case resumes at step 2


#### Use case: Add Recurring Task

**MSS**

1. User requests to add a task with a title and a periodicity, either a start time or an end time or both, or a deadline, and an optional description, optional location and optional tags
2. TaskManager creates a task
3. TaskManager shows the added task's details and that the event was successfully added <br>
Use case ends.

**Extensions**

1a. User specifies either a start time or end time
> 2a1. TaskManager assumes that the duration is 2 hours and fill the start or end time accordingly <br>
> Use case resumes at step 2

1b. User does not specify any time
> 2a1. TaskManager assumes that the recurrence starts now<br>
> Use case resumes at step 2


#### Use case: Mark Task As Done (by finding)

**MSS**

1. User requests to mark task as done by providing specified title/tag/date
2. TaskManager finds a unique task that is identified by that string using the Search method and marks it as done, and prints the task that was marked as done <br>
Use case ends.

**Extensions**

1a. User inputs a number
> 1a1. Task Manager marks that as done, and prints the task out again <br>
  Use case ends

2a. A list of unique tasks was found
> 2a1. TaskManager shows a lists of those tasks, and asks the user which task they would like to mark as done <br>
> Use case ends

2b. No list of unique tasks was found
> 2b1. TaskManager tells user that no tasks was found, and they can enter another string or use the list function to mark task as done. <br>
> Use case resumes at step 1 <br>


#### Use case: Mark Task As Done (by index)

**MSS**

1. User request to list task by providing specified title/tag/date
2. TaskManager shows a list of all tasks added within specified title/tag/date in order of date & time from first to last
3. User enters Mark Task as Done with index
4. TaskManager finds the task that is identified by that index and marks it as done <br>
Use case ends.

**Extensions**

4a. Index is not found in the TaskManager List
> 4a1. TaskManager reports that no task was found with that index.  <br>
> Use case resumes at step 3 <br>


#### Use case: Delete task

**MSS**

1. User requests to list tasks
2. TaskManager shows a list of tasks
3. User requests to delete a task with specified index in the list
4. TaskManager deletes the task <br>
Use case ends.

**Extensions**

2a. The list is empty
> Use case ends

3a. The given index is invalid
> 3a1. TaskManager shows an error message  <br>
> Use case resumes at step 2 <br>


#### Use case: List Task (by finding)

**MSS**

1. User request to list task/event by providing specified title/tag/date/type/completeness
2. TaskManager shows a list of all tasks/events added within specified title/tag/date/type/completeness in order of date & time from first to last
3. TaskManager provides options for User to perform commands from there
4. User chooses exit option <br>
Use case ends.

**Extensions**

2a. The list is empty
> 2a1. TaskManager shows List is empty message <br>
> Use case ends <br>


#### Use case: List Task (Everything)

**MSS**

1. User requests to list tasks/events
2. TaskManager shows a list of all tasks/events added in order of date from first to last
3. TaskManager provides options for User to perform commands from there
4. User chooses exit option <br>
Use case ends.

**Extensions**

2a. The list is empty
> 2a1. TaskManager shows List is empty message <br>
> Use case ends <br>


#### Use case: Edit Task

**MSS**

1. User request to list tasks
2. TaskManager shows list of existing tasks
3. User requests to edit a task with a specified index in the list. With optional new title, either a start time or an end time or both, and optional description, optional location and optional tags
12. TaskManager updates variables of the task <br>
Use case ends.

**Extensions**

2a. The list is empty
> 2a1. TaskManager shows 'List is empty' message <br>
> Use case ends

3a. The given index is invalid
> 3a1. TaskManager shows an error message <br>
> Use case resumes at step 3

3b. The given parameters are invalid
> 3a1. TaskManager shows an error message telling user the legal format<br>
> Use case resumes at step 3

3b. The given tag flag is present and empty
> 3a1. TaskManager deletes all tags of that task<br>
> Use case resumes at step 3


#### Use case: Clear Done Tasks

**MSS**

1. User requests to clear done tasks
2. TaskManager goes through list to find done tasks
3. TaskManager deletes all done tasks
4. TaskManager shows User the list of deleted done tasks <br>
Use case ends.

**Extensions**

2a. The list is empty
> 2a1. TaskManager shows 'No done tasks' message <br>
> Use case ends. <br>


#### Use case: Undo Last Command

**MSS**

1. User requests to undo last command
2. TaskManager retrieves last saved state from storage <br>
Use case ends.

**Extensions**

2a. No last saved state
> Use case ends. <br>


#### Use case: Redo Last Command

**MSS**

1. User requests to redo last command
2. TaskManager retrieves next saved state from storage <br>
Use case ends.

**Extensions**

2a. No next saved state
> Use case ends. <br>


#### Use case: Get Help

**MSS**

1. User requests help
2. TaskManager prints help <br>
Use case ends.


#### Use case: Exit Programme

**MSS**

1. User requests to exit programme
2. TaskManager saves files and closes programme <br>
Use case ends.


## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 tasks without a noticeable sluggishness in performance for typical usage.



## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Private contact detail

> A contact detail that is not meant to be shared with others


## Appendix E : Product Survey

**WunderList**

Author: 6 Wunderkinder GmbH

Pros:
* Able to start creating tasks without going through any prompts
* Able to access task manager offline and tasks will be updated when the program goes online
* Able to create To-do tasks with or without deadlines
* Able to mark tasks as done
* Tasks not completed are shown in a list which is shown in the main window

Cons:
* To add more details about the tasks such as time/date/description, the user has to right click the task in order to add in more details
* No calendar view to have an overall sense of the tasks scheduled
* Not able to look for suitable slots to schedule item
* Not able to postpone the task listed
* Not able to 'block' multiple slots when the exact timing of a task is uncertain

**Trello**

Author: Trello, Inc.

Pros:
* Have basic functions such as adding tasks with title, description, attachments, time, members and many other details. Deadlines are optional.
* Very adapted to group collaboration on same project

Cons:
* Chronological order is not intuitive since calendar view is not available by default
* Does not show available time blocks for users to schedule his tasks
* Does not schedule tasks automatically, users have to decide when to do what
* No block-release of time slots for task scheduling
* Does not have an offline desktop version
* Mainly clicking, not command line interface

**Google Tasks - based on Google Mail Task Manager**

Author: Google

Pros:
* Minimalist GUI, can create new tasks by keying in sentence as task
* Can edit tasks dynamically on webpage
* Can specify deadlines for tasks
* Can mark task as done with one click
* Can clear all done tasks with one click
* Can sort tasks according to date added as well as deadline due
* Able to synchronise with other products

Cons:
* No-frills approach means a lot of features, including blocking out dates, specifying event timings and postponing items
* Chronological order is not intuitive since calendar view is not available by default
* Does not show available time blocks for users to schedule his tasks
* Does not schedule tasks automatically, users have to decide when to do what
* No block-release of time slots for task scheduling
* Does not have an offline desktop version

**myHomework**

Author: Rodrigo Neri

Pros:
* Minimalist GUI
* Listing of tasks are separated clearly and orderly into upcoming, late and done
* Can specify deadlines for tasks
* Can mark tasks as done with a single click
* Able to sync across multiple platforms and devices with the app installed on them
* One click to access the new event tab and from there able to use keyboard to input the details
* Option to view in calendar mode if desired
* Able to add tags to the tasks while adding them
* Able to set priority level for tasks added
* Able to set tasks into recurring task

Cons:
* Input mainly through GUI and mouse instead of keyboard
* No block release of time slots for task scheduling
* Required to click quite a few times to navigate through the app in order to get to the buttons to clear all done tasks/events in one shot
* Allows clashes in time slots of different events

**Mail and Calendar**

Author: Windows

Pros:
* Able to have a list schedules/tasks for the day in list view
* Can be switched to week/month view
* Able to work in an offline environment and can be updated once the program has internet access
* Can be started up by pressing on the 'Windows' key and typing 'Calendar'/'Mail'
* Only for Windows 8/10
* Able to visualise whether there is a conflict in timeslot
* Able to postpone the event

Cons:
* Cannot schedule tasks with deadline
* Though could be worked around by setting a dummy time slot or marking it as an all day event
* Not able to mark the item as done
* Unable to 'block' time slots
* Cannot enter the event in a 'one shot' approach
