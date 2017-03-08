# Typed - Developer Guide

By : `Typedwriters`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `March 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

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

### Our User Stories

### Add Functions
Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | add a new task with a specified deadline | track when it is due <br>
`* * *` | user | add a new task without specifying deadline | do it sometime later <br>
`* * *` | user | add a new event | reserve that time <br>
`* * *` | user | add a tentative event | hold my schedule until confirmation <br>
`* * *` | user | add recurring tasks | add all at once <br>
`* * *` | user | add tasks using everyday phrases (e.g. today, next monday) | save time looking the exact dates up on a calendar<br>
`* *` | user | add subtasks to a bigger task | organise my tasks into smaller parts <br>
`* *` | user | add comments to a task | refer back to details about the task <br>
`* *` | user | add tasks with multiple tags | track overlapping tasks <br>
`* *` | user | add attachments to a task | complete my task with greater ease <br>
`* *` | user | be alerted when adding clashing events | know I am unavailable <br>


### Find Functions
Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | find tasks with upcoming deadlines | prioritise these tasks <br>
`* * *` | user | find my upcoming events | be aware of my upcoming schedule <br>
`* * *` | user | find a task by name | easily view more about it <br>
`* * *` | user | find tasks by tag | retrieve tasks i have categorised <br>
`* * *` | user | search for a keyword | view all associated tasks <br>
`* * *` | user | do power searching | find tasks whose exact names I have forgotten <br>

### Sort Functions
Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* *` | user | task type filter | view tasks by their types <br>
`* *` | user | sort the tasks based on tags | view tasks by tags <br>
`* *` | user | sort tasks by name | locate a task easily <br>
`* *` | user | sort tasks by deadline | do what is more urgent first <br>
`* *` | user | sort tasks by priority | attend to the more important tasks first <br>

### Edit Functions
Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | mark a task as done | remove it from my to-dos  <br>
`* * *` | user | delete a task | remove it completely from storage <br>
`* * *` | user | edit a task | modify and update its details  <br>
`* * *` | user | undo my last move | correct my mistake <br>
`* * *` | user | redo my last move | redo an undo action <br>
`* *` | user | undo a series of moves | correct multiple consecutive mistakes <br>
`* *` | user | redo a series of moves | redo multiple consecutive undo actions <br>
`* *` | user | mark a whole group of tasks as done | save time and effort <br>
`* *` | user | archive a done task | track what I have completed <br>

### View Functions
Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | see my overdue tasks | finish them right away <br>
`* * *` | user | view my tasks by day | know what my tasks and schedule are for a certain day <br>
`* *` | user | weekly overview of what is due this week | plan my time in advance <br>
`* *` | user | big picture overview of my daily performance | learn the patterns of my own productivity  <br>
`* *` | user | see all upcoming deadlines in a calendar view | have an overview of all my tasks <br>
`* *` | user | see a countdown timer | know how much time is left to do each task <br>
`* *` | user | view a confirmation that my command has been processed | be assured  <br>

### Help Functions
Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | view information about a particular command | learn to use it <br>
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the App <br>
`* *` | user | have the option of having a tutorial | be taught to use the task manager <br>
`* *` | user | see command formats as I type | refer to it if I am unsure of the format <br>

### Advanced Functions
Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | advanced user | enter commands with the use of flags | specify the perimeters of the command more clearly <br>
`* * *` | advanced user | use shorter versions of commands | type each command faster <br>
`* *` | advanced user | create new shortcuts | speed up my workflow <br>

