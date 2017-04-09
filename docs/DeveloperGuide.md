# Developer Guide

<img src="images/coverPage.png" width="600">

* [Introduction](#setting-up)
* [Setting Up](#setting-up)
* [Design](#design)
* [Testing](#testing)
* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b--use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Gloassary](#appendix-d--glossary)

## Introduction

We welcome all software engineers who would like to contribute to our task manager OneTwoDo.

This guide is a comprehensive document that explains the development environment and internal structure of the application. This guide will help you to contribute to OneTwoDo. 

OneTwoDo is a simple yet effective to-do list application. It helps users to remember their important tasks and to organise their schedules. It is easy to use due to its simple interface and intuitive commands. It is also designed to be extremely keyboard-friendly, so as to save time clicking on buttons and tabs. 

### 1. Setting Up

You will have to ensure that the following settings are configured correctly to start contributing.

### 1.1 Prerequisites

As prerequisites, make sure that your computer has the following software installed.

1. **JDK 1.8.0_60** or later. Having any Java 8 version is not enough. This app will not work with earlier versions of Java 8.

2. **Eclipse IDE**.

3. **e(fx)clipse** plugin for Eclipse . You may refer to the tutorial by doing the steps 2 onwards given here: http://www.eclipse.org/efxclipse/install.html#for-the-ambitious

4. **Buildship Gradle** Integration plugin from the Eclipse Marketplace.

5. **Checkstyle** Plug-in plugin from the Eclipse Marketplace.

### 1.2 Importing the project into Eclipse

We recommend developing this application using Eclipse. Simply follow these steps to import OneTwoDo into your Eclipse Workspace:

1. Fork this repo, and clone the fork to your computer.

2. Open Eclipse (Note: Ensure you have installed the e(fx)clipse and buildship plugins as given in the prerequisites above).

3. Click *File* > *Import*.

4. Click *Gradle* > Gradle *Project* > *Next* > *Next*.

5. Click *Browse*, then locate the project’s directory.

6. Click *Finish*.

**Notes:**

* If you are asked whether to ‘keep’ or ‘overwrite’ config files, choose ‘keep’.

* Depending on your connection speed and server load, it may take up to 30 minutes for the set up to finish. This is because Gradle downloads library files from servers during the project set up process. 

* If Eclipse auto-changed any settings files during the import process, you can discard those changes.

### 1.3 Configuring Checkstyle

To adhere to the common coding style adopted for this project, you may want to import Checksyle into WorkSpace as well. 

1. Click *Project* -> *Properties* -> *Checkstyle* -> *Local Check Configurations* -> *New*...

2. Choose *External Configuration File* under *Type*.

3. Enter an arbitrary configuration name e.g. onetwodo.

4. Import checkstyle configuration file found at *config/checkstyle/checkstyle.xml*.

5. Click *OK* once, go to the *Main tab*, use the newly imported check configuration.

6. Tick and select files from packages, click *Change*..., and select the resources package.

7. Click *OK* twice. Rebuild the project if prompted.

**Notes**

* Click on the files from packages text after ticking in order to enable the Change... button. 

### 1.4 Troubleshooting project setup

You may encounter some problems during the setup. Please refer to the solutions below to address some common problems.

**Problem**: Eclipse reports compile errors after new commits are pulled from Git

* Reason: Eclipse fails to recognize new files that appeared due to the Git pull.

* Solution: Refresh the project in Eclipse. Right click on the project (in Eclipse package explorer), choose *Gradle* -> *Refresh Gradle Project*.

**Problem**: Eclipse reports some required libraries missing

* Reason: Required libraries may not have been downloaded during the project import.

* Solution: Run tests using Gradle once (to refresh the libraries).

## 2. Design

In this section, you will find out more about the internal parts of the application and how they work together. 

### 2.1 Architecture

<img src="images/Architecture.png" width="600"><br>
*Figure 2.1.1 : Architecture Diagram*

A high-level design of our OneTwoDo as shown in the Architecture Diagram in **_Figure 2.1.1_** above. 

> If you want to update the diagram, you can do so in the provided `.pptx` file. Simply select the objects of the diagram, and choose `Save as picture`. 
 

`Main` has only one class called [`MainApp`](../src/main/java/seedu/onetwodo/MainApp.java). Its responsible are:

* At app launch: Initializes the components in the correct sequence, and connects them up with each other at app launch.

* At shut down: Shuts down the components and invokes cleanup method where necessary.


`Commons` represents a collection of classes used by other components. Two of those classes play important roles at the architecture level.

* `EventsCenter` : This class is used by components to communicate with other components using events (i.e. a form of **Event Driven** design).

* `LogsCenter`: This class is used by many classes to write log messages to the OneTwoDo's log file.

The rest of OneTwoDo consists of these four components.

* [**`UI`**](#ui-component): The UI of the App.

* [**`Logic`**](#logic-component) : The command executor.

* [**`Model`**](#model-component) : Holds the data of the App in-memory.

* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

Each of the four components defines its **_API_** in an `interface` with the same name as the Component. They also exposes its functionality using a {Component Name}Manager class. For example, the `Logic` component defines its API in the [`Logic.java`](../src/main/java/seedu/onetwodo/logic/Logic.java) interface and exposes its functionality using the `LogicManager.java` class.

<!-- @@author A0139343E -->
**Singleton**
The [`MainApp`](../src/main/java/seedu/onetwodo/MainApp.java) uses the common design pattern known as "Singleton". This ensures that at any point of time, there is only one instance of [`MainApp`](../src/main/java/seedu/onetwodo/MainApp.java). In this way, it allows the 4 major components to be encapsulated and still provides a global point of access for other classes.

<!-- @@author A0141138N -->
**Separation of concerns**
OneTwoDo implements the Separation of concerns (SoC) design principle.
* Our Application has been split into 4 distinct sections or components, represented by the color-coding in our Architecture Diagram: `UI`, `Model`, `Logic` and `Storage`. Each section addresses a separate feature or concern. For example, the `UI` component specifically handles the user's interactions with the application.

* Applying such a design principle ensures _modularity_ by encapsulating information inside a section of the code which has a well-defined interface.

* This ensures that changes to one componenet does not affect the usability of other components. Modifications can be made to one component without knowledge of the other components, and without having to make corresponding changes to those sections. 

<!-- @@author -->

#### Events-Driven nature of the design

The _Sequence Diagram_ below shows how the components interact for the scenario where the user issues the command **`delete e1`**

<img src="images\SDforDeletePerson.png" width="800"><br>
*Figure 2.1.2a : Component interactions for delete e1 command (part 1)*

Note how the `Model` simply raises a `OneTwoDoChangedEvent` when the OneTwoDo data is changed, instead of asking the `Storage` to save the updates to the hard disk.

The diagram below shows how the `EventsCenter` reacts to that event, which eventually results in the updates being saved to the hard disk and the status bar of the UI being updated to reflect the ‘Last Updated’ time. 

<img src="images\SDforDeletePersonEventHandling.png" width="800"><br>
*Figure 2.1.2b : Component interactions for `delete e1` command (part 2)*

Did you realise? The event is propagated through the **EventsCenter** to the Storage and UI without Model having to be coupled to either of them. This is an example of how this Event Driven approach helps us reduce direct coupling between components.


<!-- @@author A0141138N -->
**Model-View-Controller (MVC) pattern**

Model: `Model` which stores and maintains the user data in `Storage`, as seen in _Figure 2.1.3a_.

View: `UI` which interacts with the user and displays the data to the user.

Controller: `Logic` which accepts user inputs and performs follow-up actions.
The sections below give more details of each component.

In the sections below we will provide more details about each component. As OneTwoDo is written in the Object-Oriented Paradigm, you may want to pay attention to how these components work and interact with each other. 

<!-- @@author A0143029M -->
### 2.2. UI component

<img src="images/UiClassDiagram.png" width="800"><br>
*Figure 2.2.1 : Structure of the UI Component*

You can find the API for this component in [`Ui.java`](../src/main/java/seedu/onetwodo/ui/Ui.java).

The UI consists of a `MainWindow` that is made up of parts such as `CommandBox`, `ResultDisplay`, `TaskListPanel`, `StatusBarFooter`, and `WelcomeWindow`. These parts, including the `MainWindow`, inherit from the abstract `UiPart` class.

The UI component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/ view` folder. For example, the layout of the `MainWindow` is specified in [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The [jfoenix](http://www.jfoenix.com/) library has also been used to provide the `JFXDialog` component, which is used to display UI elements such as the `WelcomeWindow` and `WebView` for the command summary and user guide.

The `UI` component:

* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the `UI` can auto-update when data in the `Model` change.
* Responds to events (e.g. `CloseDialogEvent`) raised from various parts of OneTwoDo and updates the `UI` accordingly.

**Observer Pattern**
The `UI` component adopts the observer pattern as it observes the list of tasks to be shown in OneTwoDo through the `UnmodifiableObservableList` class and updates the tasks shown when it is notified of changes to the list.


<!-- @@author A0135739W-->
### 2.3. Logic component

<img src="images/LogicClassDiagram.png" width="800"><br>
*Figure 2.3.1 : Structure of the Logic Component*

You can find the API for this component in [`Logic.java`](../src/main/java/seedu/onetwodo/logic/Logic.java).

* `Logic` uses the `Parser` class to parse the user command.

* This results in a `Command` object which is executed by the `LogicManager`.

* The command execution can affect the `Model` (e.g. adding a person) and/or raise events.

* The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

Given below is the _Sequence Diagram_ for interactions within the `Logic` component for the execute("delete e1") API call.

<img src="images/DeletePersonSdForLogic.png" width="800"><br>
*Figure 2.3.1 : Interactions Inside the Logic Component for the delete d2 Command*


**Open Closed Principle**
The `Logic` component follows the _Open Closed  Principle_. Both the `Command` object and `CommandResult` object can be extended to other classes to implement different functions, without requiring themselves to be modified.

**Command Pattern**
The `Logic` component adopts the _Command Pattern_. This allows other components to execute the commands without having to know each of the command types. This also allows the command object to be passed around, stored and executed. 


<!-- @@author A0141138N -->

### 2.4. Model component

<img src="images/ModelClassDiagram.png" width="800"><br>
*Figure 2.4.1 : Structure of the Model Component*

You can find the API for this component in [`Model.java`](../src/main/java/seedu/onetwodo/model/Model.java).

The Model is capable of the following:

* Stores a `UserPref` object that represents the user’s preferences.

* Stores the OneTwoDo data.

* Exposes an `UnmodifiableObservableList`. They that can be ‘observed’ so that the UI automatically updates when the data in the list change.

* Does not depend on any of the other three components.

**Observer Pattern**
The `Model` component uses the _Observer Pattern_ to update the `UI` automatically when any changes are made to the data. This pattern is implemented by exposing the `UnmodifiableObservableList` which is 'observed' by the `UI`. Using the _Observer Pattern_ allows the `Model` and `UI` components to interact with each other while avoiding a direct coupling between both the components. 

**Facade Pattern**
The `Model` component also uses the _Facade Pattern_ to allow other components to access it without exposing its internal functionalities. All other components need to use the [`Model.java`](../src/main/java/seedu/onetwodo/model/Model.java) interface to read or make any changes to the user data.

<img src="images/Task.png" width="800"><br>
*Figure 2.4.2 : Structure of the Task Component*

You can find the API for this component in [`Task.java`](../src/main/java/seedu/onetwodo/model/task/Task.java).

It is compulsory for a task to have the following parameters: `Name` and `TaskType`. Other parameters such as `StartDate`, `EndDate`, `Priority`, `Recur`, `Description` and `Tag` are optional. A task can have multiple tags. `StartDate` and `EndDate` classes inherit from `Date` class.

A task can be defined in the following ways:

1. **To-do**: A task without a `StartDate` and `EndDate`.

2. **Deadline**: A task with an `EndDate` but no `StartDate`.

3. **Event**: A task with both a `StartDate` and an `EndDate`.

<!-- @@author A0139343E -->
### 2.5. Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>
*Figure 2.5.1 : Structure of the Storage Component*

You can find the API for this component in [`Storage.java`](../src/main/java/seedu/onetwodo/storage/Storage.java).

The Storage component has **2 functionalities**:

1. Saves the `UserPref` objects in `.json` format and read it back.

2. Saves the OneTwoDo task data in `.xml` format and read it back.
<!-- @@author -->
### 2.6. Common classes

In the `seedu.onetwodo.commons` package, you can find classes that are commonly used by multiple components. They include exception classes, `EventsCenter` and `LogsCenter`. 

## 3. Implementation

### 3.1. Logging

Debugging benefits greatly from logging. We are using the `java.util.logging` package for logging. You can use the `LogsCenter` class to manage the logging levels and logging destinations.

**Notes:**

* The logging level can be controlled using the `logLevel` setting in the configuration file (See [Configuration](#configuration)).

* The Logger for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to the specified logging level.

* Currently log messages are output through the console and to a `.log` file.

There are 4 different logging levels: 

* `SEVERE`: Used for indicating critical problems detected, which may cause OneTwoDo to terminate.

* `WARNING`: Used for indicating minor problems detected. OneTwoDo can continue to run, but warnings to caution the developer are displayed.

* `INFO`: Used for indicating typical noteworthy information and actions performed by OneTwoDo.

* `FINE`: Used for indicating details that are not usually noteworthy but may be useful in debugging (e.g. print the actual list instead of just its size).

### 3.2. Configuration

You can control various important properties of the application in the file named `config.json`. These properties include App name and logging level. Simply open the `config.json` file and change their values to suit your needs.

## 4. Testing

In this section, you will find out more about how testing is done when developing OneTwoDo.

### 4.1. Running tests

You can find all tests in the `./src/test/java` folder.

**Running tests in Eclipse:**

* To run all tests, you can simply right-click on the `src/test/java` folder and choose `Run as > JUnit Test`

* To run a subset of tests, you can right-click on a test package, test class, or even a test and choose to run as a `JUnit test`.

**Running tests using Gradle:**

* You may refer to [UsingGradle.md](UsingGradle.md) for how to run tests using Gradle.

We have two types of tests:

1. **GUI Tests** - These are _System Tests_ that test the entire application by simulating user actions on the GUI. You can find them in the guitests package.

2. **Non-GUI Tests** - These are tests not involving the GUI. They include:

    * _Unit tests_: This targets the lowest level methods/classes, e.g. `seedu.onetwodo.commons.UrlUtilTest`.

    * _Integration tests_: This checks the integration of multiple code units, e.g. `seedu.onetwodo.storage.StorageManagerTest`.

    * _Hybrids of unit and integration tests_.: These tests help to check multiple code units as well as how the are connected together. For example please see `seedu.onetwodo.logic.LogicManagerTest`.

#### Headless GUI Testing

Thanks to the [TestFX](https://github.com/TestFX/TestFX) library we use, GUI tests can be run in the headless mode. In the headless mode, GUI tests do not show up on the screen. That means the developer can do other things on the Computer while the tests are running. You may want to see [UsingGradle.md](UsingGradle.md#running-tests) to learn how to run these tests.

### 4.2. Troubleshooting tests

**Problem:** Tests fail because NullPointException when AssertionError is expected.

This is caused by assertions not being enabled for JUnit tests. This usually occurs if you are not using a recent Eclipse version (i.e. *Neon* or later).  Try enabling assertions in JUnit tests as described here: stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option. Also, you can try to delete previous run configurations that were created when you ran the tests.

## 5. Dev Ops

In this section, you will find out more about how to make builds, tests, and releases more rapidly, frequently, and reliably.

### 5.1. Build Automation

We use Gradle for automation build. You can check [UsingGradle.md](UsingGradle.md#running-tests) to learn more about Gradle automation build.

### 5.2. Continuous Integration

We use [Travis CI](https://travis-ci.org/) and [AppVeyor](https://www.appveyor.com/) to perform Continuous Integration on our projects. 

You can check [UsingTravis.md](UsingTravis.md) and [UsingAppVeyor.md](UsingAppVeyor.md) for more details.

### 5.3. Publishing Documentation

You can visit [UsingGithubPages.md](UsingGithubPages.md) to learn how to use GitHub Pages to publish documentation to the project site.

### 5.4. Making a Release

Here are the steps to create a new release:

1. Generate a JAR file using Gradle.

2. Tag the repo with the version number. e.g. v0.1.

3. Create a new release using GitHub.

4. and upload the JAR file you created.

### 5.5. Managing Dependencies

Projects often depends on third-party libraries. Likewise, OneTwoDo depends on the **Jackson** library for XML parsing. Do not worry about managing these dependencies. It can be automated using **Gradle**. Gradle can do amazing stuff such as downloading the dependencies automatically. This is better than the alternatives of: 

* Including those libraries in the repo (this bloats the repo size).

* Requiring developers to download those libraries manually (this creates extra work for developers. 

## Appendix A : User Stories

<table>
  <tr>
    <td>Priority</td>
    <td>As a …</td>
    <td>I want to …</td>
    <td>So that I can…</td>
  </tr>
  <tr>
    <td>High</td>
    <td>new user</td>
    <td>see usage instructions</td>
    <td>refer to instructions when I forget how to use the App</td>
  </tr>
  <tr>
    <td>High</td>
    <td>user</td>
    <td>add a new task</td>
    <td>add task to manage all my to-do</td>
  </tr>
  <tr>
    <td>High</td>
    <td>user</td>
    <td>delete a task</td>
    <td>remove entries that I no longer need</td>
  </tr>
  <tr>
    <td>High</td>
    <td>user with many tasks</td>
    <td>find a task by keywords</td>
    <td>search for specific task especially when the list is huge</td>
  </tr>
  <tr>
    <td>High</td>
    <td>user</td>
    <td>edit a task</td>
    <td>edit a task if there are any changes</td>
  </tr>
  <tr>
    <td>High</td>
    <td>user</td>
    <td>clear all tasks</td>
    <td>quickly start using the tool from scratch</td>
  </tr>
  <tr>
    <td>High</td>
    <td>user</td>
    <td>close application</td>
    <td>exit after use</td>
  </tr>
  <tr>
    <td>High</td>
    <td>organized user</td>
    <td>add tags to a task</td>
    <td>organize them by tags</td>
  </tr>
  <tr>
    <td>High</td>
    <td>user</td>
    <td>add description to task</td>
    <td>describe tasks in more detail</td>
  </tr>
  <tr>
    <td>High</td>
    <td>user</td>
    <td>view task description</td>
    <td>view the task in details</td>
  </tr>
  <tr>
    <td>High</td>
    <td>user</td>
    <td>list tasks</td>
    <td>view all uncompleted task</td>
  </tr>
  <tr>
    <td>High</td>
    <td>advanced user</td>
    <td>sort task by importance</td>
    <td>prioritise which task to do first</td>
  </tr>
  <tr>
    <td>High</td>
    <td>last minute user</td>
    <td>sort tasks by most upcoming tasks</td>
    <td>get myself prepared for more urgent tasks</td>
  </tr>
  <tr>
    <td>High</td>
    <td>advanced user</td>
    <td>find tasks by tag</td>
    <td>find tasks quickly that contains the specific tag</td>
  </tr>
  <tr>
    <td>High</td>
    <td>advanced user</td>
    <td>set reminder to a task</td>
    <td>be notified when tasks are approaching due date</td>
  </tr>
  <tr>
    <td>High</td>
    <td>user</td>
    <td>select folder to save data storage</td>
    <td>specify a specific folder and file for data storage</td>
  </tr>
  <tr>
    <td>High</td>
    <td>user</td>
    <td>undo most recent action</td>
    <td>undo action to rectify mistake</td>
  </tr>
  <tr>
    <td>Medium</td>
    <td>user</td>
    <td>redo most recent action</td>
    <td>redo action to rectify mistake</td>
  </tr>
  <tr>
    <td>Medium</td>
    <td>user</td>
    <td>add task to nearest free slot automatically</td>
    <td>add task easily without browsing for free time</td>
  </tr>
  <tr>
    <td>Medium</td>
    <td>user with multiple recurring tasks</td>
    <td>add recurring tasks</td>
    <td>avoid adding similar task one by one</td>
  </tr>
  <tr>
    <td>Medium</td>
    <td>user with multiple recurring tasks</td>
    <td>edit all instances of a recurring task</td>
    <td>be more efficient when editing recurring tasks</td>
  </tr>
  <tr>
    <td>Medium</td>
    <td>advanced user</td>
    <td>filter tasks by multiple keywords</td>
    <td>search tasks by more specific criteria</td>
  </tr>
  <tr>
    <td>Medium</td>
    <td>user</td>
    <td>list all tags</td>
    <td>quickly view all the tags in the to-do list</td>
  </tr>
  <tr>
    <td>Medium</td>
    <td>user</td>
    <td>keep track of completed tasks</td>
    <td>keep them in track for future references</td>
  </tr>
  <tr>
    <td>Medium</td>
    <td>busy user</td>
    <td>postpone task</td>
    <td>defer uncompleted task to near future where I am free</td>
  </tr>
  <tr>
    <td>Medium</td>
    <td>advanced user</td>
    <td>select task and pin it at the top of display</td>
    <td>quickly mark certain tasks as demanding of priority attention</td>
  </tr>
  <tr>
    <td>Medium</td>
    <td>existing google calendar user</td>
    <td>sync google calendar on create, update and delete tasks</td>
    <td>exploit google calendar and sync with this task manager</td>
  </tr>
  <tr>
    <td>Medium</td>
    <td>user with heavy email usage</td>
    <td>send email notification for upcoming tasks</td>
    <td>remind me on tasks that are approaching due date</td>
  </tr>
  <tr>
    <td>Medium</td>
    <td>user who prefers keyboard</td>
    <td>open/close application via keyboard shortcut</td>
    <td>quickly open or exit program easily without mouse click</td>
  </tr>
  <tr>
    <td>Medium</td>
    <td>advanced user</td>
    <td>remove all tasks with certain tag</td>
    <td>quickly remove all tasks with some similarity</td>
  </tr>
  <tr>
    <td>Low</td>
    <td>advanced user</td>
    <td>change all tag names in one go</td>
    <td>quickly update all tasks with that tag name</td>
  </tr>
  <tr>
    <td>Low</td>
    <td>user with many tasks</td>
    <td>assign priorities to tasks</td>
    <td>distinguish and pinpoint importance task immediately just by looking</td>
  </tr>
  <tr>
    <td>Low</td>
    <td>user</td>
    <td>list overdue tasks</td>
    <td>filter out overdue tasks to decide for further actions</td>
  </tr>
  <tr>
    <td>Low</td>
    <td>user</td>
    <td>remove tasks within certain range</td>
    <td>easily specify date to remove tasks within that range.</td>
  </tr>
  <tr>
    <td>Low</td>
    <td>user</td>
    <td>list near future free slot</td>
    <td>choose suitable free slot to add task</td>
  </tr>
</table>


**Priorities:**
- **High** (must have)
- **Medium** (nice to have) 
- **Low** (unlikely to have) 

<!-- @@author A0139343E -->
## Appendix B : Use Cases

"System" refers to OneTwoDo. "Actor" refers the user.

### UC01 - Add a task

#### **MSS**

1. User input information to add task.

2. System notifies user that the task has been successfully added.
 
Use case ends.
<br>

#### Extensions

2a. Task already exists.

2a1. System informs user that the task already exists and doesn’t add repeated task.
    
Use case ends.
<br>

2b. Invalid command format.

2b1. System informs user that the command format is invalid and outputs a sample format to user.

Use case ends.
<br>

### UC02 - Delete task

#### **MSS**

1. System display a list of tasks.

2. User input task index to specify which task to delete.

3. System notifies user that task has been successfully deleted.

Use case ends.
<br>

#### Extension

1a. List is empty.

1a1. System informs user that there is no task to delete.

Use case ends.
<br>

2a. Invalid command format.

2a1. System informs user that the input is invalid and outputs a sample format.

Use case resume at 1.
<br>

2b. User input index is not valid.

2b1. System informs user that the input index is invalid.

Use case resume at 1.
<br>

### UC03 - Edit task

#### **MSS**

1. User requests list of tasks.

2. System displays a list of tasks.

3. User selects a task to edit.

4. System shows user the task information.

5. User edits and submits a new task information.

6. System displays changes made to task.

Use case ends.
<br>

#### Extension

2a. The list is empty.

2a1. System informs user that there is no task to edit.

Use case ends.
<br>


3a. User task selection is invalid.

3a1. System shows error message.

Use case resumes from step 2.
<br>


3b. System detects an error in the data that user entered.

3b1. System selects valid task.

3b2. User enters new data.

3b3. Repeat steps 3b1 and 3b2 until data has no errors.

Use case resumes from step 4.
<br>

3c. User requests to tag a task with a category that is not currently in the system.

3c1. System creates the category.

Use case resumes from step 4.
<br>

### UC04 - Find task

#### **MSS**

1. User searches for task.

2. System lists tasks which match the keywords entered by user.

Use case ends.

#### Extension

2a. Task does not exist.

2a1. System informs user that no tasks is found.

Use case ends.
<br>

### UC05 - List tasks

#### **MSS**

1. User requests list of tasks.

2. System displays list of tasks.

Use case ends.
<br>

#### Extension

1a. User requests to list task in a certain category.

1a1. System displays list of tasks in that category.

Use case ends.
<br>


2a. The list is empty.

2a1. System displays empty list.

Use case ends.
<br>

### UC06 - Done task

#### **MSS**

1. User requests to list the tasks.

2. System displays a list of tasks.

3. User selects the task to mark as done.

4. System shows user remaining tasks that are uncompleted.

Use case ends.
<br>

#### Extension

3a. User task selection is invalid.

3a1. System shows error message.

Use case resumes from step 2.
<br>


### UC07 - Undo most recent action

#### **MSS**

1. User issues command undo.

2. System reverts user’s previous command and displays feedback message to user.

Use case ends.
<br>


#### Extension

2a. There is no previous command yet.

2a1. System feedbacks to user that no previous command has found.

Use case ends.
<br>


2b. Previous command is not mutable.

2b1. System feedbacks to user no action required.

Use case ends.
<br>

## Appendix C : Non-Functional Requirements

1. Should work on any [mainstream OS](https://hackmd.io/EYBgHATAxgzGAmBaA7BAjEgLMNNEE4QBDZRIgVnQDN1Mj9NMg===?view#mainstream-os).

2. Should be able to store up to 500 tasks.

3. Should work in online or offline mode.

4. Should respond to commands within 2 seconds.

5. Programme should startup within 3 seconds.

6. Programme should be open source code and readily available.

7. Should have an easy to use interface.

8. Should be able to handle exceptions from user input.

9. Should work as a standalone application.

10. Should have an executable file that can be launched by double clicking or keyboard shortcut.

11. Should not use relational databases to store data.

12. Command line interface should be the main mode of input.

13. Should not violate any copyrights.

14. Should not contain obscene or inappropriate content.

## Appendix D : Glossary

<table>
  <tr>
    <td>Mainstream OS</td>
    <td>Windows, Linux, Unix, OS-X.</td>
  </tr>
  <tr>
    <td>Keyword</td>
    <td>Words in task names, tag names, and task description.</td>
  </tr>
  <tr>
    <td>Invalid Commands</td>
    <td>Invalid commands includes invalid arguments.</td>
  </tr>
  <tr>
    <td>Error message</td>
    <td>Error message includes suggestion for correct command.</td>
  </tr>
  <tr>
    <td>START_DATE</td>
    <td>START_DATE refers to start date and time entered.
Defaults to time of 0000 hrs if no time is indicated.</td>
  </tr>
  <tr>
    <td>END_DATE</td>
    <td>END_DATE refers to end of date and time entered.
Defaults to time of 2359 hrs if no time is indicated.</td>
  </tr>
  <tr>
    <td>Event task</td>
    <td>Tasks that have a specific start and end date.</td>
  </tr>
  <tr>
    <td>Deadline task</td>
    <td>Tasks that only have a specific end date.</td>
  </tr>
  <tr>
    <td>To-Do task</td>
    <td>Tasks that have no start or end date.</td>
  </tr>
  <tr>
    <td>Category</td>
    <td>One of Event, Deadline or To-do.</td>
  </tr>
</table>


## Appendix E : Product Survey

<!-- @@author A0141138N -->
### Review for *Remember the Milk*

**Pros:**

* Easy to use interface.

* User can add recurring tasks.

* User can set a start date and end date for the task.

* User can add tag to the task.

* User can view a list of all completed tasks.

* User can set priority to task.

* User can postpone a task.

**Cons:**

* No online support or video tutorials for beginners.

* Pro version is very expensive and most features are only available in pro version.

* No security for data in terms of encryption or HTTPS.

<!-- @@author A0143029M -->
### Review for *Google Calender*

**Pros:**

* Calendar user interface, click on events on the calendar to view more details.

* Users can edit events by dragging.

* Users are able to import calendars using .ical files.

* Users can edit a single instance of event, or recurring events.

* Users can be notified of approaching events.

* Users have daily, weekly, monthly, and "4-days" types of view.

* Users can sync and access on multiple platforms, like mobile, and desktop.

**Cons:**

* Have to be online to access calendar.

* No command line interface.

* Updates may take a while to sync due to network issues.

<!-- @@author -->
### Review for *Wunderlist*

**Pros:**

* Allows user to note down descriptions and more information for each task.

* Allows user to create subtasks for each task.

* Allows user to set "Reminder" and “Due Date” separately.

* Allows user to highlight(star) a task.

* Separates "Delete" and “Mark as complete”.

* Parses the reminder time when user types in the task name.

* Automatically creates "Today" and “Week” folder for user to see what needs to be done for today and this week (more urgent tasks).

* Allows user to categorise tasks using folders, e.g. (work, family).

**Cons:**

* Just highlighting (starring) is not enough. User may just highlight all tasks and that defeats the purpose of starring.

* Tasks are not sorted in chronological order in the main folder.

<!-- @@author A0139343E -->
### Review for *Habitica*

**Pros:**

* It has simulation game element which keep the work fun and motivating.

* It has colourful and clean user interface that make important tasks stand out.

* It supports multiple platforms such as mobile and desktop.

* It supports recurring task adding.

* It can set multiple and different reminders on any task.

* It can easily be reordered/sorted using name or tags.

**Cons:**

* Unable to view the calendar when selecting tasks

* Adding tasks require mouse/ touch screen instead of keyboard entering.

* Has the potential of distraction due to the existence of game elements.

* Deadline of the tasks are not shown unless clicked.

* Unable to mark tasks as done without keeping them for future references.

<!-- @@author -->
