# Tâche - User Guide

By : `T09-B4`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

--- 

## Navigate Me!

1. [Introduction](#1-introduction)
2. [Getting Started](#2-getting-started)
3. [Features](#3-features) <br>
	3.1. [Command Formats](#31-command-formats) <br>
	3.2. [Adding Tasks](#32-add-a-task--add) <br>
	3.3. [Deleting Tasks](#33-delete-a-task--delete) <br>
	3.4. [Finding Tasks](#34-find-a-task--find) <br>
	3.5. [Listing Tasks](#35-list-all-tasks--list) <br>
    3.6. [Selecting Tasks](#36-select-a-task--select) <br>
    3.7. [Updating Tasks](#37-update-a-task--update) <br>
	3.8. [Getting Help](#38-get-help--help) <br>
	3.9. [Change Data File Location](#39-change-data-file-location) <br>
	3.10. [Exit](#310-exit-the-program--exit)
4. [Command Summary](#4-command-summary)
5. [FAQ](#5-faq-frequently-asked-questions)
6. [Glossary](#6-some-technical-terms)


## 1. Introduction

Hi there, and nice to meet you!

My name is Tâche, and I am a **task manager application** designed to serve busy people like you. 
Have you ever felt stressed having to deal with a hectic schedule and numerous to-do tasks?
I am here to be your virtual assistant and help you to manage all your deadlines and events. <br>

You are currently reading my user guide, which has been written to help you with: 
* Installing me
* Using me
* Troubleshooting whenever I give you problems (Hopefully I won't!)

Ready? Let's begin!


## 2. Getting Started

0. Ensure that you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Unfortunately, having any Java 8 version is not enough because I cannot work with earlier versions of Java 8.

1. Download the latest `tache.jar` file from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as my home folder.
3. Double-click the file to start me. My [GUI](#graphical-user-interface-gui) should appear in a few seconds.

  <img src="images/Ui.png" width="600"><br>
  _Figure 2.1 My GUI_

4. Type your desired command in the command box and press <kbd>Enter</kbd> to execute it. <br>

   > E.g. Typing **`help`** and pressing <kbd>Enter</kbd> will open my help window.
   
   **Let's Try it Out!** <br>
   (Do the following steps in order)
   
   * **`add`**` Finish Progress Report; 030217 1159PM` <br>
   
     > Adds a task with the following [parameters](#parameter) into your task list: <br>
     > **Name**: `Finish Progress Report` <br>
     > **Due Date**: `3rd Feb 2017` <br>
     > **Due Time**: `1159PM`
     
   * **`list`** <br>
   
     > Lists all your tasks. <br>
     Your task list should contain only 1 task, which is the task you added in the previous step 
     (i.e. `Finish Progress Report`). 
     
   * **`delete`** `Finish Progress Report` <br>
   
     > Deletes your task with the name `Finish Progress Report`. 
     
   * **`list`** <br>
   
     > Your task list should be empty now. 
     
   * **`exit`**

     > Time for me to rest!

6. Want more? Refer to the [Features](#features) section below for details of each command I can perform. <br>


## 3. Features

### 3.1. Command Formats

When typing in your commands, do take note of these specifications. <br>
If not, I will not work! 

* Durations for tasks must be specified in "hr", "min" and/or "sec".
* Times must be specified in "am" and/or "pm".
* Either date or time but not both can be left out in <... date and time> parameters.

  > i.e. 
  > * <start date and time> <br>
  > * <due date and time> <br>
  > * <end date and time> <br>
  
* `<task>` refers to the name of your task.

* Parameters for your tasks include the following: 

  > * Name
  > * Start Date
  > * Start Time
  > * End or Due Date
  > * End or Due Time
  > * Duration

### 3.2. Add a task : `add`

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

### 3.3. Delete a task : `delete`

Deletes the specified task from the task manager.<br>
Formats:

> * `delete <task>`
> delete /all (delete all activity)

Examples:

* `delete watch tv with the children`

### 3.4. Find a task : `find`

Finds task(s) whose names contain any of the given keywords.<br>
Formats:

> * `find <task>`
> * `find <task>; <due date>` (`show tasks before the due date and also tasks without any due date`)

Examples:

* `find project`
* `find meeting; monday`

### 3.5. List all tasks : `list`

Shows a list of all tasks in the task manager.<br>
Format:

> * `list`

### 3.6. Select a task : `select`

Selects a task for user to view its details and make changes to it if needed.<br>
Formats:

> * `select <task>` (display all tasks with the same name for user to choose one)
> * `unselect`

Examples:

* `select presentation`

> Task successfully selected will be highlighted for the user to see. 

### 3.7. Update a task : `update`

Edits the value(s) of parameter(s) of a task.<br>
Formats:

> * `update <parameter> <new_value>` (when task has already been selected using the 'select' command)
> * `update <task>; <parameter1> <new_value1>; <parameter2> <new_value2>`

Examples:

* `update start time 10am`
* `update project proposal; name app development project proposal; end time 11.59pm`

### 3.8. Get help : `help`

Shows a list of all commands and their usage instructions.<br>
Formats:

> * `help <command>` (provides specific information about the usage of the command)
> * `help /all`

> Help is also shown if you enter an incorrect command. e.g. `abcd`

### 3.9. Change save file location

Formats:

> * `save`
> * `save <new_save_location_directory>`

Examples:

* `save C:\Users\Jim\Desktop`

### 3.8. Exiting the program : `exit`

Exits the program.<br>
Format:

> * `exit`


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


## 5. FAQ (Frequently Asked Questions)

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Manager folder.
       
       
## 6. Some Technical Terms

#### Graphical User Interface (GUI): 

> The graphical interface that allows the you to interact with me through graphical icons and visual indicators

#### Parameter: 

> Detail associated with a task of yours (e.g. duration, name, start time)