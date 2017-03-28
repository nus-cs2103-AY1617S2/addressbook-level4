# Docket - Developer Guide

By : `Team SE-EDU`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jun 2016`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Setting Up](#setting-up)
2. [Design](#design)
3. [Implementation](#implementation)
4. [Testing](#testing)
5. [Dev Ops](#dev-ops)

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

`Main` has only one class called [`MainApp`](../src/main/java/seedu/address/MainApp.java). It is responsible for:

* At app launch, initializes the components in the correct sequence, and connects them up with each other.
* At shut down, shuts down the components and invokes cleanup method where necessary.
* Upon storage file change event, restarts the application.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.
Two of those classes play important roles at the architecture level.

* `EventsCenter` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design)
* `LogsCenter` : This class is used by many classes to write log messages to the App's log file.

The rest of the App consists of four components.

* [**`UI`**](#ui-component) : Displays the interface of the App.
* [**`Logic`**](#logic-component) : Executes commands.
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

<img src="images/UiClassDiagram.png" width="800"><br>
_Figure 2.2.1 : Structure of the UI Component_

**API** : [`Ui.java`](../src/main/java/seedu/address/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `TaskListPanel`, `TaskGroupPanel`, 
`StatusBarFooter`, `BrowserPanel` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/address/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component

* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raised from various parts of the App and updates the UI accordingly.

### 2.3. Logic component

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

<img src="images/ModelClassDiagram.png" width="800"><br>
_Figure 2.4.1 : Structure of the Model Component_

**API** : [`Model.java`](../src/main/java/seedu/address/model/Model.java)

The `Model`

* stores a `UserPref` object that represents the user's preferences.
* stores the Task Manager data.
* exposes a `UnmodifiableObservableList<ReadOnlyTask>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

### 2.5. Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>
_Figure 2.5.1 : Structure of the Storage Component_

**API** : [`Storage.java`](../src/main/java/seedu/address/storage/Storage.java)

The `Storage` component

* can save `UserPref` objects in json format and read it back.
* can save the Task Manager data in xml format and read it back.

### 2.6. Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

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

### 3.3. Date handling

The following diagram explains the relationships between classes in seedu.addressbook.model.task.date, which are responsible for date and time handling.

<img src="images/DateDiagram.png" width="800"><br>
_Figure 2.5.1 : Structure of the Storage Component_

We make use of Natty for parsing dates in natural language.

The reason to have DateOnly and DateTime is that we need a way to distinguish between dates that contain a specific time (e.g. 10:00 am) and those do not. Processing and formatting dates of these two types can be very different.

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

A project often depends on third-party libraries. For example, TaskManager depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | New user | See usage instructions | Refer to the instructions when i forget how to use the App
`* * *` | user | Add a new task | Keep track of the task in the App
`* * *` | user | Delete a task | Remove entries I no longer need
`* * *` | user | Find a task by date | Locate task without having to go through the entire list
`* * *` | user | Find a task by task name | Locate date of task without having to go through entire list
`* * *` | user | Copy a task from one date to another | Assign the same task to another date
`* * *` | user | Modify a task name | Update the task name in the App when the task name changes
`* * *` | user | Modify a task details | Update the task details in the App when the task details changes
`* * *` | user | Modify a task date | Update the task date in the App when the task date changes
`* * *` | user | Set a task as private | Prevent others from seeing my schedule
`* * *` | user | Undo the most recent command | Easily revert mistakes
`* * *` | user | Specify the location for data storage | Store the tasks in an appropriate and/or secure location
`* * *` | user | Specify the file for data storage | Store the tasks in an appropriate and/or secure location
`* *` | user | Mark a task as completed | Will not do the same task twice
`* *` | user | Assign priority to the task entry | Prioritize which task to be done first
`*` | user | Sort the list of tasks by name | Locate a task by name easily 
`*` | user | Sort the list of tasks by details | Locate a task by details easily 
`*` | user | Sort the list of tasks by date | Locate a task by date easily 
`*` | user | Delete all tasks in the App | Start a new list easily
`*` | user | Add a single task to multiple dates at the same time | Waste less time
`*` | user | Search for a list of tasks by priority | Find tasks by priority easily
`*` | user | Sort the list of tasks by priority | Locate the important tasks to complete easily
`*` | user | Redo the most recent command | Repeat a command easily
`*` | user | Modify multiple task dates | Quickly update similar task deadlines easily
`*` | user | Modify multiple task priorities | Quickly update similar task priority easily
`*` | user | Be reminded of tasks that are due soon | Will not forget to do to task
`*` | user | Be issued an appropriate task when i am free with only one command | Quickly get started on the task
`*` | user | Have a quick display of the number of undone tasks | Have a rough gauge of how much I have left to be done
`*` | user | Be able to set flexible deadlines (eg. This month, this week, today, tomorrow) | Easily set deadlines for my tasks
`*` | user | Set username & password | Prevent others from seeing my schedule & modify my data
`*` | user | Import my data from Google Calendar | Save time
`*` | user | Have a backup of my tasks | Easily restore my tasks if the software is removed
`*` | user | Export by tasks as a pdf,jpeg,...  | Print out my schedule when needed

