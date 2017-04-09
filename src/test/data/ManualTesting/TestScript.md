# Test script for manual testing

## Loading sample data for KIT

1. Ensure that you put `SampleData.xml` inside the `data/ManualTesting/` folder.
2. Start `KIT.jar` by double clicking it.
3. Type `load data/ManualTesting/SampleData.xml`.

## Loading sample data for Google Calendar
Any Google Calendar related commands require proper credentials. This will open a request on the default browser. This is only needed for the first time.

You can use the google calander account we created for testing. There are some events already in the calendar.

 Username: **`jimkeepsittidy@gmail.com`**
 Password: ****

### Brief Introduction:
In the sample data, there are 50 pre-loaded tasks. 30 task are `undone` and 20 task are `done`.

`undone` tasks are shown by default. 30 `undone` tasks will be shown when first loading up KIT. By **typing** `list` in the command box, all 50 tasks will be shown in the task list column.

`Done` task's name will be green and a tick will appear beside the task name. Task names that are not green in colour are `undone` task.

If google calander is not needed, simply click and drag the middle bar slider loacted at the center to the right to hide the login page.

**To test any command, type what is formatted like this `command` and hit <kbd>enter</kbd>. Results will be displayed in the result box below the command box.**

## Help Command

**Type `help`**

##### Expected Results:

User guide is displayed in a new window.

**Press <kbd>F1</kbd>**

##### Expected Results:

User guide is displayed in a new window.

**Type `helpf`**

##### Expected Results:

A table of command formats is displayed in a new window.

**Press <kbd>F3</kbd>**

##### Expected Results:

A table of command formats is displayed in a new window.

**Type `man add`**

##### Expected Results:

add: Adds a task to the task manager.
Parameters: NAME s/START e/END r/REMARK l/LOCATION  [t/TAG]...
Example: add John owes money s/02-03-2017 e/03-03-2017 r/john owes me $100 l/john's house t/friends t/owesMoney

## Date and Time format

##### Before proceeding to add a task into KIT, do take a look at the table that shows the date format supported by KIT. KIT supports many formats for date input.


| Date Format     | Example(s)           |
|-----------------|----------------------|
| Month/Day/Year  | 10/01/2017  10-01-2017         |
| Words           | 1 Oct 2017           |
| Month Day       | Oct 1                |
| Day of the week | Wed, Wednesday       |
| Relative date   | today, tomorrow, next wed |

> * If no year is specified, it is assumed to be the current year.
> * The day of the week refers to the following week. For example, today is Sunday (30 Oct). will interpret Wednesday and Sunday as 2 Nov and 6 Nov respectively (a week from now).


<br>

| Time Format     | Example(s)                              |
|-----------------|-----------------------------------------|
| 24 Hour format  | 2359, 1130                                  |
| Hour:Minute     | 10:30                                   |
| Relative time   | this morning, this afternoon, tonight, am, pm   |

> Tasks can be entered without a time input.
>
> KIT will assume that task that are entered without time will take the time when the task was being added to KIT.
>
**Date the are shown in KIT are all represented in mm/dd/yyyy.**


## Select
##### To show more task at once, only task name and done/undone status are shown for each task. To view the rest of the task information, select the task to expand the task.

### 1. Select to expand task information
**Type `select 1`**

##### Expected Results:
* Task with index  `1` is expanded to show the task details to user.

### 2. Select to hide task information
**Type `select 1` after task 1 is already opened.**

##### Expected Results:
* Task with index `1` closes.

### 3. Select a task with invalid index
**Type `select -1`**

##### Expected Results:
* Commandline input section turn red in colour to reflect the error.
* Remark/status displays:
  >Invalid command format!
  >select: Selects the task identified by the index number used in the last task listing.
  >Parameters: INDEX (must be a positive integer)
  >Example: select 1

Note: If there are only 5 task shown, index 6 is invalid.

## Using shortcuts

Use <kbd>shift</kbd> + <kbd>up</kbd> and <kbd>shift</kbd> + <kbd>down</kbd> to scroll up and down the tasklist.


## Add

To add a task, you have to begin your command with the keyword  **`add`**

The prefixes used to represent different information are listed below

**Prefix format**
| Task information | Prefix format |
|------------------|---------------|
| _**Start Datetime**_       | s/            |
| _**End Datetime**_         | e/            |
|_**Location**_          | l/            |
|_**Remarks**_           | r/            |
|_**Tags**_              | t/            |

> * _**Name**_ of task should be entered right after the **`add`** comand word, separated by a space.
> * Except for _**Name**_ of task, all other information are optional.
> * _**Start Datetime**_ must be before _**End Datetime**_.
>   * E.g. Start: 4/1/2017 12:00; End: 4/1/2017 12:00 is **not** valid.
>   * E.g. Start: 4/1/2017 12:00; End: 4/1/2017 13:00 is valid.

### 1. Add a floating task
**Type `add buy milk`**

##### Expected Results:
* A new task with _**name**_ `buy milk` is added to the top of the tasklist.

