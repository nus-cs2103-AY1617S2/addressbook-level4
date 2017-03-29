# KIT User Guide:

1. [Quick Start](#1-quick-start)
2. [Features](#2-features)
3. [FAQ](#3-faq)
4. [Command Summary](#4-command-summary)

## 1. Quick start
1. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

2. Download the latest `KIT.jar` from the [releases](../../../releases) tab.
3. Copy the file to the folder you want to use as the home folder for KIT.
4. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

5. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
6. Some example commands you can try:
   * **`list`** : lists all task
   * **`add`**`  do 2103 project r/waiting for john's reply s/03-03-2017 l/nus soc t/school` : <br>
     adds a task named `do 2103 project` to KIT.
   * **`delete`**` 3` : deletes the task with index 3 shown in the current list
   * **`exit`** : exits the app
7. Refer to the [Features](#features) section below for details of each command.<br>

## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `[SQUARE_BRACKETS]` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 2.1.1 Viewing help : `help`

If you are not sure where to start, take a sneakpeek at the help sheet!

Format: `help [COMMANDNAME]`, `h [COMMANDNAME]`, `man [COMMANDNAME]`, `manual [COMMANDNAME]`

> * If COMMANDNAME is entered, displays the help message for that specific command
> * If COMMANDNAME is not entered, displays user guide in another window.
> * Tips: You can also bring up the help window by pressing F1.

### 2.1.2 Viewing quick overview of command formats : `helpf`

Format: `helpformat`, `helpf`, `summary`, `hf`

> * Helpf shows a quick summary of command formats.
> * Tips: You can also bring up the help window by pressing F3.

### 2.2 Adding a task: `add`

If you are ready to start exploring KIT, do so by adding a task!

Adds a task to KIT. New tasks are [not done] by default. <br>

Format: `add NAME [r/REMARKS] [s/STARTDATE] [e/ENDDATE] [l/LOCATION] [t/TAG]...`

> * If you need to type the character /, put a \ before it to avoid t/ getting recognized as tag. eg. carrot\/cabbage <br>
> * Tasks can have any number of tags (including 0).

Here are some examples:

* `add do 2103 project r/waiting for john’s reply e/next tuesday l/nus soc t/school`
* `add buy presents s/two week before christmas e/christmas eve l/town t/shopping`
* `add buy groceries r/cabbage and broccoli s/this weekend t/personal`
* `add feed the cat\/dog t/personal t/family e/tomorrow 8am`


Supported Date/Time formats:
> * MM/DD/YY, MM-DD-YY and also dates such as 12 march 17.
> * HH:mm in 24 hour format or hh:mm am/pm.
> * Time without am/pm are assumed to be in 24 hour format.
> * You may include either both date and time seperated with a space or just either one or even none at all.

### 2.3.1	Listing of all task: `list`

If you want to view all the tasks at once, simply use the list function(s).

Description: Shows a list of all the tasks in KIT. <br>

Format: `list`, `ls`, `l`

### 2.3.2	Listing of all done task: `listdone`
Description: Shows a list of all the tasks that has been completed. <br>

Format: `listdone`, `ld`

### 2.3.3	Listing of all not done task: `listnotdone`
Description: Shows a list of all the tasks that has yet to be completed. Sorted by due date. <br>

Format: `listnotdone`, `listundone`, `lnd`

### 2.3.4	Listing of all task under a tag: `listtag`

We shouldn't mix personal life with work, we understand that. If you want to see all work-related tasks only, you can use the `listtag` function.

Description: Shows a list of task by the specified tag. <br>

Format: `listtag TAGNAME`, `listag TAGNAME`, `ltag TAGNAME`, `lt TAGNAME`

### 2.4	Marking a task as done: `done`

Congratulations!
Now that you have completed a task and would like to mark it as done, you can do so easily by using the `done` function.

Description: Marks the task at the specified `INDEX` as "Done". <br>

Format: `done INDEX`, `d INDEX`

> * The index refers to the index number shown in the listing.
> * The index **must be a positive integer** 1, 2, 3, ...

### 2.5	Marking a task as undone: `undone`

More follow ups for your task? It's alright, you can easily mark it as "Not Done" again.

Description: Marks the task at the specified `INDEX` as "Not Done". <br>

Format: `undone INDEX`, `notdone INDEX`, `ud INDEX`, `nd INDEX`

> * The index refers to the index number shown in the listing.
> * The index **must be a positive integer** 1, 2, 3, ...

### 2.6	Deleting a task: `delete`

If you would like to remove a task from KIT, simply use the `delete` function.

Description: Deletes the specified task from KIT.<br>

Format: `delete INDEX`

> * Deletes the task at the specified `INDEX`.
> * The index refers to the index number shown in the listing.
> * The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `delete 2`<br>
  Deletes the 2nd task of the current listing in KIT.

* `find homework` <br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### 2.7.1	Undo: `undo`

Made a mistake? No worries. You can retract it by using the `undo` command.

Description: Undo the last command.<br>

Format: `undo`, `uhoh`

> Able to undo up to 10 times.

### 2.7.2	Redo: `redo`

Along with undo comes redo. Just in case you changed your mind, you can also retract your `undo` by using the `redo` command.

Description: Redo the last undo. <br>

Format: `redo`

> Able to redo up till 10 times.
> If you make any new commands, you will not be able to do any more redo.
> Work similarly to the page back and page forward button in your browser.

### 2.8 Editing a task: `edit`

If you want to edit the details of a task, the `edit` command allows you to do that.

Description: Edits an existing task in KIT. <br>

Format: `edit INDEX [NAME] [r/REMARKS] [s/STARTDATE] [e/ENDDATE] [l/LOCATION] [t/TAG]...`

> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task’s tags by typing `t/` without specifying any tags after it.

Examples:

* `edit 1 r/finish up user guide l/home`<br>
  Edits the remark and location of the 1st task to be `finish up user guide` and `home` respectively.

* `edit 2 debug add command t/`<br>
  Edits the name of the 2nd task to be `debug add command` and clears all existing tags.

### 2.9.1 Finding a task:`find`

As you start to use KIT, you may find that you have too many task to scroll through all of them to find a specific task.
 Not to worry, we have a efficient `find` command that can help. Use it along with any keyword you can think of related to that task.

Description: Finds task whose names or remark contain **at least one** of the given keywords. <br>

Format: `find KEYWORD [MORE_KEYWORDS]`, `f KEYWORD [MORE_KEYWORDS]`

> * The search is case insensitive. e.g `project` will match `Project`
> * The order of the keywords does not matter. e.g. `do project` will match `project do`
> * Both the name and remarks are searched.
> * Substrings will be matched. e.g. `project` will match `projects`
> * Task matching **at least one** keyword will be found.
    e.g. `project` will match `do project` and `do homework`

Examples:

* `find homework`<br>
  Finds `do homework`

* `find homework school`<br>
  Finds any task having names or remarks with `homework`,  or `school`

### 2.9.2 Finding with exact keyword(s):`findexact`

Description: Finds task whose name or remark matches **all** the keyword(s). <br>

Format: `findexact KEYWORD...`, `fexact KEYWORD...`, `finde KEYWORD...`, `fe KEYWORD...`

> * The search is the same as to `find` except it only matches tasks that contains **all** keywords.
e.g `do project` will match `do School project` but not `do homework`

### 2.10 Google Calendar.

If you are using Google Calendar, we support some Google Calendar operations too.
To use Google Calendar with KIT, we first require your permission to read and add event to your Google Calendar.

> * The request will open in your default browser.
> * Log in to your Google account and approve the request to use this feature. 
> * You only need to do this for the first time.

### 2.10.1 Get Events from Google Calendar: `gg`

The first thing you might want to do with is to import your events to KIT. You can do this with the `getgoogle` command.

Description: Add your upcoming Events from your Google Calendar to KIT. <br>

Format: `getgoogle`, `gg`

> This command will only grab upcoming Events and ignore duplicate Events.

### 2.10.2 Post Task to Google Calendar: `pg`

Other than getting events from Google Calendar, you are also able to add a task from KIT as an Event to your calendar.

Description: Add the specified task to your Google Calendar. <br>

Format: `postgoogle [INDEX]`, `pg [INDEX]`

> If INDEX is specified and correct, adds the selected task to calendar.
> If INDEX is not specified, adds **all** displayed task to calendar.
> Note that start date and end date must not be empty to add your event to the calendar.

### 2.11 Clearing all task: `clear`

If you would like to remove every task of the manager, just use `clear`.

Description: Clears all entries from KIT. <br>

Format: `clear`

### 2.12 Exiting the program: `exit`
Description: Exits the program. <br>

Format: `exit`

### 2.13 Specifying save location: `save`

If you wish to save the file to a custom location, you can do so using the `save` function.

Description: Specify location of save file. <br>

Format: `save PATHNAME`

### 2.14. Saving the data

You do not have to worry about saving changes, because KIT saves it for you!

In KIT, data are saved automatically after a change has been made. There is no need to save manually.

## 3. FAQ

**Q**: Does KIT support Windows 10? <br>
**A**: Yes! KIT works as long as you have Java 8.

**Q**: Will my tasks be saved after I close the application?  <br>
**A**: Yes. Your data is saved after every command, you can safely exit KIT when you are done.

**Q**: Can I have two task with the same name?  <br>
**A**: Sure, tasks with same names are allowed.

**Q**: I accidentally created two exact same tasks, how do I remove just one of them?  <br>
**A**: If you just added the task, you can do so by typing undo. Otherwise, you can use find or list to search for the duplicate tasks and delete one of them.
The other task will remain in KIT. Instructions on these commands can be found in the respective section of the commands.

**Q**: Can I manually edit the storage file?  <br>
**A**: Yes, you can. It is a xml file called kit.xml. It can be found under data\kit.xml by default. Be careful in making changes to it if you are unfamiliar with XML, the program might not start properly if the file’s format is invalid.

**Q**: I use two computers. Can I transfer my data between the two computers? <br>
**A**: Yes, you can! Your data are saved in a file called kit.xml. Just copy this file to your computer and place in under data\kit.xml. You can change the file location later using the save command.

**Q**: It’s troublesome to constantly transfer my data between two computers, is there any way for it to synchronize automatically?  <br>
**A**: Sorry, we currently do not support automatically synchronization. However, if you are using Dropbox, you can specify folders that will automatically synchronize through Dropbox. This means that you can use the save command to specify your save location in your Dropbox folder and thus get synchronization.

## 4. Command Summary

<img src="images/Command Summary 1.png" width="600">
<img src="images/Command Summary 2.png" width="600">
