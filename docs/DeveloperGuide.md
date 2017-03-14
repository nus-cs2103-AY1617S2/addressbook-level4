# Burdens - Developer Guide

By : `W09-B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

0. [Introduction](#introduction)
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

## 0. Introduction

We are making Burdens to make sense of our daily lives and list our tasks and deadlines on a command-line platform.
You are welcome to contribute in any way!

## 1. Setting up

### 1.1. Prerequisites

Please ensure you have this prerequisites before contributing to development:

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
3. Enter an arbitrary configuration name e.g. addressbook
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

<img src="images\SDforDeletePerson.png" width="800"><br>
_Figure 2.1.3a : Component interactions for `delete 1` command (part 1)_

>Note how the `Model` simply raises a `AddressBookChangedEvent` when the Address Book data are changed,
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
<img src="images/DeletePersonSdForLogic.png" width="800"><br>
_Figure 2.3.1 : Interactions Inside the Logic Component for the `delete 1` Command_

### 2.4. Model component

Author: Cynthia Dharman

<img src="images/ModelClassDiagram.png" width="800"><br>
_Figure 2.4.1 : Structure of the Model Component_

**API** : [`Model.java`](../src/main/java/seedu/address/model/Model.java)

The `Model`,

* stores a `UserPref` object that represents the user's preferences.
* stores the Address Book data.
* exposes a `UnmodifiableObservableList<ReadOnlyPerson>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

### 2.5. Storage component

Author: Darius Foong

<img src="images/StorageClassDiagram.png" width="800"><br>
_Figure 2.5.1 : Structure of the Storage Component_

**API** : [`Storage.java`](../src/main/java/seedu/address/storage/Storage.java)

The `Storage` component,

* can save `UserPref` objects in json format and read it back.
* can save the Address Book data in xml format and read it back.

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

A project often depends on third-party libraries. For example, Address Book depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | add task | add task
`* * *` | user | delete task | remove tasks that I have completed
`* * *` | user | edit task | update my tasks accordingly
`* * *` | user | view task | recall the details of the task that I have input earlier
`* * *` | user | be able to mark completed tasks | differentiate between completed and uncompleted tasks
`* * *` | user | be able to undo action | undo unwanted command
`* * *` | user | be able to redo action | restore previous command
`* * *` | user | set alarms and reminders | remember tasks on hand
`* * *` | user | have a list of commands to show | see all commands
`* *` | user working in teams | be able to import task files | add multiple tasks given by team mates
`* *` | user working in teams | be able to export task files | transfer multiple tasks to team mates
`* *` | user with many tasks | list all tasks | recall all the tasks I have so far
`* *` | user with many tasks | list all urgent tasks | recall all the important tasks to be completed
`* *` | user with many tasks | list all tasks by alphabetical order | recall all the tasks by alphabetical order
`* *` | user with many tasks | list all tasks by date | recall all tasks by date
`* *` | user with many tasks | list all tasks by priority | recall all the tasks by priority
`* *` | user with many tasks | list all tasks by tag | recall all the tasks with a particular tag
`* *` | user | have shortcuts | set up a reminder quicker
`*` | user who works with complex tasks | create subtasks | break a task into smaller tasks for easier management
`*` | user who also use other task managing applications | synchronize my task list across all my applications | manage the same tasks from different applications
`*` | user | be able to customize the colours | make it look more appealing
`*` | user | have UI dark mode | ease the stress on my eyes

{More to be added}

## Appendix B : Use Cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Mark task as complete

**MSS**

1. User requests to list tasks
2. KoolToDoManager shows a list of tasks
3. User requests to mark a specific task in the list as complete
4. KoolToDoManager updates the task <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. KoolToDoManager shows an error message <br>
  Use case resumes at step 2

#### Use case: Export task files

**MSS**

1. User requests to list tasks
2. KoolToDoManager shows a list of tasks
3. User requests to export specific tasks in the list to file
4. KoolToDoManager requests for the desired file name and path
5. User inputs file name and path
6. KoolToDoManager outputs task file to the specified path <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

5a. The given file name or path is invalid

> 5a1. KoolToDoManager shows an error message <br>
  Use case resumes at step 4

#### Use case: Create subtasks

**MSS**

