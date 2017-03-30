# TaskIt - Developer Guide

By : `Team SE-EDU`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jun 2016`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

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
3. Enter an arbitrary configuration name e.g. TaskIt
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

`Main` has only one class called [`MainApp`](../src/main/java/seedu/address/MainApp.java). It is responsible for,

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

>Note how the `Model` simply raises a `TaskItChangedEvent` when the Task Manager data are changed,
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

Author: Alice Bee

<img src="images/UiClassDiagram.png" width="800"><br>
_Figure 2.2.1 : Structure of the UI Component_

**API** : [`Ui.java`](../src/main/java/seedu/address/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`,
`StatusBarFooter`, `BrowserPanel` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/address/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component,

* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raised from various parts of the App and updates the UI accordingly.

Author: Amro Shohoud

`UI` design

* Priority - Sets background color for TaskCard (transparent, yellow, red) based on the value of priority (low, medium, high). No label for priority is inserted in fxml file.
* Events - Events such as clicking trigger the listener to select a TaskCard, highlighting the background to show selection.
* Filtering - Logic creates a filtered list of ReadOnlyTasks, which is used by TaskListPanel to create TaskCards that populate the panel.


### 2.3. Logic component

Author: Bernard Choo

<img src="images/LogicClassDiagram.png" width="800"><br>
_Figure 2.3.1 : Structure of the Logic Component_

**API** : [`Logic.java`](../src/main/java/seedu/address/logic/Logic.java)

1. `Logic` uses the `Parser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a person) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
 API call.<br>
<img src="images/DeleteTaskSdForLogic.png" width="800"><br>
_Figure 2.3.1 : Interactions Inside the Logic Component for the `delete 1` Command_

### 2.4. Model component

Author: Cynthia Dharman

<img src="images/ModelClassDiagram.png" width="800"><br>
_Figure 2.4.1 : Structure of the Model Component_

**API** : [`Model.java`](../src/main/java/seedu/address/model/Model.java)

The `Model`,

* stores a `UserPref` object that represents the user's preferences.
* stores the Task Manager data.
* exposes a `UnmodifiableObservableList<ReadOnly>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

### 2.5. Storage component

Author: Darius Foong

<img src="images/StorageClassDiagram.png" width="800"><br>
_Figure 2.5.1 : Structure of the Storage Component_

**API** : [`Storage.java`](../src/main/java/seedu/address/storage/Storage.java)

The `Storage` component,

* can save `UserPref` objects in json format and read it back.
* can save the Task Manager data in xml format and read it back.

### 2.6. Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

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
      e.g. `seedu.address.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units
     (those code units are assumed to be working).<br>
      e.g. `seedu.address.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as
      how the are connected together.<br>
      e.g. `seedu.address.logic.LogicManagerTest`

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
    e.g. For [UserGuide.md](UserGuide.md), the URL will be `https://<your-username-or-organization-name>.github.io/addressbook-level4/docs/UserGuide.html`.
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

Author: Amro Shohoud


Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the App
`* * *` | user | add a new floating task | save tasks without specified deadlines
`* * *` | user | add a new deadline task | know when some tasks need to be completed by
`* * *` | user | add a new event | know when my events are
`* * *` | user | mark a task as done or undone | know what tasks still need to be completed
`* * *` | user | delete an existing task | get rid of tasks that no longer matter to me
`* * *` | user | search tasks based on keywords in title or tag | find all similar tasks
`* * *` | user | search tasks based on date | find all tasks near a certain time
`* * *` | user | modify an existing task's title | change the name of the task
`* * *` | user | modify an existing task's start and end date | keep up with changing deadlines
`* * *` | user | undo an action | undo any mistakes I make
`* * *` | user | view all tasks | see all the tasks I've ever added
`* * *` | user | view all undone or done tasks | see what tasks I still need to work on
`* * *` | user | view all tasks due today | see what tasks I will need to finish by today
`* * *` | user | view all overdue tasks | see what tasks I can delete
`* * *` | user | Save all the tasks in specified local folder/file | Store everything in local file that is controlled by a cloud syncing service (e.g. dropbox) to synchronise across multiple computers
`* *` | user | categorize tasks into work, study, leisure etc | group my tasks together
`* *` | user | view all floating tasks | see which tasks do not have a deadline
`* *` | user | view all events | see what my events are
`* *` | user | sort tasks by date | see which tasks will be coming up soon
`* *` | user | Assign priority to each task | rank my tasks depending on what has to get done first
`* *` | user | list tasks by priority | see which tasks will need my attention the most
`*` | user | Add recurring tasks | so that I can easily add a task that occurs regularly
`*` | user |Sync with other calendars (e.g. Google calendar) | I can add functionality that TaskIt may not have
`*` | user | See today’s most important task when opening the app | so that I immediately know what I need to work on
`*` | user | Receive reminder for the most recent task  | so that I remember what I need to work on
`*` | user | See encouraging words for completing priority tasks  | so that I feel good about myself and motivated to keep finishing tasks
`*` | user | Duplicate a task  | so that I don't have to reenter a complicated task
`*` | user | Link one task to another  | so that I can see when tasks are connected to each other
`*` | user | view tasks with the same day as a deadline  | so that I can see tasks that are all due on the same day
`*` | user | view tasks with the same week as a deadline  | so that I can see tasks that are all due in the same week