{More to be added} 
## Appendix B : Use Cases

(For all use cases below, the **System** is the `TaskManager` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Create task

**MSS**

1. User requests to add new task
2. Application asks for confirmation to add new task
3. User confirms to add new task
4. Application adds new task
Use case ends

**Extensions**

1a. User enters invalid input format

> Application shows an error message
  Use case resumes at step 1

1b.   Task name and date already exists in list

> Application notifies user that task already exists for the given date
  Use case resumes at step 1

3a.  User cancels add on of new task

> Use case ends

#### Use case: Update task

**MSS**

1. Use requests to list tasks
2. Application shows a list of tasks
3. User requests to update a specific task in the list
4. Application updates the task and shows new changes
Use case ends.

**Extensions**

2a. Task list is empty

> Use case ends

3a.   Invalid input format 

> Application shows an error message
  Use case resumes at step 2

3b.   Task name and date already exists in list

> Application notifies user that task already exists for the given date
  Use case resumes at step 2


#### Use case: Delete task

**MSS**

1. User requests to list tasks
2. Application shows a list of tasks
3. User requests to delete a specific task in the list
4. Application deletes the task
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. Application shows an error message <br>
  Use case resumes at step 2

#### Use case: Undo command

**MSS**

1. User requests to undo most recent command
2. Application asks for confirmation to undo most recent command
3. User confirms to undo most recent command
4. Application undo most recent command
Use case ends

**Extensions**

1a. There are no most recent commands that changes the data of task list

> Application shows an error message
> Use case ends

3a.  User cancels undo most recent command

> Use case ends

#### Use case: Sort tasks list

**MSS**

1. User requests to list tasks by a certain order
2. Application shows list of tasks by given order
Use case ends

**Extensions**

1a. User inputs invalid order

> Application displays error message
  Use case resumes at step 1

2a. Task list is empty

> Application displays error message
  Use case ends

{More to be added}

## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1024 tasks without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands)
   should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Should use only 10MB of memory or less. 

{More to be added}

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Password

> String of characters that authenticates a user

##### Username

> String of characters that identifies a user

##### Java (.java) file

> Application file which stores the application

##### Commands

> Inputs that the user enters to interact with the application

##### Hard drive

> Data storage device

##### Data 

> Digital information that is stored/used by the application

##### Megabyte (MB)

> Multiple of data storage unit

##### Memory

> Temporary storage space for application to run

##### MSS 

> Main success scenario

##### Storage space

> Available space in hard disk

##### Portable Document Format (PDF)

> One type of file format

##### Joint Photographics Expert Group (JPEG)

> One type of image file format

## Appendix E : Product Survey

**Wunderlist**

Author: Marianne

Pros:

* Calendar Feed, able to export to google calendar
* Tasks need not necessarily need a deadline
* Easy input, using only task name
* Subtasks function
* Desktop and mobile application
* Notifications
* Bookmark task function
* Able to add notes to each task
* Customisable themes

Cons:

* Requires subscription for unlimited subtasks, file uploads and assignment of tasks

