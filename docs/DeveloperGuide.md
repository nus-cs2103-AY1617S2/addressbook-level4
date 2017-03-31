# Savvy To-Do - Developer Guide

By : `Team F12-B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

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

`Main` has only one class called [`MainApp`](../src/main/java/savvytodo/MainApp.java). It is responsible for,

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

Author: Yee Jian Feng, Eric

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

Author: Alice Bee

<img src="images/UiClassDiagram.png" width="800"><br>
_Figure 2.2.1 : Structure of the UI Component_

**API** : [`Ui.java`](../src/main/java/savvytodo/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `TaskListPanel`,
`StatusBarFooter`, `BrowserPanel` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/savvytodo/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component,

* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raised from various parts of the App and updates the UI accordingly.

### 2.3. Logic component

Author: Yee Jian Feng, Eric

<img src="images/LogicClassDiagram.png" width="800"><br>
_Figure 2.3.1 : Structure of the Logic Component_

**API** : [`Logic.java`](../src/main/java/savvytodo/logic/Logic.java)

1. `Logic` uses the `Parser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a Task) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
 API call.<br>
<img src="images/DeleteTaskSdForLogic.png" width="800"><br>
_Figure 2.3.1 : Interactions Inside the Logic Component for the `delete 1` Command_

### 2.4. Model component

Author: Yee Jian Feng, Eric

<img src="images/ModelClassDiagram.png" width="800"><br>
_Figure 2.4.1 : Structure of the Model Component_

**API** : [`Model.java`](../src/main/java/savvytodo/model/Model.java)

The `Model`,

