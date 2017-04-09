# Test Script for Manual Testing

## Loading ToLuist with sample data

Ensure that you have the file `SampleData.json` inside the `/src/test/data/ManualTesting` folder.
Put `SampleData.json` in the same directory as `toluist.jar`
Create a copy of `SampleData.json` and name it `SampleData-copy.json`.
Start `toluist.jar` by double clicking it.


Input: `load SampleData.json`  

Result:
* *INCOMPLETE* tab is highlighted
* The descriptions for each tabs will be `INCOMPLETE (40/51)`, `
TODAY (0/51)`, `NEXT 7 DAYS (0/51)`, `COMPLETED (11/51)`, `ALL (51/51)` from left to right.
* 40 tasks will be shown in the current task list, with indexes ranging from 1 to 40.

## Command word suggestions

Input: Type `a` without entering
Result: `add` & `alias` are suggested as command words

## Command parameter suggestions

Input: Type `add ` without entering 
Result: Check that the different parameters for add command are suggested

Input: Type `add task /by tomorrow `
Result: Check that the parameters `/from` and `/to` are not suggested as they conflict with the parameter `/by`

## Command parameter option suggestions

Input: Type `add a task /priority ` without entering
Result: Check that `high` & `low` are suggested

## Add

### 1. Add Floating Task
Input: `add floating task`
Result: Floating task is added.

### 2. Add recurring task with deadline
Input: `add recurring deadline /by 14 april /repeat weekly /repeatuntil 22 april`
Result: Deadline task that repeats every friday until 22 April 2017 is added.

### 3. Add recurring event
Input: `add recurring event /repeat monthly /from today 5pm /to today 9pm`
Result: Recurring event that repeats every today's day of month from 5pm to 9pm indefinitely is added.

## Update

Input: 
1. Eyeball for this task named `go to the gym` and remember the index number, `<INDEX>`.
2. `update <INDEX> non-recurring floating task /floating /stoprepeating`

Result: Recurring event is updated to a non-recurring floating task.

Input:
1. Eyeball for this task named `go to supermarket for discount` and remember the index number, `<INDEX>`.
2. `update <INDEX> /by end of april /priority high /tags anyhow tag`

Result: Event is updated to a deadline, with high priorities. The old tags are also replaced with new tags.

## Delete

Input:
1. Eyeball for this task named `recurring deadline` and remember the index number, `<INDEX>`.
2. `delete <INDEX>`
3. `delete <INDEX>`

Result: The first delete will delete the current task, and the next occurrence of this task will be added. The second delete will delete the current task permanently, since it reaches the end of repeat period.

Input: `delete 4, 1-3`
Result: The first 4 tasks is deleted.

Input: `delete - 2`
Result: The first 2 tasks is deleted.

Input: `delete 34 -`
Result: The task from index 34 onwards is deleted.

Input: `delete -`
Result: All tasks are deleted. Note that there will still be some recurring tasks in the list since new occurrences of the tasks are created when the old ones are removed.

## Undo / Redo

Input:
1. `add www task`
2. `add zzz task`

Result:
1. `www task` is added
2. `zzz task` is added

Input: `undo`
Result: `zzz task` is removed

Input: Press <kbd>Ctrl</kbd> + <kbd>Z</kbd>
Result: `www task` is removed

Input: `redo`
Result: `www task` is added back

Input: Press <kbd>Ctrl</kbd> + <kbd>Y</kbd>
Result: `zzz task` is added back

Input: `undo 2`
Result: `www task` & `zzz task` are removed

Input: `redo 2`
Result: `www task` & `zzz task` are added back

## Switch

### 1. Switch using full tab name

Input: `switch completed`
Result: The tab is switched to *COMPLETED*. The task list now only shows completed tasks.

### 2. Switch using partial tab name
Input: `switch incom`
Result: The tab is switched to *INCOMPLETE*. The task list now only shows incomplete tasks.

### 3. Switch using tab index
Input: `switch 5`
Result: The tab is switched to *ALL*. The task list now shows all tasks

### 4. Switch using hotkey
Input: Press <kbd>Ctrl</kbd> + <kbd>1</kbd>
Result: The tab is switched to *INCOMPLETE*. The task list now only shows incomplete tasks.

## Mark tasks as completed

### 1. Mark single task as completed

Input:
1. `add zzz2 task`
2. `add zzz3 task`
3. `mark 15`

Result:
1. The task `zzz2 task` is added.
2. The task `zzz3 task` is added.
3. The task `zzz3 task` disappears from the current task list. The statistics on *INCOMPLETE* & *COMPLETED* tabs are updated.

### 2. Mark multiple tasks as completed

Input:
1. `mark complete 12 - 14`
2. `switch completed`

