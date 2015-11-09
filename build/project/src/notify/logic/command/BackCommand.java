//@@author A0130319R

package notify.logic.command;

import java.util.ArrayList;

import notify.Task;
import notify.logic.TaskManager;

/**
 * The BackCommand class extends the abstract class Command.
 * 
 * The BackCommand class is responsible for creating the Result object, corresponding
 * to the BACK command.
 * 
 * This class contains the execute method called by Logic.
 *
 * @author sadhikabilla
 *
 */

public class BackCommand extends Command {
	
	//These are variables that are required to store the fields of each task 
	TaskManager manager;
	
	public BackCommand(Action commandAction, TaskManager manager){
		
		super(commandAction);
		this.manager = manager;
		
	}

	/**
	 * This method is responsible for creating the Result object corresponding to the BACK action.
	 * The result object is used by the Logic class.  
	 * 
	 * @return 'result' object corresponding to the BACK action. 
	 */
	
	@Override
	public Result execute() {
		
		Result result = new Result(Action.BACK, new ArrayList<Task>());
		return result;
		
	}

}