* stores a `UserPref` object that represents the user's preferences.
* stores the Task Manager data.
* exposes a `UnmodifiableObservableList<ReadOnlyTask>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

### 2.5. Storage component

Author: Darius Foong

<img src="images/StorageClassDiagram.png" width="800"><br>
_Figure 2.5.1 : Structure of the Storage Component_

**API** : [`Storage.java`](../src/main/java/savvytodo/storage/Storage.java)

The `Storage` component,

* can save `UserPref` objects in json format and read it back.
* can save the Task Manager data in xml format and read it back.

### 2.6. Common classes

Classes used by multiple components are in the `savvytodobook.commons` package.

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
      e.g. `savvytodo.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units
     (those code units are assumed to be working).<br>
      e.g. `savvytodo.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as
      how the are connected together.<br>
      e.g. `savvytodo.logic.LogicManagerTest`

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
    e.g. For [UserGuide.md](UserGuide.md), the URL will be `https://<your-username-or-organization-name>.github.io/<repository-name>/docs/UserGuide.html`.
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
`* * *` | user | add a new [task](#task) | record task that needs to be completed
`* * *` | user | update a task | modify the details of the task without deletion and re-addition
`* * *` | user | delete a task | remove entries that I no longer need
`* * *` | user | find a task by some (case-insensitive) keywords | locate similar tasks without having to go through the entire list
`* * *` | user | mark a completed task done | know this task has been completed
`* * *` | user | view list of ongoing tasks| know what tasks need to be done
`* * *` | user | view list of archived tasks| know what has been completed
`* * *` | user | sort ongoing tasks by due date or priority | know what has been completed and upcoming schedule
`* * *` | user | change storage location | potentially move the storage to a folder that I can access from the cloud and use it on other devices
`* *` | user | undo the recent command | revert the changes quickly
`* *` | user | view the calendar view | better plan my tasks
`* *` | user | view selected day summary of tasks | know what tasks need to be done for that day
`* *` | advanced user | use keyboards commands/hot keys | quickly achieve what I want without typing
`* *` | advanced user | have [alias](#alias) for keywords | quickly type what I want
`*` | user | set recurring task | not add a similar task each time
`*` | user | have smart autocomplete suggestions as I enter my task | speed up the process of creating a task

## Appendix B : Use Cases

(For all use cases below, the **System** is the `SavvyToDo` and the **Actor** is the `user`, unless specified otherwise)

### Use case: Add task

**MSS**

1. User enters command to add task according to parameters
2. System adds the task to a list of tasks <br>
Use case ends.

**Extensions**

1a. User input is invalid
> 1a1. System shows an error message <br>
> Use case resumes at step 1

1b. START_DATE and END_DATE are different, the RECURRING_TYPE has to be larger than the duration between START_DATE and END_DATE. (e.g. A 3d2n trip can be recurring weekly, not daily)
> 2b1. System shows an error message <br>
> Use case resumes at step 1

1c. START_DATE and END_DATE are different, END_DATE is before START_DATE
> 1c1. System shows an error message <br>
> Use case resumes at step 1

1d. START_DATE and END_DATE are the same, END_TIME is before START_TIME
> 1d1. System shows an error message <br>
> Use case resumes at step 1

### Use case: List tasks

**MSS**

1. User requests to list tasks
2. System shows a list of tasks <br>
Use case ends.

**Extensions**

2a. The list is empty

> 2a1. System shows an alert message <br>
> Use case ends

### Use case: Search task

**MSS**

1. User requests to find tasks by keyword
2. System displays the list of tasks that contains the keyword in the name<br>
Use case ends.

**Extensions**

1a. No parameter entered after command word
> System shows a 'no parameter entered' error message.<br>
> Use case resumes at step 1


3a. The list is empty
> 2a1. System shows a 'no task found' alert message.<br>
> Use case ends

### Use case: Modify task

**MSS**

1. User requests to list tasks
2. System shows a list of tasks
3. User requests to modify certain attribute(s) of a specific task
4. System modifies the task and saves it <br>
Use case ends.

**Extensions**

2a. The list is empty

> 2a1. Use case ends

3a. The given index is invalid

> 3a1. System shows an error message <br>
  Use case resumes at step 2

3b. At least one parameter entered by user is invalid

> 3b1. System shows an error message and display the expected format <br>
  Use case resumes at step 2

### Use case: Delete task

**MSS**

1. User requests to list tasks
2. System shows a list of tasks
3. User requests to delete a specific task in the list
4. System deletes the task <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. System shows an error message <br>
  Use case resumes at step 2


### Use case: Mark task as completed

**MSS**

1. User requests to list tasks
2. System shows a list of tasks
3. User request to mark specific tasks in the list based on task's index
4. System marks the tasks, removes it from the task list, and adds it to the Archived list<br>
Use case ends.

**Extensions**

2a. The list is empty
> 2a1. System shows a 'no task found' alert message.<br>
> Use case ends

3a. The given index is invalid
> 3a1. System shows a 'invalid index' error message <br>
> Use case resumes at step 1

3b. The task is already marked as done
> 3b1. System shows a 'task already marked' information message.<br>
> Use case resumes at step 1

### Use case: Unmark marked task

**MSS**

1. User requests to list archived tasks
2. System displays a list of archived tasks, sorted by time and date the task has been marked
3. User requests to unmark the specific task in the list based on task's index
4. System removes the marked status of the specific task, removes it from the Archived list, and adds it back to the task list <br>
Use case ends.

**Extensions**

1a. The list is empty
> Use case ends

3a. The given index is invalid
> 3a1. System shows a 'invalid index' error message <br>
> Use case resumes at step 1

### Use case: Change storage location

**MSS**

1. User requests to change storage location
2. System prompts for storage location and file name
3. User selects storage location and file name
4. System saves tasks into new location and shows user a complete message<br>
Use case ends.

**Extensions**

3a. Error using given storage location

> 3a1. System shows an error message
> Use case ends

### Use case: Alias keyword

**MSS**

1. User requests to alias a keyword (can be a command or any other frequently used word)
2. System store the shorten keyword associated with the keyword in its database
3. User request a command
4. System check if the command contain any shorten keyword, if it does, replace the shorten keyword with the associated keyword from its database
5. System carry out the command <br>
Use case ends.

**Extensions**

1a. The alias keyword contains only 1 character
> 1a1. System shows a error message
> Use case resumes at step 1 <br>

1b. The alias keyword has already been associated with other keywords
> 1b1. System shows a error message and the alias keyword's original associated keyword
> Use case resumes at step 1 <br>

### Use case: Unalias keyword

**MSS**

1. User requests to unalias a keyword
2. System remove the alias keyword associated with the keyword in its database <br>
Use case ends.

**Extensions**

1a. The shorten keyword could not be found in System database
> 1a1. System shows a 'not found' error message
> Use case resumes at step 1 <br>

### Use case: Undo previous command

**MSS**

1. User requests to undo last executed command
2. System undos the last executed command to return to the state before that command was executed <br>
Use case ends.

**Extensions**

1a. There is no previously executed command to undo
> 1a1. System shows a 'last command does not exist' error message <br>
> Use case ends

## Appendix C : Non Functional Requirements

### System
1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 tasks without a noticeable sluggishness in performance for typical usage.
3. Should work without requiring an installer.
4. Should come with automated unit tests and open source code.
5. Should favor DOS style commands over Unix-style commands.
6. Should store data in text file.
7. Should work stand-alone and should not be a plug-in to another software.
8. Should work without internet connection.

### User Experience
1. A user with above average typing speed for regular English text (i.e. not code, not system admin commands)
   should be able to accomplish most of the tasks faster using commands than using the mouse.
2. Hotkeys/shortcuts should be intuitive so that users can remember them easily.

### Development
1. All code should be properly documented.

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Task

> * Event: Task that has a start time and end time
> * Deadline: Task that has to be done before a specific deadline
> * Floating Task: Task without specific times

##### Alias

> For instance, 'ppt' stands for 'presentation'

## Appendix E : Product Survey

**Wunderlist**

Pros:
* Allows creation of subtasks within a task.
* Allows local storage, in case there is no internet access.
* Allows the grouping of tasks as a list and even grouping into folders.
* Can sync across devices by signing in.
* Can invite other person(s), sharing the tasks with them.

Cons:
* No calendar view.
* Cannot block slots.
* Cannot ordered by due date.

**TickTick**

Pros:
* Allow voice or Siri as input
* Supports up to 11 platforms
* Automatic categorization of tasks into `Overdue`, `Today`, `Next 7 days` and `Complete`

Cons:
* Requires internet connection to use

**Trello**

Pros:
* Always in sync with all your devices
* Provides a board filled with "cards", that can used by the user or a team to organize projects
* Add comments, upload file attachments, create checklists, add labels and due dates on a Trello card
* Custimizable looks

Cons:
* Limited file attachment size
* Free version has limited customization features

**Google Calender**

Pros:
* Simple and intuitive UI
* Multiple calenders for different purposes
* Able to access the calender when offline

Cons:
* Only available as an web app on computers
* No command line inputs


**The (really) Big Table of Comparison**

> An extensive table of products comparison can be found [here (credits: Reddit/r/productivity)] (https://docs.google.com/spreadsheets/d/1yP6ZOXMWK-KsMZ-_Bod8tFhyEGdPndCu4HUBO_3rUww/edit#gid=0).
