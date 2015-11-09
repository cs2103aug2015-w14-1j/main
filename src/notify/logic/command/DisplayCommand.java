//@@author A0130319R

package notify.logic.command;

import java.util.ArrayList;

import notify.Task;
import notify.logic.TaskManager;

/**
 * The DisplayCommand class extends the abstract class ReversibleCommand.
 * 
 * The DisplayCommand class is responsible for creating the Result object, corresponding
 * to the DISPLAY command.
 * 
 * This class contains the execute method called by Logic.
 *
 * @author sadhikabilla
 *
 */

public class DisplayCommand extends Command {
	
	//These are variables that are required to store the fields of each task 
	private TaskManager manager;
	
	public DisplayCommand(Action commandAction, TaskManager manager) {
		
		super(commandAction);
		this.manager = manager;
	}

	/**
	 * This method is responsible for creating the Result object corresponding to the DISPLAY action.
	 * The result object is used by the Logic class.  
	 * 
	 * @return 'result' object corresponding to the DISPLAY action. 
	 */
	
	@Override
	public Result execute() {
		
		ArrayList<Task> completedTasks = manager.getTasks(true);
		Result result = new Result(Action.DISPLAY, completedTasks);
		return result;
	}
}
	
	