### Integration Functions
Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* *` | user | have integration with text notifications | receive text alerts on important tasks that are due when I am away from my task manager <br>
`* *` | user | access my email alongside the task manager | do not need to switch screens in order to add tasks <br>
`* *` | user | my task manager to link to the necessary resources such as email or IVLE exact pages | I do not have to open a browser to do such things <br>
`* *` | user | open the respective application to complete a particular task | save extra steps <br>
`* *` | user | have my list of to-dos sync across all my devices | work without the computer <br>
`* *` | user | port my whole schedule over to online platforms such as Dropbox | can access it on any computer <br>
`* *` | user | import calendars' schedule into the task manager | I can look at my calendar <br>

### Design/UIUX Functions
Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | have a few colour themes I can choose between | select colours I am more comfortable with <br>
`* * *` | user | a simple user interface | be less distracted and more productive <br>
`* *` | user | change font, font size, colour of words | personalise my task manager to my preference <br>
`*` | user | see affirmations of my effort upon task completions | look forward to accomplishing more tasks <br>
`*` | user | be greeted with motivational lines when I launch the task manager | feel motivated to start doing things <br>
`*` | user | greeted with a compliment when I launch the task manager | be in a good mood to start doing things <br>
`*` | user | receive encouragement | feel good about myself and want to use the task manager more <br>
`*` | user | some sort of punishment when I fail to complete a task | find motivation to do my tasks <br>

### Unique Functions
Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | commands to be highlighted | know they are valid commands <br>
`* *` | user | have a daily progress check-in function | know I have worked on a task today even if I cannot complete it <br>
`* *` | user | have some sort of points or incentive system | accomplish more and be more motivated to be productive <br>
`* *` | user | have a priority system on my tasks | finish those of higher priority first <br>
`* *` | user | have spellcheck as I type commands | correct errors immediately <br>
`* *` | user | a reminder function | get notified when deadlines or events are nearer <br>

### Basic Functions
Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | be able to do everything through typing | it is convenient for me <br>
`* * *` | user | have each command processed within a second | be more efficient and productive in managing my tasks <br>
`* *` | user | enable backup | have a backup copy in case the storage fails or crashes <br>


## Appendix B : Use Cases

(For all use cases below, the **System** is the `Typed` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: View history of actions ####

**MSS**

User keys in a series of command(s)
User requests to view history of actions
Typed displays the list of past actions
Use case ends.

**Extensions**

1a. Command is typed in wrongly
	| Systems indicate error and output a list of valid commands
2a. List is empty
	| System indicate History Command only
2b. List size is way too large
	| System shows the recent 10 commands

#### Use case: Perform simple keyword query ####

**MSS**

User enters a keyword to find any matching to do in Typed.
Typed displays all the todos with matching keywords.

**Extensions** 
1a. Typed is empty
	| User search his todos using keywords
	| Typed displays that there are no todo with the any matching keywords.
	| Use case ends

#### Use case: Undo previous action ####

**MSS**

User keys in a series of command(s) changing information stored in Typed.
User requests to undo the last action done.
Typed undoes the last action done.
Typed displays text informing user that the last action has been successfully undone.
Use case ends.

**Extensions**

1a. User does not key in any command at all
	| User requests to undo the last action done
	| Typed displays text informing user that there are no actions to be undone
	| Use case ends

1b. User keys in a series of command(s) that do not change the information stored in Typed
	| User requests to undo the last action done
	| Typed displays text informing user that there are no actions to be undone
	| Use case ends

2a. User restarts the session
	| User requests to undo the last action done
	| Typed displays text informing user that there are no actions to be undone
	| Use case ends

#### Use case: Redo previous undone action ####

**MSS**

User keys in a series of command(s) changing information stored in Typed.
User requests to undo the last action done.
User requests to redo the previously undone action.
Typed redoes the last action undone by Typed. 
Typed displays text informing user that the last undone action has been successfully redone.
Use case ends.

**Extensions**

1a. User does not key in any command at all
	| User requests to undo the last action done
	| Typed displays text informing user that there are no actions to be undone
	| Use case ends

1b. User keys in a series of command(s) that do not change the information stored in Typed
	| User requests to undo the last action done
	| Typed displays text informing user that there are no actions to be undone
	| Use case ends

2a. User restarts the session
	| User requests to undo the last action done
	| Typed displays text informing user that there are no actions to be undone
	| Use case ends

3a. User keys in a command that changes the information stored in Typed
	| User requests to redo the last action done
	| Typed displays text informing user that there are no undone actions to be redone
	| Use case ends

3b. User keys in a series of command(s) that do not change the information stored in Typed
	| Use case continues from step 3

3c. User restarts the session
	| User requests to redo the last action done
	| Typed displays text informing user that there are no undone actions to be redone
	| Use case ends


## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 tasks without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands)
   should be able to accomplish most of the tasks faster using commands than using the mouse.

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

## Appendix E : Product Survey

### **Eclipse**

Author: Lee Pei Xuan

Pros:
* Organise tasks into projects
* Organise projects by classifying them into groups
* Color coding according to groups
* Get overall status of each project at one glance using progress bars that inform you how many percent of the project is completed
* View all tasks by deadlines to prioritise your time better
* Stamp tasks that you have worked on but not completed each day

Cons:
* Typed navigation is not available 
* No offline functionality 
* Only available on Windows 10 computers

### **Reminder**

Author: Low Yong Siang

Pros:
* Multi-device syncing allows for the user to be able to continue on different devices.
* Able to mark off the list of todos and it is transferred to the Completed section.
* Able to set a reminder notification on a date or given a location.
* Offline functionality, and will sync across devices when internet connection is available.
* Able to add priority to each task (3 levels in total).
* Clean and intuitive user interface.
* Able to add comments and notes to a to do which helps to organise thoughts on the specific task. 
* Able to color code todo, for organization.

Cons:
* Multi-device syncing only happens when internet connection is available.
* Does not sort the list in the priority level. Only shows the priority symbol at the side of the task.
* Only available on Apple devices.
* Typed navigation to the completed and pending list is not available.

### **Evernote**

Author: Mun Le Yuan

Pros:
* Can be accessed on both laptops and smartphones
* Notes can be tagged with text and location
* Allows great extent of organisation by giving users the ability to group notes into notebooks and even group notebooks
* Users can add images and sound clips to notes
* Users can attach documents to notes
* Users can share notes with others (not really relevant)
* Users can search through notes using key words
* Allows notes or notebooks to be added to shortcuts for easy access
* Users can set reminders for notes
* Data is stored in the cloud
* Shows search history
* Users can clip webpages to the notes (only on laptops)
* Allows users to forward emails to Evernote

Cons:
* Involves quite a bit of GUI (not very suitable for Jim)
* Does not allow users to prioritise tasks in notes

### **Any.do**

Author: Yim Chia Hui 

Pros:
* Minimalist design reduces the clutter on the screen
* Provides Any.do Moment which runs through the things to do for the day/review what has been done already
* Organise into categories
* View based on priorities
* GUI is simple and easy to learn

Cons:
* Not much customizable possible
* No calendar view

