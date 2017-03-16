# ToLuist - Developer Guide

By : `Team ToLuist`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jan 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

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


## 1. Introduction

Welcome to ToLuist's Developer Guide.

By going through this document, you will learn how to set up the project, understand the architecture of the 
application as well as know how to troubleshoot some common development issues.

We have organized the guide in a top-down so that, as a new developer, you can look at the big picture of the project
 before zooming in on specific components.

## 1. Setting up

### 1.1. Prerequisites

1. **JDK `1.8.0_60`**  or later<br>

    > Having any Java 8 version is not enough. This app will not work with earlier versions of Java 8.

2. **Eclipse** IDE
3. **e(fx)clipse** plugin for Eclipse (Do step 2 onwards given in
   [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious))
4. **Buildship Gradle Integration** plugin from the Eclipse Marketplace
5. **Checkstyle Plug-in** plugin from the Eclipse Marketplace


### 1.2. Importing the project into Eclipse

1. Fork this repo, and clone the fork to your computer
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

### 1.3. Configuring Checkstyle
1. Click `Project` -> `Properties` -> `Checkstyle` -> `Local Check Configurations` -> `New...`
2. Choose `External Configuration File` under `Type`
3. Enter an arbitrary configuration name e.g. toluist
4. Import checkstyle configuration file found at `config/checkstyle/checkstyle.xml`
5. Click OK once, go to the `Main` tab, use the newly imported check configuration.
6. Tick and select `files from packages`, click `Change...`, and select the `resources` package
7. Click OK twice. Rebuild project if prompted

> You should click on the `files from packages` text after ticking in order to enable the `Change...` button

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
_Figure 2.1 : Architecture Diagram_

The **_Architecture Diagram_** given above explains the high-level design of ToLuist.
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
* `LogsCenter` : This class is used by many classes to write log messages to the App's log file.

The rest of the App consists of five components:

