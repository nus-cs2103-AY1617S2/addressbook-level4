# Developer Guide

1. [Setting Up](#1-setting-up)
2. [Design](#2-design)
3. [Implementation](#3-implementation)
4. [Testing](#4-testing)
5. [Dev Ops](#5-dev-ops)
6. [Appendices](#6-appendices)
    * [Appendix A: User Stories](#appendix-a--user-stories)
    * [Appendix B: Use Cases](#appendix-b--use-cases)
    * [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
    * [Appendix D: Glossary](#appendix-d--glossary)
    * [Appendix E : Product Survey](#appendix-e--product-survey)


## 1. Setting up

#### Prerequisites

1. **JDK `1.8.0_60`**  or later<br>

    > Having any Java 8 version is not enough. <br>
    This app will not work with earlier versions of Java 8.

2. **Eclipse** IDE
3. **e(fx)clipse** plugin for Eclipse (Do the steps 2 onwards given in
   [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious))
4. **Buildship Gradle Integration** plugin from the Eclipse Marketplace
5. **Checkstyle Plug-in** plugin from the Eclipse Marketplace


#### Importing the project into Eclipse

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

#### Configuring Checkstyle
1. Click `Project` -> `Properties` -> `Checkstyle` -> `Local Check Configurations` -> `New...`
2. Choose `External Configuration File` under `Type`
3. Enter an arbitrary configuration name e.g. TaskManager
4. Import checkstyle configuration file found at `config/checkstyle/checkstyle.xml`
5. Click OK once, go to the `Main` tab, use the newly imported check configuration.
6. Tick and select `files from packages`, click `Change...`, and select the `resources` package
7. Click OK twice. Rebuild project if prompted

> Note to click on the `files from packages` text after ticking in order to enable the `Change...` button

#### Troubleshooting project setup

**Problem: Eclipse reports compile errors after new commits are pulled from Git**
* Reason: Eclipse fails to recognize new files that appeared due to the Git pull.
* Solution: Refresh the project in Eclipse:<br>
  Right click on the project (in Eclipse package explorer), choose `Gradle` -> `Refresh Gradle Project`.

**Problem: Eclipse reports some required libraries missing**
* Reason: Required libraries may not have been downloaded during the project import.
* Solution: [Run tests using Gardle](UsingGradle.md) once (to refresh the libraries).


## 2. Design

### Architecture

<img src="images/Architecture.png" width="600"><br>
The **_Architecture Diagram_** given above explains the high-level design of the App.
Given below is a quick overview of each component.

`Main` has only one class called [`MainApp`](../src/main/java/seedu/geekeep/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connect them up with each other.
* At shut down: Shuts down the components and invoke cleanup method where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.
Two of those classes play important roles at the architecture level.
* `EventsCentre` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design)
* `LogsCenter` : Used by many classes to write log messages to the App's log file.

The rest of the App consists four components.
* [**`UI`**](#ui-component) : Renders the UI
* [**`Logic`**](#logic-component) : Parses and executes commands
* [**`Model`**](#model-component) : Holds the data of the App in-memory.
* [**`Storage`**](#storage-component) : Reads data from, and writes data to, the hard disk.

Each of the four components
* Defines its _API_ in an `interface` with the same name as the Component.
* Exposes its functionality using a `{Component Name}Manager` class.

For example, the `Logic` component (see the class diagram given below) defines its API in the `Logic.java`
interface and exposes its functionality using the `LogicManager.java` class.<br>
<img src="images/LogicClassDiagram.png" width="800"><br>

##### Events-Driven nature of the design

The _Sequence Diagram_ below shows how the components interact for the scenario where the user issues the
command `delete 1`.

<img src="images\SDforDeletePerson.png" width="800">

>Note how the `Model` simply raises a `TaskManagerChangedEvent` when the Task Manager data are changed,
 instead of asking the `Storage` to save the updates to the hard disk.

The diagram below shows how the `EventsCenter` reacts to that event, which eventually results in the updates
being saved to the hard disk and the status bar of the UI being updated to reflect the 'Last Updated' time. <br>
<img src="images\SDforDeletePersonEventHandling.png" width="800">

> Note how the event is propagated through the `EventsCenter` to the `Storage` and `UI` without `Model` having
  to be coupled to either of them. This is an example of how this Event Driven approach helps us reduce direct
  coupling between components.

The sections below give more details of each component.

### UI component

<img src="images/UiClassDiagram.png" width="800"><br>

**API** : [`Ui.java`](../src/main/java/seedu/geekeep/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`,
`StatusBarFooter`, `BrowserPanel` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class
and they can be loaded using the `UiPartLoader`.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/geekeep/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component,
* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raised from various parts of the App and updates the UI accordingly.

### Logic component

<img src="images/LogicClassDiagram.png" width="800"><br>

**API** : [`Logic.java`](../src/main/java/seedu/geekeep/logic/Logic.java)

1. `Logic` uses the `Parser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a task) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
 API call.<br>
<img src="images/DeletePersonSdForLogic.png" width="800"><br>

### Model component

<img src="images/ModelClassDiagram.png" width="800"><br>

**API** : [`Model.java`](../src/main/java/seedu/geekeep/model/Model.java)

The `Model`,
* stores a `UserPref` object that represents the user's preferences.
* stores the Task Manager data.
* exposes a `UnmodifiableObservableList<ReadOnlyTask>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

### Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>

**API** : [`Storage.java`](../src/main/java/seedu/geekeep/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the Task Manager data in xml format and read it back.

### Common classes

Classes used by multiple components are in the `seedu.geekeep.commons` package.

## 3. Implementation

### Logging

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

### Configuration

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
      e.g. `seedu.geekeep.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units
     (those code units are assumed to be working).<br>
      e.g. `seedu.geekeep.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as
      how the are connected together.<br>
      e.g. `seedu.geekeep.logic.LogicManagerTest`

#### Headless GUI Testing
Thanks to the [TestFX](https://github.com/TestFX/TestFX) library we use,
 our GUI tests can be run in the _headless_ mode.
 In the headless mode, GUI tests do not show up on the screen.
 That means the developer can do other things on the Computer while the tests are running.<br>
 See [UsingGradle.md](UsingGradle.md#running-tests) to learn how to run tests in headless mode.

#### Troubleshooting tests
 **Problem: Tests fail because NullPointException when AssertionError is expected**
 * Reason: Assertions are not enabled for JUnit tests.
   This can happen if you are not using a recent Eclipse version (i.e. _Neon_ or later)
 * Solution: Enable assertions in JUnit tests as described
   [here](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option). <br>
   Delete run configurations created when you ran tests earlier.

## 5. Dev Ops

### Build Automation

See [UsingGradle.md](UsingGradle.md) to learn how to use Gradle for build automation.

### Continuous Integration

We use [Travis CI](https://travis-ci.org/) to perform _Continuous Integration_ on our projects.
See [UsingTravis.md](UsingTravis.md) for more details.

### Making a Release

Here are the steps to create a new release.

 1. Generate a JAR file [using Gradle](UsingGradle.md#creating-the-jar-file).
 2. Tag the repo with the version number. e.g. `v0.1`
 2. [Create a new release using GitHub](https://help.github.com/articles/creating-releases/)
    and upload the JAR file your created.

### Managing Dependencies

A project often depends on third-party libraries. For example, Task Manager depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

## 6. Appendices

### **Appendix A : User Stories**

Priorities: High (must have) - \* \* \*, Medium (nice to have) - \* \*, Low (unlikely to have) - \*

| Priority | As a ... | I want to ... | So that I can... |
|--- | :--- | :--- | :--- |
| `* * *` | new user | see usage instructions | refer to instructions when I forget how to use the App |
| `* * *` | user | add a new task with title | add a floating task |
| `* * *` | user | add a new task with title and ending time| set a deadline for the task |
| `* * *` | user | add a new task with title, starting time and ending time| add an event |
| `* * *` | user | list tasks| have a look at all my tasks |
| `* * *` | user | list unfinished tasks | see all the tasks I have yet to complete |
| `* * *` | user | update title, starting time or ending time of a task | change the task title, starting and ending time in case of a mistake or change of plans |
| `* * *` | user | mark a task as done or undone | keep track of completeness of the task |
| `* * *` | user | delete a task | forget about it |
| `* * *` | user | undo previous command | revert the most recent command |
| `* * *` | user | search the tasks by title | find a specific task without listing all of them |
| `* * *` | user | change file location for the data storage |
| `* * *` | user | type command parameters in arbitrary order | have flexibility in typing commands |
| `* * *` | advanced user | use short versions of commands | type a command faster |
| `* *` | user | redo last undo command | revert the most recent undo command |
| `* *` | user | redo last command | repeat the most recent command |
| `* *` | user | add a recurring task | have the same task repeat itself over a specified duration without manually adding all of them |
| `* *` | user | add a task with description | have miscellaneous details not clutter the task title |
| `* *` | user | add a task with a venue | see location that task takes place |
| `* *` | user | add a task with a tag | categorize the task |
| `* *` | user | list upcoming tasks for the week | see all the upcoming tasks from now till the end of the week |
| `* *` | user | list all tasks for the day | see an summary of tasks for the day |
| `* *` | user | list all tasks having the same tags | see an summary of tasks for the category |
| `* *` | user | search the tasks by description | find a specific task without listing all of them |
| `* *` | user | search the tasks by venue | find a specific task without listing all of them |
| `* *` | user | search tasks within a time period | find tasks within a time period without listing all of them |
| `* *` | user | filter the listed or search results by title, description, time or tag | find a specific task without looking through all of them |
| `* *` | user | set more than one filter for searching tasks | find desired tasks more easily |
| `*` | user | add a task with multiple time periods | confirm the exact start and end time for the task later |
| `*` | user | delete or update one of the multiple time periods of a task | update the exact start and end time for the task |
| `*` | user | create an alias for a long command | save time typing |
| `*` | user who also uses Google Calendar | sync tasks to Google Calendar | see existing tasks and add local tasks to Google Calendar |


### **Appendix B : Use Cases**

(For all use cases below, the System is the TaskManager and the Actor is the user, unless specified otherwise)

#### **Use case: `Reschedule a task`**

MSS

1. User requests to search or list tasks

2. TaskManager shows an indexed list of tasks

3. User requests to update a specific task and the details in the list

4. TaskManager updates new detail value

5. TaskManager shows the updated task

6.  Use case ends.

Extensions

2a. The task does not exist

> Use case ends

3a. The given index is invalid

> 3a1. TaskManager shows an error message

> Use case resumes at step 2

3b. The given details to update is invalid

> 3b1. TaskManager shows an error message

> Use case resumes at step 3

3c. The given detail value format is invalid

> 3c1. TaskManager shows an error message

> Use case resumes at step 3

#### **Use case: `Summary of tasks for the day`**

MSS

1. User requests to see summary of tasks for the day

2. TaskManager shows a list of all tasks for the day

3. Use case ends.

Extensions

2a. There are no tasks for the day

> Use case ends

### **Appendix C : Non Functional Requirements**

1. Should work on Windows 7 or later as long as it has Java 1.8.0_60 or higher installed.

2. Should not require any installation apart from Java.

3. Core features should be available and work even when offline.

4. Data storage should be in plain text and be easily readable and editable.

5. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

6. Should be free and come with open sourced code.

7. Users should be able to execute all commands by using the CLI alone

### **Appendix D : Glossary**

##### **Mainstream OS**

> Windows, Linux, Unix, OS-X

##### **CLI**

> Command Line Interface

##### **GUI**

> Graphical User Interface

##### **Task**

> A task may or may not have a specific ending time
> E.g. Complete Progress Report

##### **Deadline**

> A task with a specified ending time is a deadline
> E.g. Complete Progress Report by 20 March 2017 2359H

### **Appendix E : Product Survey**

##### **Google Keep**

Author: Goh Yi Rui

Pros:

* Support for lists, plain text, pictures and voice recording

* Color codes tasks

* Easily search through all tasks by name, label, color or category

* Automatically identify tasks that lie within a same category

* Add personalised labels or taggings

* Autocompletion and suggestion for tasks

* Synced to Google Account

Cons:

* Reliance on GUI for all operations

* No support for events, only tasks and notes

* Require user to be online

* No project managements features

##### **Google Calendar**

Author: Zhang Hanming

Pros:

* Automatically parsing email and add event to calendar

* Reminder before events

* Intuitive UI

* Categorization of events

* Allow importing and exporting of calendars

Cons:

* Requires a Google account

* Unable to check off completed tasks

* Mainly a calendar app rather than a task managing one

##### **Gmail Tasks**

Author: Liu Ziyang

Pros:

+ Allows to mark a task as done

+ Allows to postpone a task

+ Has shortcuts for editing tasks

+ Allows self-defined categorizing

+ Integrates with Google Calendar

Cons:

+ Only works online

+ Does not support  command line inputs

+ Does not support events

+ Does not support undoing an action

+ Urgent events cannot stand out

##### **Habitica**

Author: How Si Wei

Pros:

* Gamification adds extra motivation

* Intuitive UI

* Categorization of events through taggings

* Has option to export data

* Has a large number of official and user-created apps and extensions

Cons:

* Requires a Habitica account

* Gamification may be distracting

* Group plans are only available to paid users
