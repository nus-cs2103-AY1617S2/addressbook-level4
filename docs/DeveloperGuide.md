# Watodo - Developer Guide

By : `CS2103JAN2017-T16-B3`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jan 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Setting Up](#1-setting-up)
2. [Design](#2-design)
3. [Implementation](#3-implementation)
4. [Testing](#4-testing)
5. [Dev Ops](#5-dev-ops)

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
3. Enter an arbitrary configuration name e.g. tasklist
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

`Main` has only one class called [`MainApp`](../src/main/java/seedu/watodo/MainApp.java). It is responsible for,

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

>Note how the `Model` simply raises a `TaskListChangedEvent` when the Task List data are changed,
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

Author: Mervyn Yee

<img src="images/UiClassDiagram.png" width="800"><br>
_Figure 2.2.1 : Structure of the UI Component_

**API** : [`Ui.java`](../src/main/java/seedu/watodo/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `TaskListPanel`,
`StatusBarFooter`, `BrowserPanel` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/watodo/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component,

* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raised from various parts of the App and updates the UI accordingly.

### 2.3. Logic component

Author: Ching Hui Qi

<img src="images/LogicClassDiagram.png" width="800"><br>
_Figure 2.3.1 : Structure of the Logic Component_

**API** : [`Logic.java`](../src/main/java/seedu/watodo/logic/Logic.java)

1. `Logic` uses the `Parser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a task) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
 API call.<br>
<img src="images/DeletePersonSdForLogic.png" width="800"><br>
_Figure 2.3.1 : Interactions Inside the Logic Component for the `delete 1` Command_

### 2.4. Model component

Author: Andrew Sugiarto

<img src="images/ModelClassDiagram.png" width="800"><br>
_Figure 2.4.1 : Structure of the Model Component_

**API** : [`Model.java`](../src/main/java/seedu/watodo/model/Model.java)

The `Model`,

* stores a `UserPref` object that represents the user's preferences.
* stores the Task List data.
* exposes a `UnmodifiableObservableList<ReadOnlyTask>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

### 2.5. Storage component

Author: Megan Quek

<img src="images/StorageClassDiagram.png" width="800"><br>
_Figure 2.5.1 : Structure of the Storage Component_

**API** : [`Storage.java`](../src/main/java/seedu/watodo/storage/Storage.java)

The `Storage` component,

* can save `UserPref` objects in json format and read it back.
* can save the Task List data in xml format and read it back.

### 2.6. Common classes

Classes used by multiple components are in the `seedu.watodo.commons` package.

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
      e.g. `seedu.watodo.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units
     (those code units are assumed to be working).<br>
      e.g. `seedu.watodo.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as
      how the are connected together.<br>
      e.g. `seedu.watodo.logic.LogicManagerTest`

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
    e.g. For [UserGuide.md](UserGuide.md), the URL will be `https://<your-username-or-organization-name>.github.io/main/docs/UserGuide.html`.
 1. Click on the `Print` option in Chrome's menu.
 1. Set the destination to `Save as PDF`, then click `Save` to save a copy of the file in PDF format. <br>
    For best results, use the settings indicated in the screenshot below. <br>
    <img src="images/chrome_save_as_pdf.png" width="300"><br>
    _Figure 5.4.1 : Saving documentation as PDF files in Chrome_

### 5.6. Managing Dependencies

A project often depends on third-party libraries. For example, Task List depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

## Appendix A : User Stories

Priorities: Highest (must have) - `* * * * *` to Lowest (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * * * *` | user | add a task by specifying task description only | record tasks that need to be done some day
`* * * * *` | user | add a task with a specific deadline | know when the task is due
`* * * * *` | user | add an event with a start and end time | record tasks that are events
`* * * * *` | user | edit the deadline of a specific task | keep my task manager up to date with any changes
`* * * * *` | user | edit the task descriptions | make corrections or updates to the task description
`* * * * *` | user | delete a task | get rid of tasks that I no longer care to track
`* * * * *` | user | mark a task as done | remove the task from my to-do list
`* * * * *` | user | unmark tasks previously marked as done | edit the task status
`* * * *` | user | list tasks that are due within the day, week or month | have an overview of my schedule and decide what needs to be done soon
`* * * *` | user with many tasks | search keyword(s) | find all the tasks that are similar or relevant to the keyword
`* * * *` | user | undo my most recent action | reverse any mistake made in the previous step
`* * * *` | user | redo my most recent action | revert back to the previous state before an undo
`* * * *` | advanced user | use shorter versions of a command | type a command faster
`* * * *` | advanced user | specify which folder I want to save the files in | have easy access to the tasks just by sharing the files
`* * * *` | new user | see usage instructions | refer to instructions when I forget how to use the programme
`* * * *` | new user | view more information about a particular command | so that I can learn how to use various commands
`* * *` | user | set tasks to repeat over a specified interval | manage recurring tasks
`* * *` | user | add additional details or subtasks to a task | record tasks in detail
`* * *` | user | assign tags to the tasks | organise them properly
`* * *` | user | indicate the priority of a task | see which tasks are more urgent or important
`* * *` | user | list the deadline tasks by date | know which are the most urgent.
`* * *` | user | list tasks by tags | see what are the tasks under a specific category.
`* * *` | user | list tasks by priority | know which are the most urgent tasks.
`* * *` | user | view the list of tasks that I have completed | unmark completed tasks if necessary.
`* * *` | advanced user | add default keywords to my interface | customize it according to the vocabulary that I am most comfortable with.
`* *` | user | colour code my tasks | I can differentiate tasks better
`* *` | user | add icons to my tasks | quickly tell what kind of tasks I have
`* *` | user | reorder my tasks | reorder floating tasks.
`* *` | user | organise my tasks into different sections | view only the tasks that are relevant to the situation
`* *` | user | pin tasks to the top of the list | see them straight away when I open the application
`* *` | advanced user | view a log of my history | track all the commands I have entered since the start of time.
`* *` | advanced user | change the layout of my UI (eg. background colour, font size) | customize it according to my preference.
`*` | user | export the tasks to a calendar file | use with other apps.
`*` | user | add location details to events | I know where the event is taking place
`*` | user | search better with auto complete | search better.
`*` | user | enable auto spell checker | correct any spelling mistakes I might make when typing commands.
`*` | user | be notified if the time period an event I am adding clashes or overlaps with another event already added | reschedule the event to another free time slot if needed
`*` | user | search for empty time periods | schedule my tasks with minimal overlap or clashes in deadlines.
`*` | user | receive flavour text when I mark a task as complete such as ¡°Good job!¡± And ¡°Another one off the list!¡±  | give myself more motivation to complete my tasks.
`*` | user | receive sound effects when I mark a task as completed | give myself more motivation to complete my tasks.

## Appendix B : Use Cases

(For all use cases below, the **System** is the `TaskList` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Add task

**MSS**

1. User inputs task details
2. TaskList shows updated task list <br>
Use case ends.

**Extensions**

1a. User adds time period for event which overlaps with time period of existing event

> 1a1. TaskList shows an error message and prompts user to edit time period of event
> 1a2. User inputs task details <br>
Steps 1a1-1a2 are repeated until the time period of event entered is valid
  Use case resumes at step 2

#### Use case: Delete task

**MSS**

1. User selects task to delete
2. TaskList asks for user confirmation
3. User confirms deletion
4. TaskList deletes task and shows updated task list <br>
Use case ends.

**Extensions**

3a. User cancels deletion
> Use case ends

#### Use case: Mark task

**MSS**

1. User marks a task
2. TaskList moves task from task list to list of completed tasks and shows updated task list <br>
Use case ends.

#### Use case: Unmark task

**MSS**

1. User unmarks a task
2. TaskList moves task from list of completed tasks back to current task list and shows updated task list <br>
Use case ends.

#### Use case: Edit task details (includes deadline)

**MSS**

1. User selects task to edit
2. TaskList shows task details
3. User edits task details
4. TaskList shows updated task details <br>
Use case ends.

**Extensions**

3a. New time period of event added overlaps with time period of existing event

> 3a1. TaskList shows an error message and prompts user to edit time period of event
> 3a2. User inputs task details <br>
Steps 3a1-3a2 are repeated until the time period of event entered is valid
  Use case resumes at step 4

#### Use case: View by deadlines

**MSS**

1. User selects time filter to filter the deadlines of the task list by
2. TaskList displays tasks that are due within the timeline specified by the filter <br>
  Use case ends.

#### Use case: Change UI layout

**MSS**

1. User requests to change UI layout
2. TaskList shows UI layout features that can be customised
3. User edits UI layout features
4. TaskList shows a preview and asks for confirmation
5. User confirms changes to UI layout
6. TaskList returns to user’s previous screen <br>
Use case ends.

#### Use case: Add default keywords

**MSS**

1. User requests to add default keywords
2. TaskList displays a list of TaskList functions and their keywords
3. User selects function to add a keyword to
4. TaskList asks for keyword
5. User inputs keyword
6. TaskList shows updated list of TaskList functions and their keywords <br>
Use case ends.

**Extensions**

5a. Keyword already in use

> 5a1. TaskList shows an error message and asks users for keyword again
> 5a2. User inputs keyword
Steps 5a1-5a2 are repeated until the keyword entered is valid
  Use case resumes at step 6

## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be portable and not require any installer to work 
3. Should be desktop app that does not require any Internet connection to use
4. Should not require a login username and password but can be launched conveniently
5. Should be able to hold up to 1000 tasks without a noticeable sluggishness in performance for typical usage.
6. Should be able to reliably store the list of tasks data and not have cases of corrupted data files
7. The data should be stored locally in the form of a human editable text file, to allow advanced users to manipulate the data by editing the data file.
8. Should respond to user commands relatively quickly (not more than 10 seconds).
9. A user with above average typing speed for regular English text (i.e. not code, not system admin commands)
   should be able to accomplish most of the tasks faster using commands than using the mouse.
10. Should be intuitive and easy to use even for users without any technical background.
11. Should have proper documentation of user guide and glossary of terms.
12. Should follow the OO paradigm and have well documented developer guide so other developers can contribute and enhance the product easily
13. Should have some degree of automated testing

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Floating task

> A task that has no start date and end date

##### Deadline task

> A task that has no start date but has an end date

##### Event task

> A task that has both a start date and end date

##### DateTime

> Refers to a day/date and/or time <br>
Eg. day/date only:
>tues, wed, 4 may, 7/12(MM/DD)
time only:
>9.50am, 1330h, noon
day/date and time:
>mon 4.30pm






## Appendix E : Product Survey

**Todoist**

Pros:

* Supports many variations of keywords for time and dates (eg. next week, 3 days, everyday etc.)
* Nice interface that highlights the date arguments, tags and priorities as they can entered
* Shortcut keys such as for adding and finding allow use of mouse clicks to be minimized
* Auto-complete when adding tags helps to increase efficiency of usage
* Can add a variety of tags (category, priority) to tasks to organize them better
* Powerful search feature that can find tasks given minimal keywords, and also find tasks by their deadlines
* Good visual feedback through colour coding for tasks with different priorities and categories
* Karma trend and goals enable user to track productivity and stay motivated to complete tasks on time

Cons:

* Certain features (eg. sync with calendar, customize theme) are only available with paid subscription
* Most other functions are not command line interface friendly (Eg. edit, mark as complete require mouse clicks)
* No help feature to guide new users to keywords like date and time. Need to refer to the user guide.

**HiTask**

Pros:

* Good GUI especially the calendar that automatically highlights dates with tasks due/ events scheduled, which makes it easy for user to find an empty time slot to schedule a new task if needed.
* Nice and clear GUI display when listing tasks for the day, week or month which come with a time-line so that task cards height are proportional to the task duration.

Cons:

* Software requires Internet connection and not available as a desktop application
* Requires an account to use and is only free for a limited period
* Not very command line interface friendly

**Any.do**

Pros:

* Able to be synced between mobile and computer as tasks can be managed either through going to the website on a browser and/or downloading and using the mobile application.
* Clean and simple UI design for anti clutter.
* Lists can be sorted by manually entered types followed by urgency (Today, tomorrow, upcoming, someday)
* Premium only: Able to change the colour theme.
* Premium only: Able to manage as a group
* Premium only: Able to attach notes and files to tasks
* Premium only: Able to assign tasks to group members
* Luckily, premium is quite affordable at $1.49/month
* Able to add notes and subtasks

Cons:

* Quite a number of features require premium account.
* Requires Internet connection to sync tasks.
* Limited options to customise UI.

**Remember the Milk**

Pros:
* Able to filter tasks according to their deadline.
* Able to most of the commands with mouse clicks.
* Able to sync with other apps such as iCalendar and Feed.
* Simple UI that is easy on the eyes.
* Most of the essential features are not locked behind the paywall.
Cons:

* Requires internet connection to use.

**Wunderlist**

Pros:

* Able to repeat tasks with customisable frequency (days, weeks, months, years)
* Able to add subtasks, notes and files
* Can star a task with the option of making it automatically go to the top of the list
* List can be subsumed under folders
* Can enable sound effects for notifications or upon completion of a task
* User is able to make a Feature Request to Wunderlist
* Able to snooze reminders for a customisable time
* Able to have email, push and desktop notifications
* Able to sync due dates with any calendar that supports the iCalendar format
* Has both a mobile and desktop application that can work offline
* Able to duplicate list
* Able to customise shortcuts
* Able to specify preferred formats for the data, time and start of the week
* Able to restore deleted lists
* Able to print list

Cons:
* Priority is based on due dates only, tasks cannot be sorted into broad priority categories
* Lacks colour coding of tasks

