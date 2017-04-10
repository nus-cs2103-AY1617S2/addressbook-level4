# Manual Testing

## Quick Start
1. Rename the SampleData.xml file to YATS.xml file
2. Copy the renamed file over to /main/data/
3. Start up the application
> * The application should now be pre-loaded with the supplied sample data.

## Test Cases

### Add
`add`

### Delete
`delete INDEX`
1. Application will delete the task at the given index
** Example **
1. `delete 1`
> This will delete the task at index 1

2. `delete 53`
> This will delete the task at index 53
> Additional unstable behaviour may be observed.

`delete INDEX1 INDEX2 INDEX3`

### List
`list`

`list by start 03:45PM`
1. Application will show one result, with the task title "Benzoic Aptitude Test"

`list by end 12/12/2017`
1. Application will show one result, with the task title "Benzoic Aptitude Test:

`list by deadline 10/01/2019`
1. Application will show one result, with the task title "Inductive Reasoning Based Philosophy"

`list by location NUS`
1. Application will show four results
> Search is not case-sensitive

`list by tag tag1`
1. Application will show one result, with the task title "Test event 2"

`list by start`

`list by end`

`list by deadline`

`list by done`


### Schedule
`schedule TASK`
`schedule TASK -h HOUR -m MINUTE`
`schedule TASK -l LOCATION -d DESCRIPTION -h HOUR -m MINUTE`

### Find
`find TASK_TITLE`
`find TASK_DESCRIPTION`

### Mark
`mark INDEX`
`mark INDEX1 INDEX2 INDEX3`

### Select
`select INDEX`

### Undo
`undo`
1. Application will restore the previous state if the previous command mutates the list 

### Redo
`redo`
1. Application will restore the last `undo` command executed

### Clear
`ckear`
1. Application will clear all done task in the task list, both primary and secondary

### Reset
`reset`
1. Application will clear all tasks in the task list, both primary and secondary

### Save
`save default`
1. Application will change the save location to the default save location

`save FILE_LOCATION`
1. Application will change the save location to the specified save location on user's computer

### Help
`help`
1. Application will open up the YATS help web page

### Exit
`exit`
1. Application will exit
