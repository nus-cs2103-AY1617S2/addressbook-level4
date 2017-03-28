# Burdens: Developer Guide

By : `W09-B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---
0. [Introduction](#0-introduction)
1. [Setting Up](#1-setting-up)
   > 1.1 [Prerequisites](#11-prerequisites)<br/>
     1.2 [Importing the project into Eclipse](#12-importing-project-burdens-into-eclipse)<br/>
     1.3 [Configuring Checkstyle](#13-configuring-checkstyle)<br/>
     1.4 [Troubleshooting project setup](#14-troubleshooting-project-setup)<br/>
2. [Design](#2-design)
   > 2.1 [Architecture](#21-architecture)<br/>
     2.2 [UI component](#22-ui-component)<br/>
     2.3 [Logic component](#23-logic-component)<br/>
     2.4 [Model component](#24-model-component)<br/>
     2.5 [Storage component](#25-storage-component)<br/>
     2.6 [Common classes](#26-common-classes)<br/>
3. [Implementation](#3-implementation)
4. [Testing](#4-testing)
   > 4.1 [Troubleshooting Tests](#41-troubleshooting-tests)
5. [Dev Ops](#5-dev-ops)

* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b--use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Glossary](#appendix-d--glossary)
* [Appendix E : Product Survey](#appendix-e--product-survey)

## 0. Introduction

**Hello Developer.**<br/>
<br/>
This is **Burdens**. It is a task manager created to make sense of our daily lives and list personal tasks and deadlines via keyboard commands.<br/>
<br/>
It is a Java desktop application that has a GUI implemented with JavaFX.<br/>
<br/>
This guide will aid current and future developers like you in understanding and augmenting the features and design implementations of **Burdens**.<br/>
<br/>
You are welcome to contribute in any way!

## 1. Setting Up

### 1.1 Prerequisites

Please ensure you have the following prerequisites before contributing to development:

1. **JDK `1.8.0_60`**  or later
2. **Eclipse** IDE<br>
3. **e(fx)clipse** plugin for Eclipse (Follow from Steps 2 onwards given in<br>
   [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious))<br>
4. **Buildship Gradle Integration** plugin from the Eclipse Marketplace<br>
5. **Checkstyle Plug-in** plugin from the Eclipse Marketplace<br>

### 1.2 Importing Project Burdens into Eclipse

0. Fork this repository, and clone the fork to your computer
1. Open Eclipse <br/>
   [Note: **e(fx)clipse** and **buildship** plugins must be installed as given in 2.1 Prerequisites]
2. Click `File` > `Import`
3. Click `Gradle` > `Gradle Project` > `Next` > `Next`
4. Click `Browse`, then locate the project's directory
5. Click `Finish`

  > * If you are asked whether to 'keep' or 'overwrite' config files, choose to 'keep'.
  > * Depending on connection speed and server load, it can take up to 30 minutes for the setup to finish
      [Gradle downloads library files from servers during the project setup]
  > * During the import process, Eclipse might change settings automatically, you can safely discard those changes.

### 1.3 Configuring Checkstyle
1. Click `Project` -> `Properties` -> `Checkstyle` -> `Local Check Configurations` -> `New...`
2. Choose `External Configuration File` under `Type`
3. Enter an arbitrary configuration name e.g. burdens
4. Import checkstyle configuration file found at `config/checkstyle/checkstyle.xml`
5. Click OK once, go to the `Main` tab, use the newly imported check configuration.
6. Tick and select `files from packages`, click `Change...`, and select the `resources` package<br/>
[Note: The Change.. button can be enabled by clicking on the files from packages text]
7. Click OK twice. Rebuild project if prompted

### 1.4 Troubleshooting Project Setup

**Problem: Eclipse reports compile errors after new commits are pulled from Git**

* Reason: Eclipse fails to recognize new files that appeared due to the Git pull.
* Solution: Refresh the project in Eclipse:<br>
  Right click on the project (in Eclipse package explorer), choose `Gradle` -> `Refresh Gradle Project`.

**Problem: Eclipse reports some required libraries missing**

* Reason: Required libraries may not have been downloaded during the project import.
* Solution: [Run tests using Gradle](UsingGradle.md) once (to refresh the libraries).


## 2. Design

### 2.1 Architecture

<p align="center">
   <img src="images/Architecture.png" width="600"><br>
   Figure 2.1.1 : Architecture Diagram
</p>

<br/>

The **_Architecture Diagram_** above condenses the high-level design of Burdens.<br/>
Here is a quick overview of the main components of Burdens and their functions:

**`Main`**<br/>
The 'Main' component has one class: [MainApp](../src/main/java/seedu/address/MainApp.java). <br/>
During application launch, it intialises all components of Burdens in proper sequence and connects them.<br/>
During application shutdown, it ceases all ongoing operations of Burdens and invokes cleanup when necessary.<br/>
<br/>
**`Commons`**<br/>
`Commons` represents a collection of classes used by multiple other components.<br/>
Two of those classes play important roles at the architecture level.

* `EventsCenter` : Written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained), this class
  is used by components to communicate with other components using events.
* `LogsCenter` : Used by many classes to write log messages to Burdens' log file to record system information and events.

**`UI`**<br/>
The ['UI'](#22-ui-component) component handles display interactions on screen such as data and results of the task lists.<br/>
<br/>
**`Logic`**<br/>
The ['Logic'](#23-logic-component) component handles the process and execution of user's commands.<br/>
<br/>
**`Model`**<br/>
The ['Model'](#24-model-component) handles data representation and data structures of Burdens.<br/>
<br/>
**`Storage`**<br/>
The ['Storage'](#25-storage-component) handles the process of reading data from and writing data to the hard disk.<br/>
<br/>
Each of the ** `UI` , `Logic`, `Model`, `Storage`** components:

* Defines its _API_ in an `interface` with the same name as the Component.
* Exposes its functionality using a `{Component Name}Manager` class.

For example, the `Logic` component (see the class diagram given below) defines it's API in the `Logic.java`
interface and exposes its functionality using the `LogicManager.java` class.<br>
<p align="center">
   <img src="images/LogicClassDiagram.png" width="800"><br>
   Figure 2.1.2 : Class Diagram of the Logic Component
</p>

#### Event Driven Design
Burdens has an inherent Event Driven design.<br>
The _Sequence Diagram_ below shows how the components interact for the scenario where the user issues the
command `delete 1`.

<p align="center">
   <img src="images\SDforDeletePerson.png" width="800"><br>
   Figure 2.1.3a : Component interactions for `delete 1` command (part 1)
</p>

>Note how the `Model` simply raises a `TaskManagerChangedEvent` when data of Burdens is changed,
 instead of asking the `Storage` to save updates to the hard disk.

The diagram below shows how the `EventsCenter` reacts to that event, which eventually results in the updates
being saved to the hard disk and the status bar of the UI being updated to reflect the 'Last Updated' time. <br>

<p align="center">
   <img src="images\SDforDeletePersonEventHandling.png" width="800"><br>
   Figure 2.1.3b : Component interactions for `delete 1` command (part 2)
</p>
>Note how the event is propagated through the `EventsCenter` to the `Storage` and `UI` without `Model` having
  to be coupled to either of them. This is an example of how this Event Driven approach helps us reduce direct
  coupling between components.

The sections below will provide more details of each component.

### 2.2 UI component
`UI` shows updates to the user; changes in data in the Model naturally updates `UI` as well.<br>
`UI` executes user commands using the Logic Component. <br>
`UI` responds to events raised from the other components of Burdens and updates the display accordingly.
<p align="center">
   <img src="images/UiClassDiagram.png" width="800"><br>
   Figure 2.2.1 : Structure of the UI Component
</p>

**API** : [`Ui.java`](../src/main/java/seedu/address/ui/Ui.java)

The `UI` component:

* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raised from various parts of the App and updates the UI accordingly.

### 2.3 Logic component
'Logic' provides several APIs for UI to execute the commands entered by the user.<br>
'Logic' obtains information about the tasklist to render to the user.
<p align="center">
   <img src="images/LogicClassDiagram.png" width="800"><br>
   Figure 2.3.1 : Structure of the Logic Component
</p>

**API** : [`Logic.java`](../src/main/java/seedu/address/logic/Logic.java)

The `Logic` component:
* uses the `Parser` class to parse the user command follwing which a `Command` object is executed by the `LogicManager`.
* invokes command execution that can affect the `Model` (e.g. adding a person) and/or raise events.
* encapsulates a `CommandResult` object which is passed back to the `Ui`.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
API call.<br>
<p align="center">
   <img src="images/DeletePersonSdForLogic.png" width="800"><br>
   Figure 2.3.1 : Interactions Inside the Logic Component for the `delete 1` Command
</p>

### 2.4 Model component
`Model` manages and stores Burdens's tasklist data and user's preferences. <br>
`Model` exposes an UnmodifiableObservableList<ReadOnlyTask> that can be 'observed' by other components<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; e.g. the UI can be bound to this list and will automatically update when the data in the list change.
<p align="center">
   <img src="images/ModelClassDiagram.png" width="800"><br>
   Figure 2.4.1 : Structure of the Model Component
</p>

**API** : [`Model.java`](../src/main/java/seedu/address/model/Model.java)

The `Model`component:
* does not depend on any of the other three components.

### 2.5 Storage component
`Storage` saves `UserPref` objects in json format and reads it back.<br>
`Storage` saves the Task Manager data in xml format and reads it back.
<p align="center">
   <img src="images/StorageClassDiagram.png" width="800"><br>
   Figure 2.5.1 : Structure of the Storage Component
</p>

**API** : [`Storage.java`](../src/main/java/seedu/address/storage/Storage.java)

### 2.6 Common classes

Classes used by multiple components are in the `seedu.taskmanager.commons` package.

Burdens further separates the packages into sub-packages - `core`, `events`, `exceptions` and `util`.

* `Core` - This package consists of the essential classes that are required by multiple components.
* `Events` -This package consists of the different type of events that can occur; these are used mainly by EventManager and EventBus.
* `Exceptions` - This package consists of exceptions that may occur with the use of Burdens.
* `Util` - This package consists of additional utilities for the different components.

## 3. Implementation

### 3.1 Logging

We are using `java.util.logging` package for logging. The `LogsCenter` class is used to manage the logging levels
and logging destinations.

* The logging level can be controlled using the `logLevel` setting in the configuration file
  (See [Configuration](#32-configuration))
* The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to
  the specified logging level
* Currently log messages are output through: `Console` and to a `.log` file.

**Logging Levels**

Burdens has 4 logging levels: `SEVERE`, `WARNING`, `INFO` and `FINE`.
* `SEVERE` : Critical problem detected which may possibly cause the termination of the application
* `WARNING` : Can continue, but with caution
* `INFO` : Information showing the noteworthy actions by Burdens
* `FINE` : Details that is not usually noteworthy but may be useful in debugging
  e.g. print the actual list instead of just its size

### 3.2 Configuration

Certain properties of Burdens can be controlled (e.g logging level) through the configuration file
(default: `config.json`):

### 3.3 Undo/Redo Mechanisms

We use an ArrayList object to store the states of the internal task list. Each time a command is issued that incurs
a change in the task list such as Add, Delete, Edit, the new state of the task list is recorded to the ArrayList object, and its current state index corresponds to the index element of the ArrayList is incremented by 1. The Undo/Redo function thus increments/decrements the current state index by 1, and the `reset` method of the current task list object is called with parameter being the element at the current state index of the ArrayList.


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

### 4.1 Troubleshooting tests

 **Problem: Tests fail because NullPointException when AssertionError is expected**

 * Reason: Assertions are not enabled for JUnit tests.
   This can happen if you are not using a recent Eclipse version (i.e. _Neon_ or later)
 * Solution: Enable assertions in JUnit tests as described
   [here](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option). <br>
   Delete run configurations created when you ran tests earlier.

## 5. Dev Ops

### 5.1 Build Automation

See [UsingGradle.md](UsingGradle.md) to learn how to use Gradle for build automation.

### 5.2 Continuous Integration

We use [Travis CI](https://travis-ci.org/) and [AppVeyor](https://www.appveyor.com/) to perform _Continuous Integration_ on our projects.
See [UsingTravis.md](UsingTravis.md) and [UsingAppVeyor.md](UsingAppVeyor.md) for more details.

### 5.3 Publishing Documentation

See [UsingGithubPages.md](UsingGithubPages.md) to learn how to use GitHub Pages to publish documentation to the
project site.

### 5.4 Making a Release

Here are the steps to create a new release.

 1. Generate a JAR file [using Gradle](UsingGradle.md#creating-the-jar-file).
 2. Tag the repo with the version number. e.g. `v0.1`
 2. [Create a new release using GitHub](https://help.github.com/articles/creating-releases/)
    and upload the JAR file you created.

### 5.5 Converting Documentation to PDF format

We use [Google Chrome](https://www.google.com/chrome/browser/desktop/) for converting documentation to PDF format,
as Chrome's PDF engine preserves hyperlinks used in webpages.

Here are the steps to convert the project documentation files to PDF format.

 1. Make sure you have set up GitHub Pages as described in [UsingGithubPages.md](UsingGithubPages.md#setting-up).
 1. Using Chrome, go to the [GitHub Pages version](UsingGithubPages.md#viewing-the-project-site) of the
    documentation file. <br>
    e.g. For [UserGuide.md](UserGuide.md), the URL will be `https://<your-username-or-organization-name>.github.io/burdens/docs/UserGuide.html`.
 1. Click on the `Print` option in Chrome's menu.
 1. Set the destination to `Save as PDF`, then click `Save` to save a copy of the file in PDF format. <br>
    For best results, use the settings indicated in the screenshot below. <br>
    <img src="images/chrome_save_as_pdf.png" width="300"><br>
    _Figure 5.4.1 : Saving documentation as PDF files in Chrome_

