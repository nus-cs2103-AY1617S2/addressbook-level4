# doTASK - Developer Guide

By : `Miao Ling`, `Ian`, `Qi Xiang` and `Dylan`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Setting Up](#1-setting-up)
2. [Design](#2-design)
3. [Implementation](#3-implementation)
4. [Testing](#4-testing)
5. [Dev Ops](#5-dev-ops)

* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b--use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Product Survey](#appendix-d--product-survey)



## 1. Setting up

### 1.1. Prerequisites

1. **JDK `1.8.0_111`**  or later<br>

    > Having any Java 8 version is not enough. <br>
    This app will not work with earlier versions of Java 8.

2. **Eclipse** IDE
3. **e(fx)clipse** plugin for Eclipse (Do steps 2 onwards given in
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

`Main` has only one class called [`MainApp`](../src/main/java/seedu/address/MainApp.java). It is responsible for,

* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup method where necessary.

[**`Commons`**](#2-6-common-classes) represents a collection of classes used by multiple other components.
Two of those classes play important roles at the architecture level.

* `EventsCenter` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design)
* `LogsCenter` : Used by many classes to write log messages to the App's log file.

The rest of the App consists of four components.

* [**`UI`**](#2-2-ui-component) : Handles the display of doTASK.
* [**`Logic`**](#2-3-logic-component) : Executes the commands keyed in by user.
* [**`Model`**](#2-4-model-component) : Contains the data of the doTASK in-memory.
* [**`Storage`**](#2-5-storage-component) : Reads data from, and writes data to, the hard disk.

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

<img src="images\SDforDeletePerson.png" width="800"><br>
_Figure 2.1.3a : Component interactions for `delete 1` command (part 1)_

>Note how the `Model` simply raises a `TaskManagerChangedEvent` when the Task Manager data are changed,
 instead of asking the `Storage` to save the updates to the hard disk.

The diagram below shows how the `EventsCenter` reacts to that event, which eventually results in the updates
being saved to the hard disk and the status bar of the UI being updated to reflect the 'Last Updated' time. <br>
<img src="images\SDforDeletePersonEventHandling.png" width="800"><br>
_Figure 2.1.3b : Component interactions for `delete 1` command (part 2)_

> Note how the event is propagated through the `EventsCenter` to the `Storage` and `UI` without `Model` having
  to be coupled to either of them. This is an example of how this Event Driven approach helps us reduce direct
  coupling between components.

The sections below give more details of each component.

### 2.2. UI component

Author: Lim Miao Ling

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

### 2.3. Logic component

Author: Dylan Sng

<img src="images/LogicClassDiagram.png" width="800"><br>
_Figure 2.3.1 : Structure of the Logic Component_

**API** : [`Logic.java`](../src/main/java/seedu/address/logic/Logic.java)

1. `Logic` uses the `Parser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a task) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
 API call.<br>
<img src="images/DeletePersonSdForLogic.png" width="800"><br>
_Figure 2.3.1 : Interactions Inside the Logic Component for the `delete 1` Command_

### 2.4. Model component

Author: Xu Qixiang

<img src="images/ModelClassDiagram.png" width="800"><br>
_Figure 2.4.1 : Structure of the Model Component_

**API** : [`Model.java`](../src/main/java/seedu/address/model/Model.java)

The `Model`,

* stores a `UserPref` object that represents the user's preferences.
* stores the Task Manager data.
* exposes a `UnmodifiableObservableList<ReadOnlyTask>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

### 2.5. Storage component

Author: Ian Tan

<img src="images/StorageClassDiagram.png" width="800"><br>
_Figure 2.5.1 : Structure of the Storage Component_

**API** : [`Storage.java`](../src/main/java/seedu/address/storage/Storage.java)

The `Storage` component,

* can save `UserPref` objects in json format and read it back.
* can save the Task Manager data in xml format and read it back.

### 2.6. Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

## 3. Implementation

### 3.1. Logging

We are using `java.util.logging` package for logging. The `LogsCenter` class is used to manage the logging levels
and logging destinations.

* The logging level can be controlled using the `logLevel` setting in the configuration file
  (See [Configuration](#3-2-configuration))
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
      e.g. `seedu.task.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units
     (those code units are assumed to be working).<br>
      e.g. `seedu.task.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as
      how the are connected together.<br>
      e.g. `seedu.task.logic.LogicManagerTest`

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

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`

### New User Guide
Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | get a list of basic commands | understand what the task manager will allow me to do.
`* * *` | new user | get help if I enter an invalid command | know that I am using it wrongly and how to correct myself.
`* * *` | new user | view more information about a particular command | learn how to use various commands.
`* * *` | new user | view the features offered by this task manager | identify what I can actually do with this task manager.


### Essential Functions
Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | add a task | use the keep track of the tasks to be completed.
`* * *` | user | delete a task | get rid of tasks that I no longer care to track.
`* * *` | user | delete a multiple tasks | get rid of multiple tasks without deleting them individually.
`* * *` | user | edit a task detail/deadline | change the deadline or the specifics of the tasks.
`* * *` | user | add comments/notes to a created task | provide extra details that aid in describing the task.
`* * *` | user | search for tasks using keywords | find the tasks with similar details.
`* * *` | user | mark a task as done | track my progress through cleared tasks.
`* * *` | user | clear all the tasks | erase all tasks without deleting them individually.
`* * *` | user | save my task list | easily retrieve my tasks.
`* * *` | user | find upcoming tasks | decide what needs to be done soon.


### Reminders
Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | have reminders about a task that is due soon | start working on the more important tasks first.
`* * *` | user | have recurring reminders for every deadline that is coming | not forget to wrap up a task.


### Prioritization
Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | add priority levels to different tasks | identify which tasks are the most urgent.
`* * *` | user | edit the priority levels of the tasks | change the priority levels to suit my needs.
`* * *` | user | sort the tasks by deadline | know which tasks are more urgent and which are less urgent.


### Tagging-related
Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | tag the tasks with relevant tags ( eg. study / work ) | easily categorise tasks.
`* * *` | user | search for tasks using tags | easily find a category of tasks.
`* * *` | user | delete a tag | remove a category of tasks that I no longer care to track.


### Subtasking
Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* *` | user | add subtasks to a task that is already present | break down said task into smaller problems.
`* *` | user | delete subtasks | get rid of subtasks that are no longer relevant.
`* *` | user | clear subtasks | keep track of my progress against the big picture.


### Advanced Features
Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* *` | user | view the tasks that were completed | keep track of my efficiency
`* *` | user | set the type of reminder about a task (vibration/ring) about a task that is due soon | punctually complete the tasks.



## Appendix B : Use Cases

(For all use cases below, the **System** is the `doTASK` and the **Actor** is the `user`, unless specified otherwise)

#### Use case 1 : Add Task

**MSS**

1. User request to add a task
2. doTASK adds the task <br>
Use case ends.

**Extensions**

1a. The task has invalid parameters eg. a/

> 1a1. doTASK shows an error message <br>
  Use case resumes at step 1

#### Use case 2 : Delete Task

**MSS**

1. User requests to list tasks
2. doTASK shows a list of tasks
3. User requests to delete a specific task in the list
4. doTASK deletes the task <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. doTASK shows an error message <br>
  Use case resumes at step 2

#### Use case 3 : Prioritize a task

**MSS**

1. User requests to list down the tasks by priority
2. doTASK shows a list of tasks in 2x2 matrix which are sorted in 4 different priorities
Use case ends

**Extensions**

1a. The list is empty

> Use case ends

#### Use case 4 : Tagging a task

**MSS**

1. User requests to list down the tasks by tags
2. doTASK request the user for the name of the tag
3. User input the name of the tag
2. doTASK executes the command and list the task under the given tag
Use case ends

**Extensions**

1a. The list is empty

> Use case ends

2a. The name of the tag does not exist

> doTASK show an error message.

#### Use case 5 : List commands

**MSS**

1. User requests to get a list of basic commands
2. doTASK shows a list of basic commands <br>
Use case ends.

#### Use case 6 : Editing parameters of a specified task

**MSS**

1. User requests to get a list of tasks
2. doTASK shows a list of tasks
3. User requests to edit the parameters of a specified task
4. doTASK edits the task <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends.

3a. Specified parameters are wrong

> 3a1. doTASK shows an error message <br>
  Use case resumes at step 2

#### Use case 7 : Searching for a task

**MSS**

1. User requests to search for a task
2. doTASK shows a list of tasks related to the the keyword
Use case ends.

**Extensions**

2a. There no tasks related to the keyword

> Use case ends

#### Use case 8 : Completing a task

**MSS**

1. User requests to search for a task
2. doTask shows a list of tasks related to the keyword
3. User requests to mark the task as completed
4. doTASK marks the task as completed
Use case ends.

**Extensions**

2a. List is empty

> Use case ends

#### Use case 9 : Clear all the tasks

**MSS**

1. User requests to clear all the tasks
2. doTASK clears the list of tasks
Use case ends.

#### Use case 10 : Clearing a specified subset of tasks

**MSS**

1. User requests to search for the tasks related to the input
2. doTASK shows the list of tasks related to the input
3. User requests to clear the list of tasks
4. doTASK clears the list of tasks
Use case ends.

#### Use case 11 : Find upcoming tasks

**MSS**

1. User requests to search for the upcoming tasks
2. doTASK shows the list of task that is near to the dateline
Use case ends.

## Appendix C : Non Functional Requirements

1. Should work on Windows 7 or later as long as it has Java `1.8.0_111` or higher installed.
2. Should be able to hold up to 1000 tasks without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands)
   should be able to accomplish most of the tasks faster using commands than using the mouse.
4. A user should be able to start using this without needing an installer.
5. Commands should take less than 3 seconds to run.
6. Should come with automated unit tests and open source code.

## Appendix D : Product Survey

**Google Tasks**

Author: Lim Miao Ling

Pros:

* Supported on almost every platform, web or mobile.
* Synced with Google Calendar. Displays tasks with deadlines along with Google calendar so you can better manage your schedule.
* Able to add task by deadline by clicking the date in Google Calendar.
* Able to create subtasks easily (just by pressing tab). Marking the supertask as complete will auto-complete all subtasks as complete.
* Uncluttered presentation as you can only see the task title along with the deadline.
* Extremely easy to get into and use.
* Completely free to use. No features locked behind a paywall.

Cons:

* Can't categorise tasks. You can make different lists for tasks, but then you can't view more than one list simultaneously.
* Must refresh page if the computer goes to sleep while the page is open. (Fixed recently.)
* No reminders or notifications for tasks that are due soon. No indication if a task is overdue.
* Can't see or edit task hiearchy when sorted by deadline.
* No search function, so it can be difficult to find a specific task when the user has a lot of tasks.

**Trello**

Author: Dylan

Pros:

* A well constructed GUI
* Logical flow of task construction
* A subtle reminder that involves changes in the layout of the taskcard
* Allows the sharing of information about a task with others

Cons:

* No calendar
* No progress chart to indicate the amount of tasks completed


**Wunderlist**

Author: Ian

Pros:

* Pleasant, detailed GUI
* Adding a task is quick, and allows for adding a flexible deadline
* Allows tasks to have extra details and information
* Organises everything into different lists
* Marking a task as completed is met with a satisfying jingle
* Reminders are present

Cons:

* No tutorial for new users
* Many features are locked behind a paywall