## Appendix B : Use Cases

Author: Amro Shohoud

(For all use cases below, the **System** is the `TaskIt` and the **Actor** is the `user`, unless specified otherwise)

#### Use case 1: Add a task

**MSS**

1. User requests to add a task
2. TaskIt adds the task <br>
Use case ends.

**Extensions**

1a. The given format is invalid
> 1a1. TaskIt requests a valid command and suggests valid format to users
> Use case resumes at step 1 <br>
Use case ends.

1b. The end date or time of an event is before the start date or time
> 1b1. TaskIt notifies the user of this error
> Use case resumes at step 1 <br>
Use case ends.


#### Use case 2: Search for an existing task based on keywords, tags and dates

**MSS**

1. User requests to search for tasks based on given keywords, tags and dates
2. TaskIt searches for the tasks and returns all the matched tasks to the user <br>
Use case ends.

**Extensions**

1a. The given command is invalid
> 2a1. TaskIt requests a valid command and suggests valid format to users
> Use case resumes at step 1 <br>
Use case ends

1b. User gives empty keywords
> 2b1. TaskIt requests user to input keywords
> Use case resumes at step 1 <br>
Use case ends

1c. No matched tasks found
> TaskIt returns a message letting the user know there were no matches <br>
Use case ends.

#### Use case 3: Delete a task

**MSS**

1. User requests to delete a specific task based on keywords
2. TaskIt deletes the task <br>
Use case ends.

**Extensions**

1a. The given command is invalid
> 1a1. TaskIt requests a valid command and suggests valid format to users
> Use case resumes at step 1 <br>
Use case ends.

1b. User did not specify a valid task
> 1b1. TaskIt notifies the user and lists all tasks and indices (UC7)
> 1b2. User specifies task to delete based on index <br>
Use case ends.

1c. Multiple tasks are found based on the keywords
> 1c1. TaskIt notifies the user and lists all tasks and indices that were found (UC2)
> 1c2. User specifies task to delete based on index <br>
Use case ends.

#### Use case 4: Modify an existing task

**MSS**

1. User searches a task
2. TaskIt returns the requested list of tasks (UC2)
3. User specifies the task to modify and the new task details (name, dates etc.)
4. TaskIt changes the task specifications and notifies user <br>
Use case ends.

**Extensions**

1a. The given command is invalid
> 1a1. TaskIt requests a valid command and suggests valid format to users
> Use case resumes at step 1 <br>
Use case ends.

2a. No tasks were found
> 2a1. TaskIt notifies the user and lists all tasks and indices (UC7)
> 2a2. User specifies task to modify based on index <br>
Use case ends.

2b. Multiple tasks are found based on the keywords
> 2b1. TaskIt notifies the user and lists all tasks and indices that were found (UC2)
> 2b2. User specifies task to modify based on index <br>
Use case ends.

#### Use case 5: Undo an action

**MSS**

1. User requests to undo last action
2. TaskIt asks for confirmation
3. User confirms
4. TaskIt undoes last action <br>
Use case ends.

**Extensions**
1a. The given command is invalid
> 1a1. TaskIt requests a valid command and suggests valid format to users
> Use case resumes at step 1 <br>
Use case ends.

2a. No previous action found
> 2a1. TaskIt notifies the user <br>
Use case ends.

1b. Multiple tasks are found based on the keywords
> 1b1. TaskIt notifies the user and lists all tasks and indices that were found (UC2)
> 1b2. User specifies task to delete based on index <br>
Use case ends.

