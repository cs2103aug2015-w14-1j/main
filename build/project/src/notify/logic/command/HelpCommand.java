//@@author A0130319R

package notify.logic.command;

import java.util.ArrayList;

import notify.Task;
import notify.logic.TaskManager;

public class HelpCommand extends Command {
	
	TaskManager manager;

	public HelpCommand(Action commandAction, TaskManager manager) {
		
		super(commandAction);
		this.manager = manager;
		
	}

	@Override
	public Result execute() {
		
		Result result = new Result(Action.HELP, new ArrayList<Task>());
		return result;
	}

}
