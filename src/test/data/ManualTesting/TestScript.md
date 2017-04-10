# Manual Testing

## Quick Start
1. Rename the SampleData.xml file to YATS.xml file
2. Copy the renamed file over to /main/data/
3. Start up the YATS
> * The YATS should now be pre-loaded with the supplied sample data.

## Test Cases

### Add
`add test floating task`
1. 

### Delete
`delete INDEX`
1. YATS will delete the task at the given index
** Example **
1. `delete 1`
> This will delete the task at index 1

2. `delete 53`
> This will delete the task at index 53
> Additional unstable behaviour may be observed.

`delete INDEX1 INDEX2 INDEX3`

### List
`list`
1. YATS will show all the tasks in the primary list
> Result in the result display
> Listed all tasks
> ** Detailed list is not shown in the TestScript.md**

`list by start 03:45PM`
1. YATS will show one result
> Result (if task is unmarked)
> 1. Benzoic Aptitude Test
> Organic revision
> Benzoic acid is a very acidic compount that requires intense alcoholic exchange. Revision has to be based on ethanol and focus on malloc.
> School
> 03:45PM 12/12/2017 - 07:00PM 12/12/2017

`list by end 12/12/2017`
1. YATS will show one result
> Result (if task is unmarked)
> 1. Benzoic Aptitude Test
> Organic revision
> Benzoic acid is a very acidic compount that requires intense alcoholic exchange. Revision has to be based on ethanol and focus on malloc.
> School
> 03:45PM 12/12/2017 - 07:00PM 12/12/2017

`list by deadline 10/01/2019`
1. YATS will show one result
> Result (if task is unmarked)
> 1. Inductive Reasoning Based Philosophy
> books armaggedon reading home
> Have to finish reading this book by 10th January in preparation for Armaggedon
> Home
> by 10:20AM 10/01/2019

`list by location NUS`
1. YATS will show four results
> Search is not case-sensitive
> Result
> 1. Seeing is believeing
> wild gone
> NUS Wifi is kind of a finicky subject, you don't want to touch it, but you have to use it to survive. Do what you will with it, but I'm leaving.
> NUS
> 2. Remove mine documentation
> techies WOAW
> Did we put the right fuse on that one?
> NUS
> 08:05AM
> 3. Sallen pointer
> never give up you
> Sallen key low pass filtering seems to have failed.
> NUS
> 02:11PM 22/04/2017 - 02:45PM 22/04/2017
> 4. Test configuration file of reverberating system
> recovering system
> System harmonics project failing test cases
> NUS
>  by 11:59PM 15/03/2017

`list by tag tag1`
1. YATS will show one result
> Result
> Test event 2
> tag1 tag6 tag7 tag4 tag5 tag2 tag3
> Loating title within application's database.
> TestDrive
> 09:00AM 09/04/2017 - 05:00PM 09/04/2017

`list by start`
1. YATS will organize the list by start date

`list by end`
1. YATS will organize the list by end date

`list by deadline`
1. YATS will organize the list by deadline

`list by done`
1. YATS will list all done task in the primary list


### Schedule
`schedule TASK`
`schedule TASK -h HOUR -m MINUTE`
`schedule TASK -l LOCATION -d DESCRIPTION -h HOUR -m MINUTE`

### Find
`find minecraft`
1. YATS will find one item
> Result
> 1. Minecraft Lets Play 100
> RT comedy
> Catch up on Minecraft Let's Plays by Rooster Teeth! Comedy as its best!
> RoosterTeeth

`find war`
1. YATS will find one item
> Result
> Red vs Blue
> crusade holy
> The great war between the 2 colours have begun. The crusade is upon us!
> Dutch
> 08:05PM 12/03/2020 - 09:00PM 30/03/2025

### Mark
`mark 1`
1. YATS will mark the first task
> Result in secondary list
> 1. Project Milestone Documentation
> school learn
>  by 11:59PM 08/04/2017

`mark 1 2 3`
1. YATS will mark the first, second and third task
> Result in secondary list
> 1. Project Milestone Documentation
> school learn
>  by 11:59PM 08/04/2017
> 2. Benzoic Aptitude Test
> Organic revision
> 03:45PM 12/12/2017 - 07:00PM 12/12/2017
> 3. Inductive Reasoning Based Phillosophy
> books armaggedon reading home
>  by 10:20AM 10/01/2019

### Unmark
`unmark 1`
1. YATS will unmark the first task in the secondary list

### Select
`select 1`
1. YATS will select the first task by highlight the task in grey

### Undo
`undo`
1. YATS will restore the previous state if the previous command mutates the list 

### Redo
`redo`
1. YATS will restore the last `undo` command executed

### Clear
`clear`
1. YATS will clear all done task in the task list, both primary and secondary
> Result
> Secondary list will be empty, the primary list will be unaffected

### Reset
`reset`
1. YATS will clear all tasks in the task list, both primary and secondary
> Result
> No tasks will be listed in both the primary and secondary list

### Save
`save default`
1. YATS will change the save location to the default save location

`save FILE_LOCATION`
1. YATS will change the save location to the specified save location on user's computer

### Help
`help`
1. YATS will open up the YATS help web page

### Exit
`exit`
1. YATS will exit
