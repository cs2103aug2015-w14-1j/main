//@@author A0130319R

package notify.logic.command;

import java.util.ArrayList;

import notify.Task;
import notify.logic.TaskManager;

/**
 * The HelpCommand class extends the abstract class ReversibleCommand.
 * 
 * The HelpCommand class is responsible for creating the Result object, corresponding
 * to the HELP command.
 * 
 * This class contains the execute method called by Logic.
 *
 * @author sadhikabilla
 *
 */

public class HelpCommand extends Command {
	
	//These are variables that are required to store the fields of each task 
	TaskManager manager;

	public HelpCommand(Action commandAction, TaskManager manager) {
		
		super(commandAction);
		this.manager = manager;
		
	}

	/**
	 * This method is responsible for creating the Result object corresponding to the HELP action.
	 * The result object is used by the Logic class.  
	 * 
	 * @return 'result' object corresponding to the HELP action. 
	 */
	@Override
	public Result execute() {
		
		Result result = new Result(Action.HELP, new ArrayList<Task>());
		return result;
	}

}
