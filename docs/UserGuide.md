# Tâche - User Guide

By : `T09-B4`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

--- 

## Shortcuts to Information You Need

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
I am here to be your _virtual assistant_ and help you to manage all your deadlines and events. <br>

You are currently reading my user guide, which has been written to help you with: 
* `Installing` me
* `Using` me
* `Troubleshooting` me whenever I give you problems (Hopefully I won't!)

Ready? Let's begin!


## 2. Getting Started

0. Ensure that you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Unfortunately, having any Java 8 version is not enough because I cannot work with earlier versions of Java 8.

1. Download the latest `tache.jar` file from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as my home folder.
3. Double-click the file to start me. My [GUI](#graphical-user-interface-gui) should appear in a few seconds: 

   <img src="images/Ui.png" width="600"><br>
   _Figure 2.1 My GUI_

4. Type your desired command in my command box and press <kbd>Enter</kbd> to execute it. <br>

   > E.g. Typing **`help`** and pressing <kbd>Enter</kbd> will open my help window.
   
   **Let's Try it Out!** <br>
   
   (Do the following steps in order)
   
   * **`add`** `Finish Progress Report; 030217 1159PM` <br>
   
     > Adds a task with the following [parameters](#parameter) into your task list: <br>
     > Name: `Finish Progress Report` <br>
     > Due Date: `3 Feb 2017` <br>
     > Due Time: `11.59 p.m.`
     
   * **`list`** <br>
   
     > Lists all your tasks. <br>
     Your task list should contain only 1 task, which is the task you added in the previous step <br>
     (i.e. `Finish Progress Report`). 
     
   * **`delete`** `Finish Progress Report` <br>
   
     > Deletes your task with the name `Finish Progress Report`. 
     
   * **`list`** <br>
   
     > Your task list should be empty now. 
     
   * **`exit`**

     > Time for me to rest!

6. Want more? Refer to the [Features](#3-features) section below for details of each command I can perform. <br>


## 3. Features

### 3.1. Command Formats

When typing in your commands, do take note of the following specifications: <br>

> Do make sure that you follow the specifications closely, if not I will not work! <br>
The specifications are [case-sensitive](#case-sensitive). 

* Durations for tasks must be specified in _hr_, _min_ and/or _sec_.
* Times must be specified in _am_ and/or _pm_.
* Dates must be specified in _DDMMYY_ format. 
* Either date or time but not both can be left out in the <... date and time> parameters.

  > i.e. 
  > * `<start date and time>` <br>
  > * `<due date and time>` <br>
  > * `<end date and time>` <br>
  
* `<task>` refers to the name of your task.

* [Parameters](#parameter) for your tasks include the following: 

  > * Name
  > * Start Date
  > * Start Time
  > * End or Due Date
  > * End or Due Time
  > * Duration

### 3.2. Add a task : `add`

Adds a task to your task list. <br>

> Type your [parameters](#parameter) in their respective orders. <br>
E.g. If you are adding a task with a deadline as in `add <task>; <due date and time>`, 
do make sure that you indicate `<task>` before `<due date and time>`, not the other way round!

#### Add a _floating_ task:

A floating task is a task that _does not have any specific times_. <br>
You are probably not sure when you are going to do it, nor are you sure when exactly it is due. <br>

Here are some examples of floating tasks: <br>

> Note: This list is not exhaustive!

* **Hobby-related activities or long-term goals**

  > You just want to record these tasks somewhere so that you can get to them when you are free someday. <br>
  
  **_E.g. Learn to bake_**
  
* **New tasks which cannot be performed _yet_**

  > You want to perform these tasks but you cannot do them yet. <br>
  
  **_E.g. Schedule meet-up with Jamie_** <br>
  Your friend Jamie might be overseas and uncontactable for now, so you will have to wait for 
  her to return to the country in order to schedule the meet-up. 
  
 * **New tasks which do not have a confirmed deadline**
 
   > It is too early to know when you have to perform these tasks by. <br>
   
   **_E.g. Watch La La Land in the theatres_** <br>
   You might not know when the movie "La La Land" will stop showing in the cinemas. It seems to 
   be very popular and hence, many theatres might offer showtimes for it for an extended period 
   of time. 
   
So, how do you add floating tasks? Just type in the following command: 

> `add <task>` <br>
E.g. `add buy detergent`

That's it!

#### Add a task with a _deadline_:

> `add <task>; <due date and time>` <br>
E.g. `add project proposal; 041216 2pm`

#### Add an _event_:

> `add <task>; <start date and time>; <end date and time>` <br>
E.g. `add sushi restaurant promotion; 040117 10am; 110117 9pm`

OR

> `add <task>; <start date and time>; <duration>` <br>
E.g. `add committee meeting; 150617`

#### Add a task with a _duration_:

> `add <task>; <duration>` <br>
E.g. `add watch tv with the children; 1hr`

### 3.3. Delete a task : `delete`

Removes a specified task from your task list.<br>

> * `delete <task>` <br>
E.g. `delete watch tv with the children`

> * `delete /all` <br>
This command clears your entire task list. <br>
Hence, do think twice before typing this command!

### 3.4. Find a task : `find`

Finds task(s) whose names contain the keyword `<task>`.<br>

> For example, `find <home>` can help you search for a task called `do homework`. 

> * `find <task>` <br>
E.g. `find project proposal`

> * `find <task>; <due date>`
E.g. `find project proposal; 041216` <br>
This command shows tasks due before `<due date>` and also [floating tasks](#floating-task). 

### 3.5. List all tasks : `list`

Shows a list of all your tasks in the task manager.<br>

> * `list`

### 3.6. Select a task : `select`

Selects a task for you to view its details. <br>
With this command, you can make changes to the task if needed!

> * `select <task>` <br>
E.g. `select buy detergent` <br>
If there are tasks with the same name, this command will display all those tasks for you to choose one. <br>
I will highlight the task that is successfully selected for you to see. 

> * `unselect` <br>
This command cancels your previous selection so that you can select another task instead.

### 3.7. Update a task : `update`

Edits 1 or more [parameters](#parameter) of a task. <br>

> * `update <parameter> <new_value>` <br>
E.g. `update start time 10am` <br>
This command will make the specified update to a task which has already been selected using my 
['select'](#select-a-task-select) command. <br>
Edit more parameters concurrently using the following format: <br>
`update <parameter1> <new_value1>; <parameter2> <new_value2>; ...`
 
> * `update <task>; <parameter> <new_value>` <br>
E.g. `update committee meeting start time 10am` <br>
This command will make the specified update to a task with the name `<task>`. <br>
You can also add more parameters to modify when using this format. <br>
E.g. `update project proposal; name app development project proposal; end time 11.59pm`

### 3.8. Get help : `help`

Shows a list of all commands and their usage instructions. <br>

> * `help <command>` <br>
E.g. `help update`
This command provides specific information about the usage of `<command>`. 

> * `help /all`
This command directs you back to this user guide. 

> Help will also be shown if you enter an incorrect command. e.g. `abcd`

### 3.9. Change data file location

> * `save`
This command saves all your data into the data file. 

> * `save <new_save_location_directory>`
E.g. `save C:\Users\Jim\Desktop`
This command saves all your data in a new data file in a `<new_save_location_directory>`

### 3.10. Exiting the program : `exit`

Exits the program. <br>

> * `exit`


## 4. Command Summary

| **Command** | **Usage**                       | **Example**                                  |
|:-----------:|:-------------------------------:|:--------------------------------------------:|
|Add          |`add <task>; <due date and time>`|`add sushi restaurant promotion; 040117 10am;`|
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

#### Case-Sensitive:

> Text that discriminates between upper-case and lower-case letters <br>
E.g. _Jenny_ versus _jenny_

#### Floating Task:

> Task with no specific times

#### Graphical User Interface (GUI): 

> The graphical interface that allows the you to interact with me through graphical icons and visual indicators

#### Parameter: 

> Detail associated with a task of yours (e.g. duration, name, start time)