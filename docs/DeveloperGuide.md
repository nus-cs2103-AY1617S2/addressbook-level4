# Today - Developer Guide

By : `T09B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`

---

1. [Introduction](#1-introduction)
2. [Setting Up](#2-setting-up)
3. [Design](#3-design) <br>
    3.1. [Architecture](#31-architecture) <br>
    3.2. [UI](#32-ui) <br>
    3.3. [Logic](#33-logic) <br>
    3.4. [Model](#34-model)<br>
    3.5. [Storage](#35-storage)
4. [Implementation](#4-implementation)
5. [Testing](#5-testing)
6. [Dev Ops](#6-dev-ops)

* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b--use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Glossary](#appendix-d--glossary)
* [Appendix E : Product Survey](#appendix-e--product-survey)

## 1. Introduction

**Today** is a minimalistic task manager that aims to be the simplest way to organize your day. We help users focus on the things that they can control - the tasks they're tackling today.

If you're interested in helping us improve the lives of our users, we're always looking for new contributors of all proficiency levels.

- If you're a **beginner**, start by looking at our [list of bugs](https://github.com/CS2103JAN2017-T09-B1/main/issues?q=is%3Aissue+is%3Aopen+label%3Atype.bug). You can help by reporting new bugs, or by fixing an existing one.
- If you're an **experienced Java programmer**, take a look at our [wishlist](https://github.com/CS2103JAN2017-T09-B1/main/issues?q=is%3Aissue+is%3Aopen+label%3Atype.enhancement), and see if you're interested in implementing any of the new features planned for the next release.

Before starting work on an issue, make sure to leave a comment to let us know. Other non-issue specific queries should be sent to ask@todaytaskmanager.com.sg.

## 2. Setting up

If it's your first-time working on **Today**, you'll need to follow the instructions in this section to set up your environment.

### 2.1. Prerequisites

1. **JDK `1.8.0_60`**  or later
2. **Eclipse** IDE
3. **e(fx)clipse** plugin for Eclipse
4. **Buildship Gradle Integration** plugin from the Eclipse Marketplace
5. **Checkstyle Plug-in** plugin from the Eclipse Marketplace


### 2.2. Importing the project into Eclipse

1. Fork this repository, and clone the fork to your computer
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
3. Enter an arbitrary configuration name e.g. addressbook
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


## 3. Design

### 3.1. Architecture

<img src="https://github.com/CS2103JAN2017-T09-B1/main/raw/develop/docs/images/Architecture.png" width="600"><br>
_Figure 3.1.1 : Architecture Diagram_

The **_Architecture Diagram_** given above explains the high-level design of the App. We provide a quick overview of each component below.

`Main` has only one class called [`MainApp`](../src/main/java/seedu/address/MainApp.java) which ...

* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup method where necessary.

`Commons` represents a collection of classes used by multiple other components.
Two of those classes play important roles at the architecture level.

* `EventsCenter` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design)
* `LogsCenter` : Used by many classes to write log messages to the App's log file.

[`UI`](#32-ui) represents a collection of classes that manages the front-end visual elements of the application.

[`Logic`](#33-logic) represents a collection of classes where all the input commands are parsed then executed.

[`Model`](#34-model) represents a collection of classes that manage the in-memory data while the application is running.

[`Storage`](#35-storage) represents a collection of classes that ensure the in-memory data is stored and saved on disk upon changes in data. This allows tasks to be restored from the disk when you close and reopen the application.

UI, Logic, Model and Storage are key components that

* Define their _API_ in interfaces with the same names as the Components.
* Expose their functionality using `{Component Name}Manager` classes.

For example, the `Logic` component defines its API in the `Logic.java` interface and exposes its functionality using the `LogicManager.java` class.

We elaborate more about the individual components below.

### 3.2. UI
<img src="https://github.com/CS2103JAN2017-T09-B1/main/raw/develop/docs/images/UiClassDiagram.png" width="800"><br>
_Figure 3.2.1 : Structure of the UI Component_

**API** : [`Ui.java`](../src/main/java/seedu/address/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter`, `BrowserPanel` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder.

For example, the layout of the [`MainWindow`](../src/main/java/seedu/address/ui/MainWindow.java) is specified in  [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component passes user commands using the `Logic` component, auto-update when data in the `Model` changes, and responds to events raised from various parts of the App and updates the interface accordingly.

### 3.3. Logic

<img src="images/TaskManagerLogicClassDiagram.png">

**API** : [`Logic.java`](../src/main/java/seedu/address/logic/Logic.java)

The **Logic** component of the software handles the input from the **UI** and calls methods from the **Model**, **Config**, and **Storage** to perform the appropriate changes.

When a command is entered, the `Parser` processes the text and selects the appropriate `CommandParser` based on the first word in the text to parse the arguments as well. Its respective `Command` is then initialized which calls the relevant methods from other components, and returns a `CommandResult` to the UI to make the relevant changes.

> `Parser` makes use of classes such as `ArgumentTokenizer`, `ParserUtil`, and `CliSyntax` for certain repetitive parsing tasks

> `CommandParser` may return an `IncorrectCommand` in the case when the arguments are not of the suitable format

### 3.4. Model

<img src="images/TaskManagerModelClassDiagram.png" width="800"><br>
_Figure 3.4.1 : Structure of the Model Component_

**API** : [`Model.java`](../src/main/java/seedu/address/model/Model.java)

`Model` consists of `UserPref`, `TaskManager` and `UnmodifiableObservableList`.

The`UserPref` object manages the user's preferences (e.g. window size) while the `TaskManager` object manages the user's tasks. All CRUD commands, such as adding and deleting tasks, are all handled by the `TaskManager` object.

Lastly, the `UnmodfiableObservableList<ReadOnlyTask>` is bound to the UI. Whenever this list is updated, the UI will be updated to reflected the new set of tasks.

### 3.5. Storage

<img src="images/StorageClassDiagram.png" width="800"><br>
_Figure 3.5.1 : Structure of the Storage Component_

**API** : [`Storage.java`](../src/main/java/seedu/address/storage/Storage.java)

Similar to the `Model`, `Storage` contains the `UserPrefsStorage` object and the `TaskManagerStorage` object.

`UserPrefsStorage` writes and reads a JSON file which contains the user's preferences.

`TaskManagerStorage` writes and reads an XML file which contains the user's tasks and the related details.

### 3.6. Event-Driven Nature

Because there are many different components that may be affected by a single command, we use events to simplify method calling. In our code, after a command has successfully executed its primary functionality like making a change to the **Model**, it raises an `Event` which is then picked up by **Storage**, and **UI** which then calls the relevant methods to make the appropriate changes.

The _Sequence Diagram_ below exemplifies this process. In the figure below, you can see that entering `delete 1` causes a change in the model which is the command's primary task.

<img src="images\SDforDeletePerson.png" width="800"><br>
_Figure 3.6.1_ : Primary Component interactions for `delete 1` command (part 1)_

Only after the task is complete, is an `Event` raised to modify the storage and UI components as can be seen in the next diagram.

<img src="images\SDforDeletePersonEventHandling.png" width="800"><br>
_Figure 3.6.2_ : Secondary Component interactions for `delete 1` command (part 2)_

## 4. Implementation

### 4.1. Logging

We are using `java.util.logging` package for logging. The `LogsCenter` class is used to manage the logging levels and logging destinations.

* The logging level can be controlled using the `logLevel` setting in the configuration file
  (See [Configuration](#configuration))
* The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to
  the specified logging level
* Currently log messages are output through: `Console` and to a `.log` file.

**Logging Levels**

* `SEVERE` : Critical problem detected which may possibly cause the termination of the application
* `WARNING` : Can continue, but with caution
* `INFO` : Information showing the noteworthy actions by the App
* `FINE` : Details that is not usually noteworthy but may be useful in debugging e.g. print the actual list instead of just its size

### 4.2. Configuration

Certain properties of the application can be controlled (e.g App name, logging level) through the configuration file
(default: `config.json`):


## 5. Testing

Tests can be found in the `./src/test/java` folder.

**In Eclipse**:

* To run all tests, right-click on the `src/test/java` folder and choose
  `Run as` > `JUnit Test`
* To run a subset of tests, you can right-click on a test package, test class, or a test and choose
  to run as a JUnit test.

**Using Gradle**:

* See [UsingGradle.md](UsingGradle.md) for how to run tests using Gradle.

We have two types of tests:

1. **GUI Tests** - These are _System Tests_ that test the entire App by simulating user actions on the GUI. These are in the `guitests` package.

2. **Non-GUI Tests** - These are tests not involving the GUI. They include,
   1. _Unit tests_ targeting the lowest level methods/classes. <br>
      e.g. `seedu.address.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units
     (those code units are assumed to be working).<br>
      e.g. `seedu.address.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as how the are connected together.<br>
      e.g. `seedu.address.logic.LogicManagerTest`

#### Headless GUI Testing
Thanks to the [TestFX](https://github.com/TestFX/TestFX) library we use,
 our GUI tests can be run in the _headless_ mode.
 In the headless mode, GUI tests do not show up on the screen.
 That means the developer can do other things on the Computer while the tests are running.<br>
 See [UsingGradle.md](UsingGradle.md#running-tests) to learn how to run tests in headless mode.

### 5.1. Troubleshooting tests

 **Problem: Tests fail because NullPointException when AssertionError is expected**

 * Reason: Assertions are not enabled for JUnit tests.
   This can happen if you are not using a recent Eclipse version (i.e. _Neon_ or later)
 * Solution: Enable assertions in JUnit tests as described
   [here](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option). <br>
   Delete run configurations created when you ran tests earlier.

## 6. Dev Ops

### 6.1. Build Automation

See [UsingGradle.md](UsingGradle.md) to learn how to use Gradle for build automation.

### 6.2. Continuous Integration

We use [Travis CI](https://travis-ci.org/) and [AppVeyor](https://www.appveyor.com/) to perform _Continuous Integration_ on our projects.
See [UsingTravis.md](UsingTravis.md) and [UsingAppVeyor.md](UsingAppVeyor.md) for more details.

### 6.3. Publishing Documentation

See [UsingGithubPages.md](UsingGithubPages.md) to learn how to use GitHub Pages to publish documentation to the
project site.

### 6.4. Making a Release

Here are the steps to create a new release.

 1. Generate a JAR file [using Gradle](UsingGradle.md#creating-the-jar-file).
 2. Tag the repo with the version number. e.g. `v0.1`
 2. [Create a new release using GitHub](https://help.github.com/articles/creating-releases/)
    and upload the JAR file you created.

### 6.5. Converting Documentation to PDF format

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

### 6.6. Managing Dependencies

A project often depends on third-party libraries. For example, Address Book depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

## Design

### Logic component

<img src="images/TaskManagerLogicClassDiagram.png">

The **Logic** component of the software handles the input from the **UI** and calls methods from the **Model**, **Config**, and **Storage** to perform the appropriate changes.

When a command is entered, the `Parser` processes the text and selects the appropriate `CommandParser` based on the first word in the text to parse the arguments as well. Its respective `Command` is then initialized which calls the relevant methods from other components, and returns a `CommandResult` to the UI to make the relevant changes.

> `Parser` makes use of classes such as `ArgumentTokenizer`, `ParserUtil`, and `CliSyntax` for certain repetitive parsing tasks

> `CommandParser` may return an `IncorrectCommand` in the case when the arguments are not of the suitable format

### Event-Driven Nature

Because there are many different components that may be affected by a single command, we use events to simplify method calling. In our code, after a command has successfully executed its primary functionality like making a change to the **Model**, it raises an `Event` which is then picked up by **Storage**, and **UI** which then calls the relevant methods to make the appropriate changes.

The _Sequence Diagram_ below exemplifies this process. In the figure below, you can see that entering `delete 1` causes a change in the model which is the command's primary task.

<img src="images\SDforDeletePerson.png" width="800"><br>
_Figure _ : Primary Component interactions for `delete 1` command (part 1)_

Only after the task is complete, is an `Event` raised to modify the storage and UI components as can be seen in the next diagram.

<img src="images\SDforDeletePersonEventHandling.png" width="800"><br>
_Figure _ : Secondary Component interactions for `delete 1` command (part 2)_

## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
 `* * *` | New User | Know all the commands that are available | Know how to use the Task Manager effectively
 `* * *` | User | Add a task | Keep track of my tasks
 `* * *` | User | Specify a start date for a task | Keep track of when I intend to start on my task
 `* * *` | User | Specify a deadline for a task | Keep track of when my tasks are due
 `* * *` | User | Update an existing task | Correct a mistake or indicate a change in the task
 `* * *` | User | Delete a task | Indicate a change in the task
 `* * *` | User | Tag my tasks | Find the tasks more easily, and make my task manager more organized
 `* * *` | User | Manage (add, delete, rename) the tags | Indicate changes in tags
 `* * *` | User | Find a specific task by keywords in the description/title | I can view or modify the task
 `* * *` | User | Find tasks due on a specific day | I can see my workload for that day
 `* * *` | User | View all tasks | Know what tasks I have
 `* * *` | User | Be able to indicate if I have completed a task or not | Keep track of my progress
 `* * *` | User | View all tasks that I have not done | View how much more work is there to be done
 `* * *` | User | View completed tasks (hidden by default) | Know what tasks I have completed
 `* * *` | User | Undo my previous commands | undo any mistakes that I have made
 `* * *` | User | Redo my undo command | undo my undo
 `* * *` | User | Specify a location to save the storage file | Choose where to save the file to
 `* * *` | User | Specify a storage file to open | Access a task list from another computer
 `* * *` | User | Enter commands using natural language and in an intuitive manner | Easily use the task manager without having to memorize command formats
 `* * *` | User | Exit the task manager | Have a peace of mind that the program exits safely
 `* *` | User | See if the commands that I typed are valid while I am typing them | Check that my command is valid before I press enter
 `* *` | User | Export all tasks to an external file | Backup the list of tasks
 `* *` | User | Restore tasks from an external file | Access tasks from one computer on another computer
 `*` | User | Add recurring tasks | Avoid duplicate work and adding daily/weekly/monthly tasks multiple times
 `*` | Team leader | Push the team event schedule to every team member | Notify the whole team about the team schedule
 `*` | Advanced User | Use hotkeys to bring the Task Manager from the background to the foreground | Make adding tasks faster

## Appendix B : Use Cases

(For all use cases below, the **System** is the `Task Manager` and the **Actor** is the `user`, unless specified otherwise)

### Use Case: Add Task
**MSS**

1. User requests to add a new task
2. TaskManager adds a new task
Use case ends

**Extensions**<br>
2a. User supplies optional parameters<br>
>  2a1. TaskManager includes optional parameters in task details<br>
>  Use case resumes at step 2

### Use Case: Find Task
**MSS**

1. User requests to find a specific task
2. TaskManager finds all related tasks and displays to user
Use case ends

**Extensions**<br>
1a. User requests to find a task in sorted order.<br>
>  1a1. TaskManager displays tasks in sorted order<br>
>  Use case ends

2a. TaskManager cannot find any related tasks.<br>
> 2a1. User tries new search<br>
> Use case resumes at step 1

### Use Case: View Tasks
**MSS**

1. User requests TaskManager to display task list
2. TaskManager displays the task list to the user
Use case ends

**Extensions**<br>
1a. User requests to view tasks in sorted order<br>
> 1a1. TaskManager displays tasks in specified sorted order.<br>
> Use case ends.


### Use Case: Update Task
**MSS**

1. User requests TaskManager to display task list
2. TaskManager displays the task list to the user
3. User specifies a task to be updated and supplies parameters to update task
4. TaskManager updates the task
Use case ends

**Extensions**<br>
4a. User supplied invalid parameters<br>
> 4a1. TaskManager informs user that invalid parameters were entered<br>
> Use case resumes at step 3

### Use Case: Delete Task
**MSS**

1. User requests TaskManager to display task list
2. TaskManager displays the task list to the user
3. User specifies a task to be deleted
4. TaskManager deletes the task
Use case ends

**Extensions**<br>
4a. Specified task does not exist<br>
> 4a1. TaskManager informs user that invalid task was specified<br>
> Use case ends

### Use Case: Indicate Task completed
**MSS**

1. User requests TaskManager to display task list
2. TaskManager displays the task list to the user
3. User specifies a task to indicate as completed
4. TaskManager saves task as completed
Use Case ends

**Extensions**<br>
4a. Specified task does not exist<br>
> 4a1. TaskManager informs user that invalid task was specified<br>
> Use case ends

### Use Case: Change Storage File Location
**MSS**

1. User specifies new storage file location to TaskManager
2. TaskManager changes the storage file to the new location
Use Case ends

**Extensions**<br>
2a. Specified location does not exist<br>
> 2a1. TaskManager informs user that invalid location was specified<br>
> Use case ends

### Use Case: Edit Tag
**MSS**

1. User requests TaskManager to display tag list
2. TaskManager displays the tag list to the user
3. User specifies a tag to be modified
4. TaskManager displays success message
Use case ends

**Extensions**<br>
3a. Specified tag does not exist<br>
> 3a1. TaskManager informs user that the selected tag does not exist<br>
> 3a2. Use case resumes at step 3<br>

3b. The new tag is not valid, i.e. contains illegal characters or too long.<br>
> 3b1. TaskManager informs user that the input is not valid<br>
> 3b2. Use case resumes at step 3<br>


### Use Case: Undo Previous Command
**MSS**

1. User requests to undo the previous command
2. TaskManager displays the last modification command sent by the user
3. User confirms the undo command
4. TaskManager displays success message
Use case ends

**Extensions**<br>
1a. There is no undo backup during this session so far<br>
> 1a1. TaskManager informs user that there is no undo backup file<br>
> 1a2. Use case ends

### Use Case: Redo Previous Command that has been undone
**MSS**

1. User requests to redo the previous command that has been undone
2. TaskManager displays the last modification command sent by the user
3. User confirms the redo command
4. TaskManager displays success message
Use case ends

**Extensions**<br>
1a. There is no redo backup during this session so far<br>
> 1a1. TaskManager informs user that there is no redo backup file<br>
> 1a2. Use case ends

## Appendix C : Non Functional Requirements

1. The task manager should be a desktop software that works on the Windows 7 or later.
1. The task manager should use a Command Line Interface as a main form of input.
1. The task manager should work without requiring additional software extensions.
1. The task manager should store data locally in the form of a human editable text file.
1. The task manager should work without requiring an installer.
1. The task manager only use third-party frameworks/libraries that:
  * are free.
  * do not require any installation by the user of your software.
  * do not violate other constraints.

## Appendix D : Glossary

## Appendix E : Product Survey

**Any.Do**

Author: Ken

Pros:
* Simple, Minimalistic
* Mobile Friendly
* "Moment", feature that cycles through the tasks you have on that day and prompts you to plan your day

Cons:
* Poor Web Application

**Google Calendar**

Author: Shi Yuan

Pros:
* Supports Natural Language commands
* Synchronizes on all devices

Cons:
* Unable to set priorities for tasks

**Things**

Author: Yu Li

Pros:
* Highly customizable
* Daily Review
* Autofill

Cons:
* Cannot export

**Wunderlist**

Author: Cao Wei

Pros:
* Many shortcuts
* Sorts tasks by priority and category
* Compatible with almost every OS

Cons:
* No calendar view