### 2. Add a task with start and end date
**Type `add project meeting s/11-10-2017 2pm e/11-10-2017 4pm`**

##### Expected Results:
* A new task with _**name**_ `project meeting`is added to the top of the tasklist.
* With the _**start date**_ and _**end date**_ listed as `11/10/2017`, time listed for start date will be `2pm` and time listed for end date will be `4pm`.

### 3. Add a task with location and remarks
**Type `add meet friends for dinner s/next tuesday 7pm e/next tuesday 9pm l/clementi mall r/korean food`**

##### Expected Results:
* A new task with _**name**_ `meet friends for dinner`is added to the top of the tasklist.
* The _**location**_ is `clementi mall` and _**remark**_ is `korean food`.

### 4. Add a task with tags
**Type `add badminton game with friends s/next wed 7pm e/next wed 9pm l/CC badminton court t/friends t/sports`**

##### Expected Results:
* A new task with _**name**_ `badminton game with friends` is added to the top of the tasklist.
* The location is `CC badminton court` and two _**tags**_ are displayed beside the task _**name**_.
* The tags are `friends` and `sports`.

### 5. Add a task with start date/time later than end date/time

**Type `add meet john to discuss about work s/next friday 10am e/next tuesday 7pm`**

##### Expected Results:
* Commandline input section turn red in colour to prompt the error of _**start date**_ is later than _**end date**_.
*  Remark/status section displays `Start date/time must be earlier than end date/time`.

## Done and Undone

##### To mark a task as done, use the done command. Done task has a green title with a tick. All tasks added to KIT are not done by default.
### 1. Done a task
**Type `done 1`**
##### Expected Results:
* Task with the index `1` is marked as `done`.

### 2. Undone a task
**Type `undone 1`**
##### Expected Results:
*  Task with the index `1` is marked as `undone`.

#### Note:
* `index` passed in for these commands must be positive and valid, meaning there must be tasks with the given `index` in the tasklist.
* Repeated Done commands on the same task should not change its status. For example, using done on an already done command should not change it status. The same goes for Undone command.

## Undo and Redo

##### Undo reverses the last changes to the data (e.g.`add`,`edit`,`delete`,`done`,`undone`). Redo simply reverses the last Undo action.
**1. Type `done 1`**
**2. Type `undo`**
##### Expected Results:
* Task with the index `1` is marked as `undone`.

**Type `redo`**
##### Expected Results:
* Task with the index `1` is marked as `undone`. (_reverses the undo_)

##### Note:
* Mutiple undo is supported, but the limit is 10 times.

## Edit

##### Edit changes the specified details of a selected task.

### 1. Edit a task's name and tag
**Type `edit 1 Default t/testing`**

##### Expected Results:
* Task with index `1` has its _**name**_ changed to `Default` and its _**tag(s)**_ changed to `testing`.
* Remark/status displays:
    * Edited Task: **Default Testing**
    * Start Date:  _as before_
    * End Date: _as before_
    * Remark: _as before_
    * Location: _as before_
    * Tags: **[testing]**

### 2. Edit a task's end date
**Type `edit 1 e/May 1 2018`**
##### Expected Results:
* Task with index `1` has _**end date**_ changed to `5/1/2018`.
* Remark/status displays:
    * Edited Task: _as before_
    * Start Date:  _as before_
    * End Date: ** 5/1/2018 _(current time)_, 1 year from now**
    * Remark: _as before_
    * Location: _as before_
    * Tags: _as before_

##### Note: The index for `edit` must be positive and valid.

## Find
##### Find searches for tasks with matching keywords and matching dates if dates are given.
### 1. Find by keyword
**Type `find cs`**
##### Expected Results:
* Task(s) with `cs` keyword are listed.
* Remark/status displays:
    * _Number of tasks_ tasks listed! e.g. _2 tasks listed!_
    * If using sample data, 22 task will be found. May differ if you entered other commands not described.

### 2. Find by date
**Type `find 3/5/2017`**
##### Expected Results:
* Task(s) with `3/5/2017` date are listed.
* Remark/status displays:
    * _Number of tasks_ tasks listed!  e.g. _2 tasks listed!_
    * If using sample data, 1 task will be found. May differ if you entered other commands not described.


### 3. Find exact keywords
**Type `findexact cs2102 final`**
##### Expected Results:
* Remark/status displays:
    * _Number of tasks_ tasks listed! e.g. _2 tasks listed!_
    * SampleData.xml should show _0 task listed!_

**Type `findexact cs2102 finals`**
##### Expected Results:
* Task(s) with both `cs2102` and `finals` will e listed.
* Result/status displays:
    * _Number of tasks_ tasks listed! e.g. _2 tasks listed!_
    * SampleData.xml should show _1 tasks listed!_
## List
##### List displays all the tasks fulfilling certain criteria (i.e. `done`,`undone`,`tag` (_with a certain tag name_)).

### 1. List all tasks

**Type `list`**
##### Expected Results:
* All tasks are listed.
*  Remark/status displays:
    * Listed all tasks!
    * 54 tasks expected.

