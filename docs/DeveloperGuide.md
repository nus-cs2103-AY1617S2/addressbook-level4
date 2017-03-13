# TaskBoss - Developer Guide

By : `Team W14-B2`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Setting Up](#setting-up)
2. [Target Users](#target-users)
3. [Design](#design)
4. [Implementation](#implementation)
5. [Testing](#testing)
6. [Dev Ops](#dev-ops)

* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b--use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Glossary](#appendix-d--glossary)
* [Appendix E : Product Survey](#appendix-e--product-survey)


## Setting up

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

## Target Users

Our target users (eg. `Jim`) are people who:
* usually work alone on their personal or office computer
* do not share their computer with others
* prefer a command-line approach over a mouse-clicking approach
* would like to have an organized set up of tasks to categorize, sort, and prioritise them for ease of task management  


## Design

### 3.1. Architecture

<img src="images/Architecture.png" width="600"><br>
_Figure 2.1.1 : Architecture Diagram_

The **_Architecture Diagram_** given above explains the high-level design of the App.
Given below is a quick overview of each component.

> Tip: The `.pptx` files used to create diagrams in this document can be found in the [diagrams](diagrams/) folder.
> To update a diagram, modify the diagram in the pptx file, select the objects of the diagram, and choose `Save as picture`.

`Main` has only one class called [`MainApp`](../src/main/java/seedu/taskboss/MainApp.java). It is responsible for,

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

>Note how the `Model` simply raises a `TaskBossChangedEvent` when the TaskBoss data is changed,
 instead of asking the `Storage` to save the updates to the hard disk.

The diagram below shows how the `EventsCenter` reacts to that event, which eventually results in the updates
being saved to the hard disk and the status bar of the UI being updated to reflect the 'Last Updated' time. <br>
<img src="images\SDforDeletePersonEventHandling.png" width="800"><br>
_Figure 2.1.3b : Component interactions for `delete 1` command (part 2)_

> Note how the event is propagated through the `EventsCenter` to the `Storage` and `UI` without `Model` having
  to be coupled to either of them. This is an example of how this Event Driven approach helps us reduce direct
  coupling between components.

The sections below give more details of each component.

### 3.2. UI component

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

### 3.3. Logic component

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

### 3.4. Model component

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

### 3.5. Storage component

Author: Darius Foong

<img src="images/StorageClassDiagram.png" width="800"><br>
_Figure 2.5.1 : Structure of the Storage Component_

**API** : [`Storage.java`](../src/main/java/seedu/address/storage/Storage.java)

The `Storage` component,

* can save `UserPref` objects in json format and read it back.
* can save the Address Book data in xml format and read it back.

### 3.6. Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

## Implementation

### 4.1. Logging

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

### 4.2. Configuration

Certain properties of the application can be controlled (e.g App name, logging level) through the configuration file
(default: `config.json`):


## Testing

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

### 5.1. Troubleshooting tests

 **Problem: Tests fail because NullPointException when AssertionError is expected**

 * Reason: Assertions are not enabled for JUnit tests.
   This can happen if you are not using a recent Eclipse version (i.e. _Neon_ or later)
 * Solution: Enable assertions in JUnit tests as described
   [here](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option). <br>
   Delete run configurations created when you ran tests earlier.

## Dev Ops

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


## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the TaskBoss
`* * *` | user | add tasks | record tasks that I need to get done |
`* * *` | user | delete tasks | get rid of tasks that I no longer need
`* * *` | user | edit tasks | update any outdated information
`* * *` | user | see a list of all the tasks | view all my pending tasks in a single view
`* * *` | user | mark my tasks as done | keep track of the task status
`* * *` | user | set deadlines for tasks that have due dates | track the urgency of any given task
`* * *` | user | sort the tasks based on their deadlines | know the most urgent tasks to address
`* * *` | user | search for any task | find a task if I remember some keywords from the description
`* *` | user | add a description to my tasks | keep note of the details related to any given task
`* *` | user | undo a command | not worry about any accidental actions I do
`* *` | user | export all the tasks into a specified folder to save | get access of my tasks from multiple computers
`* *` | user | set start and end times for any given task | cater for any event that I need to attend
`* *` | user | set priority levels for each of my tasks | give my attention to tasks without deadlines that are urgent
`* *` | user | see my tasks sorted based on priority | check what I need to do urgently
`* *` | user | categorize my tasks | keep my tasks organized
`* *` | user | see the tasks under a specific category | better manage my pending tasks
`* *` | user | see the tasks that I have completed | re-trace my completed tasks if need be
`* *` | user | create categories | add tasks under specific categories
`* *` | user | view a specific task | focus on that task
`* *` | user | clear all tasks under a specific category | delete a bulk of tasks at one time
`*` | user | set recurring tasks | not have to manually add the same task over and over again
`*` | user | set reminders | be reminded of tasks if need be
`*` | user | set locations | check location of the task (if any)
`*` | user | add people | check people associated with the task (if any)
`*` | user | create labels for tasks | easily group certain similar tasks together
`*` | user | redo a command after undoing a command | revert the state of TaskBoss if need be
`*` | user | type shorter commands | execute commands faster
`*` | user | integrate Google Calendar | see a monthly view of my tasks
`*` | user | view all tasks that are between a specified date/time interval | not be distracted by other tasks meant for another time/date

{More to be added}

## Appendix B : Use Cases

(For all use cases below, the **System** is`TaskBoss` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Add a task

**MSS**

1.  User requests to add a task according to some parameters
2.  TaskBoss adds the new task. <br>
Use case ends

**Extensions**

1a. User input is invalid

> 1a1. TaskBoss shows an error message <br>
Use case ends

1b. The category user enters does not match the existing categories

> 1b1. TaskBoss shows an error message <br>
> 1b2. TaskBoss adds the new task without the category <br>
Use case ends

1c. User enters incorrect format for time and date

> 1c1. TaskBoss shows an error message with the expected input format <br>
> 1c2. TaskBoss adds the new task without time and date <br>
Use case ends

1d. Start time and end time are the same

> 1d1. TaskBoss shows an error message <br>
> 1d2. TaskBoss adds the new task without time and date <br>
Use case ends

1e. Start time is later than end time

> 1e1. TaskBoss shows an error message <br>
> 1e2. TaskBoss adds the new task without time and date <br>
Use case ends
  
#### Use case: List all tasks

**MSS**

1.  User requests to list all tasks
2.  TaskBoss displays all tasks. <br>
Use case ends.

**Extensions**

1a.  No tasks to display

> 1a1. TaskBoss shows “No tasks to display” message <br>
Use case ends

#### Use case: Edit a task

**MSS**

1.  User requests to edit certain attribute(s) of a task
2.  TaskBoss updates the task. <br>
Use case ends

**Extensions**

1a. The given task index is invalid

> 1a1. TaskBoss shows an error message <br>
Use case ends

#### Use case: Delete a task

**MSS**

1.  User requests to delete a task
2.  TaskBoss deletes the specific task the user requests for. <br>
Use case ends

**Extensions**

1a. The given task index is invalid

> 1a1. TaskBoss shows an error message <br>
Use case ends

#### Use case: Mark task as done

**MSS**

1. User requests to mark a task as done
2. TaskBoss adds the task to the ‘Done’ section, and removes it from ‘All Tasks’ and the task’s category. <br>
Use case ends

**Extensions**

1a. The given task index is invalid

> 1a1. TaskBoss shows an error message. <br>
Use case ends


#### Use case: Sort tasks by deadline

**MSS**

1. User requests to sort tasks by deadline
2. TaskBoss displays list of tasks sorted by deadline. <br>
Use case ends.

#### Use case: Undo previous command

**MSS**

1. User requests to undo previous command
2. TaskBoss undoes previous command and returns to a state before the previous command. <br>
Use case ends

**Extensions**

2a. Previous command is invalid

> 2a1. TaskBoss shows "cannot undo" error message. <br>
Use case ends. 

#### Use case: Export tasks to an existing file

**MSS**

1. User requests to export tasks to an existing file for data storage
2. TaskBoss changes the storage location, and saves all existing data into the new file. <br>
Use case ends

**Extensions**

2a. The given file path cannot be accessed

> 2a1. TaskBoss shows an error message. <br>
Use case ends.

2b. The given file path does not exist

> 2b1. TaskBoss shows an error message. <br>
Use case ends.

#### Use case: Export tasks to a new file

**MSS**

1. User requests to export tasks to a new file for data storage, specifying the file path and the new file name
2. TaskBoss saves all existing data into the new file. <br>
Use case ends

**Extensions**

2a. The given file path cannot be accessed

> 2a1. TaskBoss shows "Cannot access file path" error message.<br>
 Use case ends.
 
#### Use case: Search tasks

**MSS**

1. User requests to search task by keyword (keyword from description, task title, or deadline)
2. TaskBoss displays list of tasks containing keyword. <br>
Use case ends

**Extensions**

3a. The task does not exist

> 3a1. TaskBoss shows “0 tasks found” message. <br>
Use case ends.
 
#### Use case: List tasks by category

**MSS**

1. User requests to list tasks by specified category
2. TaskBoss displays all tasks of the category that user requests. <br>
Use case ends

**Extension**

3a. The category does not exist

> 3a1. TaskBoss shows “0 tasks found” message. <br>
Use case ends.

#### Use case: Create new category

**MSS**

1. User requests to create a new category and specifies name
2. TaskBoss creates category. <br>
Use case ends.

**Extensions**

1a. Category name already exists

> 1a1. TaskBoss shows error message “Category already exists”. <br>
Use case ends.

#### Use Case: Edit category name

**MSS**

1. User requests to edit category name
2. TaskBoss updates the category name.<br>
Use case ends.

**Extensions**

1a. Category to be edited does not exist.
		
> 1a1. TaskBoss shows error message “Category does not exist”. <br>
Use case ends.

#### Use case: Exit TaskBoss

**MSS**

1. User requests to exit the TaskBoss application
2. TaskBoss exits. <br>
User case ends.

#### Use case: Clear tasks under certain category

**MSS**

1. User requests to clear all tasks of certain category
2. TaskBoss clears all tasks of the specified category. <br>
Use case ends.

**Extensions**

1a. The category user enters does not match the existing categories.

> 1a1. TaskBoss shows an error message. <br>
Use case ends.

#### Use case: Display help guide

**MSS**

1. User requests for help guide
2. TaskBoss displays help guide
3. User types any key in the command line
4. Help guide disappears. <br>
Use case ends

#### Use case: View a specific task

**MSS**

1. User requests to view a specific task
2. TaskBoss displays the task user requests. <br>
Use case ends

**Extensions**

1a. The given task index is invalid

> 1a1. TaskBoss shows an error message.<br>
Use case ends  


## Appendix C : Non Functional Requirements

1. Should handle at least 100 tasks.
2. Should work without Internet connection.
3. Should have an intuitive user interface.
4. Should start up in less than  2 seconds.
5. Should respond to commands in less than 1 second.
6. Should come with automated unit tests and open source code.
 

## Appendix D : Glossary

##### Priority level

> A number between 1 and 3 inclusive that indicates how urgent a task is

##### Task index

> A number assigned to each tasks to ease referrals when deleting or editing.

##### Category

> A way to classify each type of task.

##### Done tasks

> Tasks that have been completed.

##### Previous command

> The last executed command.

{add more here}

## Appendix E : Product Survey

####Google Keep

Author: Al Sharef Haya Fayez M

**Pros:**

1. Uses sticky notes to record tasks
2. Has colorful UI
3. Provides instant capturing of anything by speech and pictures
4. Able to be integrated with Google Drive and Google Docs
5. Free to use 
6. No need to set up an account
7. Supports offline editing 
8. Supports undo option <br>

**Cons:**

1. Does not support calendar view
2. Hard to organize large amount of notes
3. Unable to categorize tasks
4. Does not support short-cut typed commands
5. Depends on mouse clicks <br>

####MeisterTask

Author: Tan Wei

**Pros:**

1. Has beautiful user interface
2. Free for personal use
3. Supports third-party application integrations (eg. Google, Dropbox)
4. Available across most platforms (Web app, Android, OS X)
5. Can enable task relationships <br>

**Cons:**

1. Does not support monthly calendar view
2. Unable to set recurring tasks
3. Unable to set reminders
4. Tasks are in card-view. Can be hard to track large amount of tasks.  <br>

####Just

Author: Xu RuoLan

**Pros:**

1. Has simple and clear user interface
2. Provides statistical analysis to track daily/monthly/yearly statistics
3. Support tasks categorizing 
4. Easy to use(add, delete and sort the tasks)
5. Supports priority setting
6. Supports task reminders <br>

**Cons:**

1. Hard to search if the to-do list is long <br>

####Persoda

Author: Soh Wei Kiat Melvin

**Pros:**

1. Provides secure accounts and protected password 
2. Has user-friendly interface
3. Provides a wide range of functions
4. Requires users to pay per usage
5. Supports task reminders/hierarchy 
6. Supports instant messaging teams within users <br>

**Cons:**

1. Might be confusing to new users as features are based off different price plans <br>