Result:
1. The tasks `www task`, `zzz task`, `zzz2 task` disappear from the current task list. The statistics on 
*INCOMPLETE* & *COMPLETED* tabs are updated.
2. The completed tasks `www task`, `zzz task`, `zzz2 task`, `zzz3 task` are shown in the current task list.

## Mark tasks as incomplete

### 1. Mark single task as incomplete

Input: `mark incomplete 12`
Result: The task `www task` disappears from the current task list. The statistics on *INCOMPLETE* & *COMPLETED* tabs are updated.

### 2. Mark multiple tasks as incomplete

Input:
1. `mark incomplete 12 -`
2. `switch incomplete`

Result:
1. The tasks `zzz task`, `zzz2 task`, `zzz3 task` disappear from the current task list. The statistics on *INCOMPLETE* & *COMPLETED* tabs are updated.
2. The incomplete tasks `www task`, `zzz task`, `zzz2 task`, `zzz3 task` are shown in the current task list.

## Clear

Input:
1. `clear`
2. `undo`

Result:
1. All tasks are removed
2. Tasks are added back

## Alias

### 1. Add a new alias

Input:
1.  `alias add add`
2.  `alias c add`
3.  `c zombie task`

Result:
1. Result display shows warning that `add is a reserved word`
2. Result displays show that `c` is set as an alias for `add`
3. The task `zombie task` is added

Input: Type `c` without entering
Result: Check that `add` and `clear` are suggested

Input: Type `c a task ` without entering
Result: Check the parameters for add command are suggested

### 2. Update an alias

Input:
1.  `alias c clear`
2.  `c`
3.  `undo`

Result:
1. Result displays show that `c` is set as an alias for `clear`
2. All tasks are removed
3. Tasks are added back

## View aliases

Input: `viewalias`
Result: Result displays shows `c:clear`

## Unalias

Input: 
1. `unalias c`
2. `viewalias`

Result:
1. Result displays shows that the alias `c` has been removed
2. Result displays shows `No aliases found`

## Change the Sort Order

### 1. Sort by a Category

Input: `sort description`.
Result: The tasks are re-ordered by description.

### 2. Sort by Multiple Categories

Input: `sort priority enddate`.
Result: The tasks are re-ordered first by priority, then by end date.

### 3. Sort by Default Sorting Order

Input: `sort default`.

Result: 
1. The tasks are re-ordered to their default ordering.
2. Note the default ordering is: overdue -> priority -> enddate -> startdate -> description

## Find Specific Tasks

### 1. Find by Keyword

Input:
1. `undo 20`
2. `find cs2103`.

Result: The tasks with `cs2103` in the name or tags are displayed.

### 2. Find Specifically by Tag

Input: `find school /tag`.
Result: The tasks with `school` in the tags are displayed.

### 3. Find Specifically by Description

Input: `find buy /name`.
Result: The tasks with `buy` in the description are displayed.

### 4. Find by Case Insensitive

Input: `find EXAM`.

Result: 
1. The tasks with `EXAM` in the name or tags are displayed.
2. Note that it is case insensitive

### 5. Find by Partial

Input: `find cs`.

Result: 
1. The tasks with `cs` in the name or tags are displayed.
2. Note that words which contain `cs` are displayed.

### 6. Find by Multiple Keywords

Input: `find tutorial lab`.

Result: 
1. The tasks with `tutorial` or `lab` in the name or tags are displayed.
2. Note that tasks which only contain one keyword are still displayed.

### 7. Find keywords autocompletion

Input: Type `find ` without entering the commands
Result: Check that the previous search keywords are suggested

## Add Tags to a Task

### 1. Add a Single Tag

Input: `tag 1 newTag`.
Result: The task at index 1 will have the tag `newTag` added.

### 2. Add Multiple Tags

Input: `tag 2 newTag1 newTag2`.
Result: The task at index 1 will have the tags `newTag1` and `newTag2` added.

## Remove Tags from a Task

### 1. Remove a Single Tag

Input: `untag 2 newTag`.
Result: The task at index 2 will have the tag `newTag` removed.

### 2. Remove Multiple Tags

Input: `untag 2 newTag1 newTag2`.
Result: The task at index 2 will have the tags `newTag1` and `newTag2` removed.

## Save Data to a Different Location
Input: `save NewFile.json`.
Result: Note the change in file location in the status bar.

## Exit ToLuist

Input:
1. `exit`.
2. Re-open ToLuist.

Result:
1. Note ToLuist closes.
2. Note that the data is still there.

## Load Data From a Different Location
Input: `load SampleData-copy.json`.

Result:
1. The displayed data in ToLuist will change to the data in the new file.
2. Once again note the change in file location in the status bar.

## Help

### 1. Show General Help

Input: `help`
Result: A help window appears, listing out all the commands available, together with the command description and format.

### 2. Show Detailed Help

Input: `help add`
Result: A help window appears, listing out the command description and format for `add`, together with comments for the command, as well as some examples.

