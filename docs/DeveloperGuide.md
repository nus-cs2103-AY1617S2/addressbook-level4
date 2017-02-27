# AddressBook Level 4 - Developer Guide

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
`* * *` | user | add a task by specifying a task name and description only | record tasks that need to be done some day
`* * *` | user | add a task by specifying a task name only | update its description some other day
`* * *` | user | specify task's due date and time when adding a task | record tasks that need to be done on a specific date and time
`* * *` | user | update its parent list after adding a task | group tasks that belong to the same category
`* * *` | user | view today's upcoming tasks | decide what do I need to finish by today
`* * *` | user | view all tasks under a specific list | view my tasks by categories
`* * *` | user | create a customised list other than the default lists | personalize my categories
`* * *` | user | delete a task | remove the tasks that is no longer useful or created by mistake
`* * *` | user | update the name of the list | modify it after creating
`* * *` | user | view the help message for a specific feature | know how to use a command
`* *` | user | view the finished tasks under each list | see what I have already completed
`* *` | user | mark a task as finished | know it's done
`* *` | user | update the name/time/due date/description of task | change its content
`* *` | user | add a task which repeats | save the effort of creating it every week/day
`* *` | first-time user | view the entire help messages | get to know various commands
`* *` | user | view the list of task by its due date| view the task due on that day
`* *` | user | specify priority level for a task when adding the task| know which task is more urgent
`* *` | user | undo my previous command| undo whenever I regret
`* *` | user | delete one most recent occurrence of a repeating task | choose not to see the task for one time
`*` | user | add in the map of the venue  | know how to get to a venue
`*` | user | add the venue of the task | add a venue to the task
`*` | user | delete the list and put all the tasks inside into Personal or delete all the tasks | delete the list and delete tasks by lists
`*` | user | see different colours for different priority levels | know which one is more emergent
`*` | user | specify priority level for a task after adding the task | know which task is more urgent
`*` | user | star a task to be my favourite (independent from list) | view it under list view
`*` | user | view all the starred tasks using a "favourite" command | view all my favourite tasks
`*` | user | list next n days tasks | know what to do in n days' time
`*` | user | see the autocomplete messages when I type | know what I can type
`*` | advanced user | change the appearance of the user interface | personalize the view
`*` | user | view all the past due tasks | get to know what task is left undone
`*` | user | specify its parent list when adding a task | group tasks that belong to the same category

{More to be added}

## Appendix B : Use Cases

