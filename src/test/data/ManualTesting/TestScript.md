TestScript

{} brackets means do not type in the command but follow the instructions in ()
() brackets consists of usage keyboard shortcuts or other instructions
[] brackets consists of suggestions for efficiency

load \src\test\data\ManualTesting\SampleData.xml
> You should see the program loaded with 50 tasks 
> Note the footer of the app showing the filepath of the loaded file

add Help son with math homework p/med e/next week d/Calculus t/MA1521
> You should see that a new task is added at number 8 under `Deadline`
> Note how it jumps to and auto highlights the newly added task
> Note that the task have a yellow circle with 1 exclamation mark to indicate its medium priority
> Note it has a tag MA1521
> Note it has a description Calculus
> Note the Date and time to be same time exactly 7 days later



undo
> Note that it has reverted before adding the task

undo 
> You should get an error here as there is nothing to undo

redo
> Note that the son homework task is back

redo 
> You should get an error here as there is nothing to redo

add MA1521 lecture 1 s/next thursday 1pm t/MA1521
> Note a task was created under `Event` under number 18 without an end time
> This is to allow you to edit the task once you have finalised the time

edit 18 e/next thursday 14:00 d/give son a ride to his lecture
> You should now notice that the task has changed with a new description and a new end time

{undo}(Ctrl-Z)
> Note that it has reverted before editing the task

{undo}(Ctrl-Z)
> Note that it has reverted before adding the task

{redo}(Ctrl-Y)
> Note that it has reverted after adding the task

{redo}(Ctrl-Y)
> Note that it has reverted after editing the task

{redo}(Ctrl-Y)
> You should get an error here as there is nothing to redo

edit 8 d/ need to start early s/tmr p/high
> Note that the Help son task moved to the `Event` column from the `Dealine` column and has a start time and high priority indicated by the red circle with 2 exclamation marks. There is also a change in the description

add Change my name
> Note a new task in `Anytime` column has appeared highlighted

delete 25
> Note that the `Anytime` column becomes empty as the task Change my name disappears

undo
> Note that it has reverted before you delete the task

redo
> Note that it has reverted to after you delete the task

delete 100
> Note that there is an error as there is no index 100 in the list

(press down press up again)
> Note when you press UP delete will appear in your command box
> When you press down nothing should be in the command box

add buy ColdPlay tickets
> Note that it was added under column `Anytime` at number 24 as there was no start or end date given.

redo [Subsequent undo and redo can be short cuts and you can utilize the UP and DOWN key to navigate the previous inputs for efficiency]
> Note that you cannot redo after you successfully inputed a valid add command it should be the case for all the CRUD

undo
>Note that you can undone a add floating task command

redo
>Note that you can redo the add floating task that was undone

mark 1
> Note how the task at number 1 has disappeared
> This means that the task has been completed

done
> Note the display has changed to display completed tasks
> Completed tasks are indicated by a green tick next to it

unmark 1
> Note how the task at 1 is back to being uncompleted
> Note how the display changed to display all uncompleted tasks and highlight the unmarked task

undo
> Note that task has been completed again
> Meaning that mark command can be undone

done
redo
> Note that task has been uncompleted again
> Meaning that mark command can be redone

list
delete 1 3
> Note that the only task at 1 and 3 were deleted
> Allow multiple items to be deleted

undo
delete 1-3
> Note that the tasks from 1 to 3 were deleted
> Allow a range of items to be deleted

undo
edit 3 e/yesterday
> Note how the time turns red
> This signifies that it is overdue

edit 1 e/
> Note how the task moves from `Deadline` to `Anytime`

edit 3 e/tmr s/yesterday
> Note how the start and end times were swapped automatically

edit 1 s/last week
> Note how the task moves from `Anytime` to `Event`

undo
undo
undo
> Notice that multiple commands were undone


save data/testdata1.xml
> Note that the display is the same but the filepath at the footer of the app has changed to the new one

save \src\test\data\ManualTesting\SampleData.xml 
> You should see an error message saying that the file exists
> Note that the filepath at the footer is still the same

add Testing data
> You should see Testing data added into the `Anytime` column highlighted
> Note that this is still in the testdata1.xml file

load \src\test\data\ManualTesting\SampleData.xml
> You should realize that the Testing data floating task is not present in this file indicating that the file has indeed change.

undo
> Note that there will be an error message saying that there is nothing to undo
>
redo
> Note that there will be an error message saying that there is nothing to redo


save \src\test\data\ManualTesting\SampleData.xml
> Note that there will be an error saying it is currently on the file, it will be auto saved
save data/testdata1.xml
> Note that there will be an error saying the file exists

load data/testdata1.xml
> Note that testdata1 will be loaded with the Testing data floating task present in the `Anytime` column

help
> You should see a pop up window showing the command summary
(close help window)

{help} (F1)
> You should see a pop up window showing the command summary
(close help window)

{help} (press top button)
> You should see a pop up window showing the command summary
(close help window)

mark 1
> You should notice the task in index 1 disappears
> This is because it is marked as done

exit
> You should notice the app closes
(open app again)

undo
> Note that there will be an error message saying that there is nothing to undo
> You cannot undo previous command the marking of a task
> This is because after reopening the app the undo stack is refreshed

load \src\test\data\ManualTesting\SampleData.xml
> You should notice that SampleData.xml is loaded

(exit by pressing top button in file)
> You should notice the app closes
(open app again)

(exit using the top right close button)
> You should notice the app closes
(open app again)


find t/Test
> Note that only tasks with the tag Test are listed

find t/Test t/CS2101
> Note how only tasks with both tag Test and CS2101 are listed


find p/low p/med
> Note how task with either low or med priority are listed
> Note that completed task show up too
> You can also type find p/low med for the same effect

find t/
> Note the invalid command message

find n/Coldplay
> Note that you have to specify n/ to search for name

find e/04/20/17
> Note that you can find using time too


sort priority
> Note that tasks ordered by priority

sort start time
> Note that tasks ordered by start time
> If no start time then will be ordered lexicographically

sort end time
> Note that tasks ordered by end time
> If no end time then will be ordered lexicographically

set ADD a
> Now you can use "a" as a shortcut for add
> Also note that add is case insensitive

a Learn to use shortcuts
> Now that it behaves the same as add

add If you forget shortcut use default
> Note how the default command is still there

set DElete add
> note that you are not allowed to change to an existing command
> Note that default command delete is also case insensitive

set add +
a Invalid Command
> Note that since shortcut was overwritten, a is no longer a shortcut
> Meaning one command = one shortcut

set find looking for
> You should notice an error message saying the format is wrong

set find LookingFor
> You should notice a success message saying find has been set to lookingfor all in lower case

lookingFOR d/
> Note that lookingFOR can be used to find those task without the description
> This is because alias command and default commands are case insensitive

A need to buy pen
> Note that A is the upper case of a which as set to be the alias of add
> Note that A can be use to add the floating task need to buy pen
