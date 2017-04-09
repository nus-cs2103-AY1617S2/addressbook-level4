# Tache - User Guide

By : `T09-B4` [Github](https://github.com/CS2103JAN2017-T09-B4/main)  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

## Contents

1. [Introduction](#1-introduction)
2. [Getting Started](#2-getting-started)
3. [Features](#3-features) <br>
	3.1. [Terminology](#31-terminology) <br>
	3.2. [Adding Tasks](#32-add-a-task--add) <br>
	3.3. [Listing Tasks](#33-list-all-tasks--list) <br>
	3.4. [Finding Tasks](#34-find-a-task--find) <br>
	3.5. [Editing Tasks](#35-edit-a-task--edit) <br>
    3.6. [Deleting Tasks](#36-delete-a-task--delete) <br>
    3.7. [Selecting Tasks](#37-select-a-task--select) <br>
    3.8. [Completing Tasks](#38-complete-a-task--complete) <br>
	3.9. [Undoing a Change](#39-undo-a-change--undo) <br>
	3.10. [Navigating the Calendar](#310-navigate-the-calendar) <br>
	3.11. [Getting Help](#311-get-help--help) <br>
	3.12. [Changing Data File Location](#312-change-data-file-location) <br>
	3.13. [Loading Data File From Location](#313-load-data-file-from-location) <br>
	3.14. [Exiting](#314-exit-the-program--exit)
4. [Command Summary](#4-command-summary)
5. [FAQ](#5-faq-frequently-asked-questions)


## 1. Introduction

Hi there, and nice to meet you!

My name is Tache, and I am a **task manager application** designed to serve busy people like you.
Have you ever felt stressed having to deal with a hectic schedule and numerous to-do tasks?
I am here to be your _virtual assistant_ and help you to manage all your deadlines and events. <br>

You are currently reading my user guide, which has been written to help you with:
* `Installing` me
* `Using` me
* `Troubleshooting` me


## 2. Getting Started

0. Ensure that you have Java version `1.8.0_60` or later installed in your computer.<br>

   > Unfortunately, having any Java 8 version is not enough because I cannot work with earlier versions of Java 8.

1. Download the latest `tache.jar` file from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as my home folder.
3. Double-click the file to start me! <br>

   > My Graphical User Interface (GUI) should appear in a few seconds:

   <img src="images/UiComponents.png" width="600"><br>
   _Figure 2.1. My GUI_

4. Type your desired command in my command box and press <kbd>Enter</kbd> to execute it. <br>

   **Let's Try it Out!** <br>

   (Do the following steps in order)

   * **`add`** `Finish Progress Report by 13 apr 2pm` <br>

     > Adds a task with the following task details into your task list: <br>
     > Name: `Finish Progress Report` <br>
     > Due Date: `13 Apr` <br>
     > Due Time: `2 p.m.`

   * **`list`** <br>

     > Lists all your tasks. <br>
     Your task list should only contain 1 task, which is the task you added in the previous step (i.e. `Finish Progress Report`).

   * **`delete`** `1` <br>

     > Deletes your task `Finish Progress Report` with the task index `1`.

   * **`list`** <br>

     > Your task list should be empty now.

   * **`exit`**

     > Time for me to rest!

6. Want more? Refer to the [Features](#3-features) section below to find out more about each command I can perform. <br>


## 3. Features

### 3.1. Terminology

#### Task Details:

  > * Name
  > * Start Date
  > * Start Time
  > * End Date
  > * End Time

#### Task Filters:

  > * All
  > * Completed
  > * Uncompleted
  > * Floating
  > * Timed

### 3.2. Add a task : `add`

Adds a task to your task list. <br>

#### To add a _floating_ task:

A floating task is a task that _does not have any specific times_. <br>
You are probably not sure when you are going to do it, nor are you sure when exactly it is due. <br>

An example of a floating task: <br>

* **A hobby-related activity or long-term goal**

  > You just want to record this task somewhere so that you can get to it when you are free someday. <br>

  **_E.g. Learn baking_**

Format: **`add`** `<task_name>` <br>

#### To add a _timed_ task:

A timed task is a task that _is associated with specific dates and times_. <br>
It can be a task with a deadline or an event. <br>

Format: **`add`** `<task_name> by <due date and time>` <br>
E.g. **`add`** `project proposal by 13 apr 2pm`<br>

Format: **`add`** `<task_name> from <start date and time> to <end date and time>` <br>
E.g. **`add`** `sushi restaurant promotion from 25 apr 10am to 28 apr 9pm`<br>

<img src="images/UiAddCommand.png" width="600"><br>
_Figure 3.2.1. Add Command_

For advanced users: **`a`** `<task_name>` <br>

### 3.3. List all tasks : `list`

Displays tasks in your task list.<br>
With this command, you will be able to list tasks of specific statuses in a _chronological order_.<br>

Some reasons why you might want to _list_ your tasks:

* **You want to prioritize your tasks based on the order of their specific dates / times** <br>

  > You want to have an overview of all your tasks based on how urgent they are, then
  manually select some of these tasks to focus on.

* **You want to track the tasks that you have already completed** <br>

* **You want to plan your time to get your remaining tasks done** <br>

* **You want to schedule some of your uncompleted [floating tasks](#to-add-a-floating-task)** <br>

  > Converting your floating tasks into [timed tasks](#to-add-a-timed-task) will allow me to keep track of
  them more effectively (e.g. by alerting you when they are overdue).
  > Fun fact: you can know the _no. of floating and timed tasks_ you have instantly through my GUI!

	<img src="images/TaskCount.png" width="100"><br>
	_Figure 3.3.1. Task Count_

Format: **`list`** <br>
This lists all your uncompleted tasks. <br>

Format: **`list`** `<filter>`<br>
E.g. **`list`** `completed`, **`list`** `all`, **`list`** `floating`, **`list`** `timed` <br>

<img src="images/UiListCommand.png" width="600"><br>
_Figure 3.3.2. List Command_

For advanced users: **`l`** `<filter>` <br>

### 3.4. Find a task : `find`

Finds uncompleted and overdue task(s) whose name(s) contain `<keyword>` with one margin of error. <br>

> For example, **`find`** `<homwork>` can help you search for a task named `do probability homework`.
> Similarly, a task named `programming hoework` (notice the spelling error), will also be found.<br>

A reason why you might want to _find_ a task:

* **You want to know the [task details](#task-details) of a task** <br>

  > Such task details include the duration of a task, the deadline of a task etc.

  E.g. **Finding out when your _project proposal_ is due** <br>
  You want to plan your schedule for the week, taking into account the proposal deadline.

Format: **`find`** `<keyword>` <br>

<img src="images/UiFindCommand.png" width="600"><br>
_Figure 3.4.1. Find Command_

For advanced users: **`f`** `<keyword>` <br>

### 3.5. Edit a task : `edit`

Edits 1 or more [task details](#task-details) of a task. <br>

An example of a task you might want to _edit_: <br>

* **A task that has already exceeded its deadline**

  > You still want to complete a certain task, but you intend to postpone its deadline. <br>

  **_E.g. Buy aunt's birthday present_** <br>
  You have unfortunately already missed her birthday, but you still want to give her a
  belated birthday present the next time you meet her.

Format: **`edit`** `<task_index> change <task_detail> to <new_value>` <br>
This command will direct me to make the specified update to a task with `<task_index>`. <br>
Format: **`edit`** `<task_index> change <task_detail_1> to <new_value1> and <task_detail_2> to <new_value2> and ...`<br>
You can edit more task details for your task concurrently using the following format:<br>
E.g. **`edit`** `4 change start_date to 24 apr and end_date to 27 apr` <br>

<img src="images/UiEditCommand.png" width="600"><br>
_Figure 3.5.1. Edit Command_

For advanced users: **`e`** `<task_index> change <task_detail_1> to <new_value1> and <task_detail_2> to <new_value2> and ...` <br>
alternatively <br>
For advanced users: **`e`** `<task_index>; <task_detail_1> <new_value_1>; <task_detail_2> <new_value_2>; ...` <br>

### 3.6. Delete a task : `delete`

Removes a specified task from your task list.<br>

An example of a task that you might want to _delete_:

* **A tasks that no longer needs to be done** <br>

  E.g. **_Do financial report_** <br>
  Your supervisor had delegated the wrong task to you and has just corrected his mistake.

Format: **`delete`** `<task_index>` <br>
To delete all your tasks, you can type in **`clear`** instead. <br>

For advanced users: **`d`** `<task_index>` <br>

### 3.7. Select a task : `select`

Selects a task for you to see it at the calendar view. <br>

A reason why you might want to _select_ a task:

* **You want to view your task in relation to the other tasks you have for the month** <br>

  > This will guide you in planning your schedule for the next few weeks. <br>

Format: **`select`** `<task_index>` <br>

<img src="images/UiSelectCommand.png" width="600"><br>
_Figure 3.7.1. Select Command_

For advanced users: **`s`** `<task_index>` <br>

### 3.8. Complete a task : `complete`

Marks a task as done. <br>

A reason why you might want to _complete_ a task:

* **You want to declutter your task list** <br>

  > I will keep your completed tasks hidden from your default list view until you need to refer to them again. <br>

Format: **`complete`** `<task_index>`<br>
E.g. **`complete`** `1` <br>
Format: **`complete`** `<task_index1>,<task_index2>,<task_index3>,...`<br>
You can complete multiple tasks simultaneously using the following format:<br>
E.g. **`complete`** `1,2,3,4` <br>

<img src="images/UiCompleteCommand.png" width="600"><br>
_Figure 3.8.1. Complete Command_

For advanced users: **`c`** `<task_index>` <br>

### 3.9. Undo a change : `undo`

Undoes the last change made to my data. <br>

A reason why you might want to _undo_ a previous command:

* **You edited the wrong details of a task by mistake** <br>

  > It is too much of a hassle for you to manually correct your error by editing the task again.

Format: **`undo`** <br>

<img src="images/UiUndoCommand.png" width="600"><br>
_Figure 3.9.1. Undo Command_

For advanced users: **`u`** <br>

### 3.10. Navigate the Calendar:

Replaces navigation buttons on the calendar with user commands. <br>

<img src="images/UiCalendar.png" width="600"><br>
_Figure 3.10.1. Calendar_

* _< button_ : **`prev`** <br>
* _> button_ : **`next`** <br>
* _day button_: **`show day`** <br>
* _week button_: **`show week`** <br>
* _month button_: **`show month`** <br>

For advanced users: **`p`**, **`n`**, **`s`** `<view>` <br>

### 3.12. Get help : `help`

Shows a list of all commands I can execute and their usage instructions. <br>

Format: **`help`** <br>
This command will help to direct you back to this user guide.

Format: **`help`** `<command>` <br>
This command will instruct me to provide you specific information on how to use `<command>`.

<img src="images/UiHelpCommand.png" width="600"><br>
_Figure 3.12.1. Help Command_

For advanced users: **`h`** `<command>` <br>

### 3.13. Change data file location

Modifies the file path of my data file. <br>
Future modifications of my task list will be saved at this new location. <br>

A reason why you might want to _change my data file location_:

* **You want to [sync](#sync) my task list and access it from other devices** <br>

  > You can choose to store my data file in a local folder controlled by a cloud
  syncing device (e.g. Dropbox) so that you can access my data from multiple computers.

Format: **`save`** `<new_save_location_directory>` <br>
This command directs me to set my new data file in a `<new_save_location_directory>`,
then save all my data in that file.

<img src="images/UiSaveCommand.png" width="600"><br>
_Figure 3.13.1. Save Command_

### 3.14. Load data file from location

Loads the specified data file. <br>

Here is a reason why you might want to _load a data file_:

* **Your current data file is corrupted** <br>

  > Luckily, you've made a backup copy previously. Now you'll need me to load from that backup copy instead. <br>

Format: **`load`** `<file_path>` <br>
This command loads the data from the specified file in the `<file_path>`.

<img src="images/UiLoadCommand.png" width="600"><br>
_Figure 3.14.1. Load Command_

### 3.15. Exit the program : `exit`

Saves all data and exits the program. <br>
Time for you to actually perform your tasks!

Format: **`exit`** <br>


## 4. Command Summary

Here is a cheat sheet of what I can do. <br>
Your wish is my command!

| **Command** | **Usage**                                                     | **Example**                                         |
|:-----------:|:-------------------------------------------------------------:|:---------------------------------------------------:|
|Add          |**`add`** `<task_name> by <due date and time>`                 |**`add`** `project proposal by 13 apr 2pm`           |
|List         |**`list`** `<optional filter>`                                 |**`list`** `uncompleted`                             |
|Find         |**`find`** `<keyword>`                                         |**`find`** `prject`                                  |
|Edit         |**`edit`** `<task_index> change <task_detail> to <new_value>;` |**`edit`** `1; change name to buy white bread`      |
|Delete       |**`delete`** `<task_index>`                                    |**`delete`** `1`                                     |
|Select       |**`select`** `<task_index>`                                    |**`select`** `2`                                     |
|Complete     |**`complete`** `<task_index>`                                  |**`complete`** `1`                                   |
|Undo         |**`undo`**                                                     |                                                     |
|Prev         |**`prev`**                                                     |                                                     |
|Next         |**`next`**                                                     |                                                     |
|View         |**`view`**                                                     |**`view`** `day`                                     |
|Help         |**`help`**                                                     |**`help`** `find`                                    |
|Save         |**`save`** `<directory>`                                       |**`save`** `C:\Users\Jim\Desktop`                    |
|Load         |**`load`** `<file_path>`                                       |**`load`** `C:\Users\Jim\Desktop\taskmanager.xml`    |
|Exit         |**`exit`**                                                     |                                                     |


## 5. FAQ (Frequently Asked Questions)

Here are some questions that you might want to ask me:

**Q**: How do I _transfer my data_ to another computer? <br>
**A**: Install me in the other computer and _overwrite the empty data file_ I create with
       the file that contains the data of your previous Task Manager folder. Alternatively,
       you can type in **`load`** `<file_path>`, to load your data file.

**Q**: How do I add an _event_? <br>
**A**: Type in **`add`** `<task> from <start date and time> to <end date and time>` as mentioned
[here](#32-add-a-task--add).

**Q**: How do I retrieve my _previous commands_? <br>
**A**: Use the arrow keys <kbd>Up</kbd> and <kbd>Down</kbd> to get your previous commands.

**Q**: Is there any way I can _type my commands faster_? <br>
**A**: Yes, I _autocomplete_ all commands. Every command can also be replaced with the first letter of
its name. For example, you can replace "add" with just "a".
