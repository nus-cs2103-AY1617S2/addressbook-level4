# FlexiTask - Developer Guide

By : `Team W14-B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Setting Up](#1-setting-up)
2. [Design](#2-design)
3. [Implementation](#3-implementation)
4. [Testing](#4-testing)
5. [Dev Ops](#5-dev-ops)

* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b-use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Glossary](#appendix-d--glossary)
* [Appendix E : Product Survey](#appendix-e--product-survey)

<br>
FlexiTask is a task manager that helps users to manage schedules and tasks using simple command-line interfaces commands.

This guide describes the design and implementation of FlexiTask. This developer guide is for both existing and new developers who are interested in working on FlexiTask in the future.

It will walk you through the Setup, Architecture, APIs and the details regarding the different components of the program.

<br>
## 1. Setting up

### 1.1. Prerequisites

1. **JDK `1.8.0_60`**  or later<br>

    > Having any Java 8 version is not enough. <br>
    This app will not work with earlier versions of Java 8.

2. **Eclipse** IDE
3. **e(fx)clipse** plugin for Eclipse (Do the steps 2 onwards given in
   [this link](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious))
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
      (This is because Gradle downloads library files from servers during the project setup process)
  > * If Eclipse auto-changed any settings files during the import process, you can discard those changes.

### 1.3. Configuring Checkstyle
1. Click `Project` -> `Properties` -> `Checkstyle` -> `Local Check Configurations` -> `New...`
2. Choose `External Configuration File` under `Type`
3. Enter an arbitrary configuration name e.g. flexiTask
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
_Figure 1: Architecture Diagram_

Figure 1 given above explains the high-level design of FlexiTask.
Given below is a quick overview of the main components and their main responsibilities.

#### 2.1. Main
`Main` has only one class called [`MainApp`](../src/main/java/seedu/tasklist/MainApp.java). It is responsible for,

* Initializing the components in the correct sequence, and connects them up with each other when FlexiTask launches 
* Shutting down the components and invokes cleanup method where necessary, when FlexiTask shuts down.

#### 2.2. Commons
[**`Commons`**](#26-common-classes) represents a collection of classes used by multiple other components.
Two of those classes play important roles at the architecture level.

* `EventsCenter` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design)
* `LogsCenter` : Used by many classes to write log messages to the App's log file.

The rest of the App consists of four components.

* [**`UI`**](#22-ui-component) : The UI of the App.
* [**`Logic`**](#23-logic-component) : The command executor.
* [**`Model`**](#24-model-component) : The in-memory representation of the task list.
* [**`Storage`**](#25-storage-component) : The component responsible for reading data from, and writing data to, the hard disk.
<br>


Each of the four components

* Defines its _API_ in an `interface` with the same name as the Component.
* Exposes its functionality using a `{Component Name}Manager` class.
<br>

For example, the `Logic` component (see the class diagram given below) defines it's API in the `Logic.java`
interface and exposes its functionality using the `LogicManager.java` class.<br>

<img src="images/LogicClassDiagram.png" width="800"><br>
_Figure 2: Class Diagram of the Logic Component_

#### Events-Driven nature of the design

Figure 3 below shows how the components interact for the scenario where the user issues the
command `delete 1`.

<img src="images/SDforDeleteTask.png" width="600">
_Figure 3: Component Interactions for `delete 1` Command (Part 1)_

>Note how the `Model` simply raises a `TaskListChangedEvent` when FlexiTask data are changed,
 instead of asking the `Storage` to save the updates to the hard disk.

Figure 4 below shows how the `EventsCenter` reacts to that event, which eventually results in the updates
being saved to the hard disk and the status bar of the UI being updated to reflect the 'Last Updated' time.
<img src="images/SDforDeleteTaskEvent.png" width="800"><br>
_Figure 4: Component Interactions for `delete 1` Command (Part 2)_

> Note how the event is propagated through the `EventsCenter` to the `Storage` and `UI` without `Model` having
  to be coupled to either of them. This is an example of how this Events-Driven approach helps us reduce direct
  coupling between components.

The sections below give more details of each component.

### 2.2. UI component


<img src="images/UiClassDiagram.png" width="800"><br>
_Figure 5: Structure of the UI Component_

**API** : [`Ui.java`](../src/main/java/seedu/tasklist/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `TaskListPanel`,
`StatusBarFooter`, `BrowserPanel` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/tasklist/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component,

* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raised from various parts of the App and updates the UI accordingly.

### 2.3. Logic component


<img src="images/LogicClassDiagram.png" width="800"><br>
_Figure 6: Structure of the Logic Component_

Figure 6 shows how different components interact with each other.<br>
**API** : [`Logic.java`](../src/main/java/seedu/tasklist/logic/Logic.java)

* `Logic` uses the `Parser` class to parse the user command.
* This results in a `Command` object which is executed by the `LogicManager`.
* The command execution can affect the `Model` (e.g. adding a task) and/or raise events.
* The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

Figure 7 below shows the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
 API call.<br>
<img src="images/DeletePersonSdForLogic.png" width="800"><br>
_Figure 7: Interactions Inside the Logic Component for the `delete 1` Command_

### 2.4. Model component


<img src="images/ModelClassDiagram.png" width="800"><br>
_Figure 8: Structure of the Model Component_

Figure 8 shows the dependency of the classes within the `Model` and how they depend on each other.<br>

**API** : [`Model.java`](../src/main/java/seedu/tasklist/model/Model.java)

The `Model` has the following features:

* stores a `UserPref` object that represents the user's preferences.
* stores the FlexiTask data.
* exposes a `UnmodifiableObservableList<ReadOnlyTask>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* stays independent on any of the other three components.

