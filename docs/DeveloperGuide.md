# Developer Guide
&nbsp;

## Table of contents

1. [Introduction](#intoduction)  <br>
2. [Setting Up](#setting-up)  <br>
    2.1. [Prerequisites]() <br>
    2.2. [Importing the project into Eclipse]()  <br>
    2.3. [Configuring Checkstyle]()  <br>
    2.4. [Troubleshooting Project Setup]()  <br>
3. [Design](#design) <br>
    3.1. [Architecture]() <br>
    3.2. [UI Component]() <br>
    3.3. [Logic Component]() <br>
    3.4. [Model Component]() <br>
    3.5. [Storage Component]() <br>
    3.6. [Common Classes]() <br>
4. [Implementation](#implementation) <br>
    4.1. [Logging]() <br>
    4.2  [Configuration]() <br>
5. [Testing](#testing) <br>
    5.1 [Troubleshooting tests]() <br>
6. [Dev Ops](#dev-ops) <br>
    6.1 [Build Automation]() <br>
    6.2 [Continuous Integration]() <br>
    6.3 [Publishing Documentation]() <br>
    6.4 [Making a Release]() <br>
    6.5 [Converting Documentation to PDF format]() <br>
    6.6 [Managing Dependencies]() <br>
7. [Appendix A: User Stories](#appendix-a--user-stories) <br>
8. [Appendix B: Use Cases](#appendix-b--use-cases) <br>
9. [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements) <br>
10. [Appendix D: Glossary](#appendix-d--glossary) <br>
11. [Appendix E : Product Survey](#appendix-e--product-survey) <br>
&nbsp;

## 1. Introduction

TypeTask is a task manager for active users who prefer to manage their tasks and plans by keyboard commands. TypeTask works on a Java desktop application that has a graphical user interface (GUI) executed with JavaFX. <br>

This guide illustrates the design and implementation of TypeTask. It will guide you as a developer to understand how TypeTask functions and show you how to be a part of its progress.  <br>

&nbsp;

## 2. Setting up

### 2.1. Prerequisites

1. **JDK `1.8.0_60`**  or later<br>

    > Having any Java 8 version is not enough. <br>
    This app will not work with earlier versions of Java 8.

2. **Eclipse** IDE

3. **e(fx)clipse** plugin for Eclipse (Do the steps 2 onwards given in
   [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious))
   
4. **Buildship Gradle Integration** plugin from the Eclipse Marketplace

5. **Checkstyle Plug-in** plugin from the Eclipse Marketplace

### 2.2. Importing the project into Eclipse

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

### 2.3. Configuring Checkstyle

1. Click `Project` -> `Properties` -> `Checkstyle` -> `Local Check Configurations` -> `New...`

2. Choose `External Configuration File` under `Type`

3. Enter an arbitrary configuration name e.g. taskmanager

4. Import checkstyle configuration file found at `config/checkstyle/checkstyle.xml`

5. Click OK once, go to the `Main` tab, use the newly imported check configuration.

6. Tick and select `files from packages`, click `Change...`, and select the `resources` package

7. Click OK twice. Rebuild project if prompted

> Note to click on the `files from packages` text after ticking in order to enable the `Change...` button

### 2.4. Troubleshooting project setup

**Problem: Eclipse reports compile errors after new commits are pulled from Git**

* Reason: Eclipse fails to recognize new files that appeared due to the Git pull.
* Solution: Refresh the project in Eclipse:<br>
  Right click on the project (in Eclipse package explorer), choose `Gradle` -> `Refresh Gradle Project`.

**Problem: Eclipse reports some required libraries missing**

* Reason: Required libraries may not have been downloaded during the project import.
* Solution: [Run tests using Gradle](UsingGradle.md) once (to refresh the libraries).
&nbsp;

## 3. Design

### 3.1. Architecture

<img src="images/Architecture.png" width="600"><br>

_Figure 3.1.1 : Architecture Diagram_

The **_Architecture Diagram_** given above explains the high-level design of the App.
Given below is a quick overview of each component.

[comment]: # (> Tip: The `.pptx` files used to create diagrams in this document can be found in the [diagrams](diagrams/ folder.)
[comment]: # (> To update a diagram, modify the diagram in the pptx file, select the objects of the diagram, and choose `Save as picture`.)

#### `Main`
The **`Main`** has only one class called [`MainApp`](../src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup method where necessary.


#### `Commons`
[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.
Two of those classes play important roles at the architecture level.

* `EventsCenter` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design)
* `LogsCenter` : Used by many classes to write log messages to the App's log file.


#### `UI`
The [**`UI`**](#ui-component) component interacts with the user by receiving commands and revealing information. <br>


#### `Logic`
The [**`Logic`**](#logic-component) component processes and executes the user's commands. <br>


#### `Model`
The [**`Model`**](#model-component) component signifies and holds TypeTask's information. <br>


#### `Storage`
The [**`Storage`**](#storage-component) component component reads data from and writes data to the hard disk. <br>


Each of the `UI`, `Logic`, `Model` and `Storage` components:

* Defines its _API_ in an `interface` with the same name as the Component.
* Exposes its functionality using a `{Component Name}Manager` class.

For example, the `Logic` component ([Figure 3.3.1](#logic)) defines its API in the `Logic.java`
interface and exposes its functionality using the `LogicManager.java` class. <br>


#### Events-Driven nature of the design

The _Sequence Diagram_ below shows how the components interact for the scenario where the user issues the
command `delete 1`.

<img src="images\SDforDeletePerson.png" width="800"><br>

_Figure 3.1.1a : Component interactions for `delete 1` command (part 1)_

>Note how the `Model` simply raises a `TaskManagerChangedEvent` when the Task Manager data are changed,
 instead of asking the `Storage` to save the updates to the hard disk.

The diagram below shows how the `EventsCenter` reacts to that event, which eventually results in the updates
being saved to the hard disk and the status bar of the UI being updated to reflect the 'Last Updated' time. <br>

<img src="images\SDforDeletePersonEventHandling.png" width="800"><br>

_Figure 3.1.1b : Component interactions for `delete 1` command (part 2)_

> Note how the event is propagated through the `EventsCenter` to the `Storage` and `UI` without `Model` having
  to be coupled to either of them. This is an example of how this Event Driven approach helps us reduce direct
  coupling between components.

The sections below will give more details of each single component.


### 3.2. UI component

<img src="images/UiClassDiagram.png" width="800"><br>

_Figure 3.2.1 : Structure of the UI Component_

**API** : [`Ui.java`](../src/main/java/seedu/address/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `TaskListPanel`,
`StatusBarFooter`, `BrowserPanel` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/address/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

```
Function of `UI`

* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raised from various parts of the App and updates the UI accordingly.
```

### 3.3. Logic component

<img src="images/LogicClassDiagram.png" width="800"><br>

_Figure 3.3.1 : Structure of the Logic Component_

**API** : [`Logic.java`](../src/main/java/seedu/address/logic/Logic.java)

```
Function of `Logic`

* The `Logic` component uses the `Parser` class to parse the user command.
* The `Command` object created from the 'Parser' class is executed by the `LogicManager`.
* The command execution can affect the `Model` (e.g. adding a task) and/or raise events.
* The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.
```

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
 API call.<br>
 
<img src="images/DeletePersonSdForLogic.png" width="800"><br>

_Figure 3.3.2 : Interactions Inside the Logic Component for the `delete 1` Command_


### 3.4. Model component

<img src="images/ModelClassDiagram2.png" width="800"><br>

_Figure 3.4.1 : Structure of the Model Component_

**API** : [`Model.java`](../src/main/java/seedu/address/model/Model.java)

```
Function of `Model`

* The 'Model' component stores a `UserPref` object that represents the user's preferences.
* The 'Model' component stores the Task Manager data.
* The 'Model' component exposes a `UnmodifiableObservableList<ReadOnlyTask>` that can be 'observed' 
   e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* The 'Model' component does not depend on any of the other three components.
```
Additional Commands

* Redo Command - It is able to redo only if an undo command has been executed before.
This function is done by using stack. When an undo command is executed, taskManager is "push" into the stack before undo command's function is carried out. By doing this way, the previous state is saved inside the stack. When redo command is executed, the stack will "pop" and the taskManager will be replaced by the taskManager from the stack. The method that handle the stack reside in ModelManager under Model component.

* RemoveDeadline Command - It is able to remove all dates in a task.
It checks for valid index and set all the dates for the task to an empty string.

* Undo Command - It is able to undo action commands: add, edit, delete, clear, redo commands. 
This function is done by using stack. When an action command is executed, taskManager is "push" into the stack before the action command's function is carried out. By doing this way, the previous state is saved inside the stack. When undo command is executed,
the stack will be "pop" and the taskManager will be replaced by the taskManager from the stack. The method that handle the stack reside in ModelManager under Model component.

### 3.5. Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>

_Figure 3.5.1 : Structure of the Storage Component_

**API** : [`Storage.java`](../src/main/java/seedu/address/storage/Storage.java)

```
Function of `Storage`

* The `Storage` component can save `UserPref` objects in json format and read it back.
* The `Storage` component can save the Task Manager data in xml format and read it back.
```

### 3.6. Common classes

Classes used by multiple components are in the `typetask.commons` package.
&nbsp;

## 4. Implementation

### 4.1. Logging

We are using `java.util.logging` package for logging. The `LogsCenter` class is used to manage the logging levels
and logging destinations.

* The logging level can be controlled using the `logLevel` setting in the configuration file.
  (See [Configuration](#configuration))
* The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to
  the specified logging level.
* The log messages are output through: `Console` and to a `.log` file.

**Logging Levels**

* `SEVERE` : Critical problem detected which may possibly cause the termination of the application
* `WARNING` : Can continue, but with caution
* `INFO` : Information showing the noteworthy actions by the App
* `FINE` : Details that is not usually noteworthy but may be useful in debugging
  e.g. print the actual list instead of just its size

### 3.2. Configuration

Certain properties of the application can be controlled (e.g App name, logging level) through the configuration file
(default: `config.json`):
&nbsp;

## 4. Testing

Tests can be found in the `./src/test/java` folder.

**In Eclipse**:

* To run all tests, right-click on the `src/test/java` folder and choose
  `Run as` > `JUnit Test`.
* To run a subset of tests, you can right-click on a test package, test class, or a test and choose
  to run as a JUnit test.

**Using Gradle**:

* See [UsingGradle.md](UsingGradle.md) for how to run tests using Gradle.

We have two types of tests:

1. **GUI Tests** - These are _System Tests_ that test the entire App by simulating user actions on the GUI.
   These are in the `guitests` package.

2. **Non-GUI Tests** - These are tests not involving the GUI. They include,
   1. _Unit tests_ targeting the lowest level methods/classes. <br>
      e.g. `seedu.TypeTask.commons.UrlUtilTest`
      
   2. _Integration tests_ that are checking the integration of multiple code units
     (those code units are assumed to be working).<br>
      e.g. `seedu.TypeTask.storage.StorageManagerTest`
      
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as
      how the are connected together.<br>
      e.g. `seedu.TypeTask.logic.LogicManagerTest`

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
&nbsp;

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
    
### 5.6. Managing Dependencies

A project often depends on third-party libraries. For example, Task Manager depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>

a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>
&nbsp;

## 7. Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the Application
`* * *` | user | add a floating task  | add tasks without date or time
`* * *` | user | add a task with date and time | add tasks with a specified time and date
`* * *` | user | delete a task  | remove entries that I no longer need
`* * *` | user | edit a task  | update my tasks whenever I want to change some information
`* * *` | user | mark task as completed | remove entries that I have finished
`* * *` | user | list all my tasks  | see all the tasks I have to do
`* * *` | user | exit the application safely  | exit the application when I am done
`* * *` | user | find a task by name  | locate details of tasks without having to go through the entire list
`* * *` | user | save all my information | open the application and find my information is still there
`* * *` | user | specify a folder as the data storage location | save the data file into any directory that I want
`* * *` | user | undo my most recent command | revert the most recent command if it was wrongly issued
`* * *` | user | do task operations offline | remove the restriction of connecting to the internet to do operations
`* * *` | user | list all my to-do items in chronological order  | I can easily see all my tasks in chronological order
`* * *` | user | Use an existing data file | reuse my data file from another device
`* * ` | user | input a personal deadline | keep track of my given deadline
`* * ` | user | list my today tasks | view what tasks I have to do today
`* * ` | user | prioritize my task | know this task is more important to do
`* * ` | user | remove the priority of task | remove task from priority list
`* * ` | user | list priority tasks | See all the urgent tasks I have to do
`* * ` | Advanced user | use shortcut commands | type in the tasks quickly
`* * ` | user | block a date and time for an unconfirmed event temporary but still able to add task with the same date and time | remind myself of an unconfirmed event when i add a new task with the same date and time
`* * ` | user | list block tasks | See all the block tasks in task manager 
`* * ` | user | delete a blocked date and time event | remove an unconfirmed event when the event is confirmed to be cancelled
`* * ` | user | delete multiple to-do items at one go | I can quickly delete multiple items
`*` | user | see my google calendar | view my tasks in a calendar format
`*` | user | get a reminder to do a certain task | remind to do a task without having to forget
`*` | user | view a list of to-do items that are done | I know what tasks I have completed
`*` | user with many tasks in the task manager | sort task by name | locate a task easily
`*` | user | clear all tasks | start fresh by clearing all the tasks in the task manager 

&nbsp;
## 8. Appendix B : Use Cases

> For all use cases below, the **System** is the `Task Manager` and the **Actor** is the `user`, unless specified otherwise)

#### Use Case: Add task

**MSS**

1. User enters the add command to add a new task
2. System saves the task
Use case ends.

**Extensions**

2a. Invalid format

> 2a1. System shows an error message

> Use case ends

2b. Task’s date and time clashes with a blocked date and time

> 2b1. A window pop up warning will be shown to confirm user’s request
> 2b2. User click ok
> Use case ends
> 2b2.1 User click no
> Use case ends.

#### Use Case: Search for existing task

**MSS**

1.  User searches for an existing task
2.  System display the task
Use case ends

**Extensions**

2a. List is empty

> 2a1. System shows a message that indicates the command has been executed
> Use case ends
4a. Invalid format

> 4a1. System shows an error message
> Use case resumes at step 3
  
4b. Task does not exist

> 4b1. System shows an error message
> Use case resumes at step 3

#### Use Case: Delete an existing task

**MSS**

1.  User searches for an existing task
2.  System display the task
3.  User enters the delete command with task ID
4.  System deletes the task with the specified id
Use case ends

**Extensions**

2a. List is empty

> 2a1. System shows a message that indicates the command has been executed
> Use case ends
  
4a. Invalid format

> 4a1. System shows an error message
> Use case resumes at step 3
  
4b. Task does not exist

> 4b1. System shows an error message
> Use case resumes at step 3

#### Use Case: Deleting multiple task

**MSS**

1.  User enter the list all command
2.  System display all tasks
3.  User enters the delete command with multiple task ids
4.  System deletes the task with the specified ids
Use case ends

**Extensions**

2a. List is empty

> 2a1. System shows a message that indicates the command has been executed
> Use case ends

4a. Invalid format

> 4a1. System shows an error message
> Use case resumes at step 3

4b. Task does not exist in one of the multiple id

> 4b1. System shows an error message
> Use case resumes at step 3

#### Use Case: Editing an existing task

**MSS**

1.  User searches for an existing task
2.  System display the tasks
3.  User enters the edits command with task id
4.  System edits the task with the specified id
Use case ends

**Extensions**

2a. List is empty

  > 2a1. System shows a message that indicates the command has been executed

4a. Invalid format

> 4a1. System shows an error message
> Use case resumes at step 3

4b. Task does not exist

> 4b1. System shows an error message
> Use case resumes at step 3

#### Use Case: Mark task as complete

**MSS**

1.  User searches for an existing task
2.  System display the tasks
3.  User enters the complete command with task id
4.  System completes the task with the specified id
Use case ends

**Extensions**

2a. List is empty

> 2a1. System shows a message that indicates the command has been executed
> Use case ends

4a. Invalid format

> 4a1. System shows an error message
> Use case resumes at step 3

4b. Task does not exist

> 4b1. System shows an error message
> Use case resumes at step 3

#### Use Case: List today task

**MSS**

1.  User enters the list today command
2.  System displays today list tasks
Use case ends

**Extensions**

2a. List is empty
 
 > 2a1. System shows a message that indicates the command has been executed
 > Use case ends

#### Use Case: List all task in chronological order

**MSS**

1.  User enters the list all command
2.  System displays all tasks
Use case ends

**Extensions**

2a. List is empty
  
> 2a1. System shows a message that indicates the command has been executed
> Use case ends

#### Use Case: List of completed task

**MSS**

1.  User enters the list completed command
2.  System displays completed tasks
Use case ends

**Extensions**

2a. List is empty

> 2a1. System shows a message that indicates the command has been executed
> Use case ends

#### Use Case: List all priority task

**MSS**

1.  User enters the list priority command
2.  System displays priority list tasks
Use case ends

**Extensions**

2a. List is empty

> 2a1. System shows a message that indicates the command has been executed
> Use case ends

#### Use Case: Learning useful commands

**MSS**

1.  User enter the help command
2.  System displays the list of commands
Use case ends

**Extensions**

2a. Invalid format

> 2a1. System shows an error message
> Use case ends

#### Use Case: Saving data in another folder

**MSS**

1.  User enters save in another folder command
2.  System copies the existing file to the specified folder
Use case ends

**Extensions**

2a. Invalid format

> 2a1. System shows error message
> Use case ends

2b. Invalid path

> 2b1. System shows error message.
> Use case ends

2c. Existing file found

> 2c1. System will overwrite the existing file.
> Use case ends.

#### Use case: Changing default storage folder

**MSS**

1.  User enters changing default storage folder command
2.  System will copy the existing file to the new destination folder and set the default saving path to the new destination folder
Use case ends

**Extensions**

2a. Invalid format

> 2a1. System shows error message
> Use case ends

2b. Invalid path

> 2b1. System shows error message
> Use case ends 

2c. Existing file found

> 2c1. System will overwrite the existing file
> Use case ends.


#### Use case: Using data from another folder

**MSS**

1.  User enters use command to open another existing file
2.  System opens the existing file and will act on this folder from this point on
Use case ends

**Extensions**

2a. Invalid format

> 2a1. System shows an error message
> Use case ends

2b. Invalid file path

> 2b1. System shows an error message
> Use case ends


#### Use case: Undo last command

**MSS**

1.  User undos latest command
2.  System undos latest command
Use case ends

**Extensions**

2a. Invalid format

> 2a1. System shows an error message
> Use case ends

2b. No previous command exists

> 2b1. System shows an error message
> Use case ends

#### Use case: Exit application

**MSS**

1.  User enters exit command
2.  System exits application

**Extensions**

2a. Invalid format

> 2a1. System shows an error message
> Use case ends
&nbsp;

## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 tasks without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands)
   should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Should function on OS,Windows 7 or later
5. Should function on a desktop either with or without network or Internet connection
6. Should have minimal mouse-click actions
7. Should function stand-alone, not as a plug-in to another software
8. Should store data locally into a human editable file
9. Should function without requiring an installer
10. Should be able to hold up to 1000 to-do items
11. Should come with automated unit tests and open source code
12. Should display command results within 100 milliseconds
&nbsp;

## 10. Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Floating Task

> A task that does not need to be completed by a certain date or time

##### Storage folder

> The directory found in a computer where the application stores the data

&nbsp;

## 11. Appendix E : Product Survey

**Product #1: Wunderlist**

Pros:

* Supports offline work. User does not have to depend on Internet connectivity to use application.
* Supports adding of tasks without deadlines. User’s one-shot approach is met when adding.
* Supports editing and deleting of existing tasks. User is able to not only reschedule, but also discard tasks that cannot be completed. 
* Supports adding of deadlines and reminders. User will be reminded when a deadline is approaching.
* Supports searching and sorting of to-do items. User is able to find specific tasks easily.
* Supports viewing of tasks that are completed. User is able to know which tasks are completed and which are yet to be completed.
* Displays tasks in a user-friendly manner.
* Supports a way to prioritise a task. User is able to view prioritised task at the top of the task list.
* Supports a way to recover deleted task. User is able to recover deleted task when the task is deleted unintentionally.
* Supports grouping of task. User is able to create lists to group related task together.
* Supports sharing of task. User is able to share task list with other users with internet connection.
* Supports synchronisation. User is able to view updated shared list real-time with internet connection.
* Supports predictive due date input. Wunderlist can understand more colloquial/natural inputs and translate to specific dates, e.g. Wunderlist can detect words like ‘next week’ and add a specific due date to the input task.


Cons:

* Does not work with event with time slots. User is not able to manage events
* Does not categorise tasks. User may find it hard to differentiate tasks with and without deadlines
* Does not support an "Undo" option. Tasks that were incorrectly added has to be edited or deleted manually
* Does not store data into local storage files but links to an online user account instead. Internet connectivity is still dependent if user wants to work from different computers
* Does not have one-shot approach when performing functions other than adding a task with no deadlines
* Requires a few clicks to perform functions


**Product #2: Google Calendar + Google Task**

Pros:

* Supports displaying tasks in different views. Users are able to use multiple views
* Supports setting notification for tasks. Users are able to set notification timing for specific tasks
* Supports syncing with multiple calendars (Personal + Shared Calendars). Users are able to see tasks/events from multiple calendars in 1 page
* Supports sharing of calendars. Users are able to share their calendars with other users
* Supports integration with smartphones. Users are able to view the tasks from any devices
* Supports unique holidays calendar, e.g. Holidays in Singapore. Users are able to check on the holidays of the countries they are visiting
* Supports daily agenda emails. Users are able to enable daily agenda emails to be reminded on the agenda of that day
* Supports embedding of calendar. Users are able to embed their Google Calendar to their website/blog
* Supports changing the colour of the tab. Users are able to group tasks by changing the colour of the tab
* Supports creating repeated task, e.g. Weekly group meeting at certain timings. Users are able to create events/tasks that will be repeated every day/week/month/year, e.g. timetable

Cons:

* Requires a Google Account
* Daily agenda emails are sent only at 5am of that day
* No tutorial on all features, new users will not be able to use the hidden advanced features, e.g. Daily Agenda Emails, task, reminders (By Google Inbox)
* Reminders from Google Inbox are shown only on the smartphones


**Product #3: to-doist**

Pros:

* Supports adding of tasks that can be marked as 'done'. User is able to mark completed tasks as done
* Supports adding of tasks without deadlines. User is able to add task without deadlines with a one shot approach
* Supports searching of tasks. User is able to find a task quickly
* Supports in different platform. User is able to access the application quickly from the computer or mobile devices
* Supports offline environment. User does not have to depend on Internet connectivity to use application
* Supports postponement of tasks. User can postpone tasks when the need arise

Cons:

* The user has to search through all existing tasks to look for an empty slot, which is not considered a user-friendly approach.
* Blocking of multiple slots for tasks with unconfirmed timings is not supported. User will not be able to schedule tasks with unconfirmed timings
* Internet connectivity is required to access the application through other devices


**Product #4: Remember the Milk**

Pros:

* Supports searching of task. User is able to search for task through keywords
* Supports adding of task with no deadlines
* Supports capturing of tasks to be done after a certain date
* Supports capturing of tasks to be done before a certain date, i.e. ‘due date’
* Supports task repetition
* Supports the ‘one shot’ approach through “Smart Add” feature. User is able to use each feature with the respective commands all in a single command
* Support categorisation of tasks through “List” feature, making organisation simpler
* Supports offline use
* Supports prioritising of tasks
* Supports checking off completed tasks
* Support multi-platform use: Android, iOS, BlackBerry

Cons:

* Requires user to sign in
* Certain features are only accessible through purchase
* Does not support automatic release of blocked dates once date is confirmed
* Does not offer a calendar view or any function to easily determine a free time slot for a task
