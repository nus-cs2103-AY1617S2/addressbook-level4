# doTASK - User Guide

By : `Miao Ling` , `Ian` , `Qi Xiang` and `Dylan` - `[W09-B4]` Since : `Feb 2017`

---


1. [Introduction](#1-introduction)
2. [Quick Start](#2-quick-start)
3. [Features](#3-features)
> 3.1. [Viewing Help](#31-viewing-help-help)<br>
> 3.2. [Adding a task](#32-adding-a-task-add)<br>
> 3.3. [Listing the tasks](#33-listing-the-tasks-list)<br>
> 3.4. [Editing a task](#34-editing-a-task-edit)<br>
> 3.5. [Deleting a task](#35-deleting-a-task-delete)<br>
> 3.6. [Completing a task](#36-completing-a-task-complete)<br>
> 3.7. [Uncompleting a task](#37-uncompleting-a-task-uncomplete)<br>
> 3.8. [Searching for tasks](#38-searching-for-tasks-find)<br>
> 3.9. [Undo-ing previous action](#39-undo-ing-previous-action-undo)<br>
> 3.10. [Redo-ing previous action](#310-redo-ing-previous-action-redo)<br>
> 3.11. [Get previous command](#311-get-previous-command-)<br>
> 3.12. [Get next command](#312-get-next-command-)<br>
> 3.13. [Saving the task manager](#313-saving-the-task-manager)<br>
4. [FAQ](#4-faq)
5. [Command Summary](#5-command-summary)

## 1. Introduction

Our application doTASK helps to improve your productivity and accountability in managing your daily activities. The main feature of our application is its
prioritisation framework, which lets you focus on the important things. This user guide will provide you with the basic information you'll need in setting
up doTASK, as well as guide you on how to use it to make you more efficient in prioritising your tasks.

## 2. Quick Start

1. Ensure you have Java version `1.8.0_60` or later installed in your Computer.

2. Download the latest version of `doTASK.jar` from [releases] tab.

3. Copy the file to the folder you want to use as the home folder for your doTASK.

4. Double-click on the file to start the application. The GUI should appear in a few seconds.<br>
> <img src="images/startup.png" width="600">

5. Get started by adding your first task that you have in mind! Refer to **3. Features** for further instructions.<br>

## 3. Features

> Command Format
>
> * Items in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 3.1. Viewing help: `help`
Allows you to view the list of commands available in doTASK via the user guide. <br>
Format: `help`

### 3.2. Adding a task: `add`
There are a total of 5 parameters : `TASK_NAME` , `PRIORITY_LEVEL` , `DEADLINE` , `INFORMATION`, `TAGS`.<br>
> `taskname` is mandatory. The rest of the parameters are optional.<br>
> User can add a task with any of the optional parameters, mentioned from [3.2.2](#322-adds-a-task-with-priority-level-) - [3.2.5](#325-adds-a-task-with-tags).

#### 3.2.1. Adds a floating task.<br>
> * Floating tasks are tasks that excludes other parameters. Priority level, deadline, information and taggings are excluded. <br>
Format: `add TASK_NAME`

Example:
> I want to buy a packet of milk.<br>
* add Buy Milk

#### 3.2.2. Adds a task with `PRIORITY_LEVEL`. <br>
Format: `add TASK_NAME [p/PRIORITY_LEVEL]`
  > * `PRIORITY_LEVEL` should be defined by integers 1 (high) to 4(low).<br>

Example:
> I want to buy a packet of milk. Highest priority as it is urgent.<br>
* add Buy Milk p/1

#### 3.2.3. Adds a task with `DEADLINE`. <br>
Format: `add TASK_NAME [d/DEADLINE]`
   > * `DEADLINE` can be entered in the format of "date month", "month date", "date month year" or "month date year". <br>
	 > * If no year is specified, the current year of the system will be used as the year of the deadline.<br>
	 > * The month must be typed out as the first three letters of the month.<br>
   > * Tasks with deadlines will be complemented with reminders, so you won't forget to do them!

Examples:
> I want to buy a packet of milk by 17 March 2017.
* add Buy Milk d/17-Mar-2017

#### 3.2.4. Adds a task with `INFORMATION`.<br>
Format: `add TASK_NAME [i/INFORMATION]`
   > * `INFORMATION` refers to the details of the task.<br>

Examples:
> I want to buy a packet of milk, HL brand 1.5 Litres.<br>
* add Buy Milk i/HL Milk 1.5 Litres

#### 3.2.5. Adds a task with `TAGS`.<br>
Format: `add TASK_NAME [t/TAGS]`
   > * `TAGS` refers to the tags of the task.<br>

Examples:
> I want to buy a packet of milk for home usage.<br>
* add Buy Milk t/home

### 3.3. Listing the tasks: `list`

Shows a list of all the tasks in the task manager.<br>
> Listing is done automatically when you switch between the tabs.<br>
> However, you can choose to list all the tasks you have in the task manager. This list will be shown in a pop up.<br>
> You can also sort it according to the deadlines, priorities, etc.
> The index of the task as referenced by the task manager will always be shown alongside the task, ie. the index is not affected by filtering the list.

Shows a list of all the tasks in lexicographical order.<br>
Format: `list all`

Shows a list of all the tasks sorted by deadline.<br>
Format: `list deadline`

Shows a list of all the tasks sorted by priority level.<br>
Format: `list priority`

Shows a list of the tasks sorted by a stated priority level, from 1 - 4.<br>
Format: `list priority PRIORITY_LEVEL`
> Tasks can be given any priority level from 1 to 4.
> Tasks with `PRORITY_LEVEL` priority will be displayed.

Examples:
* `list priority 1`

Shows a list of tasks of the tags sorted in lexicographical order.<br>
Format: `list t/TAGS...`
> The tasks listed will be in clusters according to tags, but sorted in alphabetical order.<br>

Examples:
* `list t/CS3230 t/Work`

### 3.4. Editing a task: `edit`
Edits an existing task in the task manager.<br>
Format: `edit i/INDEX [n/TASK_NAME] [d/DEADLINE] [p/PRIORITY_LEVEL] [i/ANY_INFO] [t/TAGS]...`

> * Edits the task as denoted by the `INDEX` digit as shown on the screen. The `INDEX` must be a positive integer, e.g. 1, 2, 3, ...
> * The index of a task can be found beside the task description.
> * At least **one** of the optional [fields] must be provided.
> * Existing fields will be overwritten.

Examples:
* `edit i/1 n/Assignment 2 d/25 Feb 2018 p/2`
* `edit i/4 n/Exercise`

### 3.5. Deleting a task: `delete`
Deletes the specified task.<br>
Format: `delete INDEX_NUMBER`

> The task labelled `INDEX_NUMBER` will be deleted from the list.<br>
> `INDEX_NUMBER` of tasks is shown according to the current tab.

Examples:
* `delete 2`
* `delete 5`

### 3.6. Completing a task: `complete`

Marks the specified task in the "Uncompleted" list as complete.<br>
Format: `complete INDEX_NUMBER`
> `INDEX_NUMBER` of tasks is shown according to the "Uncompleted" list.<br>
> The task labelled by `INDEX_NUMBER` in the "Uncompleted" list will be marked as completed.<br>
> The completed task will be moved over to the "Completed" list.

Example:
* `complete 2`
> The task indicated by index number 2 in the "Uncompleted" list section will be marked as completed and shifted over the the "Completed" list section.

### 3.7 Uncompleting a task: `uncomplete`

Marks the specified task in the "Completed" list as uncomplete.<br>
Format: `uncomplete INDEX_NUMER`
> `INDEX_NUMBER` of tasks is shown according to the "Completed" list.<br>
> The task labelled by `INDEX_NUMBER` in the "Completed" list will be marked as uncompleted.<br>
> The uncompleted task will be moved over the the "Uncompleted" list.

Example:
* `uncomplete 2`
> The task indicated by index number 2 in the "Completed" list section will be marked as completed and shifted over the the "Uncompleted" list section.

### 3.8. Searching for tasks: `find`

Searches for tasks based on keywords that you want.<br>

Format: `find KEYWORD`
> No special characters such as ASCII whitespace is allowed.<br>
> A popout will appear to show you the list of tasks that contains the stated `KEYWORD`.

Examples:
* `find potato`

### 3.9. Undo-ing previous action: `undo`

Reverses previous action that you've made.<br>
Format: `undo`
> The command last executed will be reversed.
> Only 1 command will be reversed at a time.

### 3.10. Redo-ing previous action: `redo`

Reverses previous `undo` that you've made.<br>
Format: `redo`
> Any previous `undo` will be reversed, in successive order.

### 3.11. Get previous command: <kbd>↑</kbd>

Retrieve the previous command entered, and replaces your text field with the previous command.<br>
Format: Up arrow key
> You can retrieve earlier commands from your use session by pressing <kbd>↑</kbd> again and again.

### 3.12. Get next command: <kbd>↓</kbd>

Retrieve the next command entered, and replaces your text field with the next command.<br>
Format: Down arrow key
> You can retrieve later commands from your use session by pressing <kbd>↓</kbd> again and again.

### 3.13. Saving the task manager

Upon creation of tasks, the tasks will be automatically saved in the folder where the program is stored in.
> Do not erase the saved data as it will result in a complete loss of data that cannot be recovered by the application itself.

## 4. FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install the application in the other computer and overwrite the empty data file with the file <file_name.extension> that contains the data of your current doTASK manager.

**Q**: Will I get reminded if the deadlines of tasks are nearing?<br>
**A**: For tasks that are due in 24 hours, there will be a notification in the system tray reminding you of the task.

## 5. Command Summary

* **Help** : `help`

* **Switching between tabs** : `switch`

* **Add a Floating Task** : `add TASK_NAME p/PRIORITY_LEVEL [i/ANY_INFO] [t/TAGS]...`<br>
  e.g. `add Buy a new fan p/4 t/Home`

* **Add** : `add TASK_NAME d/DEADLINE p/PRIORITY_LEVEL [i/ANY_INFO] [t/TAGS]...`<br>
	e.g. `add Sleep d/27 December 2018 p/1 i/Sleep is good t/Home`

* **List All** : `list all`

* **List All by Deadlines** : `list deadlines`

* **List All by Priority** : `list priority`

* **List by Specific Priority** : `list priority PRIORITY_LEVEL`
	e.g. `list priority 1`

* **List by Tags** : `list t/TAGS [MORE_TAGS]`<br>
	e.g. `list t/CS2103 t/Work t/School`

* **Edit** : `edit`<br>
	e.g. `edit i/3 n/Buy a house`

* **Delete** : `delete`<br>
	e.g. `delete 1`

* **Completion of task** : `complete`<br>
	e.g. `complete 1`

* **Checking progress/performance** : `progress NUMBER_OF_DAYS`<br>
	e.g. `progress 7`

* **Search for tasks** : `find KEYWORD`<br>
	e.g. `find potato`

* **Undo previous action** : `undo`<br>

* **Redo previous action** : `redo`<br>

* **Get previous command** : <kbd>↑</kbd><br>

* **Get next command** : <kbd>↓</kbd><br>
