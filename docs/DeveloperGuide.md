# Developer Guide
1. [Setting Up](#setting-up)
   * 1.1. [Prerequisites](#prerequisites)
   * 1.2. [Importing the project into Eclipse](#importing-the-project-into-eclipse)
   * 1.3. [Configuring Checkstyle](#configuring-checkstyle)
   * 1.4. Troubleshooting project setup
2. [Design](#design)
   * 2.1. Architecture
   * 2.2. UI component
   * 2.3. Logic component
   * 2.4. Model component
   * 2.5. Storage component
   * 2.6. Common classes
3. [Implementation](#implementation)
   * 3.1. Logging
   * 3.2. Configuration
4. [Testing](#testing)
   * 4.1. Troubleshooting tests
5. [Dev Ops](#dev-ops)
   * 5.1. Build Automation
   * 5.2. Continuous Integration
   * 5.3. Publishing Documentation
   * 5.4. Making a Release
   * 5.5. Converting Documentation to PDF format
   
* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b--use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Glossary](#appendix-d--glossary)
* [Appendix E : Product Survey](#appendix-e--product-survey)


## 1. Setting up

### 1.1. Prerequisites

1. **JDK `1.8.0_60`**  or later<br>
    > Having any Java 8 version is not enough.
    This app will not work with earlier versions of Java 8.

2. **Eclipse** IDE
3. **e(fx)clipse** plugin for Eclipse (Do the steps 2 onwards given in
   [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious))
4. **Buildship Gradle Integration** plugin from the Eclipse Marketplace
5. **Checkstyle Plug-in** plugin from the Eclipse Marketplace


### 1.2. Importing the project into Eclipse

1. Fork this repo, and clone the fork to your computer
2. Open Eclipse (Note: Ensure you have installed the **e(fx)clipse** and **buildship** plugins as given
   in the prerequisites above)
3. Click `File` > `Import`
4. Click `Gradle` > `Gradle Project` > `Next` > `Next`
5. Click `Browse`, then locate the project's directory
6. Click `Finish`

  > * If you are asked whether to 'keep' or 'overwrite' config files, choose to 'keep'.
  > * Depending on your connection speed and server load, it can even take up to 30 minutes for the set up to finish
      (This is because Gradle downloads library files from servers during the project set up process)
  > * If Eclipse auto-changed any settings files during the import process, you can discard those changes.

### 1.3. Configuring Checkstyle
1. Click `Project` -> `Properties` -> `Checkstyle` -> `Local Check Configurations` -> `New...`
2. Choose `External Configuration File` under `Type`
3. Enter an arbitrary configuration name e.g. taskManager
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

`Main` has only one class called [`MainApp`](../src/main/java/seedu/description/MainApp.java). It is responsible for,

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

> Note how the `Model` simply raises a `TaskManagerChangedEvent` when the Task Manager data are changed,
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

Author: Ye Huan Hui

<img src="images/UiClassDiagram.png" width="800"><br>
_Figure 2.2.1 : Structure of the UI Component_

**API** : [`Ui.java`](../src/main/java/seedu/description/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `TaskListPanel`,
`StatusBarFooter`, `BrowserPanel` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/description/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component,

* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raised from various parts of the App and updates the UI accordingly.

### 2.3. Logic component

Author: Lee Jin Shun

<img src="images/LogicClassDiagram.png" width="800"><br>
_Figure 2.3.1 : Structure of the Logic Component_

**API** : [`Logic.java`](../src/main/java/seedu/description/logic/Logic.java)

1. `Logic` uses the `Parser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a task) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
 API call.<br>
<img src="images/DeleteTaskSdForLogic.png" width="800"><br>
_Figure 2.3.1 : Interactions Inside the Logic Component for the `delete 1` Command_

### 2.4. Model component

Author: Hon Kean Wai

<img src="images/ModelClassDiagram.png" width="800"><br>
_Figure 2.4.1 : Structure of the Model Component_

**API** : [`Model.java`](../src/main/java/seedu/description/model/Model.java)

The `Model`,

* stores a `UserPref` object that represents the user's preferences.
* stores the Task Manager data.
* exposes a `UnmodifiableObservableList<ReadOnlyTask>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

### 2.5. Storage component

Author: Chew Chia Sin

<img src="images/StorageClassDiagram.png" width="800"><br>
_Figure 2.5.1 : Structure of the Storage Component_

**API** : [`Storage.java`](../src/main/java/seedu/description/storage/Storage.java)

The `Storage` component,

* can save `UserPref` objects in json format and read it back.
* can save the Task Manager data in xml format and read it back.

### 2.6. Common classes

Classes used by multiple components are in the `seedu.doit.commons` package.

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
      e.g. `seedu.description.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units
     (those code units are assumed to be working).<br>
      e.g. `seedu.description.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as
      how the are connected together.<br>
      e.g. `seedu.description.logic.LogicManagerTest`

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
    e.g. For [UserGuide.md](UserGuide.md), the URL will be ``.
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
`* * *` | new user | have a help command | refer to instructions when I forget how to use the App
`* * *` | user | add a new task | add items that have deadlines
`* * *` | user | add a new floating task | add items without deadlines
`* * *` | user | add a new event | add items that has a start and end date
`* * *` | user | delete a task | remove items that I no longer need
`* * *` | user | delete a floating task | remove items that I no longer need
`* * *` | user | delete an event | remove items that I no longer need
`* * *` | user | edit tasks | rename or reschedule task
`* * *` | user | edit floating tasks | rename floating task
`* * *` | user | edit events | rename or reschedule events
`* * *` | user | search a task by name | locate task details without having to go through the entire list
`* * *` | user | search a floating task by name | locate task details without having to go through the entire list
`* * *` | user | search an event by name | locate task details without having to go through the entire list
`* * *` | user | set task as completed | see past tasks
`* * *` | user | set floating task as completed | see past floating tasks
`* * *` | user | set event as completed | see past events
`* * *` | forgetful user | set reminders for tasks | be reminded for events
`* * *` | forgetful user | set reminders for event | be reminded for events
`* * *` | user | be able to undo the most recent action | revert changes made
`* * *` | user | block multiple timeslots when the timing is uncertain | know which times are available for me to add new tasks
`* * *` | user | be able set priority to a task | know which is the highest priority that I should do 1st.
`* * *` | user | be able set priority to a floating task | know which is the highest priority that I should do 1st.
`* * *` | user | be able set priority to an event | know which is the highest priority that I should do 1st.
`* * *` | user | be able tag/categorise a task | know what to do when I am in the current context
`* * *` | user | be able tag/categorise a floating task | know what to do when I am in the current context
`* * *` | user | be able tag/categorise an event | know what to do when I am in the current context
`* * *` | user | set tasks as recurring | dont need to keep adding for repeating tasks
`* * *` | user | set events as recurring | don't need to keep adding for repeating events
`* * *` | user | sort tasks by date, priority, deadline, recurrence, tags | view important tasks first
`* * *` | user | sort floating tasks by priority and tags | view important floating tasks first
`* * *` | user | sort events by date, priority, deadline, recurrence, tags | view important events first
`* * *` | user | see a list of done tasks and to-do tasks when I enter a command | have a overview of tasks to be done
`* *` | user | be able know what should he do today | so they can plan what task to do first
`* *` | user | select task/events by index | to reduce typing of full name
`* *` | paranoid user | require a password to use the task manager | prevent others from accessing my task, floating tasks and events
`* *` | user | be able to filter items by date | so that you can see all items and free slots only on that date
`* *` | user | automatically delete events after they have passed | so that they do not clutter the space
`* *` | user | be able to repeat the command Eg. by pressing up | easy typing if command similar
`*` | user | launch the program with a keyboard shortcut | start the program
`*` | user | color scheme of priority | to make it obvious
`*` | user | color scheme of overdue tasks | to make it obvious
`*` | user | I want to import task data from a file | sync
`*` | user | be able to sync tasks with deadline | add task automatically
`*` | user | be able to delete all items based on date/category at one go | so that I do not need to delete one by one when there are changes to the plan of the whole day
`*` | user | be able to set progress of task. Eg pending, postpone, waiting for reply etc.


## Appendix B : Use Cases

(For all use cases below, the **System** is the `TaskManager` and the **Actor** is the `user`, unless specified otherwise)

### Use Case: Adding a task

**MSS**
1. User enters command to add task
2. System add task and shows it on the to do list
3. Use case ends

**Extensions**
1a. The user enters a duplicate task (same name same deadline)
> System shows an error message
> Use case ends

1b. The user enters a duplicate task but different deadline
> System prompt him to reschedule
> Use case ends

3a. User undos add task command
> System deletes newly added task and show feedback to user
> Use case ends


### Use Case: Editing a task

**MSS**
1. User enters command to edit task
2. System edits task and displays feedback to user
3. Use case ends

**Extensions**
1a. The user enters a task that is not in the System
> System shows an error message
> Use case ends

1b. The user uses wrong syntax to edit the task
> System shows example of correct syntax
> Resume from 1

3a. User undos task edit command
> System adds the edited task back and shows feedback to user
> Use case ends



### Use case: Deleting a task

**MSS**

1. User requests to list tasks
2. System shows a list of tasks
3. User requests to delete a specific task in the list by its name/index
4. System deletes the task <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given name/index is invalid

> 3a1. System shows an error message <br>
  Use case resumes at step 2

4a. User undos task delete command

> System adds the deleted task back and shows feedback to user
> Use case ends


### User Case : Marking a task as done

**MSS**
1. User enters command to mark task as done
2. System marks tasks as done and shows feedback to user
3. Use case ends

**Extensions**
1a. The user enters a task that is not in the System
> System shows an error message
> Use case ends

3a. User undos mark task done command
> System marks the task back as undone and shows feedback to user
> Use case ends

### Use Case: Filter events/tasks by date

**MSS**
1. User enters command to filter task by date
2. System filters tasks by date and shows them to user
3. Use case ends

**Extensions**
1a. The user enters an invalid date
> System shows an error message
> Use case ends

### Use Case: Set reminder

**MSS**
1. User enters command to set reminder for specific task
2. System reminds user at specified time via popup

**Extensions**
1a. User enters invalid task
> System shows an error message
> Use case ends

1a. User enters invalid date to be reminded
> System shows an error message
> Use case ends

## Use Case: View completed and uncompleted tasks

**MSS**
1. User enters command to view uncompleted or completed tasks
2. System opens a new window show list of completed and uncompleted tasks
3. Use case ends


## Use Case : Search items containing keywords

**MSS**
1. User enters command to view uncompleted or completed tasks
2. System show tasks containing keywords
3. Use case ends

**Extensions**
2a. User enters invalid task name to search
> System shows an error message
> Use case ends

2a. User enters task name not in list
> System shows an empty list
> Use case ends



## Use Case : Update reminder

**MSS**
1. User enters command to update reminder for specific task
2. System reminds user at specified time via popup
3. Use case ends

**Extensions**
1a. User enters invalid task
> System shows an error message
> Use case ends

1b. User enters invalid date to be reminded
> System shows an error message
> Use case ends


## Use Case : Update priority of an task

**MSS**
1. User enters command to update priority of task
2. System updates priority to user specified priority
3. Use case ends

**Extensions**
1a. User enters a task that is not in the System
> System shows an error message
> Use case ends

1b. User enters an invalid priority
> System shows an error message
> Use case ends


## Use Case : Getting Help

**MSS**
1. User enters command for help
2. System shows help prompt
3. Use case ends


## Use Case : Clear by date/category

**MSS**
1. User enters command to clear all tasks by date
2. System clears all tasks by date
3. Use case ends

**Extensions**
1a. User enters an invalid date
> System shows an error message
> Use case ends

## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.

2. Should be able to hold up to 1000 items without a noticeable sluggishness in performance for typical usage.

3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

4. Enter an event/tasks in one command instead of multiple clicks

5. Be able to access the program offline access tasks in areas without internet

6. Should be able to complete any user command within 2 seconds

7. Should delete all past events automatically without user doing it manually

8. 1st time user should take no more than 5 minutes to learn how to use program

9. Should require password to access the program

10. Passwords in password file should be salted and hashed
{More to be added}

## Appendix D : Glossary

**Items** : Refers to tasks, floating tasks and events

**UI**: User interface is the means by which the user  and the system interact with each other

**Sync**: Synchronize / adjust data on multiple files to be the same as each other

**CRUD**: Create, Read, Update and Delete.

**Event**: A to-do that has a date range.

**Task**: A to-do without a date range, and optionally has a deadline.

**Floating Tasks**: Tasks without deadlines

**Index of to-do item**: The number visually tagged to a to-do item on the UI (changes with how to-dos are listed).

**MSS**: Main Success Scenario of a use case.


##### Mainstream OS

> Windows, Linux, Unix, OS-X


## Appendix E : Product Survey

### Author: Chia Sin
### Product: Toodledo
### Advantages:
* Has many features to use
* Email Sync
* Tells you what to do
* Able to set default so minimal adjustment is needed
* Able to type commands

### Disadvantages:
* Need too many clicks for settings and other features other than task
* Need to click to delete and update
* Need internet for desktop version to use.

### Author: Kean Wai
### Product: Sticky Notes
### Advantages:
* Simple and easy to use (functions like text document)
* Can be navigated using keyboard only
* Starts up automatically on entering desktop
* Works offline
* Comes preinstalled on windows so no need for special IT permissions
### Disadvantages:
* Unable to sync with deadline
* Lack of functionality (No reminders, calendar view, etc.)
* Requires windows to be installed

### Author: Jin Shun
### Product: Momentum
### Advantages:
* Simple to use
* Nice background
* Helpful way of reminding users of pending tasks, every time user opens new tab in chrome
* Has integration with full fledged task managers like trello
### Disadvantages:
* Only works with chrome browser
* Requires internet connection
* Unable to set deadline for tasks
* Minimal features

### Author: Huanhui
### Product: Wunderlist
### Advantages:
* Some shortcut keys available, good user flexibility
* CRUD can function offline
* Many features such as the ability to set reminders, due dates, recurring tasks
* Tasks are automatically sorted by due date
* Able to display completed tasks
* Well designed UI
### Disadvantages:
* Not fully functional on keyboard, still need to use mouse to do operations such as select task
* Do not have an undo function
* Requires installation
