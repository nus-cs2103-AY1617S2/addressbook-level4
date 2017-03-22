# Werkbook - User Guide

By : `Team W15B2`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Welcome](#1-welcome)
2. [Getting Started](#2-getting-started)
    1. [Installation](#i-installation)
    2. [Starting Werkbook](#ii-starting-werkbook)
    3. [Interface Introduction](#iii-interface-introduction)
    4. [Quick Start](#iv-quick-start)
3. [User Interface](#3-user-interface)
4. [Features](#4-features)
    1. [Task Management](#i-task-management)
        1. [Add task](#a-add-task)
        2. [List task](#b-list-task)
        3. [Edit task](#c-edit-task)
        4. [Find task](#d-find-task)
        5. [Mark task](#e-mark-task)
        6. [Delete task](#f-delete-task)
        7. [Clear all tasks](#g-clear-all-tasks)
    2. [Program Usage](#ii-program-usage)
        1. [Help](#a-help)
        2. [Undo/Redo](#b-undo-redo)
        3. [Change save location](#c-change-save-location)
        4. [Exit program](#d-exit-program)
5. [FAQ](#5-faq)
6. [Commands Cheatsheet](#6-commands-cheatsheet)

## 1. Welcome

Werkbook is a task manager for you to manage their schedules and tasks via a command-line interface.
With just a single line of command, you can add, manipulate, and organize tasks in a hassle-free manner! Werkbook is a simple, yet fast and powerful tool that will help you increase your productivity.

Let Werkbook werk your work!

## 2. Getting Started

### i. Installation
Ensure you have Java version `1.8.0_60` or later installed in your Computer.
> Having any Java 8 version is not enough.<br>
> This app will not work with earlier versions of Java 8.

Download the latest `werkbook.jar` from the [Releases](../../../releases) tab as shown in *Figure 1* below.

<img src="images/userguide-figure-1.jpg" width="600"><br>
*Figure 1: Latest release*

Copy the file to the folder you want to use as the home folder for your Werkbook.

### ii. Starting Werkbook
To start Werkbook, simply double click on `werkbook.jar`.

<img src="images/userguide-figure-2.jpg" width="100"><br>
*Figure 2: Launching werkbook.jar*

### iii. Interface Introduction
After launching Werkbook, you will be greeted with the main interface.

<img src="images/userguide-figure-3.png" width="600"><br>
*Figure 3: Werkbook's main interface*

You can find the **Command Box** at the top of the interface, this is where you type your commands!

The **Results Panel** is located below the **Command Box**, and it tells you the result of your command.

All of your tasks can be found in the **Task Panel**, it is initially empty until you fill it up with tasks.

### iv. Quick Start
Here's a quick guide to get you started with Werkbook.

**Feeling lost?**

Typing **`help`** in the **Command Box** will bring up the help window with a list of all of the available commands.

**Adding your first task**

Let's start by adding a new task to Werkbook. Perhaps you want to remind yourself to get the groceries, you can type the following command in the **Command Box**:

`> add Get Groceries`

**Making changes**

Say you want it done by 5th March 8:30pm, you can edit the task by using the following command:

`> edit 1 by 05/03/2017 2030`

**Completing a task**

Once you've finished getting the groceries, it's time to mark it as complete by typing the following command:

`> mark 1`

**Ready to go!**

Now you're all set to start managing your tasks! Feel free to refer to our [Features](#4-features) section below for details of each command.

## 3. User Interface

<img src="images/annotated-ui.png" width="600"><br>
1. Menu bar
2. Command box
 > Commands are entered here
3. Output box
 > Results of commands are displayed here
4. Task cards listing
 > Tasks retrieved are listed here
5. Selected card display
 > Additional details about the selected task is displayed here
6. Last saved status
 > Time of when the tasks are saved is shown here
7. Save location status
 > Current save location is shown here

## 4. Features

**Command Format**

* Words in `UPPER_CASE` are the parameters.
* Items in `SQUARE_BRACKETS` are optional.
* Items with `...` after them can have multiple instances.
* Parameters can be in any order.

### i. Task Management

#### a. Add task

Once you receive a new task, you can add it to Werkbook using the `add` command. You can also add in extra information by surrounding them with round brackets.<br>
Format: `add NAME [(DESCRIPTION)]`

Example: `add Get the groceries (Milk, eggs, and cheese)`

But if you need to get it done by a certain date, use the `by` prefix to let Werkbook know!<br>
Format: `add NAME [(DESCRIPTION)] [by END_DATETIME]`

Example: `add Get the groceries (Milk, eggs, and cheese) by 10/03/2017 1400`

And if you plan to get it done in a particular timespan, you can specify the duration by using `from` and `to`!<br> 
Format: `add NAME [(DESCRIPTION)] [from START_DATETIME] [to END_DATETIME]`

Example: `add Get the groceries (Milk, eggs, and cheese) from 10/03/2017 1400 to 10/03/2017 1600`

> * Date and time must be formatted as DD/MM/YYYY HHMM<br>
> * A task must have a name, the description is optional<br>
> * The task can also optionally have a End Date/Time, or both a Start Date/Time and End Date/Time<br>

#### b. List task

If you want to see a list of all the tasks you have added, you can use the `list` command.<br>
Format: `list`

#### c. Edit task

Perhaps the deadline for your project has been pushed back, or maybe you want to change the details of a task that you have already created. You can use the `edit` command to change the description and start/end times of your existing tasks.<br>
Format: `edit INDEX [NAME] [(DESCRIPTION)] [from START_DATETIME] [to/by END_DATETIME]`

Examples:
* `edit 1 by 01/01/2018 2359`<br>
  Edits the deadline of the 1st task to be `01/01/2018 2359`.<br>
* `edit 2 (Help to finish up essay)`<br>
  Edits the description of the 2nd task to be `Help to finish up essay`<br>

To remove the start/end date entirely, simply add the corresponding prefix without specifying a new date.

Example:
* `edit 3 by`<br>
  Removes the deadline from task 3.<br>

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.



#### d. Find task

Too many tasks? Don't worry! Simply use the `find` command to bring up a list of tasks containing the specified keyword in the name.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

Example:
* `find Milk`<br>
  Retrieves the task `Get the Milk` but not `Get the milk`

> * The search is case sensitive. e.g `milk` will not match `Milk`
> * The order of the keywords does not matter. e.g. `Apple Juice` will match `Juice Apple`
> * Only the name is searched.
> * Only full words will be matched e.g. `Cake` will not match `Cakes`
> * tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Milk` will match `Get the Milk`

#### e. Mark task

Finally done with that long and tedious project? Mark it as `Completed` using the `mark` command!<br>
Format: `mark INDEX`

Example:
* `mark 1`<br>
  Updates the task status to `Complete` if it is `Incomplete`. <br>

Oops! Missed out on some project requirements? Just use the `mark` command again to mark the task as `Incomplete` again.<br>
* `mark 2`<br>
  Updates the task status to `Incomplete` if it is `Complete`. <br>

> * Marks the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...

#### f. Delete task

Accidentally added duplicate tasks? Use the `delete` command to remove the specified task from Werkbook.<br>
Format: `delete INDEX`

Examples:
>`list`
>`delete 2`
* Deletes the 2nd task in the task list.
>`find Betsy`
>`delete 1`
* Deletes the 1st task in the results of the `find` command.

> * Deletes the task at the specified `INDEX`. <br>
> * The index refers to the index number shown in the most recent listing.<br>
> * The index **must be a positive integer** 1, 2, 3, ...

#### g. Clear all tasks

Seeking a fresh start? Use the `clear` command to clear all the tasks in Werkbook and start anew!<br>
Format: `clear`

### ii. Program Usage

#### a. Help

Displays the help sheet.

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

#### b. Undo/Redo

Undo the last command(s) executed.

Format: `undo [AMOUNT]`

> Undo the last command executed.
> Repeated use of undo will undo the previous command from the last command undone

Examples:

>`delete 2`
>`undo`
  Undo the deletion of the 2nd task in the task list.

>`delete 2`
>`delete 3`
>`undo 2`
  Undo the deletion of the 2nd and 3rd task in the task list.

Redo the last undo command(s) executed.

Format: `undo [AMOUNT]`

> Redo the last undo command executed.

Examples:


>`delete 2`
>`undo`
>`redo`
  The final action would be the deletion of the 2nd task in the task list.

#### c. Change save location

Saves all changes to disk. <br>
Format: `save SAVE_LOCATION`

Changes where tasks data and user config are saved to specified `SAVE_LOCATION`.

#### d. Exit program

Exits the program.<br>
Format: `exit`

## 5. FAQ

**Q**: How do I transfer my data to another Computer?

> **A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous task list folder.


**Q**: How do I save my tasks?

> **A**: You don't need to! Your tasks are automatically saved after every command!

## 6. Commands Cheatsheet

Command | Purpose | Format
--- | --- | ---
`add` | Add a task | `add NAME [(DESCRIPTION)] [from START_DATETIME] [to/by END_DATETIME]`
`list` | List all tasks | `list`
`edit` | Edit specified task | `edit INDEX [NAME] [(DESCRIPTION)] [from START_DATETIME] [to/by END_DATETIME]`
`find` | Find tasks based on specified keywords | `find KEYWORD [MORE_KEYWORD]`
`mark` | Mark specified task as complete | `mark INDEX`
`delete` | Delete specified task | `delete INDEX`
`clear` | Clear all tasks | `clear`
`help` | Display help page | `help`
`undo` | Undo command(s) | `undo [AMOUNT]`
`redo` | Redo command(s) | `redo [AMOUNT]`
`save` | Change save location | `save SAVE_LOCATION`
`exit` | Exit the program | `exit`
