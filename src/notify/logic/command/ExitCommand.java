//@@author A0130319R

package notify.logic.command;

import java.util.ArrayList;

import notify.Task;
import notify.logic.TaskManager;

/**
 * The ExitCommand class extends the abstract class ReversibleCommand.
 * 
 * The ExitCommand class is responsible for creating the Result object, corresponding
 * to the EXIT command.
 * 
 * This class contains the execute method called by Logic.
 *
 * @author sadhikabilla
 *
 */


public class ExitCommand extends Command {
	
	//These are variables that are required to store the fields of each task 
	TaskManager manager;
	
	public ExitCommand(Action commandAction, TaskManager manager){
		
		super(commandAction);
		this.manager = manager;
	}
	
	/**
	 * This method is responsible for creating the Result object corresponding to the EXIT action.
	 * The result object is used by the Logic class.  
	 * 
	 * @return 'result' object corresponding to the EXIT action. 
	 */
	
    @Override
	public Result execute(){
		
		manager.exit();
		
		Result result = new Result(Action.EXIT, new ArrayList<Task>());
		return result;
	}
	

}
