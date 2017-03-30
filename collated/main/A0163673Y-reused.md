# A0163673Y-reused
###### /java/seedu/task/logic/parser/EditCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_TAG, PREFIX_START, PREFIX_END, PREFIX_DUEDATE);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setDescription(ParserUtil.parseDescription(preambleFields.get(1)));
            editTaskDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
            editTaskDescriptor.setDueDate(ParserUtil.parseDueDate(argsTokenizer.getValue(PREFIX_DUEDATE)));
            editTaskDescriptor.setDurationStart(ParserUtil.parseString(argsTokenizer.getValue(PREFIX_START)));
            editTaskDescriptor.setDurationEnd(ParserUtil.parseString(argsTokenizer.getValue(PREFIX_END)));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editTaskDescriptor);
    }
```
###### /java/seedu/task/model/ModelManager.java
``` java
        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().description, keyword)
                            || UniqueTagList.containsWordIgnoreCase(task.getTags(), keyword))
                    .findAny()
                    .isPresent();
        }
```
