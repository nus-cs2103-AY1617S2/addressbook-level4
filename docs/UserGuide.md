# User Guide
<br>

* [About](#about)<br>
* [Quick Start](#quick-start)
    * [Installation Instructions](#installation-instructions)
    * [Launch](#launch)
    * [Visual Introduction](#visual-introduction)<br>
* [Features](#features)<br>
* [Frequently Asked Questions](#frequently-asked-questions)<br>
* [Command Summary](#command-summary)<br>

<br>

## About
ezDo is the eziest™ way to keep track of all your tasks properly. Whether you’re planning a holiday, powering through your day or managing multiple work projects, ezDo is here to help you tick off all your personal and professional to-dos.

For an ez™ life, use ezDo™.

<br><br>

## Quick Start

### Installation Instructions

1. Ensure that you have [Java version `1.8.0_60`](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or later installed in your computer.<br>
2. Download the latest `ezDo.jar` from the [releases](../../../releases) tab (as shown in Figure 1).<br>

<p align="center"><img src="images/Release.png" width="500"></p>

<h5 align="center">Figure 1: Locating ezDo.jar From the Releases tab</h5>

3. Copy the latest `ezDo.jar` to the folder you want to use. This folder will be the home folder for ezDo.<br><br>

<br>

### Launch
To start the application, double-click on `ezDo.jar`. The user interface should appear in a few seconds. A sample view of the user interface is shown in Figure 2.<br>

<p align="center"><img src="images/Ui.png" width="500"></p>

<h5 align="center">Figure 2: Sample View of ezDo</h5>

<br>

### Visual Introduction

Figure 3 illustrates some features that you will find useful when you embark on ezDo:

<p align="center"><img src="images/VisualIntroduction.png" width="500"></p>

<h5 align="center">Figure 3: Visual Introduction of ezDo</h5><br>

1. **Command Box**
Your commands should be typed here.<br><br>
2. **Description Box**
The results of your command executions will be shown here.<br><br>
3. **Task Card**
Each task in ezDo is represented by a taskcard with the following properties:<br><br>
    * Each taskcard has an index on the left.<br><bR>
    * The color on the left indicates its priority. <br>
        * <font color="green">Green</font>: low priority (1)<br>
        * <font color="orange">Orange</font>: medium priority (2)<br>
        * <font color="red">Red</font>: high priority (3)<br><br>
    * Taskcards may have a start/end time.<br><br>
    * Taskcards may have any number of tags associated with it.<br><br>

Now that we are familiar with ezDo's interface, let us get started with the features of ezDo!<br>

<br><br>


## Features

A summary of the commands available on ezDo is shown in Table 1 for your convenience.<br>


| Command |Description                                                     |
|:-------:|----------------------------------------------------------------|
|[add / a](#1-adding-a-task-add--a)               |Adds a task             |
|[edit / e](#2-editing-a-task-edit--e)            |Edits a task            |
|[done / d](#3-marking-a-task-as-done-done--d)    |Marks a task as done    |
|[kill / k](#4-deleting-a-task-kill--k)           |Deletes a task          |
|[sort / s](#5-sorting-a-list-of-tasks-sort--s)   |Sorts the list of tasks |
|[find / f](#6-finding-tasks-find--f)             |Searches for a task     |
|[list / l](#7-listing-all-tasks-list--l)         |Lists the tasks         |
|[undo / u](#8-reverting-the-last-action-undo--u) |Reverts the last action |
|[clear / c](#9-clearing-all-entries-clear--c)    |Deletes all tasks       |
|[save](#10-moving-the-save-file-save)            |Saves ezDo to a path    |
|[quit / q](#11-exiting-the-program-quit--q)      |Quits ezDo              |
|[help / h](#12-viewing-help-help--h)             |Shows the user guide    |


<h5 align="center">Table 1: Summary of Commands in ezDo</h5>

<br>

> #### <u>Quick Tip:</u><br>
> You can type the <u><b>first letter</b></u> of any command instead of tying in full (except **`save`**).
> _For example, you can type **`u`** instead of **`undo`** to revert the last command._
 
<br>

**Things to note:**
* Words in **`UPPER_CASE`** are the parameters.<br><br>
* Parameters in **`SQUARE_BRACKETS`** are optional.<br><br>
* Parameters with **`...`** after them can have multiple instances (separated by a white space).<br>

<br>


### 1. Adding a task: `add / a`
---
 _**Adds a task to ezDo.**_<br>

#### Format:

**`add TASKNAME [p/PRIORITY] [s/STARTDATE] [d/DUEDATE] [t/TAGNAME1] [t/TAGNAME2]...`**

<br>

> - You need to input the task's name as the first parameter. All entries following after the task name are optional.<br><br>
> - You can tag a task with any number of tags.<br><br>
> - **`STARTDATE`** and **`DUEDATE`**, if required for a task, should ideally be in **DD/MM/YYYY HH:MM** format.<br><br>
> - You can mark your tasks with **`PRIORITY`** between 1 and 3, with 1 being the lowest priority and 3 being the highest.<br>

<br>

#### Examples:
* You need to buy milk:
**`add Buy milk`** <br><br>
* You need to buy plane tickets to Hong Kong urgently:
**`a Buy plane tickets to Hong Kong p/3`** <br><br>
* You need to buy a table by 03/02/2017 13:00:
**`add Buy a table d/03/02/2017 13:00 t/watchingTV`** <br><br>
* You need to start marking CS2101 reflections on 01/03/2017 08:00:
**`a Mark CS2101 reflections s/01/03/2017 08:00 t/school`** <br>

<br>


### 2. Editing a task: `edit / e`
---
_**Edits a particular task by <u>specifying its index</u> and <u>its new information to be updated</u>.**_<br>

#### Format:

**`edit INDEX [NEWTASKNAME] [p/NEWPRIORITY] [s/NEWSTARTDATE] [d/NEWDUEDATE] [t/NEWTAGNAME]...`**

<br>

> - If a task does not have any information in the specified field previously, the new information will be added to the field instead. <br><br>
> - You can clear a task's field (except task name) by inputting the field prefix without any succeeding information.<br>

<br>

#### Example:

You have just added this task to ezDo at index 1 with the Add command:

 **`add Buy milk p/1 t/NTUC`**<br>

You can now edit the task in several ways:<br>

* You wish to change the task name:
**`edit 1 Buy milk and cereal`** <br><br>
* You wish to change the task's priority to 3:
**`e 1 p/3`** <br><br>
* You wish to add a due date:
**`edit 1 d/05/07/2017`**<br><br>
* You wish to remove a task's tag:
**`edit 1 d/05/07/2017`**<br>

<br>


### 3. Marking a task as done: `done / d`
---
_**Marks the task at a specified index as done.**_<br>

#### Format:

**`done INDEX`**

<br>

> - The index refers to the index number of the tasks shown in the most recent listing.<br><br>
> - You must input a valid index as shown in the task list.<br><br>
> - Once a task at the specified index is marked as done, it will be removed from the task list and added to the done list.<br><br>
> - You can view the done list with the command `done` without any index specified.

<br>

#### Example:

* Marks the task at index 1 as done:
**`d 1`** <br>
* Marks the task at index 3 as done:
**`done 3`** <br>

<br>


### 4. Deleting a task: `kill / k`
---
_**Marks the task at a specified index as deleted.**_<br>

#### Format:

**`kill INDEX`**

<br>

> - You must input a valid index as shown in the task list.<br><br>
> - The index must be a positive integer no larger than the number of tasks in the list.<br><br>
> - Once a task at the specified index is deleted, it will be removed from the task list.<br>

<br>

#### Example:

* Deletes the task at index 2:
**`kill 2`** <br><br>
* Deletes the task at index 4:
**`k 4`** <br>

<br>


### 5. Sorting a list of tasks: `sort / s`
---
_**Sorts the list of tasks according to the specified ordertype lexicographically.**_ <br>

#### Format:

**`sort ORDERTYPE`**

<br>

> **`ORDERTYPE`** can be any of the following:<br>
> - n - name<br>
> - p - priority<br>
> - s - start date<br>
> - d - due date<br>

<br>

#### Example:

You have just added a few tasks to ezDo with the following **`add`** commands:<br>

 **`add Dye hair p/1 s/06/05/2017 d/07/08/2017`**<br>
 **`add Buy milk p/1 s/04/05/2017 d/09/06/2017`**<br>
 **`add Call milkman p/1 s/05/05/2017 d/08/07/2017`**

You can now sort the tasks by name using either one of the following commands:<br>

* **`sort n`**
* **`s n`**

<br>

<p align="center"><img src="images/Sorting.png" width="700"></p><br>

<h5 align="center">Figure 4: View Before and After Sorting Tasks</h5>

<br>

Figure 4 above illustrates the screenshots <u>before</u> and <u>after</u> the **`sort`** command was used.<br>

<br>


### 6. Finding tasks: `find / f`
---
_**Finds tasks whose information contains any of the given keywords specified by its prefix.**_<br>

#### Format:

**`find [KEYWORD] [MORE_KEYWORDS] [p/PRIORITY] [s/STARTDATE] [d/DUEDATE] [t/TAGNAME1] [t/TAGNAME2]...`**

<br>

> - You need to input at least one field to search for a task.<br><br>
> - The fields are case insensitive.
> _For example: `find Milk` will match the task with the name `milk`._ <br><br>
> - The order of the keywords does not matter.
> _For example: `find Buy Milk` will match the task with the name `Milk Buy`._ <br><br>
> - Only full words will be matched.
> _For example: `find Milk` will not match the task with the name `Milks`._ <br><br>
> - Only tasks that match all the keywords will be returned.
    _For example: `find Milk p/1` will not match with the task `Buy Milk` with a priority of 2._
    _For example: `find donuts milk` will match the task with the name `Buy donuts and milk`._<br><br>
> - After finding your task, type `list` to go back to the default view.

<br>

#### Examples:

*  You can find tasks that have a due date on `20/03/2017`:
**`f d/20/03/2017`** <br><br>
*  You can find tasks containing `School` with a priority of `1`:
**`find School p/1`** <br>

<br>


### 7. Listing all tasks: `list / l`
---
_**Lists all the tasks in ezDo.**_<br>

#### Format:

**`list`**

<br>


### 8. Reverting the last action: `undo / u`
---
_**Reverts the last command.**_<br>

#### Format:

**`undo`**

<br>

> - You can only undo the following commands: **`add`**, **`clear`**, **`done`**, **`edit`**, **`kill`**.<br>
> -	You can use the command **`undo`** consecutively up to <u>5 times</u>.

<br>

#### Example:

You have just deleted the task named "buy milk".
By typing the command **`undo`**, you can restore it to the task list.

<br>


### 9. Clearing all entries: `clear / c`
---
_**Clears all entries from ezDo.**_<br>

#### Format:

**`clear`**

<br>


### 10. Moving the save file: `save`
---
_**Moves the save file of ezDo to a specified directory.**_<br>

#### Format:

**`save DIRECTORY`**
<br>
> - The directory specified must be valid.
> - Administrative permissions might be required to access the various directories in the computer.

<br>

#### Example:
* Move the save file of ezDo to `C:/Desktop`:
**`save C:/Desktop`** <br>

<br>


### 11. Exiting the program: `quit / q`
---
_**Exits the program.**_<br>

#### Format:

**`quit`**

<br>

> There will not be any exit confirmation displayed on ezDo upon executing the command.

<br>


### 12. Viewing help: `help / h`
---
_**Brings up the help guide in a separate window.**_<br>

#### Format:

**`help`**

<br><br>

## Frequently Asked Questions

#### Q: How do I transfer my data to another computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous ezDo.

#### Q: How do I save my ezDo tasks?<br>
**A**: ezDo data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

<br><br>

## Command Summary

For your convenience, the parameters for every command available in ezDo are summarised in Table 2 below.

|Command|Parameters                                               |
|:-----:|---------------------------------------------------------|
|[add / a](#1-adding-a-task-add--a)             |**`add TASKNAME [p/PRIORITY] [s/STARTDATE] [d/DUEDATE] [t/TAGNAME]...`**             |
|[edit / e](#2-editing-a-task-edit--e)          |**`edit INDEX [NEWTASKNAME] [p/NEWPRIORITY] [s/NEWSTARTDATE] [d/NEWDUEDATE] [t/NEWTAGNAME...]`**            |
|[done / d](#3-marking-a-task-as-done-done--d)  |**`done INDEX`**    |
|[kill / k](#4-deleting-a-task-kill--k)         |**`kill INDEX`**          |
|[sort / s](#5-sorting-a-list-of-tasks-sort--s) |**`sort ORDERTYPE`** |
|[find / f](#6-finding-tasks-find--f)           |**`find [KEYWORD] [MORE_KEYWORDS] [p/PRIORITY] [s/STARTDATE] [d/DUEDATE] [t/TAGNAME]`**      |
|[list / l](#7-listing-all-tasks-list--l)       |**`list`**         |
|[undo / u](#8-undoing-the-last-action-undo--u) |**`undo`** |
|[clear / c](#9-clearing-all-entries-clear--c)  |**`clear`**       |
|[save](#10-moving-the-save-file-save)      |**`save DIRECTORY`**    |
|[quit / q](#11-exiting-the-program-quit--q)    |**`quit`**              |
|[help / h](#12-viewing-help-help--h)           |**`help`**    |

<h5 align="center">Table 2: Command Summary</h5>

<br>

<h3 align="center">- End -</h3>