(For all use cases below, the **System** is the `Dueue` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: View tasks due today

**MSS**

1. User requests to list tasks due today
2. Dueue shows a list of tasks<br>
Use case ends.

**Extensions**

2a. There is not task due today

> 2a1. Dueue shows message "You are done for today!"<br>
  Use case ends.

#### Use case: View tasks by list

**MSS**

1. User requests to display all list names
2. Dueue shows a list of list names with list indices
3. User requests to display all tasks of one list specified by list index
4. Dueue display the tasks of that list <br>
Use case ends.

**Extensions**

3a. The given list index is invalid

> 3a1. Dueue shows an error message <br>
  Use case resumes at step 2

4a. The list is empty

> 4a1. Dueue shows message "This list is currently empty."<br>
  Use case ends.

#### Use case: Create a customized list

**MSS**

1. User requests to create a new list
2. Dueue asks for the list name
3. User type in the name of the new list
4. Dueue displays the new customized list with new name <br>
Use case ends.

**Extensions**

3a. The name is already used for some other list 

> 3a1. Dueue shows the message "This name is already used."<br>
  Use case resumes at step 2

#### Use case: Update list name

**MSS**

1. User requests to display all list names
2. Dueue shows a list of list name
3. User requests to change name of a specific list
4. Dueue asks for a new name
5. User types in the new name
6. Dueue displays the list with new name <br>

**Extensions**

3a. The given index is invalid

> 3a1. Dueue shows an error message <br>
  Use case resumes at step 2

5a. The name is already used for some other list 

> 5a1. Dueue shows the message "This name is already used."<br>
  Use case resumes at step 4

#### Use case: View help message

1. User requests to view the help message
2. Dueue displays the help message <br>
Use case ends.

#### Use case: View finished tasks by list

1. User requests to display all lists name with finished tasks
2. Dueue displays a list of lists
3. User requests to view the finished tasks under a list specified with list index
4. Dueue displays the finished tasks under this list <br>

**Extensions**

2a. The list is empty

> 2a1. Dueue shows the message "There is no finished task."<br>
  Use case ends.

3a. The given list index is invalid

> 3a1. Dueue shows an error message <br>
  Use case resumes at step 2

#### Use case: View by due date

**MSS**

1. User requests to view all tasks due by a specified date
2. Dueue shows a list of tasks due that date<br>
Use case ends.

**Extensions**

1a. The given date is invalid

> 1a1. Dueue shows an error message<br>
  1a2. Dueue shows a list of allowed format for specifying dates<br>
  Use case resumes at step 1

2a. The list is empty

> 2a1. Dueue shows message "You have no task due on that date."<br>
  Use case ends.

#### Use case: Delete one most recent occurrence of repeating task

**MSS**

1. User requests to list all repeating tasks
2. Dueue shows a list of repeating tasks
3. User requests to delete one most recent occurrence of a task in the list
4. Dueue deletes the occurrence <br>
Use case ends.

**Extensions**

2a. The list is empty

> 2a1. Dueue shows message "You have no repeating tasks."<br>
  Use case ends.

3a. The given task index is invalid

> 3a1. Dueue shows an error message <br>
  Use case resumes at step 2

#### Use case: Delete list

**MSS**

1. User requests to display all the list names and indices
2. Dueue shows a list of list names and indices
3. User requests to delete a specific list
4. Dueue deletes the list <br>
Use case ends.

**Extensions**

2a. The list is empty

> 2a1. Dueue shows message "There are no lists."<br>
  Use case ends.

3a. The given index is invalid

> 3a1. Dueue shows an error message <br>
  Use case resumes at step 2
  
3b. The specified list still has unfinished tasks

> 3b1. Dueue prompts the user to decide whether to move the unfinished tasks to the Personal list<br>
  3b2. User requests to move to the Personal list or just delete<br>
  3b3. Dueue moves all unfinished tasks to Personal list or delete all unfinished tasks and deletes the list<br>
  Use case ends.

#### Use case: View different colors for different priority levels

**MSS**

1. User requests to display different colors for different priority levels
2. Dueue displays priority levels with different colors <br>
Use case ends.

#### Use case: View the same colors for different priority levels

**MSS**

1. User requests not to display different colors for diffrent priority levels.
2. Dueue displays priority levels with same color <br>
Use case ends.

#### Use case: Set priority level for a task

**MSS**

1. User requests to change priority of a certain task
2. Dueue asks for new priority level
3. User types in the new level
4. Dueue display this tasks with new level <br>
Use case ends.

**Extensions**

1a. The given index is invalid

> 1a1. Dueue shows an error message <br>
  Use case resumes at step 1

3a. The priority level is invalid

> 3a1. Dueue shows an error message <br>
  Use case resums at step 2

#### Use case: "Star" a task to be favorite

**MSS**

1. User requests to "star" a certain task
2. Dueue displays the task with star <br>
Use case ends.

**Extension**

1a. The given index is invalid

> 1a1. Dueue shows an error message <br>
  Use case resumes at step 1

1b. The task is already "starred"

> 1b1. Dueue shows an error message "This task is already starred." <br>
  Use case ends.

#### Use case: View all starred tasks

**MSS**

1. User requests to view all starred tasks
2. Dueue displays all tasks being starred <br>
Use case ends

**Extension**

1a. There is no starred task.

> 1a1. Dueue displays an error message "There is no task being starred." <br>
  Use case ends.
#### Use case: View all tasks due in the next n days

**MSS**

1. User requests to view all tasks due in the next n days
2. Dueue shows a list of tasks in chronological order<br>
Use case ends.

**Extensions**

1a. The given n is invalid

> 1a1. Dueue shows an error message <br>
  Use case resumes at step 1

2a. The list is empty

> 2a1. Dueue shows message "You have no tasks during that period."<br>
  Use case ends

#### Use case: See autocomplete messages when typing

**MSS**

1. User requests to list all tasks
2. Dueue shows a list of tasks
3. User types "finish" and types part of the name of a very long task name
4. Dueue displays all the possible tasks <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given name is not part of any task name

> 3a1. Dueue shows message "There is no matching task name" <br>
  3a2. User edits the name<br>
  Use case resumes at step 3

#### Use case: Change user interface

**MSS**

1. Advanced user requests to go into settings
2. Dueue shows a menu of settings
3. Advanced user requests to go into appearance
4. Dueue shows a list of commands you can use in appearance
5. Advanced user requests to change background color of UI to blue
6. Dueue changes the background color to blue <br>
Use case ends.

**Extensions**

6a. The RGB code of blue entered is invalid

> 6a1. Dueue shows message "RGB code entered is invalid"<br>
  Use case resumes at step 5

#### Use case: Change the list of a task
 
**MSS**
 
1. User requests to change the list of a task
2. Dueue ask the user for a list
3. User type in the name of the list
4. Dueue shows the tasks in the list
Use case ends.
 
**Extension**
 
1a. The given task is invalid
 
> 1a1. Dueue shows an error message and ask for a task<br>
  Use case resumes at step 2
 
3a. The user wants to create a new list for the task
 
> 3a1. Dueue shows an error message and ask whether the user wants to create a new list
> 3a2. User indicate yes<br>
Use case resume at step 4
 
3b. The list name does not exist
 
> 3b1. Dueue shows an error message and ask whether the user wants to create a new list
> 3b2. User indicate No
> 3b3. Dueue ask the user for a new list name<br>
  Use case resume at step 3 
 
#### Use case: Mark task as finished
 
**MSS**
 
1. User requests to mark a task as finished
2. Dueue show the current unfinished task
Use case ends.
 
**Extension**
 
1a. Task does not exist
> 1a1. Dueue shows an error message
Use case resume at step 1
 
1b. The task had been marked as finished
> 1b1. Dueue inform the User that the task had been marked
  Use case ends.
 
#### Use case: Change details of a task
 
**MSS**
 
1. User requests to change details of the task
2. Dueue ask for the details to be change
3. User type in the details to be changed
4. Dueue shows the changes
Use case ends.
 
**Extension**
 
1a. Task does not exist
> 1a1. Dueue shows an error message
Use case resume at step 1
 
#### Use case: View help message when entering the system.
 
**MSS**
 
1. User starts the system
2. Dueue display the help message
Use case ends
 
#### Use case: Add a task with priority level
 
**MSS**
 
1. User request for to add a task with priority level
2. Dueue shows the created task
Use case ends
 
**Extension**
 
1a. Priority level does not exist
> 1a1. Dueue shows an error message
Use case resume at step 1
 
#### Use case: Undo previous command
 
**MSS**
 
1. User request to undo previous command
2. Dueue display a successful undo message
Use case ends
 
#### Use case: View all past due task
 
**MSS**
 
1. User request to view all past due task
2. Dueue display all the past due task.
Use case ends
 
**Extension**
 
1a. There is no past due task
> 1a1. Dueue shows an error message
Use case ends

{More to be added}

## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands)
   should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Should be 

{More to be added}

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Private contact detail

> A contact detail that is not meant to be shared with others

## Appendix E : Product Survey

**Wunderlist**

Author: Yan Xiaoxuan

Pros:

* Can star a task as most important and task will automatically float to top
* Can customize background for the UI
* Can organize multiple tasks in lists, lists under folders
* Finished tasks will be automatically folded below unfinished ones
* Can restore deleted lists
* Can export task lists to formatted printable document
* Overdue tasks will be highlighted as red (relevant lists and folders are also highlighted)
* Subtask and notes functions are optional under each task 

Cons:

* Command msg format is a bit rigid (e.g. cannot interpret “due on Feb 14”)
* No priority feature attached to tasks (only have the “star” function)
* Cannot add repeated task (e.g. does not understand “everyday” command)
* Has no “undo” function
* Sorting by “alphabetical” or “creation date” order has low usability

**Remember the Milk**

Author: Yan Xiaoxuan

Pros:

* Can add repeated tasks (and deletion of repeated tasks is well-facilitated)
* Repeated task will be indicated with a special icon on the side (i.e. a everyday task will only be displayed for one time with the “repeat icon” in “this week’s list”, instead of repeated for several times)
* Can undo previous action
* Task addition function is easy to understand for new users (with a toolbar suggesting different functions)
* Special characters like ‘~’ and ‘^’ can replace words like “from” and “due on” for fast typing when adding tasks
* Can customize a lot of things, such as reminder frequency and “daily digest”
* Can archive a list instead of moving to trash
* Can add “location” (which is supported by Google Maps) and “tag”s to individual tasks by user defined location and tag lists
* Has “bulk update” function to process selected tasks together
* Can postpone task due date
* Tasks in the trash folder will be removed automatically after 30 days
* Can view “keyboard shortcuts” conveniently (users can continually learn to use them)

Cons:

* To mark a task as finished takes two steps to complete
* Cannot customize background
* Toolbars and sidebar is a bit monotone, with few visual highlights on key functions


**Todoist**

Author: Wang Zexin

Pros:

* Can create projects using “#”
* Can switch to project and add task of this project
* Can specify date using many different forms
* Can create repeating tasks (every Sunday)
* Can finish one occurrence of a repeating task early

Cons:

* Cannot add comment/description for task
* Cannot differentiate between deleting and finishing tasks
* Must click in order to navigate to projects
* Cannot customize background

**Trello**

Author: Mou Ziyang

Pros:

* Has clear 2 levels of hierarchy (Board; List)
* Has the option to archive and delete for finished tasks
* Very flexible layout, every object is draggable
* Can incorporate with many other softwares
* Can share with teammates
* Can attach relative files and images to a task

Cons:

* Hard to use without watching tutorial
* Cannot create repeated tasks
* Cannot customized background
* Cannot add priority for tasks (Have to sort tasks manually)

**Mac Calendar**

Author: Mou Ziyang

Pros:

* Can customize notification
* Can add repeated tasks
* Can delete one occurrence of the task
* Can mark festival and holiday out

Cons:
* "Bar graph schedule view" is not very useful
* Can only sort task in chronological order
* Ambiguous hierarchy
* Do not have importance level

**S Planner**

Author: Shermine Jong

Pros:
* Can set multiple reminders to an event/task
* Can set different task as different colour to group the tasks
* Can synchronize from email's calendar
* Can Specify the venue of the task and is linked to google map
* Could specify whether to repeat a certain task, the frequency of the repeat, the end date of the repeat or the number of repeat

Cons:
* There is a default reminder for a task which could be quite irritating