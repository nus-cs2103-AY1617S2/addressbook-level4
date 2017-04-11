# Opus - Developer Guide

By : `Team W15-B3`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Setting Up](#1-setting-up)
    1. [Prerequisites](#11-prerequisites)
    2. [Installing Checkstyle manually](#12-installing-checkstyle-manually)
2. [Design](#2-design)
    1. [Architecture](#21-architecture)
    2. [UI Component](#22-ui-component)
    3. [Logic Component](#23-logic-component)
    4. [Model Component](#24-model-component)
    5. [Storage Component](#25-storage-component)
3. [Implementation](#3-implementation)
4. [Code Quality and Testing](#4-code-quality-and-testing)
    1. [Code Quality](#41-code-quality)
    2. [Testing](#42-testing)
    3. [Improving test coverage using Coveralls](#43-improving-test-coverage-using-coveralls)
    4. [Troubleshooting tests](#44-troubleshooting-tests)
5. [Dev Ops](#5-dev-ops)
6. [Version Control](#6-version-control)
    1. [Git + GitHub](#61-git--github)
    2. [Branching and Workflow](#62-branching-and-workflow)
    3. [Commit Messages](#63-commit-messages)

---

* [Appendix A: Non Functional Requirements](#appendix-a--non-functional-requirements)
* [Appendix B: Glossary](#appendix-b--glossary)
* [Appendix C : Product Survey](#appendix-c--product-survey)
* [Appendix D: User Stories](./UserStories.md)
* [Appendix E: Use Cases](./UseCases.md)

---

## 1. Setting up

### 1.1. Prerequisites

1. **JDK 1.8.0_60 or later**.
    > JDK version `1.8.0_60` or later is required to run the project correctly
2. **Eclipse IDE Neon**.
    >Follow the instructions [here](https://www.eclipse.org/downloads/eclipse-packages/?show_instructions=TRUE) to download and install Eclipse.
3. **e(fx)clipse plugin for Eclipse**.
    >Follow the instructions [here](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious) to set up e(fx)clipse for Eclipse
4. **Buildship Gradle Integration**.
    >Buildship is bundled together with Eclipse Neon and above.
5. **Checkstyle Plug-in**.

### 1.2. Installing Checkstyle manually
    As of August 16, 2016, the Checkstyle repository in Eclipse Marketplace is unavailable.

1. Download the compressed .zip file [here](https://sourceforge.net/projects/eclipse-cs/?source=typ_redirect). **Do not** extract the contents.
2. Launch Eclipse Neon.
3. Click `Help` > `Install new Software...`.
<br><img src="images/checkstyle/checkstyle_1.png" width="400"><br><br>
4. Click `Add..`.
<br><img src="images/checkstyle/checkstyle_2.png" width="400"><br><br>
5. Click `Archive` to open up a directory window.
<br><img src="images/checkstyle/checkstyle_3.png" width="400"><br><br>
6. Nagivate and select the **uncompressed** download file. Click `Open` > `OK`.
<br><img src="images/checkstyle/checkstyle_4.png" width="400"><br><br>
7. Check `Checkstyle` and click `Next`.
<br><img src="images/checkstyle/checkstyle_5.png" width="400"><br><br>
8. Click `Next` and accept the user license agreement as follows.
<br><img src="images/checkstyle/checkstyle_6.png" width="400"><br><br>
9. Click `Finish` to install Checkstyle.
<br><img src="images/checkstyle/checkstyle_7.png" width="400"><br><br>

### 1.3. Importing the project through Gradle

1. Clone this repository through this [link](https://github.com/CS2103JAN2017-W15-B3/main.git).
2. Launch Eclipse.
    >Ensure you have installed the **e(fx)clipse** and **buildship** plugins per the prerequisites above.
3. Click `File` > `Import`.
4. Click `Gradle` > `Gradle Project` and then `Next`.
<br><img src="images/gradle/gradle_2.png" width="400"><br><br>
5. Click `Next` in the Gradle Import Wizard.
<br><img src="images/gradle/gradle_3.png" width="400"><br><br>
6. Click `Browse` and nagivate to the directory of the cloned repository. Click `Finish` to begin importing the project.
<br><img src="images/gradle/gradle_4.png" width="400"><br><br>
   > * Gradle will begin importing and building the project. This process may take up 15 minutes depending on the internet connection speed.
   > <br><img src="images/gradle/gradle_5.png" width="400"><br><br>
   > * Upon completion, locate the Gradle Tasks menu at the lower section of Eclipse Neon.
   > <br><img src="images/gradle/gradle_6.png" width="400"><br>
7. Open the project menu and select `build` > `build` to build and test the project.
<br><img src="images/gradle/gradle_7.png" width="400"><br><br>
8. Check that the project has builded successfully in the Console view.
<br><img src="images/gradle/gradle_8.png" width="400"><br><br>
More information about using Gradle can be found [here](https://github.com/CS2103JAN2017-W15-B3/main/blob/docs-settingup/docs/UsingGradle.md).
  > * If you are asked whether to 'keep' or 'overwrite' config files, choose to 'keep'.
  > * Depending on your connection speed and server load, it can even take up to 30 minutes for the set up to finish
      (This is because Gradle downloads library files from servers during the project set up process)
  > * If any settings files are changed by Eclipse during the import process, you can discard those changes.


### 1.4. Configuring Checkstyle
1. Click `Project` -> `Properties` -> `Checkstyle` -> `Local Check Configurations` -> `New...`.
2. Choose `External Configuration File` under `Type`.
3. Enter an arbitrary configuration name e.g. opus.
4. Import checkstyle configuration file found at `config/checkstyle/checkstyle.xml`.
5. Click OK once, go to the `Main` tab, use the newly imported check configuration.
6. Check and select `files from packages`, click `Change...`, and select the `resource`.
7. Click OK twice. Rebuild project as required.

    Click on the `files from packages` text after ticking in order to enable the `Change...` button

### 1.5. Troubleshooting project setup

#### 1.5.1.  Eclipse reports compile errors after merging new commits pulled from Git.
> This is because Eclipse failed to recognize the new files that are pulled from Git.<br>
1. Right click on the project (in Eclipse package explorer) and choose `Gradle` > `Refresh Gradle Project`.

#### 1.5.2.  Eclipse reports missing libraries.
> Eclipse has failed to retrieve all required dependencies during the project import.<br>
1. Right click on the project and select `Gradle` > `Run tests using Gradle`.

## 2. Design

### 2.1. Architecture

<img src="images/Architecture.png" width="600"><br>
_Figure 2.1.1 : Architecture Diagram_

The **_Architecture Diagram_** above explains the high-level design of the App.
Below is a quick overview of each component.

> Tip: The `.pptx` files used to create diagrams in this document can be found in the [diagrams](diagrams/) folder.
> To update a diagram, modify the diagram in the pptx file, select the objects of the diagram, and choose `Save as picture`.

#### Main
`Main` has only one class called [`MainApp`](../src/main/java/seedu/address/MainApp.java). It is responsible for,

* Initializing the components in the correct sequence at app launch, and connects them up with each other.
* Shutting down the components and invokes cleanup method where necessary on exit.

#### Commons
[**`Commons`**](#26-common-classes) represents a collection of classes used by multiple other components.
Two of those classes play important roles at the architecture level.

* `EventsCenter` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design)
* `LogsCenter` : This class is used by many other classes to write log messages to the App's log file for debugging and communication between developers.

#### User Interface (UI)
The [**`UI`**](#22-ui-component) represents graphical views and handles interactions between the user and the program.

#### Logic
The [**`Logic`**](#23-logic-component) accepts commands sent from the user passes it to the model for processing.

#### Model
The [**`Model`**](#24-model-component) holds the data of the App in-memory to manage and edit it accordingly to the commands received.

#### Storage
The [**`Storage`**](#25-storage-component) Reads and write data to the hard disk.

Each of the four components, [**`UI`**](#22-ui-component), [**`Logic`**](#23-logic-component),
[**`Model`**](#24-model-component) and [**`Storage`**](#25-storage-component)

* Defines its _API_ in an `interface` with the same name as the Component.
* Exposes its functionality using a `{Component Name}Manager` class.

For example, the `Logic` component (see the class diagram given below) defines it's Application Programming Interface (API) in the `Logic.java`
interface and exposes its functionality using the `LogicManager.java` class.<br><br>
<img src="images/LogicClassDiagram.png" width="800"><br>
_Figure 2.1.2 : Class Diagram of the Logic Component_

#### Events-Driven Architecture

Event-driven architecture (EDA) consists of event emitters and receivers that allow loosely coupled components to communicate with each other. The affected components react only when they receive events.
The _Sequence Diagram_ below shows how the components interact when the user inputs the command `delete 1`.

<img src="images/SDforDeleteTask.png" width="800"><br>
_Figure 2.1.3a : Component interactions for `delete 1` command (part 1)_

>Note how `Model` simply raises a `TaskManagerChangedEvent` when the TaskManager data is modified,
 instead of asking `Storage` to save the updates to the hard disk.

The diagram below shows how the `EventsCenter` reacts to that event, which eventually results in the updates
being saved to the hard disk and the status bar of the UI being updated to reflect the 'Last Updated' time. <br>
<img src="images/SDforDeleteTaskEventHandling.png" width="800"><br>
_Figure 2.1.3b : Component interactions for `delete 1` command (part 2)_

> Note how the event is propagated through the `EventsCenter` to the `Storage` and `UI` without `Model` having
  to be coupled to either of them. This is an example of how Event Driven approach helps us reduce direct
  coupling between components.

The sections below give more details about each component.

### 2.2. UI component

Author: [Xu Bili](http://github.com/xbili)

The `UI` component allows users to enter commands and receive the results through its graphical interface. This component is the first point of contact with the user. It is responsible for passing the correct input from the user to the `Logic` component.

<img src="images/UiClassDiagram.png" width="800"><br>
_Figure 2.2.1 : Structure of the UI Component_

**API** : [`Ui.java`](../src/main/java/seedu/address/ui/Ui.java)

The UI consists of a `MainWindow` which contains `CommandBox`, `ResultDisplay`, `MainPanel`, `SidePanel`, and a `StatusBarFooter`.
All these, including the `MainWindow`, inherit from the abstract `UiPart` class. Below is a table describing each UI component's responsibility.

| Component         | Description                                                                  |
| ----------------- |------------------------------------------------------------------------------|
| `CommandBox`      | Allows users to enter the commands for `Logic` component to execute          |
| `ResultDisplay`   | Shows feedback from command execution                                        |
| `MainPanel`       | Displays tasks that are the result of command execution                      |
| `SidePanel`       | Displays tasks that are due, or events that are starting in the current week |
| `StatusBarFooter` | Displays the time when the app has be last updated and the storage file path |

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/opus/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component:

* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` that updates the UI when the data changes.
* Responds to events raised from various parts of the App and updates the UI accordingly.

### 2.3. Logic component

Author: [Lam Guang Jun](http://github.com/gjlam95)

Logic provides APIs for the UI to execute the commands given by the user. The API of the logic component can be found at Logic.java.

The class diagram of the Logic Component is given below. `LogicManager` implements the Logic interface and has exactly one `Parser`. `Parser` is responsible for processing the user command and creating instances of Command objects (eg. AddCommand). These will then be executed by the `LogicManager`. New command classes must implement the `Command` class. Each `Command` class produces exactly one `CommandResult`.

<img src="images/LogicClassDiagram.png" width="800"><br>
_Figure 2.3.1 : Structure of the Logic Component_

**API** : [`Logic.java`](../src/main/java/seedu/address/logic/Logic.java)<br>
This section will elaborate on the functions of the `Logic` component; i.e how an input is received from the user and passed through as a command before the final product is ready to be stored and displayed on the UI.

1. `Logic` uses the `Parser` class to process the input provided by the user.
2. This creates a `Command` object according to the command word input (eg. `add` will cause the AddCommand to create an AddCommand object).
3. This is then executed by the `LogicManager`, which will process the command accordingly.
4. The command execution will interact with the `Model` component (eg. adding a task), to create a new task.
4. The result of the command execution is encapsulated as a `CommandResult` object which will be handed back to the `Ui` component and display the relevant results to the user.

Below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
 API call.<br>
<img src="images/DeleteTasksForLogic.png" width="800"><br>
_Figure 2.3.1 : Interactions Inside the Logic Component for the `delete 1` Command_

In this diagram, the `Logic Manager` receives an event to delete the task from index 1 and parses into a `DeleteCommand` that communicates with the Model to perform the deletion. The result is passed back to the `UI` component through `CommandResult`.

### 2.3.1 Sort implementation

The sorting feature in Opus is designed using different attributes comparators implemented from `Comparators<ReadOnlyTask>`. This command allows users to perform their desired sorting according to the parameters given provided by them. The default sorting is given by the command `sort all`.

Below is the Sequence Diagram for interactions within the `Logic` component for the `execute("sort all")`
 API call.<br>
<img src="images/SortTasksForLogic.png" width="800"><br>
_Figure 2.3.2 : Interactions Inside the Logic Component for the `sort all` Command_

In this diagram, the `Logic Manager` receives an event to sort the tasks according to firstly status, start date, end date, and finally priority and parses into a `DeleteCommand` that communicates with the Model to perform the sorting. The result is passed back to the `UI` component through `CommandResult`.

### 2.4. Model component

Author: [Han Lynn](http://github.com/hlynn93)

The `Model` component handles the data related logic and defines the structure of the data. It reacts to the `Logic` requests and changes its own state and its attributes' states accordingly.

<img src="images/ModelClassDiagram.png" width="800"><br>
_Figure 2.4.1 : Structure of the Model Component_

**API** : [`Model.java`](../src/main/java/seedu/address/model/Model.java)

The `Model` component does not depend on other three components and consists of three main objects: `Task`, `Tag` and `UserPref`.
* The `UserPref` object represents the user's preferences.
* The `Task` object stores the attributes of a task which consist of `Name`, `Priority`, `Status`, `Note`, `StartTime` and `EndTime`. It is also linked to the `Tag` object that categorises the existing tasks.

The `Model` component exposes a `UnmodifiableObservableList<ReadOnlyTask>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.

#### 2.4.1 Undo/Redo Implementation

The `undo/redo` feature in Opus is designed based on the momento command pattern. This command pattern design comprises of three components - `momento`, the data object which the rollback operation will be executed upon, `originator` the component that generates the `momento` object and the `momento collector`.

Whenever the data object is modified, the `originator` sends a copy of the current state of the data as a `memento` object to the `momento collector` to keep track of. When the undo command is given, the `momento collector` simply returns the `momento` object representing the previous state of the data.

In Opus, we have:
* `ModelManager` as the `originator`.
* `TaskManagers` as `momento` objects.
* `TaskManagerStateHistory` as the `momento collector`.

`History` contains two lists of `TaskManager`, one for the backwards `undo` operation and another for the forward `redo` operation.

Using the entire `TaskManager` as the `momento` object rather than the individual `Task` attributes simplifies overall design and implementation of this feature. Whenever the `TaskManager` is mutated, `ModelManager` will push a copy of `TaskManager` to `TaskManagerStateHistory`. This approach is robust and resistant to data inconsistency when multiple changes are made by a single command.

Furthermore, this reduces overall coupling and complexity of Opus and improves extensibility. New features or `Task` attributes can be added without having to modify any part of the Undo/Redo implementation. This is possible as that the entire `TaskManager` is captured as a single snapshot, which includes any attribute that is newly added to the `Task` or `Tag` implementation.

### 2.4.2. Sync/Google Task Implementation

The `Sync` component is responsible for handling all sync operations in Opus.

<img src="images/SyncClassDiagram.png" width="400"><br>
_Figure 2.4.2 : Structure of the Sync Component_

The `Sync` component is comprised of two main parts - the individual `SyncService` integration classes and `SyncManager` class. The `SyncManager` class manages all `SyncService` classes as well as delegating sync requests from Model to the corresponding `SyncService`.

All sync integration is required to implement the `SyncService` interface to handle sync requests. This allows for easy extention of other sync integration into Opus without much changes to other components. 

Presently, only Google Task integration is implemented as of *Release v0.5*. However, this implementation has the following limitaions:
1. Supports one way synchronisation from Opus to Google Task.<br>
>Google Task API rejects request calls to insert `Task` with an user-defined `Id`. This makes tracking of individual `Tasks` between Opus and Google Task difficult with the use of an unique identifer.<br>

2. Supports floating tasks and tasks with `EndTime` only.<br>
>Google Task does not include a `StartTime` field as part of their implementation of `Task`. Google Calendar, which supports events, can be considered as a solution in future release.<br>

### 2.5. Storage component

Author: [Shi Yanzhang](http://github.com/mynameisyz)

The `Storage` component reads and writes component state information to the storage files in the hard disk.

<img src="images/StorageClassDiagram.png" width="800"><br>
_Figure 2.5.1 : Structure of the Storage Component_

**API** : [`Storage.java`](../src/main/java/seedu/address/storage/Storage.java)

The `Storage` component listens the `TaskManagerChangedEvent` and
whenever there is a change to the task manager data, the component updates the storage files accordingly. The `Storage` has the following operations:

* Saves `UserPref` objects in JSON format and read it back.
* Saves the Task Manager data in XML format and read it back.

### 2.6. Common classes

Classes used by multiple components can be found in the `seedu.opus.commons` package.

### 2.7. Activity Diagram

Below is the activity diagram to model Opus' workflow.

<img src="images/activity_diagram.png" width="800"/><br/>

## 3. Implementation

### 3.1. Logging

We are using the default `java.util.logging` package for logging. The `LogsCenter` class is used to manage the logging levels
and logging destinations.

* The logging level can be controlled using the `logLevel` setting in the configuration file
  (See [Configuration](#32-configuration))
* The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to
  the specified logging level
* Currently log messages are output through: `Console` and to a `.log` file.

**Logging Levels**

* `SEVERE` : Critical problem detected which may possibly cause the termination of the application
* `WARNING` : Application can still continue to run, but proceed with caution
* `INFO` : Information showing noteworthy actions by the App
* `FINE` : Details that are not usually noteworthy but may be useful in debugging
  e.g. print the actual list instead of just its size

### 3.2. Configuration

Certain properties of the application can be cofigured (e.g App name, logging level) through the configuration file
(default: `config.json`).


## 4. Code Quality and Testing

### 4.1. Code Quality

In Opus, we aspire to attain high quality coding standards by stricting following the Java coding standard and applying the principles of defensive programming. Maintaining a good coding standard enhances readability of the code and allows other developers to quickly grasp and understand Opus' implementation. The Java coding standard can be found [here](https://oss-generic.github.io/process/codingStandards/CodingStandard-Java.html).

Defensive prgramming principles and techniques enable the developer to handle unexpected situations that may cause a program or a routine to stop working. Some examples of defensive programming are:
* Using Assertions to check validity of arguments before passing them into functions.
* Throwing Excpetions when encountering unexpected events.
* Enforcing 1-to-1 associations
A good write up on defensive programming can be found [here](http://www.comp.nus.edu.sg/~cs2103/AY1617S2/files/handouts/%5bL7P2%5d%20Putting%20up%20defenses%20to%20protect%20our%20code.pdf).

### 4.2. Testing

Tests can be found in the `./src/test/java` folder.

**Using JUnit test in Eclipse**:

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
      e.g. `seedu.opus.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units
     (those code units are assumed to be working).<br>
      e.g. `seedu.opus.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as
      how the are connected together.<br>
      e.g. `seedu.opus.logic.LogicManagerTest`

#### Headless GUI Testing
Thanks to the [TestFX](https://github.com/TestFX/TestFX) library we use,
 our GUI tests can be run in the _headless_ mode.
 In the headless mode, GUI tests do not show up on the screen.
 That means the developer can do other things on the Computer while the tests are running.<br>
 See [UsingGradle.md](UsingGradle.md#running-tests) to learn how to run tests in headless mode.

### 4.3. Improving test coverage using Coveralls
>Coveralls is a code analytic tool that tracks and analyses test coverage of a project. We have integrated Coveralls into our project to keep track and improve upon the test coverage of the code to deliver a robust and throughly-tested product.

 The Coveralls dashboard can be accessed [here](https://coveralls.io/github/CS2103JAN2017-W15-B3/main?branch=master).
 <br><img src="images/coveralls/coveralls_1.png" width="600"><br>

 Upon every pull request, Coveralls will run an analysis of the pushed code and produce a report. To view the coverage report, nagivate the to pull request in Github.

 1. Click on the Coveralls badge in the pull request to open the coverage report as follows:
 <br><img src="images/coveralls/coveralls_2.png" width="600"><br><br>
 2. Scroll the report and select any file to view detailed analysis of the code.
 <br><img src="images/coveralls/coveralls_3.png" width="600"><br><br>
 Lines that are highlighted in red indicate that there is no test coverage for the code branch.
 <br><img src="images/coveralls/coveralls_4.png" width="600"><br><br>

    To maintain the code quality of the product, we strongly insist that all possible code branches are well covered with test cases.

### 4.4. Troubleshooting tests

#### 4.4.1. Tests fail because NullPointException when AssertionError is expected
This is because Assertions are not enabled for JUnit tests.<br>
1. Enable assertions in JUnit tests as described [here](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option).
2. Delete run configurations created when you ran tests earlier.

## 5. Dev Ops

### 5.1. Build Automation

See [UsingGradle.md](UsingGradle.md#using-gradle) to learn how to use Gradle for build automation.

### 5.2. Continuous Integration

We use [Travis CI](https://travis-ci.org/) and [AppVeyor](https://www.appveyor.com/) to perform _Continuous Integration_ on our projects.
See [UsingTravis.md](UsingTravis.md) and [UsingAppVeyor.md](UsingAppVeyor.md) for more details.

### 5.3. Publishing Documentation

See [UsingGithubPages.md](UsingGithubPages.md) to learn how to use GitHub Pages to publish documentation to the
project site.

### 5.4. Making a Release

 1. Generate a JAR file [using Gradle](UsingGradle.md#creating-the-jar-file).
 2. Tag the repo with the version number. e.g. `v0.1`
 3. [Create a new release using GitHub](https://help.github.com/articles/creating-releases/)
    and upload the JAR file you created.

### 5.5. Converting Documentation to PDF format

We use [Google Chrome](https://www.google.com/chrome/browser/desktop/) to convert documentation into PDF format,
as Chrome's PDF engine preserves hyperlinks used in webpages.

 1. Make sure you have set up GitHub Pages as described in [UsingGithubPages.md](UsingGithubPages.md#setting-up).
 2. Using Chrome, go to the [GitHub Pages version](UsingGithubPages.md#viewing-the-project-site) of the
    documentation file. <br>
    e.g. For [UserGuide.md](UserGuide.md), the URL will be `https://<your-username-or-organization-name>.github.io/addressbook-level4/docs/UserGuide.html`.
 3. Click on the `Print` option in Chrome's menu.
 4. Set the destination to `Save as PDF`, then click `Save` to save a copy of the file in PDF format. <br>
    For best results, use the settings indicated in the screenshot below. <br>
    <img src="images/chrome_save_as_pdf.png" width="300"><br>
    _Figure 5.4.1 : Saving documentation as PDF files in Chrome_

### 5.6. Managing Dependencies and External Libraries

In Opus, Gradle is used to managed all dependencies and external libraries. The external libraries used in this project are:
* [ControlsFx](http://fxexperience.com/controlsfx/) `8.40.11`
* [Guava](https://github.com/google/guava) `19.0`
* [Jackson](https://github.com/FasterXML/jackson) `2.7.0`
* [jUnit](http://junit.org/junit4/) `4.12`
* [TestFx](https://github.com/TestFX/TestFX) `4.0.5-alpha`
* [Monocle](https://wiki.openjdk.java.net/display/OpenJFX/Monocle) `1.8.0_20`
* [Natty Date Parser](http://natty.joestelmach.com/) `0.6`
* [Google Tasks API](https://developers.google.com/google-apps/tasks/) `1.22.0`
* [Mockito](http://site.mockito.org/) `2.7.22`

## 6. Version Control

### 6.1. Git + GitHub

Opus version control depends highly on Git, and is [hosted on GitHub](https://github.com/CS2103JAN2017-W15-B3/main). We heavily utilised [GitHub's issue tracker](https://github.com/CS2103JAN2017-W15-B3/main/issues) to manage each release's deliverables.

#### 6.1.1. Labels

There are several predefined labels included in our issue tracker:

1. `docs`: assigned if the issue/pull request is related to documentation of the project.
2. `priority.high/medium/low`: assigned to indicate how important the issue is to the project.
3. `status.complete/ongoing`: assigned to indicate which PR/tasks are work in progress, and which PRs are completed and ready for review.
4. `type.bug`: assigned to issues that address a bug in the application.
5. `type.enhancement`: assigned to issues that improve either the code quality or user experience of the application.
6. `type.epic`: assigned to issues that comprise of a very large feature to be implemented.
7. `type.task`: assigned to issues that are misc chores/tasks to be completed.
8. `ui/storage/logic/model`: assigned to issues that are specific to a certain component of the project. This allows for the developer in charge of the component to provide more detailed feedback.
9. `tests`: assigned to issues that are related to unit/GUI testing of the code.

Assigning correct labels to your issue will result in faster response from the developer that understands your problem best.

### 6.2. Branching and Workflow

Branch name should always be `kebab-case`, i.e. all small letters with hyphens-replacing-spaces. The branch name should be descriptive of what you are trying to implement/achieve with that branch. **Avoid naming branches after your own name.**

All PRs are to be made to `development` branch, except for special/unique scenarios. Both `master` and `development` branches are **PROTECTED**, i.e. you cannot push directly to the branch. On each release, we will make a PR to the `master` branch. It will only be merged after a sanity check and thumbs up from each team member.

We follow the [GitHub Flow](https://guides.github.com/introduction/flow/). Each pull request (PR) has to be reviewed by the code QA (@gjlam95) and another team member before it can be approved for merging. This is enforced to ensure that code quality is kept up to standard and get all team members on the same page with regards to implementation details.

Code review also encourages learning from each other's mistakes or strengths, benefiting the overall productivity and competency of the team over time.

### 6.3. Commit Messages

The seven rules of writing a great Git commit message (as seen [here](https://chris.beams.io/posts/git-commit/)):

1. Separate subject from body with a blank line
2. Limit the subject line to 50 characters
3. Capitalize the subject line
4. Do not end the subject line with a period
5. Use the imperative mood in the subject line
6. Wrap the body at 72 characters
7. Use the body to explain *what* and *why* vs. *how*

Please follow these strictly when writing commit messages, it makes life easier for all developers who are trying to debug your code by looking through the commit history.

## Appendix A : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 tasks without a noticeable sluggishness in performance for typical usage.
3. Should allow a user with above average typing speed for regular English text (i.e. not code, not system admin commands)
    to accomplish tasks faster using commands than using the mouse.
4. Should include well-written guides for users and developers
5. Should follow OOP principles
6. Should come with automated tests
7. Should handle exceptions gracefully
8. Should not require any other external software to work
9. Should be open source
10. Should be easily extented by another developer
11. Should be easily maintained by another developer
12. Should contain sufficient documentation for any developer to take over the development
13. Should only use open-source, free-to-use external libraries

## Appendix B : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

## Appendix C : Product Survey

[**Simpliday**](http://www.simpliday.com/)

Author: Yanzhang

Pros:

* Excellent user interface design and user experience
* Intuitive design for users to pick up easily
* Easy to organize tasks reminiscent of a physical organiser
* Convenient integration with cloud-based services (Google sign-in)
* Advanced features for tasks including geo-tagging, sharing through social media, emails

Cons:

* Certain features are not relevant to certain types of user
* Only available on App Store (iOS)

[**Pivotal Tracker**](https://www.pivotaltracker.com/)

Author: Han Lynn

Pros:

* Dashboard view for entire project
* Tasks tracking & prioritizing
* Release and milestone management
* Dedicated apps are available on major platforms
* Open APIs for developers to integrate

Cons:

* Not intuitive enough that it requires a few tutorials to fully understand the controls
* Very specific to software engineering projects

[**Trello**](https://trello.com/)

Author: Xu Bili

Pros:

* Intuitive UI that needs little explaining of how to use it
* Stores information on the cloud, accessible from any device
* Collaborative, *kanban* can be shared with other people
* Integrates with GitHub and other useful services

Cons:

* Specific to technical tasks
* Tasks have to be small enough to be broken down into cards
* Gets really messy when the project scales up
