package seedu.doit.commons.core;

import static seedu.doit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.Serializable;

import seedu.doit.logic.commands.AddCommand;
import seedu.doit.logic.commands.ClearCommand;
import seedu.doit.logic.commands.DeleteCommand;
import seedu.doit.logic.commands.DoneCommand;
import seedu.doit.logic.commands.EditCommand;
import seedu.doit.logic.commands.ExitCommand;
import seedu.doit.logic.commands.FindCommand;
import seedu.doit.logic.commands.HelpCommand;
import seedu.doit.logic.commands.ListCommand;
import seedu.doit.logic.commands.LoadCommand;
import seedu.doit.logic.commands.MarkCommand;
import seedu.doit.logic.commands.RedoCommand;
import seedu.doit.logic.commands.SaveCommand;
import seedu.doit.logic.commands.SelectCommand;
import seedu.doit.logic.commands.SetCommand;
import seedu.doit.logic.commands.SortCommand;
import seedu.doit.logic.commands.UndoCommand;
import seedu.doit.logic.commands.UnmarkCommand;
import seedu.doit.logic.commands.exceptions.CommandExistedException;
import seedu.doit.logic.commands.exceptions.NoSuchCommandException;

//@@author A0138909R
public class CommandSettings implements Serializable {

    private String add;
    private String delete;
    private String done;
    private String edit;
    private String mark;
    private String clear;
    private String exit;
    private String find;
    private String help;
    private String list;
    private String load;
    private String redo;
    private String save;
    private String select;
    private String set;
    private String sort;
    private String undo;
    private String unmark;

    private static CommandSettings instance = null;

    public static CommandSettings getInstance() {
        if (instance == null) {
            instance = new CommandSettings();
        }
        return instance;
    }

    protected CommandSettings() {
        this.add = AddCommand.COMMAND_WORD;
        this.delete = DeleteCommand.COMMAND_WORD;
        this.edit = EditCommand.COMMAND_WORD;
        this.done = DoneCommand.COMMAND_WORD;
        this.clear = ClearCommand.COMMAND_WORD;
        this.exit = ExitCommand.COMMAND_WORD;
        this.find = FindCommand.COMMAND_WORD;
        this.help = HelpCommand.COMMAND_WORD;
        this.list = ListCommand.COMMAND_WORD;
        this.load = LoadCommand.COMMAND_WORD;
        this.mark = MarkCommand.COMMAND_WORD;
        this.redo = RedoCommand.COMMAND_WORD;
        this.save = SaveCommand.COMMAND_WORD;
        this.select = SelectCommand.COMMAND_WORD;
        this.set = SetCommand.COMMAND_WORD;
        this.sort = SortCommand.COMMAND_WORD;
        this.undo = UndoCommand.COMMAND_WORD;
        this.unmark = UnmarkCommand.COMMAND_WORD;
    }

    public void setCommandSettings(String add, String delete, String done, String edit, String clear, String exit,
            String find, String help, String list, String load, String mark, String redo, String save, String select,
            String set, String sort, String undo, String unmark) {
        this.add = add;
        this.delete = delete;
        this.done = done;
        this.edit = edit;
        this.mark = mark;
        this.clear = clear;
        this.exit = exit;
        this.find = find;
        this.help = help;
        this.list = list;
        this.load = load;
        this.redo = redo;
        this.save = save;
        this.select = select;
        this.sort = sort;
        this.undo = undo;
        this.unmark = unmark;
        this.set = set;
    }

    // Getter
    public String getAdd() {
        return this.add;
    }

    public String getDelete() {
        return this.delete;
    }

    public String getDone() {
        return this.done;
    }

    public String getEdit() {
        return this.edit;
    }

    public String getMark() {
        return this.mark;
    }

    public String getUnmark() {
        return this.unmark;
    }

    public String getClear() {
        return this.clear;
    }

    public String getExit() {
        return this.exit;
    }

    public String getFind() {
        return this.find;
    }

    public String getHelp() {
        return this.help;
    }

    public String getList() {
        return this.list;
    }

    public String getLoad() {
        return this.load;
    }

    public String getRedo() {
        return this.redo;
    }

    public String getSave() {
        return this.save;
    }

    public String getSelect() {
        return this.select;
    }

    public String getSet() {
        return this.set;
    }

    public String getSort() {
        return this.sort;
    }

    public String getUndo() {
        return this.undo;
    }

