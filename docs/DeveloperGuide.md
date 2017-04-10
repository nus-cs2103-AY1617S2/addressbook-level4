# ProcrastiNomore (Task Manager Application) - Developer Guide

By : `Team ProcrastiNomore`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Setting Up](#1-setting-up)
2. [Design](#2-design)
3. [Implementation](#3-implementation)
4. [Testing](#4-testing)
5. [Dev Ops](#5-dev-ops)

* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b--use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Glossary](#appendix-d--glossary)
* [Appendix E : Product Survey](#appendix-e--product-survey)


## 1. Setting up

### 1.1. Prerequisites

1. **JDK `1.8.0_60`**  or later<br>

    > Having any Java 8 version is not enough. <br>
    This app will not work with earlier versions of Java 8.

2. **Eclipse** IDE
3. **e(fx)clipse** plugin for Eclipse (Do the steps 2 onwards given in
   [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious))
4. **Buildship Gradle Integration** plugin from the Eclipse Marketplace
5. **Checkstyle Plug-in** plugin from the Eclipse Marketplace


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

`Main` has only one class called [`MainApp`](../src/main/java/seedu/taskmanager/MainApp.java). It is responsible for,

* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup method where necessary.

[**`Commons`**](#26-common-classes) represents a collection of classes used by multiple other components.
Two of those classes play important roles at the architecture level.

* `EventsCenter` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design)
* `LogsCenter` : Used by many classes to write log messages to the App's log file.

The rest of the App consists of four components.

* [**`UI`**](#22-ui-component) : The UI of the App.
* [**`Logic`**](#23-logic-component) : The command executor.
* [**`Model`**](#24-model-component) : Holds the data of the App in-memory.
* [**`Storage`**](#25-storage-component) : Reads data from, and writes data to, the hard disk.

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

<img src="images\ComponentInteractionforDeleteTask_p1.png" width="800"><br>
_Figure 2.1.3a : Component interactions for `delete 1` command (part 1)_

>Note how the `Model` simply raises a `TaskManagerChangedEvent` when the Task Manager data are changed,
 instead of asking the `Storage` to save the updates to the hard disk.

The diagram below shows how the `EventsCenter` reacts to that event, which eventually results in the updates
being saved to the hard disk and the status bar together with the 3 task panels of the UI being updated to reflect the 'Last Updated' time and the latest list of tasks respectively. <br>
<img src="images\ComponentInteractionforDeleteTask_p2.png" width="800"><br>
_Figure 2.1.3b : Component interactions for `delete 1` command (part 2)_

> Note how the event is propagated through the `EventsCenter` to the `Storage` and `UI` without `Model` having
  to be coupled to either of them. This is an example of how this Event Driven approach helps us reduce direct
  coupling between components.

The sections below give more details of each component.

### 2.2. UI component

Author: Alvin Loh

<img src="images/UiClassDiagram_Taskmanager.png" width="800"><br>
_Figure 2.2.1 : Structure of the UI Component_

**API** : [`Ui.java`](../src/main/java/seedu/taskmanager/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `EventTaskListPanel`,
`StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/taskmanager/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component,

* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raised from various parts of the App and updates the UI accordingly.

### 2.3. Logic component

Author: Alvin Loh

<img src="images/LogicClassDiagram.png" width="800"><br>
_Figure 2.3.1 : Structure of the Logic Component_

**API** : [`Logic.java`](../src/main/java/seedu/taskmanager/logic/Logic.java)

1. `Logic` uses the `Parser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a task) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
 API call.<br>
<img src="images/DeleteTaskLogicComponent.png" width="800"><br>
_Figure 2.3.2 : Interactions Inside the Logic Component for the `delete 1` Command_

The Command section of the Logic component utilises the Open-Closed principle where by it is very open for extension as one can create another type of command easily via Parent Abstract class [`Command`](../src/main/java/seedu/taskmanager/logic/commands/Command.java). Thus one can easily add in another new command without the need to modify current codes and hence achieves "Open for extensions and closed for modification".

<img src="images/Open-Close.png" width="800"><br>
_Figure 2.3.3 : Open-Close Principle within the Logic Component for under commands section_

### 2.4. Model component

Author: Alvin Loh

<img src="images/ModelClassDiagram_TaskManager.png" width="800"><br>
_Figure 2.4.1 : Structure of the Model Component_

**API** : [`Model.java`](../src/main/java/seedu/taskmanager/model/Model.java)

The `Model`,

* stores a `UserPref` object that represents the user's preferences.
* stores the Task Manager data.
* exposes a `UnmodifiableObservableList<ReadOnlyTask>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.
* holds history of all data changes done within run-time.

#### Undo and Redo changes in task manager
`undoTaskManager` is a stack that stores instances of `TaskManager` whenever changes are made to the data of the application.
These changes can be undone if programme is still running within the same session. Instances of `TaskManager` is only stored in runtime and Undo actions can only be called for actions done within the same session.
This supports multiple undos.
`redoTaskManager` is a stack that stores instances of `TaskManager` whenever the undo function is called. This allows for undone actions to be reloaded again, allowing for data to be reloaded again multiple times if no new changes are made after actions has been undone.

A new `TaskManager` is created based on the instance saved within these stacks and replaces the current `TaskManager` to load previous versions of data.
Actions that changes `TaskManager` includes e.g. `Add`, `Delete`, `Update`, `Clear` etc. Changing the view of the application for instance `Search` or `List` does not constitute to changing the data of the `TaskManager`.

### 2.5. Storage component

Author: Alvin Loh

<img src="images/StorageClassDiagram_TaskManager.png" width="800"><br>
_Figure 2.5.1 : Structure of the Storage Component_

**API** : [`Storage.java`](../src/main/java/seedu/taskmanager/storage/Storage.java)

The `Storage` component,

* can save `UserPref` objects in json format and read it back.
* can save the Task Manager data in xml format and read it back.

### 2.6. Common classes

Classes used by multiple components are in the `seedu.taskmanager.commons` package.

These classes keeps commonly used methods by the various methods together to facilitate code maintenance and promote code
reuse. One such class is the [DateTimeUtil](../src/main/java/seedu/taskmanager/commons/util/DateTimeUtil.java) class.

<img src="images/DateTimeUtil_TaskManager.png" width="400"><br>
_Figure 2.6.1 : Dependency of the DateTimeUtil Class_

This design structure allows for easy maintenance of codes that are related to Date and Time and reduces duplicate codes in the
logic component.

## 3. Implementation

### 3.1. Logging

We are using `java.util.logging` package for logging. The `LogsCenter` class is used to manage the logging levels
and logging destinations.

* The logging level can be controlled using the `logLevel` setting in the configuration file
  (See [Configuration](#32-configuration))
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
      e.g. `seedu.taskmanager.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units
     (those code units are assumed to be working).<br>
      e.g. `seedu.taskmanager.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as
      how the are connected together.<br>
      e.g. `seedu.taskmanager.logic.LogicManagerTest`

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
    e.g. For [UserGuide.md](UserGuide.md), the URL will be `https://<your-username-or-organization-name>.github.io/ProcrastiNomore/docs/UserGuide.html`.
 1. Click on the `Print` option in Chrome's menu.
 1. Set the destination to `Save as PDF`, then click `Save` to save a copy of the file in PDF format. <br>
    For best results, use the settings indicated in the screenshot below. <br>
    <img src="images/chrome_save_as_pdf.png" width="300"><br>
    _Figure 5.4.1 : Saving documentation as PDF files in Chrome_

### 5.6. Managing Dependencies

A project often depends on third-party libraries. For example, Task Manager depends on the
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
`* * *` | user | create a new task |
`* * *` | user | delete an existing task | remove entries that I no longer need
`* * *` | user | search a task by keyword | locate details of task without having to go through the entire list
`* * *` | user | view an existing task |
`* * *` | user | undo/redo task | easily remove my mistakes
`* * *` | user | sort all task | list all tasks alphabetically/by date dates/priorities
`* * *` | user | prioritise task | know which task has a high importance
`* *` | user | change save location | allow the files to be saved in another location or a cloud
`* *` | user | mark task as completed or uncompleted | know which tasks to do next
`* *` | user | see due dates of task | better plan what tasks I should do
`* *` | user | block and finalize time slots | allow better timetable planning for the future
`* *` | user | set reminders | be informed when the next task is due
`* *` | user | view history | view what tasks have been completed
`* *` | user | autosave |
`*` | user | make task recurring |
`*` | user | make my own settings | customize ProcrastiNomore to my own liking


{More to be added}

## Appendix B : Use Cases

(For all use cases below, the **System** is the `TaskManager` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Create task

**MSS**

1. User requests to create a new task
2. TaskManager creates the task<br>
Use case ends.

**Extensions**

2a. The task name is the same as previous task entry name

> 2a1. TaskManager shows a confirmation message<br>
> 2a2a. User accepts confirmation
> 2a3a. TaskManager creates task
> Use case ends
> 2a2b. User rejects confirmation
> Use case ends

#### Use case: Read task

**MSS**

1. User requests to list tasks
2. TaskManager shows a list of tasks
3. User requests to read a specific task in the list
4. TaskManager shows more information on the task<br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. TaskManager shows an error message <br>
> Use case resumes at step 2

#### Use case: Update task

**MSS**

1. User requests to list tasks
2. TaskManager shows a list of tasks
3. User requests to update a specific task in the list
4. TaskManager displays the old details of the task to the user and request for new details.
5. User inputs new details
6. TaskManager request for confirmation for new details to replace old details.
7. User confirms the update of the task
8. TaskManager updates the task<br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. TaskManager shows an error message <br>
> Use case resumes at step 2

4a. User rejected the confirmation

> 4a1. TaskManager request to continue updating
> 4a2a. User confirms to continue updating
> Use case resumes at step 4
> 4a2b. User rejects to continue updating
> Use case ends

#### Use case: Delete task

**MSS**

1. User requests to list tasks
2. TaskManager shows a list of tasks
3. User requests to delete a specific task in the list
4. TaskManager request for confirmation to delete task
5. User confirms to delete
6. TaskManager deletes the task<br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. TaskManager shows an error message <br>
 Use case resumes at step 2

4a. User rejected confirmation

> Use case ends

#### Use case: Undo task

**MSS**

1. User carries out a specific successful command
2. TaskManager carries out the command successfully
3. User requests to undo the command
4. TaskManager returns to the previous state before the command was called<br>
Use case ends.

**Extensions**

2a. The user just started the programme and did not carry out any command before carrying out the undo function

> Use case ends

#### Use case: Redo task

**MSS**

1. User requests to undo his previous command command
2. TaskManager undos his previous command
3. User requests to redo the command
4. TaskManager redoes the command<br>
Use case ends.

**Extensions**

2a. The user did not carry out an undo function previously

> Use case ends

#### Use case: Comment on task

**MSS**

1. User requests to list tasks
2. TaskManager shows a list of tasks
3. User requests to add a comment on a specific task in the list
4. TaskManager adds the comment to the specified task<br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. TaskManager shows an error message <br>
 Use case resumes at step 2

#### Use case: Change save location

**MSS**

1. User requests change the save location
2. TaskManager changes the save location<br>
Use case ends.

**Extensions**

2a. The given index is file location is invalid

> 2a1. TaskManager shows an error message <br>
 Use case ends

#### Use case: Search tasks

**MSS**

1. User requests to search tasks with search term provided
2. TaskManager lists the tasks to the specified search terms<br>
Use case ends.

**Extensions**

2a. There are no tasks to search

> Use case ends

2b. The given search terms are invalid

> 2b1. TaskManager shows an error message <br>
 Use case resumes at step 1

#### Use case: Sort tasks

**MSS**

1. User requests to list tasks
2. TaskManager shows a list of tasks
3. User requests to sort tasks in the list
4. TaskManager sorts the list of tasks<br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given sorting criteria is invalid

> 3a1. TaskManager shows an error message <br>
 Use case resumes at step 2

4a. Tasks is already sorted to given critera
> Use case ends

#### Use case: Assigning tasks

**MSS**

1. User requests to list tasks
2. TaskManager shows a list of tasks
3. User requests to assign an assignee to a task in the list
4. TaskManager assigns the assignee to the task<br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given assignee and/or task is invalid

> 3a1. TaskManager shows an error message <br>
 Use case resumes at step 2

#### Use case: Categorise tasks

**MSS**

1. User requests to list tasks
2. TaskManager shows a list of tasks
3. User requests to assign a category to a task in the list
4. TaskManager assigns the category to the task<br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given category and/or task is invalid

> 3a1. TaskManager shows an error message <br>
 Use case resumes at step 2

#### Use case: View free/blocked time slots

**MSS**

1. User requests to view all free/blocked timeslots
2. TaskManager lists all free/blocked timeslots<br>
Use case ends.

**Extensions**

2a. There are no available or blocked timeslots in the user specified time period

> Use case ends

#### Use case: View history

**MSS**

1. User request to view history of completed task
2. TaskManager lists the history of completed task

**Extensions**

2a. There is no completed task

> 2a1. TaskManager shows error message
> Use case ends

{More to be added}

## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 tasks without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands)
   should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Able to read inputs from a keyboard
5. All classes should follow the single responsibility guideline
6. Codes should be properly commented on to maintain readability
7. Should come with automated unit tests and open source code
8. Data should be stored and backup readily available
9. Response time should be at most 1 second no matter how big the data file

## Appendix D : Glossary

#### Task

> Event that the user wants to complete

#### Time period

> Start and end time of the event

#### Assignee
> Person that the person works with the complete the task

#### Category

> Area of user’s life that the task is concerned with

#### Search term

> Specific keywords / time

#### Sorting criteria

#### Unconfirmed events

> Events where timing has not been fixed

#### Mainstream OS

> Windows, Linux, Unix, OS-X

## Appendix E : Product Survey

**Product Name**: Desktop Calender

Author: Joel

Pros:

* Very basic user interface
* Permanently on the desktop
* Auto start-up
* Customizable skins (e.g. colour gradient can fade between 0 - 100%)
* Text box can be expanded and shorten

Cons:

* Does not have actual timeslots on it
* Everything is manually input inside
* Requires mouse to access different dates
* Very limited keyboard shortcuts
* No additional features (e.g. reminders/priority listing/tags/search/recurring events/calenders)

---

**Product Name**: ToDoist

Author: Jun Wei

Pros:

* Organised and simple interface
* Keyboard shortcuts available (some which work without being on the app)
* Friendly for keyboard users who do not have a mouse (can even add into specific list with due dates)
* Can give tasks certain priorities
* Pre-set filters available (to filter task by due date or priority)
* Auto-completion and recognition for labels,tags and dates to sort tasks when adding tasks
* Able to check done tasks and undo in the event that it was accidental
* Can share projects with others who are also using the app and add/edit tasks within project
* Synchronizes across different platforms

Cons:

* Many functions only functional for premium users (eg. labels, email/SMS reminders, custom filters)
* Notifications only possible when application is turned on
* Although adding task is easy with keyboard, other functionalities like deleting, editing and shifting tasks across projects require a mouse

---

**Product Name**: Google Calendar

Author: Alvin

Pros:

* Good calendar view of all tasks
* Able to change view to day, week, month
* A lot of functions
* Able to sync multiple calendars from different users and overlap onto your own calendar
* Able to change layout of viewing to suit users needs
* Quick add function effective
* Allow us to find available time in create interface to slot events in
* Allow us to invite guests to events (Sharing of tasks)
* Allow us to specify timezone of event for timezone change easily

Cons:

* Complicated interface
* Difficult to navigate around
* Unable to block book multiple timeslots for a single event
* Unable to view yearly calendar
* Only 1 type of event
* Does not allow us to specify task as completed
* No keyboard shortcuts available
* Does not allot priority setting
