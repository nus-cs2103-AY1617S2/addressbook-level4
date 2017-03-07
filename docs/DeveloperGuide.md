[//]: # (27 Feb | Tian Song: User Stories, UC01 - UC12, Product Survey | Mustaqiim: UC13 - UC25, Product Survey | Shawn: Product Survey | Jeremy: Product Survey)

# Suru - Developer Guide

By : `Team W09-B3`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `?`

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
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the application
`* * *` | new user | can view more information about a particular command | learn how to use various commands
`* * *` | user | add task by specifying task description only | record tasks that need to be done ‘someday’
`* * *` | user | add task with due date | record task that has to be done by given date
`* * *` | user | add event with specific duration | record event that takes place within a given timeframe
`* * *` | user | add follow-up tasks using start time | record tasks that has to be done after a certain date
`* * *` | user | delete task | get rid of task that I no longer care to track
`* * *` | user | edit task | edit task name, description
`* * *` | user | view all tasks |
`* * *` | user | view incomplete tasks |
`* * *` | user | view completed tasks |
`* * *` | user | undo previous action | revert previous action in case of mistakes
`* * *` | user | redo | reverse the effects of the last undo
`* * *` | user | search incomplete tasks by date or date range | check tasks due on a certain day or within a time frame
`* * *` | user | search for tasks | find a specific task
`* * *` | user | check off task | track what tasks are done
`* * *` | user | uncheck a task | correct accidental check off of a task
`* * *` | advanced user | Define save and load directory | specify which directory the program read and write from
`* *` | user | add reminders to task | be reminded before task is going to be due
`* *` | user | edit reminders of task |
`* *` | user | delete reminders of task |
`* *` | user | add a recurring task | add multiple entries of repetitive tasks
`* *` | user | add tags to task | categorize tasks by tags
`* *` | user | edit tags under a task |
`* *` | user | remove tags from task |
`* *` | user | sort tasks by tags | search for tasks with common category
`* *` | advanced user | hotkey shortcut | auto open the application without mouse interaction
`*` | new user | step-by-step tutorial for common features | get familiar with the program
`*` | user | add a task with checklist | find out about the different things to be done for the task
`*` | user | edit a checklist |
`*` | user | remove a checklist |
`*` | advanced user | use shorter versions of a command | type command faster
`*` | advanced user | add task reminder to Google Cal | due task shown as full day event in calendar
`*` | advanced user | parse my email automatically | automatically add task into my todo list
`*` | advanced user | voice recognition | auto include my task in without keyboard interaction

{More to be added}

## Appendix B : Use Cases

(For all use cases below, the **System** is the `Suru Task Manager` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: UC01 - Add task

**MSS**

1. User requests to add new task specifying task description and no date.
2. Suru adds task to list of tasks.
3. Suru GUI is refreshed to show the updated list of tasks.
Use case ends.

**Extensions**

1a. User requests to add new task specifying tasks and due date/time.

> 1a1. Suru adds task to list of task with due date/time.
> Use case resumes at step 3.

1b. User requests to add new task specifying tasks and time frame for task.

> 1b1. Suru adds task to list of task with time frame.
> Use case resumes at step 3.

1c. User requests to add new task specifying tasks and start date/time.

> 1c1. Suru adds task to list of task with start date/time.
> Use case resumes at step 3.

1d. User requests to add task without task description or using invalid syntax.

> 1d1. Suru displays error message.
> Use case ends.
  
  
#### Use case: UC02 - view tasks

**MSS**

1. User requests to shows all tasks.
2. Suru displays all tasks.
Use case ends.

**Extensions**

1a. User requests to show only incomplete tasks.

> Use case resumes at step 2.

1b. User requests to show only complete tasks.

> Use case resumes at step 2.

2a. The requested list is empty.

> 2a1. Suru displays error message.
> Use case ends.

#### Use case: UC03 - search for tasks

**MSS**

1. User requests to find tasks with specific keyword(s).
2. Suru displays all tasks containing specified keyword(s).
Use case ends.

**Extensions**

2a. The requested list is empty.

> 2a1. Suru displays error message.
> Use case ends.

#### Use case: UC04 - edit task

**MSS**

1. User **view all tasks (_UC02_).**
2. User requests to update a task by index with changed details.
3. Suru requests user for confirmation.
4. User confirms changes.
5. Suru GUI is refreshed to show updated list of tasks.
Use case ends.

**Extensions**

2a. The given index is invalid.

> 2a1. Suru displays error message.
> Use case ends.

2b. The new details are given using invalid syntax.

> 2b1. Suru displays error message.
> Use case ends.

4a. User rejects changes.

> Use case ends.

#### Use case: UC05 - delete task

**MSS**

1. User **view all tasks (_UC02_).**
2. User requests to delete a task by index.
3. Suru requests user for confirmation.
4. User confirms changes.
5. Suru GUI is refreshed to show updated list of tasks.
Use case ends.

**Extensions**

2a. The given index is invalid.

> 2a1. Suru displays error message.
> Use case ends.

4a. User rejects changes.

> Use case ends.

#### Use case: UC06 - Undo previous action

**MSS**

1. User requests to undo action.
2. Suru displays list of tasks according to before previous action.
Use case ends.

**Extensions**

2a. Undo is the first command entered by User during current start up.

> 2a1. Suru displays error message.
> Use case ends.

#### Use case: UC07 - Redo previous 'undo'

**MSS**

1. User requests to redo previous 'undo'.
2. Suru displays list of tasks as according to before previous undo.
Use case ends.

**Extensions**

2a. Undo is the first command entered by User during current start up.

> 2a1. Suru displays error message.
> Use case ends.

#### Use case: UC08 - Search for incomplete task by date or date range

**MSS**

1. User requests to search for task with either date or date range.
2. Suru displays list of tasks that falls on specified date or within date range.
Use case ends.

**Extensions**

2a. The requested list is empty.

> 2a1. Suru displays error message.
> Use case ends.

2b. The date or date range is in invalid format. (e.g end date earlier than start date)

> 2b1. Suru displays error message.
> Use case ends.

#### Use case: UC09 - check off task

**MSS**

1. User **view all tasks (_UC02_).**
2. User requests to check off a task by index.
3. Suru requests user for confirmation.
4. User confirms changes.
5. Suru GUI is refreshed to show updated list of tasks.
Use case ends.

**Extensions**

2a. The given index is invalid.

> 2a1. Suru displays error message.
> Use case ends.

2b. The requested task to check off is already checked.

> 2b1. Suru displays error message.
> Use case ends.

4a. User rejects changes.

> Use case ends.

#### Use case: UC10 - uncheck a task

**MSS**

1. User **view all tasks (_UC02_).**
2. User requests to uncheck a task by index.
3. Suru requests user for confirmation.
4. User confirms changes.
5. Suru GUI is refreshed to show updated list of tasks.
Use case ends.

**Extensions**

2a. The given index is invalid.

> 2a1. Suru displays error message.
> Use case ends.

2b. The requested task to uncheck is not checked.

> 2b1. Suru displays error message.
> Use case ends.

4a. User rejects changes.

> Use case ends.

#### Use case: UC11 - add a reminder to a task

**MSS**

1. User **view all tasks (_UC02_).**
2. User requests to add a reminder to a task by index and reminder date/time.
3. Suru requests user for confirmation.
4. User confirms changes.
5. Suru GUI is refreshed to show updated list of tasks.
6. Suru shows user a notification when reminder date/time is hit.
Use case ends.

**Extensions**

2a. The given index is invalid.

> 2a1. Suru displays error message.
> Use case ends.

2b. The requested task to add reminder is already completed.

> 2b1. Suru displays error message.
> Use case ends.

2c. The date for reminder is after due date of task. 

> 2c1. Suru displays error message.
> Use case ends.

2d. The date is in invalid format. 

> 2d1. Suru displays error message.
> Use case ends.

4a. User rejects changes.

> Use case ends.

#### Use case: UC12 - edit reminder for a task

**MSS**

1. User **view all tasks (_UC02_).**
2. User requests to edit a reminder for a task by index and reminder date/time.
3. Suru requests user for confirmation.
4. User confirms changes.
5. Suru GUI is refreshed to show updated list of tasks.
6. Suru shows user a notification when new reminder date/time is hit.
Use case ends.

**Extensions**

2a. The given index is invalid.

> 2a1. Suru displays error message.
> Use case ends.

2b. The requested task to edit reminder is already completed.

> 2b1. Suru displays error message.
> Use case ends.

2c. The requested task to edit reminder has no existing reminder.

> 2c1. Suru displays error message.
> Use case ends.
> 
2d. The date for reminder is after due date of task. 

> 2d1. Suru displays error message.
> Use case ends.

2e. The date is in invalid format. 

> 2e1. Suru displays error message.
> Use case ends.

4a. User rejects changes.

> Use case ends.

#### Use case: UC13 - delete reminder for a task

**MSS**

1. User **view all tasks (_UC02_).**
2. User requests to delete a reminder for a task by index and reminder date/time.
3. Suru requests user for confirmation.
4. User confirms changes.
5. Suru GUI is refreshed to show updated list of tasks.
6. Suru does not show user notification when deleted reminder date/time is hit.
Use case ends.

**Extensions**

2a. The given index is invalid.

> 2a1. Suru displays error message.
> Use case ends.

2b. The requested task to delete reminder has no existing reminder.

> 2b1. Suru displays error message.
> Use case ends.

2c. The date is in invalid format. 

> 2c1. Suru displays error message.
> Use case ends.

4a. User rejects changes.

> Use case ends.

#### Use case: UC14 - add recurring task

**MSS**

1. User **view all tasks (_UC02_).**
2. User requests to repeat a task by index.
3. Suru requests user for confirmation together with number of times to be repeated.
4. User confirms changes.
5. Suru GUI is refreshed to show updated list of tasks. 

Use case ends.

**Extensions**

2a. The given index is invalid.

> 2a1. Suru displays error message.
> Use case ends.

2b. Task selected is repeated already.

> 2b1. Suru notify user about repeated task and new value will overwrite old value.
> Use case resumes at 3.

4a. User rejects changes.

> Use case ends.

#### Use case: UC15 - add tags to a task

**MSS**

1. User **view all tasks (_UC02_).**
2. User requests to add a tag or a few tags to a task by index.
3. Suru requests user for confirmation.
4. User confirms changes.
5. Suru GUI is refreshed to show updated list of tasks.
Use case ends.

**Extensions**

2a. The given index is invalid.

> 2a1. Suru displays error message.
> Use case ends.

2b. The requested task to add tags already exists.

> 2b1. Suru displays error message.
> Use case ends.

4a. User rejects changes.

> Use case ends.

#### Use case: UC16 - edit tags

**MSS**

1. User **view all tasks (_UC02_).**
2. User requests to update tag of a task by index with an updated tag.
3. Suru requests user for confirmation and existings tags will be overwritten by the new tags.
4. User confirms changes.
5. Suru GUI is refreshed to show updated tags tied to the task.
Use case ends.

**Extensions**

2a. The given index is invalid.

> 2a1. Suru displays error message.
> Use case ends.

2b. The given index has no active tags.

> 2b1. Suru displays error message.
> Use case ends.

4a. User rejects changes.

> Use case ends.

#### Use case: UC17 - delete tags

**MSS**

1. User **view all tasks (_UC02_).**
2. User requests to delete tags for a task by index.
3. Suru requests user for confirmation.
4. User confirms changes.
5. Suru GUI is refreshed to show updated tasks witout tags.
Use case ends.

**Extensions**

2a. The given index is invalid.

> 2a1. Suru displays error message.
> Use case ends.

2b. The requested tags to be deleted from the task does not exists.

> 2b1. Suru displays error message.
> Use case ends.

4a. User rejects changes.

> Use case ends.

#### Use case: UC18 - filter tasks by tags

**MSS**

1. User **view all tasks (_UC02_).**
2. User requests to filter tasks by tags.
3. Suru requests user for name of tags to filter the task by.
4. Suru GUI is refreshed to show updated list of tasks by tags. 

Use case ends.

**Extensions**

2a. The given tags does not exists.

> 2a1. Suru displays error message.
> Use case ends.

#### Use case: UC19 - add subtask to task

**MSS**

1. User **view all tasks (_UC02_).**
2. User selects task by index.
3. Suru display task details.
4. User add subtask inside the task.
5. Suru requests user for confirmation.
6. Suru GUI is refreshed to show updated list of tasks.

Use case ends.

**Extensions**

2a. The given index is invalid.

> 2a1. Suru displays error message.
> Use case ends.

4a. Task already have existing subtask.

> 4a1. Suru displays error message.
> Use case ends.

5a. User rejects changes.

> Use case ends.

#### Use case: UC20 - edit subtask in task 

**MSS**

1. User **view all tasks (_UC02_).**
2. User select task by index.
3. Suru displays subtask details inside the selected task.
4. User requests to edit subtask in the task by subtask index.
5. User insert updated subtask.
6. Suru request for user confirmation.
7. User confirm changes.
8. Suru GUI is refreshed to show updated checklist tied to the task.

Use case ends.

**Extensions**

2a. The given index is invalid.

> 2a1. Suru displays error message.
> Use case ends.

2b. The given task index has no checklist.

> 2b1. Suru displays error message.
> Use case ends.

4a. The given subtask index is invalid.

> 4a1. Suru displays error message.
> Use case ends.

7a. User rejects changes.

> Use case ends.

#### Use case: UC21 - delete subtask in task

**MSS**

1. User **view all tasks (_UC02_).**
2. User select task by index.
3. Suru displays subtask details inside the selected task.
4. User requests to delete subtask by subtask index.
5. Suru requests user for confirmation
6. User confirm changes.
7. Suru GUI is refreshed to show updated subtask details.
Use case ends.

**Extensions**

2a. The given index is invalid.

> 2a1. Suru displays error message.
> Use case ends.

2b. The given task index has no subtasks.

> 2b1. Suru displays error message.
> Use case ends.

4a. The given subtask index is invalid.

> 4a1. Suru displays error message.
> Use case ends.

7a. User rejects changes.
> Use case ends.


#### Use case: UC22 - Define save and load database

**MSS**

1. Suru scans default directory for previous save database during start-up
2. Save database is loaded into memory from default file location
3. Suru runs as per normal

Use case ends.

**Extensions**

2a. File not found in default directory

>2a1. Suru request user to locate database file or create new database file.<br>
 Use case resumes at step 2.
  
#### Use case: UC23 - Command Shortcut

**MSS**

1. Suru detects specific command shortcut during runtime
2. Suru maps to main command

Use case ends.

**Extensions**

1a. Command entered not found or invalid.

> 1a1. Suru displays error message.
> Use case ends.

#### Use case: UC24 - Add task reminder and due date to Google Calendar

**MSS**

1. User **view all tasks (_UC02_).**
2. User requests to add reminder and due date of the task to Google Calendar as an all day event.
3. Suru requests user for confirmation.
4. User confirms changes.
5. Added event will be reflected on user Google Calendar

Use case ends.

**Extensions**

2a. No Gmail account associated with the current session.

> 2a1. Suru displays error message.
> 2a2. Suru prompts user to enter Google login details.
> Use case resumes at 3.

2b. The selected task has no due date or reminder associated with it.

> 2b1. Suru displays error message.
> Use case ends.

4a. User rejects changes.

> Use case ends.

#### Use case: UC25 - Parse email to add task automatically

**MSS**

1. User requests to add new task by reading users email.
2. Suru scans user email content for possible upcoming task todo.
3. Suru displays possible todo task with their due dates.
4. User confirms which task to be added into the task manager.
5. Suru GUI is refreshed to show the updated list of tasks.

Use case ends.

**Extensions**

2a. No possible tasks detected in email.

> 2a1. Suru displays error message.
> 2a2. Suru prompts user if a manual add is required.
> Use case resumes at **(_UC1_ - Add task)**.

3a. Suru detected unwanted task from the email.

> 3a1. Suru displays list of possible tasks.
> 3a2. Suru asks for user confirmation.0
> Use case ends.

4a. User rejects changes.

> Use case ends.

#### Use case: UC26 - Hotkey Shortcut

**MSS**

1. Suru waits for user hotkey combination in background
2. User press selected keyboard combination to perform certain task in Suru
3. Suru GUI refreshed based on user input.

Use case ends.

**Extensions**

2a. User input wrong hotkey shortcut.

> 2a1. No action from Suru.

{More to be added}

## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 tasks without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands)
   should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Should be able to work without any internet connection.
5. Should be able to have data file synced using cloud syncing services (e.g dropbox) if save/load directory is in the appropriate location.

{More to be added}

## Appendix D : Glossary

##### Mainstream OS

> Windows 7 and above


## Appendix E : Product Survey
**IKE**
Author: Shawn Lin Jingjue

Pros:

* Prioritizes task by urgency and importance
* Due dates
* Checklists

Cons:

* No syncing across devices
* Only Android platform

**Wunderlist**

Author: Muhammad Mustaqiim Bin Muhar

Pros:

* Able to share group tasks easily with teammates 
* Cross platform support on most devices and Operating Systems.
* Able to auto add reminders and due dates using rss link to a calendar of your choice

Cons:

* No import function of existing task from other apps to Wunderlist
* Assigning of to-dos to your team members is a paid feature (Organisation Features)

**Habitica**

Author: Jeremy Heng Wen Ming

Pros:
* Gamification paradigm extends to the social features of the application in the form of team quests. This is essentially a mechanism to share group tasks and to mantain accountability
* A loot and shop system allows users to obtain and purchase cosmetic items to signify character progress
* Splits tasks into dailies, habits and to-dos
* Tasks can be split into subtasks which can be assigned individual reward values

Cons:
* The interface is not efficient to use when adding many tasks
* There is no integration with any calendar applications

**Trello**

Author: Teo Tian Song

Pros:
* Has due date capability
* Keeps track of completed task
* Has checklist to break down tasks into components

Cons:
* Requires internet connection to utilize
* Requires consistent usage of mouse to perform commands