    // Setter
    public static void setInstance(CommandSettings commandSettings) {
        instance = commandSettings;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setUnmark(String unmark) {
        this.unmark = unmark;
    }

    public void setClear(String clear) {
        this.clear = clear;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }

    public void setFind(String find) {
        this.find = find;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public void setList(String list) {
        this.list = list;
    }

    public void setLoad(String load) {
        this.load = load;
    }

    public void setRedo(String redo) {
        this.redo = redo;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setUndo(String undo) {
        this.undo = undo;
    }

    public void setCommand(String oldCommand, String newCommand)
            throws NoSuchCommandException, CommandExistedException {
        if (doesCommandExist(newCommand)) {
            throw new CommandExistedException(MESSAGE_UNKNOWN_COMMAND);
        } else if (AddCommand.COMMAND_WORD.equals(oldCommand) || this.add.equals(oldCommand)) {
            setAdd(newCommand);
        } else if (EditCommand.COMMAND_WORD.equals(oldCommand) || this.edit.equals(oldCommand)) {
            setEdit(newCommand);
        } else if (SelectCommand.COMMAND_WORD.equals(oldCommand) || this.select.equals(oldCommand)) {
            setSelect(newCommand);
        } else if (DoneCommand.COMMAND_WORD.equals(oldCommand) || this.done.equals(oldCommand)) {
            setDone(newCommand);
        } else if (MarkCommand.COMMAND_WORD.equals(oldCommand) || this.mark.equals(oldCommand)) {
            setMark(newCommand);
        } else if (UnmarkCommand.COMMAND_WORD.equals(oldCommand) || this.unmark.equals(oldCommand)) {
            setUnmark(newCommand);
        } else if (SortCommand.COMMAND_WORD.equals(oldCommand) || this.sort.equals(oldCommand)) {
            setSort(newCommand);
        } else if (DeleteCommand.COMMAND_WORD.equals(oldCommand) || this.delete.equals(oldCommand)) {
            setDelete(newCommand);
        } else if (ClearCommand.COMMAND_WORD.equals(oldCommand) || this.clear.equals(oldCommand)) {
            setClear(newCommand);
        } else if (FindCommand.COMMAND_WORD.equals(oldCommand) || this.find.equals(oldCommand)) {
            setFind(newCommand);
        } else if (ListCommand.COMMAND_WORD.equals(oldCommand) || this.list.equals(oldCommand)) {
            setList(newCommand);
        } else if (LoadCommand.COMMAND_WORD.equals(oldCommand) || this.load.equals(oldCommand)) {
            setLoad(newCommand);
        } else if (ExitCommand.COMMAND_WORD.equals(oldCommand) || this.exit.equals(oldCommand)) {
            setExit(newCommand);
        } else if (HelpCommand.COMMAND_WORD.equals(oldCommand) || this.help.equals(oldCommand)) {
            setHelp(newCommand);
        } else if (SaveCommand.COMMAND_WORD.equals(oldCommand) || this.save.equals(oldCommand)) {
            setSave(newCommand);
        } else if (UndoCommand.COMMAND_WORD.equals(oldCommand) || this.undo.equals(oldCommand)) {
            setUndo(newCommand);
        } else if (RedoCommand.COMMAND_WORD.equals(oldCommand) || this.redo.equals(oldCommand)) {
            setRedo(newCommand);
        } else if (SetCommand.COMMAND_WORD.equals(oldCommand) || this.set.equals(oldCommand)) {
            setSet(newCommand);
        } else {
            throw new NoSuchCommandException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    public boolean doesCommandExist(String command) {
        switch (command) {

        case AddCommand.COMMAND_WORD:
        case EditCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_WORD:
        case DoneCommand.COMMAND_WORD:
        case MarkCommand.COMMAND_WORD:
        case UnmarkCommand.COMMAND_WORD:
        case SortCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_WORD:
        case FindCommand.COMMAND_WORD:
        case ListCommand.COMMAND_WORD:
        case LoadCommand.COMMAND_WORD:
        case ExitCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_WORD:
        case SaveCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_WORD:
        case SetCommand.COMMAND_WORD:
            return true;
        }
        if (this.add.equals(command) || this.delete.equals(command) || this.edit.equals(command)
                || this.done.equals(command) || this.clear.equals(command) || this.exit.equals(command)
                || this.find.equals(command) || this.help.equals(command) || this.list.equals(command)
                || this.load.equals(command) || this.mark.equals(command) || this.redo.equals(command)
                || this.save.equals(command) || this.select.equals(command) || this.set.equals(command)
                || this.sort.equals(command) || this.undo.equals(command) || this.unmark.equals(command)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.add == null) ? 0 : this.add.hashCode());
        result = (prime * result) + ((this.clear == null) ? 0 : this.clear.hashCode());
        result = (prime * result) + ((this.delete == null) ? 0 : this.delete.hashCode());
        result = (prime * result) + ((this.done == null) ? 0 : this.done.hashCode());
        result = (prime * result) + ((this.edit == null) ? 0 : this.edit.hashCode());
        result = (prime * result) + ((this.exit == null) ? 0 : this.exit.hashCode());
        result = (prime * result) + ((this.find == null) ? 0 : this.find.hashCode());
        result = (prime * result) + ((this.help == null) ? 0 : this.help.hashCode());
        result = (prime * result) + ((this.list == null) ? 0 : this.list.hashCode());
        result = (prime * result) + ((this.load == null) ? 0 : this.load.hashCode());
        result = (prime * result) + ((this.mark == null) ? 0 : this.mark.hashCode());
        result = (prime * result) + ((this.redo == null) ? 0 : this.redo.hashCode());
        result = (prime * result) + ((this.save == null) ? 0 : this.save.hashCode());
        result = (prime * result) + ((this.select == null) ? 0 : this.select.hashCode());
        result = (prime * result) + ((this.set == null) ? 0 : this.set.hashCode());
        result = (prime * result) + ((this.sort == null) ? 0 : this.sort.hashCode());
        result = (prime * result) + ((this.undo == null) ? 0 : this.undo.hashCode());
        result = (prime * result) + ((this.unmark == null) ? 0 : this.unmark.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CommandSettings)) {
            return false;
        }
        CommandSettings other = (CommandSettings) obj;
        if (this.add == null) {
            if (other.add != null) {
                return false;
            }
        } else if (!this.add.equals(other.add)) {
            return false;
        }
        if (this.clear == null) {
            if (other.clear != null) {
                return false;
            }
        } else if (!this.clear.equals(other.clear)) {
            return false;
        }
        if (this.delete == null) {
            if (other.delete != null) {
                return false;
            }
        } else if (!this.delete.equals(other.delete)) {
            return false;
        }
        if (this.done == null) {
            if (other.done != null) {
                return false;
            }
        } else if (!this.done.equals(other.done)) {
            return false;
        }
        if (this.edit == null) {
            if (other.edit != null) {
                return false;
            }
        } else if (!this.edit.equals(other.edit)) {
            return false;
        }
        if (this.exit == null) {
            if (other.exit != null) {
                return false;
            }
        } else if (!this.exit.equals(other.exit)) {
            return false;
        }
        if (this.find == null) {
            if (other.find != null) {
                return false;
            }
        } else if (!this.find.equals(other.find)) {
            return false;
        }
        if (this.help == null) {
            if (other.help != null) {
                return false;
            }
        } else if (!this.help.equals(other.help)) {
            return false;
        }
        if (this.list == null) {
            if (other.list != null) {
                return false;
            }
        } else if (!this.list.equals(other.list)) {
            return false;
        }
        if (this.load == null) {
            if (other.load != null) {
                return false;
            }
        } else if (!this.load.equals(other.load)) {
            return false;
        }
        if (this.mark == null) {
            if (other.mark != null) {
                return false;
            }
        } else if (!this.mark.equals(other.mark)) {
            return false;
        }
        if (this.redo == null) {
            if (other.redo != null) {
                return false;
            }
        } else if (!this.redo.equals(other.redo)) {
            return false;
        }
        if (this.save == null) {
            if (other.save != null) {
                return false;
            }
        } else if (!this.save.equals(other.save)) {
            return false;
        }
        if (this.select == null) {
            if (other.select != null) {
                return false;
            }
        } else if (!this.select.equals(other.select)) {
            return false;
        }
        if (this.set == null) {
            if (other.set != null) {
                return false;
            }
        } else if (!this.set.equals(other.set)) {
            return false;
        }
        if (this.sort == null) {
            if (other.sort != null) {
                return false;
            }
        } else if (!this.sort.equals(other.sort)) {
            return false;
        }
        if (this.undo == null) {
            if (other.undo != null) {
                return false;
            }
        } else if (!this.undo.equals(other.undo)) {
            return false;
        }
        if (this.unmark == null) {
            if (other.unmark != null) {
                return false;
            }
        } else if (!this.unmark.equals(other.unmark)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Add : " + this.add + "\n");
        sb.append("Delete : " + this.delete + "\n");
        sb.append("Edit : " + this.edit + "\n");
        sb.append("Done : " + this.done + "\n");
        sb.append("Clear : " + this.clear + "\n");
        sb.append("Exit : " + this.exit + "\n");
        sb.append("Find : " + this.find + "\n");
        sb.append("Help : " + this.help + "\n");
        sb.append("List : " + this.list + "\n");
        sb.append("Load : " + this.load + "\n");
        sb.append("Mark : " + this.mark + "\n");
        sb.append("Redo : " + this.redo + "\n");
        sb.append("Save : " + this.save + "\n");
        sb.append("Select : " + this.select + "\n");
        sb.append("Set : " + this.set + "\n");
        sb.append("Sort : " + this.sort + "\n");
        sb.append("Undo : " + this.undo + "\n");
        sb.append("Unmark : " + this.unmark);
        return sb.toString();
    }

}
