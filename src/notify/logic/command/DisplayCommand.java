package notify.logic.command;

import java.util.ArrayList;

import notify.Task;
import notify.logic.TaskManager;

public class DisplayCommand extends Command {
	
	private TaskManager manager;
	
	public DisplayCommand(Action commandAction, TaskManager manager) {
		super(commandAction);
		this.manager = manager;
	}

	@Override
	public Result execute() {
		ArrayList<Task> completedTasks = manager.getTask(true);
		Result result = new Result(Action.DISPLAY, completedTasks);
		return result;
	}
}
	
	