#### Use case 6: Mark a task as done or undone

**MSS**

1. User requests to mark a specific task based on keywords
2. TaskIt marks the task <br>
Use case ends.

**Extensions**
1a. The given command is invalid
> 1a1. TaskIt requests a valid command and suggests valid format to users
> Use case resumes at step 1 <br>
Use case ends.

1b. User did not specify a valid task
> 1b1. TaskIt notifies the user and lists all tasks and indices (UC7)
> 1b2. User specifies task to mark based on index <br>
Use case ends.

1c. Multiple tasks are found based on the keywords
> 1c1. TaskIt notifies the user and lists all tasks and indices that were found (UC2)
> 1c2. User specifies task to mark based on index <br>
Use case ends.

Author: Peng Chong
#### Use case 7: View all tasks

**MSS**

1. User requests to view all tasks
2. TaskIt lists all tasks <br>
Use case ends.

**Extensions**
1a. The given command is invalid
> 1a1. TaskIt requests a valid command and suggests valid format to users
> Use case resumes at step 1 <br>
Use case ends.

1b. User requests to view all tasks with keywords & tags
> 1b1. TaskIt lists all aforementioned tasks <br>
Use case ends

#### Use case 8: View all undone or done tasks

**MSS**

1. User requests to view all undone or done tasks
2. TaskIt lists all undone or done tasks <br>
Use case ends.

**Extensions**
1a. The given command is invalid
> 1a1. TaskIt requests a valid command and suggests valid format to users
> Use case resumes at step 1 <br>
Use case ends.

#### Use case 9: View all overdue tasks

**MSS**

1. User requests to view all overdued tasks
2. TaskIt lists all overdued tasks <br>
Use case ends.

**Extensions**
1a. The given command is invalid
> 1a1. TaskIt requests a valid command and suggests valid format to users
> Use case resumes at step 1 <br>
Use case ends.

#### Use case 10: Save all the tasks in specified local folder/file

**MSS**

1. User requests to save all the tasks in specified local folder/file
2. TaskIt saves all the tasks in the specified local folder/file <br>
Use case ends.

**Extensions**
1a. The given local folder/file is invalid
> 1a1. TaskIt requests a valid local folder/file
> Use case resumes at step 1 <br>
Use case ends.

#### Use case 11: Search tasks by a given date

**MSS**

1. User requests to search all the tasks by a given deadline
2. TaskIt sort all the tasks which deadline matches the given deadline and show the result to the user <br>
Use case ends.

**Extensions**
1a. All tasks are floating task without deadline
> 1a1. TaskIt returns all the existing tasks<br>

1b. The given date command is invalid
> 1b1. TaskIt requests a valid date command and suggests valid format to users
> Use case resumes at step 1 <br>
Use case ends.

{More to be added}

## Appendix C : Non Functional Requirements

Author: Peng Chong

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 tasks without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands)
   should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Should come with automated unit tests and open source code.
5. Should return to a functioning state when system restarts
6. Should function well offline
7. Shuld have no unhandled exceptions from incorrect user input
8. Should work well stand-alone and should not be a plug-in to another software
9. Should use text files for data storage and not [relational databases](#relational-databases)
10. Should store the data locally in a human editable text file
11. Should work without requiring an installer, just download jar and run
12. Should follow the Object-Oriented paradigm<br>


## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Relational databases

> Collection of data items organized as a set of formally-described tables from which data can be accessed, such as SQL


## Appendix E : Product Survey

Author: Amro Shohoud

**Google Calendar**

Author: Google

Pros:

* Can sync with other users
* Multi-platform
* Send reminders
* Calendar view
* Can put tasks in categories

Cons:

* Unappealing UI
* Cannot sync with other calendars

**Todoist**

Author: Doist

Pros:

* Text-based interface makes it easy to input and modify tasks
* Nice UI
* Very simple to use
* Can put tasks in categories
* Can create recurring tasks in an intuitive way

Cons:

* Lists everything out rather with no option for a calendar view
* Tasks split into categories with no option of just view ALL tasks

**Wunderlist**

Author: 6Wunderkinder

Pros:

* broad platform support

Cons:

* lacking recurring feature

**Trello**

Author: Fog Creek Software

Pros:

* Agile board layout

Cons:

* No calendar view
