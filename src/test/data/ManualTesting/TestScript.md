# Today - Test Script

By : `T09B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`

Hi Tester! This test script guides you through the basic steps of using **Today**. We hope you enjoy!

---

1. [Quick Start](#1-quick-start)<br>
2. [Loading Sample Data](#2-loading-sample-data)<br>
3. [Adding Tasks](#3-adding-tasks)<br>
4. [Viewing Tasks](#4-viewing-tasks)<br>
5. [Managing Tasks](#5-managing-tasks)<br>
6. [Managing Tags](#6-managing-tags)<br>
7. [Undo/Redo Commands](#7-undoredo-commands)<br>
8. [Advanced Usage](#8-advanced-usage)<br>
9. [Hotkeys](#9-hotkeys)<br>
10. [Miscellaneous](#10-miscellaneous)

## 1. Quick Start

Along with this TestScript.md, you should also have received [T09-B1][Today].jar and SampleData.xml. Try to keep these three files in a new folder separate from your other files to avoid cluttering up your workspace.

Now, double click on [T09-B1][Today].jar to start the application. You should see a few new files created, as well as a `data` folder.


## 2. Loading Sample Data

To load the sample data:

1. On your computer, navigate to the folder containing SampleData.xml.
1. Rename SampleData.xml to taskmanager.xml.
2. In your running application, type `import <dir_location>`, where dir_location should be the directory that SampleData.xml, now taskmanager.xml, is located. For example, if SampleData.xml was located in the same directory as your jar file, you can type `import .` to import the data.
3. This might take a few seconds, but once it's done, you should see your task manager populated with tasks from the sample data.

Note that the import command will not make changes to the imported file, but will instead write changes to a different location. If you want the changes to be written to that specific file, type `usethis <dir_location>` and hit enter.

> dir_location refers to the directory and not the file path. If the file is located in the subdirectory `subdir`, use `./subdir` instead of `./subdir/taskmanager.xml`.

## 3. Adding Tasks

To add a simple task
|Command|Result|
| --- | --- |
|`add Study for CS2106` | A new task "Study for CS2106" is added to the future task list. |

>**For datetimes**
If no date is provided, we assume you mean today. If no time provided, we assume you mean 11:59pm.

To add a task with deadline:
|Command|Result|
| --- | --- |
| `add Running due 8pm` | A new task "Running" due 8pm today is added to the today task list.|
| `add Galactus Assessment due Monday` | A new task "Galactus Assessment" due 11:59pm coming Monday is added to the future task list.|
| `add Mark Coursemology due tomorrow 3pm` | A new task "Mark Coursemology" due 3pm tomorrow is added to the future task list.|
| `add Submit lab assignment due 12/06/17 3pm` | A new task "Submit Lab Assignment" due 3pm 12/04/17 is added to the future task list.|

To add a task with tag:
|Command|Result|
| --- | --- |
|`add Meet pat to discuss lab #cs2106 #school`|A new floating task "Meet pat to discuss lab" is added to the future task list with the tags "cs2106" and "school".|

To add a task with tag and deadline:
|Command|Result|
| --- | --- |
|`add Meet john to discuss lab #cs2106 #school due tomorrow`|A new task "Meet john to discuss lab" due tomorrow is added to the future task list with the tags "cs2106" and "school".|

To add an event:
|Command|Result|
| --- | --- |
|`add Meet john for lunch from 4pm to 6pm`|A new event "Meet john for lunch" from 4pm to 6pm today is added to the today task list.|
|`add Meet jess for dinner #date from 7pm to 8pm friday`|A new event "Meet jess for dinner" from 7pm to 8pm on Friday is added to the future task list.|
|`add Strata Conference #data from 03/10/17 to 07/10/17`|A new event "Strata Conference" from 03/10/17 to 07/10/17 is added to the future task list with the tag "data".|
|`add Overseas Conference #data from 03/10/17 4pm to 07/10/17 9pm`|A new event "Overseas Conference" from 4pm on 03/10/17 to 9pm on 07/10/17 is added to the future task list with the tag "data".|

>For events, if date is specified for ending time, you need to also specify date for starting time.

## 4. Viewing Tasks

To view specific tasks/events, use the find command. This performs a search for all tasks/events with title or tags containing any of the specified keywords.
|Command|Result|
| --- | --- |
|`find john`|Only tasks/events with "john" in its title or tag will be shown.|
|`find conference data`|Only tasks/events with "conference" or "data" in its title or tag will be shown.|

To find tasks by due date, we have the find due command.
|Command|Result|
| --- | --- |
|`find due tomorrow`|Only tasks due tomorrow will be shown.|
|`find due friday`|Only tasks due coming Friday will be shown.|
|`find due 12/04/17`|Only tasks due 12/04/17 will be shown.|

To view completed tasks:
|Command|Result|
| --- | --- |
|`listcompleted`|A new window will pop up, showing your completed tasks/events.|

To view all tasks:
|Command|Result|
| --- | --- |
|`list`|You will return to your original view, with the Today and Future task lists filled with all your tasks.|

## 5. Managing Tasks

Mark a task to be done today or nottoday:
|Command|Result|
| --- | --- |
|`today F1`|The task with index `F1` is shifted from the Future task list to the Today task list.|
|`nottoday T15`|The task with index `T15` is shifted from the Today task list to the Future task list. (Note: This only works if T15 is due in the future but was moved using the `today` command)|

Mark a task as done or not done:
|Command|Result|
| --- | --- |
|`done F1`|The task with index `F1` is marked as done and shifted to the completed task list.|
|`notdone C1`|The completed task with index `C1` is marked as not done and shifted back to either the today or future task list depending on its due date.|

Editing a task:
|Command|Result|
| --- | --- |
|`edit T1 THIS IS EDITED`|The task with index `T1` is now named "THIS IS EDITED".|
|`edit T1 #heyyy`|The task with index `T1` is now tagged only with "heyyy".|
|`edit T1 due tomorrow`|The task with index `T1` is now due tomorrow.|
|`edit T1 from 3pm to 8pm`|The task with index `T1` is now from 3pm to 8pm today.|
|`edit T1 clean house due 04/05/17 6pm #schoolwork #CS2103`|The task with index `T1` is now named "clean house" due on 04/05/17 at 6pm with tags "schoolwork" and "CS2103".|

Deleting a task:
|Command|Result|
| --- | --- |
|`delete T1`|The task with index `T1` is now deleted.|

## 6. Managing Tags

Renaming a tag:
|Command|Result|
| --- | --- |
|`renametag school nus`|All tasks previously with the tag "school" has been retagged with "school".|

Deleting a tag:
|Command|Result|
| --- | --- |
|`deletetag nus`|All tasks previously with the tag "nus" no longer has that tag.|

## 7. Undo/Redo Commands

Undoing/Redoing a command:
|Command|Result|
| --- | --- |
|`undo`|Undo the previous command.|
|`redo`|Redo a recently undone command. (Only works when the previous command is undo)|

## 8. Advanced Usage

Save to/Read from different location:
|Command|Result|
| --- | --- |
|`saveto subdir`|Moves taskmanager.xml to a subdirectory "subdir".|
|`usethis subdir`|Uses the taskmanager.xml from the subdirectory "subdir".|

Export/Import:
|Command|Result|
| --- | --- |
|`export anotherdir`|Writes a new taskmanager.xml to a subdirectory "anotherdir".|
|`import anotherdir`|Imports data from the taskmanager.xml found in the subdirectory "anotherdir".|

## 9. Hotkeys

|Command|Result|
| --- | --- |
|<kbd>Ctrl</kbd> + <kbd>1</kbd>|Toggles the today task list.|
|<kbd>Ctrl</kbd> + <kbd>2</kbd>|Toggles the future task list.|
|<kbd>Ctrl</kbd> + <kbd>↑</kbd>|Scrolls up.|
|<kbd>Ctrl</kbd> + <kbd>↓</kbd>|Scrolls down.|

## 10. Miscellaneous


|Command|Result|
| --- | --- |
|help|See the help page.|
|exit|Close the application.|
