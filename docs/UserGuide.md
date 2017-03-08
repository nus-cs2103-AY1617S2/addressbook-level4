# Tâche - User Guide

By : `T09-B4`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `tache.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Tâche task manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds.

<img src="images/Ui.png" width="600"><br>

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` Finish Progress Report; 030217 1159PM` :
     adds a task named `Finish Progress Report` with due date `3rd Feb 2017' and due time '1159PM` to the Task Manager.
   * **`delete`**` project` : deletes the task with the name `project`
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## 2. Features
> **Command Format**
>
> * Duration must be specified in "hr", "min" and/or "sec".
> * Time must be specified in "am" and/or "pm".
> * For <... date and time> parameters, either date or time can be left out but not both.
> * `<task>` refers to the name of the task.
> * Parameters include: name, start time, start date, end (due) time, end (due) date, duration.

### 2.1. Adding a task: `add`

Adds a task to the task manager<br>
Formats: 
(Type parameters in the corresponding order)

> * `add <task>`
> * `add <task>; <duration>`
> * `add <task>; <due date and time>`
> * `add <task>; <start date and time>; <duration>`
> * `add <task>; <start date and time>; <end date and time>`

Examples:

* `add watch tv with the children; 1hr`
* `add iron the clothes; 5pm`
* `add project proposal; tue 2pm`
* `add committee meeting; 15 june; 2 hr`
* `add sushi restaurant promotion; 040117 10am; 110117 9pm`

### 2.2. Deleting a task : `delete`

Deletes the specified task from the task manager.<br>
Formats:

> * `delete <task>`
> delete /all (delete all activity)

Examples:

* `delete watch tv with the children`

### 2.3. Find a task: `find`

Finds task(s) whose names contain any of the given keywords.<br>
Formats:

> * `find <task>`
> * `find <task>; <due date>` (`show tasks before the due date and also tasks without any due date`)

Examples:

* `find project`
* `find meeting; monday`

### 2.4. Listing all tasks : `list`

Shows a list of all tasks in the task manager.<br>
Format:

> * `list`

### 2.5. Viewing help : `help`

Shows a list of all commands and their usage instructions.<br>
Formats:

> * `help <command>` (provides specific information about the usage of the command)
> * `help /all`

> Help is also shown if you enter an incorrect command. e.g. `abcd`

### 2.6. Select a task : `select`

Selects a task for user to view its details and make changes to it if needed.<br>
Formats:

> * `select <task>` (display all tasks with the same name for user to choose one)
> * `unselect`

Examples:

* `select presentation`

> Task successfully selected will be highlighted for the user to see. 

### 2.7. Update a task : `update`

Edits the value(s) of parameter(s) of a task.<br>
Formats:

> * `update <parameter> <new_value>` (when task has already been selected using the 'select' command)
> * `update <task>; <parameter1> <new_value1>; <parameter2> <new_value2>`

Examples:

* `update start time 10am`
* `update project proposal; name app development project proposal; end time 11.59pm`


### 2.8. Exiting the program : `exit`

Exits the program.<br>
Format:

> * `exit`

### 2.9. Change save file location

Formats:

> * `save`
> * `save <new_save_location_directory>`

Examples:

* `save C:\Users\Jim\Desktop`

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Manager folder.

## 4. Command Summary

| **Command** | **Usage**                       | **Example**                                  |
|:-----------:|:-------------------------------:|:--------------------------------------------:|
|Add          |`add <task>; <due date and time>`|`add sushi restaurant promotion; 040117 10am;`|
|Clear        |`clear`                          |                                              |
|Delete       |`delete <task>`                  |`delete watch tv with the children`           |
|Find         |`find <task>`                    |`find project`                                |
|List         |`list`                           |                                              |
|Help         |`help`                           |                                              |
|Select       |`select <task>`                  |`select presentation`                         |