### 2. List by done
**Type `list done`**
##### Expected Results:
* All `done` tasks are listed.
*  Remark/status displays:
    * _Number of tasks_  done tasks listed! e.g. _2 done tasks listed!_
    * 21 tasks expected.

##### List by undone is similar. The command is `list undone`.

### 3. List by a certain tag
**Type `list tag homework`**
##### Expected Results:
* All tasks with the tag `homework` are listed.
* Remark/status displays:
    * _Number of tasks_tasks with tag "testing" listed! e,g. _2 tasks with tag "testing" listed!_
    * 5 tasks expected.

### 4. List by floating task
**Type `list float`**
#### Expected Results:
* All floating task will be listed.
* Remark/status displays:
    * _Number of tasks_tasks floating task listed! e,g. _2 floating task listed!_
    * 1 tasks expected.

## Delete
##### Delete removes a task with the given `index` from tasklist.
### 1. Delete with an index
**Type `Delete 1`**
##### Expected Results:
* Task with index `1` is removed.
* Remark/status displays:
    * Deleted Task: _as before_
    * Start Date:  _as before_
    * End Date: _as before_
    * Remark: _as before_
    * Location: _as before_
    * Tags: _as before_
##### Note: The index for `delete` must be positive and valid.

## Save/Load
##### Save stores the file to a specified location. Load reads a file from a specified location.

### 1. Save to a specified location
**Type `save kit.xml`**
##### Expected Results:
* Data is saved to a file `kit.xml`, in the same folder as the `KIT.jar`.
* Current open file will be changed to `kit.xml`. Changes will be made to this file.
* Remark/status displays: `File save at: kit.xml`.

### 2. Load from a specified file
**Type `load kit.xml`**
##### Expected Results:
* Data is read from a file `kit.xml` from the same folder as the `KIT.jar`.
* Remark/status displays: `File loaded from: kit.xml`.

## Change theme
##### Change the theme of KIT interface, a total of three theme are available: deafault, dark and light.

### 1. Change theme of interface to dark theme
**Type `changetheme dark`**
##### Expected Results:
* Remakrs/status displays : `Theme is successfully changed to dark. Restart KIT to view the changes.`
* Restart KIT to see the updated changes
* Once KIT is restarted, a dark theme interface is applied to user.
##### Change theme to light or default uses the same command. Either `changetheme light` or `changetheme dafault`.

## Smart Add

**Ensure computer is connected to internet for commands that works with Google Calendar. These commands are smartadd, getgoogle and postgoogle. Google calendar credentials will be requested for the first time.**

#### Able to add task into KIT quickly and easily without the use of most prefixs excpet for _**tags**_ and _**remark**_.

### 1. Add a task with name, location and start time/date

**Type `sa going movie with family tomorrow at town at 4pm`**
##### Expected Results:
* A new task with _**name**_ `going to movie with family at town` is added to the top of the tasklist.
* The _**location**_ is displayed as town.
* The date listed is the next day at 4pm.

### 2. Add a task with name, location, start and end date/time

**Type `sa meeting with client next wed at office from 2pm to 4pm`**
##### Expected Results:
* A new task with _**name**_ `meeting with client at office` is added to the top of the tasklist.
* The _**location**_ is office.
* The date listed is the next wednesday with start time at 2pm and end time at 4pm.

### 3. Add a task with name, location, start date/time with remarks and tags.

**Type `sa submit documents for processing at cpf building this friday at 2pm r/form 1 and form 2 t/cpf t/documents`**
##### Expected Results:
* A new task with _**name**_ `submit documents for processing at cpf building` is added to the top of the tasklist.
* The _**location**_ is office.
* The date listed will be the next coming friday.
* The start time will be 2pm and end time is 3pm.
* _**Remark**_ is form 1 and form 2.
* _**Tags**_ are cpf and documents.

## Post new task to Google Calander from KIT
#### Update google calander with the task that was added to KIT.

### 1. Post Task to google calander
**Type `pg 1`**

##### Expected Results:
* Google calander is updated with the task with index `1` in the current tasklist.
* Google calander webpage updates, and shows updated task in google calander.
* The process can take a few seconds before the update is shown on google calander.

## Clear
#### clear all task in local KIT database.

### 1. clear
**Type `clear`**
##### Expected Results:
* All task in KIT will be deleted, a empty tasklist will be shown.
* Remakrs/status displays : KIT has been cleared!



## Import events from Google Calander to KIT


### 1. Import events from Google Calendar.
**Type `gg`**

##### Expected Results:
* Tasks in google calander are imported into kit local database, current tasklist is updated.
    * New tasks are imported from google and existing tasks are updated accordingly.


## Exit KIT

### 1. Exit
**Type `exit`**
##### Expected Results:
* KIT closes.


#### That is the end of this manual test script.

#### Please feel free to contact us with any suggestion or feedback, at our github page https://github.com/CS2103JAN2017-F14-B2/main.


### Thank you and have a nice day!
