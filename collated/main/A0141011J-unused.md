# A0141011J-unused
###### /java/seedu/taskit/logic/LogicManager.java
``` java
    @Override
    public CommandResult execute(String commandText) throws CommandException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        //commandList.addCommand(command);
        //command.setCommandHistory(commandList);
        if (command.isUndoable()) {
            model.save();
        }
        return command.execute();
    }
```
