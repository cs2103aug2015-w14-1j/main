package notify.logic.command;

import java.util.ArrayList;

import notify.Task;
import notify.logic.TaskManager;

public class ExitCommand extends Command {
	
	TaskManager manager;
	
	public ExitCommand(Action commandAction, TaskManager manager){
		super(commandAction);
		this.manager = manager;
	}
	
    
	public Result execute(){
		manager.exit();
		Result result = new Result(Action.EXIT, new ArrayList<Task>());
		return result;
	}
	

}