* [**`UI`**](#ui-component) renders the GUI of the app.
* [**`Dispatcher`**](#dispatcher-component) invokes a suitable command executor.
* [**`Controller`**](#logic-component) executes the command.
* [**`Model`**](#model-component) holds the data of the application in the memory.
* [**`Storage`**](#storage-component) reads data from, and writes data to, the hard disk.

Each of the five components defines its _API_ in an `interface` with the same name as the Component.

Our architecture follows the MVC Pattern. UI displays data and interacts with the user. Commands are passed through 
the Dispatcher and routed to a suitable Controller. Controller receives requests from the Dispatcher and acts as the 
bridge between the UI and the Model. Model & Storage store and maintain the data. A lot of inspirations for this design
 was drawn from MVC architectures used by web MVC frameworks such as [Ruby on Rails](http://paulhan221.tumblr
 .com/post/114731592051/rails-http-requests-for-mvc) and [Laravel](http://laravelbook.com/laravel-architecture/).

The sections below give more details of each component.

### 2.2. UI component

<img src="images/UiClassDiagram.png" width="600"><br>
_Figure 2.2 : Structure of the UI Component_

**API** : [`Ui.java`](../src/main/java/seedu/toluist/ui/Ui.java)

**JavaFX** is used for the UI. `MainWindow` holds all the views that make up the different parts of the UI. These views inherit from the abstract `UiView` class, while `MainWindow` itself inherits from the abstract `UiPart` class.

#### 2.2.1. UiView

**API** : [`UiView.java`](../src/main/java/seedu/toluist/ui/view/UiView.java)

`UiView` is the building block for the UI. Each `UiView` should preferably be responsible for only one UI functionality.

Some of the key properties of a `UiView` are described below.

#### Associated with a FXML
Each UIView class is associated with a FXML file. For example, `TaskView` is associated with `TaskView.fxml` file. The corresponding FXML file will be loaded automatically when a new `UiView` instance is created.

#### Attachable to one single parent node
You can attach a `UiView` to a parent node. At any point of time, a `UiView` should always have a maximum of one parent. The parent node must be an object of `Pane` class or any of its subclasses.

#### Rendered idempotently

After attaching a `UiView` to a parent node, you can render it by invoking its public method `render()`. Each render call is guaranteed to be idempotent, i.e. subsequent calls to `render()` will render the same UI, as long as the data do not change.

#### Able to load subviews

Each `UiView` has a mini lifecycle. `viewDidLoad` is run after `render` is called. There are a few uses of `viewDidLoad`:

- Control UI-specific properties which cannot be done in FXML.
- Set UI component values (e.g. using `setText` on an FXML `Text` object).
- Attach subviews and propagate the chain.

#### 2.2.2. UiStore ####

**API** : [`UiStore.java`](../src/main/java/seedu/toluist/ui/UiStore.java)

`UiStore` holds the data to be used by the `UI`. An example would be the task data to be displayed to the user.

#### 2.2.3. Reactive nature of the UI ####

To keep the UI predictable and to reduce the number of lines of codes used to dictate how the UI should change based on state changes, we make use of reactive programming in our UI. You can declare how the UI should be rendered based solely on the states held by the `UiStore`.

The diagram below shows how the UI reacts when an add command is called. The UI simply needs to display all the tasks available in the `UiStore`, without knowing what was the exact change.

<img src="images/UiSequence.png" width="600"><br>
_Figure 2.1.3 : Interactions Inside the UI for the `add study` Command_

The reactive approach is borrowed from modern Javascript front-end frameworks such as [React.js](https://facebook.github.io/react/) and [Vue.js](https://vuejs.org/v2/guide/reactivity.html).

### 2.2 Dispatcher component

**API** : [`Dispatcher.java`](../src/main/java/seedu/toluist/dispatcher/Dispatcher.java)

`Dispatcher` acts like a router in a traditional Web MVC architecture. On receiving new input from the UI, `Dispatcher` decides which `Controller` is the best candidate to handle the input, then instantiates and asks the `Controller` object to execute the command.

### 2.3. Controller component

<img src="images/ControllerClassDiagram.png" width="600"><br>
_Figure 2.3.1 : Structure of the Controller Component_

**API** : [`Controller.java`](../src/main/java/seedu/toluist/controller/Controller.java)

- `Controller` has a `execute` method to execute the command passed by the dispatcher.
- The command execution can affect the `Model`, the `Storage` (e.g. adding a task) and/or raise events.
- The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the dispatcher.
- After every `execute` invocation, `Controller` can optionally set new states in the `UiStore` and ask the `UI` to re-render.


### 2.4. Model component

**API** : [`TodoList.java`](../src/main/java/seedu/toluist/model/TodoList.java)

The `Model` stores the task data for the app inside the memory.

### 2.5. Storage component

**API** : [`TodoListStorage.java`](../src/main/java/seedu/toluist/storage/TodoListStorage.java)

The `Storage` component
- acts like a database in the application.
- provides read/write funcionalities to the `Model`, encapsuling all the inner implementation details.
- can save the task data in json format and read it back.
- holds the history of all data changes

#### Undoable History

`historyStack` hold the serialized json strings of the task list data. The minimum size of this stack is always 1. The json string at the top of the stack is the serialization of the current todo list data.

To undo the most recent changes, we simply pop the irrelevant strings in `historyStack` and then deserialize the json string at the top of the stack into task list data.

An alternative approach to implementing undoable history is to create a `unexecute` method for each mutating 
command, 
and have a local 
history 
of the data changes in each Controller instance. Compared to this alternative, storing a centralized history of data 
changes in the storage is much more robust, as we can avoid checking every Controller instance to 
get the previous data state. An additional benefit is that the integrity of the data change order is guaranteed in 
the `historyStack`, and we do not need to keep track of what the previous mutating commands were.

### 2.6. Common classes

Classes used by multiple components are in the `seedu.toluist.commons` package.

## 3. Implementation

### 3.1. Logging

We are using `java.util.logging` package for logging. The `LogsCenter` class is used to manage the logging levels
and logging destinations.

* You can control the logging level by using the `logLevel` setting in the configuration file
  (See [Configuration](#configuration)).
* You can obtain the `Logger` for a class by using `LogsCenter.getLogger(Class)` which will log messages according to
  the specified logging level.
* Currently log messages are output through `Console` and to a `.log` file.

**Logging Levels**

* `SEVERE` : Critical problem detected which may possibly cause the termination of the application.
* `WARNING` : Can continue, but with caution.
* `INFO` : Information showing the noteworthy actions by the App.
* `FINE` : Details that is not usually noteworthy but may be useful in debugging.
  e.g. The actual list is printed instead of just its size.

### 3.2. Configuration

You can control certain properties of the application (e.g App name, logging level) through the configuration file
(default: `config.json`).

## 4. Testing

You can find the tests in the `./src/test/java` folder.

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
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the App
`* * *` | user | add a new task | remind myself of things I have to do
`* * *` | user | add a new task with completion date | remind myself of things I have to do and the deadlines to meet
`* * *` | user | add a new [recurring task](#recurring-task) | prevent the need of keying the same cyclical task every period
`* * *` | user | see all tasks | have an overview of all the things I need to do and decide what I should do first
`* * *` | user | update a task | change entries that are errorneous or outdated
`* * *` | user | mark a task as completed/incompleted | focus on the tasks I have still not cleared
`* * *` | user | delete a task | remove entries that I no longer need
`* * *` | user | add a new event | remind myself of things I have to attend
`* * *` | user | see all events | have an overview of all the things I need to events
`* * *` | user | update an event | change entries that are errorneous or outdated
`* * *` | user | delete an event | remove entries that I no longer need
`* * *` | user | find a task by name | locate details of task without having to go through the entire list
`* * *` | user | find a task by tag | locate details of task without having to go through the entire list
`* * *` | user | add tag to task | group my tasks to provide more context
`* * *` | user | update tag in task | change entries that are errorneous or outdated
`* * *` | user | remove tag from task | remove entries that I no longer need
`* * *` | user | undo previous command(s) | recover gracefully from making mistakes
`* * *` | user | redo previously undone command(s) | recover gracefully from  wrongly resolving a mistake
`* * *` | user | see my [command history](#command-history) | to identify what I have to recover if I accidentally performed some wrong commands.
`* * *` | user | change my storage file path | decide where I want to save my files for my own use
`* * *` | user | exit the program | gracefully shut down the program when I don't need to use it
`* *` | experienced user | add an [alias](#alias) for a command | customise my own keyboard shortcuts to improve my efficiency
`* *` | experienced user | update an alias for a command | change entries that are errorneous or outdated
`* *` | experienced user | view all alias for commands | review the alias in case I forget what I set for them
`* *` | experienced user | delete an alias for a command | remove entries that I no longer need
`* *` | experienced user | remove multiple tasks at once | reduce the number of commands I use to get the job done
`*` | user | clear all tasks | start afresh with a new task list fast
`*` | user with many tasks | sort task by [priority level](#priority-level) then by end date | figure out which task should be cleared first
`*` | user with many tasks | see statistics for my number of tasks undone, doing, completed | figure out how much work I have left

## Appendix B : Use Cases


(For all use cases below, the **System** is `ToLuist` and the **Actor** is the `user`, unless specified otherwise)

#### Use case 1: Add a task

**MSS**

1. Actor requests to add a task with `description` in the input box.
2. System adds the task. System shows a feedback message ("Task `description` added") and displays the updated list.<br>
Use case ends.

**Extensions**

1a. `description` is not provided.

> 1a1. System shows an error message ("Please provide a task description") with the correct format example.<br>
> Use case resumes at step 1

#### Use case 2: Delete a task

**MSS**

1. Actor requests to delete a task with `index` number in the input box.
2. System finds the task and deletes it. System shows a feedback message ("Task `description` removed") and displays the updated list.<br>
Use case ends.

**Extensions**

2a. `index` number given is invalid or cannot be found (i.e. `index` number is not a positive integer, or an out-of-range positive integer).

> 2a1. System shows an error message ("Please provide a proper index number") with the correct format example.
> Use case resumes at step 1

#### Use case 3: Undo previous mutated command

**MSS**

1. Actor requests to `undo` action in the input box.
2. System finds the most recent command that mutates the list and undoes it. System shows a feedback message ("Undo '`previous command`'") and displays the updated list.<br>
Use case ends.

**Extensions**

2a. No previous mutated command to undo

> 2a1. System does nothing since there is nothing to undo.
> Use case ends

#### Use case 4: Add alias command

**MSS**

1. Actor requests to `alias` a command in the input box with a `new alias name`.
2. System finds the command and alias it. System shows a feedback message ("`new alias name` is set as the new alias for `command`.").<br>
Use case ends.

2a. The alias is already reserved for other commands.

> 2a1. System updates the alias name to refer to the new command.<br>
> Use case resumes at step 3.

#### Use case 5: Set data storage file path

**MSS**

1. Actor requests to save data to a `new file path`.
2. System saves task list to the new data storage file path and delete the old file. System shows a message ("Storage file path is updated to: `new file path`").<br>
Use case ends.

**Extensions**

1a. File path entered is `default`.

> 1a1. System updates data storage file path to default file path. System shows a message ("Storage file path is updated to: `default file path`").<br>
> Use case ends.

1b. File path is not in the correct format.

> 1b1. System shows an error message ("Storage file path is in the wrong format.") with the correct format example.<br>
> Use case resumes at step 1.

1c. File path already exist.

> 1c1. System shows an error message ("Storage file path is already being used, please choose another location.").<br>
> Use case resumes at step 1.


## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 tasks without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands)
   should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Commands should be reasonably fluid and flexible (i.e. look like written English) so it is more intuitive for users.
5. When the command entered is errorneous, the system should guess the user's intentions to the best of its ability, and execute the corrected command after the user give his confirmation.
6. The stored data should never, ever be destroyed unless that is what the user wants.
7. Should have nice UI/UX so user have a pleasant experience using this App.
8. Each command should finish executing in less than 2 seconds.
9. Should have automated unit tests.
10. Should use Continuous Integration.
11. Should be kept open source.
12. Should be a free software.
13. Source code should be well-documented.
14. Application should be easy to set up (i.e. no installer required, no assistance required other than a user guide).


## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Recurring task

> A task that has to be done every fixed length of time.

##### Command history

> A list of commands that the user has entered.

##### Alias

> An alternative name to a default command name.

##### Priority level

> The relative importance or urgency of a task compared to other tasks.

## Appendix E : Product Survey

**Google Calendar**

Pros:

* Support recurring events.
* Monthly, weekly, daily calendar view is useful for users to visualise their schedule.
* Support fuzzy search for events.
* Certain operations does not require clicking (e.g. add event).
* Support sharing of calendar with other users through export to CSV or iCal.
* Can be used offline.

Cons:

* Does not support task without deadline.
* Does not support marking task as complete/incomplete.
* Certain operations requires clicking (e.g. update task), which is not what Jim wants.

**Trello**

Pros:

* Geared towards task management.
* Intuitive UI that gives an overview of a different task in a pipeline.
* Support collaboration on the task board.

Cons:

* Does not have good calendar integration.
* Cannot be used offline.
* Certain operations requires clicking (e.g. dragging and dropping tasks between list), which is not what Jim wants.

**Google Tasks**

Pros:

* Fast to set up and use.
* Seamless integration with Google Mail and Calendar.
* Easy to sync across multiple devices.
* Small footprint.

Cons:

* Cannot be used offline.
* Inflexible design.
* Lacking in additional features.
