# doTASK - User Guide

By : `Miao Ling` , `Ian` , `Qi Xiang` and `Dylan` - `[W09-B4]` Since : `Feb 2017`

---

1. [Quick Start] (#quick-start)
2. [Features] (#features)
3. [FAQ] (#faq)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.

1. Download the latest version of `doTASK.jar` from [releases] tab.

2. Copy the file to the folder you want to use as the home folder for your doTASK.

3. Double-click on the file to start the application. The GUI should appear in a few seconds.<br>
> <img src="images/startup_screen.jpg" width="600">

4. Get started by adding your first task that you have in mind! Refer to the "2. Features" for further instructions. <br>
> <img src="images/add_screen.jpg" width="600">


5. After step 5, you can view your task in 3 different tabs.

   > **Today/Date**
   >
   > * Shows you the list of tasks you have for the specified date. By default, it will show the tasks for today. Click left/right arrow to navigate between the dates.
   > <img src="images/today_screen.jpg" width="600">
   >
   > **Overview**
   >
   > * Shows you an overall view of the dates. The highest priority tasks will be shown as a preview for every single date. Scroll to navigate between the dates.
   > <img src="images/overview_screen.jpg" width="600">
   >
   > **Priority**
   >
   > * Shows you 4 options of priority tabs, from highest to lowest, which are indicated by different colours. Selecting the tab allows you to view the list of tasks that is under that priority.<br>
	> Eg. By using `switch` feature, you can select the `Highest Priority` , it will bring you to the list of tasks that you have in it.<br>
   > <img src="images/priority_screen.jpg" width="600">1

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
   > `DEADLINE` will be in the format of "date month (indicated by first three letters) year"
   > `PRIORITY_LEVEL` should be defined by integers 1 (high) to 4(low).<br>
   > `ANY_INFO` allows you to key in details with regards to the task, optional.<br>
   > `TAGS` allows you to assign tags to the tasks, optional.

Examples:
* add CS3230 Assignment 1 d/8 Jan 2018 p/1 i/How to do? t/School t/CS3230
* add Buy Milk d/23 Feb 2017 p/4 t/Chores

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

Shows a list of the tasks sorted by a stated priority level, from 1 - 4.<br>
Format: `list priority PRIORITY_LEVEL`
> Tasks can be given any priority level from 0 to 3.
> Tasks with PRORITY_LEVEL priority will be displayed.

Examples:
* `list priority 1`
* `list priority 2`

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
* `edit i/4 n/Exercise`

### 2.5. Shifting tabs: `switch`
Switch the current tab to the next tab.
Format: `switch`
> There will be 3 tabs, which can be toggled using this command.

### 2.6. Deleting a task: `delete`
Deletes the specified task.
Format: `delete INDEX_NUMBER`

> Task labeled by INDEX_NUMBER will be deleted from the list.<br>
> INDEX_NUMBER of task is shown as according to the current tab.

Examples:
* `delete 2`
* `delete 5`

### 2.7. Completion of a task: `complete`

Marks the specified task as complete.<br>
Format: `complete INDEX_NUMBER`
> The task at INDEX_NUMBER will be marked as completed.<br>
> INDEX_NUMBER of task is shown as according to the current tab.

Examples:
* `complete 2`
* `complete 5`

### 2.8. Track overall progress: `progress`

Shows all the tasks completed over time and also specifying the tasks overdued.<br>
> A general overview of performance/efficiency based on the usage of the task manager will be shown.<br>

Format: `progress NUMBER_OF_DAYS`
> NUMBER_OF_DAYS is a interger which indicates the your progress for these past days (excluding today).<br>
> It will provide an analysis on the number of task that you have completed/overdue.

Examples:
* `progress 7`

### 2.9. Search for tasks: `search`

Searches for tasks based on keywords by user's input.<br>

Format: `search KEYWORD`
> A valid input needs to be keyed in. No special characters such as ASCII whitespace is allowed.<br>
> A popout will appear to show you the list of tasks that contains the stated `KEYWORD`

Examples:
* `search potato`

### 2.10. Undo previous action : `undo`

Undos previous action that user made.<br>
Format : `undo`
> Any previous valid input will be reversed.
> Only 1 input will be reversed at a time.

### 2.11. Redo previous action : `redo`

Redos previous `undo` that user made.<br>
Format : `redo`
> Any previous `undo` will be reversed.

### 2.12. Saving the tasks

Upon creation of tasks, the tasks will be automatically saved in the folder where the program is held in.
> Do not erase the saved data as it will result in a complete loss of data that cannot be recovered by the application itself.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the application in the other computer and overwrite the empty data file with the file <file_name.extension> that contains the data of your current doTASK manager.

**Q**: If I had keyed in the wrong details for a recently added task, how do I change it?<br>
**A**: Head to the recently added tab and select the task that you have just added (it should be at the top of the list). Click on the task and edit accordingly. Click done after completion.

**Q**: How do I clear all tasks?<br>
**A**: Under the `help` button, there will be a reset feature. It automatically wipes the saved data.

**Q**: How do I clear all the tasks under a certain tag?<br>
**A**: Navigate to `Priority` tab, click the `Clear All` button. There will be a confirmation prompt upon doing so, click confirm to clear the tasks of the tags.

**Q**: Will I get reminded if the deadline of certain high priority tasks are near?<br>
**A**: For the tasks that is due in the next 24 hours, it will be distinguished by the colours on its name.

## 4. Command Summary

* **Add** : `add TASK_NAME d/DEADLINE p/PRIORITY_LEVEL [i/ANY_INFO] [t/TAGS]...`<br>
	e.g. `add Sleep d/27 December 2018 p/1 i/Sleep is good t/Home`

* **Help** : `help`

* **List All** : `list all`

* **List All by Deadlines** : `list deadlines`

* **List All by Priority** : `list priority`

* **List by Specific Priority** : `list priority PRIORITY_LEVEL`
	e.g. `list priority 1`

* **List by Tags** : `list t/TAGS [MORE_TAGS]`<br>
	e.g. `list t/CS2103 t/Work t/School`

* **Switching between tabs** : `switch`

* **Edit** : `edit`<br>
	e.g. `edit i/3 n/Buy a house`

* **Delete** : `delete`<br>
	e.g. `delete 1`

* **Completion of task** : `complete`<br>
	e.g. `complete 1`

* **Checking progress/performance** : `progress NUMBER_OF_DAYS`<br>
	e.g. `progress 7`

* **Search for tasks** : `search KEYWORD`<br>
	e.g. `search potato`
	
* **Undo previous action** : `undo`<br>

* **Redo previous action** : `redo`<br>


