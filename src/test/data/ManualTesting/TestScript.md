## Set Up
1. Prepare a folder and place FunTaskTic.jar in it. All extra files that FunTaskTic produces will be in the same folder.
2. In this folder, create another folder `data`and put `SampleData.xml` in `data`
3. Run FunTaskTic
4. Load the sample data with 50 tasks with command `load data/SampleData.xml`

## Test Script
|	Intention	|	Input command	|	Expected result	|
|	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	|	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	|	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	|
| show help | help | the user guide is displayed in a new window |
| add floating task | add Write english essay d/1000 words | task is added and is automatically selected in the list |
| add task | add Prepare for maths test e/next week | task with end date is added  and automatically selected |
