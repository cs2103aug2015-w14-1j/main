package notify.logic.command;

import java.util.ArrayList;

import notify.Task;
import notify.logic.TaskManager;

public class ListOverdueCommand extends Command {
	
	private TaskManager manager;
	
	public ListOverdueCommand(Action commandAction, TaskManager manager){
		super(commandAction);
		this.manager = manager;
	}
	
	@Override
	public Result execute(){
		ArrayList<Task> listOfResults = manager.getOverdueTasks();
		Result result = new Result(Action.DISPLAY, listOfResults);
		return result;
		
	}
	

}
