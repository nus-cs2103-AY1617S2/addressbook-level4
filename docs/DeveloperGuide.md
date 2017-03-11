# Tâche - Developer Guide

By : `T09-B4`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Setting Up](#1-setting-up)
2. [Design](#2-design)
3. [Implementation](#3-implementation)
4. [Testing](#4-testing)
5. [Dev Ops](#5-dev-ops)

* [Appendix A: User Stories](#appendix-a-user-stories)
* [Appendix B: Use Cases](#appendix-b-use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c-non-functional-requirements)
* [Appendix D: Glossary](#appendix-d-glossary)
* [Appendix E : Product Survey](#appendix-e-product-survey)


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
3. Enter an arbitrary configuration name e.g. tache
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

(Work in progress)

## 3. Implementation

### 3.1. Logging

We are using `java.util.logging` package for logging. The `LogsCenter` class is used to manage the logging levels
and logging destinations.

* The logging level can be controlled using the `logLevel` setting in the configuration file
  (See [Configuration](#3-2-configuration))
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
      e.g. `seedu.tache.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units
     (those code units are assumed to be working).<br>
      e.g. `seedu.tache.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as
      how the are connected together.<br>
      e.g. `seedu.tache.logic.LogicManagerTest`

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

A project often depends on third-party libraries. For example, Tâche depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | obtain a list of tasks due today / this week | plan my time to complete these urgent tasks before they are due
`* * *` | user | view tasks planned to be completed within a certain time range | easily decide on tasks I should work on during that period
`* * *` | user | indicate a starting and ending time for my tasks | keep track of events I need to attend
`* * *` | user | display my tasks in a calendar view | obtain a general overview of the tasks I need to complete
`* * *` | user  | set tasks as recurring at certain intervals | save time having to add the task repeatedly for different times
`* * *` | user | add subtasks to existing tasks | keep track of and manage subtasks to be completed as part of bigger tasks
`* * *` | user | mark a task as completed and filter tasks that are not completed | keep tracks of tasks that I have to complete
`* * *` | user | view completed tasks | avoid working on tasks that are already completed
`* * *` | user | set notifications to go off when certain tasks are nearing their deadline | avoid missing those deadlines
`* * *` | user | set notifications to go off when I have planned to work on certain tasks | remind myself to work on those tasks that need to be completed
`* * *` | user | undo previous commands | revert back to previous states in case of wrongly entered commands
`* * *` | user  | activate the application quickly using a keyboard shortcut | access my task manager conveniently and efficiently
`* * *` | advanced user | autocomplete my command by pressing a key (e.g. enter) | type my commands faster
`* * *` | user | specify a particular location I want to save the data file of my task manager | sync my task list and access it from other devices
`* * *` | user | search for names of particular tasks | update or view specific tasks efficiently
`* * *` | user | add new tasks | track what i need to do
`* * *` | user | delete tasks | delete task that was inaccurately entered
`* *` | advanced user | use shorter versions of commands | type my commands faster
`* *` | new user | view additional usage information for particular commands | learn how to use specific commands effectively
`* *` | user | secure my task list | prevent other people from viewing my task list
`* *` | user | sync my task list to Google or Microsoft | view my tasks through their respective calendars
`* *` | user | retrieve previously typed commands using the UP and DOWN keys and execute them directly | save time having to retype similar commands repeatedly
`* *` | user | postpone an existing task via a specific command | save time having to specifically enter the new deadline
`* *` | user | attach related documents and links to my tasks | conveniently access documents needed for me to work on the tasks
`* *` | new user | view command hints when typing commands | ensure my commands are correct
`* *` | user | block off multiple slots for the same task and release the unused slots when the exact timing of a task is confirmed | avoid having to add multiple copies of the same task to multiple potential time slots
`* *` | user | view the slots at which I have not planned for any task to be completed | find a suitable slot for new tasks easily
`*` | user sharing this computer with other users | switch between different accounts on this task manager | share this application with the other users of this computer
`*` | user | backup my entire task list to the cloud or external storage via export | have extra redundancy against system failures
`*` | user | create shared tasks across different users | track the progress of other users for shared tasks
`*` | user | import previously exported data back | continue from where I left off
`*` | user | receive a daily email at a preferred time that contains tasks due that day | plan my schedule for that day effectively


{More to be added}

## Appendix B : Use Cases

(For all use cases below, the **System** is the `TaskManager` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Show possible commands to be executed

**MSS**

1. User types a letter or a sequence of letters
2. System shows a list of commands that contain that letter or sequence of letters
3. User selects specific command in list
4. System autocompletes user's command
Use case ends. 

**Extensions**

2a. No command contains letter or sequence of letters typed by user

> 2a1. System informs user that there is no such command
  Use case ends

2b. User makes changes to input

> Use case resumes at step 2

#### Use case: Change save location

**MSS**

1. User requests to change data file location
2. System displays a directory chooser
3. User selects a directory
4. System changes the save location to the one selected
Use case ends

**Extensions**

1a. User pressed "Alt + S" keys

> Use case resumes at step 2

3a. User clicks on "Cancel"

> Use case ends

#### Use case: Switch to console with hotkeys

**MSS**

1. User types in predefined keyboard shortcut
2. System displays main window
3. User types in keyboard shortcut again
4. System minimizes main window
Use case ends

#### Use case: Delete task

**MSS**

1. User requests to list tasks
2. System shows a list of tasks
3. User requests to delete a specific task in the list
4. System deletes the task
Use case ends.


**Extensions**

2a. List is empty

> Use case ends

3a. The given index is invalid

> 3a1. System informs the user that the given index is invalid<br>
Use case resumes at step 2

#### Use case: Add task

**MSS**

1. User enters add command 
2. System displays confirmation of add operation
Use case ends.

**Extensions**

1a. Parameters are wrong

> 1a1. System informs user that the parameters for the add command are wrong<br>
Use case ends

#### Use case: Undo task

**MSS**

1. User enters undo command
2. System reverts back to previous state before last change
Use case ends.

**Extensions**

2a. No change to undo

> 2a1. System shows nothing to undo<br>
Use case ends

2a. Error undoing change

> 2a1. System notifies user of failure to undo<br>
Use case ends


{More to be added}

## Appendix C : Non Functional Requirements

1. Should be able to hold up to 2 years worth of tasks.
2. Should process and respond to user commands within 1s. 
3. Should conform to the Java coding standard.
4. Should automatically back up data once a week. 
5. Should detect any intrusion within 5 seconds.
6. Should work on any mainstream OS as long as it has Java 1.8.0_60 or higher installed.
7. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.


{More to be added}

## Appendix D : Glossary

#####Parameter: 

> detail associated with a task (e.g. duration, name, start time)

#####Recurring task:

> a task that has to be completed periodically (e.g. daily or monthly)

#####Subtask:

> a component of a task

#####Autocomplete:

> complete words or strings without the user needing to type them in full

#####Sync:

> ensure that data files in two or more locations are updated

#####Hotkey:

> a single or combination of keys that is registered with the system to perform specific activity when pressed

#####Mainstream OS:

> Windows, Linux, Mac

## Appendix E : Product Survey

**Wunderlist (free version)**

Author: Tan Yu Wei

Pros:

* Customizable to-do lists for different purposes (e.g. movies to watch, grocery list)
* Alarm reminders for task when deadline is up AND when user should be working on the task
* Save links and web pages easily for later by via an Add to Wunderlist browser extension
* Compatible and syncable across many operating systems and commonly-used devices
* Transform emails into to-do tasks by simply forwarding them to a specific address
* Print task lists neatly and easily


Cons:

* Files added to to-do list have a limited size (i.e. 5 MB)
* Limited number of subtasks per task (i.e. 25)
* Limited number of backgrounds to personalize Wunderlist (i.e. 20)
* No support for nested to-do lists
* Cannot manage events (cannot add start and end time)
* No calendar view of tasks

**Google Calendar**

Author: Lim Shun Xian

Pros:

* Cross platform
* Integrated with majority of Google's app such as Gmail
* Able to compare friends' calendar with yours to find common free slots
* Able to share calendar to others to view
* Able to send a daily reminder email of today's agenda (due tasks) at 5AM 

Cons:

* Difficult to migrate over should one be already using Outlook or other calendars 
* The daily reminder email is fixed at 5AM

**Wunderlist (free version)**

Author: Brandon Tan Jian Sin

Pros:

* Simple layout
* Intuitive controls
* Free
* Syncable across different platforms that wunderlist supports
* Allows custom categorization of tasks
* Email, Desktop, Push Notifications

Cons:

* No calendar view
* Limitations in number of subtasks and file attachments
* Tasks only scheduled by day not time

**Get it Done**
Author: Ang Zhi Yuan

Pros:

* Simple, clear layout
* Widely accessible across multiple platforms
* Allows cooperation between multiple users
* Clean email notifications
* Can sync to other calendars

Cons:

* Subscription based
* No calendar-view, only list view