### 5.6 Managing Dependencies

A project often depends on third-party libraries. For example, Burdens depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | add task | view and manage my tasks using the application
`* * *` | user | delete task | remove tasks that I have completed
`* * *` | user | edit task | update my tasks accordingly
`* * *` | user | view task | recall the details of the task that I have input earlier
`* * *` | user | be able to mark completed tasks | differentiate between completed and uncompleted tasks
`* * *` | user | be able to undo action | undo unwanted command
`* * *` | user | be able to redo action | restore previous command
`* * *` | user | have a list of commands to show | see all commands
`* *` | user working in teams | be able to import task files | add multiple tasks given by team mates
`* *` | user working in teams | be able to export task files | transfer multiple tasks to team mates
`* *` | user with many tasks | list all tasks | recall all the tasks I have so far
`* *` | user with many tasks | list all urgent tasks | recall all the important tasks to be completed
`* *` | user with many tasks | list all tasks by alphabetical order | recall all the tasks by alphabetical order
`* *` | user with many tasks | list all tasks by date | recall all tasks by date
`* *` | user with many tasks | list all tasks by priority | recall all the tasks by priority level
`* *` | user with many tasks | list all tasks by tag | recall all the tasks with a particular tag

{More to be added}

## Appendix B : Use Cases

(For all use cases below, the **System** is the `Burden` application and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Mark task as complete

**MSS**

1. User requests to list tasks
2. Burdens shows a list of tasks
3. User requests to mark a specific task in the list as complete
4. Burdens updates the task <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. Burdens shows an error message <br>
  Use case resumes at step 2

#### Use case: Export task files

**MSS**

1. User requests to list tasks
2. Burdens shows a list of tasks
3. User requests to export specific tasks in the list to file
4. Burdens requests for the desired file name and path
5. User inputs file name and path
6. Burdens outputs task file to the specified path <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

5a. The given file name or path is invalid

> 5a1. Burdens shows an error message <br>
  Use case resumes at step 4

#### Use case: Create subtasks

**MSS**

1. User requests to list tasks
2. Burdens shows a list of tasks
3. User requests to specify a task as a subtask of another task
4. Burdens requests for the index of the subtask and the parent task
5. User inputs the indexes of subtask and parent task
6. Burdens updates task list <br>
Use case ends.

**Extensions**

2a. The list is empty or contains only 1 task

> Use case ends

5a. The given indexes are invalid

> 5a1. Burdens shows an error message <br>
  Use case resumes at step 4

#### Use case: Adding a new task

**MSS**

1. User requests to add tasks
2. Burdens prompts user for name of task
3. User inputs name of task
4. Burdens adds the task <br>
Use case ends.

**Extensions**

3a. Name of task is empty

> Use case resumes at step 2


#### Use case: Deleting a task

**MSS**

1. User requests to delete tasks
2. Burdens prompts user for name of task
3. User inputs name of task
4. Burdens deletes the task <br>
Use case ends.

**Extensions**

3a. No such name of task exists
> Use case resumes at step 2

#### Use case: Editing a task

**MSS**

1. User requests to edit a task
2. Burdens prompts user for name of task
3. User inputs name of task
4. Burdens prompts user for new details of task
5. User inputs new details of task
6. Burdens edits the task <br>
Use case ends.

**Extensions**

3a. No such name of task exists
> Use case resumes at step 2

5a. New details of task is empty
> Use case resumes at step 4

#### Use case: View task

**MSS**

1. User requests to view a task
2. Burdens prompts for name of task
3. User inputs name of task
4. Burdens brings up details of task <br>
Use case ends.

**Extensions**

3a. No such name of task exists
> Use case resumes at step 2

#### Use case: List all tasks

**MSS**

1. User requests to list all tasks
2. Burdens lists all tasks <br>
Use case ends.

**Extensions**

1a. No tasks exist
> Burdens shows an error message <br>

#### Use case: List all urgent tasks

**MSS**

1. User requests to list all urgent tasks
2. Burdens lists all urgent tasks <br>
Use case ends.

**Extensions**

1a. No urgent tasks exist
> Burdens shows an error message

#### Use case: Create alarms and reminders

**MSS**

1. User requests to create alarm
2. Burdens prompts for date and time
3. User inputs date and time
4. Burdens creates alarm <br>
Use case ends.

**Extensions**

2a. Date or time does not exist

> Burdens shows an error message <br>
  Use case resumes at step 2

#### Use case: List commands

**MSS**

1. User requests to list commands
2. Burdens shows a list of commands <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

#### Use case: Undo action

**MSS**

1. User requests to undo action
2. Burdens prompts for confirmation
3. User inputs "yes"
4. Burdens undo action <br>
Use case ends.

**Extensions**

1a. No action has been done

> Use case ends

2a. User inputs "no"

> Use case ends

#### Use case: Redo action

**MSS**

1. User requests to redo action
2. Burdens prompts for confirmation
3. User inputs "yes"
4. Burdens redo action <br>
Use case ends.

**Extensions**

1a. No action has been done

> Use case ends

2a. User inputs "no"

> Use case ends

#### Use case: List all tasks by alphabetical order

**MSS**

1. User requests to list all tasks by alphabetical order
2. Burdens executes command <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

#### Use case: List all tasks by date

**MSS**

1. User requests to list all tasks by date
2. Burdens executes command <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

#### Use case: List all tasks by priority

**MSS**

1. User requests to list all tasks by priority
2. Burdens executes command <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

#### Use case: List all tasks by tag

**MSS**

1. User requests to list all tasks by tag
2. Burdens prompts for name of tag
3. User inputs name of tag
4. Burdens executes command <br>
Use case ends.

**Extensions**

3a. Name of tag does not exist

> Burdens shows an error message <br>
  Use case resumes at step 2

{More to be added}

## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 tasks without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands)
   should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Should be easy to install
5. Should be quick when starting the application(within 1 second)
6. Should be a free and open source project
7. Should be reliable and outputs error messages correctly
8. Should complete an operation within 0.5 seconds
9. Should be easy to use and non-technical
{More to be added}

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Private contact detail

> A contact detail that is not meant to be shared with others

## Appendix E : Product Survey

**Trello**
Author: Johann Wong Jun Guo

Pros:

* Beautiful kanban interface (sticky notes)
* Supports attachments like images and documents
* Fully customisable
* Able to sync across all devices by cloud
* Able to sync to third party applications like Google Calendar
* Able to include add ons


Cons:

* Non-command line interface
* Too powerful for a simple to do manager
* Unable to work offline

**Wunderlist**
Author: Nguyen Quoc Bao

Pros:

* Clearly displays tasks that have not been completed
* Tasks can be categorized under different lists
* Tasks can have sub tasks
* Possible to highlight tasks by marking as important (starred) or pinning tasks
* Can set deadlines for tasks
* Can create recurring tasks
* Can associate files with tasks
* Can be used offline
* Keyboard friendly – keyboard shortcuts to mark tasks as completed and important
* Search and sort functionality makes finding and organizing tasks easier
* Possible to synchronize across devices
* Give notifications and reminders for tasks near deadline or overdue

Cons:

* Wunderlist has a complex interface and might require multiple clicks to get specific tasks done. For example, it has separate field to add tasks, search for tasks and a sort button. There are various lists and sub-lists. Each list has a completed/uncompleted section and each task needs to be clicked to display the associated subtasks, notes, files and comment
* New users might not know how to use the advanced features e.g. creating recurring tasks

**Google Keep**
Author: Gerald Wong Wei Chuen

Pros:

* Available on all devices (Desktop, Web, Mobile) and also on all platforms (Android, iOS, macOS, Windows 10)
* Minimalist interface
* Need a Google account
* Able to collaborate and share with other people
* Able to archive and add labels to each note

Cons:

* Text based with no advanced functionality
* Primary function to store text notes, not for reminders and tasks
* Lack of CLI functionality or NLP for text parsing of dates

**Remember The Milk**
Author: Lee Wan Qing

Pros:

* Allows the management of large number of tasks
* Clean GUI
* Predefined search terms allows for easy access to tasks that are due in the near future
* Allows for multiple lists which allows for separation of tasks according to user’s preference (such as work-related tasks and personal  tasks not in the same list)
* Allows for transferring of tasks to others using the app which can be used as a collaboration tool in an organisation
* Has a mobile app which allows for usage in multiple devices in different occasions


Cons:

* Requires many clicks to add simple deadline that is due on some day but not in the near future because of the calendar GUI
* Very confusing for a new user due to functions hidden behind small buttons with icons that are not very intuitive.
* Lots of information is hidden in the UI which requires the user to click on each task individually in order to view them
* Offline mode is only available to paying users



