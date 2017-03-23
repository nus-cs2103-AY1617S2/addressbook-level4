# Yesterday's Tomorrow - Developer Guide

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
* stores the Task Manager data.
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
      e.g. `seedu.task.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units
     (those code units are assumed to be working).<br>
      e.g. `seedu.task.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as
      how the are connected together.<br>
      e.g. `seedu.task.logic.LogicManagerTest`

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
`* * *` | new user | see [usage instructions](#usage-instructions) |  refer to instructions when I forget how to use the App
`* * *` | user | add a new task | keep track of tasks I need to complete
`* * *` | user | delete a task | remove tasks that are no longer relevant
`* * *` | user | find a task by name | locate details of task without having to go through the entire list
`* * *`| user | edit a parameter of a task | update a task without needing to delete and re-add it
`* * *` | user | mark tasks as complete | see what I've already completed (and still have left to do)
`* * *` | user | undo my last action | easily fix typos and mistakes I make
`* * *` | user | specify a duration of the task | plan when I will complete the task
`* *`| user | list tasks in order of due date | prioritize what to do next
`* *`| user | mark tasks as important | prioritize what to do next
`* *`| user | have a calendar view of all tasks | see how busy I am
`* *`| user | have my emails converted to tasks or events automatically | save time on manually entering tasks and events
`* *`| user | add custom tags to tasks | organize my tasks by category
`* *`| user | be able to migrate my data to another computer | change computers without losing my tasks
`* *`| user | get a reminder about a task | don't miss a task due date
`*`| user | print out all tasks | keep a paper copy when there is no computer access
`*`| user | have an automatically prioritized list | have more time to do the items instead of planning
`*`| user | have an customizable GUI | feel good looking at my tasks and be motivated
`*`| user | attach files to tasks | keep track of supplimentary information for the task
`*`| user | email updates on tasks to people | alert individuals when a task is done
`*`| user | see percentage of time I spend doing certain types of tasks | have a better understanding of how my time is allocated
`*`| user | easily share tasks with other people | collaborate on a project with them
`*`| new user | import task data from other TODO products | easily migrate to Yesterday's Tomorrow
`*`| user | add subtasks to tasks | break down larger tasks
`*`| user | have a history of task edits | keep track of when tasks change (or when I make mistakes)
`**`| user | edit tag labels | update tag labels (or when I make mistakes)
`**`| user | create folders for my tasks | organize related tasks together
`*`| user | save my tasks on the cloud | have a backup of my data or view my tasks accross different devices
`*`| user | print out all tasks for a specific tag | view all tasks of a specific tag
`*`| user | add subtasks to my tasks | break down major tasks into smaller steps
`*`| user | add hyperlinks to my task descriptions | click to useful links without having to copy and paste into my browser

{More to be added}

## Appendix B : Use Cases

(For all use cases below, the **System** is `Yesterday's Tomorrow` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Delete person

**MSS**

1. User requests to list all tasks
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
  
#### Use case: Edit task

**MSS**

1. User requests a list of all tasks
2. System shows a list of all tasks
3. User requests changes to a certain parameter of a specific task
4. System changes the requested parameter of the task and displays the updated item to the user
Use case ends.

**Extensions**

3a. The given index is invalid

> 3a1. System shows an error message <br>
  Use case resumes at step 2

#### Use case: Add task or event

**MSS**

1. User enters the task with optional parameters such as due date, start and end time
2. System parses task and stores it to memory, then displays the task with formatted parameters back to user
Use case ends.

**Extensions**

2a. Parameters are entered in the wrong format

> 2a1. System shows an error message and an example of correct formatting
> Use case resumes at step 1

#### Use case: Undo last action

**MSS**

1. User enters undo command
2. System display command to be undone, and undoes the command
Use case ends.

**Extensions**

2a. There is no previous action

> Use case ends.

#### Use case: Search tasks

**MSS**

1. User enters the search command with given parameters to search for
2. System interprets the search command and displays a list of matching tasks
Use case ends.

**Extensions**

2a. No search terms are given

> 2a1. System shows an error message indicating the user's error
> Use case resumes at step 1


{More to be added}

## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not [system admin commands](#system-admin-commands))
   should be able to accomplish most of the tasks faster using commands than using the mouse.
4. All classes and public methods should have documentation.
5. All commands should be completed within 200ms.
6. 80% test coverage.

{More to be added}

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Usage Instructions

> list of commands with instructions on how to use them appropriately

##### System Admin Commands

> shell commands used to perform system level tasks

## Appendix E : Product Survey

**Product Name**

Author: 6 Wunderkinder GmbH

Pros:

* Can star tasks so important tasks to be easily found.
* Can parse natural language, ex., “Task A due Wednesday” will be converted to a task with the name Task A and the upcoming Wednesday as the due date.
* Supports subtasks, comments, and attachments for richer task details.
* Has reminders so user can be notified before a due date.
* Entry of tasks is very quick.

Cons:

* No calendar view to schedule tasks

**Trello**

Author: Fog Creek Software

Pros:

* Organize according to lists within a board (kanban)
* Tags on an item for organization across lists
* Due dates for reminding users when things have to be finished
* Calendar view to see what is due today/this week/this month
* Subtasks (checklists) for breaking down what needs to be done to complete the item
* Can subscribe to changes of a task so users know when collaborators update the tasks
* Task descriptions to provide more information about the task
* Collaborative - multiple people can share boards
* Commenting for conversations about tasks
* Add attachments (e.g. PDFs) for additional information
* Can filter cards for quickly finding tasks based on tags
* Integrates into other apps (e.g. Github, Dropbox, etc.)
* Can email tasks to a board to allow non-collaborators to add tasks (e.g. for customer support)

Cons:

* Takes longer to add complex tasks; no natural language input
* Keyboard shortcuts are slow to move around with
* Cards are narrow so long descriptions have line wraps
* Can get cluttered with too many "done" tasks

**Reminder**

Author: Apple Inc

Pros:

* Simple UI design, easy to learn to use
* Can set due dates for alert
* Well integrated with notification center on the iPhone (can see tasks and check off for the day)
* Can sync with other apple products
* Has a search filter
* Data can be backed up on [iCloud](https://support.apple.com/kb/ph2608?locale=en_US)

Cons:

* Only 1 dimension of lists (cannot create lists within lists)
* Can only filter by dates (cannot filter by categories, due dates, etc)
* Very limited application, low customizability
* Cannot integrate with other technologies
* Only available for iPhone users
* Only for a single user (cannot collaborate)

**To Do Reminder**

Author: App Innovation

Pros:

* Easy and quick to set reminders.
* Customise your reminder in your own way with repeat options minute, hour, daily, weekly, monthly, weekdays, yearly.
* Can set in-advance alerts for Reminders.
* Can choose reminder alert as Notification or Alarm.
* It will remind you with alarm notification with your favourite sound.
* With Speech-to-Text, no need to type to create an Reminder.
* Can smartly handle your reminder notification in case of Driving Car etc for your safe drive.
* Synchronise birthdays and anniversaries of your friends from Facebook, Phonebook, Google Calendar, or add them manually.
* Post birthday wishes with lovely cards on your friends Facebook wall or send them Wishes by Gmail, SMS, WhatsApp.
* With Backup & Restore, you can save all your reminders to SDCard, as mail attachments or upload to Drive.
* You can see all reminder notes on home screen using app widget.
* Can choose Day or Night theme for good visibility.

Cons:

* When a reminder pops up, each time the reminder must be clicked, which then opens the app so it can be marked as complete. This is a long process for the user.
* It does not vibrate with the default notification sound settings, even when the default setting of the phone is set to vibrate.
* You cannot set the notification level of a task. For example, a user may want to use sound for more important notifications while use a silent notification for others.

**Just Reminder**

Author: AppHouze Co.

Pros:

* Can set ToDo / Task Reminders, Phone Call Reminders, Birthday Reminders, Anniversary Reminders and Bills Reminders with just few clicks.
* With Speech-to-Text, there is no need to type to create an Reminder.
* Customisable repeat intervals like repeat every few minutes, hours, day, specific week days, weeks, months, years and more.
* Customise each reminder with image, specific ring tone or Talking Alarm tone.
* Can set in-advance alerts for Reminders.
* Can customise Date & Time Formats.
* You will not miss any reminder with LED blink (if device supports) and Auto-Snooze feature.
* Can send wishes from Birthday / Anniversary Reminders.
* Smart Bills Reminders will remind you every day till they were 'PAID'.
* With Backup & Restore, you can save all your reminders to SDCard, as mail attachments or upload to Drive.

Cons:

* It has skipping several reminders near end of the year.
* It does not alert user 5 days in advance of coming bills
* It doesn't have a pass button when user want to keep something still on your notes but do it at sometime user is not sure in the coming hours or days.
* Alarms, for some strange reason, repeat themselves.
* It should have some options more, e.g. make a copy of the existing reminder.


**Remind Me - Task Reminder App**

Author: Lucidify Labs

Pros:

* Voice recognition to add reminder.
* Add a reminder easily and quickly.
* Select repeat type for the reminder ; Hourly, daily, weekly.
* Completely customizable snooze time and alarm duration.
* Do not disturb in silent mode.
* Automatically store the completed reminders.
* Disable / enable reminders.
* Search the reminders.
* Auto stop vibration and alarm tone.
* Turn vibration on and off.
* Simple and easy user interface.
* Customizable settings.
* Lightweight, eats less battery.

Cons:
  		  
* Alarm would stop ringing all together.
* If a reminder comes due and your away from your phone it stays on screen few minutes then its gone
* It alerts user 10 minutes prior. Then you snooze for 15 minutes.
* Needs alarm to be ringing for long time at least 2 minutes.
* Can't repeat reminders daily.

Author: ...

Pros:

* ...
* ...

Cons:

* ...
* ...

