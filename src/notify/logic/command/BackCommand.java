/**
 * Author: Sadhika Billa
 * Matric number: A0130319R
 * For CS2103 - Notify
 */
package notify.logic.command;

import java.util.ArrayList;

import notify.Task;
import notify.logic.TaskManager;

public class BackCommand extends Command {
	
	TaskManager manager;
	
	public BackCommand(Action commandAction, TaskManager manager){
		super(commandAction);
		this.manager = manager;
	}

	@Override
	public Result execute() {
		Result result = new Result(Action.BACK, new ArrayList<Task>());
		return result;
	}

}
