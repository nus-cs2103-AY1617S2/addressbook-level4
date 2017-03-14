# Developer Guide

* [Setting Up](#setting-up)
    * [Prerequisites](#prerequisites)
    * [Importing the Project into Eclipse](#importing-the-project-into-eclipse)
* [Design](#design)
    * [Architecture](#architecture)
    * [User Interface (UI)](#user-interface-ui)
    * [Logic](#logic)
    * [Model](#model)
    * [Storage](#storage)
    * [Common classes](#common-classes)
* [Target Users](#target-users)
* [Testing](#testing)
* [Dev Ops](#dev-ops)
    * [Build Automation](#build-automation)
    * [Continuous Integration](#continuous-integration)
    * [Publishing Documentation](#publishing-documentation)
    * [Making a Release](#making-a-release)
    * [Converting Documentation to pdf format](#converting-documentation-to-pdf-format)
    * [Managing Dependencies](#managing-dependencies)
* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b--use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Glossary](#appendix-d--glossary)
* [Appendix E: Product Surveys](#appendix-e--product-surveys)
    * [Trello](#trello)
    * [Sticky Notes](#sticky-notes)
    * [Wunderlist](#wunderlist)
    * [Nirvana for GTD](#nirvana-for-gtd)
<br>

## Setting up
---

### Prerequisites
* [**Java Development Kit (JDK) 8**](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or later
* [**Eclipse**](http://www.eclipse.org/downloads/eclipse-packages/) IDE
* [**e(fx)clipse Plug-in**](https://wiki.eclipse.org/Efxclipse/Tutorials/AddingE(fx)clipse_to_eclipse_) for Eclipse
* [**Buildship Gradle Integration Plug-in**](http://marketplace.eclipse.org/content/buildship-gradle-integration) (from the Eclipse Marketplace)
* [**Checkstyle Plug-in**](http://eclipse-cs.sourceforge.net/#!/install) (from the Eclipse Marketplace)

<br>

### Importing the project into Eclipse
1. From [this repository](https://github.com/CS2103JAN2017-W14-B4/main), ___fork___ and ___clone___ it to your computer.

2. Open your ___Eclipse IDE___. 
   > Ensure that you have installed the [e(fx)clipse](https://wiki.eclipse.org/Efxclipse/Tutorials/AddingE(fx)clipse_to_eclipse_) and [Buildship Gradle Integration](http://marketplace.eclipse.org/content/buildship-gradle-integration) plugins as given in the prerequisites above.

3. Click ___`File`___ > ___`Import`___ .

4. Click ___`General`___ > ___`Existing Projects into Workspace`___ > ___`Next`___ .

5. Click ___`Browse`___ , then ___locate the project's directory___.

6. Click ___`Finish`___ .

<br>

## Design
---
### Architecture

<br><center><img src="images/Architecture.png" width="600"></center><br>
<center>Figure 1 : Architecture Diagram</center><br>
The **_Architecture Diagram_** given above explains the high-level design of ezDo.
Given below is a quick overview of each component.

> * The `.pptx` files used to create diagrams in this document can be found in the [diagrams](diagrams/) folder.
> 
> * To update a diagram, modify the diagram in the pptx file, select the objects of the diagram, and choose `Save as picture`.

<br>
`Main` has only one class called [`MainApp`](../src/main/java/seedu/ezdo/MainApp.java). It is responsible for,

* At ezDo launch : Initializing the components in the correct sequence, and connecting them up with each other.

* At shut down : Shutting down the components and invoking cleanup methods where necessary.

<br>

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.
Two of those classes play important roles at the architecture level.

* `EventsCenter` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design).
  
* `LogsCenter` : Used by many classes to write log messages to ezDo's log file.

<br>

The rest of ezDo consists of four components.

* [**`UI`**](#ui-component) : The UI of ezDo.
* [**`Logic`**](#logic-component) : The command executor.
* [**`Model`**](#model-component) : Holds the data of ezDo in-memory.
* [**`Storage`**](#storage-component) : Reads data from, and writes data to, the hard disk.

<br>

Each of the four components

* Defines its _API_ in an `interface` with the same name as the component.
* Exposes its functionality using a `{Component Name}Manager` class.

<br>

For example, the **`Logic`** component (see the class diagram given below) defines its API in the `Logic.java` interface and exposes its functionality using the `LogicManager.java` class.

<br><center><img src="images/LogicClassDiagram.png" width="800"></center><br>
<center>Figure 2 : Class Diagram of the Logic Component</center><br>

#### Events-Driven nature of the design

The _sequence diagram_ below shows how the components interact for the scenario where the user issues the command `kill 1`.

<br><center><img src="images/SDforDeletePerson.png" width="800"></center><br>
<center>Figure 3 : Component Interactions for `kill 1` Command (Part 1)</center><br>

>Note how the **`Model`** simply raises a `EzDoChangedEvent` when ezDo data is changed, instead of asking the **`Storage`** to save the updates to the hard disk.

<br>

The diagram below shows how the `EventsCenter` reacts to that event, which eventually results in the updates being saved to the hard disk and the status bar of the UI being updated to reflect the 'Last Updated' time. <br>

<br><center><img src="images/SDforDeletePersonEventHandling.png" width="800"></center><br>
<center>Figure 4 : Component Interactions for `kill 1` Command (Part 2)_</center><br>

> Note how the event is propagated through the `EventsCenter` to the **`Storage`** and **`UI`** without **`Model`** having to be coupled to either of them. This is an example of how this Event Driven approach helps us reduce direct coupling between components.

<br>

The sections below give more details on each component.
<br>

---

### UI

<br><center><img src="images/UiClassDiagram.png" width="800"></center><br>
<center>Figure 5 : Structure of the UI Component</center><br>

**API** : [`Ui.java`](../src/main/java/seedu/ezdo/ui/Ui.java)
<br>

The **`UI`** consists of a `MainWindow` that consists of several parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter`, `BrowserPanel`. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The **`UI`** component uses `JavaFX UI` framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/ezdo/ui/MainWindow.java) is specified in [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml).
 
 <br>

The **`UI`** component,

* Executes user commands using the **`Logic`** component.

* Binds itself to some data in the **`Model`** so that the UI can auto-update when data in the **`Model`** changes.

* Responds to events raised from various parts of ezDo and updates the UI accordingly.

<br>

---

### Logic

<br><center><img src="images/LogicClassDiagram.png" width="800"></center>
<br><center>Figure 6 : Structure of the Logic Component</center><br>

**API** : [`Logic.java`](../src/main/java/seedu/ezdo/logic/Logic.java)
<br>

* Uses the `Parser` class to parse the user command.

* A `Command` object is executed by the `LogicManager`.

* The command execution can affect the **`Model`** (e.g. adding a task) and/or raise events.

* The result of the command execution is encapsulated as a  `CommandResult` object which is passed back to the **`UI`**.

The _sequence diagram_ (in Figure 7) shows the interactions within the `Logic` component for the _`execute("kill 1")`_ API call.

<br><center><img src="images/DeletePersonSdForLogic.png" width="800"></center>
<br><center>Figure 7: Interactions Inside the Logic Component</center><br>

---

### Model 


<center><img src="images/ModelClassDiagram.png" width="800"></center><br>
<center>Figure 8: Structure of the Model Component</center><br>

**API** : [`Model.java`](../src/main/java/seedu/ezdo/model/Model.java)

* Stores a `UserPref` object that represents the user's preferences.

* Stores `ezDo` data which includes `Tag` list and `Task` list.

* Exposes a `UnmodifiableObservableList<ReadOnlyTask>` object that can only be 'observed' i.e the **`UI`** can be bound to this list so that the displayed list on the interface displays changes when the data in the list changes.
<br>

---

### Storage 

<center><img src="images/StorageClassDiagram.png" width="800"></center><br>
<center>Figure 9: Structure of the Storage Component</center><br>

**API** : [`Storage.java`](../src/main/java/seedu/ezdo/storage/Storage.java)


* Can save `UserPref` objects in `.json` format and read it back.

* Can save ezDo data in `.xml` format and read it back.
<br>

---
### Common classes

Classes used by multiple components are in the `seedu.ezdo.commons` package.
<br>

---

<br>

## Target Users

Similar to [Jim](http://www.comp.nus.edu.sg/~cs2103/AY1617S2/contents/handbook.html#handbook-project), our main target users have the following characteristics :
* They are office workers and [power users](http://www.dictionary.com/browse/power-user).
* They are willing to use a task manager in advanced and complicated ways.
* They have many tasks at hand with varying deadlines.
* They prefer not to use a mouse.

<br>

## Testing
---
All tests can be found in the `./src/test/java` folder.

### Types of Tests:

1. **GUI Tests** - These are _system tests_ that test the entire application by simulating user actions on the GUI. These are in the `guitests` package.

2. **Non-GUI Tests** - These are tests not involving the GUI. They include :
   * ___Unit tests___ 
      These tests target the lowest level methods/classes. 
      e.g. `seedu.ezdo.commons.UrlUtilTest`
      
   * ___Integration tests___
      These tests check the integration of multiple code units (i.e. those code units are assumed to be working).
      e.g. `seedu.ezdo.storage.StorageManagerTest`
      
   * ___Hybrids of unit and integration tests___
      These tests check the multiple code units (i.e. how they are connected together).
      e.g. `seedu.ezdo.logic.LogicManagerTest`

<br>

### In Eclipse:

* To run ___all tests___, right-click on the `src/test/java` folder and choose `Run as` > `JUnit Test`.

* To run ___a subset of tests___, you can right-click on a test package, test class, or a test and choose `Run as` > `JUnit Test`.

<br>

### Headless GUI Testing

With compliments to the [TestFX](https://github.com/TestFX/TestFX) library we use, our GUI tests can be run in the ___headless_ mode__.
 In the headless mode, GUI tests do not show up on the screen.
 That means the developer can do other things on the computer while the tests are running.<br>
See [UsingGradle.md](UsingGradle.md#running-tests) to learn how to run tests in headless mode.
 
<br>

## Dev Ops
---
### Build Automation
See [UsingGradle.md](UsingGradle.md) to learn how to use Gradle for build automation.
<br>
### Continuous Integration
We use [Travis CI](https://travis-ci.org/) and [AppVeyor](https://www.appveyor.com/) to perform _Continuous Integration_ on our projects.
See [UsingTravis.md](UsingTravis.md) and [UsingAppVeyor.md](UsingAppVeyor.md) for more details.
<br>
### Publishing Documentation
See [UsingGithubPages.md](UsingGithubPages.md) to learn how to use GitHub Pages to publish documentation to the project site.

<br>

### Making a Release
#### To create a new release,

 1. ___Generate a JAR file___ [using Gradle](UsingGradle.md#creating-the-jar-file).
 
 2. ___Tag the repo___ with the version number. _(e.g. `v0.1`)_
 
 3. [___Create a new release___ using GitHub](https://help.github.com/articles/creating-releases/) and ___upload the JAR file you created___.

<br>

### Converting Documentation to PDF format
We use [Google Chrome](https://www.google.com/chrome/browser/desktop/) for converting documentation to PDF format, as Chrome's PDF engine preserves hyperlinks used in webpages.

#### To convert the project documentation files to PDF format,

 1. Make sure you have set up GitHub Pages as described in [UsingGithubPages.md](UsingGithubPages.md#setting-up).
 
 2. Using Chrome, ___go to the [GitHub Pages version](UsingGithubPages.md#viewing-the-project-site) of the documentation file___.
    e.g. For [UserGuide.md](UserGuide.md), the URL will be `https://cs2103jan2017-w14-b4.github.io/main/docs/UserGuide.html`.
 3. ___Click on the `Print` option___ in Chrome's menu.
 4. ___Set the destination to `Save as PDF`___ , then ___click `Save`___ to save a copy of the file in PDF format. <br>
    For best results, use the settings indicated in the screenshot below.<br>
    
    <center><img src="images/chrome_save_as_pdf.png" width="300"></center><br>
<center>Figure 10: Saving Documentation as PDF Files in Chrome</center><br>

<br>

### Managing Dependencies

A project often depends on third-party libraries. For example, ezDo depends on the [Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Gradle can automate downloading the dependencies, which is better than these alternatives. <br>
> 1. Include those libraries in the repo (this bloats the repo size). <br>
> 2. Require developers to download those libraries manually (this creates extra work for developers). <br>

<br>

## Appendix A : User Stories
---
Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`

Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | add a floating task | add tasks that need to be done ‘some-day’
`* * *` | user | add a task with deadlines | know when I must finish the task
`* * *` | user | view all the tasks by the order I added them | be more organised
`* * *` | user | delete a task | get rid of tasks I no longer want to track
`* * *` | user | check a task as done (not deleted) | know I’ve finished it
`* * *` | user | view a history of all the tasks I’ve done | check what I’ve done
`* * *` | new user | view the help guide | know more about the different commands there are and how to use them
`* * *` | user | edit my tasks | update them
`* * *` | user | undo my last command | undo mistakes/typos
`* * *` | user | add events that have a start and end time | know what my day will be like
`* * *` | user | search for tasks by deadline/description | see what task I entered
`* * *` | user | specify where to keep my tasks (file and folder) | move them around if need be
`* * *` | power user | set tags to tasks | group related tasks by tags
`* * *` | power user | search by tags | know everything I have to do related to that tag
`* *` | user | sort tasks by deadline | see quickly what task has the closest deadline
`* *` | user | list tasks that don't currently have deadlines | add deadlines to tasks without deadlines
`* *` | power user | use shortened versions of commands | save time when inputting tasks
`* *` | user | add different priorities to my tasks | know which tasks need to be done first
`* *` | user | list my tasks in priority order | see which tasks have different priorities
`* *` | user | undo multiple commands | remove changes done by a few wrong commands
`* *` | user | enter recurring tasks | not have to enter the same tasks repeatedly
`* *` | power user | map standard commands to my preferred shortcut commands | be familiar with my own  modified commands
`* *` | user | make tasks that have very close deadlines to appear as special priority | be reminded to complete them
`* *` | user | set notifications for deadlines for my tasks | get reminded to do it.
`* *` | power user | set roughly how much time it requires to finish a task | know how long I need to start and finish a task and not leave it halfway
`* *` | power user | tell the program how much time I have right now | the program can assign me a task to complete in that time
`* *` | user | set tasks that are currently underway | be aware of what are the tasks I’m working on right now
`* *` | user | redo my last action | reverse accidental undo commandss
`*` | user with many tasks in the to do list | sort tasks by name | locate a task easily
`*` | user | list the tasks that are due soon | minimize chance of me missing my deadline
`*` | paranoid user | set password | protect my privacy
`*` | power user | sync tasks and events to Google Calendar | see my tasks and events alongside Google Calendar

<br>

## Appendix B : Use Cases
---
___(For all use cases below, the **System** is `ezDo` and the **Actor** is the `user`, unless specified otherwise)___

#### Use case: Adding a task

**MSS**

1. User enters command to add task, along with relevant arguments.
2. ezDo adds the task and returns a confirmation message.
Use case ends.

**Extensions**

1a. The user enters an invalid command.

> 1a1. ezDo shows an error message and prompts the user to retry.
  Use case resumes at step 1.

#### Use case: Updating a task

**MSS**

1. User specifies task to update by index along with relevant fields and corresponding information.
2. ezDo updates the task and returns a confirmation message.
Use case ends.

**Extensions**

1a. The user enters an invalid command.

> 1a1. ezDo shows an error message and prompts the user to retry.
Use case resumes at step 1.

1b. The indexed task does not exist.

> 1b1. ezDo shows an error message and prompts the user to select another index.
Use case resumes at step 1.

#### Use case: Deleting a task

**MSS**

1. User enters index of task to delete.
2. ezDo deletes the task and returns a  confirmation message.
Use case ends.

**Extensions**

1a. The indexed task does not exist.

> 1a1. ezDo shows an error message and prompts the user to select another index.
Use case resumes at step 1.

#### Use case: Marking a task as done

**MSS**

1. User enters index of undone task to mark as done.
2. ezDo marks the specified undone task as done and returns the list of done task and a confirmation message.
Use case ends.

**Extensions**

1a. The indexed task does not exist.

> 1a1. ezDo shows an error message and prompts the user to select another index.
Use case resumes at step 1.

#### Use case: Specifying save location

**MSS**

1. User enters command to change save location, along with the new file path.
2. ezDo updates the save location to the given path and returns a confirmation message.
Use case ends.

**Extensions**

1a. The folder does not exist.

> 1a1. ezDo shows an error message and prompts the user to choose another path.
Use case resumes at step 1.

1b. The user enters an invalid command.

> 1b1. ezDo shows an error message and prompts the user to try again.
Use case resumes at step 1.

<br>

## Appendix C : Non Functional Requirements
---
1. Should be intuitive so no need to waste time learning.
2. Should come with a very high level of automated testing.
3. Should have user-friendly commands.
4. Should be able to store at least 1000 tasks.

<br>

## Appendix D : Glossary
---
##### Mainstream OS

> Windows, Linux, Unix, OS X

##### ez™

> Your life, once you start using ezDo™

<br>

## Appendix E : Product Surveys
---
### Trello
#### Pros
- Simple to use because the interface relies on many visual cues and pictures
- Can tag tasks with different colours to indicate different priority
- Can tag other Trello users to tasks
- Has all the functions that a task manager should have and can fulfill Jim's personal needs in a task manager

#### Cons
- Need to pay a sum of money monthly to access all features
- Does not sort tasks in deadline order - tasks must be moved manually

#### Verdict - NO
Trello requires most of the work to be done with a mouse (moving of tasks by mouse etc). Moreover, Trello does not sort tasks by deadline or priority. Jim will find it inconvenient if he wishes to view his tasks in a certain order.

### Sticky Notes
#### Pros
- Comes bundled free with every Microsoft PC. No need to buy
- Simple to use, like its real-world counterpart - Post-it notes
- Can rearrange notes on desktop to place at desired position
- Can resize notes for better visibility
- Can change color of sticky notes
- Can change font, font size, and other text options

#### Cons
- Requires keyboard shortcuts to change font and font options
- Can clutter up screen with many sticky notes
- No easy way to differentiate what is most important
- No reminders (Need Windows 10 Cortana - privacy!)

#### Verdict - NO
Sticky notes is not so good for Jim.
Jim follows 'Inbox Zero'. He no longer has to worry about an inbox full of emails, because he gets a desktop full of notes now instead.
Sticky notes require mouse-clicks and Jim prefers typing.
Doesn't show what has to be done by what deadline.

### Wunderlist
#### Pros
- Has all the features expected from a standard to-do list application and more, such as due dates, notes, attachments, reminders and synchronisation
- Has a keyboard shortcut to quickly input tasks (Quick Add)
- Intuitive user experience


#### Cons
- Quick Add still requires mouse input if you wish to specify a due date
- Does not support tagging of tasks


#### Verdict - NO
Wunderlist does not suit Jim's needs.
Though it supports basic task creation with the keyboard alone, it does not allow Jim to record essential details such as the deadlines or priority without using a mouse.

### Nirvana for GTD
#### Pros
- Android and iOS platforms are available for use
- Simple and clean layout
- Creating a task can be done using command prompt entry
- Focus view is available to view tasks or projects that require immediate attention

#### Cons
- Due time cannot be specified for any task
- Reminders cannot be set for any task
- Limited commands (e.g. Unable to delete a task)
- No native apps provided for OS X and Windows platforms

#### Verdict - NO
For Jim, the applications allows him to zoom in to tasks or projects that require immediate action. However, this application is restricting  him to add tasks or projects using the command line interface. He has to use the graphical interface to edit or delete a task which will decrease his productivity and therefore it is not suitable for him.
