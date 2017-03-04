# doTASK - User Guide 

By : `Miao Ling` , `Ian` , `Qi Xiang` and `Dylan` - `[W09-B4]` Since : `Feb 2017` 

---

1. [Quick Start] (#quick-start)
2. [Features] (#features)
3. [FAQ] (#faq)

## 1. Quick Start

0. Ensure you have Java version 1.8.0_60 or later installed in your Computer.

1. Download the latest version of `doTASK.jar` from [releases] tab.

2. Copy the file to the folder you want to use as the home folder for your task manager.

3. Double-click on the file to start the application. The GUI should appear in a few seconds.<br>
> <img src="images/startup screen.jpg" width="600">

4. Get started by adding the first task that you have in mind! Refer to "2. Features" for instructions. <br>
> <img src="images/add screen.jpg" width="600">


5. After step 6, you can view your task in 3 different tabs.

   > **Today/Date**
   >
   > * Shows you the list of tasks you have for the specified date. Click left/right arrow to navigate between the dates.
   > <img src="images/today screen.jpg" width="600">
   >
   > **Overview**
   >
   > * Shows you an overall view of the dates. The highest priority tasks will be shown as a preview for every single date. Scroll to navigate between the dates.
   > <img src="images/overview screen.jpg" width="600">
   >
   > **Priority**
   >
   > * Shows you 4 options of priority tabs, from highest to lowest. Selecting the tab allows you to view the list of tasks that is under that priority. Eg. By clicking on â€œHighest Priorityâ€� tab, it will bring you to the list of tasks that you have in it.<br>
   > <img src="images/priority screen.jpg" width="600">

## 2. Features

> Command Format
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BREACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.
	
### 2.1. Viewing help : `help`
Format: `help`<br>

> Help is shown if you enter an incorrect command e.g. `asdf` <br>
> Alternatively, type /help to obtain a list of commands that you can use. 

### 2.2. Adding a Task : `add`
Adds a task to the task manager. <br>
Format: `add TASK_NAME d/DEADLINE p/PRIORITY_LEVEL [i/ANY_INFO] [t/TAGS]...`
   > `PRIORITY_LEVEL` should be defined by integers 1 (high) to 4(low).<br>
   > `ANY_INFO` allows you to key in details with regards to the task, optional.<br>
	> `TAGS` allows you to assign tags to the tasks, optional.
	
Examples:
* add CS3230 Assignment 1 d/8 Jan 2018 p/1 i/How to do? t/School t/CS3230
* add Buy Milk d/23 Fev 2017 p/4 t/Chores

### 2.3. Listing the tasks: `list`

Shows a list of all the tasks in the task manager.<br>
> Listing is done automatically when user switches between the tabs.<br>
> However, user can specify to list all the tasks he has in, which will be shown in a pop up.<br>
> User can also sort it accordingly to the deadlines, priorities, etc.
	
Shows a list of all the tasks in lexographical order.<br>
Format: `list all`

Shows a list of all the tasks sorted by deadlines.<br>
Format: `list deadlines`
	
Shows a list of all the tasks sorted by priority.<br>
Format: `list priority`
	
Shows a list of tasks of the tags sorted in lexographical order.<br>
Format: `list t/TAGS...`
> The tasks listed will be in clusters according to tags, but sorted in alphabetical order.<br>

Examples:
* `list t/CS3230 t/Work` 

### 2.4. Editing an existing task: `edit`
Edits an existing task in the task manager.<br>
Format: `edit i/INDEX [n/TASK_NAME] [d/DEADLINE] [p/PRIORITY_LEVEL] [i/ANY_INFO] [t/TAGS]...`
	
> * Edits the task labeled by the INDEX digit as shown on the screen. The INDEX must be a positive integer, e.g. 1, 2, 3, ...
> * At least one of the optional [fields] must be provided.
> * Existing fields will be overwritten.

Examples:
* `edit i/1 n/Assignment 2 d/25 Feb 2018 p/2`

### 2.5. Shifting tabs: `switch`
Switch the current tab to the next tab.
Format: `switch`
> There will be 3 tabs, which can be toggled using this command.

### 2.6. Completing a task: `done`

Archives the completed task. 
Format: `done INDEX_NUMBER`
> Task labeled by INDEX_NUMBER will be removed from the list.<br>
> INDEX_NUMBER of task is shown as according to the current tab.

Examples: 
* `done 1` 
* `done 4`	
	
[Work in progress]
### 2.6. Deleting a task
	Click on the task itself and the pop out window will appear.<br>
	Click on the delete button if the task is no longer applicable to you.

[Work in progress]
### 2.7. Viewing the task according to different priorities
	Click on the `Priority` tab. <br>
	The tasks are sorted in 4 types of priorities with different colours indicating the level of importance.<br>
	The user can easily identify the tasks which are more important.

[Work in progress]
### 2.8. Completion of a task
	Click on the `...` bar available at the side of the task.<br>
	Select the option of completed and confirm your choice before clearing.

[Work in progress]
### 2.9. Overdue tasks
	Under the `Overdue` tab, you can see the list of overdue tasks that you have yet to clear. 
   > Editing the deadline will result in the relocation of the task out of the `Overdue` tab, until it has reached its due date.

[Work in progress]
### 2.10. Saving the tasks
	Upon creation of tasks, the tasks will be automatically saved in the folder where the program is held in.
   > Do not erase the saved data as it will result in a complete loss of data that cannot be recovered by the application itself.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the application in the other computer and overwrite the empty data file with the file <file_name.extension> that contains the data of your current doTASK manager.

**Q**: If I had keyed in the wrong details for a recently added task, how do I change it?<br>
**A**: Head to the â€œrecently addedâ€� tab and select the task that you have just added (it should be at the top of the list). Click on the task and edit accordingly. Click done after completion.

**Q**: How do I clear all tasks?<br>
**A**: Under the `help` button, there will be a reset feature. It automatically wipes the saved data.

**Q**: How do I clear all the tasks under a certain tag?<br>
**A**: Navigate to `Priority` tab, click the `Clear All` button. There will be a confirmation prompt upon doing so, click confirm to clear the tasks of the tags.

## 4. Command Summary

* **Add** : `add TASK_NAME d/DEADLINE p/PRIORITY_LEVEL [i/ANY_INFO] [t/TAGS]...`<br>
	e.g. `add Sleep d/27 December 2018 p/1 i/Sleep is good t/Home`
	
* **Help** : `help` 
	
* **List All** : `list all`

* **List by Deadlines** : `list deadlines`

* **List by Priority** : `list priority`

* **List by Tags** : `list t/TAGS [MORE_TAGS]`<br>
	e.g. `list t/CS2103 t/Work t/School`
	
* **Switching between tabs** : `switch`
	
* **Edit** : `edit`<br>
	e.g. `edit i/3 n/Buy a house`
	
* **Done** : `done`<br>
	e.g. `done 1`
 
 