### 2.5. Storage component


<img src="images/StorageClassDiagram.png" width="800"><br>
_Figure 2.5.1 : Structure of the Storage Component_

**API** : [`Storage.java`](../src/main/java/seedu/tasklist/storage/Storage.java)

The `Storage` component,

* can save `UserPref` objects in json format and read it back.
* can save the FlexiTask data in xml format and read it back.

### 2.6. Common classes

Classes used by multiple components are in the `seedu.tasklist.commons` package.<br>
They are divided into four sub-packages, namely `core`, `events`, `exceptions` and `util`.

* `core` - consists of the essential classes that are required by multiple components.
* `events` - consists of the different type of events that can occue; these are used mainly by `EventManager` and `EventBus`.
* `exceptions` - consists of exceptions that may occur with the use of the program.
* `util` - consists of additional utilities for the different components.

## 3. Implementation

### 3.1. Logging

We are using `java.util.logging` package for logging. The `LogsCenter` class is used to manage the logging levels
and logging destinations.

> The logging level can be controlled using the `logLevel` setting in the configuration file
  (See [Configuration](#32-configuration))<br>
  
> The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to
  the specified logging level<br>
  
> The log messages are currently output through: `Console` and to a `.log` file.

<br>

Currently, FlexiTask has 4 logging levels: `SEVERE`, `WARNING`, `INFO` and `FINE`. They record information pertaining to: 

* `SEVERE` : Critical problem detected which may possibly cause the termination of the application
* `WARNING` : Can continue, but with caution
* `INFO` : Information showing the noteworthy actions by the App
* `FINE` : Details that is not usually noteworthy but may be useful in debugging
  e.g. print the actual list instead of just its size

### 3.2. Configuration

Certain properties of the application can be controlled (e.g App name, logging level) through the configuration file
(default: `config.json`).


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
      e.g. `seedu.tasklist.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units
     (those code units are assumed to be working).<br>
      e.g. `seedu.tasklist.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as
      how the are connected together.<br>
      e.g. `seedu.tasklist.logic.LogicManagerTest`
      
3. **Headless GUI Testing** - These are test involving GUI.
  [TestFX](https://github.com/TestFX/TestFX) library allows GUI tests to be run in the _headless_ mode.
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

A project often depends on third-party libraries. For example, FlexiTask depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | add tasks | keep track of them
`* * *` | user | be able to edit a task after adding it | make any changes if I need to
`* * *` | user | be able to delete a task after adding | delete tasks that I no longer need
`* * *` | user | find specific tasks by keywords | locate my task easily
`* * *` | user | specify a particular path or folder to store my task lists | retrieve the file easily and conveniently
`* * *` | user | undo the recent action | revert in case I decide to change my mind.
`* * *` | user | list all tasts I created | so that I can plan my day more efficiently.
`* *` | user | add tasks that recur on a consistent basis (e.g. tutorials and lectures) | add recurring tasks more conveniently, since the item only needs to be added once
`* *` | user | add some comments for a particular task | store additional information that I will need when handling that task
`* *` | user | include a tag on the task | retrieve all the tasks that belong to the same tag
`* *` | user | be able to set how important a task is | know which tasks I should prioritize first
`* *` | user | sort my tasks according to priority or tags | delegate my time appropriately
`* *` | user | be able to delete a group of task by keywords | avoid deleting one by one
`* *` | user | have a view of my pending tasks for the day/week/month | know what I need to do for that particular period of time
`* *` | user | see my overdue tasks | know what actions to take on them
`* *` | advanced user | have the choice of a few preset themes | change it to my liking
`*` | user | sort my tasks according to task names | delegate my time appropriately
`*` | user | delete all my tasks | hand in my resignation letter afterwards


## Appendix B: Use Cases

(For all use cases below, the **System** is FlexiTask and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Create a task

**MSS**

1. User inputs the command with the correct keywords
2. FlexiTask confirms the addition by showing the confirmation message <br>

> Use case ends.

**EXTENSIONS**

2a. The given input is invalid (Wrong number of dates entered, wrong type of priority entered, etc)

> 2a1. FlexiTask shows an error message <br>
Use case ends

#### Use case: Find specific tasks

**MSS**

1. User inputs the command with the correct keywords
2. FlexiTask shows the list of tasks that match the input <br>

> Use case ends.

**EXTENSIONS**

2a. The given input is invalid

> 2a1. FlexiTask shows an error message <br>
Use case ends

3a. The list is empty
> FlexiTask displays "No task found" message
> Use case ends

4a. The list contains no matching entries

> Use case ends

#### Use case: Edit/Update a specific task

**MSS**

1. User requests to edit a task and inputs the command with correct keywords
2. FlexiTask updates the task with new details and displays a confirmation of the operation on the screen<br>

> Use case ends.

**EXTENSIONS**

2a. The given input is invalid

> 2a1. FlexiTask shows an error message for such situation<br>
Use case ends

3a. The list contains no matching entries for the user to edit
> 3a1. FlexiTask shows an error message for such situation <br>

> Use case ends

#### Use case: Delete a specific task from the task list

**MSS**

1. User requests to delete a task and inputs the command with correct keywords
2. FlexiTask shows a message to ask for confirmation of deletion
3. User inputs the command for confirmation
4. FlexiTask deletes the task <br>

> Use case ends

**EXTENSIONS**

2a. The given input is invalid

> 2a1. FlexiTask shows an error message for such situation<br>

> Use case ends

3a. The list contains no matching entries for the user to edit
> 3a1. FlexiTask shows an error message for such situation <br>

> Use case ends

4a. The list is empty
> "0 task listed" will be displayed.

> Use case ends

#### Use case: Undo an action

**MSS**

1. User requests to undo
2. FlexiTask undos the latest action by the user

> Use case ends

**Extensions**
1a. No action to undo
> 1a1. FlexiTask shows an error message for no action to undo

> Use case ends

#### Use case: Redo an action

**MSS**

1. User requests to redo
2. FlexiTask reverses the last undo done by user

**Extensions**
1a. No action to redo
> 1a1. FlexiTask shows an error message for no action to redo

> Use case ends

#### Use case: Clearing FlexiTask

**MSS**

1. User requests to clear the FlexiTask
2. FlexiTask clears all the tasks


## Appendix C : Non Functional Requirements

1. Should work on any mainstream OS as long as it has Java 8 or higher installed.
2. Should be able to hold up to 1000 tasks.
3. Should come with automated unit tests and open source code.
4. Should favor DOS style commands over Unix-style commands.
5. Should be able to hold up to 100 tags per task.

## Appendix D : Glossary

#### Mainstream OS

> Windows, Linux, Unix, OS-X

#### Keywords
> Task Name, Tag, From Date - To Date, Due Dates

#### Path
> Absolute path or Relative path of the Tasks Storage

####DOS style commands

> commandline commands based on Windows System

####UNIX-style commands

> commandline commands based on UNIX System

####Headless Mode:
> In the headless mode, GUI tests do not show up on the screen
> This means you can do other things on the computer while the tests are running

## Appendix E : Product Survey

**Google Calendar + Google Tasks**

Author: Wang Pengcheng

Pros:
* Has a simplistic and elegant UI
* Has the ability to add task items directly from Gmail into Google Task.
* Does not have a lot of buttons to click. Convenient and easy to learn.
* Allows different colour codings for easy references.
* Allows the user to import other existing calendars (e.g. NUSmods) into the user's calendar.

Cons:
* Requires Internet connection for usage.
* Needs improvement on the UI for Google Tasks; The deletion of items is quite a hassle and can be messy.
* Requires mouse usage instead of CLI commands, which may not cater to Jim who prefers keyboard inputs.

**Priority Matrix**

Author: Ellango Vesali

Pros:
* Has a simple and easy to use UI
* Creates multiple task under a main project
* Groups the list of items based on the different levels of priorities
* Integrates with Mail to keep track of important mails
* Syncs with iOS, Android and Windows
* Uploads files
* Has daily reports to remind one of their deadlines
* Has collaborative aspects for teams

Cons:
* Requires mouse usage instead of CLI commands, which may not cater to Jim who prefers keyboard inputs.
* Requires a monthly subscription fee

**Assembla**

Author: Lim Jie

Pros:
* Caters to advanced coding purposes
* Allows collaboration with different project groups at the same time
* Has three types of communication channel for different purposes and importance
* Connects to different software such as Git, Goggle Drive, Dropbox etc.
* Allows tracking of issues and milestones from Github projects
* Has extensive support portal that offers 3 level of assistance

Cons:
* Lacks mobile application
* Has a steep learning curve
* Imposes group pricing rather than individual pricing

**Wunderlist**

Author: Sherina Toh Shi Pei

Pros:
* Has an intuitive UI
* Allows categories to be created where tasks can be placed in
* Allows subtasks, notes and files to be added to a task
* Allows for collaboration on tasks with other people
* Allows users to set a reminder for their tasks
* Shows a daily and weekly overview of all tasks
* Has a large number of themes for user to choose from to suit their preferences

Cons:
* Requires internet to access
* Requires mouse usage instead of CLI commands, which may not cater to Jim who prefers keyboard inputs.