1. User requests to list tasks
2. KoolToDoManager shows a list of tasks
3. User requests to specify a task as a subtask of another task
4. KoolToDoManager requests for the index of the subtask and the parent task
5. User inputs the indexes of subtask and parent task
6. KoolToDoManager updates task list <br>
Use case ends.

**Extensions**

2a. The list is empty or contains only 1 task

> Use case ends

5a. The given indexes are invalid

> 5a1. KoolToDoManager shows an error message <br>
  Use case resumes at step 4

#### Use case: Adding a new task

**MSS**

1. User requests to add tasks
2. KoolToDoManager prompts user for name of task
3. User inputs name of task
4. KoolToDoManager adds the task <br>
Use case ends.

**Extensions**

3a. Name of task is empty

> Use case resumes at step 2


#### Use case: Deleting a task

**MSS**

1. User requests to delete tasks
2. KoolToDoManager prompts user for name of task
3. User inputs name of task
4. KoolToDoManager deletes the task <br>
Use case ends.

**Extensions**

3a. No such name of task exists
> Use case resumes at step 2

#### Use case: Editing a task

**MSS**

1. User requests to edit a task
2. KoolToDoManager prompts user for name of task
3. User inputs name of task
4. KoolToDoManager prompts user for new details of task
5. User inputs new details of task
6. KoolToDoManager edits the task <br>
Use case ends.

**Extensions**

3a. No such name of task exists
> Use case resumes at step 2

5a. New details of task is empty
> Use case resumes at step 4

#### Use case: View task

**MSS**

1. User requests to view a task
2. KoolToDoManager prompts for name of task
3. User inputs name of task
4. KoolToDoManager brings up details of task <br>
Use case ends.

**Extensions**

3a. No such name of task exists
> Use case resumes at step 2

#### Use case: List all tasks

**MSS**

1. User requests to list all tasks
2. KoolToDoManager lists all tasks <br>
Use case ends.

**Extensions**

1a. No tasks exist
> KoolToDoManager shows an error message <br>

#### Use case: List all urgent tasks

**MSS**

1. User requests to list all urgent tasks
2. KoolToDoManager lists all urgent tasks <br>
Use case ends.

**Extensions**

1a. No urgent tasks exist
> KoolToDoManager shows an error message

#### Use case: Create alarms and reminders

**MSS**

1. User requests to create alarm
2. KoolToDoManager prompts for date and time
3. User inputs date and time
4. KoolToDoManager creates alarm <br>
Use case ends.

**Extensions**

2a. Date or time does not exist

> KoolToDoManager shows an error message <br>
  Use case resumes at step 2

#### Use case: List commands

**MSS**

1. User requests to list commands
2. KoolToDoManager shows a list of commands <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

#### Use case: Use shortcuts

1. KoolToDoManager requests for a shortcut
2. User keys in a shortcut
3. KoolToDoManager executes shortcut <br>
Use case ends.

**Extensions**

3a. Shortcut does not exist

> Use case resumes at step 1

#### Use case: Undo action

**MSS**

1. User requests to undo action
2. KoolToDoManager prompts for confirmation
3. User inputs "yes"
4. KoolToDoManager undo action <br>
Use case ends.

**Extensions**

1a. No action has been done

> Use case ends

2a. User inputs "no"

> Use case ends

#### Use case: Redo action

**MSS**

1. User requests to redo action
2. KoolToDoManager prompts for confirmation
3. User inputs "yes"
4. KoolToDoManager redo action <br>
Use case ends.

**Extensions**

1a. No action has been done

> Use case ends

2a. User inputs "no"

> Use case ends

#### Use case: List all tasks by alphabetical order

**MSS**

1. User requests to list all tasks by alphabetical order
2. KoolToDoManager executes command <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

#### Use case: List all tasks by date

**MSS**

1. User requests to list all tasks by date
2. KoolToDoManager executes command <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

#### Use case: List all tasks by priority

**MSS**

1. User requests to list all tasks by priority
2. KoolToDoManager executes command <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

#### Use case: List all tasks by tag

**MSS**

1. User requests to list all tasks by tag
2. KoolToDoManager prompts for name of tag
3. User inputs name of tag
4. KoolToDoManager executes command <br>
Use case ends.

**Extensions**

3a. Name of tag does not exist

> KoolToDoManager shows an error message <br>
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

**Product Name**

Author: ...

Pros:

* ...
* ...

Cons:

* ...
* ...

