package notify.logic.command;

import java.util.ArrayList;

import notify.Task;
import notify.logic.TaskManager;

public class ListComingSoonCommand extends Command {

    private TaskManager manager;
	
	public ListComingSoonCommand(Action commandAction, TaskManager manager){
		super(commandAction);
		this.manager = manager;
	}
	
	@Override
	public Result execute() {
		ArrayList<Task> listOfResults = manager.getComingSoonTasks();
		Result result = new Result(Action.DISPLAY, listOfResults);
		return result;
	}

}
